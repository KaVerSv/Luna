import React from 'react';
import '../css/style.css';
import Background from "../components/Background";

const Login = () => {
    return (
        <Background background>
            <div className="login-container">
                <div className="logo">
                    <img className="resize" src="/img/logo_luna_cut.png" alt="Luna Logo" />
                </div>

                <div className="form-container">
                    <form className="login" action="login" method="POST">

                        <p className="title">Log in</p>

                        <label htmlFor="email">Login/email:</label>
                        <input type="text" id="email" name="email" required />

                        <label htmlFor="password">Hasło:</label>
                        <input type="password" id="password" name="password" required />

                        <div className="remember-me-container">
                            <input type="checkbox" id="remember" name="remember" />
                            <label htmlFor="remember">Zapamiętaj mnie</label>
                        </div>

                        <div className="center-this-item">
                            <button type="submit">Zaloguj się</button>
                        </div>

                        <div className="messages">
                            {/* Tutaj możesz umieścić warunkowe renderowanie wiadomości */}
                        </div>

                        <div className="links">
                            <a href="register">Utwórz konto</a>
                            <a href="forgot_password">Zapomniałem hasła</a>
                        </div>
                    </form>
                </div>
            </div>
        </Background>
    );
};

export default Login;
