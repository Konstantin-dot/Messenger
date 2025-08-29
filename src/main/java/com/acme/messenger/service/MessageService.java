package com.acme.messenger.service;

import com.acme.messenger.domain.MessageEntity;
import com.acme.messenger.domain.UserEntity;
import com.acme.messenger.repository.MessageRepository;
import com.acme.messenger.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepo;
    private final UserRepository userRepo;

    public MessageEntity create(UUID channelId, String username, String content) {
        UserEntity user = userRepo.findByEmail(username).orElseThrow();
        var m = new MessageEntity();
        m.setId(UUID.randomUUID());
        m.setChannelId(channelId);
        m.setAuthorId(user.getId());
        m.setContent(content);
        m.setEdited(false);
        m.setCreatedAt(Instant.now());
        return messageRepo.save(m);
    }

    public Page<MessageEntity> history(UUID channelId, int page, int size) {
        return messageRepo.findByChannelIdOrderByCreatedAtDesc(channelId, PageRequest.of(page, size));
    }
}