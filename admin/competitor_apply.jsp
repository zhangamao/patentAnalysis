<%@ page language="java" import="java.util.*" pageEncoding="gbk" contentType="text/html; charset=gbk"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>主要发明者申请趋势分析</title>
    
	<meta content="text/html; charset=gbk" http-equiv="Content-Type">

  </head>
  
  <body>
  <center>
    <img src="<%=request.getContextPath()%>/competitorTimeServlet"/>    <br/>
    
    </center>
  </body>
</html>
