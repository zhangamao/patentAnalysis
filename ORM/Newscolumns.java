package com.patent.ORM;

/**新闻栏目的持久化类*/
public class Newscolumns implements java.io.Serializable{

	private Integer id;  //ID号
	private Newscolumns newscolumns;  //父栏目
	private String columnCode;  //新闻栏目编号
	private String columnName;  //新闻栏目名称
	
	public Newscolumns(){}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Newscolumns getNewscolumns() {
		return newscolumns;
	}

	public void setNewscolumns(Newscolumns newscolumns) {
		this.newscolumns = newscolumns;
	}

	public String getColumnCode() {
		return columnCode;
	}

	public void setColumnCode(String columnCode) {
		this.columnCode = columnCode;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	
	
}
