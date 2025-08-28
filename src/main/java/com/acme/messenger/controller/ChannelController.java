package com.acme.messenger.controller;


import com.acme.messenger.domain.ChannelEntity;
import com.acme.messenger.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/channels")
@RequiredArgsConstructor
public class ChannelController {
    private final ChannelService channelService;


    @GetMapping
    public ResponseEntity<List<ChannelEntity>> list(Principal p) {
        return ResponseEntity.ok(channelService.listAll());
    }

    @PostMapping
    public ResponseEntity<ChannelEntity> create(@RequestBody Map<String, String> body, Principal p) {
        return ResponseEntity.ok(channelService.create(body.get("name"), p.getName()));
    }
}