package com.acme.messenger.controller;

import com.acme.messenger.domain.MessageEntity;
import com.acme.messenger.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @GetMapping("/api/channels/{id}/messages")
    public ResponseEntity<Page<MessageEntity>> history(@PathVariable UUID id, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "50") int size) {
        return ResponseEntity.ok(messageService.history(id, page, size));
    }

    @PostMapping("/api/channels/{id}/messages")
    public ResponseEntity<MessageEntity> create(@PathVariable UUID id, @RequestBody Map<String, String> body, Principal p) {
        var m = messageService.create(id, p.getName(), body.get("content"));
        return ResponseEntity.ok(m);
    }
}