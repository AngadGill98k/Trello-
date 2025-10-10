package com.example.Backend.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class Filter extends OncePerRequestFilter {
    private Jwt jwt;
    public Filter(Jwt jwt) {
        this.jwt = jwt;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException,
                                    IOException {
        String token = null;

        // 1️⃣ Try to get JWT from Authorization header
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }else if (request.getCookies() != null) {
Log.log.info("(config.Filter) access tokne is expired ");
            for (Cookie cookie : request.getCookies()) {

                if ("token".equals(cookie.getName())) {
//Log.log.info("(config.filter),th refresh token value is {}",cookie.getValue());
                    token = cookie.getValue();
                }
            }
        }
        if(token != null && jwt.validateToken(token)) {
            try {
                String userid = jwt.extract_token(token);
                request.setAttribute("userid", userid);
//Log.log.info("userid (config.Filter):{}", userid);
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userid, null, List.of());
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }catch (Exception e) {
                Log.log.info(e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }
}
