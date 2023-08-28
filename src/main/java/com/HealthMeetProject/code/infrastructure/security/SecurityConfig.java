//package com.HealthMeetProject.code.infrastructure.security;
//
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationManager authManager(
//            HttpSecurity http,
//            PasswordEncoder passwordEncoder,
//            UserDetailsService userDetailService
//    )
//            throws Exception {
//        return http.getSharedObject(AuthenticationManagerBuilder.class)
//                .userDetailsService(userDetailService)
//                .passwordEncoder(passwordEncoder)
//                .and()
//                .build();
//    }
//
//    @Bean
//    @ConditionalOnProperty(value = "spring.security.enabled", havingValue = "true", matchIfMissing = true)
//    SecurityFilterChain securityEnabled(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests()
//                .requestMatchers("/register", "/login", "/error", "/images/oh_no.png").permitAll()
//                .and()
//                .formLogin()
//                .permitAll()
//                .and()
//                .logout()
//                .logoutSuccessUrl("/login")
//                .invalidateHttpSession(true)
//                .deleteCookies("JSESSIONID")
//                .permitAll();
//
//        return http.build();
//    }
//
//    @Bean
//    @ConditionalOnProperty(value = "spring.security.enabled", havingValue = "false")
//    SecurityFilterChain securityDisabled(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests()
//                .anyRequest()
//                .permitAll();
//
//        return http.build();
//    }
//
//}
