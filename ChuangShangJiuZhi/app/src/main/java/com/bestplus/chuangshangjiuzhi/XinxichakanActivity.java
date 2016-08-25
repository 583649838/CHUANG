package com.bestplus.chuangshangjiuzhi;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bestplus.chuangshangjiuzhi.application.AppStatus;
import com.bestplus.chuangshangjiuzhi.common.JsonKey;
import com.bestplus.chuangshangjiuzhi.common.Variable;
import com.bestplus.chuangshangjiuzhi.dialog.CommonDialog;
import com.bestplus.chuangshangjiuzhi.dialog.CustomProgressDialog;
import com.bestplus.chuangshangjiuzhi.entity.BingrenInfo;
import com.bestplus.chuangshangjiuzhi.entity.HuanzheJiuzhiInfo;
import com.bestplus.chuangshangjiuzhi.util.DebugLog;
import com.bestplus.chuangshangjiuzhi.util.HttpClientRequest;
import com.bestplus.chuangshangjiuzhi.view.MyTitleView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by maoamo on 2016/7/27.
 */
public class XinxichakanActivity extends Activity implements View.OnClickListener {
    private final static int DOWNLOAD_AUDIO_RESULT_START = 11;
    private final static int MSG_GET_PATIENT_INFO_BY_JIUZHENHAO_RESULT = 201;

    private HuanzheJiuzhiInfo huanzheJiuzhiInfo;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private TextView editText_xingming, editText_xingbie, editText_nianling, editText_jiuzhenhao;
    private ImageView imageView_pic1, imageView_pic2, imageView_pic3;

    private TextView textView15, textView18, textView28;

    private RelativeLayout relativeLayout_audio_1, relativeLayout_audio_2, relativeLayout_audio_3;
    private ImageView imageView_audio_1,imageView_audio_2, imageView_audio_3;
    private TextView textView_audio_1, textView_audio_2, textView_audio_3;
    private MediaPlayer mPlayer;
    private String audioFileName1 = "audio1.3gp", audioFileName2 = "audio2.3gp", audioFileName3 = "audio3.3gp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xinxichakan);

        Intent intent = this.getIntent();
        huanzheJiuzhiInfo = (HuanzheJiuzhiInfo)intent.getSerializableExtra("HuanzheJiuzhiInfo");

        if(huanzheJiuzhiInfo != null){
            System.out.println("id: " + huanzheJiuzhiInfo.getItemHuanzheId());
            System.out.println("name: " + huanzheJiuzhiInfo.getItemHuanzheName());
        }

