package com.bestplus.chuangshangjiuzhi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bestplus.chuangshangjiuzhi.view.MyTitleView_Two;

public class HuanzheMainActivity extends Activity implements OnClickListener{

	private ImageView imageView_jibengxinxi, 
		imageView_xinxiluru, 
		imageView_huanzhezhuisu, 
		imageView_jiegoutixing;
	private TextView textView_jibengxinxi, 
		textView_xinxiluru, 
		textView_huanzhezhuisu, 
		textView_jiegoutixing;

	private int whichFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Intent intent = getIntent();
		whichFragment = intent.getIntExtra("whichFragment", 0);

		System.out.println(whichFragment);

		initTitle();
		initView();
		initViewHandler();
	}
	
	private void initView() {
		imageView_jibengxinxi = (ImageView) findViewById(R.id.imageView_jibengxinxi);
		imageView_xinxiluru = (ImageView) findViewById(R.id.imageView_xinxiluru);
		imageView_huanzhezhuisu = (ImageView) findViewById(R.id.imageView_huanzhezhuisu);
		imageView_jiegoutixing = (ImageView) findViewById(R.id.imageView_jiegoutixing);
		
		textView_jibengxinxi = (TextView) findViewById(R.id.textView_jibengxinxi);
		textView_xinxiluru = (TextView) findViewById(R.id.textView_xinxiluru);
		textView_huanzhezhuisu = (TextView) findViewById(R.id.textView_huanzhezhuisu);
		textView_jiegoutixing = (TextView) findViewById(R.id.textView_jiegoutixing);
	}
	
	private void initViewHandler() {
		imageView_jibengxinxi.setOnClickListener(this);
		imageView_xinxiluru.setOnClickListener(this);
		imageView_huanzhezhuisu.setOnClickListener(this);
		imageView_jiegoutixing.setOnClickListener(this);
		
		textView_jibengxinxi.setOnClickListener(this);
		textView_xinxiluru.setOnClickListener(this);
		textView_huanzhezhuisu.setOnClickListener(this);
		textView_jiegoutixing.setOnClickListener(this);
	}
	
	private void initTitle() {
		// TODO Auto-generated method stub
		MyTitleView_Two titleView = new MyTitleView_Two(
				(RelativeLayout) findViewById(R.id.title_layout),
				R.string.main,
				R.drawable.login_pwd,
				R.string.main_title_shouhuan,
				R.drawable.login_user,
				R.string.main_title_yonghu);
		
		titleView.getLeftImageView().setOnClickListener(this);
		titleView.getLeftTextView().setOnClickListener(this);
		
		titleView.getRightImageView().setOnClickListener(this);
		titleView.getRightTextView().setOnClickListener(this);
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.leftIcon:
		case R.id.leftText: //进入手环绑定就诊号页面
			startActivity(new Intent(HuanzheMainActivity.this, BangdingshouhuanActivity.class));
			break;
			
		case R.id.rightIcon:
		case R.id.rightText: //标题栏右侧的文字图片暂时仅仅显示工号对应的医生或护士的姓名
			break;
			
		case R.id.imageView_jibengxinxi:
		case R.id.textView_jibengxinxi: 
			startActivity(new Intent(HuanzheMainActivity.this, HuanzheActivity.class));
			break;

		case R.id.imageView_xinxiluru: 
		case R.id.textView_xinxiluru: 
			startActivity(new Intent(HuanzheMainActivity.this, XinxiluruActivity.class));
			break;
			
		case R.id.imageView_huanzhezhuisu: 
		case R.id.textView_huanzhezhuisu: 
			startActivity(new Intent(HuanzheMainActivity.this, HuanzhezhuisuActivity.class));
			break;
			
		case R.id.imageView_jiegoutixing:
		case R.id.textView_jiegoutixing:
			startActivity(new Intent(HuanzheMainActivity.this, JieguotixiangActivity.class));
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
