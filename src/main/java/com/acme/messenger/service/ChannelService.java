package com.acme.messenger.service;

import com.acme.messenger.domain.Channel;
import com.acme.messenger.domain.UserEntity;
import com.acme.messenger.repository.ChannelRepository;
import com.acme.messenger.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChannelService {

    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;

    public Channel createChannel(String name, boolean isPrivate, List<UUID> memberIds) {
        Channel channel = new Channel();
        channel.setId(UUID.randomUUID());
        channel.setName(name);
        channel.setPrivate(isPrivate);
        channel.setCreatedAt(Instant.now());

        if (isPrivate && memberIds != null) {
            var members = new HashSet<UserEntity>();
            for (UUID id : memberIds) {
                userRepository.findById(id).ifPresent(members::add);
            }
            channel.setMembers(members);
        }

        return channelRepository.save(channel);
    }

    public Channel updateChannel(UUID channelId, String newName, boolean isPrivate, List<UUID> memberIds) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new IllegalArgumentException("Channel not found"));
        channel.setName(newName);
        channel.setPrivate(isPrivate);

        if (isPrivate && memberIds != null) {
            var members = new HashSet<UserEntity>();
            for (UUID id : memberIds) {
                userRepository.findById(id).ifPresent(members::add);
            }
            channel.setMembers(members);
        } else {
            channel.setMembers(new HashSet<>());
        }

        return channelRepository.save(channel);
    }

    public void deleteChannel(UUID channelId) {
        channelRepository.deleteById(channelId);
    }

    public List<Channel> listAllChannels() {
        return channelRepository.findAll();
    }

    public Channel getChannel(UUID channelId) {
        return channelRepository.findById(channelId)
                .orElseThrow(() -> new IllegalArgumentException("Channel not found"));
    }
}
