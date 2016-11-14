<%@ page language="java" import="java.util.*" pageEncoding="gbk" contentType="text/html; charset=gbk"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>公开量与申请量对比分析</title>
    

  </head>
  
  <body>
    <center>
    <img src="<%=request.getContextPath()%>/applyAndPublicCompareServlet" />
			<br />
    
    <div id="explainCompare">
			<h3>书写样例</h3>
			<p>
			申请量与公开量的整体趋势大致相同，申请量最多年份为2011年，而公开量是在随后的年份2012年
			</p>
		</div>
		</center>
  </body>
</html>
