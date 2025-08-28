import { useState } from "react";
import { loginUser } from "../api/api";

export default function LoginForm() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [message, setMessage] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const result = await loginUser(email, password);
            setMessage("Вход успешен! Токен: " + result.token);
        } catch (err) {
            setMessage("Неверный логин или пароль");
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <input placeholder="Email" value={email} onChange={e => setEmail(e.target.value)} />
            <input placeholder="Пароль" type="password" value={password} onChange={e => setPassword(e.target.value)} />
            <button type="submit">Войти</button>
            <div>{message}</div>
        </form>
    );
}
