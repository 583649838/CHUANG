package com.bestplus.chuangshangjiuzhi;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.bestplus.chuangshangjiuzhi.view.MyTitleView;
import com.bestplus.chuangshangjiuzhi.view.SwipeBackActivity;

public class JibenxinxiActivity extends SwipeBackActivity implements OnClickListener{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jibenxinxi);
		
		initTitle();
		initView();
		initViewHandler();
	}
	
	private void initView() {
		
	}
	
	private void initViewHandler() {
		
	}
	
	private void initTitle() {
		// TODO Auto-generated method stub
		MyTitleView titleView = new MyTitleView(
				(RelativeLayout) findViewById(R.id.title_layout),
				getString(R.string.main_jibenxinxi),
				true, false);
		
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
