<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ page import="com.report.predict.Report" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'patent_report_generate.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  <center>
             <% 
    		Report report = new Report();
    		String reCont = report.geReport();
    		%>
    		<div>
    		 <form action="" method="get">
    		  <textarea rows="100" cols="150" >
    		  
    		    <%=reCont %>  
    		  </textarea>
    		  </form>
    		</div>
    		
  
    </center>
  </body>
</html>
