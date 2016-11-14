<%@ page contentType="text/html; charset=gbk"%>
<%@include file="../common/admin_head.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<title><s:text name="admin_title"/></title>
<link href="<%=basepath%>/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body style="background-image:url(<%=basepath%>/images/top_back.jpg); background-repeat:repeat-x;">
<div class="admin_logo">
	<img src="<%=basepath%>/images/admin_logo.jpg"/>
</div>
<div class="admin_prompt">
	<s:bean name="java.util.Date" id="now"/>
	<s:text name="admin_welcome">
		<s:param>
			<s:property value="#session.admin.loginName" default="管理员"/>
		</s:param>
	</s:text><br/>
	<s:text name="admin_datetime">
		<s:param>
			<s:date name="now" format="yyyy年MM月dd日 HH:mm:ss E"/>
		</s:param>
	</s:text>
</div>
</body>
</html>
