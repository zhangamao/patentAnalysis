package com.patent.ORM;

public class Patent implements java.io.Serializable{

	private Integer id;                  //专利id
	private String applicationNo;    //申请号
	private String patentName;       //专利名称
	private String applicationDate;  //专利申请日期
	private String lawState;         //法律状态
	private String MIPC;             //主分类号
	private String IPC;              //分类号
	private String applicant;        //申请人
	private String inventor;         //发明人
	private String publicNumber;     //公开号
	private String publicDate;       //公开日期
	private String agentORG;         //代理组织
	private String address;          //地址
	private String priority;         //优先权
	private String provinceCode;     //省代码号
	private String abs;              //摘要
	private String mainClaim;        //主权利项
	
	
	public Patent(){
		
	}
	
	public Patent(String applicationNo, String patentName, String IPC, String address){
		
		this.applicationNo = applicationNo;
		this.patentName = patentName;
		this.IPC = IPC;
		this.address = address;
	}
    

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}

	public String getPatentName() {
		return patentName;
	}

	public void setPatentName(String patentName) {
		this.patentName = patentName;
	}

	public String getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(String applicationDate) {
		this.applicationDate = applicationDate;
	}

	public String getLawState() {
		return lawState;
	}

	public void setLawState(String lawState) {
		this.lawState = lawState;
	}

	public String getMIPC() {
		return MIPC;
	}

	public void setMIPC(String mIPC) {
		MIPC = mIPC;
	}

	public String getIPC() {
		return IPC;
	}

	public void setIPC(String iPC) {
		IPC = iPC;
	}

	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	public String getInventor() {
		return inventor;
	}

	public void setInventor(String inventor) {
		this.inventor = inventor;
	}

	public String getPublicNumber() {
		return publicNumber;
	}

	public void setPublicNumber(String publicNumber) {
		this.publicNumber = publicNumber;
	}

	public String getPublicDate() {
		return publicDate;
	}

	public void setPublicDate(String publicDate) {
		this.publicDate = publicDate;
	}

	public String getAgentORG() {
		return agentORG;
	}

	public void setAgentORG(String agentORG) {
		this.agentORG = agentORG;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getAbs() {
		return abs;
	}

	public void setAbs(String abs) {
		this.abs = abs;
	}

	public String getMainClaim() {
		return mainClaim;
	}

	public void setMainClaim(String mainClaim) {
		this.mainClaim = mainClaim;
	}
	
	
	
}
