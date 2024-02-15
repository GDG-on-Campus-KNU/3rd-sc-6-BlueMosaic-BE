package com.gdsc.knu.config;

import com.gdsc.knu.service.UserService;
import com.gdsc.knu.util.ConstVariables;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class GoogleAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final UserService userService;
    private final ConstVariables constVariables;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("[User Access] login success");
        // make access token & refresh token
        String accessToken = userService.generateAccessToken(authentication);
        String refreshToken = userService.generateRefreshToken(authentication);

        // set token to response header
        response.setHeader("access-token", accessToken);
        response.setHeader("refresh-token", refreshToken);

        // redirect to main page
        response.sendRedirect(constVariables.getFRONTEND_URL() + "/token-receiver");
    }
}
