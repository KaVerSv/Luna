import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../css/style.css';
import '../css/library.css';
import '../css/book_page.css';
import Background from "../components/Background";
import TopBar from "../components/TopBar";
import authHeader from "../services/authHeader";
import {useNavigate} from "react-router-dom";

const Library = () => {
    const [books, setBooks] = useState([]);
    const [selectedBook, setSelectedBook] = useState(null);
    const [username, setUsername] = useState(null);
    const navigate = useNavigate()

    useEffect(() => {
        fetchBooks();
        if(localStorage.getItem("user") === null) {
            navigate("/login");
        }
        const fetchUsername = async () => {
            try {
                const response = await axios.get('http://localhost:8080/api/v1/user/username',{ headers: authHeader() });
            } catch (error) {
                navigate("/login");
            }
        };
        fetchUsername();
    }, []);

    const fetchBooks = async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/v1/library',{ headers: authHeader() });
            setBooks(response.data);

            if (response.data.length > 0) {
                setSelectedBook(response.data[0]);
            }
        } catch (error) {
            console.error('Error fetching books:', error);
        }
    };

    const handleBookClick = (bookId) => {
        const selected = books.find(book => book.id === bookId);
        setSelectedBook(selected);
    };

    return (
        <div className="shop">
            <Background background>
                <TopBar username={username}/>

                <div className="library-container">
                    <div className="user-books-container">
                        {books.map(book => (
                            <div className="item-lib" key={book.id} onClick={() => handleBookClick(book.id)}>
                                <a href="#" className="book-link">
                                    <h3>{book.title}</h3>
                                </a>
                            </div>
                        ))}
                    </div>
                    <div className="book-panel-container" id="book-details-container">
                        {selectedBook ? (
                            <div className="book-panel">
                                <div className="row-section">
                                    <div className="book-cover">
                                        <img className="img-resize image-container" src={selectedBook.image}
                                             alt="Book Cover"/>
                                    </div>
                                    <div className="book-info2">
                                        <h1>Title: {selectedBook.title}</h1>
                                        <p>Author: {selectedBook.author}</p>
                                        <p>Publish Date: {selectedBook.published}</p>
                                        <p>Description: {selectedBook.description}</p>
                                    </div>
                                </div>
                            </div>
                        ) : (
                            <h1>Your Library is empty</h1>
                        )}
                    </div>
                </div>

            </Background>
        </div>
    );

};

export default Library;
