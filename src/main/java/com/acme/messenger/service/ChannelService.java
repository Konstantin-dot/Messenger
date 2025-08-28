package com.acme.messenger.service;


import com.acme.messenger.domain.ChannelEntity;
import com.acme.messenger.repository.ChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ChannelService {
    private final ChannelRepository repo;


    public ChannelEntity create(String name, String creatorEmail) {
        var c = new ChannelEntity();
        c.setId(UUID.randomUUID());
        c.setName(name);
        c.setType("PUBLIC");
        c.setCreatedBy(null);
        c.setCreatedAt(Instant.now());
        return repo.save(c);
    }

    public List<ChannelEntity> listAll() {
        return repo.findAll();
    }
}