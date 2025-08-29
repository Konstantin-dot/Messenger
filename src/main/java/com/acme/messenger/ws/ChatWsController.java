package com.acme.messenger.ws;

import com.acme.messenger.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ChatWsController {
    private final MessageService messageService;
    private final SimpMessagingTemplate template;

    @MessageMapping("/channels.{channelId}.send")
    public void send(@DestinationVariable String channelId, @Payload Map<String, String> payload, java.security.Principal principal) {
        UUID cid = UUID.fromString(channelId);
        var m = messageService.create(cid, principal.getName(), payload.get("content"));
        template.convertAndSend("/topic/channels." + channelId, m);
    }
}