package com.bestplus.chuangshangjiuzhi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bestplus.chuangshangjiuzhi.MainActivity;
import com.bestplus.chuangshangjiuzhi.R;
import com.bestplus.chuangshangjiuzhi.XinxichakanActivity;
import com.bestplus.chuangshangjiuzhi.XinxiluruActivity;
import com.bestplus.chuangshangjiuzhi.adapter.HuanzheJiuzhiInfoAdapter;
import com.bestplus.chuangshangjiuzhi.common.Common;
import com.bestplus.chuangshangjiuzhi.common.Variable;
import com.bestplus.chuangshangjiuzhi.entity.BingrenInfo;
import com.bestplus.chuangshangjiuzhi.entity.HuanzheJiuzhiInfo;
import com.bestplus.chuangshangjiuzhi.interfaces.BasicListViewInterface;
import com.bestplus.chuangshangjiuzhi.view.BasicListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by maoamo on 2016/7/23.
 */
public class Fragment_Main_Chuangshajilu extends Fragment
        implements View.OnClickListener,
            BasicListViewInterface,
            MainActivity.FragmentUpdateview, MainActivity.FragmentUpdateBingrenView, MainActivity.FragmentNFCNotify{
    private View view;

    private TextView textView_startdate, textView_enddate; //查询起始时间与结束时间
    private Button button_new; //新增
    private EditText textView_jiuzhenhao; //病例号
    private ImageView imageView_search; //查询

    private HuanzheJiuzhiInfoAdapter adapter;
    private List<HuanzheJiuzhiInfo> items;
    private BasicListView listview;

    private BingrenInfo bingrenInfo;
    private String NFCCode, NFC_jiuzhenhao, NFC_name;
    private EditText editText4; //NAME
    private String temp;//监听前的文本

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_chuangshanjilu, container,false);
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
        textView_startdate = (TextView) view.findViewById(R.id.textView_startdate);
        textView_enddate = (TextView) view.findViewById(R.id.textView_enddate);

        editText4 = (EditText) view.findViewById(R.id.editText4);
        textView_jiuzhenhao = (EditText) view.findViewById(R.id.textView_jiuzhenhao);
        imageView_search = (ImageView) view.findViewById(R.id.imageView_search);
        button_new = (Button) view.findViewById(R.id.button_new);

        listview = (BasicListView) view.findViewById(R.id.list);
        listview.setOnBookResourceListViewListener(this);

