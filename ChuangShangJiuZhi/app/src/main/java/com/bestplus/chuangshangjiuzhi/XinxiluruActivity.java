package com.bestplus.chuangshangjiuzhi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaRecorder;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcF;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bestplus.chuangshangjiuzhi.common.JsonKey;
import com.bestplus.chuangshangjiuzhi.common.Variable;
import com.bestplus.chuangshangjiuzhi.dialog.CommonDialog;
import com.bestplus.chuangshangjiuzhi.dialog.CustomProgressDialog;
import com.bestplus.chuangshangjiuzhi.entity.BingrenInfo;
import com.bestplus.chuangshangjiuzhi.entity.Hotwords;
import com.bestplus.chuangshangjiuzhi.entity.HuanzheJiuzhiInfo;
import com.bestplus.chuangshangjiuzhi.util.DebugLog;
import com.bestplus.chuangshangjiuzhi.util.HttpClientRequest;
import com.bestplus.chuangshangjiuzhi.util.SocketHttpRequester;
import com.bestplus.chuangshangjiuzhi.util.Utility;
import com.bestplus.chuangshangjiuzhi.view.MyTitleView;
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

public class XinxiluruActivity extends Activity implements OnClickListener, MiBcrListener {
    private final static int GET_HOTWORDS_RESULT = 10;
    private final static int UPLOAD_RECORD_RESULT = 11;
    private final static int MSG_GET_PATIENT_INFO_BY_NFCCODE_RESULT = 12;

    private TextView editText_xingming, editText_xingbie, editText_nianling, editText_jiuzhenhao;
    private EditText editText5;

    private static final List<String> spinnerList1 = new ArrayList<String>();
    private static final List<String> spinnerList2 = new ArrayList<String>();
    private List<String> data_list = new ArrayList<String>();;
    private Spinner spinner1, spinner2;
    private ArrayAdapter<String> adapter1, adapter2;

    private ImageView imageView_paizhao, imageView_getImg, imageView_delImg;
    private ImageView imageView_pic1, imageView_pic2, imageView_pic3;
    private ImageView imageView_record_1, imageView_record_2, imageView_record_3;
    private TextView textView_audio_1, textView_audio_2, textView_audio_3;
    private ImageView imageView_audio_del1, imageView_audio_del2, imageView_audio_del3;

    private MediaRecorder mediaRecorder;
    private File audioFile1, audioFile2, audioFile3;
    private boolean audioFileFlag1, audioFileFlag2, audioFileFlag3;
    private String serverAudioFilePath1, serverAudioFilePath2, serverAudioFilePath3;
    private boolean recording = false;

    private boolean imageFileFlage1, imageFileFlage2, imageFileFlage3;
    //private File destination1, destination2, destination3;
    private final String localImageFileDefaultName1 = "myPicture1.jpg";
    private final String localImageFileDefaultName2 = "myPicture2.jpg";
    private final String localImageFileDefaultName3 = "myPicture3.jpg";
    private String localImageFilePath1, localImageFilePath2, localImageFilePath3;
    private String serverImageFilePath1, serverImageFilePath2, serverImageFilePath3;
    private String imageFilePathPrefix;
    private int imageFileCount = 0;

    private Button button_upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xinxiluru);

        initTitle();
        initView();
        initViewHandler();
        initData();

        initNFC();
    }

    private void initView() {
        editText_xingming = (TextView) findViewById(R.id.editText_xingming);
        editText_xingbie = (TextView) findViewById(R.id.editText_xingbie);
        editText_nianling = (TextView) findViewById(R.id.editText_nianling);
        editText_jiuzhenhao = (TextView) findViewById(R.id.editText_jiuzhenhao);

        editText5 = (EditText) findViewById(R.id.editText5);

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);

        imageView_paizhao = (ImageView) findViewById(R.id.imageView_paizhao);
        imageView_getImg = (ImageView) findViewById(R.id.imageView_getImg);
        imageView_delImg = (ImageView) findViewById(R.id.imageView_delImg);

        imageView_pic1 = (ImageView) findViewById(R.id.imageView_pic1);
        imageView_pic2 = (ImageView) findViewById(R.id.imageView_pic2);
        imageView_pic3 = (ImageView) findViewById(R.id.imageView_pic3);

        imageView_record_1 = (ImageView) findViewById(R.id.imageView_record1);
        imageView_record_2 = (ImageView) findViewById(R.id.imageView_record2);
        imageView_record_3 = (ImageView) findViewById(R.id.imageView_record3);

        imageView_audio_del1 = (ImageView) findViewById(R.id.imageView_del1);
        imageView_audio_del2 = (ImageView) findViewById(R.id.imageView_del2);
        imageView_audio_del3 = (ImageView) findViewById(R.id.imageView_del3);

        textView_audio_1 = (TextView) findViewById(R.id.textView_audio_1);
        textView_audio_2 = (TextView) findViewById(R.id.textView_audio_2);
        textView_audio_3 = (TextView) findViewById(R.id.textView_audio_3);

        button_upload = (Button) findViewById(R.id.button_upload);

        mediaRecorder = new MediaRecorder();
        //录音文件名
        audioFile1 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC), "myRecording1.3gp");
        audioFile2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC), "myRecording2.3gp");
        audioFile3 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC), "myRecording3.3gp");

        //照片文件名
        imageFilePathPrefix = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/";
