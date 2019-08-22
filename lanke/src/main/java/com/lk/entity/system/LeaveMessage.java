package com.lk.entity.system;

import java.util.List;

import com.lk.util.PageData;



public class LeaveMessage {
	private String LM_ID;
	private String LM_DATE;
	private String LM_CONTENT;
	private String LM_USERID;
	private String LM_TYPEID;
	private String LM_TYPENAME;
	private String INTERNET_ID;
	private String STROE_ID;
	private String LM_USERNAME;
	private String LM_INTERNET_NAME;
	private List<Pictures> plist;
	private String LM_STROE_NAME;
	private String SYSNAME;
	private String LM_STATE;
	private String LM_BACKTIME;
	private String USER_ID;
	private String IS_VIEW;

	public String getIs_VIEW() {
		return IS_VIEW;
	}

	public void setIs_VIEW(String is_VIEW) {
		IS_VIEW = is_VIEW;
	}

	private String BACKCONTENT;
	public String getLM_TYPENAME() {
		return LM_TYPENAME;
	}
	public void setLM_TYPENAME(String lM_TYPENAME) {
		LM_TYPENAME = lM_TYPENAME;
	}
	private List<PageData> userList;
	public List<PageData> getUserList() {
		return userList;
	}
	public void setUserList(List<PageData> userList) {
		this.userList = userList;
	}
	public String getBACKCONTENT() {
		return BACKCONTENT;
	}
	public void setBACKCONTENT(String bACKCONTENT) {
		BACKCONTENT = bACKCONTENT;
	}
	public String getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}
	public String getLM_STATE() {
		return LM_STATE;
	}
	public void setLM_STATE(String lM_STATE) {
		LM_STATE = lM_STATE;
	}
	public String getLM_BACKTIME() {
		return LM_BACKTIME;
	}
	public void setLM_BACKTIME(String lM_BACKTIME) {
		LM_BACKTIME = lM_BACKTIME;
	}
	public String getLM_STROE_NAME() {
		return LM_STROE_NAME;
	}
	public String getINTERNET_ID() {
		return INTERNET_ID;
	}
	public void setINTERNET_ID(String iNTERNET_ID) {
		INTERNET_ID = iNTERNET_ID;
	}
	public void setLM_STROE_NAME(String lM_STROE_NAME) {
		LM_STROE_NAME = lM_STROE_NAME;
	}
	public List<Pictures> getPlist() {
		return plist;
	}
	public void setPlist(List<Pictures> plist) {
		this.plist = plist;
	}
	public String getLM_INTERNET_NAME() {
		return LM_INTERNET_NAME;
	}
	public void setLM_INTERNET_NAME(String lM_INTERNET_NAME) {
		LM_INTERNET_NAME = lM_INTERNET_NAME;
	}
	public String getLM_USERID() {
		return LM_USERID;
	}
	public void setLM_USERID(String lM_USERID) {
		LM_USERID = lM_USERID;
	}
	public String getLM_ID() {
		return LM_ID;
	}
	public void setLM_ID(String lM_ID) {
		LM_ID = lM_ID;
	}
	public String getLM_DATE() {
		return LM_DATE;
	}
	public void setLM_DATE(String lM_DATE) {
		LM_DATE = lM_DATE;
	}
	public String getLM_CONTENT() {
		return LM_CONTENT;
	}
	public void setLM_CONTENT(String lM_CONTENT) {
		LM_CONTENT = lM_CONTENT;
	}
	public String getLM_TYPEID() {
		return LM_TYPEID;
	}
	public void setLM_TYPEID(String lM_TYPEID) {
		LM_TYPEID = lM_TYPEID;
	}
	public String getSTROE_ID() {
		return STROE_ID;
	}
	public void setSTROE_ID(String sTROE_ID) {
		STROE_ID = sTROE_ID;
	}
	public String getLM_USERNAME() {
		return LM_USERNAME;
	}
	public void setLM_USERNAME(String lM_USERNAME) {
		LM_USERNAME = lM_USERNAME;
	}
	public String getSYSNAME() {
		return SYSNAME;
	}
	public void setSYSNAME(String sYSNAME) {
		SYSNAME = sYSNAME;
	}
	
}
