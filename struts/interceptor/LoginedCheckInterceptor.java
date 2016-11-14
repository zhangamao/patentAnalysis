package com.patent.struts.interceptor;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.patent.ORM.Admin;

/** session过期、登录有效性及操作的权限验证拦截器 */
public class LoginedCheckInterceptor extends AbstractInterceptor {

	/** 拦截请求并进行登录有效性验证 */
	public String intercept(ActionInvocation ai) throws Exception {
		//取得请求的URL
		String url = ServletActionContext.getRequest().getRequestURL().toString();
		String prim = null;
		Admin admin = null;
		int index = 0;
		//验证Session是否过期
		if(!ServletActionContext.getRequest().isRequestedSessionIdValid()){
			//session过期,转向session过期提示页,最终跳转至登录页面
			return "tologin";
		}else{
			//对登录与注销请求直接放行,不予拦截
			if (url.indexOf("admin_login.action")!=-1 || url.indexOf("admin_logout.action")!=-1){
				return ai.invoke();
			}else{
				admin = (Admin)ServletActionContext.getRequest().getSession().getAttribute("admin");
				//验证是否已经登录
				if (admin==null){
					//尚未登录,跳转至登录页面
					return "tologin";
				}else{
					//功能模块与权限位映射,部分可能与前台请求重名的请求加上命名空间"/admin"以示区别
					if (url.indexOf("/admin_")!=-1 || url.indexOf("/updateAdmin")!=-1){//系统用户管理
						index = 2; //权限位为2
					}else if (url.indexOf("/columns_")!=-1 || url.indexOf("/updateColumns")!=-1){//新闻栏目管理
						index = 3; //权限位为3
					}else if (url.indexOf("/news_")!=-1 || url.indexOf("/preAddNews")!=-1 || url.indexOf("/updateNews")!=-1 || url.indexOf("/publisNews")!=-1){//新闻管理
						index = 4; //权限位为4
					}else if (url.indexOf("/rule_")!=-1 || url.indexOf("/preAddNewsrule")!=-1 || url.indexOf("/updateNewsrule")!=-1){ //新闻采集
						index = 5; //权限位为5
					}else if (url.indexOf("/level_")!=-1 || url.indexOf("/updateMemberlevel")!=-1){//会员级别管理
						index = 6; //权限位为6
					}else if (url.indexOf("/member_")!=-1 || url.indexOf("/preAddMember")!=-1 || url.indexOf("/admin/updateMember")!=-1){//会员管理
						index = 6; //权限位为6
					}else if (url.indexOf("/cate_")!=-1 || url.indexOf("/updateCategory")!=-1){//商品分类管理
						index = 7; //权限位为7
					}else if (url.indexOf("/mer_")!=-1 || url.indexOf("/preAddMerchandise")!=-1 || url.indexOf("/updateMerchandise")!=-1 || url.indexOf("/publisMerchandise")!=-1){//商品管理
						index = 8; //权限位为8
					}else if (url.indexOf("/orders_")!=-1 || url.indexOf("/admin/updateOrdersStatus")!=-1){//订单管理
						index = 9; //权限位为9
					}else if (url.indexOf("/traffic_")!=-1){//流量统计
						index = 10; //权限位为10
					}
					//取得当前用户的操作权限
					prim = admin.getPrivileges().trim();
					//进行权限验证
					if (index>0){
						if (prim.substring(0,1).equals("1") || prim.substring(index-1,index).equals("1")){
							//验证通过,放行
							return ai.invoke();
						}else{
							//验证失败,转向权限验证失败提示页
							return "noprim";
						}
					}else{
						//其它不需要权限验证的请求直接放行
						return ai.invoke();
					}					
				}				
			}			
		}
	}
}
