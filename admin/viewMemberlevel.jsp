<%@ page contentType="text/html; charset=gbk"%>
<%@include file="../common/admin_head.jsp"%>
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<title><s:text name="admin_title"/></title>
<link href="<%=basepath%>/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<center>
	<br/><div class="titleText"><s:text name="level_info"/></div><br/>
	<div class="formDiv">
		<table width="300" align="center" border="0" cellpadding="0" cellspacing="0">
		  <tr>
		    <td align="right"><s:text name="level_label_name"/>：</td>
		    <td><s:textfield name="levelName" size="22" disabled="true"/></td>
		  </tr>
		  <tr>		    
		    <td align="right"><s:text name="level_label_integral"/>：</td>
		    <td><s:textfield name="integral" size="22" disabled="true"/></td>		    	    
		  </tr>			  
		  <tr>		    
		    <td align="right"><s:text name="level_label_favourable"/>：</td>
		    <td><s:textfield name="favourable" size="22" disabled="true"/>%</td>		    	    
		  </tr>
		</table>
		<br>
		  <div align="center">
			<s:set name="label_return" value="%{getText('label_return')}"/>
		    <input type="button" name="btn_ret" value="${label_return}" onClick="window.location='level_browseMemberlevel.action';">
		  </div>
	</div>
</center>
</body>
</html>
