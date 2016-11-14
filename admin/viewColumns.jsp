<%@ page contentType="text/html; charset=gbk"%>
<%@include file="../common/admin_head.jsp"%>
<s:if test="@com.eportal.util.Tools@isDisable(#session.admin,3)">
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
	<br/><div class="titleText"><s:text name="columns_info"/></div><br/>
	<div class="formDiv">
	  <s:form theme="simple">	
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
		    <td><s:textfield name="columnName" size="22" readonly="true"/></td>
		  </tr>
		  <tr>		    
		    <td align="right"><s:text name="columns_label_code"/>：</td>
		    <td><s:textfield name="columnCode" size="22" readonly="true"/></td>		    	    
		  </tr>
		</table>
		<br>
		  <div align="center">
			<s:set name="label_return" value="%{getText('label_return')}"/>
		    <input type="button" name="btn_ret" value="${label_return}" onClick="window.location='columns_browseColumns.action';">
		  </div>
		</s:form>	
	</div>
</center>	
</body>
</html>
