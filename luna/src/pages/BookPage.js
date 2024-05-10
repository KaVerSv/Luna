import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useLocation } from 'react-router-dom'; // Importujemy hook useLocation z react-router-dom
import TopBar from '../components/TopBar';
import Background from '../components/Background';
import BookDetails from "../components/BookDetails";

const BookPage = () => {
    // Domyślne wartości
    const defaultIsLoggedIn = false;
    const defaultIsAdmin = false;
    const defaultUsername = "KaVer";

    // Stan dla danych książki
    const [book, setBook] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    // Pobieranie parametrów adresu URL
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

        if (bookId) {
            fetchBook();
        } else {
            setLoading(false);
        }
    }, [bookId]);

    if (loading) return <div>Loading...</div>;
    if (error) return <div>Error: {error.message}</div>;

    return (
        <div className="shop">
            <Background background>
                <TopBar isLoggedIn={defaultIsLoggedIn} isAdmin={defaultIsAdmin} username={defaultUsername} />
                <BookDetails book={book}/>
            </Background>
        </div>
    );
};

export default BookPage;
