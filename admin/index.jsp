<%@ page contentType="text/html; charset=gbk"%>
<%@include file="../common/admin_head.jsp"%>
<%@include file="../common/admin_isLogined.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<title><s:text name="admin_title"/></title>
</head>
<frameset rows="60,*,30" frameborder="no" border="0" framespacing="0">
  <frame src="top.jsp" name="topFrame" scrolling="no" noresize="noresize" id="topFrame"  style="border-bottom:1px #848284 solid;"/>
  <frameset cols="200,*" frameborder="no" border="0" framespacing="0">
		<frame src="menu.jsp" name="leftFrame" scrolling="auto" id="leftFrame" style="border-right:1px #848284 solid;"/>
		<frame src="main.jsp" name="mainFrame" id="mainFrame" />
  </frameset>
  <frame src="bottom.jsp" name="bottomFrame" scrolling="no" noresize="noresize" id="bottomFrame"  style="border-top:1px #848284 solid;"/>
</frameset>
<noframes><body></body></noframes>
</html>
