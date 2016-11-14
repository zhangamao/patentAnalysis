package com.patent.service;

import java.util.List;
import com.patent.DAO.BaseDAO;
import com.patent.ORM.Newsrule;

/** 新闻采集规则管理业务逻辑接口实现 */
public class CrawlServiceImpl implements CrawlService {
	/** 通过依赖注入DAO组件实例 */
	BaseDAO dao;

	/** 新增或修改新闻采集规则 */	
	public boolean saveOrUpdateNewsrule(Newsrule rule){
		boolean status = false;
		try{
			dao.saveOrUpdate(rule);
			status = true;
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return status;
	}

	/** 浏览新闻采集规则 */
	@SuppressWarnings("unchecked")
	public List<Newsrule> browseNewsrule(){
		return dao.listAll("Newsrule");
	}

	/** 删除指定的新闻采集规则 */
	public boolean delNewsrule(Integer id){
		boolean status = false;
		try{
			dao.delById(Newsrule.class, id);
			status = true;
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return status;
	}

	/** 装载指定的新闻采集规则 */
	public Newsrule loadNewsrule(Integer id){
		return (Newsrule)dao.loadById(Newsrule.class, id);
	}

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
}
