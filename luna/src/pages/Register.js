import React from 'react'
import '../css/style.css';
import Background from "../components/Background";
import RegisterForm from "../components/RegisterForm";

const Register = () => {
    return (
        <Background background>
            <RegisterForm/>
        </Background>
    );
};

export default Register;