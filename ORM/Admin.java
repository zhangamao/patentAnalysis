package com.patent.ORM;

/**
 * Admin entity
 * @author M.Line
 *
 */
public class Admin implements java.io.Serializable{

	private Integer id;
	private String loginName;  //µÇÂ¼Ãû³Æ
	private String loginPwd;   //µÇÂ¼ÃÜÂë
	private String privileges;  //È¨ÏÞÉèÖÃ
	
	/**
	 * default Constructor
	 */
	public Admin(){
		
	}
	
	/**
	 * full constructor
	 * @param loginName
	 * @param loginPwd
	 * @param privileges
	 */
	public Admin(String loginName, String loginPwd, String privileges){
		
		this.loginName = loginName;
		this.loginPwd = loginPwd;
		this.privileges = privileges;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getPrivileges() {
		return privileges;
	}

	public void setPrivileges(String privileges) {
		this.privileges = privileges;
	}
	
	
}
