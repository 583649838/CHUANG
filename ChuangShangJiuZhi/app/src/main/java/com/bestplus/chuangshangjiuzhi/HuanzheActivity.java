package com.bestplus.chuangshangjiuzhi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bestplus.chuangshangjiuzhi.common.JsonKey;
import com.bestplus.chuangshangjiuzhi.common.Variable;
import com.bestplus.chuangshangjiuzhi.dialog.CommonDialog;
import com.bestplus.chuangshangjiuzhi.dialog.CustomProgressDialog;
import com.bestplus.chuangshangjiuzhi.entity.BingrenInfo;
import com.bestplus.chuangshangjiuzhi.entity.JianchaResultGroup;
import com.bestplus.chuangshangjiuzhi.entity.YingxiangResultGroup;
import com.bestplus.chuangshangjiuzhi.entity.YongyaoInfoGroup;
import com.bestplus.chuangshangjiuzhi.fragment.Fragment_Huanzhe_Jianyanjiancha;
import com.bestplus.chuangshangjiuzhi.fragment.Fragment_Huanzhe_Jibenxinxi;
import com.bestplus.chuangshangjiuzhi.fragment.Fragment_Huanzhe_Tuxiangyingpian;
import com.bestplus.chuangshangjiuzhi.fragment.Fragment_Huanzhe_Yongyaoxinxi;
import com.bestplus.chuangshangjiuzhi.fragment.Fragment_Huanzhe_Yongyaoxinxi_2;
import com.bestplus.chuangshangjiuzhi.fragment.Fragment_Huanzhe_jiuzhijilu;
import com.bestplus.chuangshangjiuzhi.util.DebugLog;
import com.bestplus.chuangshangjiuzhi.util.HttpClientRequest;
import com.bestplus.chuangshangjiuzhi.view.MyTitleView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maoamo on 2016/7/18.
 */
public class HuanzheActivity extends FragmentActivity implements View.OnClickListener {
    private final String TAG = "Huanzhe";
    private final static int MSG_GET_PATIENT_INFO_BY_JIUZHENHAO_RESULT = 1;
    private final static int MSG_GET_YONGYAOXINXI_INFO_BY_JIUZHENHAO_RESULT = 2;
    private final static int MSG_GET_CHECK_INFO_BY_JIUZHENHAO_RESULT = 3;
    private final static int MSG_GET_IMAGE_INFO_BY_JIUZHENHAO_RESULT = 4;
    private final static int MSG_MODIFY_UNREAD_STATUS_RESULT = 5;

    private RadioGroup tab_menu;
    private FrameLayout frameLayout;
    private RadioButton radioButton_jibenxinxi,
            radioButton_yongyaoxinxi,
            radioButton_jinayanjiancha,
            radioButton_tuxiangyingpian,
            radioButton_jiuzhijilu;

    private FragmentManager fragmentManager;
    private Fragment fragment_jibenxinxi,
            fragment_yongyaoxinxi,
            fragment_jinayanjiancha,
            fragment_tuxiangyingpian,
            fragment_jiuzhijilu;
    private int whichFragment;

