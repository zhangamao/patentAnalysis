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
	<br/><div class="titleText"><s:text name="member_info"/></div><br/>
	<div class="formDiv">
		<table width="340" align="center" border="0" cellpadding="0" cellspacing="0">
		  <tr>
		    <td align="right"><s:text name="level_label_name"/>：</td>
		    <td> 			    
		    	<s:select name="memeberLevel" disabled="true" 
		    			  list="memberLevelList"
		    			  listKey="id" listValue="levelName" cssStyle="width:120px;"/>				
			</td>
		  </tr>
		  <tr>
		    <td align="right"><s:text name="member_integral"/>：</td>
		    <td><s:textfield name="integral" size="40" disabled="true"/></td>
		  </tr>		  		
		  <tr>
		    <td align="right"><s:text name="member_label_loginName"/>：</td>
		    <td><s:textfield name="loginName" size="40" disabled="true"/></td>
		  </tr>
		  <tr>		    
		    <td align="right"><s:text name="member_label_memberName"/>：</td>
		    <td><s:textfield name="memberName" size="40" disabled="true"/></td>		    	    
		  </tr>
		  <tr>		    
		    <td align="right"><s:text name="member_label_phone"/>：</td>
		    <td><s:textfield name="phone" size="40" disabled="true"/></td>		    	    
		  </tr>
		  <tr>		    
		    <td align="right"><s:text name="member_label_zip"/>：</td>
		    <td><s:textfield name="zip" size="40" disabled="true"/></td>		    	    
		  </tr>
		  <tr>		    
		    <td align="right"><s:text name="member_label_email"/>：</td>
		    <td><s:textfield name="email" size="40" disabled="true"/></td>		    	    
		  </tr>
		  <tr>		    
		    <td align="right"><s:text name="member_label_address"/>：</td>
		    <td><s:textfield name="address" size="40" disabled="true"/></td>		    	    
		  </tr>		  		  		  		  		  
		</table>
		<br>
		  <div align="center">
			<s:set name="label_return" value="%{getText('label_return')}"/>
		    <input type="button" name="btn_ret" value="${label_return}" onClick="window.location='member_browseMember.action';">
		  </div>
	</div>
</center>
</body>
</html>
