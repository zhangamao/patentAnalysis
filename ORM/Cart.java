package com.patent.ORM;

/**
 * Cart entity
 * @author M.Line
 *
 */
public class Cart implements java.io.Serializable{

	private Integer id;
	private Member member;
	private Double money;
	private Integer cartStatus;
	
	/**
	 * default constructor
	 */
	public Cart(){
		
	}
	
	/**
	 * full constructor
	 * @param member  会员id
	 * @param money  单价
	 * @param cartStatus  订单状态
	 */
	public Cart(Member member, Double money, Integer cartStatus){
		this.member = member;
		this.money = money;
		this.cartStatus = cartStatus;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Integer getCartStatus() {
		return cartStatus;
	}

	public void setCartStatus(Integer cartStatus) {
		this.cartStatus = cartStatus;
	}
	
	
}
