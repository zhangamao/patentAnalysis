<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ page import="com.report.text.bayes.*" %>
<%@ page import="com.report.predict.Report" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>专利报告页面</title>
    
	 <meta content="text/html;charset=gbk" http-equiv="Content-Type"> 
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
 				
  
  </head>
  
  <body>
  <%
  	String text = request.getParameter("testText");
  String result = "";
	if(text!= null){
		BayesClassifier classifier = new BayesClassifier();
	  	 result = "属于的类别为："+ classifier.classify(text);
	}
	else{
		result = "无法识别其类别";
	}
  %>

  
	<center>
	
		<div  >
			<form action="" method="post" >
				<textarea cols="70" rows="10" id="testText" style="font-size: 18px" name="testText">输入需要判断的文本</textarea>
				<br/>
				<br/>
				<input type="submit"  name="classify" value="判断类别" onclick="">
				
				
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button"  name="report" value="生成报告" onclick="window.open('http://localhost:8080/patentAnalysis/admin/patent_report_generate.jsp')"/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" name="newReport" value="改进版报告生成" onclick="window.open('http://localhost:8080/patentAnalysis/admin/patent_report_improveGenerate.jsp')"/>
				<br/>
				<br/>
			  <textarea rows="10" cols="70" style="font-size: 18px;  " >
			  
			  	<%=result %>
			  </textarea>
			   
			   
			</form>
			 
			    <%--设置一个隐藏层  包含报告内容，点击事件触发，报告显示--%>
		</div>
	</center>    
  </body>
</html>
