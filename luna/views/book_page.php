<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="public\css\style.css">
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

    <div class="context-container">
        <div class="background">
            <div class="book-container" >
                <h1 id="book-title"></h1>

                <div class="about">
                    <img class="img-resize image-container" src="" alt="Image 1">
                    <div class="book-info">
                        <h2 id="book-title"></h2>
                        <h2 id="book-author"></h2>
                        <p id="book-published"></p>
                        <p id="book-reviews"></p>
                        <p id="book-description"></p>
                    </div>
                </div>

                <div class="buy-container">
                    <h2>Buy&nbsp<?php echo $book->getTitle();?></h2>

                    <div class="cart-container">
                        <p id="book-price"></p>
                        <form method="post" action="addToCart">
                            <input type="hidden" name="bookId" value="<?php echo $book->getId(); ?>">
                            <input type="submit" value="Add to Cart" class="add-to-cart-button">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<?php
    if (isset($book)) {
        $jsImage = json_encode($book->getImage());
        $jsTitle = json_encode($book->getTitle());
        $jsAuthor = json_encode($book->getAuthor());
        $jsDescription = json_encode($book->getDescription());
        $jsPrice = json_encode($book->getPrice());
        $jsPublishDate = json_encode($book->getPublishDate());
        $jsId = json_encode($book->getId());
        $jsLikes = json_encode($book->getLikes());
        $jsDislikes = json_encode($book->getDislikes());
    }
?>

<script>
    var image = <?php echo $jsImage; ?>;
    var title = <?php echo $jsTitle; ?>;
    var author = <?php echo $jsAuthor; ?>;
    var description = <?php echo $jsDescription; ?>;
    var price = <?php echo $jsPrice; ?>;
    var id = <?php echo $jsId; ?>;
    var publishDate = <?php echo $jsPublishDate; ?>;
    var likes = <?php echo $jsLikes; ?>;
    var dislikes = <?php echo $jsDislikes; ?>;
    var totalVotes = likes + dislikes;
    var positivePercentage = (totalVotes > 0) ? Math.round((likes / totalVotes) * 100) : 0;

    function fillData(index) {
        var imageContainer = document.querySelector(".background img");
        imageContainer.src = image;

        // Pobierz elementy info o książce
        var titleElement = document.getElementById("book-title");
        var authorElement = document.getElementById("book-author");
        var descriptionElement = document.getElementById("book-description");
        var priceElement = document.getElementById("book-price");
        var publishedElement = document.getElementById("book-published");
        var reviewsElement = document.getElementById("book-reviews");

        // Ustaw informacje o książce
        titleElement.textContent = title;
        authorElement.textContent = "Author: " + author;
        descriptionElement.textContent = description;
        priceElement.textContent = price + "zł" ;
        publishedElement.textContent = "published: " + publishDate;
        var category = getCategory(positivePercentage);
        var titleText = positivePercentage + "% of " + totalVotes + " user reviews were positive.";

        reviewsElement.setAttribute("title", "");
        reviewsElement.setAttribute("data-title", titleText);
        reviewsElement.textContent = "Reviews: " + category;
    }

    function getCategory(percentage) {
        if (percentage >= 90) {
            return "Overwhelmingly Positive";
        } else if (percentage >= 80) {
            return "Very Positive";
        } else if (percentage >= 70) {
            return "Positive";
        } else if (percentage >= 60) {
            return "Mostly Positive";
        } else if (percentage >= 40) {
            return "Mixed";
        } else if (percentage >= 30) {
            return "Mostly Negative";
        } else {
            return "Negative";
        }
    }

    document.addEventListener("DOMContentLoaded", function() {
        fillData();
    });
</script>

</body>
</html>