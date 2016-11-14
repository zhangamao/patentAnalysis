<%@ page language="java" import="java.util.*" pageEncoding="gbk"
	contentType="text/html; charset=gbk"%>
<%@ page import="com.report.predict.Report" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>报告生成改进版</title>
		<meta content="text/html; charset=gbk" http-equiv="Content-Type">
		<style type="text/css"> 
<!--
body {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	color: #666666;
	background: #fff;
	text-align: center;
}

* {
	margin: 0;
	padding: 0;
}

a {
	color: #1E7ACE;
	text-decoration: none;
}

a:hover {
	color: #000;
	text-decoration: underline;
}

h3 {
	font-size: 14px;
	font-weight: bold;
}

pre,p {
	color: #1E7ACE;
	margin: 4px;
}

input,select,textarea {
	padding: 1px;
	margin: 2px;
	font-size: 12px;
}

.buttom {
	padding: 1px 10px;
	font-size: 12px;
	border: 1px #1E7ACE solid;
	background: #D0F0FF;
}

#formwrapper {
	width: 95%;
	margin: 15px auto;
	padding: 20px;
	text-align: left;
}

fieldset {
	padding: 10px;
	margin-top: 5px;
	border: 1px solid #1E7ACE;
	background: #fff;
}

fieldset legend {
	color: #1E7ACE;
	font-weight: bold;
	background: #fff;
}

fieldset label {
	float: left;
	width: 120px;
	text-align: right;
	padding: 4px;
	margin: 1px;
}

fieldset div {
	clear: left;
	margin-bottom: 2px;
}

.enter {
	text-align: center;
}

.clear {
	clear: both;
}

hr {
	color: red;
	border: 1px solid;
	width: 70%;
}
-->

</style>
<style type="text/css" media="print"></style>
		<script type="text/javascript">

