package com.bestplus.chuangshangjiuzhi;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bestplus.chuangshangjiuzhi.dialog.CommonDialog;
import com.bestplus.chuangshangjiuzhi.entity.CS_User;
import com.mitac.cell.device.bcr.McBcrMessage;
import com.mitac.cell.device.bcr.McBcrConnection;
import com.mitac.cell.device.bcr.McBcrListener;
import com.mitac.cell.device.bcr.McBcrMessage;
import com.mitac.cell.device.bcr.MiBcrListener;
import com.mitac.cell.device.bcr.utility.BARCODE.TYPE;

import com.bestplus.chuangshangjiuzhi.common.JsonKey;
import com.bestplus.chuangshangjiuzhi.common.Variable;
import com.bestplus.chuangshangjiuzhi.dialog.CustomProgressDialog;
import com.bestplus.chuangshangjiuzhi.entity.BingrenInfo;
import com.bestplus.chuangshangjiuzhi.util.DebugLog;
import com.bestplus.chuangshangjiuzhi.util.HttpClientRequest;
import com.bestplus.chuangshangjiuzhi.view.MyTitleView;
import com.bestplus.chuangshangjiuzhi.view.SwipeBackActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BangdingshouhuanActivity extends SwipeBackActivity implements OnClickListener, MiBcrListener{
    private final static int MSG_GET_PATIENT_INFO_BY_JIUZHENHAO_RESULT = 1;
    private final static int MSG_BIND_PATIENT_AND_NFCCODE_RESULT = 2;
    private final static int MSG_GET_PATIENT_INFO_BY_NFCCODE_RESULT = 3;
    private final static int MODIFY_FOCUS_RESULT = 4;

    private final static int MSG_DISBIND_BY_NFCCODE_RESULT = 6;

    private EditText textView_jiuzhenhao;
    private ImageView imageView_search;

    private TextView textView_xingming,
            textView_juzhenhao,
            textView_xingbie,
            textView_nianling,
            textView_shengao,
            textView_tizhong,
            textView_shenfenzhenghaoma;
    private Button button_bangdingshouhua, button_disbind;

    private BingrenInfo bingrenInfo;
    private String NFCCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bangdingshouhuan);

        initTitle();
        initView();
        initViewHandler();

        initNFC();
    }

    private void initView() {
        textView_jiuzhenhao = (EditText) findViewById(R.id.textView_jiuzhenhao);
        imageView_search = (ImageView) findViewById(R.id.imageView_search);

        textView_xingming = (TextView) findViewById(R.id.textView_xingming);
        textView_juzhenhao = (TextView) findViewById(R.id.textView_juzhenhao);
        textView_xingbie = (TextView) findViewById(R.id.textView_xingbie);
        textView_nianling = (TextView) findViewById(R.id.textView_nianling);
        textView_shengao = (TextView) findViewById(R.id.textView_shengao);
        textView_tizhong = (TextView) findViewById(R.id.textView_tizhong);
        textView_shenfenzhenghaoma = (TextView) findViewById(R.id.textView_shenfenzhenghaoma);

        button_disbind = (Button) findViewById(R.id.button_disbind);
        button_bangdingshouhua = (Button) findViewById(R.id.button_bangdingshouhua);
        enableBindOperation(false);

//        //test data
//        bingrenInfo = new BingrenInfo();
//        textView_xingming.setText(bingrenInfo.getItemHuanzheName());
//        textView_juzhenhao.setText(bingrenInfo.getItemHuanzheId());
//        textView_xingbie.setText(bingrenInfo.getItemXingbie());
//        textView_nianling.setText(bingrenInfo.getItemNianling());
//        textView_shengao.setText(bingrenInfo.getItemShengao());
//        textView_tizhong.setText(bingrenInfo.getItemTizhong());
//        textView_shenfenzhenghaoma.setText(bingrenInfo.getItemHuanZheDianhua());
    }

    private void updateView(){
        if(bingrenInfo != null ) {
            //根据获取的患者信息更新显示
            textView_xingming.setText(bingrenInfo.getItemHuanzheName());
            textView_juzhenhao.setText(bingrenInfo.getItemHuanzheId());
            textView_xingbie.setText(bingrenInfo.getItemXingbie());
            textView_nianling.setText(bingrenInfo.getItemNianling());
            textView_shengao.setText(bingrenInfo.getItemShengao());
            textView_tizhong.setText(bingrenInfo.getItemTizhong());
            textView_shenfenzhenghaoma.setText(bingrenInfo.getItemShenfengzhenghaoma());
        }
    }

    private void enableBindOperation(boolean req){
        this.button_bangdingshouhua.setEnabled(req);
    }

    private void initViewHandler() {
        // 输入不同的就诊号就清除掉之前的姓名
        textView_jiuzhenhao.addTextChangedListener(new TextWatcher() {
            //            08-09 17:56:59.490 4064-4064/com.bestplus.chuangshangjiuzhi I/System.out: beforeTextChanged
//            08-09 17:56:59.490 4064-4064/com.bestplus.chuangshangjiuzhi I/System.out: onTextChanged 1
//            08-09 17:56:59.490 4064-4064/com.bestplus.chuangshangjiuzhi I/System.out: onTextChanged !=
//            08-09 17:56:59.497 4064-4064/com.bestplus.chuangshangjiuzhi I/System.out: afterTextChanged
//            08-09 17:57:10.451 4064-4064/com.bestplus.chuangshangjiuzhi I/System.out: beforeTextChanged
//            08-09 17:57:10.451 4064-4064/com.bestplus.chuangshangjiuzhi I/System.out: onTextChanged 12
//            08-09 17:57:10.451 4064-4064/com.bestplus.chuangshangjiuzhi I/System.out: onTextChanged =
//            08-09 17:57:10.451 4064-4064/com.bestplus.chuangshangjiuzhi I/System.out: afterTextChanged
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("onTextChanged " + s.toString() + "clearLock: " + clearLock);

                if(!clearLock ) {
                    System.out.println("onTextChanged" + clearLock);
                    if (bingrenInfo != null) {
                        bingrenInfo.clear();

                        button_disbind.setVisibility(View.GONE);
                        button_disbind.setEnabled(false);

                        enableBindOperation(false);
                    }
                    updateView();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                System.out.println("beforeTextChanged");
            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println("afterTextChanged");
            }
        });
        imageView_search.setOnClickListener(this);
        button_bangdingshouhua.setOnClickListener(this);
        button_disbind.setOnClickListener(this);
    }

    private void initTitle() {
        // TODO Auto-generated method stub
        MyTitleView titleView = new MyTitleView(
                (RelativeLayout) findViewById(R.id.title_layout),
                getString(R.string.activity_title_bangdingshouhuan),
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
            case R.id.imageView_search:
                enableBindOperation(false);
                getPatientInfoByJiuzhenhao();
                break;
            case R.id.button_bangdingshouhua:
                bindPatientAndNfcCode();
                break;
            case R.id.button_disbind:
                disbindPatientAndNfcCode();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        if (!this.mpandenable)
            return;
        this.nfcAdapter.enableForegroundDispatch(this, this.pendingIntent,
                this.intentFiltersArray, (String[][]) null);

        mBcr.startListening();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();

        mBcr.stopListening();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        mBcr.close();
    }

    /**
     * 根据就诊号获取病人信息
     * 测试例子：
     *      http://192.168.10.160:8080/treat/app/treat.html?mark=getPatientInfoByCode&patientCode=234567
     *
     *      {"result":1,"居住地":"浙江省杭州市",
     *      "patient":{"acceptsStatus":0,"fixedTelephone":"18423456543","gmtCreated":1468032280000,
     *      "gmtModified":1470019102000,"id":1,"isDel":1,"linkPerson":"王竹","linkPhone":"0555-6132421",
     *      "patientAge":"23","patientCardId":"340523456724562718","patientCode":"234567","patientHight":"187",
     *      "patientName":"小刚2","patientSex":"男","receptionCode":"243649","receptionPerson":"小智",
     *      "seeDoctorDate":1467427464000,"seeDoctorRecords":"不咋地啊","transferStatus":1},
     *      "生日":"2016-08-01","入院时间":"2016-07-02"}
     *
     *
     */
    private void getPatientInfoByJiuzhenhao() {
        if("".equals(textView_jiuzhenhao.getText().toString())){
            DebugLog.e("getPatientInfoByJiuzhenhao", "jiuzhenhao is empty!");
            return;
        }

        RequestParams params = new RequestParams();
        params.put(JsonKey.mark, "getPatientInfoByCode");
        params.put(JsonKey.ItemHuanzheId, textView_jiuzhenhao.getText().toString());

        button_disbind.setVisibility(View.GONE);
        button_disbind.setEnabled(false);

        final CustomProgressDialog dialog = new CustomProgressDialog(this,
                getResources().getString(R.string.loading_data), R.anim.frame,
                R.style.progressDialog);

        HttpClientRequest.post(Variable.server + Variable.padIndex,
                params, 20000, new AsyncHttpResponseHandler() {

                    @Override
                    public void onStart() {
                        // TODO Auto-generated method stub
                        super.onStart();
                        DebugLog.e("rx", "onStart");
                        dialog.show();
                    }

                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        // TODO Auto-generated method stub
                        DebugLog.e("rx", "onSuccess");
                        Message msg = new Message();
                        msg.what = MSG_GET_PATIENT_INFO_BY_JIUZHENHAO_RESULT;
                        Bundle bundle = new Bundle();
                        bundle.putString("data", new String(arg2));
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                          Throwable arg3) {
                        // TODO Auto-generated method stub
                        DebugLog.e("rx", "onFailure");
                    }

                    @Override
                    public void onFinish() {
                        // TODO Auto-generated method stub
                        super.onFinish();
                        dialog.dismiss();
                    }
                });
    }

    /**
     * 根据手环获取病人信息
     * 测试例子：
     *      http://192.168.10.160:8080/treat/app/treat.html?mark=getPatientInfoByHandCode&handCode=048d89022c2c80
     *
     *      {"result":1,
     *      "patient":{"acceptsStatus":0,"fixedTelephone":"18423456543","gmtCreated":1468032280000,"gmtModified":1470019102000,"id":1,
     *      "isDel":1,"linkPerson":"王竹","linkPhone":"0555-6132421","patientAge":"23","patientCardId":"340523456724562718",
     *      "patientCode":"234567","patientHight":"187","patientName":"小刚2","patientSex":"男","receptionCode":"243649",
     *      "receptionPerson":"小智","seeDoctorDate":1467427464000,"seeDoctorRecords":"不咋地啊","transferStatus":1},
     *      "入院时间":"2016-07-02"}
     *
     */
    private void getPatientInfoByNFCCode() {
        if(this.NFCCode == null || "".equals(this.NFCCode)){
            DebugLog.e("getPatientInfoByNFCCode", "NFCCode is empty!");
            return;
        }

        RequestParams params = new RequestParams();
        params.put(JsonKey.mark, "getPatientInfoByHandCode");
        params.put(JsonKey.ItemNFCId, this.NFCCode);

        final CustomProgressDialog dialog = new CustomProgressDialog(this,
                getResources().getString(R.string.loading_data), R.anim.frame,
                R.style.progressDialog);

        HttpClientRequest.post(Variable.server + Variable.padIndex,
                params, 20000, new AsyncHttpResponseHandler() {

                    @Override
                    public void onStart() {
                        // TODO Auto-generated method stub
                        super.onStart();
                        DebugLog.e("rx", "onStart");
                        dialog.show();
                    }

                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        // TODO Auto-generated method stub
                        DebugLog.e("rx", "onSuccess");
                        Message msg = new Message();
                        msg.what = MSG_GET_PATIENT_INFO_BY_NFCCODE_RESULT;
                        Bundle bundle = new Bundle();
                        bundle.putString("data", new String(arg2));
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                          Throwable arg3) {
                        // TODO Auto-generated method stub
                        DebugLog.e("rx", "onFailure");
                    }

                    @Override
                    public void onFinish() {
                        // TODO Auto-generated method stub
                        super.onFinish();
                        dialog.dismiss();
                    }
                });
    }

    /**
     * 绑定NFC码与病人
     * 测试例子：
     *      http://192.168.10.160:8080/treat/app/treat.html?mark=updateHandCode&patientCode=234567&handCode=048d89022c2c80
     *
     *
     *
     */
    private void bindPatientAndNfcCode() {
        if("".equals(textView_jiuzhenhao.getText().toString())){
            DebugLog.e("bindPatientAndNfcCode", "jiuzhenhao is empty!");
            return;
        }

        RequestParams params = new RequestParams();
        params.put(JsonKey.mark, "updateHandCode");
        params.put(JsonKey.ItemHuanzheId, textView_jiuzhenhao.getText().toString());
        params.put(JsonKey.ItemNFCId, this.NFCCode);

        final CustomProgressDialog dialog = new CustomProgressDialog(this,
                getResources().getString(R.string.loading_data), R.anim.frame,
                R.style.progressDialog);

        HttpClientRequest.post(Variable.server + Variable.padIndex,
                params, 20000, new AsyncHttpResponseHandler() {

                    @Override
                    public void onStart() {
                        // TODO Auto-generated method stub
                        super.onStart();
                        DebugLog.e("rx", "onStart");
                        dialog.show();
                    }

                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        // TODO Auto-generated method stub
                        DebugLog.e("rx", "onSuccess");
                        Message msg = new Message();
                        msg.what = MSG_BIND_PATIENT_AND_NFCCODE_RESULT;
                        Bundle bundle = new Bundle();
                        bundle.putString("data", new String(arg2));
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                          Throwable arg3) {
                        // TODO Auto-generated method stub
                        DebugLog.e("rx", "onFailure");
                    }

                    @Override
                    public void onFinish() {
                        // TODO Auto-generated method stub
                        super.onFinish();
                        dialog.dismiss();
                    }
                });
    }

    /**
     * 解除绑定NFC码与病人
     * 测试例子：
     *      http://192.168.10.160:8080/treat/app/treat.html?mark=jieChuHandCode&patientCode=001
     *
     *
     *
     */
    private void disbindPatientAndNfcCode() {
        if(bingrenInfo == null){
            System.out.println("disbindPatientAndNfcCode bingrenInfo == null");
            return;
        }
        RequestParams params = new RequestParams();
        params.put(JsonKey.mark, "jieChuHandCode");
        params.put(JsonKey.ItemHuanzheId, bingrenInfo.getItemHuanzheId());

        System.out.println(params.toString());

        final CustomProgressDialog dialog = new CustomProgressDialog(this,
                getResources().getString(R.string.loading_data), R.anim.frame,
                R.style.progressDialog);

        HttpClientRequest.post(Variable.server + Variable.padIndex,
                params, 20000, new AsyncHttpResponseHandler() {

                    @Override
                    public void onStart() {
                        // TODO Auto-generated method stub
                        super.onStart();
                        DebugLog.e("rx", "onStart");
                        dialog.show();
                    }

                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        // TODO Auto-generated method stub
                        DebugLog.e("rx", "onSuccess");
                        Message msg = new Message();
                        msg.what = MSG_DISBIND_BY_NFCCODE_RESULT;
                        Bundle bundle = new Bundle();
                        bundle.putString("data", new String(arg2));
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                          Throwable arg3) {
                        // TODO Auto-generated method stub
                        DebugLog.e("rx", "onFailure");
                    }

                    @Override
                    public void onFinish() {
                        // TODO Auto-generated method stub
                        super.onFinish();
                        dialog.dismiss();
                    }
                });
    }

    /**
     * 通过医生ID、病人ID来改变关注关系
     * 测试例子：
     *      http://192.168.10.142:8080/treat/app/treat.html?mark=modifiedFocus&patientCode=789&doctorCode=006&isFocus=1
     *
     *
     */
    public void modifyPatientFocusStatus(String docId, String patientId, boolean status) {
        RequestParams params = new RequestParams();
        params.put(JsonKey.mark, "modifiedFocus");
        params.put("patientCode", patientId);
        params.put("doctorCode", docId);
        if(status)
            params.put("isFocus", "1");
        else
            params.put("isFocus", "0");

        System.out.print(params.toString() + "\n");

        final CustomProgressDialog dialog = new CustomProgressDialog(this,
                getResources().getString(R.string.loading_data),
                R.anim.frame,
                R.style.progressDialog);

        HttpClientRequest.post(Variable.server + Variable.padIndex,
                params, 20000, new AsyncHttpResponseHandler() {

                    @Override
                    public void onStart() {
                        // TODO Auto-generated method stub
                        super.onStart();
                        DebugLog.e("rx", "onStart");
                        dialog.show();
                    }

                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        // TODO Auto-generated method stub
                        DebugLog.e("rx", "onSuccess");
                        Message msg = new Message();
                        msg.what = MODIFY_FOCUS_RESULT;
                        Bundle bundle = new Bundle();
                        bundle.putString("data", new String(arg2));
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                          Throwable arg3) {
                        // TODO Auto-generated method stub
                        DebugLog.e("rx", "onFailure");
                    }

                    @Override
                    public void onFinish() {
                        // TODO Auto-generated method stub
                        super.onFinish();
                        dialog.dismiss();
                    }
                });
    }

    private boolean clearLock = false;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case MSG_GET_PATIENT_INFO_BY_JIUZHENHAO_RESULT: // 通过病人就诊号获取病人信息
                    Bundle bundle = msg.getData();
                    String data = bundle.getString("data");
                    System.out.println(data);
                    if (data != null & !data.equals("")) {
                        try {
                            JSONObject object = new JSONObject(data);
                            int flag = object.optInt(JsonKey.result);
                            if (flag == 1) {
                                Toast.makeText(getApplicationContext(), "获取病人信息成功！", Toast.LENGTH_SHORT).show();

                                JSONObject patient = object.getJSONObject("patient");
                                bingrenInfo = new BingrenInfo(patient);

                                updateView();
                                enableBindOperation(false);
                            }else {
                                CommonDialog dialog = new CommonDialog(
                                        BangdingshouhuanActivity.this,
                                        R.style.userDialog,
                                        "没有该病人！",
                                        getResources().getString(R.string.confirm_label));
                                dialog.show();

                                enableBindOperation(false);
                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                    break;

                case MSG_GET_PATIENT_INFO_BY_NFCCODE_RESULT: // 通过NFC码获取病人信息
                    bundle = msg.getData();
                    data = bundle.getString("data");
                    System.out.println(data);
                    if (data != null & !data.equals("")) {
                        try {
                            JSONObject object = new JSONObject(data);
                            int flag = object.optInt(JsonKey.result);
                            if (flag == 1) {
                                JSONObject patient = object.getJSONObject("patient");
                                bingrenInfo = new BingrenInfo(patient);

                                clearLock = true;
                                System.out.println("getItemHuanzheId" + bingrenInfo.getItemHuanzheId());
                                textView_jiuzhenhao.setText(bingrenInfo.getItemHuanzheId());
                                updateView();
                                clearLock = false;

                                CommonDialog dialog = new CommonDialog(
                                        BangdingshouhuanActivity.this,
                                        R.style.userDialog,
                                        "该手环已经绑定病人！",
                                        getResources().getString(R.string.confirm_label));
                                dialog.show();

                                button_disbind.setVisibility(View.VISIBLE);
                                button_disbind.setEnabled(true);
                            }else {
                                Toast.makeText(getApplicationContext(), "该手环尚未绑定病人！", Toast.LENGTH_SHORT).show();

                                button_disbind.setVisibility(View.GONE);
                                button_disbind.setEnabled(false);
                                enableBindOperation(true);
                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                    break;

                case MSG_BIND_PATIENT_AND_NFCCODE_RESULT:
                    bundle = msg.getData();
                    data = bundle.getString("data");
                    System.out.println(data);
                    if (data != null & !data.equals("")) {
                        try {
                            JSONObject object = new JSONObject(data);
                            int flag = object.optInt(JsonKey.result);
                            if (flag == 1) {
                                Toast.makeText(getApplicationContext(), "绑定成功！", Toast.LENGTH_SHORT).show();

                                // 绑定成功后，去关注该病人
                                modifyPatientFocusStatus(Variable.cs_user.getMemberCode(), bingrenInfo.getItemHuanzheId(), true);
                            }else {
                                CommonDialog dialog = new CommonDialog(
                                        BangdingshouhuanActivity.this,
                                        R.style.userDialog,
                                        "绑定失败！",
                                        getResources().getString(R.string.confirm_label));
                                dialog.show();
                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                    break;

                case MODIFY_FOCUS_RESULT:
                    bundle = msg.getData();
                    data = bundle.getString("data");
                    System.out.println(data);
                    if (data != null & !data.equals("")) {
                        try {
                            JSONObject object = new JSONObject(data);
                            int flag = object.optInt(JsonKey.result);
                            if (flag == 1) {
                                //{"result":1,"isFocus":1,"patientCode":"789","doctorCode":"006"}
                                Toast.makeText(getApplicationContext(), "关注成功！", Toast.LENGTH_SHORT).show();

                                finish();
                            } else {
                                CommonDialog dialog = new CommonDialog(
                                        BangdingshouhuanActivity.this,
                                        R.style.userDialog,
                                        "绑定成功但是关注该病人失败，请去《患者中心》关注！",
                                        getResources().getString(R.string.confirm_label));
                                dialog.show();
                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                    break;

                case MSG_DISBIND_BY_NFCCODE_RESULT:
                    bundle = msg.getData();
                    data = bundle.getString("data");
                    System.out.println(data);
                    if (data != null & !data.equals("")) {
                        try {
                            JSONObject object = new JSONObject(data);
                            int flag = object.optInt(JsonKey.result);
                            if (flag == 1) {
                                //{"result":1,"isFocus":1,"patientCode":"789","doctorCode":"006"}
                                Toast.makeText(getApplicationContext(), "解除绑定成功！", Toast.LENGTH_SHORT).show();

                                if (bingrenInfo != null) {
                                    bingrenInfo.clear();

                                    button_disbind.setVisibility(View.GONE);
                                    button_disbind.setEnabled(false);
                                }
                                updateView();

                                enableBindOperation(false);
                            } else {
                                CommonDialog dialog = new CommonDialog(
                                        BangdingshouhuanActivity.this,
                                        R.style.userDialog,
                                        "解除绑定失败！",
                                        getResources().getString(R.string.confirm_label));
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

    private McBcrConnection mBcr; // McBcrConnection help BCR control
    McBcrListener mBcrListener;
    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private IntentFilter[] intentFiltersArray = null;
    private IntentFilter[] mFilters;
    private PendingIntent mPendingIntent;
    private boolean isRecords = true;

    private String[][] mTechLists;
    boolean mpandenable = false;

    private void initNFC() {
        mBcr = new McBcrConnection(this);
        mBcr.setListener(this);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        invalidateNFC();

        IntentFilter[] arrayOfIntentFilter = new IntentFilter[1];
        arrayOfIntentFilter[0] = new IntentFilter(
                "android.nfc.action.TAG_DISCOVERED");
        this.intentFiltersArray = arrayOfIntentFilter;
        this.pendingIntent = PendingIntent.getActivity(this, 0, new Intent(
                this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        if (this.pendingIntent != null)
            this.mpandenable = true;
        this.mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(
                this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter localIntentFilter = new IntentFilter(
                "android.nfc.action.TECH_DISCOVERED");
        localIntentFilter.addAction("android.nfc.action.TAG_DISCOVERED");
        localIntentFilter.addAction("android.nfc.action.NDEF_DISCOVERED");
        try {
            localIntentFilter.addDataType("*/*");
            this.mFilters = new IntentFilter[] { localIntentFilter };
            String[][] arrayOfString = new String[1][];
            String[] arrayOfString1 = new String[1];
            arrayOfString1[0] = NfcF.class.getName();
            arrayOfString[0] = arrayOfString1;
            this.mTechLists = arrayOfString;
            this.isRecords = true;
        } catch (IntentFilter.MalformedMimeTypeException localMalformedMimeTypeException) {
            throw new RuntimeException("fail", localMalformedMimeTypeException);
        }
    }

    private void invalidateNFC() {
        if (nfcAdapter == null) {
            Toast.makeText(this, getResources().getString(R.string.age),
                    Toast.LENGTH_SHORT).show();
            finish();
            return;
        } else if (!nfcAdapter.isEnabled()) {
            Toast.makeText(this, getResources().getString(R.string.age),
                    Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
    }

    @Override
    public void onScanned(String decodedData, TYPE barcodeType, int length) {
        DebugLog.e("rx", "decodedData: " + decodedData);
        DebugLog.e("rx", "barcodeType: " + barcodeType);
        DebugLog.e("rx", "length: " + length);

        // String barcodeValue = String.format("Data: %s \r\n Type: %s",
        // decodedData, barcodeType.toString());

        if(com.mitac.cell.device.bcr.utility.BARCODE.TYPE.UPC_EAN13_E0 == barcodeType){
            //getBingrenData(decodedData);
            NFCCode = decodedData;

        }else if(com.mitac.cell.device.bcr.utility.BARCODE.TYPE.QR_CODE == barcodeType){
            //直接从二维码中读取信息
            NFCCode = decodedData;

        }else{

        }
    }

    @Override
    public void onStatusChanged(int status) {
        switch (status) {
            case McBcrMessage.Status_NotReady:
                break;
            case McBcrMessage.Status_Ready:
                break;
            case McBcrMessage.Status_ServiceConnected:
                break;
            case McBcrMessage.Status_ServiceDisconnected:
                break;
            case McBcrMessage.Status_ServiceStarted:
                break;
            case McBcrMessage.Status_ServiceStopped:
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);

        processIntent(intent);
    }

    private String bytesToHexString(byte[] paramArrayOfByte) {
        StringBuilder localStringBuilder = new StringBuilder("");
        if ((paramArrayOfByte == null) || (paramArrayOfByte.length <= 0))
            return null;
        char[] arrayOfChar = new char[2];
        for (int i = 0; i <= -1 + paramArrayOfByte.length; i++) {
            arrayOfChar[0] = Character.forDigit(
                    0xF & paramArrayOfByte[i] >>> 4, 16);
            arrayOfChar[1] = Character.forDigit(0xF & paramArrayOfByte[i], 16);
            localStringBuilder.append(arrayOfChar);
        }
        return localStringBuilder.toString();
    }

    /**
     *
     *
     * @param intent
     * @return
     */

    private String processIntent(Intent intent) {

        Tag localTag = (Tag) intent.getParcelableExtra("android.nfc.extra.TAG");
        String str1 = "";
        try {
            str1 = bytesToHexString(localTag.getId());
            this.NFCCode = str1.trim();

            DebugLog.e("processIntent", "this.NFCCode: " + this.NFCCode);

            if(bingrenInfo != null && bingrenInfo.isInvalidate()) { // 有待绑定的病人信息
                getPatientInfoByNFCCode();
            }
        } catch (NullPointerException localNullPointerException) {

        }
        return str1;
    }
}
