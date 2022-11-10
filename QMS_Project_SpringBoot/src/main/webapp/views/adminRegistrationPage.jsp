<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" %>
    <%@ page import="com.epam.quizproject.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
  <head>
  	<title>Quiz Management System</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<link href='https://fonts.googleapis.com/css?family=Roboto:400,100,300,700' rel='stylesheet' type='text/css'>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">

    <style>
    * {box-sizing: border-box;}

    @media screen and (max-width: 500px) {
        .header a {
            float: none;
            display: block;
            text-align: left;
        }
    }
    </style>
  </head>

  <body style="background:linen">
    	<h1 style="color:red; text-align:center"> Registration Page : Admin</h1>
    	<form action="/admin/registerAdmin" method="post">
            Username: <input type="text" placeholder="Enter your username" name="username"></input><br>
            Password: <input type="text" placeholder="Enter your password" name="password"></input><br>
            Name: <input type="text" placeholder="Enter your full name name=" name="name"></input><br>
            <input type="text" value="admin" name="userType" readOnly><br>
            <input type="submit" value="Sign Up" style="background:green"></input>
    	</form>
  </body>
</html>