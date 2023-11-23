<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,java.util.List,java.io.BufferedReader,java.io.FileReader" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Добавить объявление</title>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
<%--    <link href="styles/style2.css" rel="stylesheet">--%>
</head>
<body class="bg-gray-100 p-8">
<h1 class="text-3xl font-bold mb-4">Добавить объявление</h1>
<form action="AddAnnouncement" method="POST" accept-charset="utf-8" class="bg-white p-4 rounded shadow">
    <div class="mb-4">
        <label for="title" class="block text-gray-600">Название:</label>
        <input type="text" name="title" id="title" class="w-full p-2 border border-gray-300 rounded focus:outline-none focus:border-blue-400">
    </div>
    <div class="mb-4">
        <label for="text" class="block text-gray-600">Текст:</label>
        <input type="text" name="text" id="text" class="w-full p-2 border border-gray-300 rounded focus:outline-none focus:border-blue-400">
    </div>
    <input type="submit" value="Добавить" class="bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600">
</form>
</body>
</html>
