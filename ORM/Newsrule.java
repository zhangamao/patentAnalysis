package com.patent.ORM;

public class Newsrule implements java.io.Serializable {
	private Integer id;
	private Newscolumns newscolumns;
	private String ruleName;
	private String encode;
	private String url;
	private String midEnd;
	private String midBegin;
	private String contentEnd;
	private String contentBegin;
	private String fromEnd;
	private String fromBegin;
	private String authorEnd;
	private String authorBegin;
	private String titleEnd;
	private String titleBegin;
	private String listEnd;
	private String listBegin;

	public Newsrule(){}

	/** full constructor */
	public Newsrule(Newscolumns newscolumns, String ruleName, String encode,
			String url, String midEnd, String midBegin, String contentEnd,
			String contentBegin, String fromEnd, String fromBegin,
			String authorEnd, String authorBegin, String titleEnd,
			String titleBegin, String listEnd, String listBegin) {
		this.newscolumns = newscolumns;
		this.ruleName = ruleName;
		this.encode = encode;
		this.url = url;
		this.midEnd = midEnd;
		this.midBegin = midBegin;
		this.contentEnd = contentEnd;
		this.contentBegin = contentBegin;
		this.fromEnd = fromEnd;
		this.fromBegin = fromBegin;
		this.authorEnd = authorEnd;
		this.authorBegin = authorBegin;
		this.titleEnd = titleEnd;
		this.titleBegin = titleBegin;
		this.listEnd = listEnd;
		this.listBegin = listBegin;
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

	public String getRuleName() {
		return this.ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getEncode() {
		return this.encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMidEnd() {
		return this.midEnd;
	}

	public void setMidEnd(String midEnd) {
		this.midEnd = midEnd;
	}

	public String getMidBegin() {
		return this.midBegin;
	}

	public void setMidBegin(String midBegin) {
		this.midBegin = midBegin;
	}

	public String getContentEnd() {
		return this.contentEnd;
	}

	public void setContentEnd(String contentEnd) {
		this.contentEnd = contentEnd;
	}

	public String getContentBegin() {
		return this.contentBegin;
	}

	public void setContentBegin(String contentBegin) {
		this.contentBegin = contentBegin;
	}

	public String getFromEnd() {
		return this.fromEnd;
	}

	public void setFromEnd(String fromEnd) {
		this.fromEnd = fromEnd;
	}

	public String getFromBegin() {
		return this.fromBegin;
	}

	public void setFromBegin(String fromBegin) {
		this.fromBegin = fromBegin;
	}

	public String getAuthorEnd() {
		return this.authorEnd;
	}

	public void setAuthorEnd(String authorEnd) {
		this.authorEnd = authorEnd;
	}

	public String getAuthorBegin() {
		return this.authorBegin;
	}

	public void setAuthorBegin(String authorBegin) {
		this.authorBegin = authorBegin;
	}

	public String getTitleEnd() {
		return this.titleEnd;
	}

	public void setTitleEnd(String titleEnd) {
		this.titleEnd = titleEnd;
	}

	public String getTitleBegin() {
		return this.titleBegin;
	}

	public void setTitleBegin(String titleBegin) {
		this.titleBegin = titleBegin;
	}

	public String getListEnd() {
		return this.listEnd;
	}

	public void setListEnd(String listEnd) {
		this.listEnd = listEnd;
	}

	public String getListBegin() {
		return this.listBegin;
	}

	public void setListBegin(String listBegin) {
		this.listBegin = listBegin;
	}

}