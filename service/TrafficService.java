package com.patent.service;

import java.util.List;
import com.patent.ORM.*;

/** 流量统计业务逻辑接口 */
public interface TrafficService {
	/** 浏览访问记录 */
	public List browseTraffic(String hql);	
}
