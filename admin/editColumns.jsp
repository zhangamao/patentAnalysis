<%@ page contentType="text/html; charset=gbk"%>
<%@ taglib prefix="e" uri="/patent"%> 
<%@include file="../common/admin_head.jsp"%>
<s:if test="@com.patent.util.Tools@isDisable(#session.admin,3)">
	<jsp:forward page="/admin/error.htm"/>
</s:if>
<s:head theme="ajax" debug="true"/>
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<title><s:text name="admin_title"/></title>
<link href="<%=basepath%>/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<center>
	<br/><div class="titleText"><s:text name="columns_edit"/></div><br/>
	<div class="formDiv">
	  <s:form action="updateColumns" theme="simple">	
		<table width="300" align="center" border="0" cellpadding="0" cellspacing="0">
		  <tr>
		    <td align="right"><s:text name="columns_label_parent"/>：</td>
		    <td>
		    	<s:url id="tolist" value="columns_listColumns.action">
		    		<s:param name="parentid" value="parentid" />
		    	</s:url>
		    	<s:div href="%{tolist}" theme="ajax"/>
		    </td>
		  </tr>
		  <tr>
		    <td align="right"><s:text name="columns_label_name"/>：</td>
		    <td><s:textfield name="columnName" size="22"/></td>
		  </tr>
		  <tr>		    
		    <td align="right"><s:text name="columns_label_code"/>：</td>
		    <td><s:textfield name="columnCode" size="22"/></td>		    	    
		  </tr>
		</table>
		<br>
		  <div align="center">
			<s:submit key="label_submit"/>&nbsp;
			<s:reset key="label_reset"/>&nbsp;
			<s:set name="label_return" value="%{getText('label_return')}"/>
		    <input type="button" name="btn_ret" value="${label_return}" onClick="window.location='columns_browseColumns.action';">
		  	<s:hidden name="id"/>
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
