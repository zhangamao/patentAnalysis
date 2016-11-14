package com.patent.struts.action;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.Pattern;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.apache.struts2.ServletActionContext;
import com.patent.DAO.BaseDAO;
import com.patent.DAO.BaseDAOImpl;
import com.patent.ORM.*;
import com.patent.service.*;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.patent.util.*;

/** 新闻采集规则处理控制器 */
@SuppressWarnings("serial")
public class CrawlAction extends ActionSupport implements ModelDriven<Newsrule>{
	/** 通过依赖注入CrawlService和ColumnsService组件实例 */
	CrawlService service;
	ColumnsService columnsService;
	
	/** 新闻采集规则管理所有请求中常用的参数值 */
	private String actionMsg;	//Action间传递的消息参数
	private List<Newsrule> ruleList;//采集规则列表
	private List<Newscolumns> newscolumnsList;//新闻栏目列表	
	private String newscolumnsid=null;//所属新闻栏目ID
	
	//采用模型驱动
	private Newsrule model=new Newsrule();//用于封装采集规则属性模型
	public Newsrule getModel() {
		return model;
	}
	
	/** 处理浏览新闻采集规则请求 */
	public String browseNewsrule(){
		if(actionMsg!=null){
			try {
				actionMsg = new String(actionMsg.getBytes("ISO8859-1"),"gbk");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			addActionMessage(actionMsg);
		}
		ruleList = service.browseNewsrule();//调用业务逻辑组件取得采集规则列表
		return SUCCESS;
	}
	
	/** 处理新增新闻采集规则请求 */
	public String addNewsrule(){
		Newscolumns newscolumns;
		if(newscolumnsid!=null){
			newscolumns = columnsService.loadColumns(Integer.parseInt(newscolumnsid));
			if(newscolumns!=null){
				//采集规则与新闻栏目进行关联
				model.setNewscolumns(newscolumns);
			}
		}
		if (service.saveOrUpdateNewsrule(model)){//调用业务逻辑组件保存新增的采集规则
			addActionMessage(getText("rule_add_succ"));
		}else{
			addActionMessage(getText("rule_add_fail"));
		}
		//为后台采集规则管理构造新闻栏目下拉列表数据
		newscolumnsList = columnsService.browseColumns();		
		return SUCCESS;
	}
	
	/** 处理删除新闻采集规则请求 */
	public String delNewsrule(){
		if (model.getId()!=null){
			if (service.delNewsrule(model.getId())){//调用业务逻辑组件删除指定的采集规则
				actionMsg = getText("rule_del_succ");
			}else{
				actionMsg = getText("rule_del_fail");
			}			
		}else{
			actionMsg = getText("rule_del_fail");
		}
		return "toBrowseNewsrule";
	}
	
	/** 处理查看新闻采集规则请求 */
	public String viewNewsrule(){
		if (model.getId()!=null){
			//调用业务逻辑组件装载指定的采集规则			
			Newsrule rule = service.loadNewsrule(model.getId());
			if (rule!=null){
				try {
					//快速复制源对象中的所有属性到目标对象中
					BeanUtils.copyProperties(model, rule);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return SUCCESS;
			}else{
				actionMsg = getText("rule_view_fail");
				return "toBrowseNewsrule";
			}	
		}else{
			actionMsg = getText("rule_view_fail");
			return "toBrowseNewsrule";
		}		
	}
	
	/** 处理装载新闻采集规则请求 */
	public String editNewsrule(){
		//为后台采集规则管理构造新闻栏目下拉列表数据
		newscolumnsList = columnsService.browseColumns();
		if (model.getId()!=null){
			//调用业务逻辑组件装载指定的采集规则			
			Newsrule rule = service.loadNewsrule(model.getId());
			if (rule!=null){
				try {
					//快速复制源对象中的所有属性到目标对象中
					BeanUtils.copyProperties(model, rule);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//取得所属栏目ID
				newscolumnsid=rule.getNewscolumns().getId().toString();
				return SUCCESS;
			}else{
				actionMsg = getText("rule_view_fail");
				return "toBrowseNewsrule";
			}	
		}else{
			actionMsg = getText("rule_view_fail");
			return "toBrowseNewsrule";
		}		
	}
	
	/** 处理更新新闻采集规则请求 */
	public String updateNewsrule(){		
		Newscolumns newscolumns;
		if(newscolumnsid!=null){
			//调用业务逻辑组件装载指定的新闻栏目
			newscolumns = columnsService.loadColumns(Integer.parseInt(newscolumnsid));
			if(newscolumns!=null){
				//采集规则与新闻栏目进行关联
				model.setNewscolumns(newscolumns);
			}
		}
		if (service.saveOrUpdateNewsrule(model)){//调用业务逻辑组件更新指定的采集规则
			addActionMessage(getText("rule_edit_succ"));
		}else{
			addActionMessage(getText("rule_edit_fail"));
		}
		//为后台采集规则管理构造新闻栏目下拉列表数据
		newscolumnsList = columnsService.browseColumns();		
		return INPUT;		
	}
	
	/** 处理采集规则下拉列表请求 */
	public String preAddNewsrule(){
		//为后台采集规则管理构造新闻栏目下拉列表数据
		newscolumnsList = columnsService.browseColumns();
		return SUCCESS;		
	}
	
	/** 处理新闻采集请求 */
	public String crawlNewsrule(){
		//使用WebApplicationContextUtils工具类获取Spring IOC容器中的dao实例
		BaseDAO dao = (BaseDAOImpl)WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext()).getBean("dao");
		//使用ServletActionContext工具类获取Session中的admin属性值
		Admin admin = (Admin)ServletActionContext.getRequest().getSession().getAttribute("admin");
		if (model.getId()!=null){
			//调用业务逻辑组件装载指定的采集规则	
			Newsrule rule = service.loadNewsrule(model.getId());
			//创建新闻采集线程
			CrawlNewsThread ct = new CrawlNewsThread(rule,dao,admin);
			Thread th = new Thread(ct);
			th.start();
			//使用ServletActionContext工具类向Session中写入ct属性
			ServletActionContext.getRequest().getSession().setAttribute("ct", ct);
		}
		return "status";		
	}	
	
	/** 手动进行新增新闻采集规则的表单验证 */
	public void validateAddNewsrule(){
		//采集规则名称必填
		if (model.getRuleName()==null||model.getRuleName().trim().length()<1){
			addFieldError("rulename",getText("rule_validation_rulename"));
		}		
		//入口地址必填,且有效性使用正则表达式进行验证
		if (model.getUrl()==null || model.getUrl().trim().length()<1 || !Pattern.matches("http(s)?://(.)*", model.getUrl().trim())){
			addFieldError("url",getText("rule_validation_url"));
		}
		//新闻列表起始特征串必填
		if (model.getListBegin()==null||model.getListBegin().trim().length()<1){
			addFieldError("listbegin",getText("rule_validation_listbegin"));
		}
		//新闻列表终止特征串必填
		if (model.getListEnd()==null||model.getListEnd().trim().length()<1){
			addFieldError("listend",getText("rule_validation_listend"));
		}
		//新闻标题起始特征串必填
		if (model.getTitleBegin()==null||model.getTitleBegin().trim().length()<1){
			addFieldError("titlebegin",getText("rule_validation_titlebegin"));
		}
		//新闻标题终止特征串必填
		if (model.getTitleEnd()==null||model.getTitleEnd().trim().length()<1){
			addFieldError("titleend",getText("rule_validation_titleend"));
		}
		//新闻内容起始特征串必填
		if (model.getContentBegin()==null||model.getContentBegin().trim().length()<1){
			addFieldError("contentbegin",getText("rule_validation_contentbegin"));
		}
		//新闻内容终止特征串必填
		if (model.getContentEnd()==null||model.getContentEnd().trim().length()<1){
			addFieldError("contentend",getText("rule_validation_contentend"));
		}		
		//表单验证失败
		if (hasFieldErrors()){
			//为后台采集规则管理构造新闻栏目下拉列表数据
			newscolumnsList = columnsService.browseColumns();
		}		
	}
	
	/** 手动进行修改新闻采集规则的表单验证 */
	public void validateUpdateNewsrule(){
		validateAddNewsrule();
	}

	public CrawlService getService() {
		return service;
	}

	public void setService(CrawlService service) {
		this.service = service;
	}

	public ColumnsService getColumnsService() {
		return columnsService;
	}

	public void setColumnsService(ColumnsService columnsService) {
		this.columnsService = columnsService;
	}

	public String getActionMsg() {
		return actionMsg;
	}

	public void setActionMsg(String actionMsg) {
		this.actionMsg = actionMsg;
	}

	public List<Newsrule> getRuleList() {
		return ruleList;
	}

	public void setRuleList(List<Newsrule> ruleList) {
		this.ruleList = ruleList;
	}

	public List<Newscolumns> getNewscolumnsList() {
		return newscolumnsList;
	}

	public void setNewscolumnsList(List<Newscolumns> newscolumnsList) {
		this.newscolumnsList = newscolumnsList;
	}

	public String getNewscolumnsid() {
		return newscolumnsid;
	}

	public void setNewscolumnsid(String newscolumnsid) {
		this.newscolumnsid = newscolumnsid;
	}
}