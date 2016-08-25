package com.bestplus.chuangshangjiuzhi.dialog;

import com.bestplus.chuangshangjiuzhi.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class CustomProgressDialog extends ProgressDialog implements
		OnClickListener {

	private AnimationDrawable mAnimation;
	private Context mContext;
	private ImageView mImageView;
	private String mLoadingTip;
	private TextView mLoadingTv;
	private int count = 0;
	private String oldLoadingTip;
	private int mResid;
	private LinearLayout dismissLayout, showLayout;

//	public CustomProgressDialog(Context context, String content, int id) {
//		super(context);
//		this.mContext = context;
//		this.mLoadingTip = content;
//		this.mResid = id;
//		setCanceledOnTouchOutside(true);
//	}

	public CustomProgressDialog(Context context, String content, int id,
			int theme) {
		// TODO Auto-generated constructor stub
		super(context, theme);
		this.mContext = context;
		this.mLoadingTip = content;
		this.mResid = id;
		setCanceledOnTouchOutside(true);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		initData();
	}

	private void initData() {

		mImageView.setBackgroundResource(mResid);
		// 通过ImageView对象拿到背景显示的AnimationDrawable
		mAnimation = (AnimationDrawable) mImageView.getBackground();
		// 为了防止在onCreate方法中只显示第一帧的解决方案之一
		mImageView.post(new Runnable() {
			@Override
			public void run() {
				mAnimation.start();

			}
		});
		mLoadingTv.setText(mLoadingTip);
		dismissLayout = (LinearLayout) findViewById(R.id.dismissLayout);
		dismissLayout.setOnClickListener(this);
		showLayout = (LinearLayout) findViewById(R.id.showLayout);
		showLayout.setOnClickListener(this);
	}

	public void setContent(String str) {
		mLoadingTv.setText(str);
	}

	private void initView() {
		setContentView(R.layout.progress_dialog);
		mLoadingTv = (TextView) findViewById(R.id.loadingTv);
		mImageView = (ImageView) findViewById(R.id.loadingIv);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.dismissLayout:
			dismiss();
			break;
		case R.id.showLayout:
			break;
		default:
			break;
		}
	}

}
