package com.example.Backend.controllers;


import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Backend.config.Jwt;
import com.example.Backend.dto.Login_dto;
import com.example.Backend.dto.Response;
import com.example.Backend.dto.User_dto;
import com.example.Backend.models.User;
import com.example.Backend.services.Login_services;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
@RestController
@RequestMapping("/")
public class Login {
    private Login_services login;

    private Jwt jwt;
    Login(Login_services login,Jwt jwt){
        this.login = login;
        this.jwt = jwt;
    }

    @GetMapping("/csrf-token")
    public CsrfToken csrf(CsrfToken token, HttpServletResponse response) {
        // Create a cookie with the CSRF token value
        Cookie cookie = new Cookie("XSRF-TOKEN", token.getToken());
        cookie.setPath("/");                // available for all paths
        cookie.setHttpOnly(false);          // must be false so Angular can read it
        cookie.setSecure(true);             // true if using HTTPS
        cookie.setMaxAge(-1);               // session cookie

        response.addCookie(cookie);

        return token; // still return JSON if you want to debug
    }
    @PostMapping("/signin")
    public ResponseEntity<Response<User_dto>> signin(@RequestBody Login_dto user ){
        Response<User_dto> res=login.signin(user);
        String refreshToken=jwt.refresh_Token(res.getData().getId());
        res.setRefresh_token(refreshToken);
        ResponseCookie cookie=ResponseCookie.from("token",refreshToken)
                .httpOnly(true)
                .secure(false)
                .sameSite("Strict")
                .path("/")
                .maxAge(7*24*60*60)
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(res);
    }

    @PostMapping("/signup")
    public User signup(@RequestBody Login_dto user ){
        //Log.log.info("Signin attempt for user: {}", user.getMail());
        User res = login.signup(user);
        return res;
    }

    @GetMapping("/access")
    public Response access(@CookieValue(value = "token")String refresh_token){
        Response res = new Response();
        try {
//Log.log.info("refresh_token: {}", refresh_token);
            String userid = jwt.extract_token(refresh_token);
            String access_Token = jwt.access_Token(userid);
            res.setAccess_token(access_Token);
            res.setMsg(true);
            res.setMessage("token validated adn generated");
            return res;
        }catch(Exception e){
            res.setMsg(false);
            res.setMessage(e.getMessage());
            return res;
        }

    }

}
