package com.acme.messenger.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "messages")
@Data
@NoArgsConstructor
public class MessageEntity {
    @Id
    private UUID id;
    private UUID channelId;
    private UUID authorId;
    @Column(columnDefinition = "text")
    private String content;
    private Boolean edited;
    private Instant createdAt;
    private Instant editedAt;
}