package com.adhish.FinSec.Service;


import com.adhish.FinSec.Entity.OTP;
import com.adhish.FinSec.Entity.User;
import com.adhish.FinSec.Enum.Status;
import com.adhish.FinSec.Repo.OtpRepository;
import com.adhish.FinSec.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class OtpService {

   @Autowired
   private OtpRepository otpRepository;

   @Autowired
   private UserRepository userRepository;

    public String generateOtp(User user) {
        String otpCode = String.valueOf(new Random().nextInt(900000) + 100000); // 6-digit OTP
        OTP otp = new OTP();
        otp.setOtpCode(otpCode);
        otp.setUser(user);
        otp.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        otp.setVerified(false);

        otpRepository.save(otp);

        // Simulate sending OTP via email
        System.out.println("OTP for " + user.getEmail() + ": " + otpCode);

        return otpCode;
    }

    public boolean verifyOtp(String email, String otpInput){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        OTP latestOtp = otpRepository.findTopByUserOrderByExpiryTimeDesc(user)
                .orElseThrow(() -> new RuntimeException("OTP not found"));

        if(latestOtp.getVerified())
            throw new RuntimeException("OTP already used");

        if (latestOtp.getExpiryTime().isBefore(LocalDateTime.now()))
            throw new RuntimeException("OTP expired");

        if (!latestOtp.getOtpCode().equals(otpInput))
            throw new RuntimeException("Invalid OTP");

        latestOtp.setVerified(true);
        otpRepository.save(latestOtp);

        // Activate user
        user.setStatus(Status.ACTIVE);
        userRepository.save(user);

        return true;
    }

    public String resendOtp(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return generateOtp(user);
    }

}
