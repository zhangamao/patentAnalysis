<%@ page contentType="text/html; charset=gbk"%>
<%@include file="../common/admin_head.jsp"%>
<html>
<head>
<title><s:text name="admin_title"/></title>
<s:head theme="ajax" debug="true"/>
<link href="<%=basepath%>/css/admin.css" rel="stylesheet" type="text/css" />
 <script language="JavaScript" type="text/javascript">
 	var url = "#";
 	var root;
 	
	//响应菜单单击事件
	function treeNodeSelected(arg) {
		var node = dojo.widget.byId(arg.source.widgetId);
		if(node.isFolder){
			if(dojo.widget.byId(arg.source.widgetId).isExpanded){
				dojo.widget.byId(arg.source.widgetId).collapse();
			}else{
				dojo.widget.byId(arg.source.widgetId).expand();
			}		
		}else{
			processSelected(arg.source.widgetId);
		}
	}
	
	//处理菜单导航
	function processSelected(menuid){
		var tmp = (new Date()).getTime();
		if (menuid=='column'){
			url = "columns_browseColumns.action";
		}else if (menuid=='news'){
			url = "news_browseNews.action";
		}else if (menuid=='crawl'){
			url = "rule_browseNewsrule.action";
		}else if (menuid=='memeberLevel'){
			url = "level_browseMemberlevel.action";
		}else if (menuid=='memeber'){
			url = "member_browseMember.action";
		}else if (menuid=='cate'){
			url = "cate_browseCategory.action";
		}else if (menuid=='mer'){
			url = "mer_browseMerchandise.action";
		}else if (menuid=='order'){
			url = "orders_browseOrders.action";		
		}else if (menuid=='ip'){
			url = "browseIP_index.jsp";		
		}else if (menuid=='pv'){
			url = "browsePV_index.jsp";	
		}else if (menuid=='admin'){
			url = "admin_browseAdmin.action";	
		}else if (menuid=='exit'){
			url = "admin_logout.action";	
		}else if(menuid == 'patent'){
			url = "patent_analyse.action";
		}else if(menuid == 'patent_collection'){
			url = "/patent_Crawl.jsp";
		}else if(menuid=='patent_spider'){
			url = "/patent_spider.jsp";
		}
		else if(menuid == 'patent_search'){
			url = "/patentSearch.jsp";
		}
		else if(menuid == 'patent_year'){
			url = "/patent_yearApply.jsp";
		}
		else if(menuid == 'patent_area'){
			url = "/patent_area.jsp";
		}else if(menuid == 'patent_applicant'){
			url = "/patent_applicant.jsp";
		}else if(menuid == 'patent_inventor'){
			url = "/patent_inventor.jsp";
		}else if(menuid == 'patent_tech'){
			url = "/patent_tech.jsp";
		}else if(menuid == 'patent_theme'){
			url = "/patent_theme.jsp";
		}
		else if(menuid == 'patent_report'){
			url = "/patent_report.jsp";
		}
		else if(menuid == 'whole_apply'){
			url = "/whole_apply.jsp";
		}else if(menuid == 'whole_public'){
			url = "/whole_public.jsp";
		}else if(menuid == 'whole_compare'){
			url = "/whole_compare.jsp";
		}else if(menuid == 'whole_summary'){
			url = "/whole_summary.jsp";
		}
		
		else if(menuid == 'ipc_content'){
			url = "/ipc_content.jsp";
		}else if(menuid == 'ipc_apply'){
			url = "/ipc_apply.jsp";
		}else if(menuid == 'ipc_area'){
			url = "/ipc_area.jsp";
		}
		else if(menuid == 'area_apply'){
			url = "/area_apply.jsp";
		}else if(menuid == 'area_ipc'){
			url = "/area_ipc.jsp";
		}else if(menuid == 'area_competitor'){
			url = "/area_competitor.jsp";
		}
		else if(menuid == 'competitor_analysis'){
			url = "/competitor_analysis.jsp";
		}else if(menuid == 'competitor_apply'){
			url = "/competitor_apply.jsp";
		}
		
		
		//添加临时参数,标识这是一次全新的请求
		url = "<%=basepath%>/admin/"+url+"?tmp="+tmp;
		if (menuid=='exit'){
			window.parent.location=url;
		}else{
			window.parent.mainFrame.location=url;
		}		
	}
	
	//响应菜单展开事件
	function treeNodeExpanded(arg) {
	    alert('id['+arg.source.widgetId+'], name['+ arg.source.title+ '] expanded');
	}
	
	//响应菜单收缩事件
	function treeNodeCollapsed(arg) {
	    alert('id['+arg.source.widgetId+'], name['+ arg.source.title+ '] collapsed');
	}
	
	//注册菜单事件处理
	dojo.addOnLoad(function(){
	    root = dojo.widget.byId('adminctrl');
	    dojo.event.topic.subscribe(root.eventNames.titleClick, treeNodeSelected);
	});
	
	//展开所有菜单项
	function expandAll(){
       for(var i=0; i<root.children.length; i++) {
          var child = root.children[i];
          dojo.lang.forEach(child.getDescendants(),function(node) {node.expand(); });
       }
	}
