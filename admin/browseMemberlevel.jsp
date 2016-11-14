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
<s:set name="label2" value="%{getText('level_label_name')}" />
<s:set name="label3" value="%{getText('level_label_integral')}" />
<s:set name="label4" value="%{getText('level_label_favourable')}" />
<s:set name="label5" value="%{getText('label_edit')}" />
<center>
	<div class="titleText"><s:text name="level_manage_title"/></div>
	<div style="margin-bottom:10px;">
		<s:url value="/admin/addMemberlevel.jsp" id="addMemberlevelUrl">
			<s:param name="tmp"><%=System.currentTimeMillis()%></s:param>
		</s:url>
		<a href="${addMemberlevelUrl}"><s:text name="level_add"/></a>		
	</div>
	<div class="displayTable">
		<display:table name="memberLevelList" id="row" pagesize="15" export="true" class="displaytag" requestURI="level_browseMemberlevel.action" >
			<display:column title="${label1}" sortable="true" headerClass="sortable" style="text-align:center;"> 
				${row_rowNum} 
			</display:column>
			<display:column property="levelName" title="${label2}" sortable="true" headerClass="sortable" style="text-align:center;"/>
			<display:column property="integral" title="${label3}" sortable="true" headerClass="sortable" style="text-align:center;"/>			
			<display:column property="favourable" title="${label4}" sortable="true" headerClass="sortable" style="text-align:center;"/>			
			<display:column title="${label5}" media="html" style="text-align:center;">
				<a href="${addMemberlevelUrl}"><s:text name="label_add"/></a>&nbsp;
				<s:url action="level_viewMemberlevel" id="viewMemberlevel">
					<s:param name="id" value="%{#attr.row.id}" />
				</s:url>
				<a href="${viewMemberlevel}"><s:text name="label_view"/></a>&nbsp;									
				<s:url action="level_editMemberlevel" id="modifyMemberlevel">
					<s:param name="id" value="%{#attr.row.id}" />
				</s:url>
				<a href="${modifyMemberlevel}"><s:text name="label_modify"/></a>&nbsp;				
				<s:url action="level_delMemberlevel" id="delMemberlevel">
					<s:param name="id" value="%{#attr.row.id}" />
				</s:url>
				<a href="${delMemberlevel}"><s:text name="label_delete"/></a>
			</display:column>
			<display:setProperty name="export.csv.filename" value="Memberlevel.csv"/>
			<display:setProperty name="export.excel.filename" value="Memberlevel.xls"/>
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