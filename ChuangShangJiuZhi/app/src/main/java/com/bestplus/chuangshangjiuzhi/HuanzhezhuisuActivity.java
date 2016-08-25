package com.bestplus.chuangshangjiuzhi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bestplus.chuangshangjiuzhi.adapter.HuanzheJiuzhiInfoAdapter;
import com.bestplus.chuangshangjiuzhi.common.Common;
import com.bestplus.chuangshangjiuzhi.entity.HuanzheJiuzhiInfo;
import com.bestplus.chuangshangjiuzhi.view.MyTitleView;
import com.bestplus.chuangshangjiuzhi.view.NoScrollListview;
import com.bestplus.chuangshangjiuzhi.view.SwipeBackActivity;

public class HuanzhezhuisuActivity extends SwipeBackActivity implements OnClickListener{
	private HuanzheJiuzhiInfoAdapter adapter;
	private List<HuanzheJiuzhiInfo> items = new ArrayList<HuanzheJiuzhiInfo>();
	private ListView listview;
	
	private ImageView imageView_qureyHuanzhe;
	private TextView startDate, endDate;
	private Button button_refresh;
	private EditText editText_name;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_huanzhezhuisu);
		
		initTitle();
		initView();
		initViewHandler();
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
	
	private void initView() {
		editText_name = (EditText) findViewById(R.id.editText_name);
		imageView_qureyHuanzhe = (ImageView) findViewById(R.id.imageView_qureyHuanzhe);
		startDate = (TextView) findViewById(R.id.edt_startDate);
		endDate = (TextView) findViewById(R.id.edt_endDate);
		button_refresh = (Button) findViewById(R.id.button_refresh);
		listview = (ListView) findViewById(R.id.list);
		
		//for test data
		addTestData();
		
		adapter = new HuanzheJiuzhiInfoAdapter(this, items, handler);
		listview.setAdapter(adapter);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		String date=formatter.format(now);
		startDate.setText(date);
		endDate.setText(date);
	}
	
	private void initViewHandler() {
		startDate.setOnClickListener(this);
		endDate.setOnClickListener(this);
		imageView_qureyHuanzhe.setOnClickListener(this);
		button_refresh.setOnClickListener(this);
	}
	
	private void initTitle() {
		// TODO Auto-generated method stub
		MyTitleView titleView = new MyTitleView(
				(RelativeLayout) findViewById(R.id.title_layout),
				getString(R.string.main_huanzhezhuisu),
				true, false);
		
		titleView.getLeftImageView().setOnClickListener(this);
		titleView.getRightTextView().setOnClickListener(this);
	}

	private void initData(){

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
		case R.id.edt_startDate:
			Common.showDatePopupWindow(HuanzhezhuisuActivity.this, startDate);
			break;
		case R.id.edt_endDate:
			Common.showDatePopupWindow(HuanzhezhuisuActivity.this, endDate);
			break;
		case R.id.imageView_qureyHuanzhe: //根据条码或就诊号得到患者信息
			break;
		case R.id.button_refresh: //按起止日期查询患者的救治信息
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

