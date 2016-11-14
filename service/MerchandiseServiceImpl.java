package com.patent.service;

import java.util.List;
import com.patent.DAO.BaseDAO;
import com.patent.ORM.Merchandise;

/** 商品管理业务逻辑接口实现 */
public class MerchandiseServiceImpl implements MerchandiseService {
	/** 通过依赖注入DAO组件实例 */
	BaseDAO dao;

	/** 新增或修改商品 */	
	public boolean saveOrUpdateMerchandise(Merchandise Merchandise){
		boolean status = false;
		try{
			dao.saveOrUpdate(Merchandise);
			status = true;
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return status;
	}

	/** 浏览商品 */
	@SuppressWarnings("unchecked")
	public List<Merchandise> browseMerchandise(){
		return dao.listAll("Merchandise");
	}
	
	/** 删除指定的商品 */
	public boolean delMerchandise(Integer id){
		boolean status = false;
		try{
			dao.delById(Merchandise.class, id);
			status = true;
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return status;
	}

	/** 装载指定的商品 */
	public Merchandise loadMerchandise(Integer id){
		return (Merchandise)dao.loadById(Merchandise.class, id);
	}	

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
}
