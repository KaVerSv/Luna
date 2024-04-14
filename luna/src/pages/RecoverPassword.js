import React from 'react';
import '../css/style.css';
import Background from "../components/Background";

const RecoverPassword = () => {
    return (
        <Background background>
        <div className="login-container">
            <div className="logo">
                <img className="resize" src="/img/logo_luna_cut.png" alt="Luna Logo" />
            </div>

            <div className="form-container">
                <form className="login" action="login" method="POST">
                    <p className="title">Reset password</p>

                    <label htmlFor="email">Login/email:</label>
                    <input type="text" id="email" name="email" required />

                    <div className="center-this-item">
                        <button type="submit">Zresetuj hasło</button>
                    </div>

                    <div className="links">
                        <a href="register">Utwórz konto</a>
                        <a href="login">zaloguj się</a>
                    </div>
                </form>
            </div>
        </div>
        </Background>
    );
};

export default RecoverPassword;
