import React from 'react';
import '../css/style.css';
import Background from "../components/Background";

const Register = () => {
    return (
        <Background background>
        <div className="login-container">
            <div className="logo">
                <img className="resize" src="/img/logo_luna_cut.png" alt="Luna Logo" />
            </div>
            <div className="form-container">
                <form className="login" action="register" method="post">
                    <p className="title">Register</p>

                    <label htmlFor="Username">Username:</label>
                    <input type="text" id="Username" name="username" required />

                    <label htmlFor="email">Email:</label>
                    <input type="email" id="email" name="email" required />

                    <label htmlFor="password">Passoword:</label>
                    <input type="password" id="password" name="password" required />

                    <label htmlFor="confirmedPassword">Repeat password:</label>
                    <input type="password" id="confirmedPassword" name="confirmedPassword" required />

                    <label htmlFor="date">Date of birth:</label>
                    <input type="date" id="start" name="date" value="2018-07-22" min="1930-01-01" max="2024-12-31" required />

                    <div className="center-this-item">
                        <button type="submit">Register</button>
                    </div>
                    <div className="messages">
                        {/* Tutaj możesz umieścić warunkowe renderowanie wiadomości */}
                    </div>

                    <div className="links">
                        <a href="login">I Already have an account</a>
                    </div>
                </form>
            </div>
        </div>
        </Background>
    );
};

export default Register;
