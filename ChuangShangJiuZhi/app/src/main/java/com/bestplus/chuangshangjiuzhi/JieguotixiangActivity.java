package com.bestplus.chuangshangjiuzhi;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.bestplus.chuangshangjiuzhi.adapter.HuanzheJiuzhiInfoAdapter;
import com.bestplus.chuangshangjiuzhi.adapter.JianchaInfoAdapter;
import com.bestplus.chuangshangjiuzhi.entity.JianchaInfo;
import com.bestplus.chuangshangjiuzhi.view.MyTitleView;
import com.bestplus.chuangshangjiuzhi.view.SwipeBackActivity;

import java.util.ArrayList;
import java.util.List;

public class JieguotixiangActivity extends SwipeBackActivity implements OnClickListener{
	private EditText editText_name;
	private ImageView imageView_qureyHuanzhe;

	private JianchaInfoAdapter adapter;
	private List<JianchaInfo> items = new ArrayList<JianchaInfo>();
	private ListView listview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jieguotixiang);
		
		initTitle();
		initView();
		initViewHandler();
	}
	
	private void initView() {
		editText_name = (EditText) findViewById(R.id.editText_name);
		imageView_qureyHuanzhe = (ImageView) findViewById(R.id.imageView_qureyHuanzhe);
		listview = (ListView) findViewById(R.id.list);

		adapter = new JianchaInfoAdapter(this, items, handler);
		listview.setAdapter(adapter);
	}
	
	private void initViewHandler() {
		
	}
	
	private void initTitle() {
		// TODO Auto-generated method stub
		MyTitleView titleView = new MyTitleView(
				(RelativeLayout) findViewById(R.id.title_layout),
				getString(R.string.main_jiguotixiang),
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