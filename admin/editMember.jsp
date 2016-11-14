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
<center>
	<br/><div class="titleText"><s:text name="member_edit"/></div><br/>
	<div class="formDiv">
	  <s:form action="updateMember">	
		<table width="340" align="center" border="0" cellpadding="0" cellspacing="0">
		  <tr>
		    <td align="right"><s:text name="level_label_name"/>：</td>
		    <td> 			    
		    	<s:select name="memeberLevel" 
		    			  list="memberLevelList"
		    			  listKey="id" listValue="levelName" cssStyle="width:120px;"/>				
			</td>
		  </tr>		
		  <tr>
		    <td align="right"><s:text name="member_label_loginName"/>：</td>
		    <td><s:textfield name="loginName" size="40"/>&nbsp;<span class="redText">*</span></td>
		  </tr>
		  <tr>		    
		    <td align="right"><s:text name="member_label_loginPwd"/>：</td>
		    <td><s:password name="loginPwd" size="16"/>&nbsp;<span class="redText">如果不修改密码,此处可不填</span></td>		    	    
		  </tr>
		  <tr>		    
		    <td align="right"><s:text name="member_label_againPwd"/>：</td>
		    <td><s:password name="againPwd" size="16"/>&nbsp;<span class="redText">如果不修改密码,此处可不填</span></td>		    	    
		  </tr>		  
		  <tr>		    
		    <td align="right"><s:text name="member_label_memberName"/>：</td>
		    <td><s:textfield name="memberName" size="40"/></td>		    	    
		  </tr>
		  <tr>		    
		    <td align="right"><s:text name="member_label_phone"/>：</td>
		    <td><s:textfield name="phone" size="40"/></td>		    	    
		  </tr>
		  <tr>		    
		    <td align="right"><s:text name="member_label_zip"/>：</td>
		    <td><s:textfield name="zip" size="40"/></td>		    	    
		  </tr>
		  <tr>		    
		    <td align="right"><s:text name="member_label_email"/>：</td>
		    <td><s:textfield name="email" size="40"/></td>		    	    
		  </tr>
		  <tr>		    
		    <td align="right"><s:text name="member_label_address"/>：</td>
		    <td><s:textfield name="address" size="40"/></td>		    	    
		  </tr>		  		  		  		  		  
		</table>
		<br>
		  <div align="center">
			<s:submit key="label_submit"/>&nbsp;
			<s:reset key="label_reset"/>&nbsp;
			<s:set name="label_return" value="%{getText('label_return')}"/>
		    <input type="button" name="btn_ret" value="${label_return}" onClick="window.location='member_browseMember.action';">
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
