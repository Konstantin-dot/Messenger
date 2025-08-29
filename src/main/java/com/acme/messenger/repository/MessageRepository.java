package com.acme.messenger.repository;

import com.acme.messenger.domain.MessageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MessageRepository extends JpaRepository<MessageEntity, UUID> {
    Page<MessageEntity> findByChannelIdOrderByCreatedAtDesc(UUID channelId, Pageable pageable);
}