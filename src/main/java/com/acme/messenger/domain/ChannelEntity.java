package com.acme.messenger.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "channels")
@Data
@NoArgsConstructor
public class ChannelEntity {
    @Id
    private UUID id;
    private String name;
    private String type;
    private UUID createdBy;
    private Instant createdAt;
}