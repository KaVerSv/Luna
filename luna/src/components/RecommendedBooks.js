import React, { useState } from 'react';
import "../css/style.css"
import "../css/shop.css"

const RecommendedBooks = ({ books }) => {
    const [currentImageIndex, setCurrentImageIndex] = useState(0);

    const showImage = (index) => {
        setCurrentImageIndex(index);
    };

    const prevImage = () => {
        setCurrentImageIndex((prevIndex) => (prevIndex === 0 ? books.length - 1 : prevIndex - 1));
    };

    const nextImage = () => {
        setCurrentImageIndex((prevIndex) => (prevIndex === books.length - 1 ? 0 : prevIndex + 1));
    };

    const goToBookPage = () => {
        const currentBookId = books[currentImageIndex].book.id;
        window.location.href = `BookPage?id=${currentBookId}`;
    };

    const calculateDiscountedPrice = (price, discountPercentage) => {
        return (price * (1 - discountPercentage / 100)).toFixed(2);
    };


    return (
        <div>
            <div className="recommended">
                <button onClick={prevImage} className="svg-button left">
                    <img src="/img/arrow-right.svg" alt="SVG Button" />
                </button>

                <div className="featured" onClick={goToBookPage}>
                    <img className="img-resize image-container" src={books[currentImageIndex].book.image} alt="Book Cover" />
                    <div className="book-info">
                        <h2 id="book-title">{books[currentImageIndex].book.title}</h2>
                        <p id="book-author">Author: {books[currentImageIndex].book.author}</p>
                        <p id="book-description">{books[currentImageIndex].book.description}</p>
                    </div>
                </div>

                <button onClick={nextImage} className="svg-button">
                    <img src="/img/arrow-right.svg" alt="SVG Button" />
                </button>
            </div>
            <div>
                <div className="dots-container">
                    {/* Mapowanie kropek na podstawie ilości książek */}
                    {books.map((book, index) => (
                        <div key={index} className={`dot ${index === currentImageIndex ? 'active' : ''}`} onClick={() => showImage(index)}></div>
                    ))}
                </div>
            </div>

            <div className="center-this-item">
                {books[currentImageIndex].discount === null ? (
                    <div className="price-container" onClick={goToBookPage}>
                        <p id="book-price" className="price">{books[currentImageIndex].book.price}</p>
                    </div>
                ) : (
                    <div className="price-container" onClick={goToBookPage}>
                        <p id="discount-shop-box" className="discount-shop-box">-{books[currentImageIndex].discount.percentage}%</p>
                        <p id="old-price" className="old-price">{books[currentImageIndex].book.price}</p>
                        <p id="book-price" className="price">{calculateDiscountedPrice(books[currentImageIndex].book.price, books[currentImageIndex].discount.percentage)}</p>
                    </div>
                )}
            </div>
        </div>
    );
};

export default RecommendedBooks;
