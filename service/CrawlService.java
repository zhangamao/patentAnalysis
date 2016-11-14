package com.patent.service;

import java.util.List;
import com.patent.ORM.Newsrule;

/** 新闻采集规则管理业务逻辑接口 */
public interface CrawlService {
	/** 浏览新闻采集规则 */
	public List<Newsrule> browseNewsrule();	
	/** 装载指定的新闻采集规则 */	
	public Newsrule loadNewsrule(Integer id);	
	/** 删除指定的新闻采集规则 */	
	public boolean delNewsrule(Integer id);	
	/** 新增或修改新闻采集规则 */
	public boolean saveOrUpdateNewsrule(Newsrule rule);	
}
