<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.Farm" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Farm farm = Farm.getInstance();

%>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Грустная Ферма: возвращение</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <meta charset="UTF-8">
</head>
<body class="container my-4">
<h1>ГРУСТНАЯ ФЕРМА</h1>
<ul id="mainList" class="list-group">
</ul>

<script>
    const data = <%=farm.convertToJson()%>
</script>
<script src="script.js" ></script>

</body>
</html>
