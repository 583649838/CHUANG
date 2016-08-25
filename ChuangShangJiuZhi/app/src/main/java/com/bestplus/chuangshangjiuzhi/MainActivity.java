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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bestplus.chuangshangjiuzhi.common.JsonKey;
import com.bestplus.chuangshangjiuzhi.common.Variable;
import com.bestplus.chuangshangjiuzhi.dialog.CommonDialog;
import com.bestplus.chuangshangjiuzhi.dialog.CustomProgressDialog;
import com.bestplus.chuangshangjiuzhi.entity.BingrenInfo;
import com.bestplus.chuangshangjiuzhi.entity.Hotwords;
import com.bestplus.chuangshangjiuzhi.entity.HuanzheJiuzhiInfo;
import com.bestplus.chuangshangjiuzhi.entity.HuanzheStatus;
import com.bestplus.chuangshangjiuzhi.entity.JianchaResultGroup;
import com.bestplus.chuangshangjiuzhi.entity.JieguoNotifyInfo;
import com.bestplus.chuangshangjiuzhi.fragment.Fragment_Main_Chuangshajilu;
import com.bestplus.chuangshangjiuzhi.fragment.Fragment_Main_Guanzhuliebiao;
import com.bestplus.chuangshangjiuzhi.fragment.Fragment_Main_Huanzhezhongxin;
import com.bestplus.chuangshangjiuzhi.fragment.Fragment_Main_Jieguotixing;

