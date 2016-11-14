package com.patent.service;

import java.util.List;
import com.patent.DAO.BaseDAO;
import com.patent.ORM.Memberlevel;

/** 会员级别管理业务逻辑接口实现 */
public class MemberLevelServiceImpl implements MemberLevelService {
	/** 通过依赖注入DAO组件实例 */
	BaseDAO dao;

	/** 新增或修改会员级别 */	
	public boolean saveOrUpdateMemberlevel(Memberlevel memberlevel){
		boolean status = false;
		try{
			dao.saveOrUpdate(memberlevel);
			status = true;
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return status;
	}

	/** 浏览会员级别 */
	@SuppressWarnings("unchecked")
	public List<Memberlevel> browseMemberlevel(){
		return dao.listAll("Memberlevel");
	}

	/** 删除指定的会员级别 */
	public boolean delMemberlevel(Integer id){
		boolean status = false;
		try{
			dao.delById(Memberlevel.class, id);
			status = true;
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return status;
	}

	/** 装载指定的会员级别 */
	public Memberlevel loadMemberlevel(Integer id){
		return (Memberlevel)dao.loadById(Memberlevel.class, id);
	}
	
	/** 取得初始会员级别 */
	@SuppressWarnings("unchecked")
	public Memberlevel getInitMemberlevel() {
		List<Memberlevel> list = dao.query("from Memberlevel as a order by a.favourable asc", 1, 1);
		if (list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}		
	}	

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
}
