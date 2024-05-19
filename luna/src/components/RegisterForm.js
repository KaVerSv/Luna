import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import '../css/style.css';

import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";

import { isEmail } from "validator"


import AuthService from "../services/authService";

const required = value => {
    if (!value) {
        return (
            <div className="alert alert-danger" role="alert">
                This field is required!
            </div>
        );
    }
};

const email = value => {
    if (!isEmail(value)) {
        return (
            <div className="alert alert-danger" role="alert">
                This is not a valid email.
            </div>
        );
    }
};

const vpassword = value => {
    if (value.length < 6 || value.length > 20) {
        return (
            <div className="alert alert-danger" role="alert">
                The password must be between 6 and 20 characters.
            </div>
        );
    }
};

const passwordsMatch = (password, confirmedPassword) => {
    if (password !== confirmedPassword) {
        return (
            <div className="alert alert-danger" role="alert">
                Confirmed password must match the password.
            </div>
        );
    }
};

export default class RegisterForm extends Component {
    constructor(props) {
        super(props);
        this.handleRegister = this.handleRegister.bind(this);
        this.onChangeName = this.onChangeName.bind(this);
        this.onChangeSurname = this.onChangeSurname.bind(this);
        this.onChangeEmail = this.onChangeEmail.bind(this);
        this.onChangePassword = this.onChangePassword.bind(this);
        this.onChangeConfirmedPassword = this.onChangeConfirmedPassword.bind(this);
        this.onChangeUsername = this.onChangeUsername.bind(this);
        this.onChangeDate = this.onChangeDate.bind(this);

        this.state = {
            name: "",
            surname: "",
            username: "",
            email: "",
            password: "",
            confirmedPassword: "",
            successful: false,
            message: "",
            date: ""
        };
    }

    onChangeName(e) {
        this.setState({
            name: e.target.value
        });
    }

    onChangeSurname(e) {
        this.setState({
            surname: e.target.value
        });
    }

    onChangeEmail(e) {
        this.setState({
            email: e.target.value
        });
    }

    onChangePassword(e) {
        this.setState({
            password: e.target.value
        });
    }

    onChangeUsername(e) {
        this.setState({
            username: e.target.value
        });
    }

    onChangeConfirmedPassword(e) {
        this.setState({
            confirmedPassword: e.target.value
        });
    }

    onChangeDate(e) {
        this.setState({
            date: e.target.value
        });
    }

    handleRegister(e) {
        e.preventDefault();

        this.setState({
            message: "",
            successful: false
        });

        this.form.validateAll();

        if (this.password !== this.confirmedPassword) {
            this.setState({
                successful: false,
                message: "Confirmed password must match the password."
            });
            return;
        }

        if (this.checkBtn.context._errors.length === 0) {
            AuthService.register(
                this.state.name,
                this.state.surname,
                this.state.email,
                this.state.password,
                this.state.username
            ).then(
                response => {
                    this.setState({
                        message: response.data.message,
                        successful: true
                    });
                },
                error => {
                    const resMessage =
                        (error.response &&
                            error.response.data &&
                            error.response.data.message) ||
                        error.message ||
                        error.toString();

                    this.setState({
                        successful: false,
                        message: resMessage
                    });
                }
            );
        }
    }

    render() {
        return (
            <div className="login-container">
                <div className="logo">
                    <img className="resize" src="/img/logo_luna_cut.png" alt="Luna Logo" />
                </div>
                <div className="form-container">
                    <Form className="login" onSubmit={this.handleRegister} ref={c => {this.form = c;}}>
                        <p className="title">Register</p>

                        <label htmlFor="Username">Username:</label>
                        <input type="text" id="Username" name="username" value={this.state.username} onChange={this.onChangeUsername} required />

                        <label htmlFor="email">Email:</label>
                        <input type="email" id="email" name="email" value={this.state.email} onChange={this.onChangeEmail} required />

                        <label htmlFor="name">Name:</label>
                        <input type="name" id="name" name="name" value={this.state.name} onChange={this.onChangeName} required />

                        <label htmlFor="surname">Surname:</label>
                        <input type="surname" id="Surname" name="surname" value={this.state.surname} onChange={this.onChangeSurname} required />

                        <label htmlFor="password">Password:</label>
                        <input type="password" id="password" name="password" value={this.state.password} onChange={this.onChangePassword} required />

                        <label htmlFor="confirmedPassword">Repeat password:</label>
                        <input type="password" id="confirmedPassword" name="confirmedPassword" value={this.state.confirmedPassword} onChange={this.onChangeConfirmedPassword} required />

                        <label htmlFor="date">Date of birth:</label>
                        <input type="date" id="start" name="date" value={this.state.date} onChange={this.onChangeDate}  min="1930-01-01" max="2024-12-31" required />

                        {passwordsMatch(this.state.password, this.state.confirmedPassword)}

                        <div className="center-this-item">
                            <button type="submit">Register</button>
                        </div>
                        {this.state.message && (
                            <div className="form-group">
                                <div
                                    className={
                                        this.state.successful
                                            ? "alert alert-success"
                                            : "alert alert-danger"
                                    }
                                    role="alert"
                                >
                                    {this.state.message}
                                </div>
                            </div>
                        )}

                        <div className="links">
                            <a href="/login">I Already have an account</a>
                        </div>

                        <CheckButton style={{ display: "none" }} ref={c => { this.checkBtn = c; }} />
                    </Form>
                </div>
            </div>
        )
    }

}