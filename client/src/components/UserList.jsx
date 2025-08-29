export default function UserList({ users }) {
    return (
        <ul>
            {users.map((u) => (
                <li key={u.id}>{u.displayName}</li>
            ))}
        </ul>
    );
}
