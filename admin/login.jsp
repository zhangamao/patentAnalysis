<%@ page contentType="text/html; charset=gbk"%>
<%@ taglib prefix="e" uri="/patent"%> 
<%@include file="../common/admin_head.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<title><s:text name="admin_title"/></title>
<link href="<%=basepath%>/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<br/><br/><br/>
<s:form action="admin_login">
<table border="0" cellpadding="0" cellspacing="0" align="center" width="476" height="298" background="<%=basepath%>/images/login_back.jpg">
<tr height="110">
	<td colspan="5"></td>
</tr>
<tr>
	<td width="60"></td>
	<td class="labeltext" width="100"><s:text name="login_label_loginname"/>:</td>
	<td><s:textfield name="loginName" size="26"/></td>
	<td></td>
	<td width="60"></td>
</tr>
<tr>
	<td width="60"></td>
	<td class="labeltext" width="100"><s:text name="login_label_password"/>:</td>
	<td><s:password name="loginPwd" size="26"/></td>
	<td></td>
	<td width="60"></td>
</tr>
<tr>
	<td width="60"></td>
	<td class="labeltext" width="100"><s:text name="login_label_rand"/>:</td>
	<td><s:textfield name="rand" size="26"/>&nbsp;<img src="<%=basepath%>/common/rand.jsp" height="16" border="1"/></td>
	<td></td>
	<td width="60"></td>
</tr>
<tr>
	<td colspan="5" align="center">
		<s:submit key="login_submit"/>&nbsp;
		<s:reset key="label_reset"/>
	</td>
</tr>
<tr height="50">
	<td colspan="5" align="center"></td>
</tr> 
</table>
</s:form>
<s:if test="hasFieldErrors()">
	<e:msgdialog basepath="<%=basepath%>">
		<s:fielderror/>
	</e:msgdialog>
</s:if>
<s:if test="hasActionErrors()">
	<e:msgdialog basepath="<%=basepath%>">
		<s:actionerror/>
	</e:msgdialog>
</s:if>
</body>
</html>
