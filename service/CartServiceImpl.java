package com.patent.service;

import java.util.List;
import com.patent.DAO.BaseDAO;
import com.patent.ORM.Cart;
import com.patent.ORM.Cartselectedmer;
import com.patent.ORM.Member;
import com.patent.ORM.Merchandise;

/** 购物车管理业务逻辑接口实现 */
public class CartServiceImpl implements CartService {
	/** 通过依赖注入DAO组件实例 */
	BaseDAO dao;
	
	/** 浏览购物车 */
	@SuppressWarnings("unchecked")
	public List<Cart> browseCart() {
		return dao.listAll("Cart");
	}

	/** 浏览指定购物车的选购记录 */
	@SuppressWarnings("unchecked")
	public List<Cartselectedmer> browseCartselectedmer(Cart cart) {
		String hql = "from Cartselectedmer as a where a.cart.id="+cart.getId()+" order by a.id desc";
		return dao.query(hql);
	}

	/** 清空指定的购物车 */
	public boolean clearCart(Integer id) {
		boolean status = false;
		try{
			dao.update("delete from Cartselectedmer as a where a.cart.id="+id);
			status = true;
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return status;
	}

	/** 删除指定的购物车 */
	public boolean delCart(Integer id) {
		boolean status = false;
		try{
			dao.delById(Cart.class, id);
			status = true;
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return status;
	}

	/** 删除指定的选购记录 */
	public boolean delCartselectedmer(Integer id) {
		boolean status = false;
		try{
			dao.delById(Cartselectedmer.class, id);
			status = true;
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return status;
	}

	/** 装载指定的购物车 */
	public Cart loadCart(Integer id) {
		return (Cart)dao.loadById(Cart.class, id);
	}

	/** 装载指定会员的购物车 */	
	public Cart loadCart(Member member) {		
		return (Cart)dao.loadObject("from Cart as a where a.member.id="+member.getId()+" and a.cartStatus=0");
	}

	/** 装载指定的选购记录 */
	public Cartselectedmer loadCartselectedmer(Integer id) {
		return (Cartselectedmer)dao.loadById(Cartselectedmer.class, id);
	}
	
	/** 装载属于指定购物车且选购了指定商品的选购记录 */	
	public Cartselectedmer loadCartselectedmer(Cart cart, Merchandise mer) {
		return (Cartselectedmer)dao.loadObject("from Cartselectedmer as a where a.merchandise.id="+mer.getId()+" and a.cart.id="+cart.getId());
	}	

	/** 新增或修改购物车 */
	public boolean saveOrUpdateCart(Cart cart) {
		boolean status = false;
		try{
			dao.saveOrUpdate(cart);
			status = true;
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return status;
	}

	/** 新增或修改选购记录 */
	public boolean saveOrUpdateCartselectedmer(Cartselectedmer cartselectedmer) {
		boolean status = false;
		try{
			dao.saveOrUpdate(cartselectedmer);
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
