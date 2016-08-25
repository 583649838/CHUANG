package com.bestplus.chuangshangjiuzhi.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PrefUtil {
	private final static String _PREF_TAG = "_pref_tag";
	/**
	 * 保存字符串数据
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void putString(Context context, String key, String value) {
		SharedPreferences pref = context
				.getSharedPreferences(_PREF_TAG, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	/**
	 * 获取字符串数据
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static String getString(Context context, String key, String defValue) {
		SharedPreferences pref = context
				.getSharedPreferences(_PREF_TAG, Context.MODE_PRIVATE);
		return pref.getString(key, defValue);
	}
	
	
	/**
	 * 保存整型数据
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void putInt(Context context, String key, int value) {
		SharedPreferences pref = context
				.getSharedPreferences(_PREF_TAG, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putInt(key, value);
		editor.commit();
	}
	
	/**
	 * 保存整型数据
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static int getInt(Context context, String key, int defValue) {
		SharedPreferences pref = context
				.getSharedPreferences(_PREF_TAG, Context.MODE_PRIVATE);
		return pref.getInt(key, defValue);
	}
	
	
	/**
	 * 保存长整型数据
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void putLong(Context context, String key, long value) {
		SharedPreferences pref = context
				.getSharedPreferences(_PREF_TAG, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putLong(key, value);
		editor.commit();
	}
	
	/**
	 * 保存长整型数据
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static long getLong(Context context, String key, long defValue) {
		SharedPreferences pref = context
				.getSharedPreferences(_PREF_TAG, Context.MODE_PRIVATE);
		return pref.getLong(key, defValue);
	}
	
	/**
	 * 保存布尔值数据
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void putBoolean(Context context, String key, boolean value) {
		SharedPreferences pref = context
				.getSharedPreferences(_PREF_TAG, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	
	/**
	 * 保存布尔值数据
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static boolean getBoolean(Context context, String key, boolean defValue) {
		SharedPreferences pref = context
				.getSharedPreferences(_PREF_TAG, Context.MODE_PRIVATE);
		return pref.getBoolean(key, defValue);
	}
	
	
	/**
	 * 保存浮点型数据
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void putFloat(Context context, String key, float value) {
		SharedPreferences pref = context
				.getSharedPreferences(_PREF_TAG, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putFloat(key, value);
		editor.commit();
	}
	
	/**
	 * 保存浮点型数据
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static float getFloat(Context context, String key, float defValue) {
		SharedPreferences pref = context
				.getSharedPreferences(_PREF_TAG, Context.MODE_PRIVATE);
		return pref.getFloat(key, defValue);
	}
}
