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
    	<h1 style="color:red; text-align:center"> Result Page </h1>
    	<h4 style="color: green; text-align:center"> <c:out value="${student}" /> </h4><br>
    	<br>
    	<table border="1" style="align:center; margin-left:auto; margin-right:auto;">
        		<tr>
        			<th>Student_ID</th>
        			<th>Student Name</th>
        			<th>Quiz Id</th>
        			<th>No of Questions</th>
        			<th>Quiz Marks</th>
        			<th>Scored Marks</th>
        		</tr>
        		<c:forEach items="${resultDtoSet}" var="resultDto">
        			<tr>
        				<td>${resultDto.student.id}</td>
        				<td>${resultDto.student.name}</td>
        				<td>${resultDto.quiz.id}</td>
        				<td>${resultDto.quiz.noOfQuestions}</td>
        				<td>${resultDto.quiz.marks}</td>
        				<td>${resultDto.marksScored}</td>
        			</tr>
        		</c:forEach>
        </table>

  </body>
</html>