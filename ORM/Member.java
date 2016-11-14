package com.patent.ORM;

import java.util.Date;

public class Member implements java.io.Serializable{

	// Fields

	private Integer id;
	private Memberlevel memberlevel;
	private String loginName;
	private String loginPwd;
	private String memberName;
	private String phone;
	private String address;
	private String zip;
	private Date regDate;
	private Date lastDate;
	private Integer loginTimes;
	private String email;
	private Integer integral;
	
	/**
	 * default constructor
	 */
	public Member(){
		
	}
	
	public Member(Memberlevel memberlevel, String loginName, String loginPwd,
			String memberName, String phone, String address, String zip,
			Date regDate, Date lastDate, Integer loginTimes, String email){
		this.memberlevel = memberlevel;
		this.loginName = loginName;
		this.loginPwd = loginPwd;
		this.memberName = memberName;
		this.phone = phone;
		this.address = address;
		this.zip = zip;
		this.regDate = regDate;
		this.lastDate = lastDate;
		this.loginTimes = loginTimes;
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Memberlevel getMemberlevel() {
		return memberlevel;
	}

	public void setMemberlevel(Memberlevel memberlevel) {
		this.memberlevel = memberlevel;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	public Integer getLoginTimes() {
		return loginTimes;
	}

	public void setLoginTimes(Integer loginTimes) {
		this.loginTimes = loginTimes;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
	
	
}
