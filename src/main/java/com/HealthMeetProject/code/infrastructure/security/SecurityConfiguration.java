package com.HealthMeetProject.code.infrastructure.security;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            PasswordEncoder passwordEncoder,
            UserDetailsService userDetailsService
    ) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            HttpSecurity http,
            AuthenticationProvider authenticationProvider
    ) throws Exception {
        return http
                .getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(authenticationProvider)
                .build();
    }

    //    @Bean
//    @ConditionalOnProperty(value = "spring.security.enabled", havingValue = "true", matchIfMissing =
//            true)
//    SecurityFilterChain securityEnabled(HttpSecurity http) throws Exception {
//        return http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(requests -> requests
//                        .requestMatchers("/login", "/error", "/images/no_way.png", "/", "/doctor_register", "/patient_register", "/about",
//                                "/doctor_register/add", "/patient_register/add").permitAll()
//                        .requestMatchers("/patient/**", "terms/**").hasAnyAuthority("PATIENT")
//                        .requestMatchers("/doctor/**").hasAnyAuthority("DOCTOR")
//                        .requestMatchers("/api/**", "/swagger-ui/**").hasAnyAuthority("REST_API")
//                        .anyRequest().authenticated()
//                )
//                .formLogin(loginConfigurer -> loginConfigurer.passwordParameter("password")
//                        .usernameParameter("email")
//                        .loginPage("/login")
//                        .defaultSuccessUrl("/home")
//                        .permitAll())
//                .logout(logout -> logout
//                        .logoutSuccessUrl("/")
//                        .invalidateHttpSession(true)
//                        .deleteCookies("JSESSIONID")
//                        .permitAll()
//
//                )
//
//                .build();
//    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/login", "/error", "/images/no_way.png", "/", "/doctor_register", "/patient_register", "/about",
                                "/doctor_register/add", "/patient_register/add").permitAll()
                        .requestMatchers("/patient/**", "terms/**").hasAnyAuthority("PATIENT")
                        .requestMatchers("/doctor/**").hasAnyAuthority("DOCTOR")
                        .requestMatchers("/api/**", "/swagger-ui/**").hasAnyAuthority("REST_API")
                        .anyRequest().authenticated()
                ).formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );
        return http.build();
    }


    @Bean
    @ConditionalOnProperty(value = "spring.security.enabled", havingValue = "false")
    SecurityFilterChain securityDisabled(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests
                        .anyRequest()
                        .permitAll()
                )
                .build();
    }
}