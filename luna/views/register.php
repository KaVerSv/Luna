<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="public\css\style.css">
    <script type="text/javascript" src="./public/js/register_script.js" defer></script>
    <title>Luna register</title>
</head>

<body>

    <div class="login-container">
        <div class="logo">
            <img class="resize" src="public\img\logo_luna_cut.png"/>
        </div>
        <div class="form-container">
            <form class="login" action="register" method="post">
                <p class="title">Register</p>

                <label for="Username">Username:</label>
                <input type="text" id="Username" name="username" required>

                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>

                <label for="password">Passoword:</label>
                <input type="password" id="password" name="password" required>

                <label for="confirmedPassword">Repeat password:</label>
                <input type="password" id="confirmedPassword" name="confirmedPassword" required>

                <label for="date">Date of birth:</label>
                <input type="date" id="start" name="date" value="2018-07-22" min="1930-01-01" max="2024-12-31" required>

                <div class="center-this-item" >
                    <button type="submit">Register</button>
                </div>
                <div class="messages">
                    <?php
                    if(isset($messages)){
                        foreach($messages as $message) {
                            echo $message;
                        }
                    }
                    ?>
                </div>

                <div class="links">
                    <a href="login">I Already have an account</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>