function coverShow() {
	var div = document.getElementById("cover");

	var btnCover = document.getElementById("coverBtn").value;
	if (btnCover == '展开封面') {
		div.style.display = "block";
		document.getElementById("coverBtn").value = '隐藏封面';
	} else if (btnCover == '隐藏封面') {
		div.style.display = "none";
		document.getElementById("coverBtn").value = '展开封面';
	}

}
function bgShow() {
	var btnBg = document.getElementById("bgBtn").value;
	if (btnBg == '背景编辑修改') {
		document.getElementById("bginfo").style.display = "block";
		document.getElementById("bgBtn").value = '隐藏背景编辑修改';
	} else {
		document.getElementById("bginfo").style.display = "none";
		document.getElementById("bgBtn").value = '背景编辑修改';
	}
}
function overallShow(){
	var btnOverall = document.getElementById("overallBtn").value;
	if (btnOverall == '总体编辑修改') {
		document.getElementById("overall").style.display = "block";
		document.getElementById("overallBtn").value = '隐藏总体编辑修改';
	} else {
		document.getElementById("overall").style.display = "none";
		document.getElementById("overallBtn").value = '总体编辑修改';
	}
}
function regionShow(){
	var btnOverall = document.getElementById("regionBtn").value;
	if (btnOverall == '区域编辑修改') {
		document.getElementById("region").style.display = "block";
		document.getElementById("regionBtn").value = '隐藏区域编辑修改';
	} else {
		document.getElementById("region").style.display = "none";
		document.getElementById("regionBtn").value = '区域编辑修改';
	}
}
function ipcShow(){
	var btnIPC = document.getElementById("ipcBtn").value;
	if (btnIPC == 'IPC编辑修改') {
		document.getElementById("ipc").style.display = "block";
		document.getElementById("ipcBtn").value = '隐藏IPC编辑修改';
	} else {
		document.getElementById("ipc").style.display = "none";
		document.getElementById("ipcBtn").value = 'IPC编辑修改';
	}
}
function rivalShow(){
	var btnRival = document.getElementById("rivalBtn").value;
	if (btnRival == '竞争者编辑修改') {
		document.getElementById("rival").style.display = "block";
		document.getElementById("rivalBtn").value = '隐藏竞争者编辑修改';
	} else {
		document.getElementById("rival").style.display = "none";
		document.getElementById("rivalBtn").value = '竞争者编辑修改';
	}
}
function paramShow(){
	var btnParamAndPre = document.getElementById("paramAndPreBtn").value;
	if (btnParamAndPre == '参数编辑查看') {
		document.getElementById("paramAndPredict").style.display = "block";
		document.getElementById("paramAndPreBtn").value = '隐藏参数编辑查看';
	} else {
		document.getElementById("paramAndPredict").style.display = "none";
		document.getElementById("paramAndPreBtn").value = '参数编辑查看';
	}
}
function topicShow(){
	var btnTopic = document.getElementById("topicBtn").value;
	if (btnTopic == '主题编辑查看') {
		document.getElementById("topic").style.display = "block";
		document.getElementById("topicBtn").value = '隐藏主题编辑查看';
	} else {
		document.getElementById("topic").style.display = "none";
		document.getElementById("topicBtn").value = '主题编辑查看';
	}
}
function abstractShow(){
	var btnAbstract = document.getElementById("abstractBtn").value;
	if (btnAbstract == '相关专利内容') {
		document.getElementById("abstract").style.display = "block";
		document.getElementById("abstractBtn").value = '隐藏相关专利内容';
	} else {
		document.getElementById("abstract").style.display = "none";
		document.getElementById("abstractBtn").value = '相关专利内容';
	}
}

  
</script>
	</head>

	<body>
		<!-- 传统专利报告中所包含的内容 ，利用层的显示与隐藏-->
             
		<div id="formwrapper">
			<h2>
				专利检索报告
			</h2>
			<form action="" method="post" enctype="multipart/form-data">
				<div><input id="btnPrint" type="button" value="打        印" onclick="javascript:window.print();" style="margin-left: 1000px"/> </div>
				<fieldset>
					<legend>
						报告封面信息
					</legend>
					<div id="cover" style="display: none">
						<label for="name">
							项目名称：
						</label>
						<input type="text" name="reportName" id="reportName" size="60"
							maxlength="100" />
						*(最多100个字符)
						<br />
						<label>
							检索人：
						</label>
						<input type="text" name="searcher" id="searcher" size="60"
							maxlength="100" />
						*(最多100个字符)
						<br />
						<label>
							审核人：
						</label>
						<input type="text" name="auditor" id="auditor" size="60"
							maxlength="100" />
						*(最多100个字符)
						<br />

						<label>
							检索日期：
						</label>
						<input type="text" name="year" id="year" size="10" />
						年
						<input type="text" name="month" id="month" size="5" />
						月
						<input type="text" name="day" id="day" size="5" />
						日
						<br />
						<label>
							报告日期：
						</label>
						<input type="text" name="year" id="year" size="10" />
						年
						<input type="text" name="month" id="month" size="5" />
						月
						<input type="text" name="day" id="day" size="5" />
						日
						<br />
					</div>

					<input type="button" name="coverBtn" id="coverBtn" value="展开封面"
						onclick="coverShow()" style="margin-left: 800px" />
						<hr/>

				</fieldset>
				<fieldset>
					<legend>
						专利传统定量分析报告
					</legend>
					<div id="bginfo" style="display: none">
						<h3>
							一、课题背景
						</h3>
						<label>
							课题界定
						</label>
						<input type="text" size="150" maxlength="300" name="baseregion"
							id="baseregion" />
						*(最多300个字符)
						<br />
						<label>
							数据范围及检索年限
						</label>
						<input type="text" size="150" maxlength="200" name="time"
							id="time" />
						*(最多200个字符)
						<br />
						<label>
							分析目的
						</label>
						<input type="text" size="150" maxlength="300" name="aim" id="aim" />
						*(最多300个字符)
						<br />

						<label>
							检索关键词
						</label>
						<input type="text" size="150" maxlength="300" name="keyword"
							id="keyword" />
						*(最多300个字符)
						<br />
					</div>
					<input type="button" name="bgBtn" id="bgBtn" value="背景编辑修改"
						onclick="bgShow()" style="margin-left: 800px" />
						<hr/>
					<div id="ptAnalysis">
						<div id="overall" style="display: none">
							<center>
								<h3>
									总体发展趋势
								</h3>
							</center>
							<input type="text" style="font-size: 18px; border: 0"
								disabled="disabled" value="1.申请量年度趋势分析" />
							<br />

							<textarea cols="120" rows="5" id="textarea"
								onfocus="if(value=='年度申请量情况概述'){value=''}"
								onblur="if (value ==''){value='年度申请量情况概述'}"
								style="font-size: 18px">年度申请量情况概述</textarea>
							<br />

							<input type="text" style="font-size: 18px; border: 0"
								disabled="disabled" value="2.公开量年度趋势分析" />
							<br />

							<textarea cols="120" rows="5" id="textarea"
								onfocus="if(value=='公开量年度趋势分析'){value=''}"
								onblur="if (value ==''){value='公开量年度趋势分析'}"
								style="font-size: 18px">公开量年度趋势分析</textarea>
								
								<br />

							<input type="text" style="font-size: 18px; border: 0"
								disabled="disabled" value="3.申请公开量对比" />
							<br />

							<textarea cols="120" rows="5" id="textarea"
								onfocus="if(value=='申请公开量对比'){value=''}"
								onblur="if (value ==''){value='申请公开量对比'}"
								style="font-size: 18px">申请公开量对比</textarea>
								<br />

							<input type="text" style="font-size: 18px; border: 0"
								disabled="disabled" value="4.小结" />
							<br />

							<textarea cols="120" rows="5" id="textarea"
								onfocus="if(value=='小结'){value=''}"
								onblur="if (value ==''){value='小结'}"
								style="font-size: 18px">小结</textarea>				


						</div>
						<input type="button" name="overallBtn" id="overallBtn" value="总体编辑修改"
						onclick="overallShow()" style="margin-left: 800px" />
						<hr/>
						<div id="region" style="display: none;">
							<center>
								<h3>
									专利申请区域分析
								</h3>
							</center>
							<input type="text" style="font-size: 18px; border: 0"
								disabled="disabled" value="1.区域申请构成分析" />
							<br />

							<textarea cols="120" rows="5" id="textarea"
								onfocus="if(value=='区域申请构成分析'){value=''}"
								onblur="if (value ==''){value='区域申请构成分析'}"
								style="font-size: 18px">区域申请构成分析</textarea>
							<br />

							<input type="text" style="font-size: 18px; border: 0"
								disabled="disabled" value="2.区域申请趋势分析" />
							<br />

							<textarea cols="120" rows="5" id="textarea"
								onfocus="if(value=='区域申请趋势分析'){value=''}"
								onblur="if (value ==''){value='区域申请趋势分析'}"
								style="font-size: 18px">区域申请趋势分析</textarea>
								
								<br />

							<input type="text" style="font-size: 18px; border: 0"
								disabled="disabled" value="3.区域申请主要IPC技术构成分析" />
							<br />

							<textarea cols="120" rows="5" id="textarea"
								onfocus="if(value=='区域申请主要IPC技术构成分析'){value=''}"
								onblur="if (value ==''){value='区域申请主要IPC技术构成分析'}"
								style="font-size: 18px">区域申请主要IPC技术构成分析</textarea>
								<br />

							<input type="text" style="font-size: 18px; border: 0"
								disabled="disabled" value="4.区域申请主要竞争者分析" />
							<br />

							<textarea cols="120" rows="5" id="textarea"
								onfocus="if(value=='区域申请主要竞争者分析'){value=''}"
								onblur="if (value ==''){value='区域申请主要竞争者分析'}"
								style="font-size: 18px">区域申请主要竞争者分析</textarea>		
						</div>
						<input type="button" name="regionBtn" id="regionBtn" value="区域编辑修改"
						onclick="regionShow()" style="margin-left: 800px" />
						<hr/>
						
						<div id="ipc" style="display: none;">
							<center>
								<h3>
									主要技术领域分析
								</h3>
							</center>
							<input type="text" style="font-size: 18px; border: 0"
								disabled="disabled" value="1.主要IPC技术构成分析" />
							<br />

							<textarea cols="120" rows="5" id="textarea"
								onfocus="if(value=='主要IPC技术构成分析'){value=''}"
								onblur="if (value ==''){value='主要IPC技术构成分析'}"
								style="font-size: 18px">主要IPC技术构成分析</textarea>
							<br />

							<input type="text" style="font-size: 18px; border: 0"
								disabled="disabled" value="2.主要IPC技术申报趋势分析" />
							<br />

							<textarea cols="120" rows="5" id="textarea"
								onfocus="if(value=='I主要IPC技术申报趋势分析'){value=''}"
								onblur="if (value ==''){value='主要IPC技术申报趋势分析'}"
								style="font-size: 18px">主要IPC技术申报趋势分析</textarea>
								
								<br />

							<input type="text" style="font-size: 18px; border: 0"
								disabled="disabled" value="3.主要IPC技术区域申请对比分析" />
							<br />

							<textarea cols="120" rows="5" id="textarea"
								onfocus="if(value=='主要IPC技术区域申请对比分析'){value=''}"
								onblur="if (value ==''){value='主要IPC技术区域申请对比分析'}"
								style="font-size: 18px">主要IPC技术区域申请对比分析</textarea>
								<br />
							
						</div>
						<input type="button" name="ipcBtn" id="ipcBtn" value="IPC编辑修改"
						onclick="ipcShow()" style="margin-left: 800px" />
						<hr/>
						
						
						
						<div id="rival" style="display: none;">
							<center>
								<h3>
									主要竞争者分析
								</h3>
							</center>
							<input type="text" style="font-size: 18px; border: 0"
								disabled="disabled" value="1.主要竞争者专利份额" />
							<br />

							<textarea cols="120" rows="5" id="textarea"
								onfocus="if(value=='主要竞争者专利份额'){value=''}"
								onblur="if (value ==''){value='主要竞争者专利份额'}"
								style="font-size: 18px">主要竞争者专利份额</textarea>
							<br />

							<input type="text" style="font-size: 18px; border: 0"
								disabled="disabled" value="2.主要竞争者申报趋势分析" />
							<br />

							<textarea cols="120" rows="5" id="textarea"
								onfocus="if(value=='主要竞争者申报趋势分析'){value=''}"
								onblur="if (value ==''){value='主要竞争者申报趋势分析'}"
								style="font-size: 18px">主要竞争者申报趋势分析</textarea>
								
								<br />

							<input type="text" style="font-size: 18px; border: 0"
								disabled="disabled" value="3.主要竞争者区域申请对比分析" />
							<br />

							<textarea cols="120" rows="5" id="textarea"
								onfocus="if(value=='主要竞争者区域申请对比分析'){value=''}"
								onblur="if (value ==''){value='主要竞争者区域申请对比分析'}"
								style="font-size: 18px">主要竞争者区域申请对比分析</textarea>
								<br />
								
								<input type="text" style="font-size: 18px; border: 0"
								disabled="disabled" value="4.主要竞争者IPC技术差异分析" />
							<br />

							<textarea cols="120" rows="5" id="textarea"
								onfocus="if(value=='主要竞争者IPC技术差异分析'){value=''}"
								onblur="if (value ==''){value='主要竞争者IPC技术差异分析'}"
								style="font-size: 18px">主要竞争者IPC技术差异分析</textarea>
								<br />
							
						</div>
						<input type="button" name="rivalBtn" id="rivalBtn" value="竞争者编辑修改"
						onclick="rivalShow()" style="margin-left: 800px" />
					</div>
				</fieldset>


             <fieldset>
             	<legend>专利技术发展参数及其数据预测</legend>
             		<div id="paramAndPredict" style="display: none">
             			<!-- 这里最好是一个表格 用来显示数据 -->
             			<center>
             			<table align="center" border="0" id="paramTb" width="70%" >
             				<tr >
             					<td colspan="4" align="center">专利技术生命周期参数</td>
             				</tr>
             				<tr>
             					<td align="center">技术生长率</td>
             					<td align="center">技术成熟系数</td>
             					<td align="center">技术衰老系数</td>
             					<td align="center">新技术特征系数</td>
             					<td align="center">数据预测值</td>
             				</tr>
             				<%
             					Report rp = new Report();
             					double[] param = rp.TechS();
             				%>
             				<tr>
             				<% for(int i = 0; i < param.length; i++){ %>
             				<td align="center"> <%=param[i] %></td>
             				<%} %>
             				<td align="center"><%=rp.predictValue() %> </td>
             				</tr>
             				
             				
             			</table>
             			</center>
             		</div>
             		<input type="button" name="paramAndPreBtn" id="paramAndPreBtn" onclick="paramShow()" value="参数编辑查看" style="margin-left: 800px"/>
             		<hr/>
             		
             		
             </fieldset>
             
             <fieldset>
             	<legend>专利技术主题分布</legend>
             		<div id="topic" style="display: none;">
             			<%
             				String[] tc = rp.getTopic().split("topic");
             			    for(int i = 0; i < tc.length; i++){
             			%>
             		<p>	<%= tc[i] %></p>
             			<%} %>
             		</div>
             		<input type="button" name="topicBtn" id="topicBtn" onclick="topicShow()" value="主题编辑查看" style="margin-left: 800px"/>
             		<hr/>
             		
             		<div id="abstract" style="display: block;">
             			<%
             				String[] s = rp.getAbstract().split("\\n");
             			for(int i = 0; i < s.length; i++){
             			%>
             			<p> <%=s[i] %> </p>
             			<% }%>
             			
             		</div>
             		<input type="button" name="abstractBtn" id="abstractBtn" onclick="abstractShow()" value="相关专利内容" style="margin-left: 800px"/>
             		<hr/>
             </fieldset>
			</form>
		</div>
	</body>
</html>
