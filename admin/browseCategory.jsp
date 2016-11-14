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
<s:set name="label2" value="%{getText('category_label_parent')}" />
<s:set name="label3" value="%{getText('category_label_name')}" />
<s:set name="label4" value="%{getText('label_edit')}" />
<center>
	<div class="titleText"><s:text name="category_manage_title"/></div>
	<div style="margin-bottom:10px;">
		<s:url value="/admin/addCategory.jsp" id="addCategoryUrl">
			<s:param name="tmp"><%=System.currentTimeMillis()%></s:param>
		</s:url>
		<a href="${addCategoryUrl}"><s:text name="category_add"/></a>		
	</div>
	<div class="displayTable">
		<display:table name="categoryList" id="row" pagesize="15" export="true" class="displaytag" requestURI="cate_browseCategory.action" >
			<display:column title="${label1}" sortable="true" headerClass="sortable" style="text-align:center;"> 
				${row_rowNum} 
			</display:column>
			<display:column property="category.cateName" title="${label2}" sortable="true" headerClass="sortable" style="text-align:center;"/>			
			<display:column property="cateName" title="${label3}" sortable="true" headerClass="sortable" style="text-align:center;"/>
			<display:column title="${label4}" media="html" style="text-align:center;">
				<a href="${addCategoryUrl}"><s:text name="label_add"/></a>&nbsp;
				<s:url action="cate_viewCategory" id="viewCategory">
					<s:param name="id" value="%{#attr.row.id}" />
				</s:url>
				<a href="${viewCategory}"><s:text name="label_view"/></a>&nbsp;									
				<s:url action="cate_editCategory" id="modifyCategory">
					<s:param name="id" value="%{#attr.row.id}" />
				</s:url>
				<a href="${modifyCategory}"><s:text name="label_modify"/></a>&nbsp;				
				<s:url action="cate_delCategory" id="delCategory">
					<s:param name="id" value="%{#attr.row.id}" />
				</s:url>
				<a href="${delCategory}"><s:text name="label_delete"/></a>
			</display:column>
			<display:setProperty name="export.csv.filename" value="Category.csv"/>
			<display:setProperty name="export.excel.filename" value="Category.xls"/>
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