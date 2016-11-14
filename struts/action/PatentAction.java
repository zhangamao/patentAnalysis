package com.patent.struts.action;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.patent.ORM.Patent;
import com.patent.service.PatentService;

@SuppressWarnings("serial")
public class PatentAction extends ActionSupport implements ModelDriven<Patent>{

	/**通过依赖注入PatentService组件实例*/
	PatentService service;
	
	/**专利信息请求中常用的参数*/
	private String actionMsg;  //Action间传递的消息参数
	public List<Patent> patentList;  //专利列表
	
	//采用模型驱动
	private Patent model = new Patent();  //用于封装系统专利属性模型
	
	public Patent getModel() {
		// TODO Auto-generated method stub
		return model;
	}

	/**处理浏览专利请求*/
	public String browsePatent(){
		
		if(actionMsg!= null){
			try{
				actionMsg = new String(actionMsg.getBytes("ISO8859-1"),"gbk");
			}catch (UnsupportedEncodingException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			addActionMessage(actionMsg);
		}
		patentList = service.browsePatent();
		return SUCCESS;		
	}
	
	/**处理添加专利请求*/
	public String addPatent(){
		Patent tempPatent = new Patent();
		tempPatent.setAbs(model.getAbs());
		tempPatent.setAddress(model.getAddress());
		tempPatent.setAgentORG(model.getAgentORG());
		tempPatent.setApplicant(model.getApplicant());
		tempPatent.setApplicationDate(model.getApplicationDate());
		tempPatent.setApplicationNo(model.getApplicationNo());
		tempPatent.setInventor(model.getInventor());
		tempPatent.setIPC(model.getIPC());
		tempPatent.setLawState(model.getLawState());
		tempPatent.setMainClaim(model.getMainClaim());
		tempPatent.setMIPC(model.getMIPC());
		tempPatent.setPatentName(model.getPatentName());
		tempPatent.setPriority(model.getPriority());
		tempPatent.setProvinceCode(model.getProvinceCode());
		tempPatent.setPublicDate(model.getPublicDate());
		tempPatent.setPublicNumber(model.getPublicNumber());
		
		if(service.saveOrUpdatePatent(tempPatent)){
			addActionMessage(getText("patent_add_succ"));
		}else{
			addActionMessage(getText("patent_add_fail"));
		}
		
		return SUCCESS;
	}
	
	/**处理删除专利请求*/
	public String delPatent(){
		if(model.getId()!=null){
			if(service.delPatent(model.getId())){
				actionMsg = getText("patent_del_succ");
			}else{
				actionMsg = getText("patent_del_fail");
			}
			
		}else{
			actionMsg = getText("patent_del_fail");
		}
		return "toBrowsePatent";
	}
	
	/**处理更新专利请求*/
	public String updatePatent(){
		Patent tempPatent = service.loadPatent(model.getId());
		tempPatent.setAbs(model.getAbs());
		tempPatent.setMainClaim(model.getMainClaim());
		if(service.saveOrUpdatePatent(tempPatent)){
			addActionMessage(getText("patent_edit_succ"));
		}else{
			addActionMessage(getText("patent_edit_fail"));
		}
		return INPUT;
		
	}
	
}
