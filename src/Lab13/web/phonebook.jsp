<%@ page import="com.example.Phonebook" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Phonebook</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            display: flex;
            flex-direction: column;
            align-items: center;
            background-color: beige;
        }

        .contact-card {
            background-color: #ffdfff;
            border: 1px solid #ccc;
            border-radius: 10px;
            padding: 20px;
            margin: 10px;
            width: 250px;
        }

        .add-contact-card {
            background-color: #ffdfff;
            border: 1px solid #ccc;
            border-radius: 10px;
            padding: 20px;
            margin: 10px;
            width: 1000px;
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        .add-form {
            margin-top: 10px;
        }

        .contact-name {
            font-weight: bold;
            font-size: 1.2em;
        }

        .contact-phone {
            margin-top: 10px;
            font-size: 1em;
        }

        .add-number-button {
            margin-top: 10px;
            padding: 2px 5px;
            font-size: 1em;
            border: none;
            background-color: #FEFFBC;
            color: #000;
            border-radius: 5px;
            cursor: pointer;
            display: block;
            flex-direction: column;
            align-items: center;
        }
        .add-number-button:hover {
            background-color: #FEFFBf;
        }

        .add-contact-button {
            display: block;
            margin-top: 20px;
            padding: 10px 20px;
            font-size: 1em;
            border: none;
            background-color: #007BFF;
            color: #000;
            border-radius: 5px;
            cursor: pointer;
        }

        .add-contact-button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<h1>PHONEBOOK</h1>
<%
    Phonebook phonebook = new Phonebook();
    final String filePath = "/Users/yyyyyliaa/untitled1/src/main/java/com/example/phonebook.txt";
    phonebook.loadPhonebookFromFile(filePath);
    StringBuilder sb = new StringBuilder();
    sb.append("</form>");

    HashMap<String, List<String>> tmp = phonebook.getNamesStrings();

    for (Map.Entry<String, List<String>> entry : tmp.entrySet()) {
        String name = entry.getKey();
        List<String> phoneNumbers = entry.getValue();
%>
<div class="contact-card">
    <div class="contact-name"><%= name %></div>
    <div class="contact-phone">
        <ul>
            <% for (String phoneNumber : phoneNumbers) { %>
            <li><%= phoneNumber %></li>
            <% } %>
        </ul>
    </div>
    <form class="add-form" action="/addPhone/add" method="GET">
        <input type="hidden" name="name" value="<%= name %>">
        <label for="phone">Add Number:</label>
        <input type="text" id="phone" name="phone" required>
        <button class="add-number-button" type="submit">Add Number</button>
    </form>
    <form class="add-form" action="/addPhone/del" method="GET">
        <input type="hidden" name="name" value="<%= name %>">
        <label for="phone1">Del Number:</label>
        <input type="text" id="phone1" name="phone" required>
        <button class="add-number-button" type="submit">Del Number</button>
    </form>
    <form class="add-form" action="/addName/edit" method="GET">
        <input type="hidden" name="name" value="<%= name %>">
        <label for="phone3">Edit Name:</label>
        <input type="text" id="phone3" name="phone" required>
        <button class="add-number-button" type="submit">Edit Name</button>
    </form>
</div>

<%
    }
%>
<div class="add-contact-card">
<form class="add-form" action="/addName/add" method="GET">
    <label for="name">Enter Contact Name:</label>
    <input type="text" id="name" name="name">
    <label for="phonee">Enter Phone Number:</label>
    <input type="text" id="phonee" name="phonee">
    <button class="add-contact-button" type="submit">Add Contact</button>
</form>
</div>

</body>
</html>