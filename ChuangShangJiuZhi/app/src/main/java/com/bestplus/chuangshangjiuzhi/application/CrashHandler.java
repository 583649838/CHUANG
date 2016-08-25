package com.bestplus.chuangshangjiuzhi.application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import com.bestplus.chuangshangjiuzhi.common.FileCst;
import com.bestplus.chuangshangjiuzhi.util.DebugLog;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
 
/**
 * @author
 * @version 创建时间：2014-9-5 上午09:57:10 类说明
 */
public class CrashHandler implements UncaughtExceptionHandler {

	NotificationManager m_NotificationManager;
	ActivityManager a;
	/** Debug Log tag */
	public static final String TAG = "CrashHandler";
	/**
	 * 是否开启日志输出,在Debug状态下开启, 在Release状态下关闭以提示程序性能
	 * */
	public static final boolean DEBUG = true;
	/** 系统默认的UncaughtException处理类 */
	private Thread.UncaughtExceptionHandler mDefaultHandler;
	/** CrashHandler实例 */
	private static CrashHandler INSTANCE;
	/** 程序的Context对象 */
	private Context mContext;
	/** 文件目录 */
	private File m_fileDir;
	private Handler m_hMsgHandler;

	/** 使用Properties来保存设备的信息和错误堆栈信息 */
	private Properties mDeviceCrashInfo = new Properties();
	private static final String VERSION_NAME = "versionName";
	private static final String VERSION_CODE = "versionCode";
	private static final String STACK_TRACE = "STACK_TRACE";
	/** 错误报告文件的扩展名 */
	private static final String CRASH_REPORTER_EXTENSION = ".txt";

	/** 保证只有一个CrashHandler实例 */
	private CrashHandler() {
	}

