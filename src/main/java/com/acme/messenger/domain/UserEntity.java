package com.acme.messenger.domain;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.Instant;
import java.util.UUID;


@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class UserEntity {
    @Id
    private UUID id;


    @Column(unique = true, nullable = false)
    private String email;


    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String displayName;


    @Column(nullable = false)
    private String role;


    private Instant createdAt;
    private Instant lastLoginAt;
}