package com.acme.messenger.controller;


import com.acme.messenger.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Map;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        var token = authService.register(body.get("email"), body.get("password"), body.getOrDefault("displayName", body.get("email")));
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        var token = authService.login(body.get("email"), body.get("password"));
        return ResponseEntity.ok(Map.of("token", token));
    }
}