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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bestplus.chuangshangjiuzhi.MainActivity;
import com.bestplus.chuangshangjiuzhi.R;
import com.bestplus.chuangshangjiuzhi.adapter.HuanzheStatusAdapter;
import com.bestplus.chuangshangjiuzhi.adapter.HuanzheStatusAdapter2;
import com.bestplus.chuangshangjiuzhi.adapter.JianchaTixingInfoAdapter;
import com.bestplus.chuangshangjiuzhi.adapter.JieguoNotifyInfoAdapter;
import com.bestplus.chuangshangjiuzhi.common.Variable;
import com.bestplus.chuangshangjiuzhi.entity.HuanzheStatus;
import com.bestplus.chuangshangjiuzhi.entity.JianchaTixingInfo;
import com.bestplus.chuangshangjiuzhi.entity.JieguoNotifyInfo;
import com.bestplus.chuangshangjiuzhi.interfaces.BasicListViewInterface;
import com.bestplus.chuangshangjiuzhi.view.BasicListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by maoamo on 2016/7/23.
 */
public class Fragment_Main_Jieguotixing extends Fragment
        implements View.OnClickListener,
        BasicListViewInterface,
        MainActivity.FragmentUpdateview, MainActivity.FragmentNFCNotify{
    private View view;

    private EditText textView_jiuzhenhao;
    private ImageView imageView_search;

    private JieguoNotifyInfoAdapter adapter;
    private List<JieguoNotifyInfo> items;
    //    private HuanzheStatusAdapter itemsOfSearchAdapter;
    private List<JieguoNotifyInfo> itemsOfSearch = new ArrayList<JieguoNotifyInfo>();
    private BasicListView listview;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_jieguotixing, container,false);
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
    }

    /**
     * 更新Fragment显示数据接口
     */
    public void fragmentUpdateview() {
        System.out.println("Fragment_Main_Jieguotixing fragmentUpdateview");

        if (items == null) {
            System.out.println("Fragment_Main_Jieguotixing fragmentUpdateview null");
            items = ((MainActivity) getActivity()).getItemsOfJieguoNotifyInfo();
            adapter = new JieguoNotifyInfoAdapter(getActivity(), items, handler);
            listview.setAdapter(adapter);
        } else {
            System.out.println("Fragment_Main_Jieguotixing fragmentUpdateview NOT null");
            System.out.println("Fragment_Main_Jieguotixing fragmentUpdateview " + items.size());
//            adapter.notifyDataSetChanged();  // 为什么没有刷新出来列表项？

//            adapter = new HuanzheStatusAdapter2(getActivity(), items, handler);
            listview.setAdapter(adapter);
        }
    }

    private void initViewHandler() {
        imageView_search.setOnClickListener(this);
    }

    private void initTitle() {

    }

    private void initData(){
        items = null;

        ((MainActivity)getActivity()).getCheckAndImagList();
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.imageView_search:
                itemsOfSearch.clear();
                System.out.println("want: " + textView_jiuzhenhao.getText().toString());
                for(int i = 0; i < items.size(); i ++){
                    JieguoNotifyInfo tmp = items.get(i);
                    System.out.println("tmp: " + tmp.getItemHuanzheId());
                    if(tmp.getItemHuanzheId().equals(textView_jiuzhenhao.getText().toString())){
                        itemsOfSearch.add(tmp);
                    }
                }

                items = itemsOfSearch;
                adapter = new JieguoNotifyInfoAdapter(getActivity(), items, handler);
                listview.setAdapter(adapter);
                System.out.println("update: " );

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
                case JianchaTixingInfoAdapter.CHOICE_CONTROL_ITEM:
                    Bundle bundle = msg.getData();
                    int positon = bundle.getInt("data");
                    System.out.println("touch HEAD " + positon);
                    break;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();

        initData();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public boolean onRefresh(MotionEvent ev) {
        System.out.println("Fragment_Main_Jieguotixing onRefresh");

        textView_jiuzhenhao.setText("");
        initData();
        listview.refreshComplete();
        return false;
    }

    @Override
    public void onMoreLoadding() {
        System.out.println("Fragment_Main_Jieguotixing onMoreLoadding");

        textView_jiuzhenhao.setText("");
        initData();
        listview.moreLoaddingComplete();
    }

    @Override
    public void foundNFCCODE(String nfcCode) {

    }
}

