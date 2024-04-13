<!DOCTYPE html>
<html>
<head>
    <title>Przykładowa strona JSP</title>
</head>
<body>
    <h1>Hello <%= request.getParameter("item") %>!</h1>
    <p>date: <%= new java.util.Date() %></p>
</body>
</html>