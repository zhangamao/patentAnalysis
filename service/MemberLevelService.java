package com.patent.service;

import java.util.List;
import com.patent.ORM.Memberlevel;

/** 会员级别管理业务逻辑接口 */
public interface MemberLevelService {
	/** 浏览会员级别 */
	public List<Memberlevel> browseMemberlevel();	
	/** 装载指定的会员级别 */	
	public Memberlevel loadMemberlevel(Integer id);	
	/** 删除指定的会员级别 */	
	public boolean delMemberlevel(Integer id);	
	/** 新增或修改会员级别 */
	public boolean saveOrUpdateMemberlevel(Memberlevel memberlevel);
	/** 取得初始会员级别 */
	public Memberlevel getInitMemberlevel();		
} 
