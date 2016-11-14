package com.patent.service;

import java.util.List;
import com.patent.ORM.Merchandise;

/** 商品管理业务逻辑接口 */
public interface MerchandiseService {
	/** 浏览商品 */
	public List<Merchandise> browseMerchandise();
	/** 装载指定的商品 */	
	public Merchandise loadMerchandise(Integer id);	
	/** 删除指定的商品 */	
	public boolean delMerchandise(Integer id);	
	/** 新增或修改商品 */
	public boolean saveOrUpdateMerchandise(Merchandise merchandise);
}
