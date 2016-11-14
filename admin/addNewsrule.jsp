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
	<br/><div class="titleText"><s:text name="rule_add"/></div>
	<div class="formDiv">
	  <s:form action="rule_addNewsrule">	
		<table width="700" align="center" border="0" cellpadding="0" cellspacing="0">
		  <tr>
		    <td align="right"><s:text name="rule_newscolumns"/>：</td>
		    <td> 			    
		    	<s:select name="newscolumnsid" 
		    			  list="newscolumnsList"
		    			  listKey="id" listValue="columnName" cssStyle="width:120px;"/>				
			</td>
		  </tr>		
		  <tr>
		    <td align="right"><s:text name="rule_rulename"/>：</td>
		    <td><s:textfield name="ruleName" size="80"/>&nbsp;<span class="redText">*</span></td>
		  </tr>
		  <tr>		    
		    <td align="right"><s:text name="rule_url"/>：</td>
		    <td><s:textfield name="url" size="80"/>&nbsp;<span class="redText">*</span></td>		    	    
		  </tr>
		  <tr>		    
		    <td align="right"><s:text name="rule_encode"/>：</td>
		    <td><s:textfield name="encode" size="80"/></td>		    	    
		  </tr>		  
		  <tr>		    
		    <td align="right"><s:text name="rule_listbegin"/>：</td>
		    <td><s:textarea name="listBegin" cols="79" rows="2"/>&nbsp;<span class="redText">*</span></td>		    	    
		  </tr>
		  <tr>		    
		    <td align="right"><s:text name="rule_listend"/>：</td>
		    <td><s:textarea name="listEnd" cols="79" rows="2"/>&nbsp;<span class="redText">*</span></td>		    	    
		  </tr>
		  <tr>		    
		    <td align="right"><s:text name="rule_titlebegin"/>：</td>
		    <td><s:textarea name="titleBegin" cols="79" rows="2"/>&nbsp;<span class="redText">*</span></td>		    	    
		  </tr>	
		  <tr>		    
		    <td align="right"><s:text name="rule_titleend"/>：</td>
		    <td><s:textarea name="titleEnd" cols="79" rows="2"/>&nbsp;<span class="redText">*</span></td>		    	    
		  </tr>
		  <tr>		    
		    <td align="right"><s:text name="rule_authorbegin"/>：</td>
		    <td><s:textarea name="authorBegin" cols="79" rows="2"/></td>		    	    
		  </tr>
		  <tr>		    
		    <td align="right"><s:text name="rule_authorend"/>：</td>
		    <td><s:textarea name="authorEnd" cols="79" rows="2"/></td>		    	    
		  </tr>
		  <tr>		    
		    <td align="right"><s:text name="rule_frombegin"/>：</td>
		    <td><s:textarea name="fromBegin" cols="79" rows="2"/></td>		    	    
		  </tr>
		  <tr>		    
		    <td align="right"><s:text name="rule_fromend"/>：</td>
		    <td><s:textarea name="fromEnd" cols="79" rows="2"/></td>		    	    
		  </tr>
		  <tr>		    
		    <td align="right"><s:text name="rule_contentbegin"/>：</td>
		    <td><s:textarea name="contentBegin" cols="79" rows="2"/>&nbsp;<span class="redText">*</span></td>		    	    
		  </tr>	
		  <tr>		    
		    <td align="right"><s:text name="rule_contentend"/>：</td>
		    <td><s:textarea name="contentEnd" cols="79" rows="2"/>&nbsp;<span class="redText">*</span></td>		    	    
		  </tr>
		  <tr>		    
		    <td align="right"><s:text name="rule_midbegin"/>：</td>
		    <td><s:textarea name="midBegin" cols="79" rows="2"/></td>		    	    
		  </tr>
		  <tr>		    
		    <td align="right"><s:text name="rule_midend"/>：</td>
		    <td><s:textarea name="midEnd" cols="79" rows="2"/></td>		    	    
		  </tr>		  		  			  	  		  		  		  		  
		</table>
		<br>
		  <div align="center">
			<s:submit key="label_submit"/>&nbsp;
			<s:reset key="label_reset"/>&nbsp;
			<s:set name="label_return" value="%{getText('label_return')}"/>
		    <input type="button" name="btn_ret" value="${label_return}" onClick="window.location='rule_browseNewsrule.action';">
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
