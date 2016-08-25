package com.bestplus.chuangshangjiuzhi.entity;

import com.bestplus.chuangshangjiuzhi.common.JsonKey;

import org.json.JSONObject;

import java.text.SimpleDateFormat;

/**
 * Created by maoamo on 2016/7/26.
 */
public class YingxiangResultShuju {
    private String ItemJianchabuwei; // 检查部位
    private String ItemJianchamingcheng; // 检查名称
    private String ItemJianchashijian; // 检查时间
    private String ItemJianchasuojian; // 检查所见
    private String ItemJianchajianyi; // 检查建议
    private String ItemBaogaoyisheng; // 报告医生
    private String ItemShenheyisheng; // 审核医生
    private int ItemReadFlag;

    public YingxiangResultShuju(){

    }

    public YingxiangResultShuju(int temp){
        switch (temp){
            case 0:
                this.ItemJianchasuojian = "\t\t" + "胸廓对称，肺纹理增多，肺实质内可见多发条索状高密度影，右肺可见多发囊状透亮区，气管支气管畅通，管壁无增厚，管腔无局限性狭窄和扩张。纵膈居中，结构正常，纵膈内未见肿大淋巴结及异常肿块影。心脏大小及形态无特殊，包心腔内无积液，所见脉管粗细均匀，未见异常改变。胸腔内无积液和积气。肝实质内可见多发小囊肿状低密度影，边界清楚";
                this.ItemJianchajianyi = "\t\t" + "右肺多发肺大泡。\n" +
                        "\t\t" + "双肺多发纤维灶。\n" +
                        "\t\t" + "考虑肝脏多发囊肿。";
                this.ItemBaogaoyisheng = "叶宁";
                this.ItemShenheyisheng = "叶诗文";
                break;
            case 1:
                this.ItemJianchasuojian = "\t\t" + "胸廓对称，肺纹理增多，肺实质内可见多发条索状高密度影，右肺可见多发囊状透亮区，气管支气管畅通，管壁无增厚，管腔无局限性狭窄和扩张。纵膈居中，结构正常，纵膈内未见肿大淋巴结及异常肿块影。心脏大小及形态无特殊，包心腔内无积液，所见脉管粗细均匀，未见异常改变。胸腔内无积液和积气。肝实质内可见多发小囊肿状低密度影，边界清楚";
                this.ItemJianchajianyi =  "\t\t" + "右肺多发肺大泡。\n" +
                        "\t\t" + "双肺多发纤维灶。\n" +
                        "\t\t" + "考虑肝脏多发囊肿。";
                this.ItemBaogaoyisheng = "叶宁";
                this.ItemShenheyisheng = "叶诗文";
                break;
        }

    }

    public YingxiangResultShuju(JSONObject o) {
        this.ItemJianchabuwei = o.optString(JsonKey.ItemTuxiangBuwei, ""); // 检查部位
        this.ItemJianchamingcheng = o.optString(JsonKey.ItemTuxiangMingcheng, ""); // 检查名称

        long shijian = o.optLong(JsonKey.ItemTuxiangShijian, 0L); // 检查时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        this.ItemJianchashijian = formatter.format(shijian);

        this.ItemJianchasuojian = "\t\t" + o.optString(JsonKey.ItemTuxiangSuojian, "");
        this.ItemJianchajianyi = "\t\t" + o.optString(JsonKey.ItemTuxiangJianyi, "");

        this.ItemBaogaoyisheng = o.optString(JsonKey.ItemTuxiangSongjianyisheng, "");
        this.ItemShenheyisheng = o.optString(JsonKey.ItemTuxiangSongjianyisheng, "");

        this.ItemReadFlag = o.optInt("readFlag", 0);
    }

    public String getItemJianchabuwei() {
        return this.ItemJianchabuwei;
    }

    public void setItemJianchabuwei(String ItemJianchabuwei) {
        this.ItemJianchabuwei = ItemJianchabuwei;
    }
    public String getItemJianchamingcheng() {
        return this.ItemJianchamingcheng;
    }

    public void setItemJianchamingcheng(String ItemJianchamingcheng) {
        this.ItemJianchamingcheng = ItemJianchamingcheng;
    }
    public String getItemJianchashijian() {
        return this.ItemJianchashijian;
    }

    public void setItemJianchashijian(String ItemJianchashijian) {
        this.ItemJianchashijian = ItemJianchashijian;
    }

    public String getItemJianchasuojian() {
        return this.ItemJianchasuojian;
    }

    public void setItemJianchasuojian(String ItemJianchasuojian) {
        this.ItemJianchasuojian = ItemJianchasuojian;
    }

    public String getItemJianchajianyi() {
        return this.ItemJianchajianyi;
    }

    public void setItemJianchajianyi(String ItemJianchajianyi) {
        this.ItemJianchajianyi = ItemJianchajianyi;
    }

    public String getItemBaogaoyisheng() {
        return this.ItemBaogaoyisheng;
    }

    public void setItemBaogaoyisheng(String ItemBaogaoyisheng) {
        this.ItemBaogaoyisheng = ItemBaogaoyisheng;
    }

    public String getItemShenheyisheng() {
        return this.ItemShenheyisheng;
    }

    public void setItemShenheyisheng(String ItemShenheyisheng) {
        this.ItemShenheyisheng = ItemShenheyisheng;
    }

    public int getItemReadFlag() {
        return ItemReadFlag;
    }

    public void setItemReadFlag(int itemReadFlag) {
        ItemReadFlag = itemReadFlag;
    }
}
