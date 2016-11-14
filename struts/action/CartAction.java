package com.patent.struts.action;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.ServletActionContext;
import com.patent.ORM.*;
import com.patent.service.*;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/** 购物车处理控制器 */
@SuppressWarnings("serial")
public class CartAction extends ActionSupport implements ModelDriven<Cart>{
	/** 通过依赖注入CartService与MerchandiseService组件实例 */
	CartService service;
	MerchandiseService merService;
	
	/** 购物车管理所有请求中常用的参数值 */
	private String actionMsg;	//Action间传递的消息参数
	private List<Cart> cartList;//购物车列表
	private List<Cartselectedmer> selList;//选购记录列表
	private Integer merId;		//选购的商品ID
	private Integer number;		//选购数量
	private Integer selId;		//选购记录ID
	
	//采用模型驱动
	private Cart model=new Cart();//用于封装购物车属性模型
	public Cart getModel() {
		return model;
	}
		
	/** 处理浏览购物车请求 */
	public String browseCart(){
		if(actionMsg!=null){
			try {
				actionMsg = new String(actionMsg.getBytes("ISO8859-1"),"gbk");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			addActionMessage(actionMsg);
		}
		cartList = service.browseCart();//调用业务逻辑组件取得购物车列表
		return SUCCESS;
	}
	
	/** 处理购买请求 */
	public String addToCart(){
		Member member = (Member)ServletActionContext.getRequest().getSession().getAttribute("member");
		Cart cart = null;
		Merchandise mer = null;
		Cartselectedmer sel = null;
		double price = 0;
		if (member!=null){
			//装载当前注册会员的购物车
			cart = service.loadCart(member);
			if (cart==null){//如果尚无购物车则为该用户创建一个购物车
				cart = new Cart();
				//购物车的初始状态为0,表示尚未结帐
				cart.setCartStatus(0);
				//购物车默认总金额为0
				cart.setMoney(Double.valueOf(0));
				//购物车与当前注册会员进行关联
				cart.setMember(member);
				//持久化购物车
				if (!service.saveOrUpdateCart(cart)){
					actionMsg = getText("cart_add_fail");
				}				
			}
			//装载被选购的商品
			mer = merService.loadMerchandise(merId);
			if (mer!=null){//该商品有效
				//检查是否已存在相同的选购记录
				sel = service.loadCartselectedmer(cart, mer);
				if (sel==null){//不存在相同的选购记录
					//创建一条选购记录
					sel = new Cartselectedmer();
					sel.setCart(cart);
					sel.setMerchandise(mer);
					sel.setNumber(1);//默认选购数量为1
					//商品划价
					if (mer.getSpecial().intValue()==1){//特价商品,购买价格即为特价
						price = mer.getSprice();
					}else{//非特价商品,购买价格为优惠后的市场价
						price = mer.getPrice()*(100-member.getMemberlevel().getFavourable())/100;
					}
					sel.setPrice(price);
					sel.setMoney(price);
					//重新计算购物车总金额
					cart.setMoney(cart.getMoney().doubleValue()+price);
				}else{//已存在相同的选购记录
					sel.setNumber(sel.getNumber()+1);//选购数量加1
					sel.setMoney(sel.getMoney()+sel.getPrice());//金额加一个单价
					//重新计算购物车总金额
					cart.setMoney(cart.getMoney().doubleValue()+sel.getPrice());
				}
				//持久化购物车及其选购记录
				if (service.saveOrUpdateCart(cart)){
					if (service.saveOrUpdateCartselectedmer(sel)){
						actionMsg = getText("cart_add_succ");
					}else{
						actionMsg = getText("cart_add_fail");				
					}					
				}else{
					actionMsg = getText("cart_add_fail");
				}
					
			}else{//指定商品不存在
				actionMsg = getText("cart_add_fail");
			}		
		}else{//会员尚未登录,无法进行在线购物
			actionMsg = getText("cart_add_fail");
		}
		return "toViewCart";
	}
	
	/** 处理查看购物车请求 */
	public String viewCart(){
		//接收并处理其它Action传递的消息
		if(actionMsg!=null){
			try {
				actionMsg = new String(actionMsg.getBytes("ISO8859-1"),"gbk");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			addActionMessage(actionMsg);
		}		
		if (model.getId()!=null){//后台管理员查看购物车详情
			Cart tempCart = service.loadCart(model.getId());
			if (tempCart!=null){
				try {
					//快速复制源对象中的所有属性到目标对象中
					BeanUtils.copyProperties(model, tempCart);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//装载该购物车对应的商品选购记录集
				selList = service.browseCartselectedmer(tempCart);
				return SUCCESS;
			}else{
				actionMsg = getText("cart_view_fail");
				return "toBrowseCart";
			}	
		}else{//注册会员查看自己的购物车详情
			Member member = (Member)ServletActionContext.getRequest().getSession().getAttribute("member");
			if (member!=null){
				//装载当前注册会员的购物车
				Cart cart = service.loadCart(member);
				if (cart==null){//如果尚无购物车则为该用户创建一个购物车
					cart = new Cart();
					//购物车的初始状态为0,表示尚未结帐
					cart.setCartStatus(0);
					//购物车默认总金额为0
					cart.setMoney(Double.valueOf(0));
					//购物车与当前注册会员进行关联
					cart.setMember(member);
					//持久化购物车
					if (!service.saveOrUpdateCart(cart)){
						addActionMessage(getText("cart_create_fail"));
					}				
				}else{//装载当前购物车的商品选购记录
					selList = service.browseCartselectedmer(cart);					
				}
				try {
					//快速复制源对象中的所有属性到目标对象中
					BeanUtils.copyProperties(model, cart);
				} catch (Exception e) {
					e.printStackTrace();
				}				
			}else{
				addActionMessage(getText("cart_view_fail"));
			}
			return SUCCESS;
		}		
	}
	
	/** 处理删除指定选购记录请求 */
	public String delCartselectedmer(){
		Cartselectedmer sel = null;
		Cart cart = null;
		double money = 0;
		if (selId!=null){
			//装载指定的选购记录
			sel = service.loadCartselectedmer(selId);
			if (sel!=null){
				money = sel.getMoney();
				cart = sel.getCart();
				//更新购物车的总金额
				cart.setMoney(cart.getMoney().doubleValue()-money);
				if (service.saveOrUpdateCart(cart)){
					//删除指定的选购记录
					if (service.delCartselectedmer(selId)){
						actionMsg = getText("cart_delselect_succ");
					}else{
						actionMsg = getText("cart_delselect_fail");
					}					
				}else{
					actionMsg = getText("cart_delselect_fail");
				}				
			}else{
				actionMsg = getText("cart_delselect_fail");
			}
			
		}else{
			actionMsg = getText("cart_delselect_fail");
		}
		return INPUT;		
	}
	
	/** 处理修改选购数量请求 */
	public String updateSelectedNumber(){
		Cartselectedmer sel = null;
		Cart cart = null;
		double money = 0;
		if (selId!=null && number!=null){
			//装载指定的选购记录
			sel = service.loadCartselectedmer(selId);
			if (sel!=null){
				money = sel.getPrice()*number;
				//更新购物车的总金额
				cart = sel.getCart();
				cart.setMoney(cart.getMoney().doubleValue()-sel.getMoney()+money);				
				//更新选购数量及金额合计
				sel.setNumber(number);
				sel.setMoney(money);
				if (service.saveOrUpdateCart(cart)){
					//更新指定的选购记录
					if (service.saveOrUpdateCartselectedmer(sel)){
						actionMsg = getText("cart_updatenumber_succ");
					}else{
						actionMsg = getText("cart_updatenumber_fail");
					}					
				}else{
					actionMsg = getText("cart_updatenumber_fail");
				}				
			}else{
				actionMsg = getText("cart_updatenumber_fail");
			}			
		}else{
			actionMsg = getText("cart_updatenumber_fail");
		}
		return INPUT;		
	}
	
	/** 处理清空购物车请求 */
	public String clearCart(){
		if (model.getId()!=null){
			//装载指定的购物车
			Cart cart = service.loadCart(model.getId());
			if (cart!=null){		
				//购物车总金额清零
				cart.setMoney(Double.valueOf(0));
				if (service.saveOrUpdateCart(cart)){
					//清空当前购物车的所有选购记录
					if (service.clearCart(model.getId())){
						actionMsg = getText("cart_clear_succ");
					}else{
						actionMsg = getText("cart_clear_fail");
					}									
				}else{
					actionMsg = getText("cart_clear_fail");	
				}				
			}else{
				actionMsg = getText("cart_clear_fail");
			}				
		}else{
			actionMsg = getText("cart_clear_fail");
		}
		return "toViewCart";
	}

	public CartService getService() {
		return service;
	}
	public void setService(CartService service) {
		this.service = service;
	}
	public String getActionMsg() {
		return actionMsg;
	}

	public void setActionMsg(String actionMsg) {
		this.actionMsg = actionMsg;
	}

	public List<Cart> getCartList() {
		return cartList;
	}

	public void setCartList(List<Cart> cartList) {
		this.cartList = cartList;
	}

	public Integer getMerId() {
		return merId;
	}

	public void setMerId(Integer merId) {
		this.merId = merId;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getSelId() {
		return selId;
	}

	public void setSelId(Integer selId) {
		this.selId = selId;
	}

	public List<Cartselectedmer> getSelList() {
		return selList;
	}

	public void setSelList(List<Cartselectedmer> selList) {
		this.selList = selList;
	}

	public MerchandiseService getMerService() {
		return merService;
	}

	public void setMerService(MerchandiseService merService) {
		this.merService = merService;
	}
}