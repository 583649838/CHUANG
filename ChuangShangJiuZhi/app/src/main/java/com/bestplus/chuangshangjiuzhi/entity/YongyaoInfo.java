package com.bestplus.chuangshangjiuzhi.entity;

import com.bestplus.chuangshangjiuzhi.common.JsonKey;

import org.json.JSONObject;

import java.text.SimpleDateFormat;

public class YongyaoInfo {
    private String ItemYongyaoxinxiZuhao; //组号
    private String ItemYongyaoxinxiBianhao; //编号

    private String ItemYongyaoxinxiYizhumingcheng; //医嘱名称
    private String ItemYongyaoxinxiYizhuLeibie; //医嘱类别
    private String ItemYongyaoxinxiPinci; //频次
    private String ItemYongyaoxinxiJiliang; //剂量
    private String ItemYongyaoxinxiKaishishijian ; //开医嘱时间
    private String ItemYongyaoxinxiJiesushijian; //停医嘱时间

    public YongyaoInfo() {
        // TODO Auto-generated constructor stub
    }

    public YongyaoInfo(int tmp) {
        // TODO Auto-generated constructor stub
        switch(tmp){
            case 0:
                this.ItemYongyaoxinxiZuhao = "10001";
                this.ItemYongyaoxinxiBianhao = "23";

                this.ItemYongyaoxinxiYizhumingcheng = "葡萄糖注射";
                this.ItemYongyaoxinxiYizhuLeibie = "长期";
                this.ItemYongyaoxinxiPinci=  "TID";
                this.ItemYongyaoxinxiJiliang = "260ml";
                this.ItemYongyaoxinxiKaishishijian = "2016-07-01 10：23：40";
                this.ItemYongyaoxinxiJiesushijian = "2016-07-01 14：24：56";
                break;
            case 1:
                this.ItemYongyaoxinxiZuhao = "10002";
                this.ItemYongyaoxinxiBianhao = "20";

                this.ItemYongyaoxinxiYizhumingcheng = "葡萄糖注射";
                this.ItemYongyaoxinxiYizhuLeibie = "长期";
                this.ItemYongyaoxinxiPinci=  "TID";
                this.ItemYongyaoxinxiJiliang = "280ml";
                this.ItemYongyaoxinxiKaishishijian = "2016-07-01 11：23：40";
                this.ItemYongyaoxinxiJiesushijian = "2016-07-01 11：24：56";
                break;
            case 2:
                this.ItemYongyaoxinxiZuhao = "10003";
                this.ItemYongyaoxinxiBianhao = "3";

                this.ItemYongyaoxinxiYizhumingcheng = "葡萄糖注射";
                this.ItemYongyaoxinxiYizhuLeibie = "长期";
                this.ItemYongyaoxinxiPinci=  "TID";
                this.ItemYongyaoxinxiJiliang = "290ml";
                this.ItemYongyaoxinxiKaishishijian = "2016-07-01 10：23：40";
                this.ItemYongyaoxinxiJiesushijian = "2016-07-01 11：24：56";
                break;
            case 3:
                this.ItemYongyaoxinxiZuhao = "10005";
                this.ItemYongyaoxinxiBianhao = "17";

                this.ItemYongyaoxinxiYizhumingcheng = "葡萄糖注射";
                this.ItemYongyaoxinxiYizhuLeibie = "长期";
                this.ItemYongyaoxinxiPinci=  "TID";
                this.ItemYongyaoxinxiJiliang = "60ml";
                this.ItemYongyaoxinxiKaishishijian = "2016-07-01 13：23：40";
                this.ItemYongyaoxinxiJiesushijian = "2016-07-01 14：24：56";
                break;
            case 4:
                this.ItemYongyaoxinxiZuhao = "10004";
                this.ItemYongyaoxinxiBianhao = "56";

                this.ItemYongyaoxinxiYizhumingcheng = "葡萄糖注射";
                this.ItemYongyaoxinxiYizhuLeibie = "长期";
                this.ItemYongyaoxinxiPinci=  "TID";
                this.ItemYongyaoxinxiJiliang = "160ml";
                this.ItemYongyaoxinxiKaishishijian = "2016-07-01 11：23：40";
                this.ItemYongyaoxinxiJiesushijian = "2016-07-01 15：24：56";
                break;
        }
    }

