package com.bestplus.chuangshangjiuzhi.util;

import java.io.File;

import com.bestplus.chuangshangjiuzhi.application.AppStatus;

import android.content.Context;
import android.os.Environment;



public class PathUtil
{
  public static String pathPrefix;
  public static final String imagePathName = "/image/";
  public static final String voicePathName = "/voice/";
  public static final String videoPathName = "/video/";
  public static final String apkPathName = "/apk/";
  private static File storageDir = null;
  private static PathUtil instance = null;
  private File voicePath = null;
  private File imagePath = null;
  private File videoPath = null;
  private File apkPath = null;

  public static PathUtil getInstance()
  {
    if (instance == null)
      instance = new PathUtil();
    return instance;
  }

  public void initDirs(String paramString1, String paramString2, Context paramContext)
  {
    pathPrefix = "/" + AppStatus.getInstance().getAppName() + "/";
    this.voicePath = generateVoicePath(paramString1, paramString2, paramContext);
    if (!this.voicePath.exists())
      this.voicePath.mkdirs();
    this.imagePath = generateImagePath(paramString1, paramString2, paramContext);
    if (!this.imagePath.exists())
      this.imagePath.mkdirs();
    this.videoPath = generateVideoPath(paramString1, paramString2, paramContext);
    if (!this.videoPath.exists())
      this.videoPath.mkdirs();
    this.apkPath = generateApkPath(paramString1, paramString2, paramContext);
    if (!this.apkPath.exists())
      this.apkPath.mkdirs();
  }

  public File getImagePath()
  {
    return this.imagePath;
  }

  public File getVoicePath()
  {
    return this.voicePath;
  }

  public File getVideoPath()
  {
    return this.videoPath;
  }

  private static File getStorageDir(Context paramContext)
  {
    if (storageDir == null)
    {
      File localFile = Environment.getExternalStorageDirectory();
      if (localFile.exists())
        return localFile;
      storageDir = paramContext.getFilesDir();
    }
    return storageDir;
  }

  private static File generateImagePath(String paramString1, String paramString2, Context paramContext)
  {
    String str = null;
    if (paramString1 != null && paramString2 != null)
    	str = pathPrefix + paramString1 + "/" + paramString2 + "/image/";
    else if(paramString1 != null)
    	str = pathPrefix + paramString1 + "/image/";
    else
    	str = pathPrefix + "image/";
    	
      
    return new File(getStorageDir(paramContext), str);
  }

  private static File generateVoicePath(String paramString1, String paramString2, Context paramContext)
  {
    String str = null;
    if (paramString1 != null && paramString2 != null)
    	str = pathPrefix + paramString1 + "/" + paramString2 + "/voice/";
    else if(paramString1 != null)
    	str = pathPrefix + paramString1 + "/voice/";
    else
    	str = pathPrefix + "voice/";
    return new File(getStorageDir(paramContext), str);
  }

  private static File generateVideoPath(String paramString1, String paramString2, Context paramContext)
  {
    String str = null;
    if (paramString1 != null && paramString2 != null)
    	str = pathPrefix + paramString1 + "/" + paramString2 + "/video/";
    else if(paramString1 != null)
    	str = pathPrefix + paramString1 + "/video/";
    else
    	str = pathPrefix + "video/";
    return new File(getStorageDir(paramContext), str);
  }
  
  private static File generateApkPath(String paramString1, String paramString2, Context paramContext)
  {
    String str = null;
    if (paramString1 != null && paramString2 != null)
    	str = pathPrefix + paramString1 + "/" + paramString2 + "/apk/";
    else if(paramString1 != null)
    	str = pathPrefix + paramString1 + "/apk/";
    else
    	str = pathPrefix + "apk/";
    return new File(getStorageDir(paramContext), str);
  }

}