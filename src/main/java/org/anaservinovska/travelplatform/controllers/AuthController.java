package org.anaservinovska.travelplatform.controllers;

import jakarta.validation.Valid;
import org.anaservinovska.travelplatform.dto.RegisterRequest;
import org.anaservinovska.travelplatform.services.CustomUserDetailsService;
import org.anaservinovska.travelplatform.services.UserService;
import org.anaservinovska.travelplatform.utils.JwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody Map<String, String> request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.get("username"), request.get("password"))
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtUtil.generateToken(userDetails.getUsername(), "regular");

        javax.servlet.http.Cookie jwtCookie = new javax.servlet.http.Cookie("JWT", jwt);
        jwtCookie.setSecure(false); // Set to true in production for HTTPS
        jwtCookie.setPath("/"); // Make cookie accessible to all endpoints
        jwtCookie.setMaxAge((int) jwtUtil.getExpiration() / 1000); // Convert ms to seconds

        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", createCookieHeader(jwtCookie));

        return ResponseEntity.ok().headers(headers).build();
    }
    @PostMapping("/register")
    public String register(@RequestBody @Valid RegisterRequest registerRequest) {
        // Delegate registration logic to UserService
        return userService.registerUser(registerRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        javax.servlet.http.Cookie jwtCookie = new javax.servlet.http.Cookie("JWT", null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0); // Immediately expire the cookie

        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", createCookieHeader(jwtCookie));

        return ResponseEntity.ok().headers(headers).build();
    }

    private String createCookieHeader(javax.servlet.http.Cookie cookie) {
        return String.format("%s=%s; HttpOnly; Path=%s; Max-Age=%d; %s",
                cookie.getName(),
                cookie.getValue(),
                cookie.getPath(),
                cookie.getMaxAge(),
                cookie.getSecure() ? "Secure" : "");
    }

}
