package com.patent.service;

import java.util.List;

import com.patent.ORM.*;

/** 购物车管理业务逻辑接口 */
public interface CartService {

	/** 浏览购物车 */
	public List<Cart> browseCart();	
	/** 装载指定的购物车 */	
	public Cart loadCart(Integer id);
	/** 装载指定会员的购物车 */	
	public Cart loadCart(Member member);	
	/** 删除指定的购物车 */	
	public boolean delCart(Integer id);
	/** 清空指定的购物车 */	
	public boolean clearCart(Integer id);		
	/** 新增或修改购物车 */
	public boolean saveOrUpdateCart(Cart cart);
	
	/** 浏览指定购物车的选购记录 */
	public List<Cartselectedmer> browseCartselectedmer(Cart cart);	
	/** 装载指定的选购记录 */	
	public Cartselectedmer loadCartselectedmer(Integer id);
	/** 装载属于指定购物车且选购了指定商品的选购记录 */	
	public Cartselectedmer loadCartselectedmer(Cart cart,Merchandise mer);	
	/** 删除指定的选购记录 */	
	public boolean delCartselectedmer(Integer id);	
	/** 新增或修改选购记录 */
	public boolean saveOrUpdateCartselectedmer(Cartselectedmer cartselectedmer);	
	
}
