package com.patent.struts.action;

import java.io.UnsupportedEncodingException;
import java.util.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.ServletActionContext;
import com.patent.ORM.*;
import com.patent.service.*;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/** 订单处理控制器 */
@SuppressWarnings("serial")
public class OrdersAction extends ActionSupport implements ModelDriven<Orders>{
	/** 通过依赖注入OrderService、CartService与MemberService组件实例 */
	OrderService service;
	CartService cartService;
	MemberService memberService;
	
	/** 订单管理所有请求中常用的参数值 */
	private String actionMsg;	//Action间传递的消息参数
	private List<Orders> ordersList;//订单列表
	private List<Cartselectedmer> selList;//选购记录列表
	
	//采用模型驱动
	private Orders model=new Orders();//用于封装订单属性模型
	public Orders getModel() {
		return model;
	}
		
	/** 处理浏览订单请求 */
	public String browseOrders(){
		if(actionMsg!=null){
			try {
				actionMsg = new String(actionMsg.getBytes("ISO8859-1"),"gbk");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			addActionMessage(actionMsg);
		}
		ordersList = service.browseOrders();
		return SUCCESS;
	}
	
	/** 处理浏览当前会员所有订单的请求 */
	public String loadAllOrders(){
		if(actionMsg!=null){
			try {
				actionMsg = new String(actionMsg.getBytes("ISO8859-1"),"gbk");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			addActionMessage(actionMsg);
		}
		Member member = (Member)ServletActionContext.getRequest().getSession().getAttribute("member");
		if (member!=null){//当前会员进行订单管理
			ordersList = service.loadAllOrders(member);
		}else{//会员尚未登录或登录失效
			addActionMessage(getText("orders_add_notlogin"));
		}
		return SUCCESS;
	}	
	
	/** 处理新增订单请求 */
	public String addOrders(){
		Member member = (Member)ServletActionContext.getRequest().getSession().getAttribute("member");
		Orders order = null;
		Cart cart = null;
		if (member!=null){
			order = service.loadOrders(member);
			if (order==null){//如果尚无订单则为该用户创建一个订单
				//装载该会员的未结帐购物车
				cart = cartService.loadCart(member);
				if (cart==null){//购物车不存在，不能生成订单
					addActionMessage(getText("orders_add_nocart"));
				}else{
					if(cart.getMoney()>0){//购物车中有待结帐商品，可生成订单
						order = new Orders();
						order.setCart(cart);//订单与当前购物车关联
						order.setMember(member);//订单与当前会员关联
						order.setOrderDate(new Date());
						order.setOrderStatus(0);//未结帐状态
						order.setOrderNo(String.valueOf(System.currentTimeMillis()));//订单号
						if (service.saveOrUpdateOrders(order)){//订单创建成功
							try {
								//快速复制源对象中的所有属性到目标对象中
								BeanUtils.copyProperties(model, order);
								//选购记录清单
								selList = cartService.browseCartselectedmer(cart);
							} catch (Exception e) {
								e.printStackTrace();
							}							
						}else{//订单创建失败
							addActionMessage(getText("orders_add_fail"));
						}						
					}else{//购物车中无商品，不能生成订单
						addActionMessage(getText("orders_add_nocart"));
					}
				}				
			}else{//已经存在一张未结帐订单
				try {
					//快速复制源对象中的所有属性到目标对象中
					BeanUtils.copyProperties(model, order);
					//选购记录清单
					selList = cartService.browseCartselectedmer(order.getCart());
				} catch (Exception e) {
					e.printStackTrace();
				}				
			}
		}else{//会员尚未登录,无法进行在线购物
			addActionMessage(getText("orders_add_notlogin"));
		}
		return SUCCESS;
	}
	
	/** 处理查看订单请求 */
	public String viewOrders(){
		if (model.getId()!=null){
			Orders order = service.loadOrders(model.getId());
			if(order==null){
				addActionMessage(getText("orders_view_fail"));
				return INPUT;
			}else{
				try {
					//快速复制源对象中的所有属性到目标对象中
					BeanUtils.copyProperties(model, order);
					//选购记录清单
					selList = cartService.browseCartselectedmer(order.getCart());
					return SUCCESS;
				} catch (Exception e) {
					e.printStackTrace();
					return INPUT;
				}				
			}
		}else{
			addActionMessage(getText("orders_view_fail"));
			return INPUT;
		}		
	}
	
	/** 处理删除指定订单请求 */
	public String delOrders(){
		if (model.getId()!=null){
			if (service.delOrders(model.getId())){
				actionMsg = getText("orders_delete_succ");
			}else{
				actionMsg = getText("orders_delete_fail");
			}			
		}else{
			actionMsg = getText("orders_delete_fail");
		}
		return INPUT;	
	}
	
	/** 处理修改指定订单状态的请求 */
	public String updateOrdersStatus(){
		if (model.getId()!=null){
			Orders order = service.loadOrders(model.getId());
			if(order==null){
				actionMsg = getText("orders_update_fail");
			}else{
				order.setOrderStatus(model.getOrderStatus());
				if(service.saveOrUpdateOrders(order)){
					actionMsg = getText("orders_update_succ");
					//如果结单，则为该订单的注册会员增加相应的积分
					if (model.getOrderStatus().intValue()==4){
						Member member = order.getMember();
						int integral = member.getIntegral().intValue()+(int)(order.getCart().getMoney()/10);
						member.setIntegral(integral);
						memberService.saveOrUpdateMember(member);						
						//如果是会员提交的结单请求，则同步更新session中的会员资料
						if (ServletActionContext.getRequest().getSession().getAttribute("member")!=null){
							member = (Member)ServletActionContext.getRequest().getSession().getAttribute("member");
							member.setIntegral(integral);
							ServletActionContext.getRequest().getSession().setAttribute("member",member);
						}
					}
				}else{
					actionMsg = getText("orders_update_fail");
				}
			}		
		}else{
			actionMsg = getText("orders_update_fail");
		}
		return INPUT;	
	}
	
	/** 处理提交指定订单的请求 */
	public String submitOrders(){
		if (model.getId()!=null){
			Orders order = service.loadOrders(model.getId());
			if(order==null){
				actionMsg = getText("orders_submit_fail");
			}else{
				//判断会员的联系资料是否完整
				Member member = order.getMember();
				if (member.getMemberName()==null || member.getPhone()==null || member.getAddress()==null || member.getZip()==null){
					actionMsg = getText("orders_submit_warning");
				}else{
					Cart cart = order.getCart();
					cart.setCartStatus(1);//购物车状态设置成1，表示已经结帐
					if(cartService.saveOrUpdateCart(cart)){
						order.setOrderStatus(1);//订单状态设置成1，表示成功提交但未受理					
						if(service.saveOrUpdateOrders(order)){
							addActionMessage(getText("orders_submit_succ"));
							try {
								//快速复制源对象中的所有属性到目标对象中
								BeanUtils.copyProperties(model, order);
							} catch (Exception e) {
								e.printStackTrace();
							}						
							return SUCCESS;
						}else{
							actionMsg = getText("orders_submit_fail");
						}						
					}else{
						actionMsg = getText("orders_submit_fail");
					}
				}
			}		
		}else{
			actionMsg = getText("orders_submit_fail");
		}
		return INPUT;	
	}	

	public OrderService getService() {
		return service;
	}

	public void setService(OrderService service) {
		this.service = service;
	}

	public CartService getCartService() {
		return cartService;
	}

	public void setCartService(CartService cartService) {
		this.cartService = cartService;
	}

	public String getActionMsg() {
		return actionMsg;
	}

	public void setActionMsg(String actionMsg) {
		this.actionMsg = actionMsg;
	}

	public List<Orders> getOrdersList() {
		return ordersList;
	}

	public void setOrdersList(List<Orders> ordersList) {
		this.ordersList = ordersList;
	}

	public List<Cartselectedmer> getSelList() {
		return selList;
	}

	public void setSelList(List<Cartselectedmer> selList) {
		this.selList = selList;
	}

	public MemberService getMemberService() {
		return memberService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}	
}