package com.acme.messenger.controller;

import com.acme.messenger.domain.MessageEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificationController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendNotification(String userEmail, String content) {
        messagingTemplate.convertAndSendToUser(
                userEmail,
                "/queue/notifications",
                new MessageEntity(content)
        );
    }

    public class MessageEntity {

        private String content;

        public MessageEntity(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}



