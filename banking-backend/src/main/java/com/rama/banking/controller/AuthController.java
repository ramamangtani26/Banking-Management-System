package com.rama.banking.controller;

import com.rama.banking.dto.LoginRequest;
import com.rama.banking.dto.LoginResponse;
import com.rama.banking.exception.InvalidCredentialsException;
import com.rama.banking.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtService jwt;
    private final String adminUser;
    private final String adminPassword;

    public AuthController(JwtService jwt,
                          @Value("${app.admin.username}") String adminUser,
                          @Value("${app.admin.password}") String adminPassword) {
        this.jwt = jwt;
        this.adminUser = adminUser;
        this.adminPassword = adminPassword;
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest req) {
        boolean ok = same(adminUser, req.username()) & same(adminPassword, req.password());
        if (!ok) throw new InvalidCredentialsException();
        return new LoginResponse(jwt.generate(adminUser), "Bearer", jwt.ttlSeconds());
    }

    private static boolean same(String a, String b) {
        return MessageDigest.isEqual(a.getBytes(StandardCharsets.UTF_8), b.getBytes(StandardCharsets.UTF_8));
    }
}
