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
<s:set name="label2" value="%{getText('mer_label_cate')}" />
<s:set name="label3" value="%{getText('mer_label_name')}" />
<s:set name="label4" value="%{getText('mer_label_model')}" />
<s:set name="label5" value="%{getText('mer_label_price')}" />
<s:set name="label6" value="%{getText('mer_label_sprice')}" />
<s:set name="label7" value="%{getText('label_status')}" />
<s:set name="label8" value="%{getText('label_edit')}" />
<center>
	<div class="titleText"><s:text name="mer_manage_title"/></div>
	<div style="margin-bottom:10px;">
		<s:url value="/admin/preAddMerchandise.action" id="addMerchandiseUrl">
			<s:param name="tmp"><%=System.currentTimeMillis()%></s:param>
		</s:url>
		<a href="${addMerchandiseUrl}"><s:text name="mer_add"/></a>
	</div>
	<div class="displayTable">
		<display:table name="merList" id="row" pagesize="15" export="true" class="displaytag" requestURI="mer_browseMerchandise.action" >
			<display:column title="${label1}" sortable="true" headerClass="sortable" style="text-align:center;"> 
				${row_rowNum} 
			</display:column>
			<display:column property="category.cateName" title="${label2}" sortable="true" headerClass="sortable" style="text-align:center;"/>			
			<display:column property="merName" title="${label3}" sortable="true" headerClass="sortable" style="text-align:center;"/>
			<display:column property="merModel" title="${label4}" sortable="true" headerClass="sortable" style="text-align:center;"/>
			<display:column property="price" title="${label5}" sortable="true" headerClass="sortable" style="text-align:center;"/>
			<display:column title="${label6}" sortable="true" headerClass="sortable" style="text-align:center;">
				<s:if test="#attr.row.special==1">
					<s:property value="#attr.row.sprice"/>
				</s:if>
			</display:column>						
			<display:column title="${label7}" sortable="true" headerClass="sortable" style="text-align:center;">
				<s:if test="#attr.row.status==1">
					<img src="<%=basepath%>/images/published.gif" alt="已发布"/>
				</s:if>
			</display:column>			
			<display:column title="${label8}" media="html" style="text-align:center;">
				<a href="${addMerchandiseUrl}"><s:text name="label_add"/></a>&nbsp;
				<s:url action="mer_editMerchandise" id="modifyMerchandise">
					<s:param name="id" value="%{#attr.row.id}" />
				</s:url>
				<a href="${modifyMerchandise}"><s:text name="label_modify"/></a>&nbsp;				
				<s:url action="mer_delMerchandise" id="delMerchandise">
					<s:param name="id" value="%{#attr.row.id}" />
				</s:url>
				<a href="${delMerchandise}"><s:text name="label_delete"/></a>&nbsp;
				<s:url action="viewMerchandise" id="viewMerchandise">
					<s:param name="id" value="%{#attr.row.id}" />
				</s:url>
				<a href="${viewMerchandise}" target="_blank"><s:text name="label_preview"/></a>&nbsp;					
				<s:url action="publisMerchandise" id="pubMerchandise">
					<s:param name="id" value="%{#attr.row.id}" />
				</s:url>
				<a href="${pubMerchandise}"><s:text name="label_publish"/></a>				
			</display:column>
			<display:setProperty name="export.csv.filename" value="Merchandise.csv"/>
			<display:setProperty name="export.excel.filename" value="Merchandise.xls"/>
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