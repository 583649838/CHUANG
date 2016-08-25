package com.bestplus.chuangshangjiuzhi.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.bestplus.chuangshangjiuzhi.HuanzheActivity;
import com.bestplus.chuangshangjiuzhi.MainActivity;
import com.bestplus.chuangshangjiuzhi.R;
import com.bestplus.chuangshangjiuzhi.adapter.HuanzheStatusAdapter;
import com.bestplus.chuangshangjiuzhi.adapter.HuanzheStatusAdapter2;
import com.bestplus.chuangshangjiuzhi.adapter.JieguoNotifyInfoAdapter;
import com.bestplus.chuangshangjiuzhi.adapter.YongyaoInfoGroupAdpater;
import com.bestplus.chuangshangjiuzhi.common.Variable;
import com.bestplus.chuangshangjiuzhi.entity.HuanzheStatus;
import com.bestplus.chuangshangjiuzhi.entity.JieguoNotifyInfo;
import com.bestplus.chuangshangjiuzhi.interfaces.BasicListViewInterface;
import com.bestplus.chuangshangjiuzhi.view.BasicListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maoamo on 2016/7/23.
 */
public class Fragment_Main_Huanzhezhongxin extends Fragment
        implements View.OnClickListener,
        BasicListViewInterface,
        MainActivity.FragmentUpdateview, MainActivity.FocusUpdateview, MainActivity.FragmentNFCNotify{
    private View view;

    private EditText textView_jiuzhenhao;
    private ImageView imageView_search;

    private HuanzheStatusAdapter2 adapter;
    private List<HuanzheStatus> items;
    private List<HuanzheStatus> itemsOfSearch = new ArrayList<HuanzheStatus>();
    private BasicListView listview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_huanzhezhongxin, container,false);
        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initTitle();
        initView();
        initViewHandler();
    }

    private void initView() {
        textView_jiuzhenhao = (EditText) view.findViewById(R.id.textView_jiuzhenhao);
        imageView_search = (ImageView) view.findViewById(R.id.imageView_search);

        listview = (BasicListView) view.findViewById(R.id.list);
        listview.setOnBookResourceListViewListener(this);
        //for test data
//        addTestData();

//        adapter = new HuanzheStatusAdapter2(getActivity(), items, handler);
//        listview.setAdapter(adapter);
    }

    /**
     * 更新Fragment显示数据接口
     */
    public void fragmentUpdateview() {
        System.out.println("Fragment_Main_Huanzhezhongxin fragmentUpdateview");

        if (items == null) {
            System.out.println("Fragment_Main_Huanzhezhongxin fragmentUpdateview null");
            items = ((MainActivity) getActivity()).getItemsOfHuanzheStatus();
            adapter = new HuanzheStatusAdapter2(getActivity(), items, handler);
            listview.setAdapter(adapter);
        } else {
            System.out.println("Fragment_Main_Huanzhezhongxin fragmentUpdateview NOT null");
            System.out.println("Fragment_Main_Huanzhezhongxin fragmentUpdateview " + items.size());
//            adapter.notifyDataSetChanged();  // 为什么没有刷新出来列表项？

//            adapter = new HuanzheStatusAdapter2(getActivity(), items, handler);
            listview.setAdapter(adapter);
        }
    }

    /**
     *  更新某病人的关注状态
     * @param patientCode
     * @param newStatus
     */
    @Override
    public void focusUpdateview(String patientCode, boolean newStatus) {
        if(items != null){
            for(HuanzheStatus huanzhe: items){
                if(huanzhe.getItemHuanzheId().equals(patientCode)){
                    huanzhe.setItemGuangzhu(newStatus);
                    adapter.notifyDataSetChanged();
                    break;
                }
            }
        }
    }

    private void initViewHandler() {
        imageView_search.setOnClickListener(this);
    }

    private void initTitle() {

    }

    private void initData(){
        items = null;

        ((MainActivity)getActivity()).getAllPatient(Variable.cs_user.getMemberCode());
    }

    private void addTestData(){
        HuanzheStatus tmp1 = new HuanzheStatus(0, false);
        HuanzheStatus tmp2 = new HuanzheStatus(1, false);
        HuanzheStatus tmp3 = new HuanzheStatus(2, false);
        HuanzheStatus tmp4 = new HuanzheStatus(3, false);
        HuanzheStatus tmp5 = new HuanzheStatus(4, false);

        HuanzheStatus tmp6 = new HuanzheStatus(0, false);
        HuanzheStatus tmp7 = new HuanzheStatus(1, false);
        HuanzheStatus tmp8 = new HuanzheStatus(2, false);
        HuanzheStatus tmp9 = new HuanzheStatus(3, false);
        HuanzheStatus tmp10 = new HuanzheStatus(4, false);

        HuanzheStatus tmp11 = new HuanzheStatus(0, false);
        HuanzheStatus tmp12 = new HuanzheStatus(1, false);
        HuanzheStatus tmp13 = new HuanzheStatus(2, false);
        HuanzheStatus tmp14 = new HuanzheStatus(3, false);
        HuanzheStatus tmp15 = new HuanzheStatus(4, false);

        HuanzheStatus tmp16 = new HuanzheStatus(0, false);
        HuanzheStatus tmp17 = new HuanzheStatus(1, false);
        HuanzheStatus tmp18 = new HuanzheStatus(2, false);
        HuanzheStatus tmp19 = new HuanzheStatus(3, false);
        HuanzheStatus tmp20 = new HuanzheStatus(4, false);

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
            case R.id.imageView_search:
                itemsOfSearch.clear();
                System.out.println("want: " + textView_jiuzhenhao.getText().toString());
                for(int i = 0; i < items.size(); i ++){
                    HuanzheStatus tmp = items.get(i);
                    System.out.println("tmp: " + tmp.getItemHuanzheId());
                    if(tmp.getItemHuanzheId().equals(textView_jiuzhenhao.getText().toString())){
                        itemsOfSearch.add(tmp);
                        items = itemsOfSearch;
                        adapter = new HuanzheStatusAdapter2(getActivity(), items, handler);
                        listview.setAdapter(adapter);
                        System.out.println("update: " );
                        break;
                    }
                }
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
                case HuanzheStatusAdapter2.CHOICE_CONTROL_HEAD:
                    Bundle bundle = msg.getData();
                    int positon = bundle.getInt("data");
                    System.out.println("touch HEAD " + positon);
                    break;
                case HuanzheStatusAdapter2.CHOICE_CONTROL_1:
                    bundle = msg.getData();
                    positon = bundle.getInt("data");
                    System.out.println("touch 1 " + positon);

                    //进行关注处理
                    if (items.get(positon).getItemGuangzhu()) {
                        // 显示 "取消关注"
                        System.out.println("Fragment_Main_Huanzhezhongxin cancel focuing");

                        ((MainActivity)getActivity()).modifyPatientFocusStatus(Variable.cs_user.getMemberCode(),
                                items.get(positon).getItemHuanzheId(),
                                false);
                    }else{
                        // 显示 "关 注"
                        System.out.println("Fragment_Main_Huanzhezhongxin focuing");

                        ((MainActivity)getActivity()).modifyPatientFocusStatus(Variable.cs_user.getMemberCode(),
                                items.get(positon).getItemHuanzheId(),
                                true);
                    }
                    break;
                case HuanzheStatusAdapter2.CHOICE_CONTROL_TAIL:
                    bundle = msg.getData();
                    positon = bundle.getInt("data");
                    System.out.println("touch 2 " + positon);
                    break;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();

        System.out.println("Fragment_Main_Huanzhezhongxin onResume");

        initData();
    }

    @Override
    public void onPause() {
        super.onPause();

        System.out.println("Fragment_Main_Huanzhezhongxin onPause");
    }

    @Override
    public boolean onRefresh(MotionEvent ev) {
        System.out.println("Fragment_Main_Huanzhezhongxin onRefresh");

        textView_jiuzhenhao.setText("");
        initData();
        listview.refreshComplete();
        return false;
    }

    @Override
    public void onMoreLoadding() {
        System.out.println("Fragment_Main_Huanzhezhongxin onMoreLoadding");

        textView_jiuzhenhao.setText("");
        initData();
        listview.moreLoaddingComplete();
    }

    @Override
    public void foundNFCCODE(String nfcCode) {

    }
}