	/** 获取CrashHandler实例 ,单例模式 */
	public static CrashHandler getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CrashHandler();
		}
		return INSTANCE;
	}

	/**
	 * 初始化,注册Context对象, 获取系统默认的UncaughtException处理器, 设置该CrashHandler为程序的默认处理器
	 * 
	 * @param ctx
	 */
	public void init(Context ctx) {
		mContext = ctx;
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
		// 如果有SD卡，文件夹保存于SD卡中，如果没有，保存在默认路径中（/data/data/android/com.ChatRoom/文件夹）
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			m_fileDir = new File(Environment.getExternalStorageDirectory(),
					FileCst.DIR_CRASH);
		} else {
			m_fileDir = new File("ErrorReport");
		}
		if (!m_fileDir.isDirectory()) {
			m_fileDir.mkdirs();
		}
	}

	/**
	 * 当UncaughtException发生时会转入该函数来处理
	 */
	public void uncaughtException(Thread thread, Throwable ex) {
		if (!handleException(ex) && mDefaultHandler != null) {
			// 如果用户没有处理则让系统默认的异常处理器来处理
			mDefaultHandler.uncaughtException(thread, ex);
			System.exit(0);
		} else {
			// Sleep一会后结束程序
			try {
				m_NotificationManager = (NotificationManager) mContext
						.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
				m_NotificationManager.cancelAll();
				Thread.sleep(3000);
				// m_hMsgHandler.sendMessage(m_hMsgHandler.obtainMessage(GlobalDef.EX_OCCUR,
				// 0, 0, 0));
				DebugLog.i(TAG, "m_hMsgHandler.sendMessage(GlobalDef.EX_OCCUR) ok!");
			} catch (InterruptedException e) {
				Log.e(TAG, "Error : ", e);
			}

			exitClient(mContext);
		}
	}

	/**
	 * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
	 * 
	 * @param ex
	 * @return true:如果处理了该异常信息;否则返回false
	 */
	private boolean handleException(Throwable ex) {
		if (ex == null) {
			return true;
		}
		// final String msg = ex.getLocalizedMessage();
		// 使用Toast来显示出错信息
		/*  //------错误时提示
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				Toast.makeText(mContext, "程序出错啦！", Toast.LENGTH_LONG).show();
				Looper.loop();
			}
		}.start();*/

		// 收集设备信息
		collectCrashDeviceInfo(mContext);
		// 保存错误报告文件
		String crashFileName = saveCrashInfoToFile(ex);
		// 发送错误报告到服务器
		sendCrashReportsToServer();
		return true;
	}

	/**
	 * 在程序启动时候, 可以调用该函数来发送以前没有发送的报告
	 */
	public void sendPreviousReportsToServer() {
		File sd_fileDir;
		File de_fileDir;
		try {
			if (Environment.MEDIA_MOUNTED.equals(Environment
					.getExternalStorageState())) {
				sd_fileDir = new File(
						Environment.getExternalStorageDirectory(),
						FileCst.DIR_CRASH);
				if (sd_fileDir.exists()) {
					File crFile[] = sd_fileDir.listFiles();
					if (crFile != null) {
						DebugLog.i(TAG, "sendCrashReportsToServer, SDcard file number : "+ crFile.length);
							
						File cr;
						for (int i = 0; i < crFile.length; i++) {
							cr = crFile[i];
							postReport(cr);
							// cr.delete();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			de_fileDir = new File("ErrorReport");
			if (de_fileDir.exists()) {
				File crFile[] = de_fileDir.listFiles();
				if (crFile != null) {
					DebugLog.i(TAG, "&sendCrashReportsToServer, default file number : "+ crFile.length);
						
					File cr;
					for (int i = 0; i < crFile.length; i++) {
						cr = crFile[i];
						postReport(cr);
						// cr.delete();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 把错误报告发送给服务器,新产生的错误报告.
	 * 
	 * @param ctx
	 */
	private void sendCrashReportsToServer() {
		try {
			if (m_fileDir.exists()) {
				File crFile[] = m_fileDir.listFiles();
				if (crFile != null) {
					DebugLog.i(TAG, "sendCrashReportsToServer, new file number : "+ crFile.length);
						
					File cr;
					for (int i = 0; i < crFile.length; i++) {
						cr = crFile[i];
						postReport(cr);
						// cr.delete();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void postReport(File file) {
		// TODO 使用HTTP Post 发送错误报告到服务器
		// 这里不再详述,开发者可以根据OPhoneSDN上的其他网络操作
		// 教程来提交错误报告
		// if(isNetworkAvailable(ctx)==true){

		// }
	}

	/**
	 * 获取错误报告文件名
	 * 
	 * @param ctx
	 * @return
	 */
	/*
	 * private String[] getCrashReportFiles(Context ctx) { File filesDir =
	 * ctx.getFilesDir();
	 * 
	 * FilenameFilter filter = new FilenameFilter() { public boolean accept(File
	 * dir, String name) { return name.endsWith(CRASH_REPORTER_EXTENSION); } };
	 * return filesDir.list(filter); }
	 */
	/**
	 * 保存错误信息到文件中
	 * 
	 * @param ex
	 * @return
	 */
	private String saveCrashInfoToFile(Throwable ex) {
		Writer info = new StringWriter();
		PrintWriter printWriter = new PrintWriter(info);
		ex.printStackTrace(printWriter);

		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}

		String result = info.toString();
//		MobclickAgent.reportError(mContext, result);友盟统计代码，暂时先注释掉
		printWriter.close();
		try {
			mDeviceCrashInfo.put(STACK_TRACE, result);
		} catch (Exception e) {
			Log.e(TAG, "an error occured while putting StackTrace info...", e);
		}
		String fileName = null;
		try {
			// long timestamp = System.currentTimeMillis();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd-HHmmss");
			Date curDate = new Date(System.currentTimeMillis());
			String timestamp = formatter.format(curDate);
			File file;
			DebugLog.i(TAG, "timestamp is :" + timestamp);
			
			fileName = "crash-" + timestamp + CRASH_REPORTER_EXTENSION;
			file = new File(m_fileDir, fileName);
			DebugLog.i(TAG, "new Exception file occur : " + fileName);
			
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream trace = new FileOutputStream(file);
			mDeviceCrashInfo.store(trace, "");
			trace.flush();
			trace.close();
			// FileOutputStream trace = mContext.openFileOutput(fileName,
			// Context.MODE_WORLD_WRITEABLE);
			// if(UserStatus.DeBUG)
			// Log.i(TAG, "Exception info : "+mDeviceCrashInfo.toString());

		} catch (Exception e) {
			Log.e(TAG, "an error occured while writing report file...", e);
		}
		return fileName;
	}

	/**
	 * 收集程序设备的信息
	 * 
	 * @param ctx
	 */
	public void collectCrashDeviceInfo(Context ctx) {
		try {
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
					PackageManager.GET_ACTIVITIES);
			if (pi != null) {
				mDeviceCrashInfo.put(VERSION_NAME,
						pi.versionName == null ? "not set" : pi.versionName);
				// mDeviceCrashInfo.put(VERSION_CODE, pi.versionCode);
			}
		} catch (NameNotFoundException e) {
			Log.e(TAG, "Error while collect package info", e);
		}
		// 使用反射来收集设备信息.在Build类中包含各种设备信息,
		// 例如: 系统版本号,设备生产商 等帮助调试程序的有用信息
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				// mDeviceCrashInfo.put(field.getName(), field.get(null));
				if (DEBUG) {
					Log.d(TAG, field.getName() + " : " + field.get(null));
				}
			} catch (Exception e) {
				Log.e(TAG, "Error while collect crash info", e);
			}

		}

	}

	public void SetExHandler(Handler hHandler) {
		m_hMsgHandler = hHandler;
		DebugLog.i(TAG, "SetExHandler ok!");
	}

	public void exitClient(Context context) {
		DebugLog.i(TAG, "----- exitClient -----");
		AppStatus.getInstance().AppExit(context);
	}

}
