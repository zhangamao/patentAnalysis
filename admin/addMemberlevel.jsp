<%@ page contentType="text/html; charset=gbk"%>
<%@ taglib prefix="e" uri="/patent"%> 
<%@include file="../common/admin_head.jsp"%>
<s:if test="@com.eportal.util.Tools@isDisable(#session.admin,6)">
	<jsp:forward page="/admin/error.htm"/>
</s:if>
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<title><s:text name="admin_title"/></title>
<link href="<%=basepath%>/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<center>
	<br/><div class="titleText"><s:text name="level_add"/></div><br/>
	<div class="formDiv">
	  <s:form action="level_addMemberlevel">	
		<table width="300" align="center" border="0" cellpadding="0" cellspacing="0">
		  <tr>
		    <td align="right"><s:text name="level_label_name"/>：</td>
		    <td><s:textfield name="levelName" size="22"/></td>
		  </tr>
		  <tr>		    
		    <td align="right"><s:text name="level_label_integral"/>：</td>
		    <td><s:textfield name="integral" size="22"/></td>		    	    
		  </tr>		  
		  <tr>		    
		    <td align="right"><s:text name="level_label_favourable"/>：</td>
		    <td><s:textfield name="favourable" size="22"/>%</td>		    	    
		  </tr>
		</table>
		<br>
		  <div align="center">
			<s:submit key="label_submit"/>&nbsp;
			<s:reset key="label_reset"/>&nbsp;
			<s:set name="label_return" value="%{getText('label_return')}"/>
		    <input type="button" name="btn_ret" value="${label_return}" onClick="window.location='level_browseMemberlevel.action';">
		  </div>
		</s:form>	
	</div>
</center>
<s:if test="hasFieldErrors()">
	<e:msgdialog basepath="<%=basepath%>">
		<s:fielderror/>
	</e:msgdialog>
</s:if>
<s:if test="hasActionMessages()">
	<e:msgdialog basepath="<%=basepath%>">
		<s:actionmessage/>
	</e:msgdialog>
</s:if>	
</body>
</html>
