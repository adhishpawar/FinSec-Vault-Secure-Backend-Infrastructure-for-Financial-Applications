package com.adhish.FinSec.Repo;

import com.adhish.FinSec.Entity.OTP;
import com.adhish.FinSec.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<OTP,Long> {
    Optional<OTP> findTopByUserOrderByExpiryTimeDesc(User user);
}
