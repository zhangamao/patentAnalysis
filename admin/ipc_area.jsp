<%@ page language="java" import="java.util.*" pageEncoding="gbk" contentType="text/html; charset=gbk"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>IPC�����������</title>
    
	

  </head>
  
  <body>
   <center>
			<img src="<%=request.getContextPath()%>/ipcAreaApplyServlet" />
			<br />
		</center>
		
		<div id="explainIPC">
			<h3>��д����</h3>
  </body>
</html>
