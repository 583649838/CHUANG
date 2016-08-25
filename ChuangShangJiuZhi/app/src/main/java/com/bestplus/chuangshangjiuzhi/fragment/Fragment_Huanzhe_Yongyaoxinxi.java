package com.bestplus.chuangshangjiuzhi.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bestplus.chuangshangjiuzhi.HuanzheActivity;
import com.bestplus.chuangshangjiuzhi.R;
import com.bestplus.chuangshangjiuzhi.adapter.YongyaoInfoAdapter;
import com.bestplus.chuangshangjiuzhi.common.Common;
import com.bestplus.chuangshangjiuzhi.entity.YongyaoInfo;
import com.bestplus.chuangshangjiuzhi.view.MyHScrollView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by maoamo on 2016/7/18.
 */
public class Fragment_Huanzhe_Yongyaoxinxi extends Fragment implements View.OnClickListener {
    private View view;
    private ListView mListView1;
    private List<YongyaoInfo> items = new ArrayList<YongyaoInfo>();
    private YongyaoInfoAdapter myAdapter;
    private RelativeLayout mHead;
    private LinearLayout main;

    private TextView textView_startdate, textView_enddate;
    private Button button_search;

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
        view = inflater.inflate(R.layout.fragment_yongyaoxinxi, container,false);
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

        mHead = (RelativeLayout) view.findViewById(R.id.head);
        mHead.setFocusable(true);
        mHead.setClickable(true);
        mHead.setBackgroundColor(Color.parseColor("#b2d235"));
        mHead.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());

        //for test data
        addTestData();

        mListView1 = (ListView) view.findViewById(R.id.listView1);
        mListView1.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());

        myAdapter = new YongyaoInfoAdapter(getActivity(),mHead, items);
        mListView1.setAdapter(myAdapter);

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
        YongyaoInfo tmp1 = new YongyaoInfo(0);
        YongyaoInfo tmp2 = new YongyaoInfo(1);
        YongyaoInfo tmp3 = new YongyaoInfo(2);
        YongyaoInfo tmp4 = new YongyaoInfo(3);
        YongyaoInfo tmp5 = new YongyaoInfo(4);

        YongyaoInfo tmp6 = new YongyaoInfo(0);
        YongyaoInfo tmp7 = new YongyaoInfo(1);
        YongyaoInfo tmp8 = new YongyaoInfo(2);
        YongyaoInfo tmp9 = new YongyaoInfo(3);
        YongyaoInfo tmp10 = new YongyaoInfo(4);

        YongyaoInfo tmp11 = new YongyaoInfo(0);
        YongyaoInfo tmp12 = new YongyaoInfo(1);
        YongyaoInfo tmp13 = new YongyaoInfo(2);
        YongyaoInfo tmp14 = new YongyaoInfo(3);
        YongyaoInfo tmp15 = new YongyaoInfo(4);

        YongyaoInfo tmp16 = new YongyaoInfo(0);
        YongyaoInfo tmp17 = new YongyaoInfo(1);
        YongyaoInfo tmp18 = new YongyaoInfo(2);
        YongyaoInfo tmp19 = new YongyaoInfo(3);
        YongyaoInfo tmp20 = new YongyaoInfo(4);

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

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    class ListViewAndHeadViewTouchLinstener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View arg0, MotionEvent arg1) {
            //当在列头 和 listView控件上touch时，将这个touch的事件分发给 ScrollView
            HorizontalScrollView headSrcrollView = (HorizontalScrollView) mHead
                    .findViewById(R.id.horizontalScrollView1);
            headSrcrollView.onTouchEvent(arg1);
            return false;
        }
    }
}
