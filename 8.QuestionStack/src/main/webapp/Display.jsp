<%@page import="java.util.ArrayList"%>
<%@page import="Model.Question"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
</head>
<body>
<% Object o= request.getAttribute("QuestionList"); %>
<% ArrayList<Question> list=(ArrayList<Question>)o; %>
<%int i=1; %>

<form action="submitAnswer" method="post">
<% for(Question data:list){ %>
<h5>Question :- <%= i +")"+data.getQuestion() %></h5>
      <div class="p-2">
        <input type="radio" name="<%=data.getQuestionId() %>" value="1"  class="form-check-input"> <label><%= data.getOption1() %></label><br>	
		<input type="radio" name="<%=data.getQuestionId() %>" value="2"  class="form-check-input"> <label><%= data.getOption2() %></label><br>
		<input type="radio" name="<%=data.getQuestionId() %>" value="3"  class="form-check-input"> <label><%= data.getOption3() %></label><br>
	    <input type="radio" name="<%=data.getQuestionId() %>" value="4"  class="form-check-input"> <label><%= data.getOption4() %></label><br>
	   </div>	
<%i++; } %>
<input type="submit" value="SUBMIT-ANSWER" class="btn btn-primary">
</form>
</body>
</html>