package com.example.Luna.security.service;

import com.example.Luna.api.model.UserActivation;
import com.example.Luna.repository.UserActivationRepository;
import com.example.Luna.security.auth.AuthenticationRequest;
import com.example.Luna.security.auth.AuthenticationResponse;
import com.example.Luna.security.auth.RegisterRequest;
import com.example.Luna.api.model.User;
import com.example.Luna.repository.UserRepository;


import com.example.Luna.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final SecureRandom secureRandom = new SecureRandom();
    private final EmailService emailService;
    private final UserActivationRepository userActivationRepository;

    public String register(RegisterRequest request) {

        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());

        //check if account is enabled if not send new verification request
        if (existingUser.isPresent()) {
            if (existingUser.get().isEnabled())
                throw new IllegalArgumentException("Email already in use");
            else {
                UserActivation userActivation = createUserActivation(existingUser.get());

                emailService.send(
                        existingUser.get().getEmail(),
                        "Activate your account",
                        "http://localhost:8080//api/v1/auth/verifyMail/" + existingUser.get().getEmail() + "/" + userActivation.getOtp(),
                        existingUser.get().getName(),
                        "Almost ready! To activate your account follow this link",
                        "Account Activation"
                );
                return "Check your email to activate account";
            }
        }

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already in use");
        }

        User newUser = User.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(new HashSet<>())
                .enabled(false)
                .build();
        userRepository.save(newUser);
        UserActivation userActivation = createUserActivation(newUser);
        emailService.send(
                newUser.getEmail(),
                "Activate your account",
                "http://localhost:8080//api/v1/auth/verifyMail/" + newUser.getEmail() + "/" + userActivation.getOtp(),
                newUser.getName(),
                "Almost ready! To activate your account follow this link",
                "Account Activation"
        );

        return "Account created! Check your email to activate account";
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByUsername(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public String verifyMail(String email, String otp) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email not found"));

        UserActivation userActivation = userActivationRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("User activation not found"));

        if (userActivation.getExpirationTime().before(new Date()) ) {
            throw new RuntimeException("OTP expired");
        }

        if (userActivation.getOtp().equals(otp)) {
            user.setEnabled(true);
            userRepository.save(user);
        }

        return "Account verified!";
    }

    private UserActivation createUserActivation(User user) {
        byte[] rawToken = new byte[32];
        secureRandom.nextBytes(rawToken);
        String plainToken =  Base64.getUrlEncoder().withoutPadding().encodeToString(rawToken);

        Optional<UserActivation> userActivation = userActivationRepository.findByUser(user);

        if (userActivation.isPresent()) {
            //userActivation is present: update fields
            userActivation.get().setOtp(plainToken);
            userActivation.get().setExpirationTimeInHours(1);
            userActivationRepository.save(userActivation.get());
            return userActivation.get();
        } else {
            //crate new UserActivation
            UserActivation newUserActivation = new UserActivation();
            newUserActivation.setUser(user);
            newUserActivation.setOtp(plainToken);
            newUserActivation.setExpirationTimeInHours(1);
            userActivationRepository.save(newUserActivation);
            return newUserActivation;
        }
    }
}