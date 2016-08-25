package com.bestplus.chuangshangjiuzhi.entity;

import com.bestplus.chuangshangjiuzhi.common.JsonKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maoamo on 2016/7/25.
 * 检查结果组
 * {"check2":
 *      {"checkName":"尿检",
 *      "checkTime":1468656017000,"checkTime2":"2016-07-16",
 *      "checkType":"尿检",
 *      "gmtCreated":1468828849000,"gmtModified":1469954657000,
 *      "id":2,"isDel":1,
 *      "patientCode":"123","patientName":"小刚","picCode":"TID",
 *      "readflag":0,
 *      "reportTime":1469857475000,"reportTime2":"2016-07-30"}
 */
public class JianchaResultGroup { //假定一组只有一个检查结果详细情况
    private String ItemZuhao; // 组号 改成 id
    private String ItemMingcheng; //检查名称
    private int ItemID; //检查ID
    private int ItemReadflag; //未读标志
    private String ItemLeixing; //检查 类型
    private String ItemShijian; // 检查时间
    private String ItemBaogaoshijian; // 报告时间
    private String ItemSongjianyisheng; // 送检医生

    private boolean haveDetail = false;
    private List<JianchaResultShuju> items = new ArrayList<JianchaResultShuju>();

    public JianchaResultGroup() {
        // TODO Auto-generated constructor stub
    }

    public JianchaResultGroup(String groupId, int temp) {
        this.ItemZuhao = groupId;
        this.ItemMingcheng = "血常规五类";
        this.ItemShijian = "2016-07-09";
        this.ItemSongjianyisheng = "黄晨泽明";

        switch (temp) {
            case 0:
                JianchaResultShuju tmp1 = new JianchaResultShuju(0);
                items.add(tmp1);
                JianchaResultShuju tmp2 = new JianchaResultShuju(1);
                items.add(tmp2);
                JianchaResultShuju tmp3 = new JianchaResultShuju(0);
                items.add(tmp3);
                JianchaResultShuju tmp4 = new JianchaResultShuju(1);
                items.add(tmp4);
                break;
            case 1:
                JianchaResultShuju tmp5 = new JianchaResultShuju(1);
                items.add(tmp5);
                JianchaResultShuju tmp6 = new JianchaResultShuju(0);
                items.add(tmp6);
                JianchaResultShuju tmp7 = new JianchaResultShuju(1);
                items.add(tmp7);
                JianchaResultShuju tmp8 = new JianchaResultShuju(0);
                items.add(tmp8);
                break;
        }
    }

    public JianchaResultGroup(JSONObject o, boolean haveDetail) {
        JSONObject checkObject = o.optJSONObject("check");

        this.ItemZuhao = checkObject.optString(JsonKey.ItemJianyanID, "");
        this.ItemMingcheng = checkObject.optString(JsonKey.ItemMingcheng, "");
        this.ItemID = checkObject.optInt(JsonKey.ItemID, 1);
        this.ItemReadflag = checkObject.optInt("readflag", 0);
        this.ItemLeixing = checkObject.optString(JsonKey.ItemLeixing, "");
        long shijian = checkObject.optLong(JsonKey.ItemShijian, 0L);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        this.ItemShijian = formatter.format(shijian);

        shijian = checkObject.optLong(JsonKey.ItemBaogaoshijian, 0L);
        this.ItemBaogaoshijian = formatter.format(shijian);

        this.ItemSongjianyisheng = checkObject.optString(JsonKey.ItemSongjianyisheng, "");

        if(haveDetail) {
            try {
                String list = o.optString("checkDetailList");
                if (list != null && !"".equals(list)) {
                    JSONArray checkDetailArray = o.optJSONArray("checkDetailList");
                    if(checkDetailArray.length() > 0)
                        this.haveDetail = true;

                    for (int i = 0; i < checkDetailArray.length(); i++) {
                        JSONObject itemObj = checkDetailArray.getJSONObject(i);
                        JianchaResultShuju item = new JianchaResultShuju(itemObj);
                        items.add(item);
                    }
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
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

    //ItemID
    public int getItemID() {
        return this.ItemID;
    }

    public void setItemID(int ItemID) {
        this.ItemID = ItemID;
    }
    //ItemReadflag
    public int getItemReadflag() {
        return this.ItemReadflag;
    }

    public void setItemReadflag(int ItemReadflag) {
        this.ItemReadflag = ItemReadflag;
    }
    //ItemLeixing
    public String getItemLeixing() {
        return this.ItemLeixing;
    }

    public void setItemLeixing(String ItemLeixing) {
        this.ItemLeixing = ItemLeixing;
    }
    //ItemBaogaoshijian
    public String getItemBaogaoshijian() {
        return this.ItemBaogaoshijian;
    }

    public void setItemBaogaoshijian(String ItemBaogaoshijian) {
        this.ItemBaogaoshijian = ItemBaogaoshijian;
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

    public int getChildCount(){
        return 1;
    }

//    public JianchaResultShuju getChild(int position){
//        if(position < items.size())
//            return items.get(position);
//        else
//            return null;
//    }


    public List<JianchaResultShuju> getItems(){
        return items;
    }
}
