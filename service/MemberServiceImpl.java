package com.patent.service;

import java.util.List;
import com.patent.DAO.BaseDAO;
import com.patent.ORM.Member;

/** 注册会员管理业务逻辑接口实现 */
public class MemberServiceImpl implements MemberService {
	/** 通过依赖注入DAO组件实例 */
	BaseDAO dao;

	/** 注册会员登录 */
	public Member memberLogin(String loginName, String loginPwd){
		String hql = "from Member as a where a.loginName='"+loginName+"' and a.loginPwd='"+loginPwd+"'";
		return (Member)dao.loadObject(hql);
	}

	/** 新增或修改注册会员 */	
	public boolean saveOrUpdateMember(Member member){
		boolean status = false;
		try{
			dao.saveOrUpdate(member);
			status = true;
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return status;
	}

	/** 浏览注册会员 */
	@SuppressWarnings("unchecked")
	public List<Member> browseMember(){
		return dao.listAll("Member");
	}

	/** 删除指定的注册会员 */
	public boolean delMember(Integer id){
		boolean status = false;
		try{
			dao.delById(Member.class, id);
			status = true;
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return status;
	}

	/** 装载指定的注册会员 */
	public Member loadMember(Integer id){
		return (Member)dao.loadById(Member.class, id);
	}
	
	/** 检查指定登录账号是否可用 */
	public boolean isEnable(String loginname) {
		if (dao.countQuery("select count(*) from Member as a where a.loginName='"+loginname+"'")>0){
			return false;
		}else{
			return true;
		}
	}	

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
}
