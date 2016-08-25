package com.bestplus.chuangshangjiuzhi.entity;

import org.json.JSONObject;

import com.bestplus.chuangshangjiuzhi.common.JsonKey;

public class User {
	private String accountname;
	private String id;
	private String ksId;
	private String ksName;
	private String loginName;
	private String no;
	private String sex;
	
	public User(){
		
	}
	public User(JSONObject o){
		accountname=o.optString(JsonKey.accountname,"");
		id=o.optString(JsonKey.id,"");
		ksId=o.optString(JsonKey.ksId,"");
		ksName=o.optString(JsonKey.ksName,"");
		loginName=o.optString(JsonKey.loginName,"");
		no=o.optString(JsonKey.no,"");
		sex=o.optString(JsonKey.account_sex);
		
	}
	public String getAccountname() {
		return accountname;
	}
	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getKsId() {
		return ksId;
	}
	public void setKsId(String ksId) {
		this.ksId = ksId;
	}
	public String getKsName() {
		return ksName;
	}
	public void setKsName(String ksName) {
		this.ksName = ksName;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}

}
