import React, { useEffect, useState } from 'react';
import axios from 'axios';
import '../css/discounts.css';
import '../css/cart.css';
import '../css/style.css';
import Background from "../components/Background";
import TopBar from "../components/TopBar";
import { useNavigate } from "react-router-dom";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const Discounts = () => {
    const [books, setBooks] = useState([]);
    const [discounts, setDiscounts] = useState({});
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');
    const [isAdmin, setIsAdmin] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        axios.get('http://localhost:8080/api/v1/user/Admin')
            .then(response => {
                setIsAdmin(response.data);
                if (!isAdmin) {
                    navigate("/shop");
                }
            })
            .catch(error => {
                console.error('There was an error fetching the session data!', error);
            });

        axios.get('http://localhost:8080/api/books/all')
            .then(response => {
                setBooks(response.data);
            })
            .catch(error => {
                console.error('There was an error fetching the books!', error);
            });
    }, []);

    const handleDiscountChange = (bookId, value) => {
        setDiscounts({
            ...discounts,
            [bookId]: value
        });
    };

    const createDiscounts = () => {
        const discountRequests = Object.keys(discounts).map(bookId => {
            const discountDto = {
                percentage: discounts[bookId],
                startDate: startDate,
                endDate: endDate
            };
            return axios.post(`http://localhost:8080/api/discounts/book/${bookId}`, discountDto);
        });

        Promise.all(discountRequests)
            .then(responses => {
                toast.success('Discounts created successfully');
            })
            .catch(error => {
                toast.error('There was an error creating the discounts');
            });
    };

    return (
        <Background background>
            <TopBar />
            <div className="context-container">
                <div className="background">
                    <h1>Create Discount</h1>
                    <div className="center-this-item">
                        <div className="discounts-search-bar"></div>
                    </div>
                    <section className="discounts-container">
                        {books.length > 0 ? (
                            <form id="discountForm" onSubmit={(e) => e.preventDefault()}>
                                <div className="global-discount-dates">
                                    <input
                                        type="date"
                                        name="startDate"
                                        placeholder="Start Date"
                                        required
                                        onChange={(e) => setStartDate(e.target.value)}
                                    />
                                    <input
                                        type="date"
                                        name="endDate"
                                        placeholder="End Date"
                                        required
                                        onChange={(e) => setEndDate(e.target.value)}
                                    />
                                </div>
                                {books.map(book => (
                                    <div key={book.id} className="item">
                                        <div className="cart-item">
                                            <a href={`book_page?id=${book.id}`}><h3>{book.title}</h3></a>
                                            <div className="end">
                                                <p>Base Price: {book.price} zł</p>
                                            </div>
                                        </div>
                                        <div className="input-discount">
                                            <input
                                                type="number"
                                                name={`discounts[${book.id}]`}
                                                min="1"
                                                max="100"
                                                placeholder="Discount %"
                                                required
                                                onChange={(e) => handleDiscountChange(book.id, e.target.value)}
                                            />
                                        </div>
                                    </div>
                                ))}
                                <div className="total">
                                    <div className="end">
                                        <input
                                            type="button"
                                            onClick={createDiscounts}
                                            value="Create Discounts"
                                            className="add-to-cart-button"
                                        />
                                    </div>
                                </div>
                            </form>
                        ) : (
                            <p>No books available</p>
                        )}
                    </section>
                </div>
            </div>
            <ToastContainer />
        </Background>
    );
};

export default Discounts;
