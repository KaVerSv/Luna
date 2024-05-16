import React, {useContext, useEffect, useState} from 'react';
import "../css/style.css"
import jwt_decode, {jwtDecode} from 'jwt-decode';
import axios from "axios";

const TopBar = () => {
    let isAdmin = false;
    const [username, setUsername] = useState('');
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchUsername = async () => {
            try {
                const response = await axios.get('http://localhost:8080/api/v1/user/username');
                setUsername(response.data);
                setLoading(false);
            } catch (error) {
                setError(error);
                setLoading(false);
            }
        };

        fetchUsername();
    }, []);


    return (
        <div className="upper-border">
            <div className="top-buttons">
                <img className="resize2" src="/img/logo_luna_cut.png" alt="Logo"/>
                <a href="shop" className="button"> shop </a>
                <a href="library" className="button"> library </a>
                {username ? (
                    <div className="user-menu">
                        <a href="#" className="button">{username}</a>
                        <div className="logout-menu">
                            <a href="logout">Wyloguj</a>
                        </div>
                    </div>
                ) : (
                    <a href="login" className="button">log in</a>
                )}
                {isAdmin && (
                    <a href="discounts" className="button"> discounts </a>
                )}
                <a href="cart" className="button">
                    <div>
                        cart
                        <img className="cart" src="/img/cart-shopping-white.svg" alt="SVG Button"/>
                    </div>
                </a>
                <div className="search-bar">
                    <form>
                        <input placeholder="search"/>
                    </form>
                </div>
            </div>
        </div>
    );
};

export default TopBar;
