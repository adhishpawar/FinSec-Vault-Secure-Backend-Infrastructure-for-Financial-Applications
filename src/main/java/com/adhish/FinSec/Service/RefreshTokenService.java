package com.adhish.FinSec.Service;

import com.adhish.FinSec.Entity.RefreshToken;
import com.adhish.FinSec.Entity.User;
import com.adhish.FinSec.Repo.RefreshTokenRepository;
import com.adhish.FinSec.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final Long refreshTokenDurationMs = 1000L * 60 * 10;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;


    @Autowired
    private UserRepository userRepository;


    @Transactional
    public RefreshToken createRefreshToken(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not Found"));


        refreshTokenRepository.deleteByUser(user);
        refreshTokenRepository.flush();

        RefreshToken token  = new RefreshToken();
        token.setUser(user);
        token.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        token.setToken(UUID.randomUUID().toString());
        return refreshTokenRepository.save(token);
    }

    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpiryDate().isBefore(Instant.now())){
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token expired. Please login again.");
        }
        return token;
    }

    public void deleteByUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        refreshTokenRepository.deleteByUser(user);
    }

    @Transactional
    public void logout(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        refreshTokenRepository.deleteByUser(user);
    }

}
