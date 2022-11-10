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
    	<h2 style="color: green; text-align:center"> Play Quiz Menu </h2><br>
        <h4 style="color: green; text-align:center"> <c:out value="${student}" /> </h4><br>
        <br>
        <form action="/student/persistPlayedQuiz" method="post">
                You are playing quiz with quiz id: <input type="text" value="${quizId}"  name = "quizId" readOnly></input><br>
                <c:forEach items="${questions}" var="question">
                    <tr>
                        <td>${question.content}</td><br>
                        <td>${question.options.optionA}</td><br>
                        <td>${question.options.optionB}</td><br>
                        <td>${question.options.optionC}</td><br>
                        <td>${question.options.optionD}</td><br>
                    </tr>
                    Your Answer:<input type="text" name = "answerList" placeholder="Enter the correct option"></input><br>

                </c:forEach>
                <input type="submit" value="Get Quiz " style="background:green"></input>
        </form>


  </body>
</html>