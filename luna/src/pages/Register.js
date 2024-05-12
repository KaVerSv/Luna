import React, {useState} from 'react';
import '../css/style.css';
import Background from "../components/Background";
import axios from 'axios';

const Register = () => {
    const [formData, setFormData] = useState({
        username: '',
        email: '',
        name:'',
        surname:'',
        password: '',
        confirmedPassword: '',
        date: ''
    });

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (formData.password !== formData.confirmedPassword) {
            console.error('Passwords do not match');
            return;
        }

        try {
            const response = await axios.post('http://localhost:8080/api/v1/auth/register', formData);
            console.log(response.data); // Reakcja z serwera po udanej rejestracji
        } catch (error) {
            console.error('Registration failed:', error);
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
                        <p className="title">Register</p>

                        <label htmlFor="Username">Username:</label>
                        <input type="text" id="Username" name="username" value={formData.username} onChange={handleChange} required />

                        <label htmlFor="email">Email:</label>
                        <input type="email" id="email" name="email" value={formData.email} onChange={handleChange} required />

                        <label htmlFor="email">Name:</label>
                        <input type="name" id="name" name="name" value={formData.name} onChange={handleChange} required />

                        <label htmlFor="email">Surname:</label>
                        <input type="surname" id="Surname" name="surname" value={formData.surname} onChange={handleChange} required />

                        <label htmlFor="password">Password:</label>
                        <input type="password" id="password" name="password" value={formData.password} onChange={handleChange} required />

                        <label htmlFor="confirmedPassword">Repeat password:</label>
                        <input type="password" id="confirmedPassword" name="confirmedPassword" value={formData.confirmedPassword} onChange={handleChange} required />

                        <label htmlFor="date">Date of birth:</label>
                        <input type="date" id="start" name="date" value={formData.date} onChange={handleChange} min="1930-01-01" max="2024-12-31" required />

                        <div className="center-this-item">
                            <button type="submit">Register</button>
                        </div>
                        <div className="messages">

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