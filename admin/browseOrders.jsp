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
<s:set name="label2" value="%{getText('orders_orderno')}" />
<s:set name="label3" value="%{getText('orders_money')}" />
<s:set name="label4" value="%{getText('orders_member')}" />
<s:set name="label5" value="%{getText('orders_orderdate')}" />
<s:set name="label6" value="%{getText('orders_status')}" />
<s:set name="label7" value="%{getText('label_edit')}" />
<center>
	<div class="titleText"><s:text name="orders_manage_title"/></div>
	<div class="displayTable">
		<display:table name="ordersList" id="row" pagesize="15" export="true" class="displaytag" requestURI="orders_browseOrders.action" >
			<display:column title="${label1}" sortable="true" headerClass="sortable" style="text-align:center;"> 
				${row_rowNum} 
			</display:column>
			<display:column property="orderNo" title="${label2}" sortable="true" headerClass="sortable" style="text-align:center;"/>			
			<display:column title="${label3}" sortable="true" headerClass="sortable" style="text-align:center;">
				<s:property value="@com.eportal.util.Tools@formatCcurrency(#attr.row.cart.money)"/>
			</display:column>
			<display:column property="member.memberName" title="${label4}" headerClass="sortable" style="text-align:center;"/>
			<display:column title="${label5}" sortable="true" headerClass="sortable" style="text-align:center;">
				<s:property value="@com.eportal.util.Tools@formatDate(#attr.row.orderDate)"/>
			</display:column>
			<display:column title="${label6}" headerClass="sortable" style="text-align:center;">
				<s:select name="orderStatus" list="#{'1':'已下单,未受理','2':'已受理,未处理','3':'已处理,未结单','4':'已结单'}" listKey="key" listValue="value" value="#attr.row.orderStatus" onchange="window.location='updateOrdersStatus.action?id=%{#attr.row.id}&orderStatus='+this.value;"/>
			</display:column>								
			<display:column title="${label7}" media="html" style="text-align:center;">
				<s:url action="orders_viewOrders" id="viewOrders">
					<s:param name="id" value="%{#attr.row.id}" />
				</s:url>
				<a href="${viewOrders}"><s:text name="label_view"/></a>&nbsp;									
				<s:url action="orders_delOrders" id="delOrders">
					<s:param name="id" value="%{#attr.row.id}" />
				</s:url>
				<a href="${delOrders}"><s:text name="label_delete"/></a>
			</display:column>
			<display:setProperty name="export.csv.filename" value="Orders.csv"/>
			<display:setProperty name="export.excel.filename" value="Orders.xls"/>
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