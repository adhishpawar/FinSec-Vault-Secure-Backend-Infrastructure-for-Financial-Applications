package com.adhish.FinSec.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
public class OTP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String otpCode;

    private LocalDateTime expiryTime;

    private Boolean verified = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

