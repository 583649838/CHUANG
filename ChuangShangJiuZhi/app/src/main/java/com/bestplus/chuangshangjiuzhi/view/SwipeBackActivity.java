package com.bestplus.chuangshangjiuzhi.view;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.bestplus.chuangshangjiuzhi.R;
import com.bestplus.chuangshangjiuzhi.BaseActivity;
import com.bestplus.chuangshangjiuzhi.util.Utility;
import com.bestplus.chuangshangjiuzhi.view.SwipeBackLayout;

/**
 * 想要实现向右滑动删除Activity效果只需要继承SwipeBackActivity即可，如果当前页面含有ViewPager
 * 只需要调用SwipeBackLayout的setViewPager()方法即可
 * 
 * @author xiaanming
 * 
 */
public class SwipeBackActivity extends BaseActivity {
	protected SwipeBackLayout layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		layout = (SwipeBackLayout) LayoutInflater.from(this).inflate(R.layout.base_swipeback, null);
		layout.attachToActivity(this);
		//AppStatus.register(this);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//AppStatus.unregister(this);
	}
	
	protected void hideSoft() {
		// TODO Auto-generated method stub
		Utility.hideSoftInput(this);
	}

}
