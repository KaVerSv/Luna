package com.example.Luna.api.controller;

import com.example.Luna.api.dto.ChangePassword;
import com.example.Luna.api.dto.MailBody;
import com.example.Luna.api.model.ForgotPassword;
import com.example.Luna.api.model.OldPassword;
import com.example.Luna.api.model.User;
import com.example.Luna.repository.ForgotPasswordRepository;
import com.example.Luna.repository.OldPasswordRepository;
import com.example.Luna.repository.UserRepository;
import com.example.Luna.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forgotPassword")
public class ForgotPasswordController {

    private final UserRepository userRepository;
    private final SecureRandom secureRandom = new SecureRandom();
    private final EmailService emailService;
    private final ForgotPasswordRepository forgotPasswordRepository;
    private final PasswordEncoder passwordEncoder;
    private final OldPasswordRepository oldPasswordRepository;

    //send mail for email verification
    @PostMapping("/verifyMail/{email}")
    public ResponseEntity<String> verifyEmail(@PathVariable String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("Email not found"));

        int otp = secureRandom.nextInt();

        MailBody mailBody = MailBody.builder()
                .to(email)
                .text("This is OTP for your Forgot Password request : " + otp)
                .subject("OTP for forgot Password request")
                .build();

        ForgotPassword fp = ForgotPassword.builder()
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis() + 70000)) //15 min duration
                .user(user)
                .build();

        forgotPasswordRepository.save(fp);
        emailService.sendSimpleMessage(mailBody);

        return ResponseEntity.ok("Email sent for verification!");
    }

    @PostMapping("/verifyOtp/{otp}/{email}")
    public ResponseEntity<String> verifyOtp(@PathVariable Integer otp, @PathVariable String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("Email not found"));

        ForgotPassword fp = forgotPasswordRepository.findByOtpAndUser(otp, user)
                .orElseThrow(()->new RuntimeException("Invalid OTP for email: " + email));

        if (fp.getExpirationTime().before(Date.from(Instant.now()))) {
            forgotPasswordRepository.deleteById(fp.getId());
            return new ResponseEntity<>("OTP has expired!", HttpStatus.EXPECTATION_FAILED);
        }

        return ResponseEntity.ok("OTP verified!");
    }

    @PostMapping("/changePassword/{email}")
    public ResponseEntity<String> changePassword(@RequestBody ChangePassword changePassword, @PathVariable String email) {
        if (!Objects.equals(changePassword.password(), changePassword.repeatedPassword())) {
            return new ResponseEntity<>("Please enter the password again!", HttpStatus.EXPECTATION_FAILED);
        }

        String encodedPassword = passwordEncoder.encode(changePassword.password());

        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("Email not found"));

        List<OldPassword> oldPasswords = oldPasswordRepository.findByUser(user)
                .orElse(Collections.emptyList());

        for (OldPassword oldPassword : oldPasswords) {
            if (passwordEncoder.matches(encodedPassword, oldPassword.getPassword())) {
                return new ResponseEntity<>("New password cannot be the same as a previous password.", HttpStatus.CONFLICT);
            }
        }

        userRepository.updatePassword(email, encodedPassword);

        //save new password to old Passwords
        OldPassword savedOldPassword = new OldPassword(encodedPassword, user);
        oldPasswordRepository.save(savedOldPassword);

        return ResponseEntity.ok("Password changed!");
    }

}
