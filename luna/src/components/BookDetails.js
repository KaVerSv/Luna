import React, { useEffect, useState } from 'react';
import axios from 'axios';
import "../css/style.css";
import "../css/book_page.css";
import authHeader from '../services/authHeader';
import AuthService from '../services/authService';
import {useNavigate} from "react-router-dom";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const BookDetails = ({ book }) => {
    const [positivePercentage, setPositivePercentage] = useState(0);
    const [totalVotes, setTotalVotes] = useState(0);
    const navigate = useNavigate();

    useEffect(() => {
        const calculatePercentage = () => {
            const likes = book.likes;
            const dislikes = book.dislikes;
            const total = likes + dislikes;
            const percentage = total > 0 ? Math.round((likes / total) * 100) : 0;
            setPositivePercentage(percentage);
            setTotalVotes(total);
        };

        calculatePercentage();
    }, [book.likes, book.dislikes]);

    const getCategory = (percentage) => {
        if (percentage >= 90) {
            return 'Overwhelmingly Positive';
        } else if (percentage >= 80) {
            return 'Very Positive';
        } else if (percentage >= 70) {
            return 'Positive';
        } else if (percentage >= 60) {
            return 'Mostly Positive';
        } else if (percentage >= 40) {
            return 'Mixed';
        } else if (percentage >= 30) {
            return 'Mostly Negative';
        } else {
            return 'Negative';
        }
    };

    const handleAddToCart = async (e) => {
        e.preventDefault();

        const user = AuthService.getCurrentUser();

        if (user === null) {
            navigate('/login');
        }

        try {
            const response = await axios.post(
                `http://localhost:8080/api/v1/cart/${book.id}`,
                {},
                { headers: authHeader() }
            );
            console.log('Book added to cart:', response.data);
            toast.success('Book added to cart successfully!');
        } catch (error) {
            if (error.response && error.response.status === 409) {
                toast.error('Item already in cart');
            } else {
                toast.error('Failed to add book to cart.');
            }
            console.error('Error adding book to cart:', error);
        }
    };

    return (
        <div className="context-container">
            <ToastContainer />
            <div className="background">
                <div className="book-container">
                    <h1 id="book-title">{book.title}</h1>

                    <div className="about">
                        <img className="img-resize image-container" src={book.image} alt="Book Cover" />
                        <div className="book-info">
                            <h2>{book.title}</h2>
                            <h2>{book.author}</h2>
                            <p>{book.published}</p>

                            <p>{book.description}</p>
                        </div>
                    </div>

                    <div className="buy-container">
                        <h2>Buy {book.title}</h2>

                        <div className="cart-container">
                            <p>{book.price} zł</p>
                            <form onSubmit={handleAddToCart}>
                                <input type="hidden" name="bookId" value={book.id}/>
                                <input type="submit" value="Add to Cart" className="add-to-cart-button"/>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default BookDetails;