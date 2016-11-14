<%@ page language="java" import="java.util.*" pageEncoding="gbk"
	contentType="text/html; charset=gbk"%>
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

		<title>收集专利</title>

		<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

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
-->
</style>
		<script type="text/javascript" src="../js/ckeditor/ckeditor.js">
</script>
		<script type="text/javascript">
function initEditor() {
	CKEDITOR.replace('content', {
		skin : 'office2003',
		width : 700
	});
}
</script>
	</head>

	<body onload="initEditor()">

		<div id="formwrapper">

			<h3>
				收集专利数据
			</h3>
			<form action="SpiderServlet" method="post">
				<input type="hidden" name="method" value="collect" />

				<fieldset>
					<legend>
						请输入要收集的专利关键词
					</legend>
					<div>
						<input type="text" name="url" id="url" value="" size="60"
							maxlength="200" />
						<br />
					</div>
					<div class="center">
						<input name="submit" type="submit" class="buttom" value="收集" />
						<input name="reset" type="reset" class="buttom" value="重置" />
					</div>

				</fieldset>
			</form>
		</div>
	</body>
</html>
