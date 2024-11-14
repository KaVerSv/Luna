package com.example.Luna.service;

import org.springframework.stereotype.Service;
import com.example.Luna.api.dto.ChangePassword;

import com.example.Luna.api.model.ForgotPassword;
import com.example.Luna.api.model.OldPassword;
import com.example.Luna.api.model.User;
import com.example.Luna.repository.ForgotPasswordRepository;
import com.example.Luna.repository.OldPasswordRepository;
import com.example.Luna.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ForgotPasswordService {

    private final UserRepository userRepository;
    private final SecureRandom secureRandom = new SecureRandom();
    private final EmailService emailService;
    private final ForgotPasswordRepository forgotPasswordRepository;
    private final PasswordEncoder passwordEncoder;
    private final OldPasswordRepository oldPasswordRepository;

    public void generateAndSendOtp(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email not found"));

        // generate 256-bit token
        byte[] rawToken = new byte[32];
        secureRandom.nextBytes(rawToken);
        String plainToken = Base64.getUrlEncoder().withoutPadding().encodeToString(rawToken);
        String otpHashed = passwordEncoder.encode(plainToken);

        //get existing fp or create new
        ForgotPassword fp = forgotPasswordRepository.findByUser(user)
                .orElse(ForgotPassword.builder()
                        .otp(otpHashed)
                        .expirationTime(new Date(System.currentTimeMillis() + 300000)) // 5 minutes valid
                        .user(user)
                        .failedAttempts(1) // first attempt
                        .blockTime(null)
                        .build());

        if (fp.getBlockTime() != null && fp.getBlockTime().after(new Date())) {
            throw new RuntimeException("Password reset is temporarily blocked due to too many failed attempts.");
        }
        if (fp.getFailedAttempts() >= 5) {
            fp.setBlockTime(new Date(System.currentTimeMillis() + 86400000)); // Blockade na 24h
            forgotPasswordRepository.save(fp);
            throw new RuntimeException("Account is temporarily locked due to too many failed attempts.");
        }

        fp.setOtp(otpHashed);
        fp.setExpirationTime(new Date(System.currentTimeMillis() + 300000)); // 5 minutes
        fp.setFailedAttempts(fp.getFailedAttempts() + 1);
        forgotPasswordRepository.save(fp);

        emailService.send(
                email,
                "Password recovery request",
                "http://localhost:8080/forgotPassword/verifyMail/kacperwx@gmail.com" + plainToken,
                user.getName(),
                "Request for password recovery, to reset your password follow this link"
        );
    }

    public void changePassword(String otp, String email, ChangePassword changePassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email not found"));

        ForgotPassword fp = forgotPasswordRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Invalid OTP"));

        verifyOtp(otp, email);

        if (!changePassword.password().equals(changePassword.repeatedPassword())) {
            throw new RuntimeException("Passwords do not match.");
        }

        String newEncodedPassword = passwordEncoder.encode(changePassword.password());
        List<OldPassword> oldPasswords = oldPasswordRepository.findByUser(user).orElse(Collections.emptyList());

        for (OldPassword oldPassword : oldPasswords) {
            if (passwordEncoder.matches(changePassword.password(), oldPassword.getPassword())) {
                throw new RuntimeException("New password cannot be the same as previous passwords.");
            }
        }

        userRepository.updatePassword(email, newEncodedPassword);
        oldPasswordRepository.save(new OldPassword(newEncodedPassword, user));
        forgotPasswordRepository.delete(fp);
    }

    private void verifyOtp(String otp, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email not found"));

        ForgotPassword fp = forgotPasswordRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("OTP not found for user"));

        if (fp.getBlockTime() != null && fp.getBlockTime().after(new Date())) {
            throw new RuntimeException("Account is temporarily locked.");
        }

        if (fp.getExpirationTime().before(new Date()) ) {
            throw new RuntimeException("OTP expired");
        }

        if (!passwordEncoder.matches(otp, fp.getOtp())) {
            throw new RuntimeException("is invalid.");
        }
    }
}
