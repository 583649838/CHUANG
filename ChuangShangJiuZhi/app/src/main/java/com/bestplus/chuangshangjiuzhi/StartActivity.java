package com.bestplus.chuangshangjiuzhi;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


import com.bestplus.chuangshangjiuzhi.application.AppStatus;
import com.bestplus.chuangshangjiuzhi.common.PrefKey;
import com.bestplus.chuangshangjiuzhi.util.PrefUtil;
import com.bestplus.chuangshangjiuzhi.view.SwipeBackActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


public class StartActivity extends SwipeBackActivity {
	Handler mhandler;
	Runnable mRunnable;
	private boolean isfirst = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);

		isfirst = PrefUtil.getBoolean(StartActivity.this, 
				PrefKey.KEY_ISFIRST,
				true);
		AppStatus.getInstance().setFirst(isfirst);
		AppStatus.getInstance().setIsNull("not null");
	}

	private void finishSplashView() {
		// 延迟两秒后执行run方法中的页面跳转
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if (isfirst) {
					Intent intent = new Intent(StartActivity.this,LoginActivity.class);
					startActivity(intent);
				} else {
					Intent intent = new Intent(StartActivity.this,LoginActivity.class);
					startActivity(intent);
				}
				finish();
			}
		}, 1200);
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			Bundle bundle = msg.getData();
			String data = bundle.getString("data");
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				System.out.println(data);
				break;
			}
		}
	};



	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		//稍候离开启动页，进入引导页或登录页面
		finishSplashView();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
