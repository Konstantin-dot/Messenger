package com.acme.messenger.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;
import java.util.UUID;

@Entity
@Data
public class UserEntity {
    @Id
    private UUID id;
    private String email;
    private String passwordHash;
    private String displayName;
    private String role;
    private Instant createdAt;
    private Instant lastLoginAt;
}
