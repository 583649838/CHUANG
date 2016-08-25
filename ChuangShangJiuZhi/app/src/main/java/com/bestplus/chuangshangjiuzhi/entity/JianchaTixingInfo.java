package com.bestplus.chuangshangjiuzhi.entity;

import com.bestplus.chuangshangjiuzhi.common.JsonKey;

import org.json.JSONObject;

/**
 * Created by maoamo on 2016/7/23.
 */
public class JianchaTixingInfo {
    private String ItemHuanzheId;
    private String ItemHuanzheName;

    private String ItemJianchaBianhao; //检查编号
    private String ItemJianchaNeirong; //检查项目
    private String ItemJianchaYizhuId; //医嘱号
    private String ItemJianchaBaogaoshijian; //报告时间


    public JianchaTixingInfo() {
        // TODO Auto-generated constructor stub
    }

    public JianchaTixingInfo(int tmp) {
        // TODO Auto-generated constructor stub
        switch (tmp) {
            case 0:
                this.ItemHuanzheId = "55667701";
                this.ItemHuanzheName = "百事家";

                this.ItemJianchaBianhao = "1";
                this.ItemJianchaNeirong = "血常规检验";
                this.ItemJianchaYizhuId = "1";
                this.ItemJianchaBaogaoshijian = "2016-07-23 21：30";
                break;
            case 1:
                this.ItemHuanzheId = "55667721";
                this.ItemHuanzheName = "洪七公";

                this.ItemJianchaBianhao = "2";
                this.ItemJianchaNeirong = "血常规检验";
                this.ItemJianchaYizhuId = "2";
                this.ItemJianchaBaogaoshijian = "2016-07-23 21：35";
                break;
            case 2:
                this.ItemHuanzheId = "55667732";
                this.ItemHuanzheName = "得道高僧";

                this.ItemJianchaBianhao = "3";
                this.ItemJianchaNeirong = "血常规检验";
                this.ItemJianchaYizhuId = "3";
                this.ItemJianchaBaogaoshijian = "2016-07-23 21：40";
                break;
            case 3:
                this.ItemHuanzheId = "55667745";
                this.ItemHuanzheName = "胡锡敏";

                this.ItemJianchaBianhao = "4";
                this.ItemJianchaNeirong = "血常规检验";
                this.ItemJianchaYizhuId = "4";
                this.ItemJianchaBaogaoshijian = "2016-07-23 22：30";
                break;
            case 4:
                this.ItemHuanzheId = "55667767";
                this.ItemHuanzheName = "毛德操";

                this.ItemJianchaBianhao = "5";
                this.ItemJianchaNeirong = "血常规检验";
                this.ItemJianchaYizhuId = "5";
                this.ItemJianchaBaogaoshijian = "2016-07-23 22：38";
                break;
        }
    }

    public JianchaTixingInfo(JSONObject o) {
        this.ItemHuanzheId = o.optString(JsonKey.ItemHuanzheId, "");
        this.ItemHuanzheName = o.optString(JsonKey.ItemHuanzheName, "");

        this.ItemJianchaBianhao = o.optString(JsonKey.ItemJianchaBianhao, "");
        this.ItemJianchaNeirong = o.optString(JsonKey.ItemJianchaNeirong, "");
        this.ItemJianchaYizhuId = o.optString(JsonKey.ItemJianchaYizhuId, "");
        this.ItemJianchaBaogaoshijian = o.optString(JsonKey.ItemJianchaBaogaoshijian, "");
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

    public String getItemJianchaBianhao() {
        return this.ItemJianchaBianhao;
    }

    public void setItemJianchaBianhao(String ItemJianchaBianhao) {
        this.ItemJianchaBianhao = ItemJianchaBianhao;
    }

    public String getItemJianchaNeirong() {
        return this.ItemJianchaNeirong;
    }

    public void setItemJianchaNeirong(String ItemJianchaNeirong) {
        this.ItemJianchaNeirong = ItemJianchaNeirong;
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