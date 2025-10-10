package com.example.Backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class Whitelist {
    private Jwt jwt;
    public Whitelist(Jwt jwt) {
        this.jwt = jwt;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/signin", "/signup","/access","/csrf-token")
                )

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/signin", "/signup", "/access","/csrf-token").permitAll() // whitelist
                        .anyRequest().authenticated() // everything else requires auth
                )
                .addFilterBefore(new Filter(jwt), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
