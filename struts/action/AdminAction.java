package com.patent.struts.action;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.patent.ORM.Admin;
import com.patent.service.AdminService;
import com.patent.util.MD5;

/** 系统管理员处理控制器 */
@SuppressWarnings("serial")
public class AdminAction extends ActionSupport implements ModelDriven<Admin>{
	/** 通过依赖注入AdminService组件实例 */
	AdminService service;
	
	/** 系统用户管理所有请求中常用的参数值 */
	private String rand; 		//随机验证码
	private String actionMsg;	//Action间传递的消息参数
	private List<Admin> adminList;		//系统用户列表
	
	//采用模型驱动
	private Admin model=new Admin();//用于封装系统用户属性模型
	public Admin getModel() {
		return model;
	}
	
	/** 处理登录请求 */
	public String login(){
		if(!rand.equalsIgnoreCase((String)ServletActionContext.getRequest().getSession().getAttribute("rand"))){
			addActionError(getText("login_rand_error"));
			return "login";
		}else{
			Admin tempAdmin = service.adminLogin(model.getLoginName(), MD5.MD5Encode(model.getLoginPwd()));
			if(tempAdmin!=null){
				ServletActionContext.getRequest().getSession().setAttribute("admin",tempAdmin);
				return "index";
			}else{
				addActionError(getText("login_fail"));
				return "login";				
			}
		}
	}
	
	/** 处理注销请求 */
	public String logout(){		
		ServletActionContext.getRequest().getSession().invalidate();
		return "login";
	}
	
	/** 处理浏览系统用户请求 */
	public String browseAdmin(){
		if(actionMsg!=null){
			try {
				actionMsg = new String(actionMsg.getBytes("ISO8859-1"),"gbk");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			addActionMessage(actionMsg);
		}
		adminList = service.browseAdmin();
		return SUCCESS;
	}
	
	/** 处理新增系统用户请求 */
	public String addAdmin(){
		Admin tempAdmin = new Admin();
		tempAdmin.setLoginName(model.getLoginName());
		tempAdmin.setLoginPwd(MD5.MD5Encode(model.getLoginPwd()));
		tempAdmin.setPrivileges(model.getPrivileges());
		if (service.saveOrUpdateAdmin(tempAdmin)){
			addActionMessage(getText("admin_add_succ"));
		}else{
			addActionMessage(getText("admin_add_fail"));
		}
		return SUCCESS;
	}
	
	/** 处理删除系统用户请求 */
	public String delAdmin(){
		if (model.getId()!=null){
			if (service.delAdmin(model.getId())){
				actionMsg = getText("admin_del_succ");
			}else{
				actionMsg = getText("admin_del_fail");
			}			
		}else{
			actionMsg = getText("admin_del_fail");
		}
		return "toBrowseAdmin";
	}
	
	/** 处理查看系统用户请求 */
	public String viewAdmin(){
		if (model.getId()!=null){
			Admin tempAdmin = service.loadAdmin(model.getId());
			if (tempAdmin!=null){
				model.setLoginName(tempAdmin.getLoginName().trim());
				model.setPrivileges(tempAdmin.getPrivileges().trim());
				return SUCCESS;
			}else{
				actionMsg = getText("admin_view_fail");
				return "toBrowseAdmin";
			}	
		}else{
			actionMsg = getText("admin_view_fail");
			return "toBrowseAdmin";
		}		
	}
	
	/** 处理装载系统用户请求 */
	public String editAdmin(){
		if (model.getId()!=null){
			Admin tempAdmin = service.loadAdmin(model.getId());
			if (tempAdmin!=null){
				model.setLoginName(tempAdmin.getLoginName().trim());
				model.setPrivileges(tempAdmin.getPrivileges().trim());				
				return SUCCESS;
			}else{
				actionMsg = getText("admin_edit_fail");
				return "toBrowseAdmin";
			}	
		}else{
			actionMsg = getText("admin_edit_fail");
			return "toBrowseAdmin";
		}		
	}
	
	/** 处理更新系统用户请求 */
	public String updateAdmin(){		
		Admin tempAdmin = service.loadAdmin(model.getId());
		tempAdmin.setLoginName(model.getLoginName());
		if (model.getLoginPwd()!=null&&model.getLoginPwd().length()>0){
			tempAdmin.setLoginPwd(MD5.MD5Encode(model.getLoginPwd()));
		}
		tempAdmin.setPrivileges(model.getPrivileges());
		if (service.saveOrUpdateAdmin(tempAdmin)){
			addActionMessage(getText("admin_edit_succ"));
		}else{
			addActionMessage(getText("admin_edit_fail"));
		}
		return INPUT;		
	}	
	
	public String getRand() {
		return rand;
	}
	public void setRand(String rand) {
		this.rand = rand;
	}
	public AdminService getService() {
		return service;
	}
	public void setService(AdminService service) {
		this.service = service;
	}
	public String getActionMsg() {
		return actionMsg;
	}

	public void setActionMsg(String actionMsg) {
		this.actionMsg = actionMsg;
	}
	
	public List<Admin> getAdminList() {
		return adminList;
	}
	
	public void setAdminList(List<Admin> adminList) {
		this.adminList = adminList;
	}	
}
