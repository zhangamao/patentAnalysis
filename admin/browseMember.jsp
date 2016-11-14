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
<s:set name="label2" value="%{getText('member_label_loginName')}" />
<s:set name="label3" value="%{getText('member_label_memberName')}" />
<s:set name="label4" value="%{getText('member_integral')}" />
<s:set name="label5" value="%{getText('level_label_name')}" />
<s:set name="label6" value="%{getText('label_edit')}" />
<center>
	<div class="titleText"><s:text name="member_manage_title"/></div>
	<div style="margin-bottom:10px;">
		<s:url value="/admin/preAddMember.action" id="addMemberUrl">
			<s:param name="tmp"><%=System.currentTimeMillis()%></s:param>
		</s:url>
		<a href="${addMemberUrl}"><s:text name="member_add"/></a>		
	</div>
	<div class="displayTable">
		<display:table name="memberList" id="row" pagesize="15" export="true" class="displaytag" requestURI="member_browseMember.action" >
			<display:column title="${label1}" sortable="true" headerClass="sortable" style="text-align:center;"> 
				${row_rowNum} 
			</display:column>
			<display:column property="loginName" title="${label2}" sortable="true" headerClass="sortable" style="text-align:center;"/>
			<display:column property="memberName" title="${label3}" sortable="true" headerClass="sortable" style="text-align:center;"/>			
			<display:column property="integral" title="${label4}" sortable="true" headerClass="sortable" style="text-align:center;"/>
			<display:column property="memberlevel.levelName" title="${label5}" sortable="true" headerClass="sortable" style="text-align:center;"/>						
			<display:column title="${label6}" media="html" style="text-align:center;">
				<a href="${addMemberUrl}"><s:text name="label_add"/></a>&nbsp;
				<s:url action="member_viewMember" id="viewMember">
					<s:param name="id" value="%{#attr.row.id}" />
				</s:url>
				<a href="${viewMember}"><s:text name="label_view"/></a>&nbsp;									
				<s:url action="member_editMember" id="modifyMember">
					<s:param name="id" value="%{#attr.row.id}" />
				</s:url>
				<a href="${modifyMember}"><s:text name="label_modify"/></a>&nbsp;				
				<s:url action="member_delMember" id="delMember">
					<s:param name="id" value="%{#attr.row.id}" />
				</s:url>
				<a href="${delMember}"><s:text name="label_delete"/></a>
			</display:column>
			<display:setProperty name="export.csv.filename" value="Member.csv"/>
			<display:setProperty name="export.excel.filename" value="Member.xls"/>
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