import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../css/style.css';
import '../css/cart.css';
import Background from "../components/Background";
import authHeader from '../services/auth-header';
import TopBar from "../components/TopBar";

const Cart = () => {
    const [books, setBooks] = useState([]);
    const [totalPrice, setTotalPrice] = useState(0);

    useEffect(() => {
        fetchCartData();
    }, []);

    const fetchCartData = async () => {
        try {
            const response = await axios.get('/api/v1/user/cart?id=8', { headers: authHeader() }); // Załóżmy, że API endpoint to '/api/cart'
            const cartData = response.data;
            setBooks(cartData.books);
            calculateTotalPrice(cartData.books);
        } catch (error) {
            console.error('Error fetching cart data:', error);
        }
    };

    const calculateTotalPrice = (cartBooks) => {
        let total = 0;
        cartBooks.forEach(book => {
            total += parseFloat(book.price);
        });
        setTotalPrice(total);
    };

    const handleDelete = async (bookId) => {
        try {
            await axios.delete(`/api/cart/${bookId}`); // usuwanie z koszyka  przez DELETE request na '/api/cart/:bookId'
            fetchCartData(); //refresh after del
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
                                            <a href={`/book_page?id=${book.id}`}><h3>{book.title}</h3></a>
                                            <div className="end">
                                                <p>Price: {book.price} zł</p>
                                            </div>
                                        </div>
                                        <button onClick={() => handleDelete(book.id)}>Delete</button>
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
