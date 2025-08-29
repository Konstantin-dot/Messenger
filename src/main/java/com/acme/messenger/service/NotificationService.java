package com.acme.messenger.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    public void sendNotification(String userEmail, String message) {
        messagingTemplate.convertAndSend("/topic/notifications/" + userEmail, message);
    }
}
