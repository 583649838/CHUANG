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
import android.widget.ListView;
import android.widget.TextView;

import com.bestplus.chuangshangjiuzhi.HuanzheActivity;
import com.bestplus.chuangshangjiuzhi.R;
import com.bestplus.chuangshangjiuzhi.adapter.HuanzheJiuzhiInfoAdapter;
import com.bestplus.chuangshangjiuzhi.common.Common;
import com.bestplus.chuangshangjiuzhi.entity.HuanzheJiuzhiInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by maoamo on 2016/7/20.
 */
public class Fragment_Huanzhe_jiuzhijilu extends Fragment implements View.OnClickListener{
    private View view;

    private TextView textView_startdate, textView_enddate;
    private Button button_search;

    private HuanzheJiuzhiInfoAdapter adapter;
    private List<HuanzheJiuzhiInfo> items = new ArrayList<HuanzheJiuzhiInfo>();
    private ListView listview;

    private String huanzheID;

    private String getActivityHuanzheID(){
        return ((HuanzheActivity)getActivity()).getHuanzheID();
    }
    private void updateActivityHuanzheName(String name){
        ((HuanzheActivity)getActivity()).updateHuanzheName(name);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_jiuzhijilu, container,false);
        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        huanzheID = getActivityHuanzheID();

        initTitle();
        initView();
        initViewHandler();
    }

    private void initView() {
        textView_startdate = (TextView) view.findViewById(R.id.textView_startdate);
        textView_enddate = (TextView) view.findViewById(R.id.textView_enddate);
        button_search = (Button) view.findViewById(R.id.button_search);

        listview = (ListView) view.findViewById(R.id.list);
        //for test data
        addTestData();

        adapter = new HuanzheJiuzhiInfoAdapter(getActivity(), items, handler);
        listview.setAdapter(adapter);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String date=formatter.format(now);
        textView_startdate.setText(date);
        textView_enddate.setText(date);
    }
    private void initViewHandler() {
        textView_startdate.setOnClickListener(this);
        textView_enddate.setOnClickListener(this);
        button_search.setOnClickListener(this);
    }

    private void initTitle() {

    }

    private void initData(){

    }

    private void addTestData(){
        HuanzheJiuzhiInfo tmp1 = new HuanzheJiuzhiInfo(0);
        HuanzheJiuzhiInfo tmp2 = new HuanzheJiuzhiInfo(1);
        HuanzheJiuzhiInfo tmp3 = new HuanzheJiuzhiInfo(2);
        HuanzheJiuzhiInfo tmp4 = new HuanzheJiuzhiInfo(3);
        HuanzheJiuzhiInfo tmp5 = new HuanzheJiuzhiInfo(4);

        HuanzheJiuzhiInfo tmp6 = new HuanzheJiuzhiInfo(0);
        HuanzheJiuzhiInfo tmp7 = new HuanzheJiuzhiInfo(1);
        HuanzheJiuzhiInfo tmp8 = new HuanzheJiuzhiInfo(2);
        HuanzheJiuzhiInfo tmp9 = new HuanzheJiuzhiInfo(3);
        HuanzheJiuzhiInfo tmp10 = new HuanzheJiuzhiInfo(4);

        HuanzheJiuzhiInfo tmp11 = new HuanzheJiuzhiInfo(0);
        HuanzheJiuzhiInfo tmp12 = new HuanzheJiuzhiInfo(1);
        HuanzheJiuzhiInfo tmp13 = new HuanzheJiuzhiInfo(2);
        HuanzheJiuzhiInfo tmp14 = new HuanzheJiuzhiInfo(3);
        HuanzheJiuzhiInfo tmp15 = new HuanzheJiuzhiInfo(4);

        HuanzheJiuzhiInfo tmp16 = new HuanzheJiuzhiInfo(0);
        HuanzheJiuzhiInfo tmp17 = new HuanzheJiuzhiInfo(1);
        HuanzheJiuzhiInfo tmp18 = new HuanzheJiuzhiInfo(2);
        HuanzheJiuzhiInfo tmp19 = new HuanzheJiuzhiInfo(3);
        HuanzheJiuzhiInfo tmp20 = new HuanzheJiuzhiInfo(4);

        items.add(tmp1);
        items.add(tmp2);
        items.add(tmp3);
        items.add(tmp4);
        items.add(tmp5);
        items.add(tmp6);
        items.add(tmp7);
        items.add(tmp8);
        items.add(tmp9);
        items.add(tmp10);
        items.add(tmp11);
        items.add(tmp12);
        items.add(tmp13);
        items.add(tmp14);
        items.add(tmp15);
        items.add(tmp16);
        items.add(tmp17);
        items.add(tmp18);
        items.add(tmp19);
        items.add(tmp20);
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.textView_startdate:
                Common.showDatePopupWindow(getActivity(), textView_startdate);
                break;
            case R.id.textView_enddate:
                Common.showDatePopupWindow(getActivity(), textView_enddate);
                break;
            case R.id.button_search:
                break;
            default:
                break;
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case 1:
                    break;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}

