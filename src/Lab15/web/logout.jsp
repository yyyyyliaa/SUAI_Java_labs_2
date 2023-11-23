<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html lang="ru">
<head>
  <title>Выход из системы</title>
  <meta charset="UTF-8">
  <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
<%--  <link href="styles/style2.css" rel="stylesheet">--%>
</head>
<body class="bg-gray-100 p-8">
<h1 class="text-3xl font-bold mb-4">Выход из системы</h1>
<%
  session.invalidate();
%>
<p class="text-gray-700">Вы успешно вышли из системы.</p>
<a href="main_page.jsp" class="text-blue-600 hover:underline">Вернуться на главную страницу</a>
</body>
</html>

