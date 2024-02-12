package com.gdsc.knu.config;

import com.gdsc.knu.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.config.Customizer.withDefaults;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Slf4j
public class SpringConfig {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors(withDefaults())
//                .httpBasic().disable()
//                .formLogin().disable()

                .authorizeHttpRequests(request -> request
                        .requestMatchers("/health").permitAll()
                        .anyRequest().authenticated()
                ).oauth2Login(oauth2Login ->
                        oauth2Login
                                .defaultSuccessUrl("/qwer2", true)
                                .successHandler((request, response, authentication) -> {
                                    log.info("[User Access] login success");
                                    response.sendRedirect("/qwer");
                                })
                                .userInfoEndpoint(userInfo ->
                                        userInfo.userService(customOAuth2UserService)
                                )
                )
                .logout(withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder PasswordEncoder () {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
