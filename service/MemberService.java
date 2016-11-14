package com.patent.service;

import java.util.List;
import com.patent.ORM.Member;

/** 注册会员管理业务逻辑接口 */
public interface MemberService {
	/** 注册会员登录 */
	public Member memberLogin(String loginName,String loginPwd);	
	/** 浏览注册会员 */
	public List<Member> browseMember();	
	/** 装载指定的注册会员 */	
	public Member loadMember(Integer id);	
	/** 删除指定的注册会员 */	
	public boolean delMember(Integer id);	
	/** 新增或修改注册会员 */
	public boolean saveOrUpdateMember(Member member);
	/** 检查指定登录账号是否可用 */
	public boolean isEnable(String loginname);	
}
