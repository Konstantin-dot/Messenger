import { useState } from "react";
import RegisterForm from "./components/RegisterForm";
import LoginForm from "./components/LoginForm";
import MainScreen from "./components/MainScreen";
import Notifications from "./components/Notifications";

function App() {
    const [user, setUser] = useState(null);

    const handleLogin = (userData) => {
        setUser({
            displayName: userData.displayName || userData.email,
            email: userData.email,
            token: userData.token
        });
        localStorage.setItem("token", userData.token);
    };

    return (
        <div>
            <h1>Корпоративный мессенджер</h1>
            {user ? (
                <>
                    <Notifications token={user.token} />
                    <MainScreen user={user} />
                </>
            ) : (
                <>
                    <RegisterForm />
                    <LoginForm onLogin={handleLogin} />
                </>
            )}
        </div>
    );
}

export default App;
