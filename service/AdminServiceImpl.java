package com.patent.service;

import java.util.List;
import com.patent.DAO.BaseDAO;
import com.patent.ORM.Admin;

/** 系统用户管理业务逻辑接口实现 */
public class AdminServiceImpl implements AdminService {
	/** 通过依赖注入DAO组件实例 */
	BaseDAO dao;

	/** 系统管理员登录 */
	public Admin adminLogin(String loginName, String loginPwd){
		String hql = "from Admin as a where a.loginName='"+loginName+"' and a.loginPwd='"+loginPwd+"'";
		return (Admin)dao.loadObject(hql);
	}

	/** 新增或修改管理员 */	
	public boolean saveOrUpdateAdmin(Admin admin){
		boolean status = false;
		try{
			dao.saveOrUpdate(admin);
			status = true;
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return status;
	}

	/** 浏览管理员 */
	@SuppressWarnings("unchecked")
	public List<Admin> browseAdmin(){
		return dao.listAll("Admin");
	}

	/** 删除指定的管理员 */
	public boolean delAdmin(Integer id){
		boolean status = false;
		try{
			dao.delById(Admin.class, id);
			status = true;
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return status;
	}

	/** 装载指定的管理员 */
	public Admin loadAdmin(Integer id){
		return (Admin)dao.loadById(Admin.class, id);
	}

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
}
