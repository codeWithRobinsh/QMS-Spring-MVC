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
    	<h1 style="color:red; text-align:center"> Process Add Question to Quiz Details </h1>
    	<br>
    	<table border="1">
        		<tr>
        			<th>Quiz_ID</th>
        			<th>Quiz Tag</th>
        		</tr>
        		<c:forEach items="${availableQuizzes}" var="quiz">
        			<tr>
        				<td>${quiz.id}</td>
        				<td>${quiz.tag}</td>
        			</tr>
        		</c:forEach>
        </table>

        <br><br>
        <table border="1">
               <tr>
               	    <th>Question_ID</th>
               	    <th>Question Tag </th>
               	    <th>Difficulty Level</th>
               </tr>
               <c:forEach items="${availableQuestions}" var="question">
               	<tr>
               		<td>${question.id}</td>
               		<td>${question.tag}</td>
               		<td>${question.difficultyLevel}</td>
               	</tr>
               </c:forEach>
        </table>
        <br>

    	<form action="/quiz/addQuestionToQuiz" method="post">
    	      <p> Enter the quizId of the quiz to which u want add question (from above table)</p>
              QuizId: <input type="text" name = "quizId" placeholder="Enter the quizId" required></input><br>

              <p> Enter the questionId of the question which u want to add (from above table)</p>
              Question Id: <input type="text" name = "questionId" placeholder="Enter the question id" required></input><br>
              <input type="submit" value="Add Question" style="background:green"></input>
        </form>

  </body>
</html>