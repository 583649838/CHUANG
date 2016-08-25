package com.bestplus.chuangshangjiuzhi.entity;

import com.bestplus.chuangshangjiuzhi.common.JsonKey;

import org.json.JSONObject;

/**
 * Created by maomao on 2016/8/9.
 */
public class JieguoNotifyInfo {
    private String ItemHuanzheId;
    private String ItemHuanzheName;
    private String ItemJieguoName; // 名称
    private String ItemJieguoType; // 类型
    private int ItemJieguoId; // ID
    private String ItemJieguoBaogaoshijian; //报告时间

    public JieguoNotifyInfo() {
        // TODO Auto-generated constructor stub
    }

    public JieguoNotifyInfo(JSONObject o) {
        this.ItemHuanzheId = o.optString("patientCode", "");
        this.ItemHuanzheName = o.optString("patientName", "");

        this.ItemJieguoName = o.optString("name", "");
        this.ItemJieguoType = o.optString("type", "");
        this.ItemJieguoId = o.optInt("id", 0);
        this.ItemJieguoBaogaoshijian = o.optString("time", "");
    }

    public String getItemHuanzheId() {
        return ItemHuanzheId;
    }

    public void setItemHuanzheId(String itemHuanzheId) {
        ItemHuanzheId = itemHuanzheId;
    }

    public String getItemHuanzheName() {
        return ItemHuanzheName;
    }

    public void setItemHuanzheName(String itemHuanzheName) {
        ItemHuanzheName = itemHuanzheName;
    }

    public String getItemJieguoName() {
        return ItemJieguoName;
    }

    public void setItemJieguoName(String itemJieguoName) {
        ItemJieguoName = itemJieguoName;
    }

    public String getItemJieguoType() {
        return ItemJieguoType;
    }

    public void setItemJieguoType(String itemJieguoType) {
        ItemJieguoType = itemJieguoType;
    }

    public int getItemJieguoId() {
        return ItemJieguoId;
    }

    public void setItemJieguoId(int itemJieguoId) {
        ItemJieguoId = itemJieguoId;
    }

    public String getItemJieguoBaogaoshijian() {
        return ItemJieguoBaogaoshijian;
    }

    public void setItemJieguoBaogaoshijian(String itemJieguoBaogaoshijian) {
        ItemJieguoBaogaoshijian = itemJieguoBaogaoshijian;
    }
}
