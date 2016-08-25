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
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.bestplus.chuangshangjiuzhi.HuanzheActivity;
import com.bestplus.chuangshangjiuzhi.R;
import com.bestplus.chuangshangjiuzhi.adapter.JianchaResultGroupAdapter;
import com.bestplus.chuangshangjiuzhi.adapter.YingxiangResultGroupAdapter;
import com.bestplus.chuangshangjiuzhi.common.Common;
import com.bestplus.chuangshangjiuzhi.common.Variable;
import com.bestplus.chuangshangjiuzhi.entity.YingxiangResultGroup;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by maoamo on 2016/7/18.
 */
public class Fragment_Huanzhe_Tuxiangyingpian extends Fragment implements View.OnClickListener, HuanzheActivity.HuanzheFragmentUpdateview{
    private View view;

    private TextView textView_startdate, textView_enddate;
    private Button button_search;

    private ExpandableListView mListView1;
    private List<YingxiangResultGroup> items;
    private YingxiangResultGroupAdapter myAdapter;

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
        System.out.println("Fragment_Huanzhe_Tuxiangyingpian onCreateView");

        view = inflater.inflate(R.layout.fragment_tuxiangyingpian, container,false);
        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        System.out.println("Fragment_Huanzhe_Tuxiangyingpian onActivityCreated");

        huanzheID = getActivityHuanzheID();

        initTitle();
        initView();
        initViewHandler();
    }

    private void addTestData(){
        YingxiangResultGroup tmp1 = new YingxiangResultGroup("1001", 0);
        YingxiangResultGroup tmp2 = new YingxiangResultGroup("1002", 1);
        YingxiangResultGroup tmp3 = new YingxiangResultGroup("1003", 1);
        YingxiangResultGroup tmp4 = new YingxiangResultGroup("1004", 0);
        YingxiangResultGroup tmp5 = new YingxiangResultGroup("1005", 0);;

        YingxiangResultGroup tmp6 = new YingxiangResultGroup("1006", 0);
        YingxiangResultGroup tmp7 = new YingxiangResultGroup("1007", 1);
        YingxiangResultGroup tmp8 = new YingxiangResultGroup("1008", 0);
        YingxiangResultGroup tmp9 = new YingxiangResultGroup("1009", 0);
        YingxiangResultGroup tmp10 = new YingxiangResultGroup("1010", 0);

        YingxiangResultGroup tmp11 = new YingxiangResultGroup("1011", 1);
        YingxiangResultGroup tmp12 = new YingxiangResultGroup("1012", 0);
        YingxiangResultGroup tmp13 = new YingxiangResultGroup("1013", 0);
        YingxiangResultGroup tmp14 = new YingxiangResultGroup("1014", 0);
        YingxiangResultGroup tmp15 = new YingxiangResultGroup("1015", 0);

        YingxiangResultGroup tmp16 = new YingxiangResultGroup("1016", 0);
        YingxiangResultGroup tmp17 = new YingxiangResultGroup("1017", 0);
        YingxiangResultGroup tmp18 = new YingxiangResultGroup("1018", 0);

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
    }

    private void initView() {
        textView_startdate = (TextView) view.findViewById(R.id.textView_startdate);
        textView_enddate = (TextView) view.findViewById(R.id.textView_enddate);
        button_search = (Button) view.findViewById(R.id.button_search);

        //for test data
//        addTestData();

        mListView1 = (ExpandableListView) view.findViewById(R.id.listView1);
//        myAdapter = new YingxiangResultGroupAdapter(getActivity(), items);
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
        System.out.println("Fragment_Huanzhe_Tuxiangyingpian fragmentUpdateview");

        if(items == null) {
            System.out.println("Fragment_Huanzhe_Jianyanjiancha fragmentUpdateview null");
            items = ((HuanzheActivity) getActivity()).getItemsOfYingxiangResultGroup();
            myAdapter = new YingxiangResultGroupAdapter(getActivity(), items, handler);
            mListView1.setAdapter(myAdapter);
        }else {
            System.out.println("Fragment_Huanzhe_Jianyanjiancha fragmentUpdateview NOT null");
            System.out.println("Fragment_Huanzhe_Jianyanjiancha fragmentUpdateview " + items.size());
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
        ((HuanzheActivity)getActivity()).getImagByIdAndTime(getActivityHuanzheID(),
                textView_startdate.getText().toString(),
                textView_enddate.getText().toString());
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
                        YingxiangResultGroup yingxiangResultGroup = items.get(positon);
                        if(yingxiangResultGroup != null && yingxiangResultGroup.getItemReadFlag() == 1) {
                            // 记录类型 + 记录ID + 医生ID
                            String recordId = String.valueOf(items.get(positon).getItemZuhao());
                            String recordType = "1";//String.valueOf(items.get(positon).getItemLeixing());
                            System.out.println("recordId: " + recordId);
                            System.out.println("recordType: " + recordType);
                            ((HuanzheActivity) getActivity()).modifyCheckAndImageUnreadReportStatus(Variable.cs_user.getMemberCode(),
                                    recordType,
                                    recordId);
                        }
                    }
                    break;
            }
        }
    };

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

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("Fragment_Huanzhe_Tuxiangyingpian onResume, this= " + this.toString());

        initData();
    }

    @Override
    public void onPause() {
        super.onPause();

        System.out.println("Fragment_Huanzhe_Tuxiangyingpian onPause");
    }
}
