<%@ page contentType="text/html; charset=gbk"%>
<%@ taglib uri="/WEB-INF/FCKeditor.tld" prefix="fck"%>
<%@ taglib prefix="e" uri="/patent"%> 
<%@include file="../common/admin_head.jsp"%>
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<title><s:text name="admin_title"/></title>
<link href="<%=basepath%>/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<center>
	<div class="titleText"><s:text name="news_add"/></div>
	<div class="formDiv">
	  <s:form action="news_addNews" enctype="multipart/form-data">
		<table width="100%" align="center" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <td>新闻标题:</td>
		    <td colspan="3"><s:textfield name="title" size="88"/></td>
		    <td>点&nbsp;击&nbsp;数:</td>
		    <td>
		    	<s:if test="clicks!=null"><s:textfield name="clicks" size="8"/></s:if>
		    	<s:else><s:textfield name="clicks" size="8" value="0"/></s:else>		    	
		    </td>		    
		  </tr>		
		  <tr>
		    <td width="60">所属栏目:</td>
		    <td class="doubselect">
				<s:doubleselect list="doubleSelectNodes" listKey="value" listValue="name" doubleList="subNodes"  doubleListKey="value" doubleListValue="name" doubleName="column2" doubleId="column2" name="column1" id="column1" value="column1" doubleValue="column2"/>
		    </td>
		    <td width="60">新闻类型:</td>
		    <td>
		    	<s:select name="newsType" 
		    			  list="#{'0':'最新新闻','1':'头条新闻','2':'热点新闻','3':'精彩推荐'}"
		    			  listKey="key" listValue="value" cssStyle="width:120px;" value="newsType"/>
		    </td>
		    <td width="60">优&nbsp;先&nbsp;值:</td>
		    <td>
		    	<s:if test="priority!=null"><s:textfield name="priority" size="8"/></s:if>
		    	<s:else><s:textfield name="priority" size="8" value="0"/></s:else>		    
		    </td>
		  </tr>
		  <tr>
		    <td>来&nbsp;&nbsp;&nbsp;&nbsp;源:</td>
		    <td>
		    	<s:if test="from!=null"><s:textfield name="from" size="44"/></s:if>
		    	<s:else><s:textfield name="from" size="44" value="原创"/></s:else>		    	
		    </td>
		    <td>作&nbsp;&nbsp;&nbsp;&nbsp;者:</td>
		    <td>
		    	<s:if test="author!=null"><s:textfield name="author" size="18"/></s:if>
		    	<s:else><s:textfield name="author" size="18" value="不详"/></s:else>
		    </td>
		    <td>是否描红:</td>
		    <td>
		    	<s:if test="red!=null"><s:radio name="red" list="#{'0':'否','1':'是'}" listKey="key" listValue="value" value="red"/></s:if>
		    	<s:else><s:radio name="red" list="#{'0':'否','1':'是'}" listKey="key" listValue="value" value="0"/></s:else>			    
		    </td>
		  </tr>
		  <tr>
		    <td>关&nbsp;键&nbsp;词:</td>
		    <td colspan="5"><s:textfield name="keyWords" size="112"/></td>
		  </tr>
		  <tr>
		    <td>图片新闻:</td>
		    <td colspan="5">
		    	<s:if test="isPicNews!=null">
			    	<s:radio name="isPicNews" list="#{'0':'否','1':'是'}" listKey="key" listValue="value" value="isPicNews" onclick="hideShowFile(this.value)"/>&nbsp;
			    	<s:if test="isPicNews==1">
				    	<span id="fileSpan" style="display:inline;">
				    		<s:file name="pic" size="88"/>
				    	</span>
			    	</s:if>
			    	<s:else>
				    	<span id="fileSpan" style="display:none;">
				    		<s:file name="pic" size="88"/>
				    	</span>			    	
			    	</s:else>		    	
		    	</s:if>
		    	<s:else>
		    		<s:radio name="isPicNews" list="#{'0':'否','1':'是'}" listKey="key" listValue="value" value="0" onclick="hideShowFile(this.value)"/>
			    	<span id="fileSpan" style="display:none;">
			    		<s:file name="pic" size="88"/>
			    	</span>			    		
		    	</s:else>		    
		    </td>
		  </tr>
		  <tr>
		    <td valign="top">新闻摘要:</td>
		    <td colspan="5"><s:textarea name="abstract_" rows="3" cols="112"/></td>
		  </tr>
		  <tr>
		    <td valign="top">新闻内容:</td>
		    <td colspan="5" height="500">
				<s:if test="content!=null">
					<s:set name="contentvalue" value="@com.eportal.util.Tools@unescape(#request.content)"/>
				</s:if>			    
				<fck:editor id="content" basePath="../" height="500"
					    	skinPath="../editor/skins/silver/"
					    	toolbarSet="Default"
							imageBrowserURL="../editor/filemanager/browser/default/browser.html?Type=Image&Connector=connectors/jsp/connector"
							linkBrowserURL="../editor/filemanager/browser/default/browser.html?Connector=connectors/jsp/connector"
							flashBrowserURL="../editor/filemanager/browser/default/browser.html?Type=Flash&Connector=connectors/jsp/connector"
					    	imageUploadURL="../editor/filemanager/upload/simpleuploader?Type=Image"
							linkUploadURL="../editor/filemanager/upload/simpleuploader?Type=File"
							flashUploadURL="../editor/filemanager/upload/simpleuploader?Type=Flash">								    
					${contentvalue}
				</fck:editor>			
			</td>
		  </tr>
		</table>
		<br>
		  <div align="center">
			<s:submit key="label_submit"/>&nbsp;
			<s:reset key="label_reset"/>&nbsp;
			<s:set name="label_return" value="%{getText('label_return')}"/>
		    <input type="button" name="btn_ret" value="${label_return}" onClick="window.location='news_browseNews.action';">
		  </div>
		</s:form>	
	</div>
</center>
<br/><br/>
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
<script language="javascript">
	//显示与隐藏图片上传区域
	function hideShowFile(v){
		if (v==1){
			document.all.fileSpan.style.display="inline";
		}else{
			document.all.fileSpan.style.display="none";
		}
	}
</script>	
</body>
</html>
