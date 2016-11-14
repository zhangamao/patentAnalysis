package com.patent.service;

import java.util.List;
import com.patent.ORM.Category;

/** 商品分类管理业务逻辑接口 */
public interface CategoryService {
	/** 浏览商品分类 */
	public List<Category> browseCategory();
	/** 一级商品分类列表 */
	public List<Category> listCategory();	
	/** 下级商品分类列表 */
	public List<Category> listChildCategory(Category category);		
	/** 装载指定的商品分类 */	
	public Category loadCategory(Integer id);	
	/** 删除指定的商品分类 */	
	public boolean delCategory(Integer id);	
	/** 新增或修改商品分类 */
	public boolean saveOrUpdateCategory(Category category);
}
