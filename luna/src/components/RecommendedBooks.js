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
        const currentBookId = books[currentImageIndex].id;
        // Przekieruj do odpowiedniej strony dla książki z danym ID
        window.location.href = `book_page?id=${currentBookId}`;
    };

    return (
        <div>
            <div className="recommended">
                <button onClick={prevImage} className="svg-button left">
                    <img src="/img/arrow-right.svg" alt="SVG Button" />
                </button>

                <div className="featured" onClick={goToBookPage}>
                    <img className="img-resize image-container" src={books[currentImageIndex].image} alt="Book Cover" />
                    <div className="book-info">
                        <h2 id="book-title">{books[currentImageIndex].title}</h2>
                        <p id="book-author">Author: {books[currentImageIndex].author}</p>
                        <p id="book-description">{books[currentImageIndex].description}</p>
                    </div>
                </div>

                <button onClick={nextImage} className="svg-button">
                    <img src="/img/arrow-right.svg" alt="SVG Button" />
                </button>
            </div>
            <div>
                <div className="dots-container">
                    {/* Mapujemy kropki na podstawie ilości książek */}
                    {books.map((book, index) => (
                        <div key={index} className={`dot ${index === currentImageIndex ? 'active' : ''}`} onClick={() => showImage(index)}></div>
                    ))}
                </div>
            </div>

            <div className="center-this-item">
                <div className="price-container" onClick={goToBookPage}>
                    <p id="book-old-price" className="old-price">{books[currentImageIndex].oldPrice}</p>
                    <p id="book-price" className="price">{books[currentImageIndex].price}</p>
                </div>
            </div>
        </div>
    );
};

export default RecommendedBooks;
