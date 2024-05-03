import React from 'react';
import TopBar from '../components/TopBar';
import Background from '../components/Background';
import BookDetails from "../components/BookDetails";

const BookPage = () => {
    // Domyślne wartości
    const defaultIsLoggedIn = false;
    const defaultIsAdmin = false;
    const defaultUsername = "KaVer";

    // Definicja domyślnej książki
    const defaultBook = {
        id: 1,
        title: "The Mountains of Madness",
        author: "H.P. Lovecraft",
        description: "At the Mountains of Madness is a science fiction-horror novella by American author H. P. Lovecraft, written in February/March 1931.",
        image: "/img/book_images/Mountains_of_Madness.jpg",
        price: 29.99,
        oldPrice: 39.99,
        likes: 2000,
        dislikes: 20
    };

    return (
        <div className="shop">
            <Background background>
                <TopBar isLoggedIn={defaultIsLoggedIn} isAdmin={defaultIsAdmin} username={defaultUsername} />
                <BookDetails book={defaultBook}/>
            </Background>
        </div>
    );

};
    export default BookPage;