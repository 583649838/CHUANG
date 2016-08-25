package com.bestplus.chuangshangjiuzhi.entity;

import com.bestplus.chuangshangjiuzhi.common.JsonKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maoamo on 2016/7/26.
 */
public class YingxiangResultGroup {//假定一组只有一个影像结果详细情况
    private String ItemZuhao; // 组号
    private String ItemMingcheng; //检查名称
    private String ItemShijian; // 报告时间
    private String ItemSongjianyisheng; // 送检医生
    private int ItemReadFlag;

    private List<YingxiangResultShuju> items = new ArrayList<YingxiangResultShuju>();

    public YingxiangResultGroup() {
        // TODO Auto-generated constructor stub
    }

    public YingxiangResultGroup(String groupId, int temp) {
        this.ItemZuhao = groupId;
        this.ItemMingcheng = "胸部CT检查";
        this.ItemShijian = "2016-07-09";
        this.ItemSongjianyisheng = "黄泽明";

        switch (temp) {
            case 0:
                YingxiangResultShuju tmp1 = new YingxiangResultShuju(0);
                items.add(tmp1);
                YingxiangResultShuju tmp2 = new YingxiangResultShuju(1);
                items.add(tmp2);
                YingxiangResultShuju tmp3 = new YingxiangResultShuju(0);
                items.add(tmp3);
                YingxiangResultShuju tmp4 = new YingxiangResultShuju(1);
                items.add(tmp4);
                break;
            case 1:
                YingxiangResultShuju tmp5 = new YingxiangResultShuju(1);
                items.add(tmp5);
                YingxiangResultShuju tmp6 = new YingxiangResultShuju(0);
                items.add(tmp6);
                YingxiangResultShuju tmp7 = new YingxiangResultShuju(1);
                items.add(tmp7);
                YingxiangResultShuju tmp8 = new YingxiangResultShuju(0);
                items.add(tmp8);
                break;
        }

    }

    public YingxiangResultGroup(JSONObject o) {
        this.ItemZuhao = o.optString(JsonKey.ItemTuxiangZuhao, "");

        YingxiangResultShuju item = new YingxiangResultShuju(o);
        items.add(item);

        this.ItemMingcheng = items.get(0).getItemJianchabuwei() + items.get(0).getItemJianchamingcheng();
        this.ItemShijian = items.get(0).getItemJianchashijian();
        this.ItemSongjianyisheng = items.get(0).getItemJianchashijian();
        this.ItemReadFlag = items.get(0).getItemReadFlag();
    }

    public String getItemZuhao() {
        return this.ItemZuhao;
    }

    public void setItemZuhao(String ItemZuhao) {
        this.ItemZuhao = ItemZuhao;
    }

    public String getItemMingcheng() {
        return this.ItemMingcheng;
    }

    public void setItemMingcheng(String ItemMingcheng) {
        this.ItemMingcheng = ItemMingcheng;
    }

    public String getItemShijian() {
        return this.ItemShijian;
    }

    public void setItemShijian(String ItemShijian) {
        this.ItemShijian = ItemShijian;
    }

    public String getItemSongjianyisheng() {
        return this.ItemSongjianyisheng;
    }

    public void setItemSongjianyisheng(String ItemSongjianyisheng) {
        this.ItemSongjianyisheng = ItemSongjianyisheng;
    }

    public int getItemReadFlag() {
        return ItemReadFlag;
    }

    public void setItemReadFlag(int itemReadFlag) {
        ItemReadFlag = itemReadFlag;
    }

    public int getChildCount() {
        return 1;
    }

//    public JianchaResultShuju getChild(int position){
//        if(position < items.size())
//            return items.get(position);
//        else
//            return null;
//    }


    public List<YingxiangResultShuju> getItems() {
        return items;
    }
}

