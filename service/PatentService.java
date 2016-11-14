package com.patent.service;

import java.util.List;

import com.patent.ORM.Patent;

/**专利业务逻辑接口*/
public interface PatentService {

	/**浏览专利*/
	public List<Patent> browsePatent();
	/**装载指定专利*/
	public Patent loadPatent(Integer id);
	/**删除指定的专利*/
	public boolean delPatent(Integer id);
	/**新增或修改专利*/
	public boolean saveOrUpdatePatent(Patent patent);
	
}