//        adapter = new HuanzheJiuzhiInfoAdapter(getActivity(), items, handler);
//        listview.setAdapter(adapter);

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
        System.out.println("Fragment_Main_Chuangshajilu fragmentUpdateview");

        if (items == null) {
            System.out.println("Fragment_Main_Chuangshajilu fragmentUpdateview null");
            items = ((MainActivity) getActivity()).getItemsOfHuanzheJiuzhiInfo();
            adapter = new HuanzheJiuzhiInfoAdapter(getActivity(), items, handler);
            listview.setAdapter(adapter);
        } else {
            System.out.println("Fragment_Main_Chuangshajilu fragmentUpdateview NOT null");
            System.out.println("Fragment_Main_Chuangshajilu fragmentUpdateview " + items.size());
//            adapter.notifyDataSetChanged();  // 为什么没有刷新出来列表项？

//            adapter = new HuanzheStatusAdapter2(getActivity(), items, handler);
            listview.setAdapter(adapter);
        }
    }

    public void fragemntUpdateBingrenInfo(BingrenInfo bingrenInfo){
        if(bingrenInfo != null){
            this.bingrenInfo = bingrenInfo; // 保存当前查询到的病人信息对象

            this.editText4.setText(bingrenInfo.getItemHuanzheName());
        }
    }
    public void fragemntUpdateBingrenInfoByNFC(BingrenInfo bingrenInfo){
        if(bingrenInfo != null){
            this.bingrenInfo = bingrenInfo; // 保存当前查询到的病人信息对象

            // 注意次序
            this.textView_jiuzhenhao.setText(bingrenInfo.getItemHuanzheId());
            this.editText4.setText(bingrenInfo.getItemHuanzheName());

            NFC_jiuzhenhao = bingrenInfo.getItemHuanzheId();
            NFC_name = bingrenInfo.getItemHuanzheName();
        }
    }

    private void initViewHandler() {
        textView_startdate.setOnClickListener(this);
        textView_enddate.setOnClickListener(this);

        imageView_search.setOnClickListener(this);
        button_new.setOnClickListener(this);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("position : " + position);

                Intent intent = new Intent();
                intent.setClass(getActivity(), XinxichakanActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("HuanzheJiuzhiInfo", items.get(position - 1));
                intent.putExtras(bundle);
                getActivity().startActivity(intent);

            }
        });

        // 输入不同的就诊号就清除掉之前的姓名
        textView_jiuzhenhao.addTextChangedListener(new TextWatcher() {
//            08-09 17:56:59.490 4064-4064/com.bestplus.chuangshangjiuzhi I/System.out: beforeTextChanged
//            08-09 17:56:59.490 4064-4064/com.bestplus.chuangshangjiuzhi I/System.out: onTextChanged 1
//            08-09 17:56:59.490 4064-4064/com.bestplus.chuangshangjiuzhi I/System.out: onTextChanged !=
//            08-09 17:56:59.497 4064-4064/com.bestplus.chuangshangjiuzhi I/System.out: afterTextChanged
//            08-09 17:57:10.451 4064-4064/com.bestplus.chuangshangjiuzhi I/System.out: beforeTextChanged
//            08-09 17:57:10.451 4064-4064/com.bestplus.chuangshangjiuzhi I/System.out: onTextChanged 12
//            08-09 17:57:10.451 4064-4064/com.bestplus.chuangshangjiuzhi I/System.out: onTextChanged =
//            08-09 17:57:10.451 4064-4064/com.bestplus.chuangshangjiuzhi I/System.out: afterTextChanged
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("onTextChanged " + s.toString());
                if(temp != null && temp.toString().equals(s.toString())){
                    System.out.println("onTextChanged = ");
                }else{
                    System.out.println("onTextChanged != ");
                    editText4.setText("");
                    temp = s.toString();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                System.out.println("beforeTextChanged");
            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println("afterTextChanged");
            }
        });
    }

    private void initTitle() {

    }

    private void initData(){
        String code = this.textView_jiuzhenhao.getText().toString();
        if(code != null && !"".equals(code)) {
            ((MainActivity) getActivity()).getProcessRecordsBycodeAndtime(code,
                    textView_startdate.getText().toString(),
                    textView_enddate.getText().toString());
        }
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
            case R.id.imageView_search: //查询--病人，时间
                String code = this.textView_jiuzhenhao.getText().toString(); //获取就诊号
                if(code != null && !"".equals(code)) { // 有就诊号
                    String name = this.editText4.getText().toString(); // 获取病人姓名
                    if(name != null && !"".equals(name)){ // 已经有病人信息，则查询创伤记录
                        initData();
                    }else {
                        ((MainActivity) getActivity()).getPatientInfoByJiuzhenhao(code); // 无病人信息，则查询病人信息
                        if(items != null) {
                            items.clear();
                            listview.setAdapter(adapter);
                        }
                    }
                }
                break;
            case R.id.button_new: //新增记录
                Intent intent = new Intent(getActivity(), XinxiluruActivity.class);
                startActivity(intent);
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

    @Override
    public boolean onRefresh(MotionEvent ev) {
        System.out.println("Fragment_Main_Chuangshajilu onRefresh");

        initData();
        listview.refreshComplete();
        return false;
    }

    @Override
    public void onMoreLoadding() {
        System.out.println("Fragment_Main_Chuangshajilu onMoreLoadding");

        initData();
        listview.moreLoaddingComplete();
    }

    @Override
    public void foundNFCCODE(String nfcCode) {
        if(this.NFCCode != null && this.NFCCode.equals(nfcCode)){
            // 相同的NFC码表示直接查询
            System.out.println("searching records");
            this.editText4.setText(NFC_name);
            this.textView_jiuzhenhao.setText(NFC_jiuzhenhao);

            initData();
        }else {
            System.out.println("searching patient");
            this.NFCCode = nfcCode;
            ((MainActivity) getActivity()).getPatientInfoByNFCCode(nfcCode); // 无病人信息，则查询病人信息
            if(items != null) {
                items.clear();
                listview.setAdapter(adapter);
            }
        }
    }
}
