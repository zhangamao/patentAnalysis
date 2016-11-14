package com.crawl;

public class Patent {

	private String image; //专利图片
	
	private String name; //专利名称	
	
	private String type;//专利类型
	
	private String publicNumber; //公开号
	
	private String lw; //法律装填
	
	private String applicant;//申请人
	
	private String applyDate; //申请时间
	
	private String IPC; //分类号
	
	private String introduce; //专利摘要
	
	
	private int totalHits; //命中率
	private int totalTime; //花费时间
	
	
	
	public int getTotalHits() {
		return totalHits;
	}
	public void setTotalHits(int totalHits) {
		this.totalHits = totalHits;
	}
	public int getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPublicNumber() {
		return publicNumber;
	}
	public void setPublicNumber(String publicNumber) {
		this.publicNumber = publicNumber;
	}
	public String getLw() {
		return lw;
	}
	public void setLw(String lw) {
		this.lw = lw;
	}
	public String getApplicant() {
		return applicant;
	}
	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}
	public String getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}
	public String getIPC() {
		return IPC;
	}
	public void setIPC(String iPC) {
		IPC = iPC;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	
	
}