import com.bestplus.chuangshangjiuzhi.util.DebugLog;
import com.bestplus.chuangshangjiuzhi.util.HttpClientRequest;
import com.bestplus.chuangshangjiuzhi.view.MyTitleView_Two;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mitac.cell.device.bcr.McBcrConnection;
import com.mitac.cell.device.bcr.McBcrListener;
import com.mitac.cell.device.bcr.McBcrMessage;
import com.mitac.cell.device.bcr.MiBcrListener;
import com.mitac.cell.device.bcr.utility.BARCODE;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maoamo on 2016/7/23.
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener, MiBcrListener {
    private final static int GET_All_PATIENTS_RESULT = 101;
    private final static int MODIFY_FOCUS_RESULT = 102;
    private final static int GET_All_PATIENTS_BY_DOCTORID_RESULT = 103;
    private final static int GET_FOCUS_PATIENT_AND_UNREAD_INFO_BY_DOCTORID_RESULT = 104;
    private final static int GET_CHECK_AND_IMAGE_LIST_RESULT = 105;
    private final static int GET_PROCESS_RECORDS_BY_CODE_AND_TIME_RESULT = 106;

    private final static int MSG_GET_PATIENT_INFO_BY_JIUZHENHAO_RESULT = 201;
    private final static int MSG_GET_PATIENT_INFO_BY_NFCCODE_RESULT = 202;

    private MyTitleView_Two titleView;

    private FragmentManager fragmentManager;
    private int fragmentIndex = 0;
    private Fragment fragment_guanzhuliebian,
            fragment_huanzhezhongxin,
            fragment_chuangshanjilu,
            fragment_jieguotixing;

    private RadioButton radioButton_guanzhuliebian,
            radioButton_huanzhezhongxin,
            radioButton_chuangshanjilu,
            radioButton_jieguotixing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        fragmentManager = getSupportFragmentManager();

        initTitle();
        initView();
        initViewHandler();

        initNFC();
    }

    private void initView() {
        radioButton_guanzhuliebian = (RadioButton) findViewById(R.id.radioButton_guanzhuliebian);
        radioButton_huanzhezhongxin = (RadioButton) findViewById(R.id.radioButton_huanzhezhongxin);
        radioButton_chuangshanjilu = (RadioButton) findViewById(R.id.radioButton_chuangshanjilu);
        radioButton_jieguotixing = (RadioButton) findViewById(R.id.radioButton_jieguotixing);

        changeTitleCenterText("关注列表");
        radioButton_guanzhuliebian.setChecked(true);
        if(null == fragment_guanzhuliebian){
            fragment_guanzhuliebian = new Fragment_Main_Guanzhuliebiao();
        }
        fragmentIndex = 0;
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragment_guanzhuliebian)
                .commit();
    }

    private void initViewHandler() {
        radioButton_guanzhuliebian.setOnClickListener(this);
        radioButton_huanzhezhongxin.setOnClickListener(this);
        radioButton_chuangshanjilu.setOnClickListener(this);
        radioButton_jieguotixing.setOnClickListener(this);


    }

    private void initTitle() {
        // TODO Auto-generated method stub
        titleView = new MyTitleView_Two(
                (RelativeLayout) findViewById(R.id.title_layout),
                R.string.main,
                R.drawable.nfc,
                R.string.main_title_shouhuan,
                R.drawable.login_user,
                Variable.cs_user.getMemberName());
                //R.string.main_title_yonghu);

        titleView.getLeftImageView().setOnClickListener(this);
        titleView.getLeftTextView().setOnClickListener(this);

        titleView.getRightImageView().setOnClickListener(this);
        titleView.getRightTextView().setOnClickListener(this);
    }

    public void changeTitleCenterText(String newText){
        titleView.changeCenterText(newText);
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.leftIcon:
            case R.id.leftText: //进入手环绑定就诊号页面
                startActivity(new Intent(MainActivity.this, BangdingshouhuanActivity.class));
                break;

            case R.id.rightIcon:
            case R.id.rightText: //标题栏右侧的文字图片暂时仅仅显示工号对应的医生或护士的姓名
                break;

            case R.id.radioButton_guanzhuliebian: // 关注列表
                changeTitleCenterText("关注列表");
                if(null == fragment_guanzhuliebian){
                    fragment_guanzhuliebian = new Fragment_Main_Guanzhuliebiao();
                }
                fragmentIndex = 0;
                fragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, fragment_guanzhuliebian)
                        .commit();
                break;

            case R.id.radioButton_huanzhezhongxin: // 患者中心
                changeTitleCenterText("患者中心");
                if(null == fragment_huanzhezhongxin){
                    fragment_huanzhezhongxin = new Fragment_Main_Huanzhezhongxin();
                }
                fragmentIndex = 1;
                fragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, fragment_huanzhezhongxin)
                        .commit();
                break;

            case R.id.radioButton_chuangshanjilu: // 创伤记录
                changeTitleCenterText("创伤记录");
                if(null == fragment_chuangshanjilu){
                    fragment_chuangshanjilu = new Fragment_Main_Chuangshajilu();
                }
                fragmentIndex = 2;
                fragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, fragment_chuangshanjilu)
                        .commit();
                break;

            case R.id.radioButton_jieguotixing: // 结果提醒
                changeTitleCenterText("结果列表");
                if(null == fragment_jieguotixing){
                    fragment_jieguotixing = new Fragment_Main_Jieguotixing();
                }
                fragmentIndex = 3;
                fragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, fragment_jieguotixing)
                        .commit();
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
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
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

    private List<HuanzheStatus> itemsOfHuanzheStatus = new ArrayList<HuanzheStatus>();
    public List<HuanzheStatus> getItemsOfHuanzheStatus(){
        return itemsOfHuanzheStatus;
    }
    /**
     * 获取创伤科所有病人
     * 测试例子：
     *      http://192.168.10.142:8080/treat/app/treat.html?mark=getAllPatient
     *
     *      {"result":1,
     *      "patientList":
     *          [
     *              {"patient":{"acceptsStatus":0,"fixedTelephone":"18423456543","gmtCreated":1468032280000,"gmtModified":1470278321000,"id":1,"isDel":1,"linkPerson":"王","linkPhone":"0555-6132421","patientAge":"23","patientCardId":"340523456724562718","patientCode":"123","patientHight":"187","patientName":"小刚2","patientSex":"男","receptionCode":"243649","receptionPerson":"小智","seeDoctorDate":1467427464000,"seeDoctorRecords":"不咋地啊","transferDepartment":"","transferFlag":1,"transferHospital":"","transferOutpatient":"","transferType":"1"}},
     *              {"patient":{"acceptsStatus":1,"fixedTelephone":"18423456543","gmtCreated":1467351173000,"gmtModified":1470273615000,"id":2,"isDel":1,"linkPerson":"万发","linkPhone":"0555-6132421","patientAge":"24","patientCardId":"340523456724562718","patientCode":"456","patientHight":"187","patientName":"小花2","patientSex":"男","receptionCode":"245675","receptionPerson":"张三","seeDoctorDate":1467696769000,"seeDoctorRecords":"让我感到","transferDepartment":"","transferFlag":0,"transferHospital":"","transferOutpatient":"","transferType":"2"}},
     *              {"patient":{"acceptsStatus":0,"fixedTelephone":"18423456543","gmtCreated":1468550680000,"gmtModified":1470274223000,"id":4,"isDel":1,"linkPerson":"王小鱼","linkPhone":"0555-6132421","patientAge":"42","patientCardId":"340523456724562718","patientCode":"789","patientHight":"187","patientName":"是否","patientSex":"男","receptionCode":"243640","receptionPerson":"小智","seeDoctorDate":1468723464000,"seeDoctorRecords":"各地v额","transferDepartment":"","transferFlag":1,"transferHospital":"","transferOutpatient":"","transferType":"治愈"}},
     *              {"patient":{"acceptsStatus":1,"fixedTelephone":"18423456543","gmtCreated":1467783173000,"gmtModified":1470275716000,"id":5,"isDel":1,"linkPerson":"撒村","linkPhone":"0555-6132421","patientAge":"55","patientCardId":"340523456724562718","patientCode":"245654","patientHight":"187","patientName":"而无法","patientSex":"男","receptionCode":"245689","receptionPerson":"阿萨德","seeDoctorDate":1467955969000,"seeDoctorRecords":"外人无法","transferDepartment":"","transferFlag":1,"transferHospital":"","transferOutpatient":"阿萨德啊","transferType":"留观"}},
     *              {"patient":{"acceptsStatus":1,"fixedTelephone":"18423456543","gmtCreated":1467955973000,"gmtModified":1470226921000,"id":6,"isDel":1,"linkPerson":"上次","linkPhone":"0555-6132421","patientAge":"23","patientCardId":"340523456724562718","patientCode":"245619","patientHight":"187","patientName":"王道","patientSex":"男","receptionCode":"247188","receptionPerson":"文档","seeDoctorDate":1468215169000,"seeDoctorRecords":" 而我国仍","transferDepartment":"","transferFlag":0,"transferHospital":"","transferOutpatient":"","transferType":"2"}},
     *              {"patient":{"acceptsStatus":0,"fixedTelephone":"18423456543","gmtCreated":1467254680000,"gmtModified":1470223758000,"id":7,"isDel":1,"linkPerson":"二层","linkPhone":"0555-6132421","patientAge":"45","patientCardId":"340523456724562718","patientCode":"234589","patientHight":"187","patientName":"当初","patientSex":"男","receptionCode":"242778","receptionPerson":"服务","seeDoctorDate":1468118664000,"seeDoctorRecords":"人格女","transferFlag":0}},
     *              {"patient":{"acceptsStatus":1,"fixedTelephone":"18423456543","gmtCreated":1467341080000,"gmtModified":1470222787000,"id":8,"isDel":1,"linkPerson":"二分法","linkPhone":"0555-6132421","patientAge":"24","patientCardId":"340523456724562718","patientCode":"234524","patientHight":"187","patientName":"是否","patientSex":"男","receptionCode":"243640","receptionPerson":"小智","seeDoctorDate":1468723464000,"seeDoctorRecords":"各地v额","transferDepartment":"","transferFlag":0,"transferHospital":"吴市吹箫","transferOutpatient":"","transferType":"转ICU"}},
     *              {"patient":{"acceptsStatus":1,"fixedTelephone":"18423456543","gmtCreated":1468301573000,"gmtModified":1470275751000,"id":9,"isDel":1,"linkPerson":"王竹","linkPhone":"0555-6132421","patientAge":"53","patientCardId":"340523456724562718","patientCode":"245654","patientHight":"187","patientName":"李强","patientSex":"男","receptionCode":"245689","receptionPerson":"阿萨德","seeDoctorDate":1467955969000,"seeDoctorRecords":"外人无法","transferDepartment":"","transferFlag":1,"transferHospital":"","transferOutpatient":"","transferType":"治愈"}},
     *              {"patient":{"acceptsStatus":0,"fixedTelephone":"18423456543","gmtCreated":1467955973000,"gmtModified":1470226922000,"id":10,"isDel":1,"linkPerson":"舞蹈服","linkPhone":"0555-6132421","patientAge":"23","patientCardId":"340523456724562718","patientCode":"123456","patientHight":"187","patientName":"小刚","patientSex":"女","receptionCode":"247188","receptionPerson":"文档","seeDoctorDate":1468215169000,"seeDoctorRecords":" 而我国仍","transferDepartment":"","transferFlag":0,"transferHospital":"","transferOutpatient":"","transferType":"2"}},
     *              {"patient":{"acceptsStatus":1,"fixedTelephone":"18423456543","gmtCreated":1467254680000,"gmtModified":1470275454000,"id":11,"isDel":1,"linkPerson":"王竹","linkPhone":"0555-6132421","patientAge":"25","patientCardId":"340523456724562718","patientCode":"234557","patientHight":"187","patientName":"当初","patientSex":"男","receptionCode":"242778","receptionPerson":"服务","seeDoctorDate":1468118664000,"seeDoctorRecords":"人格女","transferDepartment":"","transferFlag":0,"transferHospital":"","transferOutpatient":"","transferType":"2"}},
     *              {"patient":{"acceptsStatus":0,"fixedTelephone":"18423456543","gmtCreated":1469069080000,"gmtModified":1470223154000,"id":12,"isDel":1,"linkPerson":"数据","linkPhone":"0555-6132421","patientAge":"24","patientCardId":"340523456724562718","patientCode":"234987","patientHight":"187","patientName":"是否","patientSex":"男","receptionCode":"243640","receptionPerson":"小智","seeDoctorDate":1468723464000,"seeDoctorRecords":"各地v额","transferFlag":0}},
     *              {"patient":{"acceptsStatus":0,"fixedTelephone":"18423456543","gmtCreated":1467783173000,"gmtModified":1469845736000,"id":13,"isDel":1,"linkPerson":"小姑娘","linkPhone":"0555-6132421","patientAge":"34","patientCardId":"340523456724562718","patientCode":"245609","patientHight":"187","patientName":"张麻","patientSex":"男","receptionCode":"245689","receptionPerson":"阿萨德","seeDoctorDate":1467955969000,"seeDoctorRecords":"外人无法","transferFlag":0}},
     *              {"patient":{"acceptsStatus":1,"fixedTelephone":"18423456543","gmtCreated":1467955973000,"gmtModified":1469870105000,"id":14,"isDel":1,"linkPerson":"我的话","linkPhone":"0555-6132421","patientAge":"23","patientCardId":"340523456724562718","patientCode":"245619","patientHight":"187","patientName":"李四","patientSex":"男","receptionCode":"247188","receptionPerson":"文档","seeDoctorDate":1468215169000,"seeDoctorRecords":" 而我国仍","transferFlag":0}},
     *              {"patient":{"acceptsStatus":0,"fixedTelephone":"18423456543","gmtCreated":1469414680000,"gmtModified":1470204529000,"id":15,"isDel":1,"linkPerson":"而房价","linkPhone":"0555-6132421","patientAge":"34","patientCardId":"340523456724562718","patientCode":"243","patientHight":"187","patientName":"当初","patientSex":"男","receptionCode":"242778","receptionPerson":"服务","seeDoctorDate":1471076261000,"seeDoctorRecords":"人格女","transferDepartment":"","transferFlag":0,"transferHospital":"云山医院","transferOutpatient":"","transferType":"转院"}},
     *              {"patient":{"fixedTelephone":"","gmtCreated":1470107175000,"gmtModified":1470212494000,"id":37,"isDel":1,"linkPerson":"12315","linkPhone":"12306","patientAge":"55","patientCardId":"420625199805164523","patientCode":"23254","patientHight":"178","patientName":"的撒","patientSex":"男","transferDepartment":"","transferFlag":0,"transferHospital":"","transferOutpatient":"","transferType":"治愈"}},
     *              {"patient":{"fixedTelephone":"123456","gmtCreated":1470187420000,"gmtModified":1470187420000,"id":38,"isDel":1,"linkPerson":"13654657895","linkPhone":"123456789","patientAge":"39","patientCardId":"420628199504251152","patientCode":"242568","patientHight":"178","patientName":"赵四","patientSex":"男"}}
     *          ]
     *      }
     */
    public void getAllPatient() {
        RequestParams params = new RequestParams();
        params.put(JsonKey.mark, "getAllPatient");

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
                        msg.what = GET_All_PATIENTS_RESULT;
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

    private List<JieguoNotifyInfo> itemsOfJieguoNotifyInfo= new ArrayList<JieguoNotifyInfo>();
    public List<JieguoNotifyInfo> getItemsOfJieguoNotifyInfo(){
        return itemsOfJieguoNotifyInfo;
    }

    /**
     * 获取结果提醒列表（检验、检查）
     *      * 测试例子：
     *      http://192.168.10.142:8080/treat/app/treat.html?mark=checkAndImagList
     *
     *      {"result":1,"firstList":[{"id":33,"name":"大亲爱","time":"2016-08-25","type":"2"}]}
     */
    public void getCheckAndImagList() {
        RequestParams params = new RequestParams();
        params.put(JsonKey.mark, "checkAndImagList");

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
                        msg.what = GET_CHECK_AND_IMAGE_LIST_RESULT;
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
     * 通过医生ID获取创伤科所有病人
     * 测试例子：
     *      http://192.168.10.142:8080/treat/app/treat.html?mark=getAllPatient&doctorCode=006
     *
     *      {"result":1,
     *      "patientList":
     *          [
     *              {"patient":{"acceptsStatus":0,"fixedTelephone":"18423456543","gmtCreated":1468032280000,"gmtModified":1470278321000,"id":1,"isDel":1,"linkPerson":"王","linkPhone":"0555-6132421","patientAge":"23","patientCardId":"340523456724562718","patientCode":"123","patientHight":"187","patientName":"小刚2","patientSex":"男","receptionCode":"243649","receptionPerson":"小智","seeDoctorDate":1467427464000,"seeDoctorRecords":"不咋地啊","transferDepartment":"","transferFlag":1,"transferHospital":"","transferOutpatient":"","transferType":"1"}},
     *              {"patient":{"acceptsStatus":1,"fixedTelephone":"18423456543","gmtCreated":1467351173000,"gmtModified":1470273615000,"id":2,"isDel":1,"linkPerson":"万发","linkPhone":"0555-6132421","patientAge":"24","patientCardId":"340523456724562718","patientCode":"456","patientHight":"187","patientName":"小花2","patientSex":"男","receptionCode":"245675","receptionPerson":"张三","seeDoctorDate":1467696769000,"seeDoctorRecords":"让我感到","transferDepartment":"","transferFlag":0,"transferHospital":"","transferOutpatient":"","transferType":"2"}},
     *              {"patient":{"acceptsStatus":0,"fixedTelephone":"18423456543","gmtCreated":1468550680000,"gmtModified":1470274223000,"id":4,"isDel":1,"linkPerson":"王小鱼","linkPhone":"0555-6132421","patientAge":"42","patientCardId":"340523456724562718","patientCode":"789","patientHight":"187","patientName":"是否","patientSex":"男","receptionCode":"243640","receptionPerson":"小智","seeDoctorDate":1468723464000,"seeDoctorRecords":"各地v额","transferDepartment":"","transferFlag":1,"transferHospital":"","transferOutpatient":"","transferType":"治愈"}},
     *              {"patient":{"acceptsStatus":1,"fixedTelephone":"18423456543","gmtCreated":1467783173000,"gmtModified":1470275716000,"id":5,"isDel":1,"linkPerson":"撒村","linkPhone":"0555-6132421","patientAge":"55","patientCardId":"340523456724562718","patientCode":"245654","patientHight":"187","patientName":"而无法","patientSex":"男","receptionCode":"245689","receptionPerson":"阿萨德","seeDoctorDate":1467955969000,"seeDoctorRecords":"外人无法","transferDepartment":"","transferFlag":1,"transferHospital":"","transferOutpatient":"阿萨德啊","transferType":"留观"}},
     *              {"patient":{"acceptsStatus":1,"fixedTelephone":"18423456543","gmtCreated":1467955973000,"gmtModified":1470226921000,"id":6,"isDel":1,"linkPerson":"上次","linkPhone":"0555-6132421","patientAge":"23","patientCardId":"340523456724562718","patientCode":"245619","patientHight":"187","patientName":"王道","patientSex":"男","receptionCode":"247188","receptionPerson":"文档","seeDoctorDate":1468215169000,"seeDoctorRecords":" 而我国仍","transferDepartment":"","transferFlag":0,"transferHospital":"","transferOutpatient":"","transferType":"2"}},
     *              {"patient":{"acceptsStatus":0,"fixedTelephone":"18423456543","gmtCreated":1467254680000,"gmtModified":1470223758000,"id":7,"isDel":1,"linkPerson":"二层","linkPhone":"0555-6132421","patientAge":"45","patientCardId":"340523456724562718","patientCode":"234589","patientHight":"187","patientName":"当初","patientSex":"男","receptionCode":"242778","receptionPerson":"服务","seeDoctorDate":1468118664000,"seeDoctorRecords":"人格女","transferFlag":0}},
     *              {"patient":{"acceptsStatus":1,"fixedTelephone":"18423456543","gmtCreated":1467341080000,"gmtModified":1470222787000,"id":8,"isDel":1,"linkPerson":"二分法","linkPhone":"0555-6132421","patientAge":"24","patientCardId":"340523456724562718","patientCode":"234524","patientHight":"187","patientName":"是否","patientSex":"男","receptionCode":"243640","receptionPerson":"小智","seeDoctorDate":1468723464000,"seeDoctorRecords":"各地v额","transferDepartment":"","transferFlag":0,"transferHospital":"吴市吹箫","transferOutpatient":"","transferType":"转ICU"}},
     *              {"patient":{"acceptsStatus":1,"fixedTelephone":"18423456543","gmtCreated":1468301573000,"gmtModified":1470275751000,"id":9,"isDel":1,"linkPerson":"王竹","linkPhone":"0555-6132421","patientAge":"53","patientCardId":"340523456724562718","patientCode":"245654","patientHight":"187","patientName":"李强","patientSex":"男","receptionCode":"245689","receptionPerson":"阿萨德","seeDoctorDate":1467955969000,"seeDoctorRecords":"外人无法","transferDepartment":"","transferFlag":1,"transferHospital":"","transferOutpatient":"","transferType":"治愈"}},
     *              {"patient":{"acceptsStatus":0,"fixedTelephone":"18423456543","gmtCreated":1467955973000,"gmtModified":1470226922000,"id":10,"isDel":1,"linkPerson":"舞蹈服","linkPhone":"0555-6132421","patientAge":"23","patientCardId":"340523456724562718","patientCode":"123456","patientHight":"187","patientName":"小刚","patientSex":"女","receptionCode":"247188","receptionPerson":"文档","seeDoctorDate":1468215169000,"seeDoctorRecords":" 而我国仍","transferDepartment":"","transferFlag":0,"transferHospital":"","transferOutpatient":"","transferType":"2"}},
     *              {"patient":{"acceptsStatus":1,"fixedTelephone":"18423456543","gmtCreated":1467254680000,"gmtModified":1470275454000,"id":11,"isDel":1,"linkPerson":"王竹","linkPhone":"0555-6132421","patientAge":"25","patientCardId":"340523456724562718","patientCode":"234557","patientHight":"187","patientName":"当初","patientSex":"男","receptionCode":"242778","receptionPerson":"服务","seeDoctorDate":1468118664000,"seeDoctorRecords":"人格女","transferDepartment":"","transferFlag":0,"transferHospital":"","transferOutpatient":"","transferType":"2"}},
     *              {"patient":{"acceptsStatus":0,"fixedTelephone":"18423456543","gmtCreated":1469069080000,"gmtModified":1470223154000,"id":12,"isDel":1,"linkPerson":"数据","linkPhone":"0555-6132421","patientAge":"24","patientCardId":"340523456724562718","patientCode":"234987","patientHight":"187","patientName":"是否","patientSex":"男","receptionCode":"243640","receptionPerson":"小智","seeDoctorDate":1468723464000,"seeDoctorRecords":"各地v额","transferFlag":0}},
     *              {"patient":{"acceptsStatus":0,"fixedTelephone":"18423456543","gmtCreated":1467783173000,"gmtModified":1469845736000,"id":13,"isDel":1,"linkPerson":"小姑娘","linkPhone":"0555-6132421","patientAge":"34","patientCardId":"340523456724562718","patientCode":"245609","patientHight":"187","patientName":"张麻","patientSex":"男","receptionCode":"245689","receptionPerson":"阿萨德","seeDoctorDate":1467955969000,"seeDoctorRecords":"外人无法","transferFlag":0}},
     *              {"patient":{"acceptsStatus":1,"fixedTelephone":"18423456543","gmtCreated":1467955973000,"gmtModified":1469870105000,"id":14,"isDel":1,"linkPerson":"我的话","linkPhone":"0555-6132421","patientAge":"23","patientCardId":"340523456724562718","patientCode":"245619","patientHight":"187","patientName":"李四","patientSex":"男","receptionCode":"247188","receptionPerson":"文档","seeDoctorDate":1468215169000,"seeDoctorRecords":" 而我国仍","transferFlag":0}},
     *              {"patient":{"acceptsStatus":0,"fixedTelephone":"18423456543","gmtCreated":1469414680000,"gmtModified":1470204529000,"id":15,"isDel":1,"linkPerson":"而房价","linkPhone":"0555-6132421","patientAge":"34","patientCardId":"340523456724562718","patientCode":"243","patientHight":"187","patientName":"当初","patientSex":"男","receptionCode":"242778","receptionPerson":"服务","seeDoctorDate":1471076261000,"seeDoctorRecords":"人格女","transferDepartment":"","transferFlag":0,"transferHospital":"云山医院","transferOutpatient":"","transferType":"转院"}},
     *              {"patient":{"fixedTelephone":"","gmtCreated":1470107175000,"gmtModified":1470212494000,"id":37,"isDel":1,"linkPerson":"12315","linkPhone":"12306","patientAge":"55","patientCardId":"420625199805164523","patientCode":"23254","patientHight":"178","patientName":"的撒","patientSex":"男","transferDepartment":"","transferFlag":0,"transferHospital":"","transferOutpatient":"","transferType":"治愈"}},
     *              {"patient":{"fixedTelephone":"123456","gmtCreated":1470187420000,"gmtModified":1470187420000,"id":38,"isDel":1,"linkPerson":"13654657895","linkPhone":"123456789","patientAge":"39","patientCardId":"420628199504251152","patientCode":"242568","patientHight":"178","patientName":"赵四","patientSex":"男"}}
     *          ]
     *      }
     */
    public void getAllPatient(String docId) {
        RequestParams params = new RequestParams();
        params.put(JsonKey.mark, "getAllPatient");
        params.put("doctorCode", docId);

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
                        msg.what = GET_All_PATIENTS_RESULT;
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

    private List<HuanzheStatus> itemsOfGuanzhuHuanzheStatus = new ArrayList<HuanzheStatus>();
    public List<HuanzheStatus> getItemsOfGuanzhuHuanzheStatus(){
        return itemsOfGuanzhuHuanzheStatus;
    }

    /**
     * 通过医生ID获取关注病人列表以及未读的检验和检查信息
     * 测试例子：
     *      http://192.168.10.142:8080/treat/app/treat.html?mark=getDRPBydoctorCode&doctorCode=001
     *
     *      {"result":1,
     *      "firstList":
     *          [
     *              {"noRead":{"patient":{"acceptsStatus":0,"fixedTelephone":"18423456543","gmtCreated":1467254680000,"gmtModified":1470223758000,"id":7,"isDel":1,"linkPerson":"二层","linkPhone":"0555-6132421","patientAge":"45","patientCardId":"340523456724562718","patientCode":"234589","patientHight":"187","patientName":"当初","patientSex":"男","receptionCode":"242778","receptionPerson":"服温泉","seeDoctorDate":1468118664000,"seeDoctorRecords":"人格女","transferFlag":0}}}]}
     *
     */
    public void getDRPBydoctorCode(String docId) {
        RequestParams params = new RequestParams();
        params.put(JsonKey.mark, "getDRPBydoctorCode");
        params.put("doctorCode", docId);

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
                        msg.what = GET_FOCUS_PATIENT_AND_UNREAD_INFO_BY_DOCTORID_RESULT;
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

    // 创伤记录
    private List<HuanzheJiuzhiInfo> itemsOfHuanzheJiuzhiInfo = new ArrayList<HuanzheJiuzhiInfo>();
    public List<HuanzheJiuzhiInfo> getItemsOfHuanzheJiuzhiInfo(){
        return itemsOfHuanzheJiuzhiInfo;
    }
    /**
     * 根据病人CODE与起止时间查询病人的创伤记录
     * 测试例子：
     *      http://192.168.10.142:8080/treat/app/treat.html?mark=getProcessRecordsBycodeAndtime&from=2016-07-01&to=2016-08-20&patientCode=456
     *
     *
     */
    public void getProcessRecordsBycodeAndtime(String patientCode,String startDate, String endDate) {
        RequestParams params = new RequestParams();
        params.put(JsonKey.mark, "getProcessRecordsBycodeAndtime");
        params.put(JsonKey.ItemHuanzheId, patientCode);
        params.put(JsonKey.ItemStartDate, startDate);
        params.put(JsonKey.ItemEndDate, endDate);

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
                        msg.what = GET_PROCESS_RECORDS_BY_CODE_AND_TIME_RESULT;
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

    private BingrenInfo bingrenInfo;
    public BingrenInfo getBingrenInfo(){
        return bingrenInfo;
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
    public void getPatientInfoByJiuzhenhao(String jiuzhenhao) {
        RequestParams params = new RequestParams();
        params.put(JsonKey.mark, "getPatientInfoByCode");
        params.put(JsonKey.ItemHuanzheId, jiuzhenhao);

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
    public void getPatientInfoByNFCCode(String NFCCode) {
        RequestParams params = new RequestParams();
        params.put(JsonKey.mark, "getPatientInfoByHandCode");
        params.put(JsonKey.ItemNFCId, NFCCode);

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


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case GET_All_PATIENTS_RESULT: // 获取创伤科所有病人
                    Bundle bundle = msg.getData();
                    String data = bundle.getString("data");
                    System.out.println(data);
                    if (data != null & !data.equals("")) {
                        try {
                            JSONObject object = new JSONObject(data);
                            int flag = object.optInt(JsonKey.result);
                            if (flag == 1) {
                                Toast.makeText(MainActivity.this, "获取所有病人信息成功！", Toast.LENGTH_SHORT).show();

                                itemsOfHuanzheStatus.clear();

                                //itemsOfHuanzheStatus， HuanzheStatus
                                String list = object.optString("finallList");
                                if (list != null && !"".equals(list)) {
                                    JSONArray array = object.optJSONArray("finallList");

                                    if(array != null && array.length() > 0) {
                                        for (int i = 0; i < array.length(); i++) {
                                            HuanzheStatus item = new HuanzheStatus(
                                                    array.optJSONObject(i).optJSONObject("patient"),
                                                    "1".equals(array.optJSONObject(i).optString("mark")));

                                            itemsOfHuanzheStatus.add(item);
                                        }
                                    }
                                }
                            } else {
                                CommonDialog dialog = new CommonDialog(
                                        MainActivity.this,
                                        R.style.userDialog,
                                        "获取所有病人信息失败！",
                                        getResources().getString(R.string.confirm_label));
                                dialog.show();
                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }

                    //更新
                    if (fragment_huanzhezhongxin != null && fragmentIndex == 1) {
                        ((FragmentUpdateview) fragment_huanzhezhongxin).fragmentUpdateview();
                    }
                    break;

                case MODIFY_FOCUS_RESULT: // 通过医生ID、病人ID来改变关注关系
                    bundle = msg.getData();
                    data = bundle.getString("data");
                    System.out.println(data);
                    if (data != null & !data.equals("")) {
                        try {
                            JSONObject object = new JSONObject(data);
                            int flag = object.optInt(JsonKey.result);
                            if (flag == 1) {
                                Toast.makeText(MainActivity.this, "修改关注状态成功！", Toast.LENGTH_SHORT).show();

                                //{"result":1,"isFocus":1,"patientCode":"789","doctorCode":"006"}
                                boolean newStatus = false;
                                String patientCode;
                                String docCode;

                                int tempSatus = object.optInt("isFocus", 0);
                                if(1 == tempSatus)
                                    newStatus = true;
                                patientCode = object.optString("patientCode", "");
                                docCode = object.optString("doctorCode", "");

                                //更新
                                if (fragment_huanzhezhongxin != null) {
                                    ((FocusUpdateview) fragment_huanzhezhongxin).focusUpdateview(patientCode, newStatus);
                                }
                            } else {
                                CommonDialog dialog = new CommonDialog(
                                        MainActivity.this,
                                        R.style.userDialog,
                                        "修改关注状态失败！",
                                        getResources().getString(R.string.confirm_label));
                                dialog.show();
                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                    break;

                case GET_FOCUS_PATIENT_AND_UNREAD_INFO_BY_DOCTORID_RESULT: // 通过医生ID获取关注病人列表以及未读的检验和检查信息
                    bundle = msg.getData();
                    data = bundle.getString("data");
                    System.out.println(data);
                    if (data != null & !data.equals("")) {
                        try {
                            JSONObject object = new JSONObject(data);
                            int flag = object.optInt(JsonKey.result);
                            if (flag == 1) {
                                Toast.makeText(MainActivity.this, "获取关注病人信息成功！", Toast.LENGTH_SHORT).show();

                                itemsOfGuanzhuHuanzheStatus.clear();

                                //itemsOfHuanzheStatus， HuanzheStatus
                                String list = object.optString("focusList");
                                if (list != null && !"".equals(list)) {
                                    JSONArray array = object.optJSONArray("focusList");

                                    if(array != null && array.length() > 0) {
                                        for (int i = 0; i < array.length(); i++) {
                                            JSONArray unReadArray = array.getJSONObject(i).optJSONObject("focusPatient").optJSONArray("noReadItem");
                                            JSONObject patientObject = array.getJSONObject(i).optJSONObject("focusPatient").optJSONObject("patient");
                                            if(patientObject != null) {
                                                HuanzheStatus item = new HuanzheStatus(
                                                        unReadArray,
                                                        patientObject);

                                                itemsOfGuanzhuHuanzheStatus.add(item);
                                            }
                                        }
                                    }
                                }
                            } else {
                                CommonDialog dialog = new CommonDialog(
                                        MainActivity.this,
                                        R.style.userDialog,
                                        "获取关注病人信息失败！",
                                        getResources().getString(R.string.confirm_label));
                                dialog.show();
                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }

                    //更新
                    if (fragment_guanzhuliebian != null && fragmentIndex == 0) {
                        ((FragmentUpdateview) fragment_guanzhuliebian).fragmentUpdateview();
                    }
                    break;

                case GET_CHECK_AND_IMAGE_LIST_RESULT: // 获取结果提醒列表（检验、检查）
                    bundle = msg.getData();
                    data = bundle.getString("data");
                    System.out.println(data);
                    if (data != null & !data.equals("")) {
                        try {
                            JSONObject object = new JSONObject(data);
                            int flag = object.optInt(JsonKey.result);
                            if (flag == 1) {
                                Toast.makeText(MainActivity.this, "获取结果提醒信息成功！", Toast.LENGTH_SHORT).show();

                                itemsOfJieguoNotifyInfo.clear();

                                String list = object.optString("firstList");
                                if (list != null && !"".equals(list)) {
                                    JSONArray array = object.optJSONArray("firstList");

                                    if(array != null && array.length() > 0) {
                                        for (int i = 0; i < array.length(); i++) {
                                            JieguoNotifyInfo item = new JieguoNotifyInfo(array.getJSONObject(i));

                                            itemsOfJieguoNotifyInfo.add(item);
                                        }
                                    }
                                }

                                //更新
                                if (fragment_jieguotixing != null && fragmentIndex == 3) {
                                    ((FragmentUpdateview) fragment_jieguotixing).fragmentUpdateview();
                                }
                            } else {
                                CommonDialog dialog = new CommonDialog(
                                        MainActivity.this,
                                        R.style.userDialog,
                                        "获取结果提醒信息失败！",
                                        getResources().getString(R.string.confirm_label));
                                dialog.show();
                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                    break;

                case GET_PROCESS_RECORDS_BY_CODE_AND_TIME_RESULT: // 根据病人CODE与起止时间查询病人的创伤记录
                    bundle = msg.getData();
                    data = bundle.getString("data");
                    System.out.println(data);
                    if (data != null & !data.equals("")) {
                        try {
                            JSONObject object = new JSONObject(data);
                            int flag = object.optInt(JsonKey.result);
                            if (flag == 1) {
                                Toast.makeText(MainActivity.this, "获取病人创伤记录成功！", Toast.LENGTH_SHORT).show();

                                itemsOfHuanzheJiuzhiInfo.clear();

                                String list = object.optString("fristArray");
                                if (list != null && !"".equals(list)) {
                                    JSONArray array = object.optJSONArray("fristArray");

                                    if(array != null && array.length() > 0) {
                                        for (int i = 0; i < array.length(); i++) {
                                            HuanzheJiuzhiInfo item = new HuanzheJiuzhiInfo(array.getJSONObject(i).optJSONObject("ps"));

                                            itemsOfHuanzheJiuzhiInfo.add(item);
                                        }
                                    }
                                }

                                //更新
                                if (fragment_chuangshanjilu != null && fragmentIndex == 2) {
                                    ((FragmentUpdateview) fragment_chuangshanjilu).fragmentUpdateview();
                                }

                            } else {
                                CommonDialog dialog = new CommonDialog(
                                        MainActivity.this,
                                        R.style.userDialog,
                                        "获取病人创伤记录失败！",
                                        getResources().getString(R.string.confirm_label));
                                dialog.show();
                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                    break;

                case MSG_GET_PATIENT_INFO_BY_JIUZHENHAO_RESULT: // 根据就诊号获取病人信息
                    bundle = msg.getData();
                    data = bundle.getString("data");
                    System.out.println(data);
                    if (data != null & !data.equals("")) {
                        try {
                            JSONObject object = new JSONObject(data);
                            int flag = object.optInt(JsonKey.result);
                            if (flag == 1) {
                                Toast.makeText(MainActivity.this, "获取病人信息成功！", Toast.LENGTH_SHORT).show();

                                JSONObject patient = object.getJSONObject("patient");
                                bingrenInfo = new BingrenInfo(patient);

                                //更新
                                if (fragment_chuangshanjilu != null) {
                                    ((FragmentUpdateBingrenView) fragment_chuangshanjilu).fragemntUpdateBingrenInfo(bingrenInfo);
                                }
                            }else {
                                CommonDialog dialog = new CommonDialog(
                                        MainActivity.this,
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

                case MSG_GET_PATIENT_INFO_BY_NFCCODE_RESULT: // 根据手环获取病人信息
                    bundle = msg.getData();
                    data = bundle.getString("data");
                    System.out.println(data);
                    if (data != null & !data.equals("")) {
                        try {
                            JSONObject object = new JSONObject(data);
                            int flag = object.optInt(JsonKey.result);
                            if (flag == 1) {
                                Toast.makeText(MainActivity.this, "获取手环绑定的病人信息成功！", Toast.LENGTH_SHORT).show();

                                JSONObject patient = object.getJSONObject("patient");
                                bingrenInfo = new BingrenInfo(patient);

                                //更新
                                if (fragment_chuangshanjilu != null) {
                                    ((FragmentUpdateBingrenView) fragment_chuangshanjilu).fragemntUpdateBingrenInfoByNFC(bingrenInfo);
                                }
                            }else {
                                CommonDialog dialog = new CommonDialog(
                                        MainActivity.this,
                                        R.style.userDialog,
                                        "尚未绑定病人！",
                                        getResources().getString(R.string.confirm_label));
                                dialog.show();
                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                    break;

                case 50:
                    bundle = msg.getData();
                    data = bundle.getString("data");
                    System.out.println(data);
                    if (data != null & !data.equals("")) {
                        try {
                            JSONObject object = new JSONObject(data);
                            int flag = object.optInt(JsonKey.result);
                            if (flag == 1) {
                                Toast.makeText(MainActivity.this, "获取所有病人信息成功！", Toast.LENGTH_SHORT).show();

                            } else {
                                CommonDialog dialog = new CommonDialog(
                                        MainActivity.this,
                                        R.style.userDialog,
                                        "获取所有病人信息失败！",
                                        getResources().getString(R.string.confirm_label));
                                dialog.show();
                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                    break;
            }
        }
    };

    public interface FragmentUpdateview{
        public void fragmentUpdateview();
    }

    public interface FocusUpdateview{
        public void focusUpdateview(String patientCode, boolean newStatus);
    }

    public interface FragmentUpdateBingrenView{
        public void fragemntUpdateBingrenInfo(BingrenInfo bingrenInfo);
        public void fragemntUpdateBingrenInfoByNFC(BingrenInfo bingrenInfo);
    }

    public interface FragmentNFCNotify{
        public void foundNFCCODE(String nfcCode);
    }

    /**
     * NFC 相关处理
     */
    private String NFCCode;

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
    public void onScanned(String decodedData, BARCODE.TYPE barcodeType, int length) {
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

            // 通知当前的那个Fragment
            switch (this.fragmentIndex){
                case 0:
                    if (fragment_guanzhuliebian != null) {
                        ((FragmentNFCNotify) fragment_guanzhuliebian).foundNFCCODE(this.NFCCode);
                    }
                    break;
                case 1:
                    if (fragment_huanzhezhongxin != null) {
                        ((FragmentNFCNotify) fragment_huanzhezhongxin).foundNFCCODE(this.NFCCode);
                    }
                    break;
                case 2:
                    if (fragment_chuangshanjilu != null) {
                        ((FragmentNFCNotify) fragment_chuangshanjilu).foundNFCCODE(this.NFCCode);
                    }
                    break;
                case 3:
                    if (fragment_jieguotixing != null) {
                        ((FragmentNFCNotify) fragment_jieguotixing).foundNFCCODE(this.NFCCode);
                    }
                    break;
            }

        } catch (NullPointerException localNullPointerException) {

        }
        return str1;
    }
}

