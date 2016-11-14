package com.patent.ORM;

import java.util.Date;

public class Merchandise implements java.io.Serializable{

	private Integer id;
	private Category category;
	private String merName;
	private Double price;
	private Double sprice;
	private String merModel;
	private String picture;
	private String video;
	private String merDesc;
	private String manufacturer;
	private Date leaveFactoryDate;
	private Integer special;
	private String htmlPath;
	private Integer status;	
	
	/** default constructor */
	public Merchandise() {
	}

	/** full constructor */
	public Merchandise(Category category, String merName, Double price,
			Double sprice, String merModel, String picture, String video,
			String merDesc, String manufacturer, Date leaveFactoryDate,
			Integer special, String htmlPath) {
		this.category = category;
		this.merName = merName;
		this.price = price;
		this.sprice = sprice;
		this.merModel = merModel;
		this.picture = picture;
		this.video = video;
		this.merDesc = merDesc;
		this.manufacturer = manufacturer;
		this.leaveFactoryDate = leaveFactoryDate;
		this.special = special;
		this.htmlPath = htmlPath;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getMerName() {
		return merName;
	}

	public void setMerName(String merName) {
		this.merName = merName;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getSprice() {
		return sprice;
	}

	public void setSprice(Double sprice) {
		this.sprice = sprice;
	}

	public String getMerModel() {
		return merModel;
	}

	public void setMerModel(String merModel) {
		this.merModel = merModel;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getMerDesc() {
		return merDesc;
	}

	public void setMerDesc(String merDesc) {
		this.merDesc = merDesc;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Date getLeaveFactoryDate() {
		return leaveFactoryDate;
	}

	public void setLeaveFactoryDate(Date leaveFactoryDate) {
		this.leaveFactoryDate = leaveFactoryDate;
	}

	public Integer getSpecial() {
		return special;
	}

	public void setSpecial(Integer special) {
		this.special = special;
	}

	public String getHtmlPath() {
		return htmlPath;
	}

	public void setHtmlPath(String htmlPath) {
		this.htmlPath = htmlPath;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
}
