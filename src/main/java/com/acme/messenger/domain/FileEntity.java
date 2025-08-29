package com.acme.messenger.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "files")
@Data
@NoArgsConstructor
public class FileEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID channelId;

    @Column(nullable = false)
    private UUID uploaderId;

    @Column(nullable = false)
    private String originalName;

    @Column(nullable = false)
    private String mimeType;

    @Column(nullable = false)
    private long size;

    @Column(nullable = false)
    private String storagePath;

    private Instant createdAt;
}

