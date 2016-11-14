<%@page contentType="text/html; charset=gbk"%>
<%@page import="com.patent.struts.action.CrawlNewsThread"%>
<%@page import="com.patent.ORM.Admin"%>
<%@taglib prefix="e" uri="/patent"%> 
<%@include file="../common/admin_head.jsp"%>
<%
	//提取新闻采集进度信息
	String msg = "";
	CrawlNewsThread ct = (CrawlNewsThread)session.getAttribute("ct");
	if (ct!=null){
		msg = ct.getStatusMessage().toString();
	}		
%>
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<meta http-equiv="refresh" content="3">  
<title><s:text name="admin_title"/></title>
<link href="<%=basepath%>/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<center>
	<br><div class="titleText"><s:text name="rule_craw_status"/></div>
	<textarea rows="30" cols="100" style="background-color:#D6D3CE;color:black;overflow:hidden;border:0;"><%=msg%></textarea>
	<br/>
	<div align="center">
		<s:set name="label_return" value="%{getText('label_return')}"/>
	    <input type="button" name="btn_ret" value="${label_return}" onClick="window.location='rule_browseNewsrule.action';">
	 </div>	
</center>
</body>
</html>
