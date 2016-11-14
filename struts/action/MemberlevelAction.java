package com.patent.struts.action;

import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import com.patent.ORM.Memberlevel;
import com.patent.service.MemberLevelService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/** 会员级别处理控制器 */
@SuppressWarnings("serial")
public class MemberlevelAction extends ActionSupport implements ModelDriven<Memberlevel>{
	/** 通过依赖注入MemberLevelService组件实例 */
	MemberLevelService service;
	
	/** 会员级别管理所有请求中常用的参数值 */
	private String actionMsg;	//Action间传递的消息参数
	private List<Memberlevel> memberLevelList;	//会员级别列表
	
	//采用模型驱动
	private Memberlevel model=new Memberlevel();//用于封装会员级别属性模型
	public Memberlevel getModel() {
		return model;
	}
		
	/** 处理浏览会员级别请求 */
	public String browseMemberlevel(){
		if(actionMsg!=null){
			try {
				actionMsg = new String(actionMsg.getBytes("ISO8859-1"),"gbk");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			addActionMessage(actionMsg);
		}
		memberLevelList = service.browseMemberlevel();//调用业务逻辑组件取得会员级别列表
		return SUCCESS;
	}
	
	/** 处理新增会员级别请求 */
	public String addMemberlevel(){
		if (service.saveOrUpdateMemberlevel(model)){//调用业务逻辑组件保存新增的会员级别
			addActionMessage(getText("level_add_succ"));
		}else{
			addActionMessage(getText("level_add_fail"));
		}
		return SUCCESS;
	}
	
	/** 处理删除会员级别请求 */
	public String delMemberlevel(){
		if (model.getId()!=null){
			if (service.delMemberlevel(model.getId())){//调用业务逻辑组件删除指定的会员级别
				actionMsg = getText("level_del_succ");
			}else{
				actionMsg = getText("level_del_fail");
			}			
		}else{
			actionMsg = getText("level_del_fail");
		}
		return "toBrowseMemberlevel";
	}
	
	/** 处理查看会员级别请求 */
	public String viewMemberlevel(){
		if (model.getId()!=null){
			//调用业务逻辑组件装载指定的会员级别	
			Memberlevel tempMemberlevel = service.loadMemberlevel(model.getId());
			if (tempMemberlevel!=null){
				try {
					//快速复制源对象中的所有属性到目标对象中
					BeanUtils.copyProperties(model, tempMemberlevel);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return SUCCESS;
			}else{
				actionMsg = getText("level_view_fail");
				return "toBrowseMemberlevel";
			}	
		}else{
			actionMsg = getText("level_view_fail");
			return "toBrowseMemberlevel";
		}		
	}
	
	/** 处理装载会员级别请求 */
	public String editMemberlevel(){
		if (model.getId()!=null){
			//调用业务逻辑组件装载指定的会员级别
			Memberlevel tempMemberlevel = service.loadMemberlevel(model.getId());
			if (tempMemberlevel!=null){
				try {
					//快速复制源对象中的所有属性到目标对象中
					BeanUtils.copyProperties(model, tempMemberlevel);
				} catch (Exception e) {
					e.printStackTrace();
				}				
				return SUCCESS;
			}else{
				actionMsg = getText("level_edit_fail");
				return "toBrowseMemberlevel";
			}	
		}else{
			actionMsg = getText("level_edit_fail");
			return "toBrowseMemberlevel";
		}		
	}
	
	/** 处理更新会员级别请求 */
	public String updateMemberlevel(){		
		if (service.saveOrUpdateMemberlevel(model)){//调用业务逻辑组件更新指定的会员级别
			addActionMessage(getText("level_edit_succ"));
		}else{
			addActionMessage(getText("level_edit_fail"));
		}
		return INPUT;		
	}	

	public String getActionMsg() {
		return actionMsg;
	}

	public void setActionMsg(String actionMsg) {
		this.actionMsg = actionMsg;
	}

	public MemberLevelService getService() {
		return service;
	}

	public void setService(MemberLevelService service) {
		this.service = service;
	}

	public List<Memberlevel> getMemberLevelList() {
		return memberLevelList;
	}

	public void setMemberLevelList(List<Memberlevel> memberLevelList) {
		this.memberLevelList = memberLevelList;
	}	
}