</script>
</head>
<body style="padding:18px;">
<s:tree label="<b>ePortal后台管理</b>" id="adminctrl" theme="ajax" showRootGrid="true" showGrid="true">
    <s:treenode theme="ajax" label="<img src='../images/icon_newscolumn.gif'/>新闻管理" id="news_column">
        <s:treenode theme="ajax" label="<img src='../images/icon_column.gif'/>新闻栏目管理" id="column"/>
        <s:treenode theme="ajax" label="<img src='../images/icon_news.gif'/>新闻管理" id="news"/>
    </s:treenode>
    <s:treenode theme="ajax" label="<img src='../images/icon_crawl.gif'/>新闻采集" id="crawl"/>
    <s:treenode theme="ajax" label="<img src='../images/icon_member.gif'/>会员管理" id="memeber_level">
    	<s:treenode theme="ajax" label="<img src='../images/icon_level.gif'/>会员级别管理" id="memeberLevel"/>
    	<s:treenode theme="ajax" label="<img src='../images/icon_member.gif'/>会员管理" id="memeber"/>    
    </s:treenode>
    <!-- 
    <s:treenode theme="ajax" label="<img src='../images/icon_catemer.gif'/>商品管理" id="cate_mer">
        <s:treenode theme="ajax" label="<img src='../images/icon_cate.gif'/>商品分类" id="cate"/>
        <s:treenode theme="ajax" label="<img src='../images/icon_mer.gif'/>商品管理" id="mer"/>
    </s:treenode>
     -->
     <s:treenode theme="ajax" label="<img src='../images/icon_catemer.gif'/>专利数据分析" id="patent">
     	<s:treenode theme="ajax" label="<img src='../images/icon_cate.gif'/>专利定量分析" id="">
     	 <s:treenode theme="ajax" label="<img src='../images/icon_mer.gif'/>时间趋势" id="patent_year" />
     		<s:treenode theme="ajax" label="<img src='../images/icon_mer.gif'/>区域分布分析" id="patent_area"/>
     		<s:treenode theme="ajax" label="<img src='../images/icon_mer.gif'/>申请人" id="patent_applicant"/>
     		<s:treenode theme="ajax" label="<img src='../images/icon_mer.gif'/>发明人" id="patent_inventor"/>
     		<s:treenode theme="ajax" label="<img src='../images/icon_mer.gif'/>技术分类" id="patent_tech"/> 
     		
     	<!-- 总体趋势分析 -->	
     	<s:treenode theme="ajax" label="<img src='../images/icon_statistis.jpg'/>总体趋势分析" id="">
     		<s:treenode theme="ajax" label="<img src='../images/icon_zhexian.gif'/>申请量年度趋势分析" id="whole_apply"/>
     		<s:treenode theme="ajax" label="<img src='../images/icon_zhexian.gif'/>公开量年度趋势分析" id="whole_public"/>
     		<s:treenode theme="ajax" label="<img src='../images/icon_zhexian.gif'/>申请公开量对比" id="whole_compare"/>
     		<s:treenode theme="ajax" label="<img src='../images/icon_zhexian.gif'/>小结" id="whole_summary"/>
     	     	
     	</s:treenode>	
     		
     		
     	<!-- 区域编辑修改 -->
     	<s:treenode theme="ajax" label="<img src='../images/icon_statistis.jpg'/>专利申请区域分析" id="">
     		<s:treenode theme="ajax" label="<img src='../images/icon_zhexian.gif'/>区域申请构成分析" id="area_apply"/>
     		
     		<s:treenode theme="ajax" label="<img src='../images/icon_zhexian.gif'/>区域申请主要IPC技术构成" id="area_ipc"/>
     		<s:treenode theme="ajax" label="<img src='../images/icon_zhexian.gif'/>区域申请主要竞争者分析" id="area_competitor"/>     	     	
     	</s:treenode>	
     	
     	
     	<!-- 主要技术领域分析 -->
     	<s:treenode theme="ajax" label="<img src='../images/icon_statistis.jpg'/>主要技术领域分析" id="">
     		<s:treenode theme="ajax" label="<img src='../images/icon_zhexian.gif'/>主要IPC技术构成分析" id="ipc_content"/>
     		<s:treenode theme="ajax" label="<img src='../images/icon_zhexian.gif'/>主要IPC技术申报趋势分析" id="ipc_apply"/>
     		<s:treenode theme="ajax" label="<img src='../images/icon_zhexian.gif'/>主要IPC技术区域申请对比" id="ipc_area"/>      	     	
     	</s:treenode>	
     	
     	<!-- 主要竞争者分析 -->
     	<s:treenode theme="ajax" label="<img src='../images/icon_statistis.jpg'/>主要竞争者分析" id="">
     		<s:treenode theme="ajax" label="<img src='../images/icon_zhexian.gif'/>主要竞争者专利份额" id="competitor_analysis"/>
     		<s:treenode theme="ajax" label="<img src='../images/icon_zhexian.gif'/>主要竞争者申报趋势分析" id="competitor_apply"/>
     		<s:treenode theme="ajax" label="<img src='../images/icon_zhexian.gif'/>主要竞争者区域申请对比分析" id="competitor_area"/>
     		<s:treenode theme="ajax" label="<img src='../images/icon_zhexian.gif'/>主要竞争者IPC技术差异分析" id="competitor_ipc"/>     	     	
     	</s:treenode>	
     		
     	</s:treenode>
     	
     	<!-- 专利数据信息 收集及管理 -->
        <s:treenode theme="ajax" label="<img src='../images/icon_newscolumn.gif'/>专利数据收集" id="patent_coll" >
        	<s:treenode theme="ajax"   label="<img src='../images/icon_column.gif'/> 爬取专利" id="patent_spider" />
        	<s:treenode theme="ajax"   label="<img src='../images/icon_column.gif'/>专利信息管理" id="patent_collection"/>
        </s:treenode>
        <s:treenode theme="ajax" label="<img src='../images/icon_cate.gif'/>专利全文检索" id="patent_search"/>
      
        <%--利用云标签显示生成的主题树 --%>
        <s:treenode theme="ajax" label="<img src='../images/icon_cate.gif'/>专利主题分类树" id="patent_theme" />
        <s:treenode theme="ajax" label="<img src='../images/icon_cate.gif'/>相关专利报告生成" id="patent_report"/>
       
     </s:treenode>
    <s:treenode theme="ajax" label="<img src='../images/icon_order.gif'/>订单管理" id="order"/>
    <s:treenode theme="ajax" label="<img src='../images/icon_trafic.gif'/>流量统计" id="ip_pv">
        <s:treenode theme="ajax" label="<img src='../images/icon_ip.gif'/>IP统计" id="ip"/>
        <s:treenode theme="ajax" label="<img src='../images/icon_pv.gif'/>PV统计" id="pv"/>
    </s:treenode>    
    <s:treenode theme="ajax" label="<img src='../images/icon_admin.gif'/>系统用户管理" id="admin"/>
    <s:treenode theme="ajax" label="<img src='../images/icon_exit.gif'/>安全退出" id="exit"/>    
</s:tree>
<br/>
</body>
<script type="text/javascript">
	//展开所有菜单项
	window.setTimeout("expandAll();",2000);
</script>
</html>