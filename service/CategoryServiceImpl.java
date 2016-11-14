package com.patent.service;

import java.util.List;
import com.patent.DAO.BaseDAO;
import com.patent.ORM.Category;

/** 商品分类管理业务逻辑接口实现 */
public class CategoryServiceImpl implements CategoryService {
	/** 通过依赖注入DAO组件实例 */
	BaseDAO dao;

	/** 新增或修改商品分类 */	
	public boolean saveOrUpdateCategory(Category category){
		boolean status = false;
		try{
			dao.saveOrUpdate(category);
			status = true;
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return status;
	}

	/** 浏览商品分类 */
	@SuppressWarnings("unchecked")
	public List<Category> browseCategory(){
		return dao.listAll("Category");
	}
	
	/** 一级商品分类列表 */
	@SuppressWarnings("unchecked")
	public List<Category> listCategory(){
		return dao.query("from Category as a where a.category is null");
	}
	
	/** 下级商品分类列表 */
	@SuppressWarnings("unchecked")
	public List<Category> listChildCategory(Category category){
		if (category==null){
			return null;
		}else{
			return dao.query("from Category as a where a.category.id="+category.getId());
		}		
	}

	/** 删除指定的商品分类 */
	public boolean delCategory(Integer id){
		boolean status = false;
		try{
			dao.delById(Category.class, id);
			status = true;
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return status;
	}

	/** 装载指定的商品分类 */
	public Category loadCategory(Integer id){
		return (Category)dao.loadById(Category.class, id);
	}	

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
}
