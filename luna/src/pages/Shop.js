import React, { useState, useEffect } from 'react';
import axios from 'axios';
import TopBar from '../components/TopBar';
import Background from '../components/Background';
import RecommendedBooks from "../components/RecommendedBooks";

const Shop = () => {
    const [books, setBooks] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [username, setUsername] = useState(null);

    useEffect(() => {
        const fetchBooks = async () => {
            try {
                const response = await axios.get('http://localhost:8080/api/books/featuredD?name=featured');
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
                <TopBar username={username} />
                <RecommendedBooks books={books}/>
            </Background>
        </div>
    );
};

export default Shop;
