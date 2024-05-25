import React, {useContext, useEffect, useState} from 'react';
import "../css/style.css"
import axios from "axios";
import authService from "../services/authService";
import authHeader from "../services/authHeader";

const TopBar = () => {
    const [admin, setAdmin] = useState(false);
    const [username, setUsername] = useState(null);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);
    const config = {
        headers: authHeader()
    };

    useEffect(() => {
        const fetchUsername = async () => {
            try {
                const response = await axios.get('http://localhost:8080/api/v1/user/username',config);
                const response2 = await axios.get('http://localhost:8080/api/v1/user/admin',config);
                setUsername(response.data);
                setAdmin(response2.data);
                setLoading(false);
            } catch (error) {
                setError(error);
                setLoading(false);
            }
        };

        fetchUsername();
    }, []);

    const handleLogout = async () => {
        try {
            await authService.logout();
            setUsername(null);
            window.location.reload();
        } catch (error) {
            console.error("Error:", error);
        }
    };

    return (
        <div className="upper-border">
            <div className="top-buttons">
                <img className="resize2" src="/img/logo_luna_cut.png" alt="Logo"/>
                <a href="shop" className="button"> Shop </a>
                <a href="library" className="button"> Library </a>
                {username ? (
                    <div className="user-menu">
                        <a href="#" className="button">{username}</a>
                        <div className="logout-menu">
                            <a href="#" onClick={handleLogout}>Logout</a>
                        </div>
                    </div>
                ) : (
                    <a href="login" className="button">Log in</a>
                )}
                {admin && (
                    <a href="Discounts" className="button"> Discounts </a>
                )}
                <a href="cart" className="button">
                    <div>
                        Cart
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
