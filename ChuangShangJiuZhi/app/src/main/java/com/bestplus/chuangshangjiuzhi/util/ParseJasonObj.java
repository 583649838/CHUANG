package com.bestplus.chuangshangjiuzhi.util;

import org.json.JSONException;
import org.json.JSONObject;

public class ParseJasonObj {	
	public static String  parseString(JSONObject  obj,String key,String  deaultvalue){
		String value;
		try {
			 String test="银江";
			 value = obj.getString(key);
			 DebugLog.d("xjg","ajofjadjo");
			
		}catch(JSONException e){
			
			value=deaultvalue;
		}
		return value;
	}
	
	public static int  parseInt(JSONObject  obj,String key,int  deaultvalue){
		int  value;
		try {
			
			 value = obj.getInt(key);
			
		}catch(JSONException e){
			
			value=deaultvalue;
		}
		return value;
	}
	
	public static Boolean  parseBoolean(JSONObject  obj,String key,Boolean  deaultvalue){
		Boolean  value;
		try {
			
			 value = obj.getBoolean(key);
			
		}catch(JSONException e){
			
			value=deaultvalue;
		}
		return value;
	}
	
}
