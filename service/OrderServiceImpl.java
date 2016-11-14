package com.patent.service;

import java.util.List;
import com.patent.DAO.BaseDAO;
import com.patent.ORM.*;

/** 订单管理业务逻辑接口实现 */
public class OrderServiceImpl implements OrderService {
	/** 通过依赖注入DAO组件实例 */
	BaseDAO dao;
	
	/** 浏览订单 */
	@SuppressWarnings("unchecked")
	public List<Orders> browseOrders() {
		return dao.query("from Orders as a where a.orderStatus>0 order by a.id desc");
	}

	/** 删除指定的订单 */
	public boolean delOrders(Integer id) {
		boolean status = false;
		try{
			dao.delById(Orders.class, id);
			status = true;
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return status;
	}

	/** 装载指定的订单 */
	public Orders loadOrders(Integer id) {
		return (Orders)dao.loadById(Orders.class, id);
	}

	/** 装载指定会员的所有订单 */	
	@SuppressWarnings("unchecked")
	public List<Orders> loadAllOrders(Member member) {		
		return dao.query("from Orders as a where a.orderStatus>0 and a.member.id="+member.getId());
	}
	
	/** 装载指定会员的未结帐订单 */		
	public Orders loadOrders(Member member) {
		return (Orders)dao.loadObject("from Orders as a where a.member.id="+member.getId()+" and a.orderStatus=0");
	}	

	/** 新增或修改订单 */
	public boolean saveOrUpdateOrders(Orders order) {
		boolean status = false;
		try{
			dao.saveOrUpdate(order);
			status = true;
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return status;
	}

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
}
