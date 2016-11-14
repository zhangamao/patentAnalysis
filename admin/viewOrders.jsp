<%@ page contentType="text/html; charset=gbk"%>
<%@include file="../common/admin_head.jsp"%>
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<title><s:text name="admin_title"/></title>
<link href="<%=basepath%>/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<center>
	<br/><div class="titleText"><s:text name="orders_baseinfo"/></div><br/>
	<table width="700" align="center" border="0" cellpadding="0" cellspacing="0">
	  <tr height="24">
	    <td align="right" class="blackbold"><s:text name="orders_orderno"/>：</td>
	    <td><s:property value="orderNo"/></td>
	  </tr>
	  <tr height="24">
	    <td align="right" class="blackbold"><s:text name="orders_member"/>：</td>
	    <td><s:property value="member.memberName"/></td>
	  </tr>
	  <tr height="24">
	    <td align="right" class="blackbold"><s:text name="member_label_phone"/>：</td>
	    <td><s:property value="member.phone"/></td>
	  </tr>
	  <tr height="24">
	    <td align="right" class="blackbold"><s:text name="member_label_address"/>：</td>
	    <td><s:property value="member.address"/></td>
	  </tr>
	  <tr height="24">
	    <td align="right" class="blackbold"><s:text name="member_label_zip"/>：</td>
	    <td><s:property value="member.zip"/></td>
	  </tr>
	  <tr height="24">
	    <td align="right" class="blackbold"><s:text name="orders_orderdate"/>：</td>
	    <td><s:property value="@com.eportal.util.Tools@formatDate(#request.orderDate)"/></td>
	  </tr>	
	  <tr height="24">
	    <td align="right" class="blackbold"><s:text name="orders_status"/>：</td>
	    <td>
			<s:if test="orderStatus==1"><s:text name="orders_status1"/></s:if>
			<s:elseif test="orderStatus==2"><s:text name="orders_status2"/></s:elseif>
			<s:elseif test="orderStatus==3"><s:text name="orders_status3"/></s:elseif>
			<s:elseif test="orderStatus==4"><s:text name="orders_status4"/></s:elseif>				    
	    </td>
	  </tr>
	  <tr height="24">
		<td align="right" class="blackbold"><s:text name="orders_money"/>：</td>
		<td><s:property value="@com.eportal.util.Tools@formatCcurrency(#request.cart.money)"/></td>							
	  </tr>	  
	</table>	
	<br/><div class="titleText"><s:text name="orders_merlist"/></div><br/>
	<table cellspacing="1" cellpadding="0" width="90%" border="0" bgcolor="#FFFFFF" style="border: 1px solid #848284;">
	  <tr height="24" class="blackbold" align="center" bgcolor="#D6D3CE" class="blackbold">
		<td><s:text name="mer_label_name"/></td>
		<td><s:text name="mer_label_price"/></td>
		<td><s:text name="orders_buy_price"/></td>
		<td><s:text name="orders_number"/></td>
		<td><s:text name="orders_buy_money"/></td>
	  </tr>
	  <s:iterator value="selList" id="row" status="st">
	  	<s:if test="#st.odd"><!-- 奇数行,背景为白色 -->
	  		<tr class="text" align="center" bgcolor="#FFFFFF" height="24">
	  	</s:if>
	  	<s:else><!-- 偶数行,背景为浅灰色 -->
	  		<tr class="text" align="center" bgcolor="#EEEEEE" height="24">
	  	</s:else>
			<td>
				&nbsp;<a href="<%=basepath%>${row.merchandise.htmlPath}" target="_blank"> 
				  <span class="blueText">${row.merchandise.merName}</span>
				</a>							</td>
			<td><s:property value="@com.eportal.util.Tools@formatCcurrency(#row.merchandise.price)"/></td>
			<td><s:property value="@com.eportal.util.Tools@formatCcurrency(#row.price)"/></td>
			<td><s:property value="#row.number"/></td>
			<td><s:property value="@com.eportal.util.Tools@formatCcurrency(#row.money)"/></td>
		  </tr>						  	
	  </s:iterator>		  						  						  
	</table><br/><br/>	
	<s:set name="label_return" value="%{getText('label_return')}"/>
    <input type="button" name="btn_ret" value="${label_return}" onClick="window.location='orders_browseOrders.action';">	
</center>
</body>
</html>
