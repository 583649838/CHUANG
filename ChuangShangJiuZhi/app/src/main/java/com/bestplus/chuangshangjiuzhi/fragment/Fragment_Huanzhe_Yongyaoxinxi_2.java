package com.bestplus.chuangshangjiuzhi.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bestplus.chuangshangjiuzhi.HuanzheActivity;
import com.bestplus.chuangshangjiuzhi.R;
import com.bestplus.chuangshangjiuzhi.adapter.JianchaResultGroupAdapter;
import com.bestplus.chuangshangjiuzhi.adapter.YongyaoInfoGroupAdpater;
import com.bestplus.chuangshangjiuzhi.common.Common;
import com.bestplus.chuangshangjiuzhi.common.Variable;
import com.bestplus.chuangshangjiuzhi.entity.YongyaoInfoGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by maoamo on 2016/7/25.
 */
public class Fragment_Huanzhe_Yongyaoxinxi_2 extends Fragment implements View.OnClickListener, HuanzheActivity.HuanzheFragmentUpdateview{
    private View view;
    private ExpandableListView mListView1;

    private List<YongyaoInfoGroup> items;
    private YongyaoInfoGroupAdpater myAdapter;

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
        System.out.println("Fragment_Huanzhe_Yongyaoxinxi_2 onCreateView");

        view = inflater.inflate(R.layout.fragment_yongyaoxinxi_2, container,false);
        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("Fragment_Huanzhe_Yongyaoxinxi_2 onActivityCreated");
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
//        addTestData();

        mListView1 = (ExpandableListView) view.findViewById(R.id.listView1);
        mListView1.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());

//        myAdapter = new YongyaoInfoGroupAdpater(getActivity(), mHead, items);
//        mListView1.setAdapter(myAdapter);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String date=formatter.format(now);
        textView_startdate.setText(date);
        textView_enddate.setText(date);
    }

    /**
     * 更新Fragment显示数据接口
     */
    public void fragmentUpdateview() {
        System.out.println("Fragment_Huanzhe_Yongyaoxinxi_2 fragmentUpdateview");

        if (items == null) {
            System.out.println("Fragment_Huanzhe_Yongyaoxinxi_2 fragmentUpdateview null");
            items = ((HuanzheActivity) getActivity()).getItemsOfYongyaoInfoGroup();
            myAdapter = new YongyaoInfoGroupAdpater(getActivity(), mHead, items, handler);
            mListView1.setAdapter(myAdapter);
        } else {
            System.out.println("Fragment_Huanzhe_Yongyaoxinxi_2 fragmentUpdateview NOT null");
            myAdapter.notifyDataSetChanged();
            mListView1.setAdapter(myAdapter);
        }
    }

    private void initViewHandler() {
        textView_startdate.setOnClickListener(this);
        textView_enddate.setOnClickListener(this);
        button_search.setOnClickListener(this);
    }

    private void initTitle() {

    }

    private void initData(){
        ((HuanzheActivity)getActivity()).getCareInfoByCodeTime(getActivityHuanzheID(),
                textView_startdate.getText().toString(),
                textView_enddate.getText().toString());
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
                initData();
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
                case JianchaResultGroupAdapter.CHOICE_CONTROL_ITEM:
                    Bundle bundle = msg.getData();
                    int positon = bundle.getInt("data");
                    System.out.println("touch: " + positon);

                    // 修改该记录状态
                    if(items != null){
                        YongyaoInfoGroup yongyaoInfoGroup = items.get(positon);
                        if(yongyaoInfoGroup != null && yongyaoInfoGroup.getItemReadFlag() == 1) {
                            // 记录类型（2） + 记录ID + 医生ID + 病人ID
                            String groupCode = yongyaoInfoGroup.getItemYongyaoxinxiZuhao();
                            String yizhuCode = yongyaoInfoGroup.getItemYongyaoxinxiYizhuID();
                            String recordType = "2";
                            System.out.println("groupCode: " + groupCode);
                            System.out.println("yizhuCode: " + yizhuCode);
                            ((HuanzheActivity) getActivity()).modifyYizhuUnreadReportStatus(
                                    Variable.cs_user.getMemberCode(),
                                    getActivityHuanzheID(),
                                    recordType,
                                    yizhuCode,
                                    groupCode);
                        }
                    }
                    break;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();

        System.out.println("Fragment_Huanzhe_Yongyaoxinxi_2 onResume, this= " + this.toString());

        initData();
    }

    @Override
    public void onPause() {
        super.onPause();

        System.out.println("Fragment_Huanzhe_Yongyaoxinxi_2 onPause");
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
