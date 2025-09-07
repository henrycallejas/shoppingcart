package com.shoppingcart.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.beans.factory.annotation.Autowired;

import com.shoppingcart.app.security.filters.JwtAuthenticationFilter;
import com.shoppingcart.app.security.filters.JwtAuthorizationFilter;
import com.shoppingcart.app.security.jwt.JWTUtils;

@Configuration
public class SecurityConfig {
    @Autowired
    JWTUtils jwtUtils;

    @Autowired
    UserDetailImpl userDetailImpl;

    @Bean
    JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtUtils, userDetailImpl);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager)
            throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtils);
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
        jwtAuthenticationFilter.setFilterProcessesUrl("/login");

        return httpSecurity
                .csrf(config -> config.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/login").permitAll();
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .addFilter(jwtAuthenticationFilter)
                .addFilterAfter(jwtAuthorizationFilter(), JwtAuthenticationFilter.class)
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder)
            throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailImpl)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }
}