//        destination1 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "myPicture1.jpg");
//        destination2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "myPicture2.jpg");
//        destination3 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "myPicture3.jpg");
    }

    private void updatePatientInfo(){
        if(bingrenInfo != null){
            editText_xingming.setText(bingrenInfo.getItemHuanzheName());
            editText_xingbie.setText(bingrenInfo.getItemXingbie());
            editText_nianling.setText(bingrenInfo.getItemNianling());
            editText_jiuzhenhao.setText(bingrenInfo.getItemHuanzheId());
        }
    }

    private void initSpinnerData(){
        if (itemsOfHotwords != null) {
            spinnerList1.clear();
            for (int i = 0; i < itemsOfHotwords.size(); i++) {
                spinnerList1.add(itemsOfHotwords.get(i).getWords());
            }
        }

        //将可选内容与ArrayAdapter连接起来
        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerList1);
        //设置下拉列表的风格
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter 添加到spinner中
        spinner1.setAdapter(adapter1);
        //添加事件Spinner事件监听
        spinner1.setOnItemSelectedListener(new SpinnerSelectedListener());
        //设置默认值
        spinner1.setVisibility(View.VISIBLE);

        //将可选内容与ArrayAdapter连接起来
        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerList2);
        //设置下拉列表的风格
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter 添加到spinner中
        spinner2.setAdapter(adapter2);
        //添加事件Spinner事件监听
        spinner2.setOnItemSelectedListener(new SpinnerSelectedListener2());
        //设置默认值
        spinner2.setVisibility(View.VISIBLE);
    }

    private void initViewHandler() {
        imageView_paizhao.setOnClickListener(this);
        imageView_getImg.setOnClickListener(this);
        imageView_delImg.setOnClickListener(this);

        imageView_record_1.setOnClickListener(this);
        imageView_record_2.setOnClickListener(this);
        imageView_record_3.setOnClickListener(this);
        imageView_audio_del1.setOnClickListener(this);
        imageView_audio_del2.setOnClickListener(this);
        imageView_audio_del3.setOnClickListener(this);

        button_upload.setOnClickListener(this);
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

    private void initData(){
        getHotWord();
    }

    private HuanzheJiuzhiInfo createUploadHuanzheJiuzhiInfo(){
        HuanzheJiuzhiInfo huanzheJiuzhiInfo = new HuanzheJiuzhiInfo();

        huanzheJiuzhiInfo.setItemHuanzheId(bingrenInfo.getItemHuanzheId());
        huanzheJiuzhiInfo.setItemHuanzheName(bingrenInfo.getItemHuanzheName());

        if (imageFileCount > 0) {
            int count = imageFileCount;
            if (count > 0) {
                serverImageFilePath1 = uploadFileNameMap.get(localImageFilePath1);
                System.out.println("serverImageFilePath1:" + serverImageFilePath1);
                huanzheJiuzhiInfo.setItemPicFile1(serverImageFilePath1);
                count--;
            }
            if (count > 0) {
                serverImageFilePath2 = uploadFileNameMap.get(localImageFilePath2);
                System.out.println("serverImageFilePath2:" + serverImageFilePath2);
                huanzheJiuzhiInfo.setItemPicFile2(serverImageFilePath2);
                count--;
            }
            if (count > 0) {
                serverImageFilePath3 = uploadFileNameMap.get(localImageFilePath3);
                System.out.println("serverImageFilePath3:" + serverImageFilePath3);
                huanzheJiuzhiInfo.setItemPicFile3(serverImageFilePath3);
                count--;
            }
        }

        if(audioFileFlag1) {
            serverAudioFilePath1 = uploadFileNameMap.get(audioFile1.getAbsolutePath());
            huanzheJiuzhiInfo.setItemAudFile1(serverAudioFilePath1);
        }
        if(audioFileFlag2) {
            serverAudioFilePath2 = uploadFileNameMap.get(audioFile2.getAbsolutePath());
            huanzheJiuzhiInfo.setItemAudFile2(serverAudioFilePath2);
        }
        if(audioFileFlag3) {
            serverAudioFilePath3 = uploadFileNameMap.get(audioFile3.getAbsolutePath());
            huanzheJiuzhiInfo.setItemAudFile3(serverAudioFilePath3);
        }

        huanzheJiuzhiInfo.setItemJiuzhiCaozuoren(Variable.cs_user.getMemberName());

        // itemsOfHotwords
        // spinnerPostion1 spinnerPostion2
        String jiluString1 = "一级处理：", jiluString2 = "二级处理：";
        int len = itemsOfHotwords.size();
        if(len > 0){
            if(spinnerPostion1 < len){
                Hotwords hotwords = itemsOfHotwords.get(spinnerPostion1);
                jiluString1 += hotwords.getWords();

                if(hotwords.getTwoLevelList() != null)
                    len = hotwords.getTwoLevelList().size();
                else
                    len = 0;
                if(len > 0){
                    if(spinnerPostion2 < len){
                        jiluString2 += hotwords.getTwoLevelList().get(spinnerPostion2).getWords();
                    }
                }
            }
        }
        String jiluString = editText5.getText().toString();
        huanzheJiuzhiInfo.setItemJiuzhiJianyaoJilu(jiluString1 + jiluString +jiluString2);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        String jiluDate=formatter.format(now);
        huanzheJiuzhiInfo.setItemJiuzhiShijian(jiluDate);

        return huanzheJiuzhiInfo;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 200 && resultCode == Activity.RESULT_OK) {
            saveImage(1);
        } else if (requestCode == 201 && resultCode == Activity.RESULT_OK) {
            saveImage(2);
        } else if (requestCode == 202 && resultCode == Activity.RESULT_OK) {
            saveImage(3);
        } else if (requestCode == 300 && resultCode == RESULT_OK && data != null) {

            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            System.out.println("picturePath : " + picturePath);// /storage/emulated/0/DCIM/maomao.jpg

            if (imageFileCount < 3)
                saveImage(imageFileCount + 1, picturePath);
        }
    }

    /**
     * 复制单个文件
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public void copyFile(String oldPath, String newPath) {
        System.out.println("oldPath : " + oldPath);// oldPath : /storage/emulated/0/DCIM/maomao.jpg
        System.out.println("newPath : " + newPath);// newPath : /storage/emulated/0/DCIM/myPicture1.jpg

        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                System.out.println("OLD FILE EXISTS");
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ( (byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
                fs.close();
            }else{
                System.out.println("OLD FILE NOT EXISTS");
            }
        }
        catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }

    }

    private void saveImage(int index, String selectedFile) {
        FileInputStream in;
        ImageView imageView;
        switch (index) {
            case 1:
                localImageFilePath1 = selectedFile;
                imageView = imageView_pic1;
                break;
            case 2:
                localImageFilePath2 = selectedFile;
                imageView = imageView_pic2;
                break;
            case 3:
                localImageFilePath3 = selectedFile;
                imageView = imageView_pic3;
                break;
            default:
                return;
        }

        imageFileCount ++;
        System.out.println("imageFileCount 1 : " + imageFileCount);
        System.out.println("selectedFile 1 : " + selectedFile);

        try {
            in = new FileInputStream(selectedFile);
            // 缩放图片, width, height 按相同比例缩放图片
            BitmapFactory.Options options = new BitmapFactory.Options();
            // options 设为true时，构造出的bitmap没有图片，只有一些长宽等配置信息，但比较快，设为false时，才有图片
            options.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeFile(selectedFile, options);
            System.out.println("bitmap 1 : width : " + options.outWidth + " height: " + options.outHeight + " type: " + options.outMimeType);
            int scale = (int)( options.outWidth / (float)486);
            if(scale <= 0)
                scale = 1;
            else
                scale = 4;

            scale = 4;

            options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            options.inSampleSize = scale; // 4;

            //Bitmap userImage = (Bitmap) data.getExtras().get("data");
            Bitmap userImage = BitmapFactory.decodeStream(in, null, options);

            if (userImage != null) {
                System.out.println("userImage 1: width : " + userImage.getWidth() + " height: " + userImage.getHeight() + " density: " + userImage.getDensity());
                imageView.setImageBitmap(userImage);
            }
        } catch (Exception e) {
            e.printStackTrace();

            imageFileCount --;
            System.out.println("save file fail -- imageFileCount 1 : " + imageFileCount);
            System.out.println("save file fail -- selectedFile 1 : " + selectedFile);

            Toast.makeText(XinxiluruActivity.this, "添加照片失败，文件不存在！", Toast.LENGTH_SHORT).show();
        }

    }

    private void saveImage(int index) {
        String file;
        ImageView imageView;
        switch (index) {
            case 1:
                file = localImageFilePath1;
                imageView = imageView_pic1;
                break;
            case 2:
                file = localImageFilePath2;
                imageView = imageView_pic2;
                break;
            case 3:
                file = localImageFilePath3;
                imageView = imageView_pic3;
                break;
            default:
                return;
        }
        try {
            imageFileCount++;
            System.out.println("imageFileCount 2 : " + imageFileCount);
            System.out.println("imageFile 2 : " + file.toString());

            FileInputStream in = new FileInputStream(file);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;

            //Bitmap userImage = (Bitmap) data.getExtras().get("data");
            Bitmap userImage = BitmapFactory.decodeStream(in, null, options);

            if (userImage != null) {
                System.out.println("userImage 2: width : " + userImage.getWidth() + " height: " + userImage.getHeight() + " density: " + userImage.getDensity());
                imageView.setImageBitmap(userImage);
            }
        } catch (Exception e) {
            e.printStackTrace();

            imageFileCount --;
            System.out.println("save file fail -- imageFileCount 1 : " + imageFileCount);
            System.out.println("save file fail -- selectedFile 1 : " + file);

            Toast.makeText(XinxiluruActivity.this, "添加照片失败，文件不存在！", Toast.LENGTH_SHORT).show();
        }
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

            case R.id.button_upload:
                System.out.println("R.id.button_upload ...");

                if(bingrenInfo != null) {
                    //button_upload.setText("正在上传创伤记录(文件)...");
                    //逐个上传文件
                    if (!upload()) {
                        button_upload.setText("正在上传新增的创伤记录...");

                        uploadRecord();
                    }
                }else{
                    CommonDialog commonDialog = new CommonDialog(
                            XinxiluruActivity.this,
                            R.style.userDialog,
                            "请刷手环！",
                            getResources().getString(R.string.confirm_label));
                    commonDialog.show();
                }
                break;

            case R.id.imageView_getImg:
                if (imageFileCount < 3) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 300);
                } else {
                    Toast.makeText(XinxiluruActivity.this, "当前已经选择了3张照片！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.imageView_paizhao:
                switch (imageFileCount) {
                    case 0:
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File targetFile;
                        Uri uri;
                        localImageFilePath1 = imageFilePathPrefix + localImageFileDefaultName1;
                        System.out.println("imageFile 2 : " + localImageFilePath1);
                        targetFile = new File(localImageFilePath1);
                        if(!targetFile.exists()){
                            File dirPath = targetFile.getParentFile();
                            dirPath.mkdirs();
                        }
                        uri = Uri.fromFile(targetFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(intent, 200);
                        break;
                    case 1:
                        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        localImageFilePath2 = imageFilePathPrefix + localImageFileDefaultName2;
                        System.out.println("imageFile 2 : " + localImageFilePath2);
                        targetFile = new File(localImageFilePath2);
                        if(!targetFile.exists()){
                            File dirPath = targetFile.getParentFile();
                            dirPath.mkdirs();
                        }
                        uri = Uri.fromFile(targetFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(intent, 201);
                        break;
                    case 2:
                        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        localImageFilePath3 = imageFilePathPrefix + localImageFileDefaultName3;
                        System.out.println("imageFile 2 : " + localImageFilePath3);
                        targetFile = new File(localImageFilePath3);
                        if(!targetFile.exists()){
                            File dirPath = targetFile.getParentFile();
                            dirPath.mkdirs();
                        }
                        uri = Uri.fromFile(targetFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(intent, 202);
                        break;
                    case 3:
                        System.out.println("imageFileCount : " + imageFileCount);
                        Toast.makeText(XinxiluruActivity.this, "当前已经选择了3张照片！", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            case R.id.imageView_delImg:
                switch (imageFileCount) {
                    case 0:
                        System.out.println("imageFileCount : " + imageFileCount);
                        Toast.makeText(XinxiluruActivity.this, "当前没有照片，无法删除！", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        if (localImageFilePath1 != null) {
                            if(Utility.deleteFile(localImageFilePath1)) {
                                imageFileCount = 0;
                                imageView_pic1.setImageResource(R.drawable.pic_empty2);
                                Toast.makeText(XinxiluruActivity.this, "已经删除一张照片！", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(XinxiluruActivity.this, "删除照片失败！", Toast.LENGTH_SHORT).show();
                            }
                        }
                        break;
                    case 2:
                        if (localImageFilePath2 != null) {
                            if(Utility.deleteFile(localImageFilePath2)) {
                                imageFileCount = 1;
                                imageView_pic2.setImageResource(R.drawable.pic_empty2);
                                Toast.makeText(XinxiluruActivity.this, "已经删除一张照片！", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(XinxiluruActivity.this, "删除照片失败！", Toast.LENGTH_SHORT).show();
                            }
                        }
                        break;
                    case 3:
                        if (localImageFilePath3 != null) {
                            if(Utility.deleteFile(localImageFilePath3)) {
                                imageFileCount = 2;
                                imageView_pic3.setImageResource(R.drawable.pic_empty2);
                                Toast.makeText(XinxiluruActivity.this, "已经删除一张照片！", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(XinxiluruActivity.this, "删除照片失败！", Toast.LENGTH_SHORT).show();
                            }
                        }
                        break;
                }
                break;
            case R.id.imageView_record1:
                if (!recording) {
                    resetRecorder(1);

                    mediaRecorder.start();
                    recording = !recording;

                    updateRecordView(1, true);
                } else {
                    mediaRecorder.stop();

                    recording = !recording;

                    updateRecordView(1, false);
                }
                break;
            case R.id.imageView_record2:
                if (!recording) {
                    resetRecorder(2);

                    mediaRecorder.start();
                    recording = !recording;

                    updateRecordView(2, true);
                } else {
                    mediaRecorder.stop();

                    recording = !recording;

                    updateRecordView(2, false);
                }
                break;
            case R.id.imageView_record3:
                if (!recording) {
                    resetRecorder(3);

                    mediaRecorder.start();
                    recording = !recording;

                    updateRecordView(3, true);
                } else {
                    mediaRecorder.stop();

                    recording = !recording;

                    updateRecordView(3, false);
                }
                break;

            case R.id.imageView_del1:
                if (audioFile1 != null && audioFile1.exists() && !audioFile1.isDirectory()) {
                    audioFile1.delete();
                }
                textView_audio_1.setText("请录音！");
                break;
            case R.id.imageView_del2:
                if (audioFile2 != null && audioFile2.exists() && !audioFile2.isDirectory()) {
                    audioFile2.delete();
                }
                textView_audio_2.setText("请录音！");
                break;
            case R.id.imageView_del3:
                if (audioFile3 != null && audioFile3.exists() && !audioFile3.isDirectory()) {
                    audioFile3.delete();
                }
                textView_audio_3.setText("请录音！");
                break;
            default:
                break;
        }
    }

    /**
     * 初始化设置录音接口
     *
     * @param which 哪个按钮
     */
    private void resetRecorder(int which) {
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        switch (which) {
            case 1:
                mediaRecorder.setOutputFile(audioFile1.getAbsolutePath());
                break;
            case 2:
                mediaRecorder.setOutputFile(audioFile2.getAbsolutePath());
                break;
            case 3:
                mediaRecorder.setOutputFile(audioFile3.getAbsolutePath());
                break;
        }
        try {
            mediaRecorder.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateRecordView(int id, boolean recording){
        switch (id){
            case 1:
                if(recording) {
                    imageView_record_1.setImageResource(R.drawable.play_stop);
                    audioFileFlag1 = false;

                    textView_audio_1.setText("正在录音...");

                    imageView_record_2.setEnabled(false);
                    imageView_record_3.setEnabled(false);
                }else{
                    textView_audio_1.setText(audioFile1.getName());
                    imageView_record_1.setImageResource(R.drawable.play_record);
                    audioFileFlag1 = true;

                    imageView_record_2.setEnabled(true);
                    imageView_record_3.setEnabled(true);
                }
                break;
            case 2:
                if(recording) {
                    imageView_record_2.setImageResource(R.drawable.play_stop);
                    audioFileFlag2 = false;

                    textView_audio_2.setText("正在录音...");

                    imageView_record_1.setEnabled(false);
                    imageView_record_3.setEnabled(false);
                }else{
                    textView_audio_2.setText(audioFile2.getName());
                    imageView_record_2.setImageResource(R.drawable.play_record);
                    audioFileFlag2 = true;

                    imageView_record_1.setEnabled(true);
                    imageView_record_3.setEnabled(true);
                }
                break;
            case 3:
                if(recording) {
                    imageView_record_3.setImageResource(R.drawable.play_stop);
                    audioFileFlag3 = false;

                    textView_audio_3.setText("正在录音...");

                    imageView_record_1.setEnabled(false);
                    imageView_record_2.setEnabled(false);
                }else{
                    textView_audio_3.setText(audioFile1.getName());
                    imageView_record_3.setImageResource(R.drawable.play_record);
                    audioFileFlag3 = true;

                    imageView_record_1.setEnabled(true);
                    imageView_record_2.setEnabled(true);
                }
                break;
        }
    }

    private void fillSpinner2(int position) {
        if (position >= 0) {
            if(itemsOfHotwords != null){
                data_list.clear();
                Hotwords oneLevelHotwords = itemsOfHotwords.get(position);
                if(oneLevelHotwords != null){
                    List<Hotwords> currentList = oneLevelHotwords.getTwoLevelList();
                    if (currentList != null)
                        for (int i = 0; i < currentList.size(); i++) {
                            data_list.add(currentList.get(i).getWords());
                        }
                }
            }

            //将可选内容与ArrayAdapter连接起来
            adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
            //设置下拉列表的风格
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //将adapter 添加到spinner中
            spinner2.setAdapter(adapter2);
            //添加事件Spinner事件监听
            spinner2.setOnItemSelectedListener(new SpinnerSelectedListener2());
            //设置默认值
            spinner2.setVisibility(View.VISIBLE);
        } else {
            //将可选内容与ArrayAdapter连接起来
            adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerList2);
            //设置下拉列表的风格
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //将adapter 添加到spinner中
            spinner2.setAdapter(adapter2);
            //添加事件Spinner事件监听
            spinner2.setOnItemSelectedListener(new SpinnerSelectedListener2());
            //设置默认值
            spinner2.setVisibility(View.VISIBLE);
        }
    }

    private int spinnerPostion1 = 0;
    //使用数组形式操作
    class SpinnerSelectedListener implements OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            // TODO Auto-generated method stub
            spinnerPostion1 = position;
            fillSpinner2(position);

            System.out.println("spinnerPostion1: " + spinnerPostion1);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // TODO Auto-generated method stub

        }

    }
    private int spinnerPostion2 = 0;
    //使用数组形式操作
    class SpinnerSelectedListener2 implements OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            // TODO Auto-generated method stub
            spinnerPostion2 = position;

            System.out.println("spinnerPostion2: " + spinnerPostion2);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // TODO Auto-generated method stub

        }

    }

    private BingrenInfo bingrenInfo;
    public BingrenInfo getBingrenInfo(){
        return bingrenInfo;
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

    private List<Hotwords> itemsOfHotwords = new ArrayList<Hotwords>();
    public List<Hotwords> getItemsOfHotwords(){
        return itemsOfHotwords;
    }

    /**
     * 获取热词库 两级结构
     * 测试例子：
     *      http://192.168.10.142:8080/treat/app/treat.html?mark=getHotWord
     *
     *
     *      {"result":1,
     *      "finalList":
     *      [
     *          {"chilrenList":[
     *              {"explanation":"一级处理B-2.3","gmtCreated":1470377627000,"gmtModified":1470377627000,"hotWordName":"一级处理B-2.3","id":15,"isDel":1,"parent":12},
     *              {"explanation":"一级处理B-2.2","gmtCreated":1470377613000,"gmtModified":1470377613000,"hotWordName":"一级处理B-2.2","id":14,"isDel":1,"parent":12},
     *              {"explanation":"一级处理B-2.1","gmtCreated":1470377594000,"gmtModified":1470377594000,"hotWordName":"一级处理B-2.1","id":13,"isDel":1,"parent":12}
     *              ],
     *           "parent":{"gmtCreated":1470377476000,"gmtModified":1470377476000,"hotWordTypeName":"一级处理B","id":12,"isDel":1}
     *           },
     *          {"chilrenList":[
     *              {"explanation":"你说你急哦","gmtCreated":1470377306000,"gmtModified":1470377306000,"hotWordName":"加班表留","id":9,"isDel":1,"parent":8}
     *              ],
     *          "parent":{"gmtCreated":1470377291000,"gmtModified":1470377291000,"hotWordTypeName":"瓦","id":8,"isDel":1}
     *          },
     *          {"chilrenList":[
     *              {"explanation":"11","gmtCreated":1470377400000,"gmtModified":1470377400000,"hotWordName":"二级处理","id":11,"isDel":1,"parent":4},
     *              {"explanation":"A二级处理1","gmtCreated":1470377340000,"gmtModified":1470377340000,"hotWordName":"A二级处理1","id":10,"isDel":1,"parent":4},
     *              {"gmtCreated":1470018737000,"gmtModified":1470018737000,"hotWordType":"消息","hotWordTypeName":"asas","id":2,"isDel":1,"parent":4}
     *              ],
     *              "parent":{"gmtCreated":1470057046000,"gmtModified":1470377257000,"hotWordTypeName":"一级处理A","id":4,"isDel":1}}
     *       ]}
     *
     *       {"result":1,
     *       "finalList":
     *          [
     *              {"chilrenList":[{"explanation":"一级处理B-2.3","gmtCreated":1470377627000,"gmtModified":1470377627000,"hotWordName":"一级处理B-2.3","id":15,"isDel":1,"parent":12},
     *              {"explanation":"一级处理B-2.2","gmtCreated":1470377613000,"gmtModified":1470377613000,"hotWordName":"一级处理B-2.2","id":14,"isDel":1,"parent":12},
     *              {"explanation":"一级处理B-2.1","gmtCreated":1470377594000,"gmtModified":1470377594000,"hotWordName":"一级处理B-2.1","id":13,"isDel":1,"parent":12}],
     *              "parent":{"gmtCreated":1470377476000,"gmtModified":1470377476000,"hotWordTypeName":"一级处理B","id":12,"isDel":1}},
     *
     *              {"chilrenList":[{"explanation":"你说你急哦","gmtCreated":1470377306000,"gmtModified":1470377306000,"hotWordName":"加班表留","id":9,"isDel":1,"parent":8}],"parent":{"gmtCreated":1470377291000,"gmtModified":1470377291000,"hotWordTypeName":"瓦","id":8,"isDel":1}},{"chilrenList":[{"explanation":"11","gmtCreated":1470377400000,"gmtModified":1470377400000,"hotWordName":"二级处理","id":11,"isDel":1,"parent":4},{"explanation":"A二级处理1","gmtCreated":1470377340000,"gmtModified":1470377340000,"hotWordName":"A二级处理1","id":10,"isDel":1,"parent":4},{"gmtCreated":1470018737000,"gmtModified":1470018737000,"hotWordType":"消息","hotWordTypeName":"asas","id":2,"isDel":1,"parent":4}],"parent":{"gmtCreated":1470057046000,"gmtModified":1470377257000,"hotWordTypeName":"一级处理A","id":4,"isDel":1}}]}
     */
    public void getHotWord() {
        RequestParams params = new RequestParams();
        params.put(JsonKey.mark, "getHotWord");

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
                        msg.what = GET_HOTWORDS_RESULT;
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
     *  上传创伤记录
     *  测试例子：
     *      http://192.168.10.174:8080/treat/app/treat.html?mark=upLoadProcessRecord&patientCode=009&doctorCode=1000000121&recordDate=2016-08-09&recordDetil=%271.创伤时间2.急救措施%27
     *
     *      {"processRecords":{"patientCode":"009","recordDate":1470672000000,"recordDetil":"'1.创伤时间2.急救措施'","recordPerson":"王雪梅"},
     *      "result":1}
     *
     *      http://192.168.10.174:8080/treat/app/treat.html?mark=upLoadProcessRecord&patientCode=001&doctorCode=001&recordDate=2016-08-09&recordDetil=%271.创伤时间2.急救措施%27
     *
     *      {"processRecords":{"patientCode":"001","recordDate":1470672000000,"recordDetil":"'1.å\u0088\u009Bä¼¤æ\u0097¶é\u0097´2.æ\u0080¥æ\u0095\u0091æ\u008Eªæ\u0096½'"},
     *      "result":1,
     *      "reason":"该医生不存在"}
     */
    public void upLoadProcessRecord(HuanzheJiuzhiInfo huanzheJiuzhiInfo) {
        if(huanzheJiuzhiInfo == null){
            Toast.makeText(XinxiluruActivity.this, "新增创伤记录失败！", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestParams params = new RequestParams();
        params.put(JsonKey.mark, "upLoadProcessRecord");
        params.put(JsonKey.ItemHuanzheId, huanzheJiuzhiInfo.getItemHuanzheId());
        params.put("doctorCode", Variable.cs_user.getMemberCode());
        params.put("recordDate", huanzheJiuzhiInfo.getItemJiuzhiShijian());
        params.put("recordDetil", huanzheJiuzhiInfo.getItemJiuzhiJianyaoJilu());

        params.put("imagFile1", huanzheJiuzhiInfo.getItemPicFile1());
        params.put("imagFile2", huanzheJiuzhiInfo.getItemPicFile2());
        params.put("imagFile3", huanzheJiuzhiInfo.getItemPicFile3());

        params.put("audioFile1", huanzheJiuzhiInfo.getItemAudFile1());
        params.put("audioFile2", huanzheJiuzhiInfo.getItemAudFile2());
        params.put("audioFile3", huanzheJiuzhiInfo.getItemAudFile3());

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
                        msg.what = UPLOAD_RECORD_RESULT;
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
                case 1:// 上传文件
                    Bundle bundle = msg.getData();
                    String data = bundle.getString(JsonKey.response_result);
                    System.out.println("upload file result:" + data);
                    JSONObject object;
                    try {
                        object = new JSONObject(data);
                        if (!data.equals("")) {
                            int result = object.optInt(JsonKey.result);
                            if (result > 0) {
                                String iconUrl = object.optString("url", ""); // 该上传文件最终的访问URL

                                String key = getCurrentKey();
                                if(key != null && !key.equals("")){
                                    addOneUploadFileNameMap(key, iconUrl);
                                }

                                // 若有要上传的文件，继续
                                if(!uploadContinue()) {
                                    // 传完文件，开始传记录
                                    button_upload.setText("正在上传新增的创伤记录...");

                                    uploadRecord();
                                }
                            } else {
                                CommonDialog commonDialog = new CommonDialog(
                                        XinxiluruActivity.this,
                                        R.style.userDialog,
                                        "文件上传失败，请重试！",
                                        getResources().getString(R.string.confirm_label));
                                commonDialog.show();

                                clearUploadFileNameMap();
                            }

                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;

                case 2:
                    CommonDialog commonDialog = new CommonDialog(
                            XinxiluruActivity.this,
                            R.style.userDialog,
                            "文件上传失败，请重试！",
                            getResources().getString(R.string.confirm_label));
                    commonDialog.show();

                    clearUploadFileNameMap();
                    break;

                case GET_HOTWORDS_RESULT:
                    bundle = msg.getData();
                    data = bundle.getString("data");
                    System.out.println(data);
                    if (data != null & !data.equals("")) {
                        try {
                            object = new JSONObject(data);
                            int flag = object.optInt(JsonKey.result);
                            if (flag == 1) {
                                Toast.makeText(XinxiluruActivity.this, "获取热词库成功！", Toast.LENGTH_SHORT).show();

                                itemsOfHotwords.clear();

                                //itemsOfHuanzheStatus， HuanzheStatus
                                String list = object.optString("finalList");
                                if (list != null && !"".equals(list)) {
                                    JSONArray array = object.optJSONArray("finalList");

                                    if(array != null && array.length() > 0) {
                                        for (int i = 0; i < array.length(); i++) {
                                            Hotwords item = new Hotwords(array.getJSONObject(i));

                                            itemsOfHotwords.add(item);
                                        }
                                    }
                                }

                                for (int j = 0; j < itemsOfHotwords.size(); j++) {
                                    Hotwords tmp = itemsOfHotwords.get(j);
                                    System.out.println(tmp.toString());
                                    if (tmp.getTwoLevelList() != null)
                                        for (int x = 0; x < tmp.getTwoLevelList().size(); x++) {
                                            System.out.println("\t\t" + tmp.getTwoLevelList().get(x).toString());
                                        }
                                }

                                // UPDATE controller's data
                                initSpinnerData();
                            } else {
                                CommonDialog dialog = new CommonDialog(
                                        XinxiluruActivity.this,
                                        R.style.userDialog,
                                        "获取热词库失败！",
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
                             object = new JSONObject(data);
                            int flag = object.optInt(JsonKey.result);
                            if (flag == 1) {
                                Toast.makeText(XinxiluruActivity.this, "开始记录创伤处理！", Toast.LENGTH_SHORT).show();

                                JSONObject patient = object.getJSONObject("patient");
                                bingrenInfo = new BingrenInfo(patient);

                                updatePatientInfo();
                            }else {
                                CommonDialog dialog = new CommonDialog(
                                        XinxiluruActivity.this,
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

                case UPLOAD_RECORD_RESULT:
                    bundle = msg.getData();
                    data = bundle.getString("data");
                    System.out.println(data);
                    if (data != null & !data.equals("")) {
                        try {
                            object = new JSONObject(data);
                            int flag = object.optInt(JsonKey.result);
                            if (flag == 1) {
                                Toast.makeText(XinxiluruActivity.this, "上传创伤记录成功！", Toast.LENGTH_SHORT).show();

                                //关闭，返回上一级
                                finish();
                            }else {
                                CommonDialog dialog = new CommonDialog(
                                        XinxiluruActivity.this,
                                        R.style.userDialog,
                                        "上传创伤记录失败！",
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

    private Map<String, String> uploadFileNameMap = new HashMap<String, String>() ;
    private String currentKey;

    private void clearUploadFileNameMap(){
        uploadFileNameMap.clear();
        currentKey = null;
    }

    private void addOneUploadFileNameMap(String key, String value){
        uploadFileNameMap.put(key, value);
    }

    private String getCurrentKey(){
        return currentKey;
    }
    private void setCurrentKey(String value){
        currentKey = value;
    }
    /**
     * 上传文件到服务器，返回文件的URL给APP
     *       http://192.168.10.142:8080/treat/app/treat.html?mark=upload
     * @param SaveFile
     */
    private void uploadFile(String SaveFile){
        System.out.println("upload file path:" + SaveFile);

        // 保存当前本地上传文件名
        setCurrentKey(SaveFile);

        SocketHttpRequester requester = new SocketHttpRequester(Variable.server + Variable.padIndex + "?" + "mark=upload",
                SaveFile,
                "file", //JSON KEY
                this,
                handler);
        requester.execute();
    }

    private ArrayList<String> wantToUplaodFilePaths = new ArrayList<String>();
    private int uploadFilePathIndex = 0;
    private boolean upload() {
        boolean result = false;

        // 图片文件
        int count = imageFileCount;
        if (count > 0) {
            wantToUplaodFilePaths.add(localImageFilePath1);
            count--;
            if (count > 0) {
                wantToUplaodFilePaths.add(localImageFilePath2);
                count--;
            }
            if (count > 0) {
                wantToUplaodFilePaths.add(localImageFilePath3);
                count--;
            }
        }

        // 音频文件
        if(audioFileFlag1){
            wantToUplaodFilePaths.add(audioFile1.getAbsolutePath());
        }
        if(audioFileFlag2){
            wantToUplaodFilePaths.add(audioFile2.getAbsolutePath());
        }
        if(audioFileFlag3){
            wantToUplaodFilePaths.add(audioFile3.getAbsolutePath());
        }

        if(wantToUplaodFilePaths.size() > 0) {
            uploadFilePathIndex = 0;

            uploadFile(wantToUplaodFilePaths.get(uploadFilePathIndex));

            uploadFilePathIndex ++;

            result = true;
        }

        return result;
    }

    private boolean uploadContinue(){
        int count = wantToUplaodFilePaths.size();
        boolean result = false;

        if(uploadFilePathIndex < count){
            uploadFile(wantToUplaodFilePaths.get(uploadFilePathIndex));
            uploadFilePathIndex ++;

            result = true;
        }

        return result;
    }
    /**
     * 上传当前的创伤记录
     */
    private void uploadRecord(){
        HuanzheJiuzhiInfo info = createUploadHuanzheJiuzhiInfo();

        upLoadProcessRecord(info);
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

            // 根据当前的NFC码找到病人
            getPatientInfoByNFCCode(this.NFCCode);

        } catch (NullPointerException localNullPointerException) {

        }
        return str1;
    }
}