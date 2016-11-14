package com.patent.struts.action;

import java.io.UnsupportedEncodingException;
import java.util.List;
import com.patent.ORM.Category;
import com.patent.service.CategoryService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/** 商品分类管理控制器 */
@SuppressWarnings("serial")
public class CategoryAction extends ActionSupport implements ModelDriven<Category>{
	/** 通过依赖注入CategoryService组件实例 */
	CategoryService service;
	
	/** 商品分类管理所有请求中常用的参数值 */
	private String actionMsg;	//Action间传递的消息参数
	private List<Category> categoryList;	//商品分类列表
	private String parentid;	//上级栏目的ID
	
	//采用模型驱动
	private Category model=new Category();//用于封装商品分类属性模型
	public Category getModel() {
		return model;
	}
	
	/** 处理浏览商品分类请求 */
	public String browseCategory(){
		if(actionMsg!=null){
			try {
				actionMsg = new String(actionMsg.getBytes("ISO8859-1"),"gbk");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			addActionMessage(actionMsg);
		}
		categoryList = service.browseCategory();//调用业务逻辑组件取得商品类别列表
		return SUCCESS;
	}
	
	/** 处理新增商品分类请求 */
	public String addCategory(){
		Category tempCategory = new Category();
		tempCategory.setCateName(model.getCateName());
		if(parentid!=null && Integer.parseInt(parentid)>0){
			tempCategory.setCategory(service.loadCategory(Integer.valueOf(parentid)));
		}
		if (service.saveOrUpdateCategory(tempCategory)){//调用业务逻辑组件保存新增的商品类别
			addActionMessage(getText("category_add_succ"));
		}else{
			addActionMessage(getText("category_add_fail"));
		}
		return SUCCESS;
	}
	
	/** 处理删除商品分类请求 */
	public String delCategory(){
		if (model.getId()!=null){
			if (service.delCategory(model.getId())){//调用业务逻辑组件删除指定的商品类别
				actionMsg = getText("category_del_succ");
			}else{
				actionMsg = getText("category_del_fail");
			}			
		}else{
			actionMsg = getText("category_del_fail");
		}
		return "toBrowseCategory";
	}
	
	/** 处理查看商品分类请求 */
	public String viewCategory(){
		if (model.getId()!=null){
			//调用业务逻辑组件装载指定的商品类别
			Category tempCategory = service.loadCategory(model.getId());
			if (tempCategory!=null){
				model.setCateName(tempCategory.getCateName().trim());
				if (tempCategory.getCategory()!=null){
					parentid = tempCategory.getCategory().getId().toString();
				}else{
					parentid = "-1";
				}				
				return SUCCESS;
			}else{
				actionMsg = getText("category_view_fail");
				return "toBrowseCategory";
			}	
		}else{
			actionMsg = getText("category_view_fail");
			return "toBrowseCategory";
		}		
	}
	
	/** 处理装载商品分类请求 */
	public String editCategory(){
		if (model.getId()!=null){
			//调用业务逻辑组件装载指定的商品类别
			Category tempCategory = service.loadCategory(model.getId());
			if (tempCategory!=null){
				model.setCateName(tempCategory.getCateName().trim());
				if (tempCategory.getCategory()!=null){
					parentid = tempCategory.getCategory().getId().toString();
				}else{
					parentid = "-1";
				}
				return SUCCESS;
			}else{
				actionMsg = getText("category_edit_fail");
				return "toBrowseCategory";
			}	
		}else{
			actionMsg = getText("category_edit_fail");
			return "toBrowseCategory";
		}		
	}
	
	/** 处理更新商品分类请求 */
	public String updateCategory(){
		//调用业务逻辑组件装载指定的商品类别
		Category tempCategory = service.loadCategory(model.getId());
		tempCategory.setCateName(model.getCateName());
		if(parentid!=null && Integer.parseInt(parentid)>0){
			tempCategory.setCategory(service.loadCategory(Integer.valueOf(parentid)));
		}else{
			tempCategory.setCategory(null);
		}
		if (service.saveOrUpdateCategory(tempCategory)){//调用业务逻辑组件更新指定的商品类别
			addActionMessage(getText("category_edit_succ"));
		}else{
			addActionMessage(getText("category_edit_fail"));
		}
		return INPUT;		
	}
	
	/** 处理一级商品分类下拉列表请求 */
	public String listCategory(){
		categoryList = service.listCategory();//调用业务逻辑组件取得一级商品类别列表
		Category firstnode = new Category();
		firstnode.setId(-1);
		firstnode.setCateName("无上级类别");
		categoryList.add(0,firstnode);
		return SUCCESS;		
	}	

	public String getActionMsg() {
		return actionMsg;
	}
	public void setActionMsg(String actionMsg) {
		this.actionMsg = actionMsg;
	}
	public CategoryService getService() {
		return service;
	}
	public void setService(CategoryService service) {
		this.service = service;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
}
