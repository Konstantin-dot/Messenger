package com.acme.messenger.controller;

import com.acme.messenger.domain.Channel;
import com.acme.messenger.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/channels")
@RequiredArgsConstructor
public class ChannelController {

    private final ChannelService channelService;

    @PostMapping
    public Channel createChannel(@RequestBody CreateChannelRequest request) {
        return channelService.createChannel(request.getName(), request.isPrivate(), request.getMemberIds());
    }

    @PutMapping("/{id}")
    public Channel updateChannel(@PathVariable UUID id, @RequestBody CreateChannelRequest request) {
        return channelService.updateChannel(id, request.getName(), request.isPrivate(), request.getMemberIds());
    }

    @DeleteMapping("/{id}")
    public void deleteChannel(@PathVariable UUID id) {
        channelService.deleteChannel(id);
    }

    @GetMapping
    public List<Channel> listChannels() {
        return channelService.listAllChannels();
    }

    @GetMapping("/{id}")
    public Channel getChannel(@PathVariable UUID id) {
        return channelService.getChannel(id);
    }

    public static class CreateChannelRequest {
        private String name;
        private boolean isPrivate;
        private List<UUID> memberIds;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isPrivate() {
            return isPrivate;
        }

        public void setPrivate(boolean aPrivate) {
            isPrivate = aPrivate;
        }

        public List<UUID> getMemberIds() {
            return memberIds;
        }

        public void setMemberIds(List<UUID> memberIds) {
            this.memberIds = memberIds;
        }
    }
}
