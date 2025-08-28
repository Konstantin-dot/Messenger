//package com.acme.messenger.service;
//
//
//import com.acme.messenger.domain.UserEntity;
//import com.acme.messenger.repository.UserRepository;
//import com.acme.messenger.security.JwtUtil;
//import lombok.RequiredArgsConstructor;
//import org.apache.commons.codec.digest.DigestUtils;
//import org.springframework.stereotype.Service;
//
//
//import java.time.Instant;
//import java.util.UUID;
//
//
//@Service
//@RequiredArgsConstructor
//public class AuthService {
//    private final UserRepository userRepository;
//    private final JwtUtil jwtUtil;
//
//    public String register(String email, String password, String displayName) {
//        if (userRepository.findByEmail(email).isPresent()) throw new IllegalArgumentException("Email already");
//        var u = new UserEntity();
//        u.setId(UUID.randomUUID());
//        u.setEmail(email);
//        u.setPasswordHash(DigestUtils.sha256Hex(password)); // simple hashing for example; use BCrypt in prod
//        u.setDisplayName(displayName);
//        u.setRole("ROLE_USER");
//        u.setCreatedAt(Instant.now());
//        userRepository.save(u);
//        return jwtUtil.generateToken(u.getEmail(), 1000L * 60 * 60 * 24); // 1 day for example
//    }
//
//
//    public String login(String email, String password) {
//        var u = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Bad credentials"));
//        if (!u.getPasswordHash().equals(DigestUtils.sha256Hex(password))) throw new IllegalArgumentException("Bad credentials");
//        u.setLastLoginAt(Instant.now());
//        userRepository.save(u);
//        return jwtUtil.generateToken(u.getEmail(), 1000L * 60 * 60 * 24);
//    }
//}

package com.acme.messenger.service;

import com.acme.messenger.domain.UserEntity;
import com.acme.messenger.repository.UserRepository;
import com.acme.messenger.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public String register(String email, String password, String displayName) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        var user = new UserEntity();
        user.setId(UUID.randomUUID());
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setDisplayName(displayName);
        user.setRole("ROLE_USER");
        user.setCreatedAt(Instant.now());
        userRepository.save(user);

        return jwtUtil.generateToken(user.getEmail(), 1000L * 60 * 60 * 24); // 1 day
    }

    public String login(String email, String password) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        user.setLastLoginAt(Instant.now());
        userRepository.save(user);

        return jwtUtil.generateToken(user.getEmail(), 1000L * 60 * 60 * 24);
    }
}
