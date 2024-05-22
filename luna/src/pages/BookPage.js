import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useLocation } from 'react-router-dom';
import TopBar from '../components/TopBar';
import Background from '../components/Background';
import BookDetails from "../components/BookDetails";

const BookPage = () => {
    const [username, setUsername] = useState(null);
    const [book, setBook] = useState(null);
    const [discount, setDiscount] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    //get selected book id
    const location = useLocation();
    const searchParams = new URLSearchParams(location.search);
    const bookId = searchParams.get('id');

    useEffect(() => {
        const fetchBook = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/books/${bookId}`);
                setBook(response.data);
                setLoading(false);
            } catch (error) {
                setError(error);
                setLoading(false);
            }
        };

        const fetchDiscount = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/discounts/${bookId}`);
                setDiscount(response.data);
                setLoading(false);
            } catch (error) {
                setLoading(false);
            }
        };

        if (bookId) {
            fetchBook();
            fetchDiscount();
        } else {
            setLoading(false);
        }
    }, [bookId]);

    if (loading) return <div>Loading...</div>;
    if (error) return <div>Error: {error.message}</div>;

    return (
        <div className="shop">
            <Background background>
                <TopBar isLoggedIn={username}/>
                <BookDetails book={book} username={username} discount={discount}/>
            </Background>
        </div>
    );
};

export default BookPage;
