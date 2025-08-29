const API_BASE = "http://localhost:8080/api";

export async function loginUser(email, password, onLogin) {
    try {
        const response = await fetch(`${API_BASE}/auth/login`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email, password })
        });

        if (!response.ok) {
            throw new Error(`Login failed: ${response.status}`);
        }

        const data = await response.json();

        if (data.token) {
            localStorage.setItem("token", data.token);
            if (onLogin) onLogin(data);
        }

        return data;
    } catch (err) {
        console.error("Login error:", err);
        throw err;
    }
}

export async function registerUser(email, password, displayName) {
    try {
        const response = await fetch(`${API_BASE}/auth/register`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email, password, displayName })
        });

        if (!response.ok) {
            throw new Error(`Registration failed: ${response.status}`);
        }

        const data = await response.json();

        if (data.token) {
            localStorage.setItem("token", data.token);
        }

        return data;
    } catch (err) {
        console.error("Registration error:", err);
        throw err;
    }
}

export async function getChannels() {
    const token = localStorage.getItem("token");
    const response = await fetch(`${API_BASE}/channels`, {
        headers: { "Authorization": `Bearer ${token}` }
    });

    if (!response.ok) throw new Error(`Failed to fetch channels: ${response.status}`);
    return response.json();
}

export async function getActiveUsers() {
    const token = localStorage.getItem("token");
    const response = await fetch(`${API_BASE}/users/active`, {
        headers: { "Authorization": `Bearer ${token}` }
    });

    if (!response.ok) throw new Error(`Failed to fetch active users: ${response.status}`);
    return response.json();
}

export async function uploadFile(file, ownerId) {
    const token = localStorage.getItem("token");
    const formData = new FormData();
    formData.append("file", file);
    formData.append("ownerId", ownerId);

    const response = await fetch(`${API_BASE}/files/upload`, {
        method: "POST",
        headers: { "Authorization": `Bearer ${token}` },
        body: formData
    });

    if (!response.ok) throw new Error(`File upload failed: ${response.status}`);
    return response.json();
}
