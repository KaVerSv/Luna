import React, { useEffect, useState } from 'react';
import axios from 'axios';
import "../css/style.css";
import "../css/book_page.css";
import authHeader from '../services/authHeader';
import {useNavigate} from "react-router-dom";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const BookDetails = ({ book, username }) => {
    const [positivePercentage, setPositivePercentage] = useState(0);
    const [totalVotes, setTotalVotes] = useState(0);
    const navigate = useNavigate();
    const [onWishList, setOnWishList] = useState(false);

    const fetchWishList = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/api/v1/user/wishList/${book.id}`,{ headers: authHeader() });
            setOnWishList(response.data);

        } catch (error) {
            console.error(error);
        }
    };

    useEffect(() => {
        const calculatePercentage = () => {
            const likes = book.likes;
            const dislikes = book.dislikes;
            const total = likes + dislikes;
            const percentage = total > 0 ? Math.round((likes / total) * 100) : 0;
            setPositivePercentage(percentage);
            setTotalVotes(total);
        };

        fetchWishList();
        calculatePercentage();
    }, [book.likes, book.dislikes, book.id]);

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

    const checkForLogin = () => {
        const fetchUsername = async () => {
            try {
                const response = await axios.get('http://localhost:8080/api/v1/user/username',{ headers: authHeader() });
            } catch (error) {
                navigate("/Login");
            }
        };

        if(localStorage.getItem("user") === null) {
            navigate("/Login");
        }
        fetchUsername();
    }

    const handleAddToCart = async (e) => {
        e.preventDefault();
        checkForLogin();

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

    const handleAddToWishList = async (e) => {
        e.preventDefault();
        checkForLogin();

        try {
            const response = await axios.post(
                `http://localhost:8080/api/v1/user/wishList/${book.id}`,
                {},
                { headers: authHeader() }
            );
            console.log('Book added to WishList:', response.data);
            toast.success('Book added to Wish List successfully!');
        } catch (error) {
            if (error.response && error.response.status === 409) {
                toast.error('Book already in Wish List');
            } else {
                toast.error('Failed to add book to Wish List.');
            }
            console.error('Error adding book to Wish List:', error);
        }
        fetchWishList();
    };

    const handleRemoveFromWishList = async (e) => {
        e.preventDefault();
        checkForLogin();

        try {
            const response = await axios.delete(
                `http://localhost:8080/api/v1/user/wishList/${book.id}`, { headers: authHeader() }
            );
            console.log('Book added to WishList:', response.data);
            toast.success('Book removed from Wish List!');
        } catch (error) {
            if (error.response && error.response.status === 409) {
                toast.error('Book not on Wish List');
            } else {
                toast.error('Failed to remove book from Wish List.');
            }
            console.error('Error removing book from Wish List:', error);
        }
        fetchWishList();
    };

    return (
        <div className="context-container">
            <ToastContainer />
            <div className="background">
                <div className="book-container">

                    <div className="about">
                        <img className="img-resize image-container" src={book.image} alt="Book Cover"/>
                        <div className="book-info">
                            <h2>{book.title}</h2>
                            <h2>{book.author}</h2>
                            <p>Published: {book.publish_date}</p>
                            <p>{book.description}</p>
                        </div>
                    </div>

                    <div className="buy-container">
                        <h2>Buy {book.title}</h2>

                        <div className="cart-container2">
                            <p>{book.price} zł</p>
                            <form onSubmit={handleAddToCart}>
                                <input type="hidden" name="bookId" value={book.id}/>
                                <input type="submit" value="Add to Cart" className="add-to-cart-button2"/>
                            </form>
                        </div>
                    </div>
                    <div className="buy-container">
                        <div className="wish-list">

                            {username === null ? (
                                onWishList !== null && onWishList ? (
                                    <form onSubmit={handleRemoveFromWishList}>
                                        <input type="hidden" name="bookId" value={book.id}/>
                                        <input type="submit" value="On Wish List" className="add-to-cart-button2"/>
                                    </form>
                                ) : (
                                    <form onSubmit={handleAddToWishList}>
                                        <input type="hidden" name="bookId" value={book.id}/>
                                        <input type="submit" value="Add to Wish List" className="add-to-cart-button2"/>
                                    </form>
                                )
                            ) : (
                                <form onSubmit={checkForLogin}>
                                    <input type="submit" value="Add to Wish List" className="add-to-cart-button2"/>
                                </form>
                            )}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default BookDetails;