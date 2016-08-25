package com.bestplus.chuangshangjiuzhi.entity;

import org.json.JSONObject;

import com.bestplus.chuangshangjiuzhi.common.JsonKey;

public class BingrenInfo {
	private String ItemHuanzheId; //病例号
	private String ItemHuanzheName; //患者姓名
	
	private String ItemShenfengzhenghaoma; //身份证号码
	private String ItemXingbie; //性别
	private String ItemNianling; //年龄
	private String ItemChushengriqi; //出生日期
	private String ItemShengao; //身高
	private String ItemTizhong; //体重
	private String ItemHuanZheDianhua; //患者电话
	private String ItemJiatinglianxiren; //家庭联系人
	private String ItemJiatinglianxirenDianhua; //家庭联系人电话
	private boolean invalidate = false;
	
	public BingrenInfo() {
		// TODO Auto-generated constructor stub
	}

	public BingrenInfo(int temp){
        switch (temp){
            case 0:
                ItemHuanzheId = "5556644";
                ItemHuanzheName = "郭靖";
                ItemShenfengzhenghaoma = "320223198202036544";
                ItemXingbie=  "男";
                ItemNianling = "44";
                ItemChushengriqi = "1972-12-10";
                ItemShengao = "188厘米";
                ItemTizhong = "90公斤";
                ItemHuanZheDianhua = "13500000009";
                ItemJiatinglianxiren = "黄蓉";
                ItemJiatinglianxirenDianhua = "13500008888";
                break;
            case 1:
                ItemHuanzheId = "7326647";
                ItemHuanzheName = "黄蓉";
                ItemShenfengzhenghaoma = "320223198202036544";
                ItemXingbie=  "男";
                ItemNianling = "44";
                ItemChushengriqi = "1975-12-30";
                ItemShengao = "168厘米";
                ItemTizhong = "53公斤";
                ItemHuanZheDianhua = "13500008888";
                ItemJiatinglianxiren = "郭靖";
                ItemJiatinglianxirenDianhua = "13500000009";
                break;
        }
    }

	public BingrenInfo(JSONObject o) {
		ItemHuanzheId = o.optString(JsonKey.ItemHuanzheId, "");
		ItemHuanzheName = o.optString(JsonKey.ItemHuanzheName, "");
		ItemShenfengzhenghaoma = o.optString(JsonKey.ItemShenfengzhenghaoma, "");
		ItemXingbie=  o.optString(JsonKey.ItemXingbie, "");
		ItemNianling = o.optString(JsonKey.ItemNianling, "");
		ItemChushengriqi = o.optString(JsonKey.ItemChushengriqi, "");
		ItemShengao = o.optString(JsonKey.ItemShengao,"");
		ItemTizhong = o.optString(JsonKey.ItemTizhong, "");
		ItemHuanZheDianhua = o.optString(JsonKey.ItemHuanZheDianhua, "");
		ItemJiatinglianxiren = o.optString(JsonKey.ItemJiatinglianxiren, "");
		ItemJiatinglianxirenDianhua = o.optString(JsonKey.ItemJiatinglianxirenDianhua, "");

		invalidate = true;
	}

	public void clear(){
		invalidate = false;

		ItemHuanzheId = "";
		ItemHuanzheName = "";
		ItemShenfengzhenghaoma = "";
		ItemXingbie=  "";
		ItemNianling = "";
		ItemChushengriqi = "";
		ItemShengao = "";
		ItemTizhong = "";
		ItemHuanZheDianhua = "";
		ItemJiatinglianxiren = "";
		ItemJiatinglianxirenDianhua = "";
	}

	public boolean isInvalidate() {
		return invalidate;
	}

	public void setInvalidate(boolean invalidate) {
		this.invalidate = invalidate;
	}

	public String getItemHuanzheId() {
		return ItemHuanzheId;
	}

	public void setItemHuanzheId(String ItemHuanzheId) {
		this.ItemHuanzheId = ItemHuanzheId;
	}
	
	public String getItemHuanzheName() {
		return ItemHuanzheName;
	}

	public void setItemHuanzheName(String ItemHuanzheName) {
		this.ItemHuanzheName = ItemHuanzheName;
	}
	
	public String getItemShenfengzhenghaoma() {
		return ItemShenfengzhenghaoma;
	}

	public void setItemShenfengzhenghaoma(String ItemShenfengzhenghaoma) {
		this.ItemShenfengzhenghaoma = ItemShenfengzhenghaoma;
	}
	
	public String getItemXingbie() {
		return ItemXingbie;
	}

	public void setItemXingbie(String ItemXingbie) {
		this.ItemXingbie = ItemXingbie;
	}
	
	public String getItemNianling() {
		return ItemNianling;
	}

	public void setItemNianling(String ItemNianling) {
		this.ItemNianling = ItemNianling;
	}
	
	public String getItemChushengriqi() {
		return ItemChushengriqi;
	}

	public void setItemChushengriqi(String ItemChushengriqi) {
		this.ItemChushengriqi = ItemChushengriqi;
	}
	
	public String getItemShengao() {
		return ItemShengao;
	}

	public void setItemShengao(String ItemShengao) {
		this.ItemShengao = ItemShengao;
	}
	
	public String getItemTizhong() {
		return ItemTizhong;
	}

	public void setItemTizhong(String ItemTizhong) {
		this.ItemTizhong = ItemTizhong;
	}
	
	public String getItemHuanZheDianhua() {
		return ItemHuanZheDianhua;
	}

	public void setItemHuanZheDianhua(String ItemHuanZheDianhua) {
		this.ItemHuanZheDianhua = ItemHuanZheDianhua;
	}
	
	public String getItemJiatinglianxiren() {
		return ItemJiatinglianxiren;
	}

	public void setItemJiatinglianxiren(String ItemJiatinglianxiren) {
		this.ItemJiatinglianxiren = ItemJiatinglianxiren;
	}
	
	public String getItemJiatinglianxirenDianhua() {
		return ItemJiatinglianxirenDianhua;
	}

	public void setItemJiatinglianxirenDianhua(String ItemJiatinglianxirenDianhua) {
		this.ItemJiatinglianxirenDianhua = ItemJiatinglianxirenDianhua;
	}
}
