package com.acme.messenger.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
public class Channel {

    @Id
    private UUID id;

    private String name;

    private boolean isPrivate; // true — приватный, false — публичный

    private Instant createdAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "channel_members",
            joinColumns = @JoinColumn(name = "channel_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserEntity> members;
}
