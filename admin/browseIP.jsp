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
<s:set name="label2" value="%{getText('traffic_label_ip')}" />
<s:set name="label3" value="%{getText('traffic_label_lastdate')}" />
<s:set name="label4" value="%{getText('traffic_label_times')}" />
<s:set name="label5" value="%{getText('traffic_label_area')}" />
<center>
	<div class="displayTable">
		<display:table name="result" id="row" pagesize="10" export="true" class="displaytag" requestURI="traffic_browseIP.action" >		
			<display:column title="${label1}" sortable="true" headerClass="sortable" style="text-align:center;"> 
				${row_rowNum} 
			</display:column>
			<display:column property="ip" title="${label2}" sortable="true" headerClass="sortable" style="text-align:center;"/>			
			<display:column property="visitDate" title="${label3}" sortable="true" headerClass="sortable" style="text-align:center;"/>
			<display:column property="times" title="${label4}" sortable="true" headerClass="sortable" style="text-align:center;"/>
			<display:column property="area" title="${label5}" sortable="true" headerClass="sortable" style="text-align:center;"/>			
			<display:setProperty name="export.csv.filename" value="ip.csv"/>
			<display:setProperty name="export.excel.filename" value="ip.xls"/>
		</display:table>  		
	</div>
</center>
  </body>
</html>