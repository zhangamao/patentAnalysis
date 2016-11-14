package com.patent.service;

import java.util.List;
import com.patent.DAO.BaseDAO;

/** 流量统计业务逻辑接口实现 */
public class TrafficServiceImpl implements TrafficService {
	/** 通过依赖注入DAO组件实例 */
	BaseDAO dao;
	
	/** 浏览访问记录 */
	public List browseTraffic(String hql) {
		return dao.query(hql);
	}

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
}
