package com.bestplus.chuangshangjiuzhi.application;

import java.io.File;
import java.util.Stack;


import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.bestplus.chuangshangjiuzhi.util.DebugLog;
import com.bestplus.chuangshangjiuzhi.util.PathUtil;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;



public class AppStatus extends Application{
public String appName  = "feihuoliang"; //sdcard保存目录
	
	private static AppStatus instance;
	public static boolean isTest  = true; //是否是测试环境
	public static boolean isFirst = false; //是否是安装后第一次登陆
	private String isNull; //清除缓存引起的重启
	private static Stack<Activity> activityStack; //Activity堆栈
	public static DisplayImageOptions options; //通用图片加载参数 - 使用背景图
	
	private  boolean isLogin = false;
	private Object SQL_LOCK = new Object(); //锁

	private String HTTP_WEB = "http://www.baidu.com/";
	private static Context context;

	@Override
 	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		instance = this;
 	
		PathUtil.getInstance().initDirs(null,null,getApplicationContext());
		
		initImageLoader(getApplicationContext()); //图片加载库
		
		context = getApplicationContext();
	}
	
	public static Context getContext() {
		return context;
	}
	
	private void initImageLoader(Context  context){
		File cacheDir = StorageUtils.getCacheDirectory(context);
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
		  .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
//		  .diskCacheExtraOptions(480, 800, CompressFormat.JPEG, 75, null)
//		  .taskExecutor(...)
//		  .taskExecutorForCachedImages(...)
		  .threadPoolSize(3) // default
		  .threadPriority(Thread.NORM_PRIORITY - 1) // default
		  .tasksProcessingOrder(QueueProcessingType.FIFO) // default
		  .denyCacheImageMultipleSizesInMemory()
		  .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
		  .memoryCacheSize(2 * 1024 * 1024)
		  .memoryCacheSizePercentage(13) // default
		  .diskCache(new UnlimitedDiscCache(cacheDir)) // default
		  .diskCacheSize(50 * 1024 * 1024)
		  .diskCacheFileCount(100)
		  .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
		  .imageDownloader(new BaseImageDownloader(context)) // default
//		  .imageDecoder(new BaseImageDecoder()) // default
		  .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
		  .writeDebugLogs()
		  .build();
		
		//图片加载参数
		options = new DisplayImageOptions.Builder()
			.cacheInMemory(true) // 设置下载的图片是否缓存在内存中  
			.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中  
			.considerExifParams(true)
			.bitmapConfig(Bitmap.Config.ARGB_8888)// 设置图片的解码类型,默认值——Bitmap.Config.ARGB_8888  
			.build();
		
		ImageLoader.getInstance().init(config);
	}
	
//	private void initImageLoader(Context context) {
//		// This configuration tuning is custom. You can tune every option, you may tune some of them,
//		// or you can create default configuration by
//		//  ImageLoaderConfiguration.createDefault(this);
//		// method.
//		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
//				.threadPriority(Thread.NORM_PRIORITY - 2)
//				//.memoryCache(new LruMemoryCache(4 * 1024 * 1024))
//				//.memoryCacheSize(4 * 1024 * 1024)  
//				//当同一个Uri获取不同大小的图片，缓存到内存时，只缓存一个。默认会缓存多个不同的大小的相同图片 
//				.denyCacheImageMultipleSizesInMemory()
//				//设置缓存文件的名字 
//				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
//				//设置图片下载和显示的工作队列排序
//				.tasksProcessingOrder(QueueProcessingType.LIFO)
//				//.diskCacheSize(50 * 1024 * 1024) // 50 Mb
//				//日志
//				//.writeDebugLogs() // Remove for release app
//				.build();
//		
//		//图片加载参数
//		options = new DisplayImageOptions.Builder()
//			.cacheInMemory(true) // 设置下载的图片是否缓存在内存中  
//			.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中  
//			.considerExifParams(true)
//			.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型,默认值——Bitmap.Config.ARGB_8888  
//			.build();
//		
//		// Initialize ImageLoader with configuration.
//		ImageLoader.getInstance().init(config);
//	}
	
	public static AppStatus getInstance() {
		return instance;
	}
	
	/**
	 * 添加Activity到堆栈
	 */
	public synchronized static void register(Activity activity){
		if(activityStack == null){
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}
	
	/**
	 * 结束指定的Activity
	 */
	public synchronized static void unregister(Activity activity){
		if(activity != null){
			activityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}
	
	/**
	 * 输出日志
	 * */
	public static void echoActivityStack(){
		for (int i = activityStack.size() - 1; i >= 0; i--) {
			DebugLog.d("activityStack", activityStack.get(i).getClass().getName());
		}
	}
	
	/**
	 * 根据名称获取Activity
	 * */
	public static Activity getActivityByName(String name) {

		for (int i = activityStack.size() - 1; i >= 0; i--) {
			Activity ac = activityStack.get(i);
			if (ac.isFinishing())
				continue;
			if (ac.getClass().getName().indexOf(name) >= 0) {
				return ac;
			}
		}
		return null;
	}
	
	/**
	 * 获取当前Activity（堆栈中最后一个压入的）
	 */
	public Activity currentActivity(){
		Activity activity = activityStack.lastElement();
		return activity;
	}
	
	/**
	 * 结束当前Activity（堆栈中最后一个压入的）
	 */
	public void finishActivity(){
		Activity activity = activityStack.lastElement();
		unregister(activity);
	}
	
	/**
	 * 结束指定类名的Activity
	 */
	public static void finishActivity(Class<?> cls){
		for (Activity activity : activityStack) {
			if(activity.getClass().equals(cls) ){
				unregister(activity);
			}
		}
	}
	
	/**
	 * 删除 [当前activity、main] 外其它的 
	 * */
	public static void finishOtherActivity(Activity ac){
		for (int i = activityStack.size() - 1; i >= 0; i--){
			Activity acStack = activityStack.get(i);
			if(acStack != ac && acStack.getClass().getName().indexOf("HuanzheMainActivity") < 0){
				unregister(acStack);
			}
	    }
	}
	
	/**
	 * 结束所有Activity
	 */
	public void finishAllActivity(){
		for (int i = 0, size = activityStack.size(); i < size; i++){
            if (null != activityStack.get(i)){
            	activityStack.get(i).finish();
            }
	    }
		activityStack.clear();
	}
	
	/**
	 * 退出应用程序
	 */
	public void AppExit(Context context) {
		try {
			finishAllActivity();
			ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.restartPackage(context.getPackageName());
			System.exit(0);
		} catch (Exception e) {	}
	}

	public String getAppName() {
		return appName;
	}

	public boolean isTest() {
		return isTest;
	}

	public boolean isFirst() {
		return isFirst;
	}

	public void setFirst(boolean isFirst) {
		this.isFirst = isFirst;
	}

 

	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	public String getIsNull() {
		return isNull;
	}

	public void setIsNull(String isNull) {
		this.isNull = isNull;
	}

	public Object getSQL_LOCK() {
		return SQL_LOCK;
	}

	public String getHTTP_WEB() {
		return HTTP_WEB;
	}

	public DisplayImageOptions getOptions() {
		return options;
	}
}