    public YongyaoInfo(String groupId, String no) {
        this.ItemYongyaoxinxiZuhao = groupId;
        this.ItemYongyaoxinxiBianhao = no;
    }

    public YongyaoInfo(JSONObject o) {
        this.ItemYongyaoxinxiZuhao = o.optString(JsonKey.ItemYongyaoxinxiZuhao, "");
        this.ItemYongyaoxinxiBianhao = o.optString(JsonKey.ItemYongyaoxinxiBianhao, "");

        this.ItemYongyaoxinxiYizhumingcheng = o.optString(JsonKey.ItemYongyaoxinxiYizhumingcheng, "");
        this.ItemYongyaoxinxiYizhuLeibie = o.optString(JsonKey.ItemYongyaoxinxiYizhuLeibie, "");
        this.ItemYongyaoxinxiPinci = o.optString(JsonKey.ItemYongyaoxinxiPinci, "");
        this.ItemYongyaoxinxiJiliang = o.optString(JsonKey.ItemYongyaoxinxiJiliang, "");

        long shijian = o.optLong(JsonKey.ItemYongyaoxinxiKaishishijian, 0L); // 检查时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        this.ItemYongyaoxinxiKaishishijian = formatter.format(shijian);

        shijian = o.optLong(JsonKey.ItemYongyaoxinxiJiesushijian, 0L); // 检查时间
        this.ItemYongyaoxinxiJiesushijian = formatter.format(shijian);
    }

    public String getItemYongyaoxinxiZuhao() {
        return this.ItemYongyaoxinxiZuhao;
    }

    public void setItemYongyaoxinxiZuhao(String ItemYongyaoxinxiZuhao) {
        this.ItemYongyaoxinxiZuhao = ItemYongyaoxinxiZuhao;
    }

    public String getItemYongyaoxinxiBianhao() {
        return this.ItemYongyaoxinxiBianhao;
    }

    public void setItemYongyaoxinxiBianhao(String ItemYongyaoxinxiBianhao) {
        this.ItemYongyaoxinxiBianhao = ItemYongyaoxinxiBianhao;
    }
    public String getItemYongyaoxinxiYizhumingcheng() {
        return this.ItemYongyaoxinxiYizhumingcheng;
    }

    public void setItemYongyaoxinxiYizhumingcheng(String ItemYongyaoxinxiYizhumingcheng) {
        this.ItemYongyaoxinxiYizhumingcheng = ItemYongyaoxinxiYizhumingcheng;
    }

    public String getItemYongyaoxinxiYizhuLeibie() {
        return this.ItemYongyaoxinxiYizhuLeibie;
    }

    public void setItemYongyaoxinxiYizhuLeibie(String ItemYongyaoxinxiYizhuLeibie) {
        this.ItemYongyaoxinxiYizhuLeibie = ItemYongyaoxinxiYizhuLeibie;
    }

    public String getItemYongyaoxinxiPinci() {
        return this.ItemYongyaoxinxiPinci;
    }

    public void setItemYongyaoxinxiPinci(String ItemYongyaoxinxiPinci) {
        this.ItemYongyaoxinxiPinci = ItemYongyaoxinxiPinci;
    }
    public String getItemYongyaoxinxiJiliang() {
        return this.ItemYongyaoxinxiJiliang;
    }

    public void setItemYongyaoxinxiJiliang(String ItemYongyaoxinxiJiliang) {
        this.ItemYongyaoxinxiJiliang = ItemYongyaoxinxiJiliang;
    }
    public String getItemYongyaoxinxiKaishishijian() {
        return this.ItemYongyaoxinxiKaishishijian;
    }

    public void setItemYongyaoxinxiKaishishijian(String ItemYongyaoxinxiKaishishijian) {
        this.ItemYongyaoxinxiKaishishijian = ItemYongyaoxinxiKaishishijian;
    }
    public String getItemYongyaoxinxiJiesushijian() {
        return this.ItemYongyaoxinxiJiesushijian;
    }

    public void setItemYongyaoxinxiJiesushijian(String ItemYongyaoxinxiJiesushijian) {
        this.ItemYongyaoxinxiJiesushijian = ItemYongyaoxinxiJiesushijian;
    }
}
