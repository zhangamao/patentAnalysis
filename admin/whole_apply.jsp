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

		<title></title>


	</head>

	<body>
		<center>
			<img src="<%=request.getContextPath()%>/applyNumberYearLine" />
			<br />
		</center>
		
		<div id="explainApplyNumber">
			<h3>书写样例</h3>
			<p>
			自行车领域技术发展自1985年以来，我国实行专利制度以来一直持续发展，至90年代初因市场经济的发展，
			人民生活水平的得到提高导致自行车开始普及，同时由于经济发展局限小轿车还未得到广泛应用于普通生活，
			此期间处于自行车发展的高峰，其后90年代中至20世纪初因人们生活水平得到提高，经济较为富裕，
			故自行车发展较缓，小轿车发展迅速；
			至2006年开始因世界环保理念的快速普及和世界油价的持续上扬人们开始关注自行车这种既环保又节能的出行方式。
			但由于自行车技术发展技术已较完善故专利技术相对申请较少。
			但从整体专利趋势分析自行车在今后一段时间还将继续得到发展尤其是涉及节能环保类技术突破将为自行车行业提供新的发展动力
			</p>
		</div>
		
	</body>
</html>
