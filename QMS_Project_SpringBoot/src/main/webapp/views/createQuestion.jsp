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
    	<h1 style="color:red; text-align:center"> Process Question Details </h1>
    	<br>
    	<form action="/question/persistQuestion" method="post">
              QuestionTag: <input type="text" name = "tag" placeholder="Enter the tag"></input><br>
              Difficulty Level: <input type="text" name = "difficultyLevel" placeholder="Enter the difficulty level"></input><br>
              Marks: <input type = "number" name = "marks" placeholder="Enter the marks"></input><br>
              Question: <input type = "text" name = "content" placeholder="Enter the question"></input><br>
              Answer: <input type = "text" name = "answer" placeholder="Enter the correct answer"></input><br>

              Option A: <input type="text" name = "optionA" placeholder="Enter the option A"></input><br>
              Option B: <input type="text" name = "optionB" placeholder="Enter the option A"></input><br>
              Option C: <input type="text" name = "optionC" placeholder="Enter the option A"></input><br>
              Option D: <input type="text" name = "optionD" placeholder="Enter the option A"></input><br>
              <input type="submit" value="Create Question" style="background:green"></input>
        </form>


  </body>
</html>