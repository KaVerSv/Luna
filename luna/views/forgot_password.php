<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="public\css\style.css">
    <title>Luna reset password</title>
</head>
<body>

    <div class="login-container">
        <div class="logo">
            <img class="resize" src="public\img\logo_luna_cut.png"/>
        </div>

        <div class="form-container">
            <form class="login" action="login" method="POST">

                <p class="title">Reset password</p>

                <label for="email">Login/email:</label>
                <input type="text" id="email" name="email" required>

                <div class="center-this-item" >
                    <button type="submit">Zresetuj hasło</button>
                </div>

                <div class="links">
                    <a href="register">Utwórz konto</a>
                    <a href="login">zaloguj się</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>