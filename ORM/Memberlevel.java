package com.patent.ORM;

/**会员级别的持久化类*/
public class Memberlevel implements java.io.Serializable{

	private Integer id;
	private String levelName;  //等级名称
	private Integer integral;  
	private Integer favourable;
	
	public Memberlevel(){}
	
	public Memberlevel(String levelName, Integer favourable){
		this.levelName = levelName;
		this.favourable = favourable;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public Integer getFavourable() {
		return favourable;
	}

	public void setFavourable(Integer favourable) {
		this.favourable = favourable;
	}
	
	
}
