package com.adhish.FinSec.Controller;

import com.adhish.FinSec.CoreSecurity.service.JwtService;
import com.adhish.FinSec.CoreSecurity.service.MyUserDetailsService;
import com.adhish.FinSec.DTO.LoginRequest;
import com.adhish.FinSec.DTO.OtpVerificationRequest;
import com.adhish.FinSec.DTO.RefreshTokenRequest;
import com.adhish.FinSec.DTO.ResendOtpRequest;
import com.adhish.FinSec.Service.OtpService;
import com.adhish.FinSec.Service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private OtpService otpService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        UserDetails user = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        String jwtToken = jwtService.generateToken(user.getUsername());
        String refreshToken = refreshTokenService.createRefreshToken(loginRequest.getEmail()).getToken();


        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", jwtToken);
        tokens.put("refreshToken", refreshToken);
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody OtpVerificationRequest request){
        try{
            boolean verified = otpService.verifyOtp(request.getEmail(),request.getOtp());
            return ResponseEntity.ok("OTP verified! Your account is now active.");
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body("OTP verification failed: " + e.getMessage());
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Map<String, String>> refreshToken(@RequestBody RefreshTokenRequest request) {
        return refreshTokenService.findByToken(request.getRefreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(rt -> {
                    String token = jwtService.generateToken(rt.getUser().getEmail());
                    Map<String, String> response = new HashMap<>();
                    response.put("accessToken", token);
                    response.put("refreshToken", rt.getToken());
                    return ResponseEntity.ok(response);
                })
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        refreshTokenService.logout(email);
        return ResponseEntity.ok("Successfully logged out.");
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<String> resendOtp(@RequestBody ResendOtpRequest request)
    {
        String email = request.getEmail();
        try {
            String newOtp = otpService.resendOtp(email);
            // You might send it via email/SMS etc. here
            return ResponseEntity.ok("OTP resent successfully to: " + email);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Failed to resend OTP: " + e.getMessage());
        }
    }

}
