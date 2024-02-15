package com.gdsc.knu.service;

import com.gdsc.knu.entity.RefreshToken;
import com.gdsc.knu.repository.TokenRepository;
import com.gdsc.knu.repository.UserRepository;
import com.gdsc.knu.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtProvider jwtProvider;
    private final static long ACCESS_TOKEN_EXPIRE_TIME = 1000L * 60 * 60 * 24; // 24 hours
    private final static long REFRESH_TOKEN_EXPIRE_TIME = 1000L * 60 * 60 * 24 * 30; // 30 days


    /**
     * 사용자의 생성은 OAuth2UserService에서 이루어진다.
     * 그에 따라 read, update, delete만 구현한다.
     */

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public String generateAccessToken(Authentication authentication) {
        //make access token with authentication
        OAuth2User oAuth2User =  (OAuth2User)authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        return jwtProvider.generateToken(email, ACCESS_TOKEN_EXPIRE_TIME);
    }

    public String generateRefreshToken(Authentication authentication) {
        //make refresh token with authentication
        OAuth2User oAuth2User =  (OAuth2User)authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String token = jwtProvider.generateToken(email, REFRESH_TOKEN_EXPIRE_TIME);
        tokenRepository.save(new RefreshToken(token));
        return token;
    }
}
