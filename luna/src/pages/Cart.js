import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../css/style.css';
import '../css/cart.css';
import Background from "../components/Background";
import authHeader from '../services/authHeader';
import TopBar from "../components/TopBar";

const Cart = () => {
    const [books, setBooks] = useState([]);
    const [totalPrice, setTotalPrice] = useState(0);
    const [loading, setLoading] = useState(true);

    const calculateTotalPrice = () => {
        let total = 0.0;
        books.forEach(book => {
            total += parseFloat(book.price);
        });
        setTotalPrice(total);
    };

    useEffect(() => {
        fetchCartData();

    }, []);

    const fetchCartData = async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/v1/cart', { headers: authHeader() });
            setBooks(response.data);
            calculateTotalPrice();
            setLoading(false);
        } catch (error) {
            console.error('Error fetching cart data:', error);
        }
    };

    if (loading) {
        return <p>Loading...</p>;
    }

    const handleDelete = async (bookId) => {
        try {
            await axios.delete('http://localhost:8080/api/v1/cart/' + bookId,{ headers: authHeader() });
            await fetchCartData();
        } catch (error) {
            console.error('Error deleting book from cart:', error);
        }
    };

    const handleBuy = () => {
        // Obsługa zakupu
    };

    return (
        <>
            <Background background>
                <TopBar />
                <div className="context-container">
                    <div className="background">
                        <h1>YOUR SHOPPING CART</h1>
                        <div className="cart-container">
                            {books.length > 0 ? (
                                books.map(book => (
                                    <div className="item" key={book.id}>
                                        <div className="cart-item">
                                            <a href={"http://localhost:3000/BookPage?id=" + book.id}><h3>{book.title}</h3></a>
                                            <div className="end">
                                                <p>Price: {book.price} zł</p>
                                            </div>
                                        </div>
                                        <button onClick={() => handleDelete(book.id)} className="delete-button">remove</button>
                                    </div>
                                ))
                            ) : (
                                <p>Your cart is empty</p>
                            )}
                            {books.length > 0 && (
                                <div className="total">
                                    <p>Total: {totalPrice.toFixed(2)} zł</p>
                                    <div className="end">
                                        <button onClick={handleBuy} className="add-to-cart-button">Buy</button>
                                    </div>
                                </div>
                            )}
                        </div>
                    </div>
                </div>
            </Background>
        </>
    );
};

export default Cart;
