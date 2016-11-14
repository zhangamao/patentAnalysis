package com.patent.struts.action;

import java.io.UnsupportedEncodingException;
import java.util.List;
import com.patent.ORM.Newscolumns;
import com.patent.service.ColumnsService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/** 新闻栏目管理控制器 */
@SuppressWarnings("serial")
public class ColumnsAction extends ActionSupport implements ModelDriven<Newscolumns>{
	/** 通过依赖注入ColumnsService组件实例 */
	ColumnsService service;
	
	/** 新闻栏目管理所有请求中常用的参数值 */
	private String actionMsg;	//Action间传递的消息参数
	private List<Newscolumns> columnsList;	//新闻栏目列表
	private String parentid;	//上级栏目的ID
	
	//采用模型驱动
	private Newscolumns model=new Newscolumns();//用于封装新闻栏目属性的模型实例
	public Newscolumns getModel() {
		return model;
	}
	
	/** 处理浏览新闻栏目请求 */
	public String browseColumns(){
		if(actionMsg!=null){
			try {
				actionMsg = new String(actionMsg.getBytes("ISO8859-1"),"gbk");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			addActionMessage(actionMsg);
		}
		columnsList = service.browseColumns();//调用业务逻辑组件取得新闻栏目列表
		return SUCCESS;
	}
	
	/** 处理新增新闻栏目请求 */
	public String addColumns(){
		Newscolumns tempColumns = new Newscolumns();
		tempColumns.setColumnName(model.getColumnName());
		tempColumns.setColumnCode(model.getColumnCode());
		if(parentid!=null && Integer.parseInt(parentid)>0){
			tempColumns.setNewscolumns(service.loadColumns(Integer.valueOf(parentid)));
		}
		if (service.saveOrUpdateColumns(tempColumns)){//调用业务逻辑组件保存新增的新闻栏目
			addActionMessage(getText("columns_add_succ"));
		}else{
			addActionMessage(getText("columns_add_fail"));
		}
		return SUCCESS;
	}
	
	/** 处理删除新闻栏目请求 */
	public String delColumns(){
		if (model.getId()!=null){
			if (service.delColumns(model.getId())){//调用业务逻辑组件删除指定的新闻栏目
				actionMsg = getText("columns_del_succ");
			}else{
				actionMsg = getText("columns_del_fail");
			}			
		}else{
			actionMsg = getText("columns_del_fail");
		}
		return "toBrowseColumns";
	}
	
	/** 处理查看新闻栏目请求 */
	public String viewColumns(){
		if (model.getId()!=null){
			//调用业务逻辑组件装载指定的新闻栏目
			Newscolumns tempColumns = service.loadColumns(model.getId());
			if (tempColumns!=null){
				model.setColumnName(tempColumns.getColumnName().trim());
				model.setColumnCode(tempColumns.getColumnCode().trim());
				if (tempColumns.getNewscolumns()!=null){
					parentid = tempColumns.getNewscolumns().getId().toString();
				}else{
					parentid = "-1";
				}				
				return SUCCESS;
			}else{
				actionMsg = getText("columns_view_fail");
				return "toBrowseColumns";
			}	
		}else{
			actionMsg = getText("columns_view_fail");
			return "toBrowseColumns";
		}		
	}
	
	/** 处理装载新闻栏目请求 */
	public String editColumns(){
		if (model.getId()!=null){
			//调用业务逻辑组件装载指定的新闻栏目
			Newscolumns tempColumns = service.loadColumns(model.getId());
			if (tempColumns!=null){
				model.setColumnName(tempColumns.getColumnName().trim());
				model.setColumnCode(tempColumns.getColumnCode().trim());
				if (tempColumns.getNewscolumns()!=null){
					parentid = tempColumns.getNewscolumns().getId().toString();
				}else{
					parentid = "-1";
				}
				return SUCCESS;
			}else{
				actionMsg = getText("columns_edit_fail");
				return "toBrowseColumns";
			}	
		}else{
			actionMsg = getText("columns_edit_fail");
			return "toBrowseColumns";
		}		
	}
	
	/** 处理更新新闻栏目请求 */
	public String updateColumns(){		
		Newscolumns tempColumns = service.loadColumns(model.getId());
		tempColumns.setColumnName(model.getColumnName());
		tempColumns.setColumnCode(model.getColumnCode());
		if(parentid!=null && Integer.parseInt(parentid)>0){
			tempColumns.setNewscolumns(service.loadColumns(Integer.valueOf(parentid)));
		}else{
			tempColumns.setNewscolumns(null);
		}
		if (service.saveOrUpdateColumns(tempColumns)){//调用业务逻辑组件更新指定的新闻栏目
			addActionMessage(getText("columns_edit_succ"));
		}else{
			addActionMessage(getText("columns_edit_fail"));
		}
		return INPUT;		
	}
	
	/** 处理一级新闻栏目下拉列表请求 */
	public String listColumns(){
		columnsList = service.listColumns();//调用业务逻辑组件取得一级新闻栏目列表
		Newscolumns firstnode = new Newscolumns();
		firstnode.setId(-1);
		firstnode.setColumnName("无上级栏目");
		columnsList.add(0,firstnode);
		return SUCCESS;	
	}	

	public String getActionMsg() {
		return actionMsg;
	}
	public void setActionMsg(String actionMsg) {
		this.actionMsg = actionMsg;
	}
	public ColumnsService getService() {
		return service;
	}
	public void setService(ColumnsService service) {
		this.service = service;
	}

	public List<Newscolumns> getColumnsList() {
		return columnsList;
	}
	public void setColumnsList(List<Newscolumns> columnsList) {
		this.columnsList = columnsList;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
}
