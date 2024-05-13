import React, { useState } from 'react';
import { useHistory } from 'react-router-dom';
import '../css/style.css';
import Background from "../components/Background";
import axios from 'axios';

const Login = () => {
    const [errorMessage, setErrorMessage] = useState('');

    const [formData, setFormData] = useState({
        email: '',
        password: '',
        remember: false
    });

    const handleChange = (e) => {
        const value = e.target.type === 'checkbox' ? e.target.checked : e.target.value;
        setFormData({ ...formData, [e.target.name]: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const response = await axios.post('http://localhost:8080/api/v1/auth/authenticate', formData);
            console.log(response.data);
            window.location.href = 'shop';
        } catch (error) {
            console.error('Authentication failed:', error);
            setErrorMessage('Authentication failed. Please check your credentials and try again.');
        }
    };





    return (
        <Background background>
            <div className="login-container">
                <div className="logo">
                    <img className="resize" src="/img/logo_luna_cut.png" alt="Luna Logo" />
                </div>

                <div className="form-container">
                    <form className="login" onSubmit={handleSubmit}>
                        <p className="title">Log in</p>

                        <label htmlFor="email">Login/email:</label>
                        <input type="text" id="email" name="email" value={formData.email} onChange={handleChange} required />

                        <label htmlFor="password">Hasło:</label>
                        <input type="password" id="password" name="password" value={formData.password} onChange={handleChange} required />

                        <div className="remember-me-container">
                            <input type="checkbox" id="remember" name="remember" checked={formData.remember} onChange={handleChange} />
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
