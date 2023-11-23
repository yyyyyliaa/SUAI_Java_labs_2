<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,java.util.List,java.io.BufferedReader,java.io.FileReader" %>
<%@ page import="com.example.Adboard" %>
<%@ page import="com.example.Ad" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html lang="ru">
<head>
  <title>Доска объявлений</title>
  <meta charset="UTF-8">
  <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
<%--  <link href="styles/style2.css" rel="stylesheet">--%>
</head>
<body class="bg-gray-100 p-8">
<h1 class="text-3xl font-bold mb-4">Доска объявлений</h1>

<div class="mt-4">
  <%
    if (session.getAttribute("loggedInUser") == null) {
  %>
  <a href="login.jsp" class="bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600">Войти в систему</a>
  <%
  } else if (session.getAttribute("loggedInUser").toString().equals("admin")){
  %>
  <a href="addAnnouncement.jsp" class="bg-green-500 text-white py-2 px-4 rounded hover:bg-green-600">Добавить объявление</a>
  <a href="logout.jsp" class="bg-red-500 text-white py-2 px-4 rounded hover:bg-red-600">Выйти из системы</a>
  <a href="add_user.jsp" class="bg-red-500 text-white py-2 px-4 rounded hover:bg-red-600">Добавить пользователя</a>
  <%
    } else{
  %>
  <a href="addAnnouncement.jsp" class="bg-green-500 text-white py-2 px-4 rounded hover:bg-green-600">Добавить объявление</a>
  <a href="add_coment.jsp" class="bg-green-500 text-white py-2 px-4 rounded hover:bg-green-600">Добавить комментарий</a>
  <a href="logout.jsp" class="bg-red-500 text-white py-2 px-4 rounded hover:bg-red-600">Выйти из системы</a>
  <%
    }
  %>
</div>

<div class="ad-card grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-4">
  <%
    Adboard adboard = Adboard.getInstance();
    if(!adboard.getAds().isEmpty()){
      for(Ad ad : adboard.getAds()){

  %>
  <div class="ad-custom-card bg-white p-4 rounded shadow" >
    <h2 class="text-xl font-semibold mb-2"><%= ad.getTitle() %></h2>
    <p class="text-gray-700"><%= ad.getText() %></p>
    <%
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : ad.getComments().entrySet()) {
          sb.append("--");
          String str = sb.toString() + "Author:";
          String str1 = sb.toString() + "Comment:";

    %>
    <p class="font-bold"> <%=str%> <%=entry.getValue() %></p>
    <p class="italic"><%=str1%><%=entry.getKey() %></p>

    <%
          }
    %>

    <div class="border-t mt-3 pt-2 flex justify-between">
      <p class="font-bold"><%= ad.getCreator() %></p>
      <p class="italic"><%= ad.getDate() %></p>
    </div>
  </div>
  <%
      }
    }
  %>
</div>


</body>
</html>