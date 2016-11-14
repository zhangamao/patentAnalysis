package com.patent.service;

import java.util.List;
import com.patent.ORM.Admin;

/** 系统用户管理业务逻辑接口 */
public interface AdminService {
	/** 系统管理员登录 */
	public Admin adminLogin(String loginName,String loginPwd);	
	/** 浏览管理员 */
	public List<Admin> browseAdmin();	
	/** 装载指定的管理员 */	
	public Admin loadAdmin(Integer id);	
	/** 删除指定的管理员 */	
	public boolean delAdmin(Integer id);	
	/** 新增或修改管理员 */
	public boolean saveOrUpdateAdmin(Admin admin);	
}
