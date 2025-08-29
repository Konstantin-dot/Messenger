// src/components/Notifications.js
import { useEffect } from "react";
import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";

const Notifications = ({ token }) => {
    useEffect(() => {
        const socket = new SockJS("http://localhost:8080/ws"); // URL WebSocket сервера
        const client = new Client({
            webSocketFactory: () => socket,
            connectHeaders: { Authorization: `Bearer ${token}` },
            debug: (str) => console.log(str),
            onConnect: () => {
                console.log("Connected to WebSocket");
                client.subscribe("/user/queue/notifications", (message) => {
                    const body = JSON.parse(message.body);
                    alert(`Новое сообщение: ${body.content}`);
                    const audio = new Audio("/notification.mp3"); // кастомный звук
                    audio.play();
                });
            },
            onStompError: (frame) => {
                console.error("STOMP Error: ", frame.headers.message);
            },
        });

        client.activate();
        return () => client.deactivate();
    }, [token]);

    return null;
};

export default Notifications;
