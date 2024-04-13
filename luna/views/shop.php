<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="public\css\style.css">
    <link rel="stylesheet" href="public\css\shop.css">
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

        <div class="recommended">

            <button onclick="prevImage()" class="svg-button left">
                <img src="public/img/arrow-right.svg" alt="SVG Button">
            </button>

            <div class="featured" onclick="goToBookPage()">
                <img class="img-resize image-container" src="public/img/recommended/Mountains_of_Madness.jpg" alt="Image 1">
                <div class="book-info">
                    <h2 id="book-title"></h2>
                    <p id="book-author"></p>
                    <p id="book-description"></p>

                </div>
            </div>

            <button onclick="nextImage()" class="svg-button">
                <img src="public/img/arrow-right.svg" alt="SVG Button">
            </button>
        </div>
        <div>
            <div class="dots-container">
                <!-- Dodaj 7 kropek -->
                <div class="dot active" onclick="showImage(0)"></div>
                <div class="dot" onclick="showImage(1)"></div>
                <div class="dot" onclick="showImage(2)"></div>
                <div class="dot" onclick="showImage(3)"></div>
                <div class="dot" onclick="showImage(4)"></div>
                <div class="dot" onclick="showImage(5)"></div>
                <div class="dot" onclick="showImage(6)"></div>
                <!-- Dodaj pozostałe kropki -->
            </div>

        </div>
        <div class="center-this-item">
            <div class="price-container" onclick="goToBookPage()">
                <p id="book-old-price" class="old-price"></p>
                <p id="book-price" class="price"></p>
            </div>
        </div>




        <div class="table">

        </div>

        <?php
        // Assuming $books is an array of Book objects
        $id = [];
        $images = [];
        $titles = [];
        $descriptions = [];
        $author = [];
        $prices = [];
        $publish_date = [];

        if (isset($books)) {
            foreach ($books as $book) {
                $id[] = $book->getId();
                $images[] = $book->getImage();
                $titles[] = $book->getTitle();
                $descriptions[] = $book->getDescription();
                $authors[] = $book->getAuthor();
                $prices[] = $book->getPrice();
                $publish_date[] = $book->getPublishDate();
            }
        }

        // Convert PHP array to JavaScript array
        $jsArray = json_encode($images);
        $jsTitles = json_encode($titles);
        $jsAuthors = json_encode($authors);
        $jsDescriptions = json_encode($descriptions);
        $jsPrices = json_encode($prices);
        $jsId = json_encode($id);
        ?>

    </div>



    <script>
        var currentImageIndex = 0;
        var images = <?php echo $jsArray; ?>;
        var titles = <?php echo $jsTitles; ?>;
        var authors = <?php echo $jsAuthors; ?>;
        var descriptions = <?php echo $jsDescriptions; ?>;
        var prices = <?php echo $jsPrices; ?>;
        var id = <?php echo $jsId; ?>;


        function showImage(index) {
            var imageContainer = document.querySelector(".featured img");
            imageContainer.src = images[index];

            // Pobierz elementy info o książce
            var titleElement = document.getElementById("book-title");
            var authorElement = document.getElementById("book-author");
            var descriptionElement = document.getElementById("book-description");
            var priceElement = document.getElementById("book-price");

            // Ustaw informacje o książce
            titleElement.textContent = titles[index];
            authorElement.textContent = "Author: " + authors[index];
            descriptionElement.textContent = descriptions[index];
            priceElement.textContent = prices[index] + "zł" ;

            // Usuń aktywność ze wszystkich kropek
            var dots = document.querySelectorAll(".dot");
            dots.forEach(dot => dot.classList.remove("active"));

            // Dodaj aktywność tylko dla wybranej kropki
            dots[index].classList.add("active");
        }


        function prevImage() {
            currentImageIndex = (currentImageIndex - 1 + images.length) % images.length;
            showImage(currentImageIndex);
        }

        function nextImage() {
            currentImageIndex = (currentImageIndex + 1) % images.length;
            showImage(currentImageIndex);
        }

        function goToBookPage() {
            // Otrzymaj ID aktualnie wyświetlanej książki
            var currentBookId = id[currentImageIndex];

            // Przekieruj do widoku book_page z odpowiednim ID
            window.location.href = "book_page?id=" + currentBookId;

            //window.location.href = "book_page/id=" + currentBookId;
        }

        document.addEventListener("DOMContentLoaded", function() {
            // Wywołaj funkcję showImage z indeksem 0 po załadowaniu strony
            showImage(0);
        });
    </script>
</body>
</html>