package com.bestplus.chuangshangjiuzhi.entity;

import com.bestplus.chuangshangjiuzhi.common.JsonKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maomao on 2016/8/8.
 *      {"chilrenList":[
 *              {"explanation":"一级处理B-2.3","gmtCreated":1470377627000,"gmtModified":1470377627000,"hotWordName":"一级处理B-2.3","id":15,"isDel":1,"parent":12},
 *              {"explanation":"一级处理B-2.2","gmtCreated":1470377613000,"gmtModified":1470377613000,"hotWordName":"一级处理B-2.2","id":14,"isDel":1,"parent":12},
 *              {"explanation":"一级处理B-2.1","gmtCreated":1470377594000,"gmtModified":1470377594000,"hotWordName":"一级处理B-2.1","id":13,"isDel":1,"parent":12}
 *              ],
 *           "parent":{"gmtCreated":1470377476000,"gmtModified":1470377476000,"hotWordTypeName":"一级处理B","id":12,"isDel":1}
 *          },
 */
public class Hotwords {
    private int id;
    private String words;
    private boolean isOneLevel;

    private List<Hotwords> twoLevelList;

    public Hotwords(){

    }

    public Hotwords(JSONObject o) {
        try {
            JSONObject parentObject = o.optJSONObject(JsonKey.ItemHotwordsParent);
            if (parentObject != null) {
                this.id = parentObject.optInt(JsonKey.ItemHotwordsID, 0);
                this.words = parentObject.optString(JsonKey.ItemHotwordsParentName, "");
                this.isOneLevel = true;

                JSONArray chilrenList = o.optJSONArray(JsonKey.ItemHotwordsChildrenList);
                if (chilrenList != null) {
                    this.twoLevelList = new ArrayList<Hotwords>();
                    for (int i = 0; i < chilrenList.length(); i++) {
                        Hotwords hotwords = new Hotwords();
                        hotwords.id = chilrenList.getJSONObject(i).optInt(JsonKey.ItemHotwordsID, 0);
                        hotwords.words = chilrenList.getJSONObject(i).optString(JsonKey.ItemHotwordsName, "");
                        hotwords.isOneLevel = false;

                        this.twoLevelList.add(hotwords);
                    }
                }
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public boolean isOneLevel() {
        return isOneLevel;
    }

    public void setOneLevel(boolean oneLevel) {
        isOneLevel = oneLevel;
    }

    public List<Hotwords> getTwoLevelList() {
        return twoLevelList;
    }

    public void setTwoLevelList(List<Hotwords> twoLevelList) {
        this.twoLevelList = twoLevelList;
    }

    public String toString(){
        return "id: " + String.valueOf(this.id) + " words: " + this.words.toString() + " isOneLevel: " + String.valueOf(this.isOneLevel);
    }
}
