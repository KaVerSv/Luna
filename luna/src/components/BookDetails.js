import React, { useEffect, useState } from 'react';
import "../css/style.css"
import "../css/book_page.css"


const BookDetails = ({ book }) => {
    console.log(book);
    const [positivePercentage, setPositivePercentage] = useState(0);
    const [totalVotes, setTotalVotes] = useState(0);

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

    return (
        <div className="context-container">
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
                            <form method="post" action="addToCart">
                                <input type="hidden" name="bookId" value={book.id} />
                                <input type="submit" value="Add to Cart" className="add-to-cart-button" />
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default BookDetails;