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
        <h4 style="color: green; text-align:center"> <c:out value="${admin}" /> </h4><br>

    	<div style="color: #243D25; text-align:center">
    	    <a href="/quiz/createQuizForm">1.Create Quiz </a><br>
    	    <a href="/quiz/addQuestionToQuizForm">2.Add Question To Quiz </a><br>
    	    <a href="/quiz/deleteQuizForm">3.Delete Quiz </a><br>
    	    <a href="/question/createQuestionForm">4.Create Question </a><br>
    	    <a href="/quiz/retrieveQuizzes">5.Retrieve Quizzes </a><br>
    	    <a href="/quiz/retrieveQuizzesByTag">6.Retrieve Quizzes By Tag </a><br>
    	    <a href="/question/retrieveQuestionsByTagForm">7.Retrieve Questions By tag </a><br>
    	    <a href="/admin/adminDashboard">8.Return to Admin Dashboard </a><br>
    	    <a href="/student/studentDashboard">9.Return to Student Dashboard  </a><br>
    	    <a href="/exitPage">10.Exit </h3>
    	<div>

  </body>
</html>