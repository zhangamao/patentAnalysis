<%@ page language="java" import="java.util.*" pageEncoding="gbk" contentType="text/html; charset=gbk"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>主要IPC技术构成分析</title>
    
	
  </head>
  
  <body>
    <center>
			<img src="<%=request.getContextPath()%>/ipcContentServlet" />
			<br />
		</center>
		
		<div id="explainIPC">
			<h3>书写样例</h3>
			
		</div>
  </body>
</html>
