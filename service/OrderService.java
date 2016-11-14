package com.patent.service;

import java.util.List;
import com.patent.ORM.*;

/** 订单管理业务逻辑接口 */
public interface OrderService {

	/** 浏览订单 */
	public List<Orders> browseOrders();	
	/** 装载指定的订单 */	
	public Orders loadOrders(Integer id);
	/** 装载指定会员的未结帐订单 */	
	public Orders loadOrders(Member member);	
	/** 装载指定会员的所有订单 */	
	public List<Orders> loadAllOrders(Member member);	
	/** 删除指定的订单 */	
	public boolean delOrders(Integer id);
	/** 新增或修改订单 */
	public boolean saveOrUpdateOrders(Orders order);
	
}
