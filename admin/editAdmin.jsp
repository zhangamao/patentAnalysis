<%@ page contentType="text/html; charset=gbk"%>
<%@ taglib prefix="e" uri="/patent"%> 
<%@include file="../common/admin_head.jsp"%>
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<title><s:text name="admin_title"/></title>
<link href="<%=basepath%>/css/admin.css" rel="stylesheet" type="text/css" />
<script language="javascript">
	var teipprim ="";
	//构造权限字符串
	function checkPrim(){
		for (var i=1;i<16;i++){
			c = "0";
			tempid = "prim"+i;
			if (document.getElementById(tempid).checked == true)c = "1";
			teipprim = teipprim+c;
		}
		if (teipprim!='000000000000000'){
			document.all.privileges.value = teipprim;		
		}else{
			document.all.privileges.value = "";
		}
	}
	//还原权限列表
	function recoverPrim(temp){
		if (teipprim=='000000000000000'){
			document.all.privileges.value = "";	
			return;	
		}else{
			for (var i=0;i<15;i++){
				if (temp.substring(i,i+1)=="1")document.getElementById("prim"+(i+1)).checked = true;
			}			
		}	
	}
</script>
</head>
<body>
<center>
	<br/><div class="titleText"><s:text name="admin_edit"/></div><br/>
	<div class="formDiv">
	  <s:form action="updateAdmin" onsubmit="checkPrim()">	
		<table width="500" align="center" border="0" cellpadding="0" cellspacing="0">
		  <tr>
		    <td align="right"><s:text name="login_label_loginname"/>：</td>
		    <td><s:textfield name="loginName" size="22"/></td>
		    <td align="right"><s:text name="login_label_password"/>：</td>
		    <td><s:textfield name="loginPwd" size="22"/></td>		    	    
		  </tr>
		  <tr>
		    <td align="right" valign="top"><s:text name="admin_prim"/>：</td>
		    <td valign="top" colspan="3"><table width="420" cellspacing="0" cellpadding="0">
		        <tr>
		          <td width="20"><s:checkbox id="prim1" name="prim1"/></td>
		          <td>1、全权</td>
		          <td width="20"><s:checkbox id="prim2" name="prim2"/></td>
		          <td>2、系统用户管理</td>
		          <td width="20"><s:checkbox id="prim3" name="prim3"/></td>
		          <td>3、新闻栏目管理</td>		          
		        </tr>
		        <tr>
		          <td width="20"><s:checkbox id="prim4" name="prim4"/></td>
		          <td>4、新闻管理</td>
		          <td width="20"><s:checkbox id="prim5" name="prim5"/></td>
		          <td>5、新闻采集</td>
		          <td width="20"><s:checkbox id="prim6" name="prim6"/></td>
		          <td>6、会员管理</td>		          
		        </tr>
		        <tr>
		          <td width="20"><s:checkbox id="prim7" name="prim7"/></td>
		          <td>7、商品分类管理</td>
		          <td width="20"><s:checkbox id="prim8" name="prim8"/></td>
		          <td>8、商品管理</td>
		          <td width="20"><s:checkbox id="prim9" name="prim9"/></td>
		          <td>9、订单管理</td>		          
		        </tr>
		        <tr>
		          <td width="20"><s:checkbox id="prim10" name="prim10"/></td>
		          <td>10、流量统计</td>
		          <td width="20"><s:checkbox id="prim11" name="prim11"/></td>
		          <td>11、其它一</td>
		          <td width="20"><s:checkbox id="prim12" name="prim12"/></td>
		          <td>12、其它二</td>		          
		        </tr>
		        <tr>
		          <td width="20"><s:checkbox id="prim13" name="prim13"/></td>
		          <td>13、其它三</td>
		          <td width="20"><s:checkbox id="prim14" name="prim14"/></td>
		          <td>14、其它四</td>
		          <td width="20"><s:checkbox id="prim15" name="prim15"/></td>
		          <td>15、其它五</td>		          
		        </tr> 		        		        		        		        		        
		      </table></td>
		  </tr>
		</table>
		<br>
		  <div align="center">
			<s:submit key="label_submit"/>&nbsp;
			<s:reset key="label_reset"/>&nbsp;
			<s:set name="label_return" value="%{getText('label_return')}"/>
		    <input type="button" name="btn_ret" value="${label_return}" onClick="window.location='admin_browseAdmin.action';">
		    <s:hidden id="privileges" name="privileges"/>
		    <s:hidden name="id"/>
		  </div>
		</s:form>	
	</div>
</center>
<s:if test="#request.privileges!=null && #request.privileges.length()>0">
	<script language="javascript">
		recoverPrim('${privileges}');
	</script>	
</s:if>
<s:if test="hasFieldErrors()">
	<e:msgdialog basepath="<%=basepath%>">
		<s:fielderror/>
	</e:msgdialog>
</s:if>
<s:if test="hasActionMessages()">
	<e:msgdialog basepath="<%=basepath%>">
		<s:actionmessage/>
	</e:msgdialog>
</s:if>	
</body>
</html>
