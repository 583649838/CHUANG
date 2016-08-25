package com.bestplus.chuangshangjiuzhi.entity;

import com.bestplus.chuangshangjiuzhi.common.JsonKey;

import org.json.JSONObject;

/**
 * Created by maoamo on 2016/7/25.
 * 检查结果中的某一项数据
 */
public class JianchaResultShuju {
    private String ItemJianyanxiangmu; //检查项目
    private String ItemJianyanjieguo; //检查结果
    private int ItemJianyanjieguoUpdown; //检查结果高低
    private String ItemJianyancankao; //检查参考
    private String ItemJianyandanwei; //检查单位

    public JianchaResultShuju(){

    }

    public JianchaResultShuju(int temp){
        switch (temp){
            case 0:
                this.ItemJianyanxiangmu = "血红素质量";
                this.ItemJianyanjieguo = "18";
                this.ItemJianyanjieguoUpdown = 1;
                this.ItemJianyancankao = "14.0 - 20.0";
                this.ItemJianyandanwei = "mg";
                break;
            case 1:
                this.ItemJianyanxiangmu = "白细胞个数";
                this.ItemJianyanjieguo = "18000";
                this.ItemJianyanjieguoUpdown = 2;
                this.ItemJianyancankao = "10000 - 20000";
                this.ItemJianyandanwei = "个/毫升";
                break;
        }

    }

    public JianchaResultShuju(JSONObject o) {
        this.ItemJianyanxiangmu = o.optString(JsonKey.ItemJianyanxiangmu, "");
        this.ItemJianyanjieguo = o.optString(JsonKey.ItemJianyanjieguo, "");
        this.ItemJianyanjieguoUpdown = o.optInt(JsonKey.ItemJianyanjieguoUpdown, 0);
        this.ItemJianyancankao = o.optString(JsonKey.ItemJianyancankao, "");
        this.ItemJianyandanwei = o.optString(JsonKey.ItemJianyandanwei, "");
    }

    public String getItemJianyanxiangmu() {
        return this.ItemJianyanxiangmu;
    }

    public void setItemJianyanxiangmu(String ItemJianyanxiangmu) {
        this.ItemJianyanxiangmu = ItemJianyanxiangmu;
    }

    public String getItemJianyanjieguo() {
        return this.ItemJianyanjieguo;
    }

    public void setItemJianyanjieguo(String ItemJianyanjieguo) {
        this.ItemJianyanjieguo = ItemJianyanjieguo;
    }

    public int getItemJianyanjieguoUpdown() {
        return this.ItemJianyanjieguoUpdown;
    }

    public void setItemJianyanjieguoUpdown(int ItemJianyanjieguoUpdown) {
        this.ItemJianyanjieguoUpdown = ItemJianyanjieguoUpdown;
    }

    public String getItemJianyancankao() {
        return this.ItemJianyancankao;
    }

    public void setItemJianyancankao(String ItemJianyancankao) {
        this.ItemJianyancankao = ItemJianyancankao;
    }

    public String getItemJianyandanwei() {
        return this.ItemJianyandanwei;
    }

    public void setItemJianyandanwei(String ItemJianyandanwei) {
        this.ItemJianyandanwei = ItemJianyandanwei;
    }
}
