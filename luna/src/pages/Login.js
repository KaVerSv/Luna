import React from 'react'
import LoginForm from '../components/LoginForm'

import '../css/style.css';
import Background from "../components/Background";

const Login = () => {
    return (
        <>
            <Background background>
                <LoginForm/>
            </Background>
        </>
    )
}

export default Login;
