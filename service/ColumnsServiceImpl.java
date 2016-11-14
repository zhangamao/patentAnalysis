package com.patent.service;

import java.util.List;
import com.patent.DAO.BaseDAO;
import com.patent.ORM.Newscolumns;

/** 新闻栏目管理业务逻辑接口实现 */
public class ColumnsServiceImpl implements ColumnsService {
	/** 通过依赖注入DAO组件实例 */
	BaseDAO dao;

	/** 新增或修改新闻栏目 */	
	public boolean saveOrUpdateColumns(Newscolumns columns){
		boolean status = false;
		try{
			dao.saveOrUpdate(columns);
			status = true;
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return status;
	}

	/** 浏览新闻栏目 */
	@SuppressWarnings("unchecked")
	public List<Newscolumns> browseColumns(){
		return dao.listAll("Newscolumns");
	}
	
	/** 一级新闻栏目列表 */
	@SuppressWarnings("unchecked")
	public List<Newscolumns> listColumns(){
		return dao.query("from Newscolumns as a where a.newscolumns is null");
	}
	
	/** 下级新闻栏目列表 */
	@SuppressWarnings("unchecked")
	public List<Newscolumns> listChildColumns(Newscolumns columns){
		if (columns==null){
			return null;
		}else{
			return dao.query("from Newscolumns as a where a.newscolumns.id="+columns.getId());
		}		
	}

	/** 删除指定的新闻栏目 */
	public boolean delColumns(Integer id){
		boolean status = false;
		try{
			dao.delById(Newscolumns.class, id);
			status = true;
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return status;
	}

	/** 装载指定的新闻栏目 */
	public Newscolumns loadColumns(Integer id){
		return (Newscolumns)dao.loadById(Newscolumns.class, id);
	}	

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
}
