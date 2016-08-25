package com.bestplus.chuangshangjiuzhi.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

import org.json.JSONObject;

import com.bestplus.chuangshangjiuzhi.common.JsonKey;

public class HuanzheJiuzhiInfo implements Serializable {
	private String ItemHuanzheId;
	private String ItemHuanzheName;
	
	private String ItemJiuzhiShijian; //救治时间
	private String ItemJiuzhiJianyaoJilu; //救治记录
	private String ItemJiuzhiCaozuoren; //操作人

	private String ItemPicFile1, ItemPicFile2, ItemPicFile3;
	private String ItemAudFile1, ItemAudFile2, ItemAudFile3;
	
	public HuanzheJiuzhiInfo() {
		// TODO Auto-generated constructor stub
	}
	
	public HuanzheJiuzhiInfo(int tmp) {
		// TODO Auto-generated constructor stub
		switch(tmp){
		case 0:
			this.ItemHuanzheId = "55667701";
			this.ItemHuanzheName = "百事家";
			
			this.ItemJiuzhiShijian = "2016-07-01";
			this.ItemJiuzhiJianyaoJilu=  "三翻四复但是斯蒂芬三翻四复所放松放松放松";
			this.ItemJiuzhiCaozuoren = "毛毛";
			break;
		case 1:
			this.ItemHuanzheId = "55667721";
			this.ItemHuanzheName = "洪七公";
			
			this.ItemJiuzhiShijian = "2016-07-01";
			this.ItemJiuzhiJianyaoJilu=  "三翻四复但是斯蒂芬三翻四复所放斯蒂芬松放松放松";
			this.ItemJiuzhiCaozuoren = "毛毛";
			break;
		case 2:
			this.ItemHuanzheId = "55667732";
			this.ItemHuanzheName = "得道高僧";
			
			this.ItemJiuzhiShijian = "2016-07-01";
			this.ItemJiuzhiJianyaoJilu=  "三翻四复但是手术斯蒂芬 斯蒂芬三翻四复所放松放松放松";
			this.ItemJiuzhiCaozuoren = "毛毛";
			break;
		case 3:
			this.ItemHuanzheId = "55667745";
			this.ItemHuanzheName = "胡锡敏";
			
			this.ItemJiuzhiShijian = "2016-07-01";
			this.ItemJiuzhiJianyaoJilu=  "三翻送人头地图四复但是斯蒂芬三翻四复所放松放松放松";
			this.ItemJiuzhiCaozuoren = "毛毛";
			break;
		case 4:
			this.ItemHuanzheId = "55667767";
			this.ItemHuanzheName = "毛德操";
			
			this.ItemJiuzhiShijian = "2016-07-01";
			this.ItemJiuzhiJianyaoJilu=  "三翻四复但是斯蒂迎合他人芬三翻四复所放松放松放松";
			this.ItemJiuzhiCaozuoren = "毛毛";
			break;
		}
	}

	public HuanzheJiuzhiInfo(JSONObject o) {
		this.ItemHuanzheId = o.optString(JsonKey.ItemHuanzheId, "");
		this.ItemHuanzheName = o.optString(JsonKey.ItemHuanzheName, "");

		long shijian = o.optLong(JsonKey.ItemJiuzhiShijian, 0L);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		this.ItemJiuzhiShijian = formatter.format(shijian);
		//this.ItemJiuzhiShijian = o.optString(JsonKey.ItemJiuzhiShijian, "");

		this.ItemJiuzhiJianyaoJilu=  o.optString(JsonKey.ItemJiuzhiJianyaoJilu, "");
		this.ItemJiuzhiCaozuoren = o.optString(JsonKey.ItemJiuzhiCaozuoren, "");

		this.ItemPicFile1 = o.optString(JsonKey.ItemJiuzhiFile1, "");
		this.ItemPicFile2 = o.optString(JsonKey.ItemJiuzhiFile2, "");
		this.ItemPicFile3 = o.optString(JsonKey.ItemJiuzhiFile3, "");
		this.ItemAudFile1 = o.optString(JsonKey.ItemJiuzhiFile4, "");
		this.ItemAudFile2 = o.optString(JsonKey.ItemJiuzhiFile5, "");
		this.ItemAudFile3 = o.optString(JsonKey.ItemJiuzhiFile6, "");
	}
	
	public String getItemHuanzheId() {
		return this.ItemHuanzheId;
	}

	public void setItemHuanzheId(String ItemHuanzheId) {
		this.ItemHuanzheId = ItemHuanzheId;
	}
	
	public String getItemHuanzheName() {
		return this.ItemHuanzheName;
	}

	public void setItemHuanzheName(String ItemHuanzheName) {
		this.ItemHuanzheName = ItemHuanzheName;
	}
	
	public String getItemJiuzhiShijian() {
		return this.ItemJiuzhiShijian;
	}

	public void setItemJiuzhiShijian(String ItemJiuzhiShijian) {
		this.ItemJiuzhiShijian = ItemJiuzhiShijian;
	}
	
	public String getItemJiuzhiJianyaoJilu() {
		return this.ItemJiuzhiJianyaoJilu;
	}

	public void setItemJiuzhiJianyaoJilu(String ItemJiuzhiJianyaoJilu) {
		this.ItemJiuzhiJianyaoJilu = ItemJiuzhiJianyaoJilu;
	}
	
	public String getItemJiuzhiCaozuoren() {
		return this.ItemJiuzhiCaozuoren;
	}

	public void setItemJiuzhiCaozuoren(String ItemJiuzhiCaozuoren) {
		this.ItemJiuzhiCaozuoren = ItemJiuzhiCaozuoren;
	}

	public String getItemPicFile1() {
		return ItemPicFile1;
	}

	public void setItemPicFile1(String itemPicFile1) {
		ItemPicFile1 = itemPicFile1;
	}

	public String getItemPicFile2() {
		return ItemPicFile2;
	}

	public void setItemPicFile2(String itemPicFile2) {
		ItemPicFile2 = itemPicFile2;
	}

	public String getItemPicFile3() {
		return ItemPicFile3;
	}

	public void setItemPicFile3(String itemPicFile3) {
		ItemPicFile3 = itemPicFile3;
	}

	public String getItemAudFile1() {
		return ItemAudFile1;
	}

	public void setItemAudFile1(String itemAudFile1) {
		ItemAudFile1 = itemAudFile1;
	}

	public String getItemAudFile2() {
		return ItemAudFile2;
	}

	public void setItemAudFile2(String itemAudFile2) {
		ItemAudFile2 = itemAudFile2;
	}

	public String getItemAudFile3() {
		return ItemAudFile3;
	}

	public void setItemAudFile3(String itemAudFile3) {
		ItemAudFile3 = itemAudFile3;
	}
}
