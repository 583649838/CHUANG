package com.bestplus.chuangshangjiuzhi.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bestplus.chuangshangjiuzhi.HuanzheActivity;
import com.bestplus.chuangshangjiuzhi.R;
import com.bestplus.chuangshangjiuzhi.common.JsonKey;
import com.bestplus.chuangshangjiuzhi.common.Variable;
import com.bestplus.chuangshangjiuzhi.dialog.CommonDialog;
import com.bestplus.chuangshangjiuzhi.dialog.CustomProgressDialog;
import com.bestplus.chuangshangjiuzhi.entity.BingrenInfo;
import com.bestplus.chuangshangjiuzhi.util.DebugLog;
import com.bestplus.chuangshangjiuzhi.util.HttpClientRequest;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by maoamo on 2016/7/18.
 */
public class Fragment_Huanzhe_Jibenxinxi extends Fragment implements View.OnClickListener, HuanzheActivity.HuanzheFragmentUpdateview{

    private View view;

    private BingrenInfo bingrenInfo;
    private TextView textView_xingming,
            textView_juzhenhao,
            textView_xingbie,
            textView_nianling,
            textView_shengao,
            textView_tizhong,
            textView_shenfenzhenghaoma;

    private String huanzheID;

    private String getActivityHuanzheID(){
        return ((HuanzheActivity)getActivity()).getHuanzheID();
    }
    private void updateActivityHuanzheName(String name){
        ((HuanzheActivity)getActivity()).updateHuanzheName(name);
    }

    private BingrenInfo getActivityBingrenInfo(){
        return ((HuanzheActivity)getActivity()).getBingrenInfo();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println("Fragment_Huanzhe_Jibenxinxi onCreateView");

        view = inflater.inflate(R.layout.fragment_jibenxinxi, container, false);
        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("Fragment_Huanzhe_Jibenxinxi onActivityCreated");

        huanzheID = getActivityHuanzheID();

        initTitle();
        initView();
        initViewHandler();
    }

    private void initView() {
        textView_xingming = (TextView) view.findViewById(R.id.textView_xingming);
        textView_juzhenhao = (TextView) view.findViewById(R.id.textView_juzhenhao);
        textView_xingbie = (TextView) view.findViewById(R.id.textView_xingbie);
        textView_nianling = (TextView) view.findViewById(R.id.textView_nianling);
        textView_shengao = (TextView) view.findViewById(R.id.textView_shengao);
        textView_tizhong = (TextView) view.findViewById(R.id.textView_tizhong);
        textView_shenfenzhenghaoma = (TextView) view.findViewById(R.id.textView_shenfenzhenghaoma);
    }

    public void fragmentUpdateview(){
        System.out.println("Fragment_Huanzhe_Jibenxinxi fragmentUpdateview");

        bingrenInfo = getActivityBingrenInfo();
        if(bingrenInfo != null) {
            textView_xingming.setText(bingrenInfo.getItemHuanzheName());
            updateActivityHuanzheName(bingrenInfo.getItemHuanzheName()); //更新activity中的患者姓名
            textView_juzhenhao.setText(bingrenInfo.getItemHuanzheId());
            textView_xingbie.setText(bingrenInfo.getItemXingbie());
            textView_nianling.setText(bingrenInfo.getItemNianling());
            textView_shengao.setText(bingrenInfo.getItemShengao());
            textView_tizhong.setText(bingrenInfo.getItemTizhong());
            textView_shenfenzhenghaoma.setText(bingrenInfo.getItemHuanZheDianhua());
        }
    }

    private void initViewHandler() {

    }

    private void initTitle() {

    }

    private void initData() {
    //
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("Fragment_Huanzhe_Jibenxinxi onResume, this= " + this.toString());

        fragmentUpdateview();
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("Fragment_Huanzhe_Jibenxinxi onPause");
    }



}