    private String huanzheID;
    private EditText editText_name, editText_jiuzhenhao;
    private BingrenInfo bingrenInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huanzhe);

        fragmentManager = getSupportFragmentManager();

        Intent intent = getIntent();
        whichFragment = intent.getIntExtra("whichFragment", 0);
        huanzheID = intent.getStringExtra("huanzheID");

        System.out.println("whichFragment: " + whichFragment);

        initTitle();
        initView();
        initViewHandler();
    }

    private void initView() {
        editText_jiuzhenhao = (EditText) findViewById(R.id.editText_jiuzhenhao);
        editText_name =  (EditText) findViewById(R.id.editText_name);
        tab_menu = (RadioGroup) findViewById(R.id.tab_menu);
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);

        radioButton_jibenxinxi = (RadioButton) findViewById(R.id.radioButton_jibenxinxi);
        radioButton_yongyaoxinxi = (RadioButton) findViewById(R.id.radioButton_yongyaoxinxi);
        radioButton_jinayanjiancha = (RadioButton) findViewById(R.id.radioButton_jinayanjiancha);
        radioButton_tuxiangyingpian = (RadioButton) findViewById(R.id.radioButton_tuxiangyingpian);
        radioButton_jiuzhijilu = (RadioButton) findViewById(R.id.radioButton_jiuzhijilu);

        editText_jiuzhenhao.setText(huanzheID);
        startFragment(whichFragment);
    }
    private void initViewHandler() {
        radioButton_jibenxinxi.setOnClickListener(this);
        radioButton_yongyaoxinxi.setOnClickListener(this);
        radioButton_jinayanjiancha.setOnClickListener(this);
        radioButton_tuxiangyingpian.setOnClickListener(this);
        radioButton_jiuzhijilu.setOnClickListener(this);
    }

    private void startFragment(int index){
        whichFragment = index;
        switch (index){
            case 0:
                System.out.println("Fragment_Huanzhe_Jibenxinxi");
                if(null == fragment_jibenxinxi){
                    fragment_jibenxinxi = new Fragment_Huanzhe_Jibenxinxi();
                }
                radioButton_jibenxinxi.setChecked(true);
                fragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, fragment_jibenxinxi)
                        .commit();
                break;

            case 1:
                System.out.println("Fragment_Huanzhe_Yongyaoxinxi");
                if(null == fragment_yongyaoxinxi){
                    fragment_yongyaoxinxi = new Fragment_Huanzhe_Yongyaoxinxi_2();
                }
                radioButton_yongyaoxinxi.setChecked(true);
                fragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, fragment_yongyaoxinxi)
                        .commit();
                break;

            case 2:
                System.out.println("Fragment_Huanzhe_Jianyanjiancha");
                if(null == fragment_jinayanjiancha){
                    fragment_jinayanjiancha = new Fragment_Huanzhe_Jianyanjiancha();
                }
                radioButton_jinayanjiancha.setChecked(true);
                fragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, fragment_jinayanjiancha)
                        .commit();
                break;

            case 3:
                System.out.println("Fragment_Huanzhe_Tuxiangyingpian");
                if(null == fragment_tuxiangyingpian){
                    fragment_tuxiangyingpian = new Fragment_Huanzhe_Tuxiangyingpian();
                }
                radioButton_tuxiangyingpian.setChecked(true);
                fragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, fragment_tuxiangyingpian)
                        .commit();
                break;

            case 4:
                System.out.println("Fragment_Huanzhe_jiuzhijilu");
                if(null == fragment_jiuzhijilu){
                    fragment_jiuzhijilu = new Fragment_Huanzhe_jiuzhijilu();
                }
                radioButton_jiuzhijilu.setChecked(true);
                fragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, fragment_jiuzhijilu)
                        .commit();
                break;
        }
    }

    private void initTitle() {
        // TODO Auto-generated method stub
        MyTitleView titleView = new MyTitleView(
                (RelativeLayout) findViewById(R.id.title_layout),
                getString(R.string.main_huanzhexinxi),
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

            case R.id.radioButton_jibenxinxi:
                startFragment(0);
                break;
            case R.id.radioButton_yongyaoxinxi:
                startFragment(1);
                break;
            case R.id.radioButton_jinayanjiancha:
                startFragment(2);
                break;
            case R.id.radioButton_tuxiangyingpian:
                startFragment(3);
                break;
            case R.id.radioButton_jiuzhijilu:
                startFragment(4);
                break;

            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        System.out.println("HuanzheActivity -- onResume,this= " + this.toString());

        getPatientInfoByJiuzhenhao();
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();

        System.out.println("HuanzheActivity -- onPause");
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        System.out.println("HuanzheActivity -- onDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        System.out.println("HuanzheActivity -- onSaveInstanceState");
        //super.onSaveInstanceState(outState);
    }

    public String getHuanzheID(){
        return this.huanzheID;
    }

    public void updateHuanzheName(String newName){
        editText_name.setText(newName);
    }

    public BingrenInfo getBingrenInfo(){
        return this.bingrenInfo;
    }

    /**
     * 根据就诊号获取病人信息
     * 测试例子：
     *      http://192.168.10.142:8080/treat/app/treat.html?mark=getPatientInfoByCode&patientCode=234567
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
    public void getPatientInfoByJiuzhenhao() {
        if(huanzheID == null){
            DebugLog.e("getPatientInfoByJiuzhenhao", "huanzheID is empty!");
            return;
        }

        RequestParams params = new RequestParams();
        params.put(JsonKey.mark, "getPatientInfoByCode");
        params.put(JsonKey.ItemHuanzheId, huanzheID);

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

    private List<YongyaoInfoGroup> itemsOfYongyaoInfoGroup = new ArrayList<YongyaoInfoGroup>();
    public List<YongyaoInfoGroup> getItemsOfYongyaoInfoGroup() {
        return itemsOfYongyaoInfoGroup;
    }

    /**
     * 根据病例号获取病人某段时间内的用药信息
     * 测试例子：
     *      http://192.168.10.142:8080/treat/app/treat.html?mark=getCareInfoByCodeTime&patientCode=123&from=2016-06-30&to=2016-07-31
     *
     *      {"result":1,
     *      "patient":{"acceptsStatus":0,"fixedTelephone":"18423456543","gmtCreated":1468032280000,"gmtModified":1470019102000,"id":1,"isDel":1,
     *      "linkPerson":"王竹","linkPhone":"0555-6132421","patientAge":"23","patientCardId":"340523456724562718","patientCode":"123",
     *      "patientHight":"187","patientName":"小刚2","patientSex":"男","receptionCode":"243649","receptionPerson":"小智",
     *      "seeDoctorDate":1467427464000,"seeDoctorRecords":"不咋地啊","transferStatus":1},
     *
     *      "care":{"careCode":"23","careDate":1467369286000,"careDate2":"2016-07-01","careDuty":"王二",
     *      "careName":"温都水城","careSttopDate":1468060803000,"careSttopDate2":"2016-07-09",
     *      "careType":"12","delDate":1468751991000,"gmtCreated":1468579187000,"gmtModified":1468147183000,"id":1,
     *      "patientCode":"123","patientName":"小刚","pingci":"TID","useAmount":"250ml","useMethod":"注射","yizhuCode":"1"}}
     */
    public void getCareInfoByCodeTime(String patientCode,String startDate, String endDate) {
        if(patientCode == null){
            DebugLog.e("getCareInfoByCodeTime", "patientCode is empty!");
            return;
        }

        RequestParams params = new RequestParams();
        params.put(JsonKey.mark, "getCareInfoByCodeTime");
        params.put(JsonKey.ItemHuanzheId, patientCode);
        params.put(JsonKey.ItemStartDate, startDate);
        params.put(JsonKey.ItemEndDate, endDate);
        params.put("doctorCode", Variable.cs_user.getMemberCode());

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
                        msg.what = MSG_GET_YONGYAOXINXI_INFO_BY_JIUZHENHAO_RESULT;
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

    private List<JianchaResultGroup> itemsOfJianchaResultGroup = new ArrayList<JianchaResultGroup>();
    public List<JianchaResultGroup> getItemsOfJianchaResultGroup(){
        return itemsOfJianchaResultGroup;
    }
    /**
     * 根据病例号获取病人某段时间内收到的检查报告
     * 测试例子：
     *      http://192.168.10.142:8080/treat/app/treat.html?mark=getCheckInfoByCodeTime&patientCode=123&from=2016-06-01&to=2016-08-01
     *
     *       {"result":1,
     *       "patient":{
     *          "acceptsStatus":1,"fixedTelephone":"18423456543","gmtCreated":1468032280000,"gmtModified":1470103103000,"id":1,"isDel":1,"linkPerson":"王竹","linkPhone":"0555-6132421","patientAge":"23","patientCardId":"340523456724562718","patientCode":"123","patientHight":"187","patientName":"小刚2","patientSex":"男","receptionCode":"243649","receptionPerson":"小智","seeDoctorDate":1467427464000,"seeDoctorRecords":"不咋地啊","transferStatus":4
     *          },
     *       "CheckArray":[
     *          {"check2":{"checkName":"血常规","checkTime":1469692733000,"checkTime2":"2016-07-28","checkType":"血常规","gmtCreated":1468569581000,"gmtModified":1470013785000,"id":1,"isDel":1,"patientCode":"123","patientName":"小刚","picCode":"TID","readflag":0,"reportTime":1469771070000,"reportTime2":"2016-07-29"}},
     *          {"check2":{"checkName":"尿检","checkTime":1468656017000,"checkTime2":"2016-07-16","checkType":"尿检","gmtCreated":1468828849000,"gmtModified":1469954657000,"id":2,"isDel":1,"patientCode":"123","patientName":"小刚","picCode":"TID","readflag":0,"reportTime":1469857475000,"reportTime2":"2016-07-30"}}
     *          ]
     *       }
     */
    public void getCheckInfoByCodeTime(String patientCode,String startDate, String endDate) {
        if(patientCode == null){
            DebugLog.e("getCheckInfoByCodeTime", "patientCode is empty!");
            return;
        }

        RequestParams params = new RequestParams();
        params.put(JsonKey.mark, "getCheckInfoByCodeTime");
        params.put(JsonKey.ItemHuanzheId, patientCode);
        params.put(JsonKey.ItemStartDate, startDate);
        params.put(JsonKey.ItemEndDate, endDate);
        params.put("doctorCode", Variable.cs_user.getMemberCode());

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
                        msg.what = MSG_GET_CHECK_INFO_BY_JIUZHENHAO_RESULT;
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

    private List<YingxiangResultGroup> itemsOfYingxiangResultGroup = new ArrayList<YingxiangResultGroup>();
    public List<YingxiangResultGroup> getItemsOfYingxiangResultGroup() {
        return itemsOfYingxiangResultGroup;
    }

        /**
         * 根据病例号获取病人某段时间内的图像影片信息
         * 测试例子：
         *      http://192.168.10.142:8080/treat/app/treat.html?mark=getImagByIdAndTime&patientCode=123&from=2016-07-01&to=2016-08-01
         *
         *
         *
         */
    public void getImagByIdAndTime(String patientCode,String startDate, String endDate) {
        if(patientCode == null){
            DebugLog.e("getImagByIdAndTime", "patientCode is empty!");
            return;
        }

        RequestParams params = new RequestParams();
        params.put(JsonKey.mark, "getImagByIdAndTime");
        params.put(JsonKey.ItemHuanzheId, patientCode);
        params.put(JsonKey.ItemStartDate, startDate);
        params.put(JsonKey.ItemEndDate, endDate);
        params.put("doctorCode", Variable.cs_user.getMemberCode());

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
                        msg.what = MSG_GET_IMAGE_INFO_BY_JIUZHENHAO_RESULT;
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

                        String tempTestData = "{\"CareList\":[{\"groupYizhu\":[{\"careList\":[{\"careCode\":\"10\",\"careDate\":1467628486000,\"careDuty\":\"小三\",\"careName\":\"菜户\",\"careSttopDate\":1467715203000,\"careType\":\"13\",\"delDate\":1468147191000,\"gmtCreated\":1468751987000,\"gmtModified\":1468406383000,\"id\":2,\"patientCode\":\"123\",\"patientName\":\"小刚\",\"pingci\":\"TID\",\"useAmount\":\"300ml\",\"useMethod\":\"注射\",\"yizhuCode\":\"1\"}],\"groupCode\":\"10\"},{\"careList\":[{\"careCode\":\"23\",\"careDate\":1467369286000,\"careDuty\":\"王二\",\"careName\":\"板蓝根\",\"careSttopDate\":1468060803000,\"careType\":\"12\",\"delDate\":1468751991000,\"gmtCreated\":1468579187000,\"gmtModified\":1468147183000,\"id\":1,\"patientCode\":\"123\",\"patientName\":\"小刚\",\"pingci\":\"TID\",\"useAmount\":\"250ml\",\"useMethod\":\"注射\",\"yizhuCode\":\"1\"}],\"groupCode\":\"23\"},{\"careList\":[{\"careCode\":\"25\",\"careDate\":1468146886000,\"careDuty\":\"小四\",\"careName\":\"泻立停\",\"careSttopDate\":1467888003000,\"careType\":\"16\",\"delDate\":1468233591000,\"gmtCreated\":1469097587000,\"gmtModified\":1468751983000,\"id\":3,\"patientCode\":\"123\",\"patientName\":\"小花\",\"pingci\":\"TID\",\"useAmount\":\"350ml\",\"useMethod\":\"注射\",\"yizhuCode\":\"1\"}],\"groupCode\":\"25\"}],\"yizhuCode\":\"1\"},{\"groupYizhu\":[{\"careList\":[{\"careCode\":\"27\",\"careDate\":1468166400000,\"careDuty\":\"小四\",\"careName\":\"点滴\",\"careSttopDate\":1467820800000,\"careType\":\"16\",\"delDate\":1468233591000,\"gmtCreated\":1469867745000,\"gmtModified\":1469867745000,\"id\":5,\"patientCode\":\"123\",\"patientName\":\"小刚\",\"pingci\":\"TID\",\"useAmount\":\"350ml\",\"useMethod\":\"注射\",\"yizhuCode\":\"5\"}],\"groupCode\":\"27\"}],\"yizhuCode\":\"5\"}],\"result\":1,\"patient\":{\"acceptsStatus\":0,\"fixedTelephone\":\"18423456543\",\"gmtCreated\":1468032280000,\"gmtModified\":1470205829000,\"id\":1,\"isDel\":1,\"linkPerson\":\"王\",\"linkPhone\":\"0555-6132421\",\"patientAge\":\"23\",\"patientCardId\":\"340523456724562718\",\"patientCode\":\"123\",\"patientHight\":\"187\",\"patientName\":\"小刚2\",\"patientSex\":\"男\",\"receptionCode\":\"243649\",\"receptionPerson\":\"小智\",\"seeDoctorDate\":1467427464000,\"seeDoctorRecords\":\"不咋地啊\",\"transferDepartment\":\"骨科\",\"transferFlag\":1,\"transferHospital\":\"\",\"transferOutpatient\":\"\",\"transferType\":\"住院\"}} ";

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
     * 根据医生ID 报告ID 报告类型 修改检验和检查CT报告状态为已读
     * 测试例子：
     *      http://192.168.10.179:8080/treat/app/treat.html?mark=updatereadflagById&doctorCode=1000000121&type=1&typeId=34
     *
     *
     *
     */
    public void modifyCheckAndImageUnreadReportStatus(String docId, String reportType, String reportId) {
        RequestParams params = new RequestParams();
        params.put(JsonKey.mark, "updatereadflagById");
        params.put("doctorCode", docId);
        params.put("type", reportType);
        params.put("typeId", reportId);

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
                        msg.what = MSG_MODIFY_UNREAD_STATUS_RESULT;
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

    public void modifyYizhuUnreadReportStatus(String docId,String patientCode,String reportType, String yizhuCode,String groupCode) {
        RequestParams params = new RequestParams();
        params.put(JsonKey.mark, "updatereadflagById");
        params.put("doctorCode", docId);
        params.put("patientCode", patientCode);
        params.put("type", reportType);
        params.put("typeCode", yizhuCode);
        params.put("groupCode", groupCode);

        System.out.print("modifyYizhuUnreadReportStatus \n");
        System.out.print(params.toString());

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
                        msg.what = MSG_MODIFY_UNREAD_STATUS_RESULT;
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

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case MSG_GET_PATIENT_INFO_BY_JIUZHENHAO_RESULT:
                    Bundle bundle = msg.getData();
                    String data = bundle.getString("data");
                    System.out.println(data);
                    if (data != null & !data.equals("")) {
                        try {
                            JSONObject object = new JSONObject(data);
                            int flag = object.optInt(JsonKey.result);
                            if (flag == 1) {
                                Toast.makeText(HuanzheActivity.this, "获取病人信息成功！", Toast.LENGTH_SHORT).show();
                                //构造BingrenInfo对象
                                JSONObject patient = object.getJSONObject("patient");
                                bingrenInfo = new BingrenInfo(patient);
                                if(bingrenInfo != null)
                                    updateHuanzheName(bingrenInfo.getItemHuanzheName());
                                //更新患者基本信息Fragment页面数据
                                if(fragment_jibenxinxi != null && whichFragment == 0){
                                    ((HuanzheFragmentUpdateview)fragment_jibenxinxi).fragmentUpdateview();
                                }
                            }else {
                                CommonDialog dialog = new CommonDialog(
                                        HuanzheActivity.this,
                                        R.style.userDialog,
                                        "没有该病人！",
                                        getResources().getString(R.string.confirm_label));
                                dialog.show();
                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                    break;

                case MSG_GET_YONGYAOXINXI_INFO_BY_JIUZHENHAO_RESULT:
                    bundle = msg.getData();
                    data = bundle.getString("data");
                    System.out.println(data);
                    if (data != null & !data.equals("")) {
                        try
                        {
                            JSONObject object = new JSONObject(data);
                            int flag = object.optInt(JsonKey.result);
                            if (flag == 1) {
                                Toast.makeText(HuanzheActivity.this, "获取病人用药信息成功！", Toast.LENGTH_SHORT).show();

                                itemsOfYongyaoInfoGroup.clear();

                                //itemsOfYongyaoInfoGroup， YongyaoInfoGroup
                                String list = object.optString("CareList");
                                if (list != null && !"".equals(list)) {
                                    JSONArray array = object.optJSONArray("CareList"); // CareList数组
                                    if (array != null && array.length() > 0) {
                                        for (int i = 0; i < array.length(); i++) {
                                            String yizhuCode = array.optJSONObject(i).optString("yizhuCode", "");
                                            String list2 = array.optJSONObject(i).optString("groupYizhu");
                                            if (list2 != null && !"".equals(list2)) {
                                                JSONArray array2 = array.optJSONObject(i).optJSONArray("groupYizhu"); // groupYizhu数组
                                                if (array2 != null && array2.length() > 0) {
                                                    for (int j = 0; j < array2.length(); j++) {
                                                        JSONObject tempObject = array2.optJSONObject(j);
                                                        JSONArray tempObject2 = tempObject.optJSONArray("careList");
                                                        String groupCode = tempObject.optString("groupCode", "");
                                                        int readFlag = tempObject.optInt("readFlag", 0);
                                                        YongyaoInfoGroup item = new YongyaoInfoGroup(
                                                                tempObject2, yizhuCode, groupCode, readFlag); // careList数组中存放相同组号的用药信息

                                                        itemsOfYongyaoInfoGroup.add(item);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                //更新患者用药信息Fragment页面数据
                                if(fragment_yongyaoxinxi != null && whichFragment == 1){
                                    ((HuanzheFragmentUpdateview)fragment_yongyaoxinxi).fragmentUpdateview();
                                }
                            }else{
                                    CommonDialog dialog = new CommonDialog(
                                            HuanzheActivity.this,
                                            R.style.userDialog,
                                            "获取病人用药信息失败，请重试！",
                                            getResources().getString(R.string.confirm_label));
                                    dialog.show();
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                    break;

                case MSG_GET_CHECK_INFO_BY_JIUZHENHAO_RESULT:
                    bundle = msg.getData();
                    data = bundle.getString("data");
                    System.out.println(data);
                    if (data != null & !data.equals("")) {
                        try {
                            JSONObject object = new JSONObject(data);
                            int flag = object.optInt(JsonKey.result);
                            if (flag == 1) {
                                Toast.makeText(HuanzheActivity.this, "获取病人检验信息成功！", Toast.LENGTH_SHORT).show();
                                itemsOfJianchaResultGroup.clear();

                                //填充itemsOfJianchaResultGroup， JianchaResultGroup
                                String list = object.optString("list");
                                if (list != null && !"".equals(list)) {
                                    JSONArray array = object.optJSONArray("list");

                                    if(array != null && array.length() > 0) {
                                        for (int i = 0; i < array.length(); i++) {
                                            JianchaResultGroup item = new JianchaResultGroup(
                                                    array.optJSONObject(i), true);

                                            itemsOfJianchaResultGroup.add(item);
                                        }
                                    }
                                    //outstorageAdapter.notifyDataSetChanged();
                                }

                                //更新患者检验信息Fragment页面数据
                                if(fragment_jinayanjiancha != null && whichFragment == 2){
                                    ((HuanzheFragmentUpdateview)fragment_jinayanjiancha).fragmentUpdateview();
                                }
                            }else {
                                CommonDialog dialog = new CommonDialog(
                                        HuanzheActivity.this,
                                        R.style.userDialog,
                                        "获取病人检验信息失败，请重试！",
                                        getResources().getString(R.string.confirm_label));
                                dialog.show();
                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                    break;

                case MSG_GET_IMAGE_INFO_BY_JIUZHENHAO_RESULT:
                    bundle = msg.getData();
                    data = bundle.getString("data");
                    System.out.println(data);
                    if (data != null & !data.equals("")) {
                        try {
                            JSONObject object = new JSONObject(data);
                            int flag = object.optInt(JsonKey.result);
                            if (flag == 1) {
                                Toast.makeText(HuanzheActivity.this, "获取病人图像影片信息成功！", Toast.LENGTH_SHORT).show();
                                itemsOfYingxiangResultGroup.clear();

                                //itemsOfYingxiangResultGroup， YingxiangResultGroup
                                String list = object.optString("secondList");
                                if (list != null && !"".equals(list)) {
                                    JSONArray array = object.optJSONArray("secondList");

                                    if(array != null && array.length() > 0) {
                                        for (int i = 0; i < array.length(); i++) {
                                            YingxiangResultGroup item = new YingxiangResultGroup(
                                                    array.optJSONObject(i).optJSONObject("imagery"));

                                            itemsOfYingxiangResultGroup.add(item);
                                        }
                                    }
                                    //outstorageAdapter.notifyDataSetChanged();
                                }
                                //更新患者检验信息Fragment页面数据
                                if(fragment_tuxiangyingpian != null && whichFragment == 3){
                                    ((HuanzheFragmentUpdateview)fragment_tuxiangyingpian).fragmentUpdateview();
                                }
                            }else {
                                CommonDialog dialog = new CommonDialog(
                                        HuanzheActivity.this,
                                        R.style.userDialog,
                                        "获取病人图像影片信息失败，请重试！",
                                        getResources().getString(R.string.confirm_label));
                                dialog.show();
                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                    break;

                case MSG_MODIFY_UNREAD_STATUS_RESULT:
                    bundle = msg.getData();
                    data = bundle.getString("data");
                    System.out.println(data);
                    if (data != null & !data.equals("")) {
                        try {
                            JSONObject object = new JSONObject(data);
                            int flag = object.optInt(JsonKey.result);
                            if (flag == 1) {
                                //{"result":1,"isFocus":1,"patientCode":"789","doctorCode":"006"}
                                boolean newStatus = false;

                                //更新

                            } else {
                                Toast.makeText(HuanzheActivity.this, "修改未读状态失败！", Toast.LENGTH_SHORT).show();
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

    public interface HuanzheFragmentUpdateview{
        public void fragmentUpdateview();
    }
}
