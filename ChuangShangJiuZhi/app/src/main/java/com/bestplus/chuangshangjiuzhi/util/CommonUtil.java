package com.bestplus.chuangshangjiuzhi.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;

public class CommonUtil {
	private static final int[] weight = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
	private static final char[] validate = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };

	public static byte[] hexStringToByteArray(String s) {
	    byte[] b = new byte[s.length() / 2];
	    for (int i = 0; i < b.length; i++) {
	      int index = i * 2;
	      int v = Integer.parseInt(s.substring(index, index + 2), 16);
	      b[i] = (byte) v;
	    }
	    return b;
	  }
	public static String m(double f) {
		DecimalFormat df = new DecimalFormat("#.0");
		return df.format(f);
	}
	
	//�������ж�
	public static boolean isDecimal(String str) {
		if(str==null || "".equals(str))
			return false;  
		Pattern pattern = Pattern.compile("[0-9]*(\\.?)[0-9]*");
		return pattern.matcher(str).matches();
	}
	
	public static boolean isInteger(String str) {
		if(str==null || "".equals(str))
			return false;  
		Pattern pattern = Pattern.compile("[0-9]+");
		return pattern.matcher(str).matches();
	}
	
	public static String convert2HexString(byte b) {
		String hex = Integer.toHexString(b & 0xFF);
		if (hex.length() == 1) {
			hex = '0' + hex;
		}
		return hex.toUpperCase();

	}
	// ת��ʮ�����Ʊ���Ϊ�ַ���
	public static String toStringHex(String s) {
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			s = new String(baKeyword, "gb2312");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s;
	}
	/**
     * ��ȡ��ֵ����ͻ��˰汾��
     */
    public static int getIDServicePluginClientVersionCode(Context context) {      
		PackageInfo info;
		try {
			info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return info.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return 0;
    }
	/**
	 * �ж�Apk�ļ��Ƿ����
	 * @param packageName apk����
	 */
	public static boolean checkApkExist(Context context, String packageName) {
		if (packageName == null || packageName.trim().length()<=0){
			return false;
		}
		try {
			context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
			return true;
		} catch (NameNotFoundException e) {
			return false;
		}
	}
	public static boolean retrieveApkFromAssets(Context context, String srcfileName, String desFileName) {
		boolean bRet = false;
		InputStream is = null;
		FileOutputStream fos = null;
		try {
			is = context.getAssets().open(srcfileName);
			fos = context.openFileOutput(desFileName, Context.MODE_WORLD_READABLE);
			byte[] buffer = new byte[1024];
			int byteCount = -1;
			while ((byteCount = is.read(buffer)) != -1) {
				fos.write(buffer, 0, byteCount);
			}
			fos.flush();
			bRet = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(fos!=null) fos.close();
				if(is!=null) is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bRet;
	}
	
	public static void installApp(Context context, String filePath) {
		if (filePath == null || filePath.trim().length()<=0) {
			return;
		}
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse("file:" + filePath), "application/vnd.android.package-archive");
		context.startActivity(intent);
	}
	public static void installApp(Context context, File apkFile) {
		if (apkFile == null || !apkFile.exists()) {
			return;
		}
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
		context.startActivity(intent);
	}
	
	public static boolean checkID(String id) {
		if (id.length() != 18) {
			return false;
		}
		String id17 = id.substring(0, 17);
		char c = id.charAt(17);
		if (c == getValidateCode(id17)) {
			return true;
		}
		return false;
	}
	private static char getValidateCode(String id17) {
		int sum = 0;
		int mode = 0;
		for (int i = 0; i < id17.length(); i++) {
			sum = sum + Integer.parseInt(String.valueOf(id17.charAt(i))) * weight[i];
		}
		mode = sum % 11;
		return validate[mode];
	}
	
	public static String getCurrnetDate(String pattern) {
		
        return new SimpleDateFormat(pattern).format(new Date());
        
    }

	public static String formatDate(String template1, String datetime, String template2) {
		try {
			return new SimpleDateFormat(template2).format(new SimpleDateFormat(template1).parse(datetime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}
	
}
