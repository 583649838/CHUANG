package com.bestplus.chuangshangjiuzhi.entity;

import com.bestplus.chuangshangjiuzhi.common.JsonKey;

import org.json.JSONObject;

/**
 * Created by maoamo on 2016/7/28.
 */
public class CS_User {
//"expertise":"跌打损伤",
// "gmtCreated":1469669954000,
// "gmtModified":1469669952000,
// "id":9,
// "isDel":1,
// "isUse":1,
// memberCode":"1000000121",
// "memberName":"王雪梅",
// "memberPhone":"13758836998",
// "password":"e10adc3949ba59abbe56e057f20f883e",
// "roleId":1},"
    private String expertise;
    private int id;
    private int isUse;
    private String memberCode;
    private String memberName;
    private String memberPhone;
    private int roleId;

    public CS_User(){

    }
    public CS_User(JSONObject o){
        this.expertise = o.optString("expertise", "");
        this.id = o.optInt("id", 0);
        this.isUse = o.optInt("isUse", 0);
        this.memberCode = o.optString("memberCode", "");;
        this.memberName = o.optString("memberName", "");;
        this.memberPhone = o.optString("memberPhone", "");;
        this.roleId = o.optInt("roleId", 0);
    }

    public String getExpertise() {
        return expertise;
    }
    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public String getMemberCode() {
        return memberCode;
    }
    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public String getMemberName() {
        return memberName;
    }
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberPhone() {
        return memberPhone;
    }
    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone;
    }

    public int getId() {
        return id;
    }
    public void seId(int id) {
        this.id = id;
    }

    public int getIsUse() {
        return isUse;
    }
    public void seIsUse(int isUse) {
        this.isUse = isUse;
    }

    public int getRoleId() {
        return roleId;
    }
    public void seRoleId(int roleId) {
        this.roleId = roleId;
    }
}
