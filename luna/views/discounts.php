<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="public\css\style.css">
    <link rel="stylesheet" href="public\css\discounts.css">
    <link rel="stylesheet" href="public\css\cart.css">

    <script type="text/javascript" src="./public/js/search.js" defer></script>
    <script type="text/javascript" src="./public/js/discounts.js" defer></script>
    <title>discounts</title>
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

            <h1>CREATE DICOUNT</h1>

            <div class="center-this-item" >
                <div class="discounts-search-bar">

                </div>
            </div>

            <section class="discounts-container">
                <?php

                if (!empty($books)) {
                    echo '<form id="discountForm" method="post" action="create_discount.php">';

                    // Iteruj przez każdą książkę w koszyku
                    foreach ($books as $book) {
                        echo '<div class="item">';
                        echo '<div class="cart-item">';
                        echo '<a href="book_page?id=' . $book->getId() . '"><h3>' . $book->getTitle() . '</h3></a>';

                        echo '<div class="end">';
                        echo '<p>Base Price: ' . $book->getPrice() . " zł" . '</p>';

                        echo '</div>';
                        echo '</div>';

                        // Formularz wprowadzania procentowej przeceny dla każdej książki
                        echo '<div class="input-discount">';
                        echo '<input type="number" name="discounts[' . $book->getId() . ']" min="1" max="100" placeholder="Discount %" required>';
                        echo '</div>';

                        echo '</div>';
                    }

                    echo '<div class="total">';
                    echo '<div class="end">';
                    echo '<input type="button" onclick="createDiscounts()" value="Create Discounts" class="add-to-cart-button">';
                    echo '</div>';
                    echo '</div>';
                    echo '</form>';
                } else {
                    echo '<p>no books available</p>';
                }

                ?>
            </section>
        </div>
    </div>


</div>

</body>

<template id="book-template">
    <div class="item">
        <div class="cart-item">
            <a href=link><h3> title </h3></a>
            <div class="end">
                <p>price</p>
            </div>
        </div>
        <div class="input-discount">
            <input type="number" name="the-discounts" min="1" max="100" placeholder="Discount %" required>
        </div>
    </div>'
</template>

</html>