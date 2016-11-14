<%@ page contentType="text/html; charset=gbk"%>
<%@ taglib prefix="e" uri="/patent"%> 
<%@include file="../common/admin_head.jsp"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<title><s:text name="admin_title"/></title>
<link href="<%=basepath%>/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body style="padding-top:10px;">
<s:set name="label1" value="%{getText('label_rownum')}" />
<s:set name="label2" value="%{getText('login_label_loginname')}" />
<s:set name="label3" value="%{getText('label_edit')}" />
<center>
	<div class="titleText"><s:text name="admin_user_title"/></div>
	<div style="margin-bottom:10px;">
		<s:url value="/admin/addAdmin.jsp" id="addAdminUrl">
			<s:param name="tmp"><%=System.currentTimeMillis()%></s:param>
		</s:url>
		<a href="${addAdminUrl}"><s:text name="admin_add"/></a>		
	</div>
	<div class="displayTable">
		<display:table name="adminList" id="row" pagesize="15" export="true" class="displaytag" requestURI="admin_browseAdmin.action" >
			<display:column title="${label1}" sortable="true" headerClass="sortable" style="text-align:center;"> 
				${row_rowNum} 
			</display:column>
			<display:column property="loginName" title="${label2}" sortable="true" headerClass="sortable" style="text-align:center;"/>
			<display:column title="${label3}" media="html" style="text-align:center;">
				<a href="${addAdminUrl}"><s:text name="label_add"/></a>&nbsp;
				<s:url action="admin_viewAdmin" id="viewAdmin">
					<s:param name="id" value="%{#attr.row.id}" />
				</s:url>
				<a href="${viewAdmin}"><s:text name="label_view"/></a>&nbsp;									
				<s:url action="admin_editAdmin" id="modifyAdmin">
					<s:param name="id" value="%{#attr.row.id}" />
				</s:url>
				<a href="${modifyAdmin}"><s:text name="label_modify"/></a>&nbsp;				
				<s:url action="admin_delAdmin" id="delAdmin">
					<s:param name="id" value="%{#attr.row.id}" />
				</s:url>
				<a href="${delAdmin}"><s:text name="label_delete"/></a>
			</display:column>
			<display:setProperty name="export.csv.filename" value="Admin.csv"/>
			<display:setProperty name="export.excel.filename" value="Admin.xls"/>
		</display:table>  		
	</div>
</center>
<s:if test="hasActionMessages()">
	<e:msgdialog basepath="<%=basepath%>">
		<s:actionmessage/>
	</e:msgdialog>
</s:if>
  </body>
</html>