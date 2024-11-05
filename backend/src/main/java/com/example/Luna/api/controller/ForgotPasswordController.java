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
import java.util.*;

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

        //generate 256-bit key
        byte[] token = new byte[32];
        secureRandom.nextBytes(token);

        String otp =  Base64.getUrlEncoder().withoutPadding().encodeToString(token);

        // check if user have already an otp
        Optional<ForgotPassword> existingOtp = forgotPasswordRepository.findByUser(user);

        ForgotPassword fp;
        if (existingOtp.isPresent()) {
            //update otp with new values
            fp = existingOtp.get();
            fp.setOtp(otp);
            fp.setExpirationTime(new Date(System.currentTimeMillis() + 300000)); // 5 min duration
        } else {
            //create new
            fp = ForgotPassword.builder()
                    .otp(otp)
                    .expirationTime(new Date(System.currentTimeMillis() + 300000)) // 5 min duration
                    .user(user)
                    .build();
        }

        MailBody mailBody = MailBody.builder()
                .to(email)
                .text("This is OTP for your Forgot Password request : " + otp)
                .subject("OTP for forgot Password request")
                .build();

        forgotPasswordRepository.save(fp);
        emailService.sendSimpleMessage(mailBody);

        return ResponseEntity.ok("Email sent for verification!");
    }

    @PostMapping("/verifyOTP/{otp}/{email}")
    public ResponseEntity<String> verifyOtp(@PathVariable String otp, @PathVariable String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("Email not found"));

        ForgotPassword fp = forgotPasswordRepository.findByOtpAndUser(otp, user)
                .orElseThrow(()->new RuntimeException("Invalid OTP " + email));

        if (fp.getExpirationTime().before(Date.from(Instant.now()))) {
            forgotPasswordRepository.deleteById(fp.getId());
            return new ResponseEntity<>("OTP has expired!", HttpStatus.EXPECTATION_FAILED);
        }

        return ResponseEntity.ok("OTP verified!");
    }

    @PostMapping("/changePassword/{otp}/{email}")
    public ResponseEntity<String> changePassword(@RequestBody ChangePassword changePassword,@PathVariable String otp, @PathVariable String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("Email not found"));

        ForgotPassword fp = forgotPasswordRepository.findByOtpAndUser(otp, user)
                .orElseThrow(()->new RuntimeException("Invalid OTP " + email));

        if (fp.getExpirationTime().before(Date.from(Instant.now()))) {
            forgotPasswordRepository.deleteById(fp.getId());
            return new ResponseEntity<>("OTP has expired!", HttpStatus.EXPECTATION_FAILED);
        }

        if (!Objects.equals(changePassword.password(), changePassword.repeatedPassword())) {
            return new ResponseEntity<>("Please enter the password again!", HttpStatus.EXPECTATION_FAILED);
        }

        String encodedPassword = passwordEncoder.encode(changePassword.password());

        List<OldPassword> oldPasswords = oldPasswordRepository.findByUser(user)
                .orElse(Collections.emptyList());

        for (OldPassword oldPassword : oldPasswords) {
            if (passwordEncoder.matches(encodedPassword, oldPassword.getPassword())) {
                return new ResponseEntity<>("New password cannot be the same as a previous password.", HttpStatus.CONFLICT);
            }
        }

        //update password
        userRepository.updatePassword(email, encodedPassword);

        //save new password to old Passwords
        OldPassword savedOldPassword = new OldPassword(encodedPassword, user);
        oldPasswordRepository.save(savedOldPassword);

        //delete otp after successful use
        forgotPasswordRepository.deleteById(fp.getId());

        return ResponseEntity.ok("Password changed!");
    }
}
