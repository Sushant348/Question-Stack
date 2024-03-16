<%@page import="java.util.ArrayList"%>
<%@page import="Model.Question"%>
<%@page import="java.util.HashMap"%>
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
<% Object o= request.getAttribute("displayList"); %>
<% ArrayList<Question> list=(ArrayList<Question>)o; %>
<% ArrayList<String> userAns=(ArrayList<String>)request.getAttribute("userAns"); %>
<%request.setAttribute("value", "checked"); %>
<%for(int i=0;i<list.size();i++){ %>
<%  if(list.get(i).getAnswer().equals(userAns.get(i))){ %>
    <h5 class="text-success">Question :- <%= list.get(i).getQuestion() %></h5>
      <div class="p-2 text-success">
         <input type="radio"   <% if(userAns.get(i).equals("1")){%><%=request.getAttribute("value")%><%} %>><label style="font-size:20px"><%= list.get(i).getOption1() %></label><br>	
		 <input type="radio" <% if(userAns.get(i).equals("2")){%><%=request.getAttribute("value")%><%} %>><label style="font-size:20px"><%= list.get(i).getOption2() %></label><br>
		 <input type="radio"   <% if(userAns.get(i).equals("3")){%><%=request.getAttribute("value")%><%} %>><label style="font-size:20px"><%= list.get(i).getOption3() %></label><br>
	     <input type="radio"  <% if(userAns.get(i).equals("4")){%><%=request.getAttribute("value")%><%} %>><label style="font-size:20px"><%= list.get(i).getOption4() %></label><br>
	   </div>	
<%}else{ %>
<h5 class="text-danger">Question :- <%= list.get(i).getQuestion() %></h5>
      <div class="p-2 text-danger">
         <input type="radio"  <% if(userAns.get(i).equals("1")){%><%=request.getAttribute("value")%><%} %>><label style="font-size:20px"><%= list.get(i).getOption1() %></label><br>	
		 <input type="radio"  <% if(userAns.get(i).equals("2")){%><%=request.getAttribute("value")%><%} %>><label style="font-size:20px"><%= list.get(i).getOption2() %></label><br>
		 <input type="radio"  <% if(userAns.get(i).equals("3")){%><%=request.getAttribute("value")%><%} %>><label style="font-size:20px"><%= list.get(i).getOption3() %></label><br>
	     <input type="radio"  <% if(userAns.get(i).equals("4")){%><%=request.getAttribute("value")%><%} %>><label style="font-size:20px"><%= list.get(i).getOption4() %></label><br>
	   </div>
	  <h5 class="text-success">CORRECT ANSWER IS :- <%=list.get(i).getAnswer() %></h5>
<%} %>
<%} %>
 

</body>
</html>