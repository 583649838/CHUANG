package com.bestplus.chuangshangjiuzhi.common;

import com.bestplus.chuangshangjiuzhi.entity.CS_User;
import com.bestplus.chuangshangjiuzhi.entity.User;

public class Variable {
    // 叶丹凤 本机测试服务器
    //http://192.168.10.160:8080/treat/login.html
//	public static String server = "http://120.27.197.95:9089";
//	public static String server = "http://192.168.10.132:8080/gaozhihaocai";
//    public static String server = "http://192.168.10.179:8080/treat";
	public static String server = "http://120.27.197.95:8066";
	public static String padIndex = "/app/treat.html";

	public static String padChangPassword="/pad/padChangPassword.html";

	public static String padInstorerage="/pad/getListPutInByPad.html";
	public static String padGoodsListDetail="/pad/getListDetailPutInByPad.html";
	public static String padPatientInfo="/pad/getaPatientByPadraid.html";
	public static String padCheckoutGroup="/pad/getListDetailPutOutByPad.html";
	public static String padGoodsRapidcode="/pad/checkStatusByGoodsRapidcode.html";
	
	public static String Account="";
	public static String checkUser="";
	public static String checkGood="";
	public static String goodProcess="";

	
	public static User user;
    public static CS_User cs_user;
	
	public static String DES_KEY = "chuangshangjiuzhi_key";
	
	public static String password = "password";


	// 根据病例号获取该病人的基本信息
    public static String login = "login";
    public static String getPatientBaseInfoByBinglihao = "getPatientBaseInfoByBinglihao";
}
