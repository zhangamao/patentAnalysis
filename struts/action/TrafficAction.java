package com.patent.struts.action;

import java.util.*;
import java.net.*;
import org.apache.struts2.ServletActionContext;
import com.patent.service.*;
import com.opensymphony.xwork2.ActionSupport;

/** 流量统计控制器 */
@SuppressWarnings("serial")
public class TrafficAction extends ActionSupport{
	/** 通过依赖注入TrafficService组件实例 */
	TrafficService service;

	private List result;//查询结果列表
	private String hql =null;//查询HQL语句
	
	/** 处理IP统计请求 */
	public String browseIP(){
		hql = ServletActionContext.getRequest().getParameter("hql");
		if(hql==null){
			hql="select a.ip,max(a.area),max(a.visitDate),count(a.ip) from Traffic as a group by a.ip order by a.id desc";
		}else{			
			try {
				hql=URLDecoder.decode(hql,"UTF-8");
			} catch (Exception e) {}
		}
		List tempresult = service.browseTraffic(hql);
		//对查询结果进行重新封装
		Iterator it = tempresult.iterator();
		Object[] obj = null;
		Map row = null;
		result = new ArrayList();
		while(it.hasNext()){
			obj = (Object[])it.next();
			row = new HashMap();
			row.put("ip", obj[0]);
			row.put("area", obj[1]);			
			row.put("visitDate", obj[2]);
			row.put("times", obj[3]);
			result.add(row);
		}
		return SUCCESS;
	}
	
	/** 处理PV统计请求 */
	public String browsePV(){
		hql = ServletActionContext.getRequest().getParameter("hql");
		if(hql==null){
			hql="from Traffic as a order by a.id desc";
		}else{			
			try {
				hql=URLDecoder.decode(hql,"UTF-8");
			} catch (Exception e) {}
		}
		result = service.browseTraffic(hql);
		return SUCCESS;
	}	

	public TrafficService getService() {
		return service;
	}

	public void setService(TrafficService service) {
		this.service = service;
	}

	public List getResult() {
		return result;
	}

	public void setResult(List result) {
		this.result = result;
	}

	public String getHql() {
		return hql;
	}

	public void setHql(String hql) {
		this.hql = hql;
	}	
}