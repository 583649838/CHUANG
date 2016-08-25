package com.bestplus.chuangshangjiuzhi.entity;

import com.bestplus.chuangshangjiuzhi.common.JsonKey;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maoamo on 2016/7/23.
 */
public class HuanzheStatus {
    private String ItemHuanzheId;
    private String ItemHuanzheName;

    private boolean ItemJianchaStatus; // 检查状态
    private boolean ItemYizhuStaus; // 医嘱状态
    private boolean ItemYingxiangStatus; // 影像状态
    private boolean ItemGuangzhu; // 关注状态

    private BingrenInfo bingrenInfo;
    private List<String> ItemUnreadJianchaIDList = new ArrayList<>();
    private List<String> ItemUnreadYizhuIDList = new ArrayList<>();
    private List<String> ItemUnreadYingxiangIDList = new ArrayList<>();

    public HuanzheStatus() {
        // TODO Auto-generated constructor stub
    }

    public HuanzheStatus(int tmp, boolean guanzhu) {
        // TODO Auto-generated constructor stub
        switch (tmp) {
            case 0:
                this.ItemHuanzheId = "55667701";
                this.ItemHuanzheName = "百事家";

                this.ItemJianchaStatus = false;
                this.ItemYizhuStaus = false;
                this.ItemYingxiangStatus = true;
                this.ItemGuangzhu = guanzhu;
                break;
            case 1:
                this.ItemHuanzheId = "55667721";
                this.ItemHuanzheName = "洪七公";

                this.ItemJianchaStatus = true;
                this.ItemYizhuStaus = false;
                this.ItemYingxiangStatus = false;
                this.ItemGuangzhu = guanzhu;
                break;
            case 2:
                this.ItemHuanzheId = "55667732";
                this.ItemHuanzheName = "得道高僧";

                this.ItemJianchaStatus = true;
                this.ItemYizhuStaus = true;
                this.ItemYingxiangStatus = true;
                this.ItemGuangzhu = guanzhu;
                break;
            case 3:
                this.ItemHuanzheId = "55667745";
                this.ItemHuanzheName = "胡锡敏";

                this.ItemJianchaStatus = false;
                this.ItemYizhuStaus = true;
                this.ItemYingxiangStatus = true;
                this.ItemGuangzhu = guanzhu;
                break;
            case 4:
                this.ItemHuanzheId = "55667767";
                this.ItemHuanzheName = "毛德操";

                this.ItemJianchaStatus = true;
                this.ItemYizhuStaus = false;
                this.ItemYingxiangStatus = true;
                this.ItemGuangzhu = guanzhu;
                break;
        }
    }

    public HuanzheStatus(int tmp) {
        this(tmp, true);
    }

    public HuanzheStatus(JSONObject o) {
        this.ItemHuanzheId = o.optString(JsonKey.ItemHuanzheId, "");
        this.ItemHuanzheName = o.optString(JsonKey.ItemHuanzheName, "");

        this.ItemJianchaStatus = o.optBoolean(JsonKey.ItemJianchaStatus, false);
        this.ItemYizhuStaus = o.optBoolean(JsonKey.ItemYizhuStaus, false);
        this.ItemYingxiangStatus = o.optBoolean(JsonKey.ItemYingxiangStatus, false);
        this.ItemGuangzhu = o.optBoolean(JsonKey.ItemGuangzhu, false);
    }

    public HuanzheStatus(JSONObject o, boolean guangzhu) {
        this.ItemHuanzheId = o.optString(JsonKey.ItemHuanzheId, "");
        this.ItemHuanzheName = o.optString(JsonKey.ItemHuanzheName, "");

        this.ItemJianchaStatus = o.optBoolean(JsonKey.ItemJianchaStatus, false);
        this.ItemYizhuStaus = o.optBoolean(JsonKey.ItemYizhuStaus, false);
        this.ItemYingxiangStatus = o.optBoolean(JsonKey.ItemYingxiangStatus, false);

        this.ItemGuangzhu = guangzhu;
    }

    public HuanzheStatus(JSONArray unReadObjectArray, JSONObject bingrenInfoObject) {
        this.bingrenInfo = new BingrenInfo(bingrenInfoObject);

        this.ItemHuanzheId = this.bingrenInfo.getItemHuanzheId();
        this.ItemHuanzheName = this.bingrenInfo.getItemHuanzheName();

        //遍历产生未读的检验/检查/医嘱ID列表  // "noRead":["type"="1","id"="20"],
        if(unReadObjectArray != null && unReadObjectArray.length() > 0) {
            for (int i = 0; i < unReadObjectArray.length(); i++) {
                String type = unReadObjectArray.optJSONObject(i).optString("type");
                String id = unReadObjectArray.optJSONObject(i).optString("typeId");

                switch (type){
                    case "0":
                        ItemUnreadJianchaIDList.add(id);
                        break;
                    case "1":
                        ItemUnreadYingxiangIDList.add(id);
                        break;
                    case "2":
                        ItemUnreadYizhuIDList.add(id);
                        break;
                }
            }
        }

        this.ItemJianchaStatus = (ItemUnreadJianchaIDList.size() != 0);
        this.ItemYizhuStaus = (ItemUnreadYizhuIDList.size() != 0);
        this.ItemYingxiangStatus = (ItemUnreadYingxiangIDList.size() != 0);

        this.ItemGuangzhu = true;
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

    public boolean getItemJianchaStatus() {
        return this.ItemJianchaStatus;
    }

    public void setItemJianchaStatus(boolean ItemJianchaStatus) {
        this.ItemJianchaStatus = ItemJianchaStatus;
    }

    public boolean getItemYizhuStaus() {
        return this.ItemYizhuStaus;
    }

    public void setItemYizhuStaus(boolean ItemYizhuStaus) {
        this.ItemYizhuStaus = ItemYizhuStaus;
    }

    public boolean getItemYingxiangStatus() {
        return this.ItemYingxiangStatus;
    }

    public void setItemYingxiangStatus(boolean ItemYingxiangStatus) {
        this.ItemYingxiangStatus = ItemYingxiangStatus;
    }

    public boolean getItemGuangzhu() {
        return this.ItemGuangzhu;
    }

    public void setItemGuangzhu(boolean ItemGuangzhu) {
        this.ItemGuangzhu = ItemGuangzhu;
    }
}
