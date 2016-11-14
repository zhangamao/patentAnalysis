package com.patent.ORM;

import java.util.Date;

public class News implements java.io.Serializable {

	// Fields

	private Integer id;
	private Newscolumns newscolumns;
	private String title;
	private String content;
	private String abstract_;
	private String keyWords;
	private Integer isPicNews;
	private String picture;
	private String from;
	private Date cdate;
	private String author;
	private String editor;
	private Integer clicks;
	private Integer newsType;
	private Integer priority;
	private Integer red;
	private String htmlPath;
	private Integer status;

	// Constructors

	/** default constructor */
	public News() {
	}

	/** full constructor */
	public News(Newscolumns newscolumns, String title, String content,
			String abstract_, String keyWords, Integer isPicNews,
			String picture, String from, Date cdate, String author,
			String editor, Integer clicks, Integer newsType, Integer priority,
			Integer red, String htmlPath, Integer status) {
		this.newscolumns = newscolumns;
		this.title = title;
		this.content = content;
		this.abstract_ = abstract_;
		this.keyWords = keyWords;
		this.isPicNews = isPicNews;
		this.picture = picture;
		this.from = from;
		this.cdate = cdate;
		this.author = author;
		this.editor = editor;
		this.clicks = clicks;
		this.newsType = newsType;
		this.priority = priority;
		this.red = red;
		this.htmlPath = htmlPath;
		this.status = status;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Newscolumns getNewscolumns() {
		return this.newscolumns;
	}

	public void setNewscolumns(Newscolumns newscolumns) {
		this.newscolumns = newscolumns;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAbstract_() {
		return this.abstract_;
	}

	public void setAbstract_(String abstract_) {
		this.abstract_ = abstract_;
	}

	public String getKeyWords() {
		return this.keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public Integer getIsPicNews() {
		return this.isPicNews;
	}

	public void setIsPicNews(Integer isPicNews) {
		this.isPicNews = isPicNews;
	}

	public String getPicture() {
		return this.picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getFrom() {
		return this.from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public Date getCdate() {
		return this.cdate;
	}

	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getEditor() {
		return this.editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public Integer getClicks() {
		return this.clicks;
	}

	public void setClicks(Integer clicks) {
		this.clicks = clicks;
	}

	public Integer getNewsType() {
		return this.newsType;
	}

	public void setNewsType(Integer newsType) {
		this.newsType = newsType;
	}

	public Integer getPriority() {
		return this.priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Integer getRed() {
		return this.red;
	}

	public void setRed(Integer red) {
		this.red = red;
	}

	public String getHtmlPath() {
		return this.htmlPath;
	}

	public void setHtmlPath(String htmlPath) {
		this.htmlPath = htmlPath;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}