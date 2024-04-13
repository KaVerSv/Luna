<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="public\css\style.css">
    <link rel="stylesheet" href="public\css\library.css">
    <link rel="stylesheet" href="public\css\book_page.css">
    <title>Luna</title>
</head>
<body>
<div class="shop-container">
    <div class="upper-border">
        <div class="top-buttons">
            <img class="resize2" src="public\img\logo_luna_cut.png"/>
            <a href="shop" class="button"> shop </a>
            <a href="library" class="button"> library </a>
            <?php
            if (isset($_SESSION['userId'])) {
                $username = $_SESSION['username'];
                echo '<div class="user-menu">';
                echo '<a href="#" class="button">' . $username . '</a>';
                //rozwijany pasek
                echo '<div class="logout-menu">';
                echo '<a href="logout">Wyloguj</a>';
                echo '</div>';
                echo '</div>';
            } else {
                echo '<a href="login" class="button">log in</a>';
            }
            ?>

            <?php
            if (isset($_SESSION['userId'])) {
                if ($_SESSION['user_type'] == 'admin') {
                    $username = $_SESSION['username'];
                    echo '<a href="discounts" class="button"> discounts </a>';
                }
            }
            ?>

            <a href="cart" class="button">
                <div>
                    koszyk
                    <img class="cart" src="public/img/cart-shopping-white.svg" alt="SVG Button">
                </div>
            </a>

            <div class="search-bar">
                <form>
                    <input placeholder="search">
                </form>
            </div>
        </div>
    </div>

    <div class="library-container">
        <div class="user-books-container">
            <?php
            if (!empty($books)) {
                // Iteruj przez każdą książkę w koszyku
                foreach ($books as $book) {
                    echo '<div class="item">';
                    echo '<a href="#" class="book-link" data-book-id="' . $book->getId() . '"><h3>' . $book->getTitle() . '</h3></a>';
                    echo '</div>';
                }
            }
            ?>
        </div>
        <div class="book-panel-container" id="book-details-container">
            <?php
            if (!empty($books)) {
                echo '<div class="book-panel">';
                echo '<div class="column-section">';
                echo '<div class="book-cover">';

                echo '<img class="img-resize image-container" src="' . $books[0]->getImage() . '" alt="Book Cover">';
                echo '</div>';

                echo '<div class="book-info">';
                echo '<h3>Title: ' . $books[0]->getTitle() . '</h3>';
                echo '<p>Author: ' . $books[0]->getAuthor() . '</p>';
                echo '<p>Publish Date: ' . $books[0]->getPublishDate() . '</p>';
                echo '<p>Description: ' . $books[0]->getDescription() . '</p>';
                echo '</div>';
                echo '</div>';

                // Dodaj link do strony z pełnymi informacjami o książce
                echo '<a href="book_page?id=' . $books[0]->getId() . '">Shop page</a>';

                echo '</div>';
            } else {
                echo '<p>No books available.</p>';
            }
            ?>
        </div>
    </div>

</div>

<?php
// Przypisz dane o książkach do tablicy JavaScript
$booksData = array();
foreach ($books as $book) {
    $booksData[] = array(
        'id' => $book->getId(),
        'title' => $book->getTitle(),
        'author' => $book->getAuthor(),
        'publish_date' => $book->getPublishDate(),
        'description' => $book->getDescription(),
        'price' => $book->getPrice(),
        'image' => $book->getImage(),
        'likes' => $book->getLikes(),
        'dislikes' => $book->getDislikes()
    );
}
echo '<script>';
echo 'var booksData = ' . json_encode($booksData) . ';';
echo '</script>';
?>

<script>
    document.addEventListener("DOMContentLoaded", function() {
        var bookLinks = document.querySelectorAll(".book-link");

        bookLinks.forEach(function(link) {
            link.addEventListener("click", function(event) {
                event.preventDefault();
                // Pobierz id książki z atrybutu data-book-id i zamień na liczbę całkowitą
                var bookId = parseInt(link.getAttribute("data-book-id"), 10);

                loadBookDetails(bookId);
            });
        });

        function loadBookDetails(bookId) {
            // Użyj metody find do znalezienia książki o określonym id w tablicy JavaScript
            var book = booksData.find(function(item) {
                return item.id === bookId;
            });

            // Wyświetl informacje o książce
            var bookDetailsContainer = document.getElementById("book-details-container");
            bookDetailsContainer.innerHTML = '<div class="book-panel">' +
                '<div class="column-section">' +
                '<div class="book-cover">' +
                '<img class="img-resize image-container" src="' + book.image + '" alt="Book Cover">' +
                '</div>' +

                '<div class="book-info">' +
                '<h3>Title: ' + book.title + '</h3>' +
                '<p>Author: ' + book.author + '</p>' +
                '<p>Publish Date: ' + book.publish_date + '</p>' +
                '<p>Description: ' + book.description + '</p>' +
                '</div>' +
                '</div>' +

                // Dodaj link do strony z pełnymi informacjami o książce
                '<a href="book_page?id=' + book.id + '">Shop page</a>' +

                '</div>';
        }
    });
</script>

</body>
</html>