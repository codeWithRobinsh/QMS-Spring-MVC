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
    	<h2 style="color: green; text-align:center"> Play Quiz Form </h2><br>
        <h4 style="color: green; text-align:center"> <c:out value="${student}" /> </h4><br>
        <br>
        <table border="1" style="align:center; margin-left:auto; margin-right:auto;">
           		<tr>
           			<th>Quiz_ID</th>
           			<th>Quiz Tag</th>
           			<th>Difficulty Level</th>
           			<th>No of Questions</th>
           			<th>Marks</th>
           		</tr>
           		<c:forEach items="${quizzes}" var="quiz">
           			<tr>
           				<td>${quiz.id}</td>
           				<td>${quiz.tag}</td>
           				<td>${quiz.difficultyLevel}</td>
           				<td>${quiz.noOfQuestions}</td>
           				<td>${quiz.marks}</td>
           			</tr>
           		</c:forEach>
        </table>
        <p> Enter the id of the quiz you want to play from above table</p>
        <form action="/student/playQuizMenu" method="post">
                QuizId: <input type="number" name = "quizId" placeholder="Enter the quiz id"></input><br>
                <input type="submit" value="Get Quiz " style="background:green"></input>
        </form>


  </body>
</html>