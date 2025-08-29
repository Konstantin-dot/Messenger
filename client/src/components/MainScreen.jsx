import { useEffect, useState } from "react";
import ChannelList from "./ChannelList";
import UserList from "./UserList";
import ChatWindow from "./ChatWindow";
import { getChannels, getActiveUsers } from "../api";

export default function MainScreen({ user }) {
    const [channels, setChannels] = useState([]);
    const [activeUsers, setActiveUsers] = useState([]);

    useEffect(() => {
        getChannels().then(setChannels);
        getActiveUsers().then(setActiveUsers);
    }, []);

    return (
        <div style={{ display: "flex", height: "80vh" }}>
            <div style={{ width: "20%", borderRight: "1px solid #ccc" }}>
                <ChannelList channels={channels} />
            </div>
            <div style={{ width: "20%", borderRight: "1px solid #ccc" }}>
                <UserList users={activeUsers} />
            </div>
            <div style={{ flex: 1 }}>
                <ChatWindow user={user} />
            </div>
        </div>
    );
}
