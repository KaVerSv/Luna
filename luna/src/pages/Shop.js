import React from 'react';
import TopBar from '../components/TopBar';
import Background from '../components/Background';
import RecommendedBooks from "../components/RecommendedBooks";

const Shop = () => {
    // Domyślne wartości propsów
    const defaultIsLoggedIn = false;
    const defaultIsAdmin = false;
    const defaultUsername = "KaVer";

    return (
        <div className="shop">
            <Background background>
                <TopBar isLoggedIn={defaultIsLoggedIn} isAdmin={defaultIsAdmin} username={defaultUsername} />
                <RecommendedBooks books={defaultBooks}/>
            </Background>
        </div>
    );
};

const defaultBooks = [
    {
        id: 1,
        title: "The Mountains of Madness",
        author: "H.P. Lovecraft",
        description: "At the Mountains of Madness is a science fiction-horror novella by American author H. P. Lovecraft, written in February/March 1931.",
        image: "/img/book_images/Mountains_of_Madness.jpg",
        price: 29.99,
        oldPrice: 39.99
    },
    {
        id: 2,
        title: "1984",
        author: "George Orwell",
        description: "Nineteen Eighty-Four is a dystopian novel and cautionary tale by English writer George Orwell. It was published on 8 June 1949",
        image: "/img/book_images/1984.jpg",
        price: 19.99,
        oldPrice: 24.99
    },

];

export default Shop;
