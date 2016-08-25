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
import com.bestplus.chuangshangjiuzhi.common.Common;
import com.bestplus.chuangshangjiuzhi.common.Variable;
import com.bestplus.chuangshangjiuzhi.entity.JianchaResultGroup;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by maoamo on 2016/7/18.
 */
public class Fragment_Huanzhe_Jianyanjiancha extends Fragment implements View.OnClickListener, HuanzheActivity.HuanzheFragmentUpdateview{
    private View view;

    private TextView textView_startdate, textView_enddate;
    private Button button_search;

    private ExpandableListView mListView1;
    private List<JianchaResultGroup> items;
    private JianchaResultGroupAdapter myAdapter;

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
        view = inflater.inflate(R.layout.fragment_jianyanjiancha, container,false);
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

    private void addTestData(){
        JianchaResultGroup tmp1 = new JianchaResultGroup("1001", 0);
        JianchaResultGroup tmp2 = new JianchaResultGroup("1002", 1);
        JianchaResultGroup tmp3 = new JianchaResultGroup("1003", 1);
        JianchaResultGroup tmp4 = new JianchaResultGroup("1004", 0);
        JianchaResultGroup tmp5 = new JianchaResultGroup("1005", 0);;

        JianchaResultGroup tmp6 = new JianchaResultGroup("1006", 0);
        JianchaResultGroup tmp7 = new JianchaResultGroup("1007", 1);
        JianchaResultGroup tmp8 = new JianchaResultGroup("1008", 0);
        JianchaResultGroup tmp9 = new JianchaResultGroup("1009", 0);
        JianchaResultGroup tmp10 = new JianchaResultGroup("1010", 0);

        JianchaResultGroup tmp11 = new JianchaResultGroup("1011", 1);
        JianchaResultGroup tmp12 = new JianchaResultGroup("1012", 0);
        JianchaResultGroup tmp13 = new JianchaResultGroup("1013", 0);
        JianchaResultGroup tmp14 = new JianchaResultGroup("1014", 0);
        JianchaResultGroup tmp15 = new JianchaResultGroup("1015", 0);

        JianchaResultGroup tmp16 = new JianchaResultGroup("1016", 0);
        JianchaResultGroup tmp17 = new JianchaResultGroup("1017", 0);
        JianchaResultGroup tmp18 = new JianchaResultGroup("1018", 0);

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

    /**
     * 更新Fragment显示数据接口
     */
    public void fragmentUpdateview() {
        System.out.println("Fragment_Huanzhe_Jianyanjiancha fragmentUpdateview");

        if(items == null) {
            System.out.println("Fragment_Huanzhe_Jianyanjiancha fragmentUpdateview null");
            items = ((HuanzheActivity) getActivity()).getItemsOfJianchaResultGroup();
            myAdapter = new JianchaResultGroupAdapter(getActivity(), items, handler);
            mListView1.setAdapter(myAdapter);
        }else {
            System.out.println("Fragment_Huanzhe_Jianyanjiancha fragmentUpdateview NOT null");
            myAdapter.notifyDataSetChanged();
            mListView1.setAdapter(myAdapter);
        }
    }

    private void initView() {
        textView_startdate = (TextView) view.findViewById(R.id.textView_startdate);
        textView_enddate = (TextView) view.findViewById(R.id.textView_enddate);
        button_search = (Button) view.findViewById(R.id.button_search);

        //for test data
        //addTestData();

        mListView1 = (ExpandableListView) view.findViewById(R.id.listView1);
//        myAdapter = new JianchaResultGroupAdapter(getActivity(), items);
//        mListView1.setAdapter(myAdapter);

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
        ((HuanzheActivity)getActivity()).getCheckInfoByCodeTime(getActivityHuanzheID(),
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
                        JianchaResultGroup jianchaResultGroup = items.get(positon);
                        if(jianchaResultGroup != null && jianchaResultGroup.getItemReadflag() == 1) {
                            // 记录类型 + 记录ID + 医生ID
                            String recordId = String.valueOf(items.get(positon).getItemID());
                            String recordType = "0";
                            System.out.println("recordId: " + recordId);
                            System.out.println("recordType: " + recordType);
                            ((HuanzheActivity) getActivity()).modifyCheckAndImageUnreadReportStatus(
                                    Variable.cs_user.getMemberCode(),
                                    recordType,
                                    recordId);
                        }
                    }
                    break;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();

        System.out.println("Fragment_Huanzhe_Jianyanjiancha onResume, this= " + this.toString());
        initData();
    }

    @Override
    public void onPause() {
        super.onPause();

        System.out.println("Fragment_Huanzhe_Jianyanjiancha onPause");
    }
}
