package com.bestplus.chuangshangjiuzhi.entity;

import com.bestplus.chuangshangjiuzhi.common.JsonKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maoamo on 2016/7/25.
 */
public class YongyaoInfoGroup {
    private String ItemYongyaoxinxiYizhuID; //医嘱ID
    private String ItemYongyaoxinxiZuhao; //组号
    private int ItemReadFlag; // 未读标志

    private List<YongyaoInfo> items = new ArrayList<YongyaoInfo>();

    public YongyaoInfoGroup() {
        // TODO Auto-generated constructor stub
    }

    public YongyaoInfoGroup(String groupId, int count) {
        this.ItemYongyaoxinxiZuhao = groupId;

        for(int i = 0; i < count; i ++){
            YongyaoInfo tmp = new YongyaoInfo(this.ItemYongyaoxinxiZuhao, String.valueOf(i + 1));
            tmp.setItemYongyaoxinxiYizhumingcheng("葡萄糖注射");
            tmp.setItemYongyaoxinxiYizhuLeibie("长期");
            tmp.setItemYongyaoxinxiPinci("TID");
            tmp.setItemYongyaoxinxiJiliang("160ml");
            tmp.setItemYongyaoxinxiKaishishijian("2016-07-01 11：23：40");
            tmp.setItemYongyaoxinxiJiesushijian("2016-07-01 15：24：56");
            items.add(tmp);
        }
    }

    public YongyaoInfoGroup(JSONObject o) {
        this.ItemYongyaoxinxiYizhuID = o.optString(JsonKey.ItemYongyaoxinxiYizhuID, "");
        this.ItemYongyaoxinxiZuhao = o.optString(JsonKey.ItemYongyaoxinxiZuhao, "");
        try {
            String list = o.optString("groupYizhu");
            if (list != null && !"".equals(list)) {
                JSONArray array = o.getJSONArray("groupYizhu");

                for(int i = 0; i < array.length(); i++){
                    JSONObject itemObj = array.getJSONObject(i).getJSONObject("careList");
                    YongyaoInfo item = new YongyaoInfo(itemObj);
                    items.add(item);
                }
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        this.ItemYongyaoxinxiZuhao = items.get(0).getItemYongyaoxinxiZuhao();
    }

    public YongyaoInfoGroup(JSONObject o, String groupCode) {
        this.ItemYongyaoxinxiYizhuID = o.optString(JsonKey.ItemYongyaoxinxiYizhuID, "");
        this.ItemYongyaoxinxiZuhao = groupCode;
        try {
            String list = o.optString("groupYizhu");
            if (list != null && !"".equals(list)) {
                JSONArray array = o.getJSONArray("groupYizhu");

                for(int i = 0; i < array.length(); i++){
                    JSONObject itemObj = array.getJSONObject(i).getJSONObject("careList");
                    YongyaoInfo item = new YongyaoInfo(itemObj);
                    items.add(item);
                }
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public YongyaoInfoGroup(JSONArray o ,String yizhuCode, String groupCode) {
        this.ItemYongyaoxinxiYizhuID = yizhuCode;
        this.ItemYongyaoxinxiZuhao = groupCode;
        try {
                JSONArray array = o;
                for(int i = 0; i < array.length(); i++){
                    JSONObject itemObj = array.getJSONObject(i);
                    YongyaoInfo item = new YongyaoInfo(itemObj);
                    items.add(item);
                }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public YongyaoInfoGroup(JSONArray o ,String yizhuCode, String groupCode, int readFlag) {
        this.ItemYongyaoxinxiYizhuID = yizhuCode;
        this.ItemYongyaoxinxiZuhao = groupCode;
        this.ItemReadFlag = readFlag;

        try {
            JSONArray array = o;
            for(int i = 0; i < array.length(); i++){
                JSONObject itemObj = array.getJSONObject(i);
                YongyaoInfo item = new YongyaoInfo(itemObj);
                items.add(item);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getItemYongyaoxinxiYizhuID() {
        return ItemYongyaoxinxiYizhuID;
    }

    public void setItemYongyaoxinxiYizhuID(String itemYongyaoxinxiYizhuID) {
        ItemYongyaoxinxiYizhuID = itemYongyaoxinxiYizhuID;
    }

    public String getItemYongyaoxinxiZuhao() {
        return this.ItemYongyaoxinxiZuhao;
    }

    public void setItemYongyaoxinxiZuhao(String ItemYongyaoxinxiZuhao) {
        this.ItemYongyaoxinxiZuhao = ItemYongyaoxinxiZuhao;
    }

    public int getChildCount(){
        return items.size();
    }

    public YongyaoInfo getChild(int position){
        return items.get(position);
    }

    public int getItemReadFlag() {
        return ItemReadFlag;
    }

    public void setItemReadFlag(int itemReadFlag) {
        ItemReadFlag = itemReadFlag;
    }
}
