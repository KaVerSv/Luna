import React from 'react';
import TopBar from '../components/TopBar';
import Background from '../components/Background';
import RecommendedBooks from "../components/RecommendedBooks";

const Shop = () => {
    // Domyślne wartości propsów
    const defaultIsLoggedIn = false;
    const defaultIsAdmin = false;
    const defaultUsername = "";

    // Tutaj możemy przekazać odpowiednie propsy do komponentu TopBar
    const isLoggedIn = defaultIsLoggedIn; // Domyślna wartość dla zalogowanego użytkownika
    const isAdmin = defaultIsAdmin; // Domyślna wartość dla administratora
    const username = defaultUsername; // Domyślna wartość dla nazwy użytkownika

    return (
        <div className="shop">
            <Background background>
                <TopBar isLoggedIn={isLoggedIn} isAdmin={isAdmin} username={username} />
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
        description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
        image: "/img/book_images/Mountains_of_Madness.jpg",
        price: 29.99,
        oldPrice: 39.99
    },
    {
        id: 2,
        title: "1984",
        author: "George Orwell",
        description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
        image: "/img/book_images/1984.jpg",
        price: 19.99,
        oldPrice: 24.99
    },

];

export default Shop;
