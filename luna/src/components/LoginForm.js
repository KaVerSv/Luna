import React, { Component } from 'react'
import { Link } from 'react-router-dom';
import '../css/style.css';

import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";

import AuthService from "../services/auth.service";
import { withRouter } from '../routes/with-router';

class LoginForm extends Component {
    constructor(props) {
        super(props);
        this.handleLogin = this.handleLogin.bind(this);
        this.onChangeEmail = this.onChangeEmail.bind(this);
        this.onChangePassword = this.onChangePassword.bind(this);

        this.state = {
            email: "",
            password: "",
            loading: false,
            message: "",
            remember: false
        };
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

    handleLogin(e) {
        e.preventDefault();

        this.setState({
            message: "",
            loading: true
        });

        this.form.validateAll();

        if (this.checkBtn.context._errors.length === 0) {
            AuthService.login(this.state.email, this.state.password).then(
                () => {
                    this.props.router.navigate("/shop");
                    window.location.reload();
                },
                error => {
                    const resMessage =
                        (error.response &&
                            error.response.data &&
                            error.response.data.message) ||
                        error.message ||
                        error.toString();

                    this.setState({
                        loading: false,
                        message: resMessage
                    });
                }
            );
        } else {
            this.setState({
                loading: false
            });
        }
    }

    render() {
        return (
            <div className="login-container">
                <div className="logo">
                    <img className="resize" src="/img/logo_luna_cut.png" alt="Luna Logo" />
                </div>

                <div className="form-container">
                    <Form className="login" onSubmit={this.handleLogin} ref={c => {this.form = c;}}>
                        <p className="title">Log in</p>

                        <label htmlFor="email">Login/email:</label>
                        <input type="text" name="email" value={this.state.email} onChange={this.onChangeEmail} required />

                        <label htmlFor="password">Hasło:</label>
                        <input type="password" id="password" name="password" value={this.state.password} onChange={this.onChangePassword} required />

                        <div className="remember-me-container">
                            <input type="checkbox" id="remember" name="remember" value={this.state.remember} />
                            <label htmlFor="remember">Zapamiętaj mnie</label>
                        </div>

                        <div className="center-this-item">
                            <button type="submit">Zaloguj się</button>
                        </div>

                        <div className="messages">
                            {/* to do */}
                        </div>

                        <div className="links">
                            <a href="/register">Utwórz konto</a>
                            <a href="/forgot_password">Zapomniałem hasła</a>
                        </div>

                        <CheckButton
                            style={{ display: "none" }}
                            ref={c => {
                                this.checkBtn = c;
                            }}
                        />
                    </Form>
                </div>
            </div>
        )
    }

}

export default withRouter(LoginForm);