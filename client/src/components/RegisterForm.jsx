import { useState } from "react";
import { registerUser } from "../api";

export default function RegisterForm() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [displayName, setDisplayName] = useState("");
    const [message, setMessage] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const result = await registerUser(email, password, displayName);
            setMessage("Регистрация успешна! Токен: " + result.token);
        } catch (err) {
            setMessage("Ошибка регистрации");
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <input placeholder="Email" value={email} onChange={e => setEmail(e.target.value)} />
            <input placeholder="Пароль" type="password" value={password} onChange={e => setPassword(e.target.value)} />
            <input placeholder="Имя" value={displayName} onChange={e => setDisplayName(e.target.value)} />
            <button type="submit">Регистрация</button>
            <div>{message}</div>
        </form>
    );
}
