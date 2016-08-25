package com.bestplus.chuangshangjiuzhi.util;



import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.bestplus.chuangshangjiuzhi.common.JsonKey;
import com.bestplus.chuangshangjiuzhi.common.Method;
import com.bestplus.chuangshangjiuzhi.common.Variable;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;


public class HttpClientRequest {

	private static final String BASE_URL =Variable.server;

	private static AsyncHttpClient client = new AsyncHttpClient();
	
	 

	public static void get(String url, RequestParams params, int timeout,
			AsyncHttpResponseHandler responseHandler) {
		if (timeout > 0)
			client.setTimeout(timeout);
		if(url.equals("")) url = BASE_URL;
		client.get(url, params, responseHandler);
	}

	public static void post(String url, RequestParams params, int timeout,
			AsyncHttpResponseHandler responseHandler) {
		if (timeout > 0)
			client.setTimeout(timeout);
		if(url.equals("")) url = BASE_URL;
		client.post(url, params, responseHandler);
		
	}

	public static void post(String url, RequestParams params, int timeout,
			JsonHttpResponseHandler responseHandler) {
		if (timeout > 0)
			client.setTimeout(timeout);
		if(url.equals("")) url = BASE_URL;
		client.post(url, params, responseHandler);
	}

	public static void getServerData(int method,String url,String data,final AsyncHttpResponseHandler responseHandler) {
     	RequestParams params = new RequestParams(); // 绑定参数
     	params.put(JsonKey.HttpMethod, Integer.toString(method) );	
     	if(data!=null)
     		params.put(JsonKey.HttpData, data );
     	String mUrl=url;    	 
     	HttpClientRequest.post(mUrl, params, 0, responseHandler); 
     } 
	
	public static void cancel(Context context){
		client.cancelRequests(context, true);
	}
}
