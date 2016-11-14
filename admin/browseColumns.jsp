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
<s:set name="label2" value="%{getText('columns_label_parent')}" />
<s:set name="label3" value="%{getText('columns_label_name')}" />
<s:set name="label4" value="%{getText('columns_label_code')}" />
<s:set name="label5" value="%{getText('label_edit')}" />
<center>
	<div class="titleText"><s:text name="columns_manage_title"/></div>
	<div style="margin-bottom:10px;">
		<s:url value="/admin/addColumns.jsp" id="addColumnsUrl">
			<s:param name="tmp"><%=System.currentTimeMillis()%></s:param>
		</s:url>
		<a href="${addColumnsUrl}"><s:text name="columns_add"/></a>		
	</div>
	<div class="displayTable">
		<display:table name="columnsList" id="row" pagesize="15" export="true" class="displaytag" requestURI="columns_browseColumns.action" >
			<display:column title="${label1}" sortable="true" headerClass="sortable" style="text-align:center;"> 
				${row_rowNum} 
			</display:column>
			<display:column property="newscolumns.columnName" title="${label2}" sortable="true" headerClass="sortable" style="text-align:center;"/>			
			<display:column property="columnName" title="${label3}" sortable="true" headerClass="sortable" style="text-align:center;"/>
			<display:column property="columnCode" title="${label4}" sortable="true" headerClass="sortable" style="text-align:center;"/>
			<display:column title="${label5}" media="html" style="text-align:center;">
				<a href="${addColumnsUrl}"><s:text name="label_add"/></a>&nbsp;
				<s:url action="columns_viewColumns" id="viewColumns">
					<s:param name="id" value="%{#attr.row.id}" />
				</s:url>
				<a href="${viewColumns}"><s:text name="label_view"/></a>&nbsp;									
				<s:url action="columns_editColumns" id="modifyColumns">
					<s:param name="id" value="%{#attr.row.id}" />
				</s:url>
				<a href="${modifyColumns}"><s:text name="label_modify"/></a>&nbsp;				
				<s:url action="columns_delColumns" id="delColumns">
					<s:param name="id" value="%{#attr.row.id}" />
				</s:url>
				<a href="${delColumns}"><s:text name="label_delete"/></a>
			</display:column>
			<display:setProperty name="export.csv.filename" value="Columns.csv"/>
			<display:setProperty name="export.excel.filename" value="Columns.xls"/>
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