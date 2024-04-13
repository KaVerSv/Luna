<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="public\css\style.css">
    <link rel="stylesheet" href="public\css\cart.css">
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



    <div class="context-container">
        <div class="background">
            <h1>YOUR SHOPPING CART</h1>
            <div class="cart-container">
                <?php

                $totalPrice = 0; // Inicjalizacja zmiennej na łączną kwotę zamówienia

                if (!empty($books)) {
                    // Iteruj przez każdą książkę w koszyku
                    foreach ($books as $book) {
                        echo '<div class="item">';
                            echo '<div class="cart-item">';
                            echo '<a href="book_page?id=' . $book->getId() . '"><h3>' . $book->getTitle() . '</h3></a>';

                            echo '<div class="end">';
                                echo '<p>Price: ' . $book->getPrice() . " zł" . '</p>';
                            echo '</div>';

                            echo '</div>';
                        echo '<a href="delete?userId=' . $_SESSION['userId'] . '&bookId=' . $book->getId() . '">Delete</a>';
                        echo '</div>';

                        $totalPrice += floatval($book->getPrice());
                    }

                    // Wyświetl łączną kwotę zamówienia po zakończeniu pętli
                    echo '<div class="total">';
                        echo '<p>Total: ' . number_format($totalPrice, 2) . " zł" . '</p>'; // Formatuj kwotę do dwóch miejsc po przecinku
                        echo '<div class="end">';
                            echo '<input type="submit" onclick="buy()" value="Buy" class="add-to-cart-button">';
                        echo '</div>';
                    echo '</div>';
                } else {
                    echo '<p>Your cart is empty</p>';
                }

                ?>
            </div>



        </div>
    </div>
</div>

</body>
</html>