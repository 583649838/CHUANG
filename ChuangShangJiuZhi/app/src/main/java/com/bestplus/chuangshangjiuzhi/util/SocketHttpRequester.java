package com.bestplus.chuangshangjiuzhi.util;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.bestplus.chuangshangjiuzhi.common.JsonKey;

/**
 * Created by maomao on 2016/8/8.
 */
public class SocketHttpRequester extends AsyncTask<String, Integer, String>{
    private ProgressDialog pd;
    private long totalSize;
    private static Context context;
    private static String url;
    private static String filePath;
    private static String fileIcon;
    private static String param;
    private static Handler handler=null;
    private ArrayList<String> paths;

    public SocketHttpRequester(String url,String filePath,String param,Context context,Handler handler){

        this.url=url;
        this.filePath=filePath;
        this.param =param;
        this.context=context;
        this.handler=handler;


    }
    public SocketHttpRequester(String url,String filePath,String fileIcon,String param,Context context,Handler handler){

        this.url=url;
        this.filePath=filePath;
        this.param =param;
        this.context=context;
        this.handler=handler;
        this.fileIcon=fileIcon;
    }

    public SocketHttpRequester(String url,ArrayList<String> paths,String param,Context context,Handler handler){

        this.url=url;
        this.filePath=filePath;
        this.param =param;
        this.context=context;
        this.handler=handler;
        this.fileIcon=fileIcon;
        this.paths=paths;
    }
    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("资料上传中...");
        pd.setCancelable(false);
        pd.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String serverResponse = "";
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext httpContext = new BasicHttpContext();
        HttpPost httpPost = new HttpPost(url);
        try {
            CustomMultipartEntity multipartContent = new CustomMultipartEntity(
                    new CustomMultipartEntity.ProgressListener() {
                        @Override
                        public void transferred(long num) {
                            publishProgress((int) ((num / (float) totalSize) * 100));
                        }
                    });

            // We use FileBody to transfer an image
            multipartContent.addPart("param", new StringBody(param.toString(),Charset.forName("UTF-8")));

            if(this.paths != null && paths.size() > 0){
                System.out.println("paths.size(): " + paths.size());

                for (String path : paths) {
                    System.out.println("path: " + path);
                    multipartContent.addPart("data", new FileBody(new File(path)));
                }
            }
            else{
                System.out.println("filePath: " + filePath);

                multipartContent.addPart("data", new FileBody(new File(filePath)));

                if(fileIcon != null && !"".equals(fileIcon))
                    multipartContent.addPart("data", new FileBody(new File(fileIcon)));
            }
            totalSize = multipartContent.getContentLength();

            System.out.println("totalSize: " + totalSize);

            // Send it
            httpPost.setEntity(multipartContent);
            HttpResponse response = httpClient.execute(httpPost, httpContext);
            serverResponse = EntityUtils.toString(response.getEntity(),HTTP.UTF_8);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return serverResponse;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        pd.setProgress((int) (progress[0]));
    }

    @Override
    protected void onPostExecute(String result) {
        System.out.println("result: " + result);
        DebugLog.d("result:"+result);

        pd.dismiss();
        Message message=new Message();
        if(handler!=null){
            if(result != null && !"".equals(result)){
                Bundle data =new Bundle();
                data.putString(JsonKey.response_result, result);
                message.what=1;
                message.setData(data);
                handler.sendMessage(message);
            }
            else
            {
                message.what=2;
                handler.sendMessage(message);
            }
        }

    }

    @Override
    protected void onCancelled() {
        System.out.println("cancle");
    }


}