        initTitle();
        initView();
        initViewHandler();
        initData();
    }

    private void initView() {
        editText_xingming = (TextView) findViewById(R.id.editText_xingming);
        editText_xingbie = (TextView) findViewById(R.id.editText_xingbie);
        editText_nianling = (TextView) findViewById(R.id.editText_nianling);
        editText_jiuzhenhao = (TextView) findViewById(R.id.editText_jiuzhenhao);

        textView15 = (TextView) findViewById(R.id.textView15);
        textView18 = (TextView) findViewById(R.id.textView18);
        textView28 = (TextView) findViewById(R.id.textView28);

        imageView_pic1 = (ImageView) findViewById(R.id.imageView_pic1);
        imageView_pic2 = (ImageView) findViewById(R.id.imageView_pic2);
        imageView_pic3 = (ImageView) findViewById(R.id.imageView_pic3);

        relativeLayout_audio_1 = (RelativeLayout) findViewById(R.id.relativeLayout_audio_1);
        relativeLayout_audio_2 = (RelativeLayout) findViewById(R.id.relativeLayout_audio_2);
        relativeLayout_audio_3 = (RelativeLayout) findViewById(R.id.relativeLayout_audio_3);
        imageView_audio_1 = (ImageView) findViewById(R.id.imageView_audio_1);
        imageView_audio_2 = (ImageView) findViewById(R.id.imageView_audio_2);
        imageView_audio_3 = (ImageView) findViewById(R.id.imageView_audio_3);
        textView_audio_1 = (TextView) findViewById(R.id.textView_audio_1);
        textView_audio_2 = (TextView) findViewById(R.id.textView_audio_2);
        textView_audio_3 = (TextView) findViewById(R.id.textView_audio_3);

        if(huanzheJiuzhiInfo != null){
            editText_jiuzhenhao.setText(huanzheJiuzhiInfo.getItemHuanzheId());
            editText_xingming.setText(huanzheJiuzhiInfo.getItemHuanzheName());
            textView15.setText(huanzheJiuzhiInfo.getItemJiuzhiCaozuoren());
            textView18.setText(huanzheJiuzhiInfo.getItemJiuzhiShijian());
            textView28.setText("\t\t" + huanzheJiuzhiInfo.getItemJiuzhiJianyaoJilu());
        }
    }

    private void updateBingrenView(){
        if(bingrenInfo != null){
            editText_jiuzhenhao.setText(bingrenInfo.getItemHuanzheId());
            editText_xingming.setText(bingrenInfo.getItemHuanzheName());
            editText_xingbie.setText(bingrenInfo.getItemXingbie());
            editText_nianling.setText(bingrenInfo.getItemNianling());

            textView28.setText("\t\t" + huanzheJiuzhiInfo.getItemJiuzhiJianyaoJilu());
        }
    }

    private void initViewHandler() {
//        imageView_audio_1.setOnClickListener(this);
//        imageView_audio_2.setOnClickListener(this);
//        imageView_audio_3.setOnClickListener(this);
    }

    private void initTitle() {
        // TODO Auto-generated method stub
        MyTitleView titleView = new MyTitleView(
                (RelativeLayout) findViewById(R.id.title_layout),
                getString(R.string.main_xinxiluru),
                true, false);

        titleView.getLeftImageView().setOnClickListener(this);
        titleView.getRightTextView().setOnClickListener(this);
    }

    private void initData() {
        if (huanzheJiuzhiInfo != null && huanzheJiuzhiInfo.getItemPicFile1() != null && !huanzheJiuzhiInfo.getItemPicFile1().equals(""))
            imageLoader.displayImage(
                    Variable.server + "/" + huanzheJiuzhiInfo.getItemPicFile1(),
                    imageView_pic1,
                    AppStatus.options);
        else
            imageView_pic1.setVisibility(View.GONE);

        if (huanzheJiuzhiInfo != null && huanzheJiuzhiInfo.getItemPicFile2() != null && !huanzheJiuzhiInfo.getItemPicFile2().equals(""))
            imageLoader.displayImage(
                    Variable.server + "/" + huanzheJiuzhiInfo.getItemPicFile2(),
                    imageView_pic2,
                    AppStatus.options);
        else
            imageView_pic2.setVisibility(View.GONE);

        if (huanzheJiuzhiInfo != null && huanzheJiuzhiInfo.getItemPicFile3() != null && !huanzheJiuzhiInfo.getItemPicFile3().equals(""))
            imageLoader.displayImage(
                    Variable.server + "/" + huanzheJiuzhiInfo.getItemPicFile3(),
                    imageView_pic3,
                    AppStatus.options);
        else
            imageView_pic3.setVisibility(View.GONE);

        if (huanzheJiuzhiInfo != null && huanzheJiuzhiInfo.getItemAudFile1() != null && !huanzheJiuzhiInfo.getItemAudFile1().equals("")){
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    downLoadFile(Variable.server + "/" + huanzheJiuzhiInfo.getItemAudFile1(),audioFileName1, 1);
                }
            }).start();
        }
        else
            relativeLayout_audio_1.setVisibility(View.GONE);

        if (huanzheJiuzhiInfo != null && huanzheJiuzhiInfo.getItemAudFile2() != null && !huanzheJiuzhiInfo.getItemAudFile2().equals("")){
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    downLoadFile(Variable.server + "/" + huanzheJiuzhiInfo.getItemAudFile2(),audioFileName2, 2);
                }
            }).start();
        }
        else
            relativeLayout_audio_2.setVisibility(View.GONE);

        if (huanzheJiuzhiInfo != null && huanzheJiuzhiInfo.getItemAudFile3() != null && !huanzheJiuzhiInfo.getItemAudFile3().equals("")){
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    downLoadFile(Variable.server + "/" + huanzheJiuzhiInfo.getItemAudFile3(),audioFileName3, 3);
                }
            }).start();
        }
        else
            relativeLayout_audio_3.setVisibility(View.GONE);

        getPatientInfoByJiuzhenhao(huanzheJiuzhiInfo.getItemHuanzheId());
    }

    /**
     * @Title: downLoadFile
     * @Description:文件下载
     * @throws
     */
    private String downLoadFile(String downloadFilePath, String fileName, int id) { // Variable.server + "/" +
        String filePath = null;
        HttpClient httpClient1 = new DefaultHttpClient();
//        HttpGet httpGet1 = new HttpGet(
//                "http://192.168.31.144:10010/MINATest/vioce/strikefreedom.jpg");
        HttpGet httpGet1 = new HttpGet(downloadFilePath);
        try {
            HttpResponse httpResponse1 = httpClient1.execute(httpGet1);

            StatusLine statusLine = httpResponse1.getStatusLine();
            if (statusLine.getStatusCode() == 200) {
                filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + fileName; // 文件路径

                System.out.println("filePath: " + filePath);

                File file = new File(filePath);
                FileOutputStream outputStream = new FileOutputStream(file);
                InputStream inputStream = httpResponse1.getEntity().getContent();
                byte b[] = new byte[1024];
                int j = 0;
                while ((j = inputStream.read(b)) != -1) {
                    outputStream.write(b, 0, j);
                }
                outputStream.flush();
                outputStream.close();

                Message msg = new Message();
                msg.what = DOWNLOAD_AUDIO_RESULT_START + id;
                handler.sendMessage(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpClient1.getConnectionManager().shutdown();
        }

        return filePath;
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


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case MSG_GET_PATIENT_INFO_BY_JIUZHENHAO_RESULT: // 根据就诊号获取病人信息
                    Bundle bundle = msg.getData();
                    String data = bundle.getString("data");
                    System.out.println(data);
                    if (data != null & !data.equals("")) {
                        try {
                            JSONObject object = new JSONObject(data);
                            int flag = object.optInt(JsonKey.result);
                            if (flag == 1) {
                                Toast.makeText(XinxichakanActivity.this, "获取病人信息成功！", Toast.LENGTH_SHORT).show();

                                JSONObject patient = object.getJSONObject("patient");
                                bingrenInfo = new BingrenInfo(patient);

                                //更新
                                updateBingrenView();
                            }else {
                                CommonDialog dialog = new CommonDialog(
                                        XinxichakanActivity.this,
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

                case DOWNLOAD_AUDIO_RESULT_START + 1:
                    System.out.println("-------------1------------");
                    enableAudioFilePlay1();
                    break;
                case DOWNLOAD_AUDIO_RESULT_START + 2:
                    System.out.println("-------------2------------");
                    enableAudioFilePlay2();
                    break;
                case DOWNLOAD_AUDIO_RESULT_START + 3:
                    System.out.println("-------------3------------");
                    enableAudioFilePlay3();
                    break;
            }
        }
    };

    private void enableAudioFilePlay1(){
        textView_audio_1.setOnClickListener(this);
        imageView_audio_1.setOnClickListener(this);
    }

    private void enableAudioFilePlay2(){
        textView_audio_2.setOnClickListener(this);
        imageView_audio_2.setOnClickListener(this);
    }

    private void enableAudioFilePlay3(){
        textView_audio_3.setOnClickListener(this);
        imageView_audio_3.setOnClickListener(this);
    }

    public boolean playing = false;
    public void audioStartPlay(String path) {
        System.out.println("-------------start------------ path: " + path);
        System.out.println("-------------start------------ playing: " + playing);



        try {
            mPlayer = new MediaPlayer();

            //设置要播放的文件
            mPlayer.setDataSource(path);
            mPlayer.prepare();
            //播放
            mPlayer.start();
            playing = true;
        }catch(Exception e){
            System.out.println("prepare() failed");
        }
    }

    public void audioStopPlay() {
        System.out.println("-------------stop------------");

        mPlayer.stop();
        mPlayer.release();
        mPlayer = null;

        playing = false;

        imageView_audio_1.setEnabled(true);
        textView_audio_1.setEnabled(true);

        imageView_audio_2.setEnabled(true);
        textView_audio_2.setEnabled(true);

        imageView_audio_3.setEnabled(true);
        textView_audio_3.setEnabled(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

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

            case R.id.imageView_audio_1:
            case R.id.textView_audio_1:
                if(playing){
                    audioStopPlay();
                }

                imageView_audio_2.setEnabled(false);
                textView_audio_2.setEnabled(false);

                imageView_audio_3.setEnabled(false);
                textView_audio_3.setEnabled(false);

                audioStartPlay(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + audioFileName1);
                break;
            case R.id.imageView_audio_2:
            case R.id.textView_audio_2:
                if(playing){
                    audioStopPlay();
                }

                imageView_audio_1.setEnabled(false);
                textView_audio_1.setEnabled(false);

                imageView_audio_3.setEnabled(false);
                textView_audio_3.setEnabled(false);

                audioStartPlay(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + audioFileName2);
                break;
            case R.id.imageView_audio_3:
            case R.id.textView_audio_3:
                if(playing){
                    audioStopPlay();
                }

                imageView_audio_2.setEnabled(false);
                textView_audio_2.setEnabled(false);

                imageView_audio_1.setEnabled(false);
                textView_audio_1.setEnabled(false);

                audioStartPlay(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + audioFileName3);
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

        if(playing){
            audioStopPlay();
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
}
