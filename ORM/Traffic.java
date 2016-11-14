package com.patent.ORM;

import java.util.Date;

public class Traffic implements java.io.Serializable{

	private Integer id;
	private String sourceUrl;
	private String targetUrl;
	private String ip;
	private String area;
	private Date visitDate;
	
	public Traffic(){}
	
	public Traffic(String souceUrl, String targetUrl, 
			String ip, String area, Date visitDate){
		this.sourceUrl = souceUrl;
		this.targetUrl = targetUrl;
		this.ip = ip;
		this.area = area;
		this.visitDate = visitDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}
	
	
}
