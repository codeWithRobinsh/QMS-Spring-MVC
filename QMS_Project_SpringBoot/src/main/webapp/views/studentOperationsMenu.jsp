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
    	<h1 style="color:red; text-align:center"> Welcome to the Quiz Management System </h1>
    	<h2 style="color: green; text-align:center"> <c:out value="${message}" /> </h2><br>
        <h4 style="color: green; text-align:center"> <c:out value="${student}" /> </h4><br>

    	<div style="color: #243D25; text-align:center">
    	    <a href="/quiz/retrieveQuizzes">1.Retrieve Quizzes </a><br>
    	    <a href="/quiz/retrieveQuizzesByTag">2.Retrieve Quizzes By Tag </a><br>
    	    <a href="/student/playQuizForm">3.Play Quiz </a><br>
    	    <a href="/student/getResult">4.Get Result </a><br>
    	    <a href="/student/studentDashboard">5. Return to Student Dashboard</a><br>
    	    <a href="/admin/adminDashboard">6. Return to Admin Dashboard</a><br>
    	    <a href="/exitPage">7.Exit </h3>
    	<div>

  </body>
</html>