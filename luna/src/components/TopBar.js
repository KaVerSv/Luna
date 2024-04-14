import React, { useContext } from 'react';
import "../css/style.css"

const TopBar = ({ isLoggedIn, isAdmin, username }) => {
    return (
        <div className="upper-border">
            <div className="top-buttons">
                <img className="resize2" src="/img/logo_luna_cut.png" alt="Logo"/>
                <a href="shop" className="button"> shop </a>
                <a href="library" className="button"> library </a>
                {isLoggedIn ? (
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
                        koszyk
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
