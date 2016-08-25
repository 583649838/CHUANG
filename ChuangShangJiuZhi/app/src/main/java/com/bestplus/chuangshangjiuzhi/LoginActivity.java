package com.bestplus.chuangshangjiuzhi;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bestplus.chuangshangjiuzhi.common.JsonKey;
import com.bestplus.chuangshangjiuzhi.common.PrefKey;
import com.bestplus.chuangshangjiuzhi.common.Variable;
import com.bestplus.chuangshangjiuzhi.dialog.CommonDialog;
import com.bestplus.chuangshangjiuzhi.dialog.CustomProgressDialog;
import com.bestplus.chuangshangjiuzhi.entity.CS_User;
import com.bestplus.chuangshangjiuzhi.entity.User;
import com.bestplus.chuangshangjiuzhi.entity.YongyaoInfoGroup;
import com.bestplus.chuangshangjiuzhi.util.DebugLog;
import com.bestplus.chuangshangjiuzhi.util.HttpClientRequest;
import com.bestplus.chuangshangjiuzhi.util.PrefUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends Activity implements OnClickListener {
    private final static String TAG = "LoginActivity";
	Button forgetPwdButton,  loginButton, ipButton;
	EditText loginUser,loginPwd;
	private CheckBox rememberPwd;
	private LinearLayout rememberPwdLayout;
	TextView app_name;
    LinearLayout lay_ip;
    EditText ip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		loginUser = (EditText) findViewById(R.id.loginUser);
		loginPwd = (EditText) findViewById(R.id.loginPwd);
		forgetPwdButton = (Button) findViewById(R.id.forgetPwdButton);
		loginButton = (Button) findViewById(R.id.loginButton);
		app_name=(TextView)findViewById(R.id.app_name);
		ipButton=(Button)findViewById(R.id.ipButton);
		lay_ip=(LinearLayout)findViewById(R.id.lay_ip);
		ip=(EditText)findViewById(R.id.ip);
		forgetPwdButton.setOnClickListener(this);
		loginButton.setOnClickListener(this);
		rememberPwdLayout = (LinearLayout) findViewById(R.id.rememberPwdLayout);
		rememberPwd = (CheckBox) findViewById(R.id.rememberPwd);
		rememberPwdLayout.setOnClickListener(this);
		
		loginUser.setText(PrefUtil.getString(getApplicationContext(),
				"KEY_USER_ACCOUNT", ""));
		int remFlag = PrefUtil.getInt(LoginActivity.this,
				"KEY_REMEMBER_PWD", 
				0);
		if(1 == remFlag){
			loginPwd.setText(PrefUtil.getString(getApplicationContext(),
					"KEY_USER_PWD", ""));
			rememberPwd.setChecked(true);
		}else{
			loginPwd.setText("");
			rememberPwd.setChecked(false);
		}
		
		app_name.setOnLongClickListener((OnLongClickListener) new OnLongClickListener() {			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				lay_ip.setVisibility(View.VISIBLE);
				ipButton.setOnClickListener(LoginActivity.this);
				return false;
			}
		});
		
		if(!PrefUtil.getString(getApplicationContext(),
				PrefKey.KEY_IP, "").equals("")){
			Variable.server =PrefUtil.getString(getApplicationContext(),
					PrefKey.KEY_IP, "");
			ip.setText(Variable.server);
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.forgetPwdButton:
			if ( "".equals(loginUser.getText().toString().trim())) {
				CommonDialog dialog = new CommonDialog(LoginActivity.this,
						R.style.userDialog, getResources().getString(R.string.name_err), getResources()
								.getString(R.string.confirm_label));
				dialog.show();
				return;
			}
			Variable.Account=loginUser.getText().toString().trim();
			startActivity(new Intent(this, ModifyPwdActivity.class));
			break;
		case R.id.loginButton:
			if ("".equals(loginUser.getText().toString().trim()))  {
				CommonDialog dialog = new CommonDialog(LoginActivity.this,
						R.style.userDialog, getString(R.string.name_err), getResources()
								.getString(R.string.confirm_label));
				dialog.show();
				return;
			} else if ("".equals(loginPwd.getText().toString().trim())) {
				CommonDialog dialog = new CommonDialog(LoginActivity.this,
						R.style.userDialog, getString(R.string.pwd_err), getResources().getString(
								R.string.confirm_label));
				dialog.show();
				return;
			}
			login();
			break;
		case R.id.ipButton:
			String ip=this.ip.getText().toString();
			if(ip!=null&&!"".equals(ip))
			{
				Variable.server =ip;
				PrefUtil.putString(this, PrefKey.KEY_IP, Variable.server);
				lay_ip.setVisibility(View.INVISIBLE);
				lay_ip.invalidate();
			}
			else
			Toast.makeText(this,getString(R.string.ip_err), Toast.LENGTH_LONG).show();
			break;
		case R.id.rememberPwdLayout:
			if (rememberPwd.isChecked())
				rememberPwd.setChecked(false);
			else
				rememberPwd.setChecked(true);

			break;
		default:
			break;
		}
	}

	private void login() {
		//记住密码之保存数据
		PrefUtil.putString(LoginActivity.this,
				"KEY_USER_ACCOUNT",
				loginUser.getText().toString().trim());

		if (rememberPwd.isChecked()){
			PrefUtil.putString(LoginActivity.this,
					"KEY_USER_PWD", 
					loginPwd.getText().toString().trim());
			PrefUtil.putInt(LoginActivity.this,
					"KEY_REMEMBER_PWD", 
					1);
		}else{
			PrefUtil.putString(LoginActivity.this,
					"KEY_USER_PWD", 
					"");
			PrefUtil.putInt(LoginActivity.this,
					"KEY_REMEMBER_PWD", 
					0);
		}

		RequestParams params = new RequestParams();

	    params.put(JsonKey.mark, Variable.login.trim());//mark=login
		params.put(JsonKey.username, loginUser.getText().toString().trim());//memberCode=xxxxxx
		
		String password = loginPwd.getText().toString().trim();
		params.put(JsonKey.password, password);//password=xxxxxxxx

        System.out.println("login() params:" + params.toString());

		final CustomProgressDialog dialog = new CustomProgressDialog(this,
				getResources().getString(R.string.loading_login),
                R.anim.frame,
				R.style.progressDialog);

        //"http://192.168.10.160:8080/treat" + "/app/treat.html" + "?mark=login&"
		HttpClientRequest.post(Variable.server + Variable.padIndex , params, 1000,//20000,
				new AsyncHttpResponseHandler() {

					@Override
					public void onStart() { // TODO Auto-generated method stub
						super.onStart();
						DebugLog.e("login()", "onStart");
						dialog.show();
					}

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						// TODO Auto-generated method stub
						DebugLog.e("login()", "onSuccess length:" + arg2.length);
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
						DebugLog.e("login()", "onFailure");

					}

					@Override
					public void onFinish() { // TODO Auto-generated method stub
						super.onFinish();
						dialog.dismiss();
					}
				});

	}
	private void jumpLogin(){
		startActivity(new Intent(LoginActivity.this,
				MainActivity.class));
		finish();
	}

	private List<YongyaoInfoGroup> itemsOfYongyaoInfoGroup = new ArrayList<YongyaoInfoGroup>();
	public List<YongyaoInfoGroup> getItemsOfYongyaoInfoGroup() {
		return itemsOfYongyaoInfoGroup;
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Bundle bundle = msg.getData();
			switch (msg.what) {
			case 1:
				String data = bundle.getString("data");
				System.out.print("login() return data:" + data);
                DebugLog.e("login()result:", data);

//{"member":{"expertise":"跌打损伤","gmtCreated":1469669954000,"gmtModified":1469669952000,"id":9,"isDel":1,"isUse":1,"memberCode":"1000000121","memberName":"王雪梅","memberPhone":"13758836998","password":"e10adc3949ba59abbe56e057f20f883e","roleId":1},
// "result":1}

                if (!data.equals("")) {

					try {
						JSONObject obj = new JSONObject(data);
						int flag = obj.optInt(JsonKey.result);
						// flag == 1,说明登录成功
						// flag == 0,说明密码错误
						// flag == -1,说明账号不存在
						if (flag == 1) {
							Toast.makeText(getApplicationContext(), "登录成功。", Toast.LENGTH_SHORT).show();

//							String Suser=obj.optString(JsonKey.user,"");
//							if(Suser!=null&&!"".equals(Suser)){
//								JSONObject user = new JSONObject(Suser);
//								Variable.user=new User(user);
//							}

                            JSONObject member = obj.getJSONObject("member");
                            String userName = member.optString("memberName", "");
                            Log.i(TAG, "login() userName:" + userName);

                            Variable.cs_user = new CS_User(member);
                            Log.i(TAG, "Variable.cs_user:" + Variable.cs_user.getExpertise());

							startActivity(new Intent(LoginActivity.this, MainActivity.class));

							finish();
						} else if (flag == 0) {
							CommonDialog dialog = new CommonDialog(
									LoginActivity.this, R.style.userDialog,
									"密码错误，请重新输入！", getResources().getString(
											R.string.confirm_label));
							dialog.show();
						} else if (flag == -1) {
							CommonDialog dialog = new CommonDialog(
									LoginActivity.this, R.style.userDialog,
									"账号不存在，请重新输入！", getResources().getString(
											R.string.confirm_label));
							dialog.show();
						} else {
							CommonDialog dialog = new CommonDialog(
									LoginActivity.this, R.style.userDialog,
									"登录失败，请稍后再试！", getResources().getString(
											R.string.confirm_label));
							dialog.show();
						}

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

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
