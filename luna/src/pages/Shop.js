import React, { useState, useEffect } from 'react';
import axios from 'axios';
import TopBar from '../components/TopBar';
import Background from '../components/Background';
import RecommendedBooks from "../components/RecommendedBooks";

const Shop = () => {
    const [books, setBooks] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    // Domyślne wartości propsów
    const defaultIsLoggedIn = false;
    const defaultIsAdmin = false;
    const defaultUsername = "KaVer";

    useEffect(() => {
        const fetchBooks = async () => {
            try {
                const response = await axios.get('http://localhost:8080/api/books/featured?name=featured');
                setBooks(response.data);
                setLoading(false);
            } catch (error) {
                setError(error);
                setLoading(false);
            }
        };

        fetchBooks();
    }, []);

    if (loading) return <div>Loading...</div>;
    if (error) return <div>Error: {error.message}</div>;

    return (
        <div className="shop">
            <Background background>
                <TopBar isLoggedIn={defaultIsLoggedIn} isAdmin={defaultIsAdmin} username={defaultUsername} />
                <RecommendedBooks books={books}/>
            </Background>
        </div>
    );
};

export default Shop;
