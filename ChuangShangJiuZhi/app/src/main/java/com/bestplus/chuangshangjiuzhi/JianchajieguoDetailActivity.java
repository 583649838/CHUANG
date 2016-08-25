package com.bestplus.chuangshangjiuzhi;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.bestplus.chuangshangjiuzhi.adapter.JianchaResultShujuAdapter;
import com.bestplus.chuangshangjiuzhi.entity.JianchaResultDetail;
import com.bestplus.chuangshangjiuzhi.entity.JianchaResultShuju;
import com.bestplus.chuangshangjiuzhi.view.MyTitleView;
import com.bestplus.chuangshangjiuzhi.view.SwipeBackActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maoamo on 2016/7/17.
 */
public class JianchajieguoDetailActivity  extends SwipeBackActivity implements View.OnClickListener {
    private JianchaResultShujuAdapter adapter;
    private List<JianchaResultShuju> items = new ArrayList<JianchaResultShuju>();
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jianchajieguodetail);

        initTitle();
        initView();
        initViewHandler();
    }

    private void initView() {
        listview = (ListView) findViewById(R.id.list);

        adapter = new JianchaResultShujuAdapter(this, items);
        listview.setAdapter(adapter);
    }

    private void initViewHandler() {

    }

    private void initTitle() {
        // TODO Auto-generated method stub
        MyTitleView titleView = new MyTitleView(
                (RelativeLayout) findViewById(R.id.title_layout),
                getString(R.string.activity_jianchajiegoudetail),
                true, true);

        titleView.getLeftImageView().setOnClickListener(this);
        titleView.getRightTextView().setOnClickListener(this);
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.leftIcon:
                finish();
                break;
            case R.id.rightText:
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
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
}
