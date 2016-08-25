package com.bestplus.chuangshangjiuzhi.util;

import java.io.File;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


import com.bestplus.chuangshangjiuzhi.common.Variable;
import com.bestplus.chuangshangjiuzhi.encrypt.JAVADESEncrypt;
import com.bestplus.chuangshangjiuzhi.encrypt.JAVARSAEncrypt;
import com.bestplus.chuangshangjiuzhi.encrypt.XorEncrypt;
import com.loopj.android.http.AsyncHttpResponseHandler;


/**
 * @author
 * @date : 2014-6-1
 * @desc : 该项目可复用方法　
 */
public class Utility {
	private static Map<String, SimpleDateFormat> formatMap = new HashMap<String, SimpleDateFormat>();
	
	public Utility() {
		// TODO Auto-generated constructor stub
	}

	// RSA加密包
	public static byte[] getRSAEncrypt(Context con, short code,
			JSONObject jsonval) {
		// TODO Auto-generated method stub
		try {
			List<byte[]> packDataList = new ArrayList<byte[]>();

			byte[] codeData = new byte[2];// 消息编码
			codeData = ConversionTools.shortToBytes(code);
			codeData = XorEncrypt.xorEncryptAndDecrypt(codeData);// 异或加密
			packDataList.add(codeData);

			packDataList.add(jsonval.toString().getBytes("utf-8"));
			byte[] packeData = ConversionTools.arraysByteToBytes(packDataList);// 数据组包
			// 数据RSA加密
			byte[] publicKeyData = JAVARSAEncrypt.getPublicKey(con);
			return JAVARSAEncrypt.encryptByPublicKey(packeData,
					ConversionTools.bytesToString(publicKeyData));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	// des加密包
	public static byte[] getDESEncrypt(Context con, byte code,
			JSONObject jsonval) {
		// TODO Auto-generated method stub
		try {
			/*
			List<byte[]> packDataList = new ArrayList<byte[]>();

			byte[] codeData = new byte[2];// 消息编码
			codeData = ConversionTools.shortToBytes(code);
			codeData = XorEncrypt.xorEncryptAndDecrypt(codeData);// 异或加密
			packDataList.add(codeData);

			if(jsonval != null){
				packDataList.add(jsonval.toString().getBytes("utf-8"));
			}
			
			byte[] packeData = ConversionTools.arraysByteToBytes(packDataList);// 数据组包
			
			return JAVADESEncrypt.encrypt(packeData,AppStatus.DES_KEY.getBytes());
			*/
			
			List<byte[]> packDataList = new ArrayList<byte[]>(); //所有
			
			List<byte[]> desData = new ArrayList<byte[]>();  //加密
			
			
			byte[] codeData = new byte[1];//消息编码
			codeData[0] = code;
			packDataList.add(codeData);  //放在第一个字节的code不加密
			
			desData.add(codeData); //code放入加密
			
			if(jsonval != null){
				desData.add(jsonval.toString().getBytes("utf-8"));
			}
			
			byte[] PackeDataAll = ConversionTools.arraysByteToBytes(desData);// 数据组包
			byte[] PackeDataDes = JAVADESEncrypt.encrypt(PackeDataAll,Variable.DES_KEY.getBytes());
			packDataList.add(PackeDataDes);
			
			byte[] packeDataReturn = ConversionTools.arraysByteToBytes(packDataList);// 数据组包
			
			return packeDataReturn;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	// des加密包
		public static byte[] getDESData(String sourceData) {
			// TODO Auto-generated method stub
			try {				
				List<byte[]> packDataList = new ArrayList<byte[]>(); //所有				
				List<byte[]> desData = new ArrayList<byte[]>();  //加密
				desData.add(sourceData.getBytes("utf-8"));			
				byte[] PackeDataAll = ConversionTools.arraysByteToBytes(desData);// 数据组包
				byte[] PackeDataDes = JAVADESEncrypt.encrypt(PackeDataAll,Variable.DES_KEY.getBytes());
				packDataList.add(PackeDataDes);				
				byte[] packeDataReturn = ConversionTools.arraysByteToBytes(packDataList);// 数据组包			
				return packeDataReturn;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return null;
		}


	public static String getCurrentTime() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	
	/**
	 * 将String格式的时间转换成Date
	 * 
	 * @param str
	 * @param format
	 * @return
	 */
	public static Date parseDate(String str, String format) {
		if (str == null || "".equals(str)) {
			return null;
		}
		SimpleDateFormat sdf = formatMap.get(format);
		if (null == sdf) {
			sdf = new SimpleDateFormat(format, Locale.ENGLISH);
			formatMap.put(format, sdf);
		}
		try {
			synchronized (sdf) {
				// SimpleDateFormat is not thread safe
				return sdf.parse(str);
			}
		} catch (ParseException pe) {
			// TODO: handle exception
		}
		return null;
	}
	
	/**
	 * 
	 * */
	public static String formatDate(String str, String format) {
		Date d = parseDate(str, "yyyy-MM-dd HH:mm:ss");
		if(d == null){
			return null;
		}
		return formatDate(d,format);
	}
	
	// 将字符串转为时间戳

		public static long getTime(String user_time) {
			long re_time = 0;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d;
			try {
				d = sdf.parse(user_time);
				long l = d.getTime();
				String str = String.valueOf(l);
				re_time = Long.parseLong(str.substring(0, 10));
		
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return re_time;
		}

		public static String formatDate(long times) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String str_time = sdf.format(new Date(times));
			return str_time;
		}
		
		/**
	     * 格式化日期
	     * @param long unixTime unix时间戳
	     * @param String format 格式化字符串
	     * @return 日期字符串
	     * */
		public static String formatUnixTime(String timestampString){    
			Long timestamp = Long.parseLong(timestampString)*1000;    
			String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(timestamp));    
			return date;    
		}  

		public static String formatDate(long times, String format) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			String str_time = sdf.format(new Date(times));
			return str_time;
		}

		public static String formatDate(Date date) {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String str_time = sdf.format(date);
			return str_time;
		}

		public static String formatDate(Date date, String format) {

			SimpleDateFormat sdf = new SimpleDateFormat(format);
			String str_time = sdf.format(date);
			return str_time;
		}
	
	/**
	 * 隐藏键盘
	 * 
	 * */
	public static void hideSoftInput(Activity mContext) {
		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(mContext.INPUT_METHOD_SERVICE);

		if (imm != null && imm.isActive() && mContext.getCurrentFocus() != null) {
			imm.hideSoftInputFromWindow(mContext.getCurrentFocus()
					.getWindowToken(), 0);// InputMethodManager.HIDE_NOT_ALWAYS
		}
	}
	
    /**   
    * 返回当前程序版本名   
    */    
    public static String getAppVersionName(Context context) {    
        String versionName = "";    
        try {    
            // ---get the package info---    
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);    
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {    
                return "-";    
            }    
        } catch (Exception e) {    
        	versionName = "-";
        }    
        return versionName;    
    }  
    
    /**
	 * md5加密
	 */
	public static String md5(String plainText) {
		// 返回字符串
		String md5Str = null;
		try {
			// 操作字符串
			StringBuffer buf = new StringBuffer();
			MessageDigest md = MessageDigest.getInstance("MD5");

			// 添加要进行计算摘要的信息,使用 plainText 的 byte 数组更新摘要。
			md.update(plainText.getBytes());

			// 计算出摘要,完成哈希计算。
			byte b[] = md.digest();
			int i;

			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0) {
					i += 256;
				}

				if (i < 16) {
					buf.append("0");
				}

				// 将整型 十进制 i 转换为16位，用十六进制参数表示的无符号整数值的字符串表示形式。
				buf.append(Integer.toHexString(i));

			}

			// 32位的加密
			md5Str = buf.toString();

			// 16位的加密
			// md5Str = buf.toString().md5Strstring(8,24);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return md5Str;
	}
	
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
		}

	/**
	 * 删除单个文件
	 * @param   filePath    被删除文件的文件名
	 * @return 文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String filePath) {
		File file = new File(filePath);
		if (file.isFile() && file.exists()) {
			return file.delete();
		}
		return false;
	}
}
