package com.example.Luna.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Entity
@Table(name = "user_activations")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class UserActivation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String otp;

    @Column(nullable = false)
    private Date expirationTime;

    @OneToOne
    private User user;

    public void setExpirationTimeInHours(int hours) {
        LocalDateTime expirationTime = LocalDateTime.now().plusHours(hours);
        this.expirationTime = Date.from(expirationTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
