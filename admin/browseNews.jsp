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
<s:set name="label2" value="%{getText('news_label_columns')}" />
<s:set name="label3" value="%{getText('news_label_title')}" />
<s:set name="label4" value="%{getText('news_label_cdate')}" />
<s:set name="label5" value="%{getText('news_label_editor')}" />
<s:set name="label6" value="%{getText('label_status')}" />
<s:set name="label7" value="%{getText('label_edit')}" />
<center>
	<div class="titleText"><s:text name="news_manage_title"/></div>
	<div style="margin-bottom:10px;">
		<s:url value="/admin/preAddNews.action" id="addNewsUrl">
			<s:param name="tmp"><%=System.currentTimeMillis()%></s:param>
		</s:url>
		<a href="${addNewsUrl}"><s:text name="news_add"/></a>		
	</div>
	<div class="displayTable">
		<display:table name="newsList" id="row" pagesize="15" export="true" class="displaytag" requestURI="news_browseNews.action" >
			<display:column title="${label1}" sortable="true" headerClass="sortable" style="text-align:center;"> 
				${row_rowNum} 
			</display:column>
			<display:column property="newscolumns.columnName" title="${label2}" sortable="true" headerClass="sortable" style="text-align:center;"/>			
			<display:column title="${label3}" sortable="true" headerClass="sortable" style="text-align:center;">
				<s:property value="@com.eportal.util.Tools@cutString(#attr.row.title,40)"/>
			</display:column>
			<display:column title="${label4}" sortable="true" headerClass="sortable" style="text-align:center;">
				<s:property value="@com.eportal.util.Tools@formatDate(#attr.row.cdate)" />
			</display:column>
			<display:column property="editor" title="${label5}" sortable="true" headerClass="sortable" style="text-align:center;"/>
			<display:column title="${label6}" sortable="true" headerClass="sortable" style="text-align:center;">
				<s:if test="#attr.row.status==1">
					<img src="<%=basepath%>/images/published.gif" alt="已发布"/>
				</s:if>
			</display:column>						
			<display:column title="${label7}" media="html" style="text-align:center;">
				<a href="${addNewsUrl}"><s:text name="label_add"/></a>&nbsp;							
				<s:url action="news_editNews" id="modifyNews">
					<s:param name="id" value="%{#attr.row.id}" />
				</s:url>
				<a href="${modifyNews}"><s:text name="label_modify"/></a>&nbsp;				
				<s:url action="news_delNews" id="delNews">
					<s:param name="id" value="%{#attr.row.id}" />
				</s:url>
				<a href="${delNews}"><s:text name="label_delete"/></a>&nbsp;
				<s:url action="viewNews" id="viewNews">
					<s:param name="id" value="%{#attr.row.id}" />
				</s:url>
				<a href="${viewNews}" target="_blank"><s:text name="label_preview"/></a>&nbsp;	
				<s:url action="publisNews" id="pubNews">
					<s:param name="id" value="%{#attr.row.id}" />
				</s:url>
				<a href="${pubNews}"><s:text name="label_publish"/></a>				
			</display:column>
			<display:setProperty name="export.csv.filename" value="News.csv"/>
			<display:setProperty name="export.excel.filename" value="News.xls"/>
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