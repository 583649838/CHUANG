package com.bestplus.chuangshangjiuzhi.entity;

import com.bestplus.chuangshangjiuzhi.common.JsonKey;

import org.json.JSONObject;

public class JianchaInfo {
    private String ItemHuanzheId;
    private String ItemHuanzheName;

    private String ItemJianchaShijian; //检查时间
    private String ItemJianchaNeirong; //检查内容
    private String ItemJianchaYizhuId; //医嘱号
    private String ItemJianchaBaogaoshijian; //报告时间
    private int ItemJianchaDanziShuliangZong; //检查单子数量--总数
    private int ItemJianchaDanzishuliangWei; //检查单子数量--未查看


    public JianchaInfo() {
        // TODO Auto-generated constructor stub
    }

    public JianchaInfo(int tmp) {
        // TODO Auto-generated constructor stub
        switch (tmp) {
            case 0:
                this.ItemHuanzheId = "55667701";
                this.ItemHuanzheName = "百事家";

                this.ItemJianchaShijian = "2016-07-01";
                this.ItemJianchaNeirong = "三翻四复但是斯蒂芬三翻四复所放松放松放松";
                this.ItemJianchaDanziShuliangZong = 10;
                this.ItemJianchaDanzishuliangWei = 2;
                break;
            case 1:
                this.ItemHuanzheId = "55667721";
                this.ItemHuanzheName = "洪七公";

                this.ItemJianchaShijian = "2016-07-01";
                this.ItemJianchaNeirong = "三翻四复但是斯蒂芬三翻四复所放斯蒂芬松放松放松";
                this.ItemJianchaDanziShuliangZong = 10;
                this.ItemJianchaDanzishuliangWei = 5;
                break;
            case 2:
                this.ItemHuanzheId = "55667732";
                this.ItemHuanzheName = "得道高僧";

                this.ItemJianchaShijian = "2016-07-01";
                this.ItemJianchaNeirong = "三翻四复但是手术斯蒂芬 斯蒂芬三翻四复所放松放松放松";
                this.ItemJianchaDanziShuliangZong = 8;
                this.ItemJianchaDanzishuliangWei = 1;
                break;
            case 3:
                this.ItemHuanzheId = "55667745";
                this.ItemHuanzheName = "胡锡敏";

                this.ItemJianchaShijian = "2016-07-01";
                this.ItemJianchaNeirong = "三翻送人头地图四复但是斯蒂芬三翻四复所放松放松放松";
                this.ItemJianchaDanziShuliangZong = 6;
                this.ItemJianchaDanzishuliangWei = 1;
                break;
            case 4:
                this.ItemHuanzheId = "55667767";
                this.ItemHuanzheName = "毛德操";

                this.ItemJianchaShijian = "2016-07-01";
                this.ItemJianchaNeirong = "三翻四复但是斯蒂迎合他人芬三翻四复所放松放松放松";
                this.ItemJianchaDanziShuliangZong = 8;
                this.ItemJianchaDanzishuliangWei = 2;
                break;
        }
    }

    public JianchaInfo(JSONObject o) {
        this.ItemHuanzheId = o.optString(JsonKey.ItemHuanzheId, "");
        this.ItemHuanzheName = o.optString(JsonKey.ItemHuanzheName, "");

        this.ItemJianchaShijian = o.optString(JsonKey.ItemJianchaShijian, "");
        this.ItemJianchaNeirong = o.optString(JsonKey.ItemJianchaNeirong, "");
        this.ItemJianchaYizhuId = o.optString(JsonKey.ItemJianchaYizhuId, "");
        this.ItemJianchaBaogaoshijian = o.optString(JsonKey.ItemJianchaBaogaoshijian, "");
        this.ItemJianchaDanziShuliangZong = o.optInt(JsonKey.ItemJianchaDanziShuliangZong, 0);
        this.ItemJianchaDanzishuliangWei = o.optInt(JsonKey.ItemJianchaDanzishuliangWei, 0);
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

    public String getItemJianchaShijian() {
        return this.ItemJianchaShijian;
    }

    public void setItemJianchaShijian(String ItemJianchaShijian) {
        this.ItemJianchaShijian = ItemJianchaShijian;
    }

    public String getItemJianchaNeirong() {
        return this.ItemJianchaNeirong;
    }

    public void setItemJianchaNeirong(String ItemJianchaNeirong) {
        this.ItemJianchaNeirong = ItemJianchaNeirong;
    }

    public int getItemJianchaDanziShuliangZong() {
        return this.ItemJianchaDanziShuliangZong;
    }

    public void setItemJianchaDanziShuliangZong(int ItemJianchaDanziShuliangZong) {
        this.ItemJianchaDanziShuliangZong = ItemJianchaDanziShuliangZong;
    }

    public int getItemJianchaDanzishuliangWei() {
        return this.ItemJianchaDanzishuliangWei;
    }

    public void setItemJianchaDanzishuliangWei(int ItemJianchaDanzishuliangWei) {
        this.ItemJianchaDanzishuliangWei = ItemJianchaDanzishuliangWei;
    }

    public String getItemJianchaYizhuId() {
        return this.ItemJianchaYizhuId;
    }

    public void setItemJianchaYizhuId(String ItemJianchaYizhuId) {
        this.ItemJianchaYizhuId = ItemJianchaYizhuId;
    }

    public String getItemJianchaBaogaoshijian() {
        return this.ItemJianchaBaogaoshijian;
    }

    public void setItemJianchaBaogaoshijian(String ItemJianchaBaogaoshijian) {
        this.ItemJianchaBaogaoshijian = ItemJianchaBaogaoshijian;
    }
}
