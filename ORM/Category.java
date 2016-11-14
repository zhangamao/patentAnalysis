package com.patent.ORM;

/**
 * Category entity
 * @author M.Line
 *
 */
public class Category implements java.io.Serializable{

	private Integer id;
	private Category category;
	private String cateName;
	
	
	public Category(){}
	
	public Category(Category category, String cateName){
		this.category = category;
		this.cateName = cateName;
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

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}
	
	
}
