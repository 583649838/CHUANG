package com.bestplus.chuangshangjiuzhi;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bestplus.chuangshangjiuzhi.common.JsonKey;
import com.bestplus.chuangshangjiuzhi.common.Variable;
import com.bestplus.chuangshangjiuzhi.dialog.CommonDialog;
import com.bestplus.chuangshangjiuzhi.dialog.CustomProgressDialog;
import com.bestplus.chuangshangjiuzhi.util.DebugLog;
import com.bestplus.chuangshangjiuzhi.util.HttpClientRequest;
import com.bestplus.chuangshangjiuzhi.view.MyTitleView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ModifyPwdActivity extends Activity implements
		OnClickListener, OnTouchListener {

	private LinearLayout oldPwdLayout, newPwdLayout, surePwdLayout;
	private EditText oldPwd, newPwd, surePwd;
	private Button sureBtn;
	private TextView hintFalse;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modifypwd);
		initTitle();
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		hintFalse = (TextView) findViewById(R.id.hintFalse);
		oldPwdLayout = (LinearLayout) findViewById(R.id.oldPwdLayout);
		newPwdLayout = (LinearLayout) findViewById(R.id.newPwdLayout);
		surePwdLayout = (LinearLayout) findViewById(R.id.surePwdLayout);
		oldPwd = (EditText) findViewById(R.id.oldPwd);
		newPwd = (EditText) findViewById(R.id.newPwd);
		surePwd = (EditText) findViewById(R.id.surePwd);
		sureBtn = (Button) findViewById(R.id.sureBtn);
		oldPwd.setOnTouchListener(this);
		newPwd.setOnTouchListener(this);
		surePwd.setOnTouchListener(this);
		sureBtn.setOnClickListener(this);
	}

	private void initTitle() {
		// TODO Auto-generated method stub
		MyTitleView titleView = new MyTitleView(
				(RelativeLayout) findViewById(R.id.title_layout),
				getString(R.string.modify_pwd), true, false);
		titleView.getLeftImageView().setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.leftIcon:
			finish();
			break;
		case R.id.sureBtn:
			if (oldPwd.getText().toString().trim().equals("")) {
				hintFalse.setVisibility(View.VISIBLE);
				hintFalse.setText(R.string.hint_pwd_null);
				oldPwdLayout.setBackgroundResource(R.color.edit_false_hint);
				return;
			} else if (newPwd.getText().toString().trim().equals("")) {
				hintFalse.setVisibility(View.VISIBLE);
				hintFalse.setText(R.string.hint_newpwd_null);
				newPwdLayout.setBackgroundResource(R.color.edit_false_hint);
				return;
			} else if (newPwd.getText().toString().trim().length() < 6
					| newPwd.getText().toString().trim().length() > 12) {
				hintFalse.setVisibility(View.VISIBLE);
				hintFalse.setText(R.string.hint_newpwd_num);
				newPwdLayout.setBackgroundResource(R.color.edit_false_hint);
				return;
			}
			else if (!newPwd.getText().toString().trim()
					.equals(surePwd.getText().toString().trim())) {
				hintFalse.setVisibility(View.VISIBLE);
				hintFalse.setText(R.string.hint_surepwd_false);
				surePwdLayout.setBackgroundResource(R.color.edit_false_hint);
				return;
			}
 
			 modifyPwd();
			break;

		default:
			break;
		}
	}

	
	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.oldPwd:
			oldPwdLayout.setBackgroundResource(R.color.white);
			break;
		case R.id.newPwd:
			newPwdLayout.setBackgroundResource(R.color.white);
			break;
		case R.id.surePwd:
			surePwdLayout.setBackgroundResource(R.color.white);
			break;
		default:
			break;
		}
		return false;
	}

	private void modifyPwd() {
		RequestParams params = new RequestParams();
//		params.put(JsonKey.username, Variable.Account);
		params.put(JsonKey.username, "gg");
		String oldPpassword = oldPwd.getText().toString().trim();

		params.put(JsonKey.password, oldPpassword);
		
		
		String newPpassword =  newPwd.getText().toString().trim();
		
		params.put(JsonKey.newpassword,newPpassword);
		

		final CustomProgressDialog dialog = new CustomProgressDialog(this,
				getResources().getString(R.string.loading_data), R.anim.frame,
				R.style.progressDialog);
		HttpClientRequest.post(Variable.server+Variable.padChangPassword, params, 20000,
				new AsyncHttpResponseHandler() {

					@Override
					public void onStart() { // TODO Auto-generated method stub
						super.onStart();
						DebugLog.e("rx", "onStart");
						dialog.show();
					}

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						// TODO Auto-generated method stub
						DebugLog.e("rx", "onSuccess");
						Message msg = new Message();
						msg.what = 1;
						Bundle bundle = new Bundle();
						bundle.putString("data", new String(arg2));
						msg.setData(bundle);
						handler.sendMessage(msg);
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) { // TODO Auto-generated method stub
						DebugLog.e("rx", "onFailure");
					}

					@Override
					public void onFinish() { // TODO Auto-generated method stub
						super.onFinish();
						dialog.dismiss();
					}
				});
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 1:
				Bundle bundle = msg.getData();
				String data = bundle.getString("data");
				System.out.println(data);
				try {
					JSONObject obj = new JSONObject(data);
					int flag = obj.optInt(JsonKey.result);
					if (flag == -1) {
						CommonDialog dialog = new CommonDialog(
								ModifyPwdActivity.this, R.style.userDialog,
								"修改失败，原密码错误！", getResources().getString(
										R.string.confirm_label));
						dialog.show();
					} else if (flag == 0) {
						CommonDialog dialog = new CommonDialog(
								ModifyPwdActivity.this, R.style.userDialog,
								"修改失败，请稍后再试！", getResources().getString(
										R.string.confirm_label));
						dialog.show();
					} else {
						Toast.makeText(getApplicationContext(), "修改密码成功。",
								Toast.LENGTH_SHORT).show();
						finish();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;

			default:
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
