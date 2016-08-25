package com.bestplus.chuangshangjiuzhi.entity;

import android.support.annotation.NonNull;

import com.bestplus.chuangshangjiuzhi.common.JsonKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class JianchaResultDetail {
    private String ItemHuanzheId;
    private String ItemHuanzheName;

    private String ItemJianchaShijian; //检查时间
    private String ItemJianchaNeirong; //检查内容

//    private String ItemJianyanxiangmu; //检查项目
//    private String ItemJianyanjieguo; //检查结果
//    private int ItemJianyanjieguoUpdown; //检查结果高低
//    private String ItemJianyancankao; //检查参考
//    private String ItemJianyandanwei; //检查单位
    private List<JianchaResultShuju> items = new ArrayList<JianchaResultShuju>();

    public JianchaResultDetail() {
        // TODO Auto-generated constructor stub
    }

    public JianchaResultDetail(int temp) {
        switch (temp){
            case 0:
                this.ItemHuanzheId = "2001";
                this.ItemHuanzheName = "郭靖";
                this.ItemJianchaShijian = "2016-07-23 12：30：04";
                this.ItemJianchaNeirong = "血常规检查";
                this.items.add(new JianchaResultShuju(0));
                this.items.add(new JianchaResultShuju(1));
                this.items.add(new JianchaResultShuju(1));
                this.items.add(new JianchaResultShuju(0));
                this.items.add(new JianchaResultShuju(1));
                this.items.add(new JianchaResultShuju(0));

                break;
            case 1:
                this.ItemHuanzheId = "2002";
                this.ItemHuanzheName = "黄蓉";
                this.ItemJianchaShijian = "2016-07-20 10：30：04";
                this.ItemJianchaNeirong = "血常规检查";
                this.items.add(new JianchaResultShuju(1));
                this.items.add(new JianchaResultShuju(0));
                this.items.add(new JianchaResultShuju(0));
                this.items.add(new JianchaResultShuju(1));
                this.items.add(new JianchaResultShuju(1));
                this.items.add(new JianchaResultShuju(1));
                break;
        }
    }

    public JianchaResultDetail(JSONObject o) {
        this.ItemHuanzheId = o.optString(JsonKey.ItemHuanzheId, "");
        this.ItemHuanzheName = o.optString(JsonKey.ItemHuanzheName, "");

        this.ItemJianchaShijian = o.optString(JsonKey.ItemJianchaShijian, "");
        this.ItemJianchaNeirong = o.optString(JsonKey.ItemJianchaNeirong, "");

        try {
            String list = o.optString(JsonKey.ItemArrays);
            if (list != null && !"".equals(list)) {
                JSONArray array = o.getJSONArray(JsonKey.ItemArrays);

                for(int i = 0; i < array.length(); i++){
                    JSONObject itemObj = array.getJSONObject(i);
                    JianchaResultShuju item = new JianchaResultShuju(itemObj);
                    items.add(item);
                }
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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

    public List<JianchaResultShuju> getItems(){
        return items;
    }
}
