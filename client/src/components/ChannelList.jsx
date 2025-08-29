export default function ChannelList({ channels }) {
    return (
        <ul>
            {channels.map((ch) => (
                <li key={ch.id}>{ch.name}</li>
            ))}
        </ul>
    );
}
