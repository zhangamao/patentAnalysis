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
<s:set name="label2" value="%{getText('rule_rulename')}" />
<s:set name="label3" value="%{getText('rule_url')}" />
<s:set name="label4" value="%{getText('rule_newscolumns')}" />
<s:set name="label5" value="%{getText('label_edit')}" />
<center>
	<div class="titleText"><s:text name="rule_manage_title"/></div>
	<div style="margin-bottom:10px;">
		<s:url value="/admin/preAddNewsrule.action" id="addRuleUrl">
			<s:param name="tmp"><%=System.currentTimeMillis()%></s:param>
		</s:url>
		<a href="${addRuleUrl}"><s:text name="rule_add"/></a>		
	</div>
	<div class="displayTable">
		<display:table name="ruleList" id="row" pagesize="15" export="true" class="displaytag" requestURI="rule_browseNewsrule.action" >
			<display:column title="${label1}" sortable="true" headerClass="sortable" style="text-align:center;"> 
				${row_rowNum} 
			</display:column>
			<display:column property="ruleName" title="${label2}" sortable="true" headerClass="sortable" style="text-align:center;"/>
			<display:column title="${label3}" headerClass="sortable" style="text-align:center;">
				<a target="_blank" href="${attr.row.url}">${attr.row.url}</a>
			</display:column>
			<display:column property="newscolumns.columnName" title="${label4}" sortable="true" headerClass="sortable" style="text-align:center;"/>						
			<display:column title="${label5}" media="html" style="text-align:center;">
				<a href="${addRuleUrl}"><s:text name="label_add"/></a>&nbsp;
				<s:url action="rule_viewNewsrule" id="viewNewsrule">
					<s:param name="id" value="%{#attr.row.id}" />
				</s:url>
				<a href="${viewNewsrule}"><s:text name="label_view"/></a>&nbsp;									
				<s:url action="rule_editNewsrule" id="modifyNewsrule">
					<s:param name="id" value="%{#attr.row.id}" />
				</s:url>
				<a href="${modifyNewsrule}"><s:text name="label_modify"/></a>&nbsp;				
				<s:url action="rule_delNewsrule" id="delNewsrule">
					<s:param name="id" value="%{#attr.row.id}" />
				</s:url>
				<a href="${delNewsrule}"><s:text name="label_delete"/></a>&nbsp;
				<s:url action="rule_crawlNewsrule" id="crawlNewsrule">
					<s:param name="id" value="%{#attr.row.id}" />
				</s:url>
				<a href="${crawlNewsrule}"><s:text name="rule_crawl"/></a>				
			</display:column>
			<display:setProperty name="export.csv.filename" value="Newsrule.csv"/>
			<display:setProperty name="export.excel.filename" value="Newsrule.xls"/>
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