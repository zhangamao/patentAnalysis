package com.patent.ORM;

public class Cartselectedmer implements java.io.Serializable{

	private Integer id;
	private Merchandise merchandise;
	private Cart cart;
	private Integer number;
    private Double price;
    private Double money;
    
    public Cartselectedmer(){
    	
    }
    public Cartselectedmer(Merchandise merchandise, Cart cart,
    		Integer number, Double price, Double money){
    	
    	this.merchandise = merchandise;
    	this.cart = cart;
    	this.number = number;
    	this.price = price;
    	this.money = money;
    }
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Merchandise getMerchandise() {
		return merchandise;
	}
	public void setMerchandise(Merchandise merchandise) {
		this.merchandise = merchandise;
	}
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
    
    
}
