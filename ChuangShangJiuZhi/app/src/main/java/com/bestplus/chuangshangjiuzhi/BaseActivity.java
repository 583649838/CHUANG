package com.bestplus.chuangshangjiuzhi;

import com.bestplus.chuangshangjiuzhi.application.AppStatus;

import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity{
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		AppStatus.register(this);
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		AppStatus.unregister(this);
	}
}
