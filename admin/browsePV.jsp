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
<s:set name="label3" value="%{getText('traffic_label_from')}" />
<s:set name="label4" value="%{getText('traffic_label_target')}" />
<s:set name="label5" value="%{getText('traffic_label_date')}" />
<s:set name="label6" value="%{getText('traffic_label_area')}" />
<center>
	<div class="displayTable">
		<display:table name="result" id="row" pagesize="10" export="true" class="displaytag" requestURI="traffic_browsePV.action" >
			<display:column title="${label1}" sortable="true" headerClass="sortable" style="text-align:center;"> 
				${row_rowNum} 
			</display:column>
			<display:column property="ip" title="${label2}" sortable="true" headerClass="sortable" style="text-align:center;"/>			
			<display:column title="${label3}/${label4}" headerClass="sortable" style="text-align:left;line-height:20px;">
				<s:if test="#attr.row.sourceUrl!=null && !(#attr.row.sourceUrl.startsWith('直接进入'))">
					${label3}:<a href="${attr.row.sourceUrl}"><s:property value="#attr.row.sourceUrl"/></a><br/>
				</s:if>
				<s:else>
					${label3}:<s:property value="#attr.row.sourceUrl"/><br/>
				</s:else>				
				<s:if test="#attr.row.targetUrl!=null&&#attr.row.targetUrl.length()>0">
					${label4}:<a href="${attr.row.targetUrl}"><s:property value="#attr.row.targetUrl"/></a>
				</s:if>
				<s:else>
					${label4}:
				</s:else>			
			</display:column>
			<display:column property="visitDate" title="${label5}" sortable="true" headerClass="sortable" style="text-align:center;"/>			
			<display:column property="area" title="${label6}" sortable="true" headerClass="sortable" style="text-align:center;"/>
			<display:setProperty name="export.csv.filename" value="pv.csv"/>
			<display:setProperty name="export.excel.filename" value="pv.xls"/>
		</display:table>  		
	</div>
</center>
  </body>
</html>