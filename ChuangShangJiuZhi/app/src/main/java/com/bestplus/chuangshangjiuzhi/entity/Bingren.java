package com.bestplus.chuangshangjiuzhi.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONObject;

import android.text.format.Time;

import com.bestplus.chuangshangjiuzhi.common.JsonKey;

public class Bingren {
	private String bingrenName;// 鐥呬汉鍚嶇О
	private String bingrenNameNo; // 鐥呬緥鍙�
	private String bingrenSex;// 鎬у埆
	private String bingrenAge; // 骞撮緞
	private String groupId;//绫诲埆
	
	
	
	private String bingrenArea;// 鐥呭尯
	private String bingrenRoom;// 鎵嬫湳瀹�
	private String bingrenTime;// 鎵嬫湳鏃堕棿
	private String bingrenSeniorDoc;// 涓绘不鍖荤敓
	private String bingrenOptDoc;// 鎵嬫湳鍖荤敓
	private String bingrenOptName;// 鎵嬫湳鍚�
	private String bingrenOptTime;// 鎵嬫湳鏃堕棿

	// "birthday": 379180800000,
	// "dedId": "10",
	// "hospitalizationId": "123498",
	// "identityCard": "110101198201071029",
	// "ksId": "1001",
	// "ksName": "鍛煎惛绉�",
	// "patientName": "椹帀鐟�",
	// "patientRecord": "484676",
	// "phone": "18600909027",
	// "sex": "濂�"

	// 鐥呬汉淇℃伅
	private String identityCard;// 韬唤璇�
	private String patientName;// 鐥呬汉濮撳悕
	private String patientRecord;// 鐥呬汉ID
	private String hospitalizationId;// 浣忛櫌ID
	private String sex;
	private long birthday;
	private String nativePlace;
	private String ethnic;
	private String phone;
	private String ksId;// 绉戝I
	private String ksName;// 绉戝鍚嶇О
	private String dedId;// 搴婂彿

	public Bingren(JSONObject obj) {
		// TODO Auto-generated constructor stub锛�
		this.identityCard=obj.optString(JsonKey.IdentityCard,"");
		this.patientName=obj.optString(JsonKey.PatientName,"");
		this.patientRecord=obj.optString(JsonKey.PatientRecord,"");
		this.hospitalizationId=obj.optString(JsonKey.HospitalizationId,"");
		this.sex=obj.optString(JsonKey.Sex,"");
		this.birthday=obj.optLong(JsonKey.Birthday);
		this.nativePlace=obj.optString(JsonKey.NativePlace,"");
		this.ethnic=obj.optString(JsonKey.Ethnic,"");
		this.phone=obj.optString(JsonKey.Phone,"");
		this.ksId=obj.optString(JsonKey.KsId,"");
		this.ksName=obj.optString(JsonKey.KsName,"");
		this.dedId=obj.optString(JsonKey.DedId,"");	
		
			// TODO Auto-generated constructor stub
		setBingrenName(obj.optString(JsonKey.bingrenName,""));
		setBingrenNameNo(obj.optString(JsonKey.bingrenNameNo,""));
		dedId=obj.optString(JsonKey.dedId,"");
		setBingrenSex(obj.optString(JsonKey.bingrenSex,""));
		birthday=obj.optLong(JsonKey.birthday);
		setBingrenAge(getAge(birthday));
		ksName=obj.optString(JsonKey.ksName,"");
		setGroupId(obj.optString(JsonKey.groupId,""));
		
		setBingrenArea(obj.optString(JsonKey.bingrenArea));
		setBingrenRoom(obj.optString(JsonKey.bingrenRoom));
		setBingrenTime(obj.optString(JsonKey.bingrenTime));
		setBingrenSeniorDoc(obj.optString(JsonKey.bingrenSeniorDoc));
		setBingrenOptDoc(obj.optString(JsonKey.bingrenOptDoc));
		setBingrenOptName(obj.optString(JsonKey.bingrenOptName));
		setBingrenOptTime(obj.optString(JsonKey.bingrenOptTime));
	}

	public long getBirthday() {
		return birthday;
	}

	public static String getAge(long birth) {
		
		String birthDate;
		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
		birthDate=formatter.format(birth);
		try {
			 date = formatter.parse(birthDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (date == null)
		throw new
		RuntimeException("鍑虹敓鏃ユ湡涓嶈兘涓簄ull");

		int age = 0;

		Date now = new Date();

		SimpleDateFormat format_y = new		SimpleDateFormat("yyyy");
		SimpleDateFormat format_M = new		SimpleDateFormat("MM");

		String birth_year =	format_y.format(date);
		String this_year =	format_y.format(now);

		String birth_month =format_M.format(date);
		String this_month =	format_M.format(now);

		// 鍒濇锛屼及绠�
		age =Integer.parseInt(this_year) - Integer.parseInt(birth_year);

		// 濡傛灉鏈埌鍑虹敓鏈堜唤锛屽垯age - 1
		if(this_month.compareTo(birth_month) < 0)
			age -=1;
		if (age <0)
			age =0;
			return Integer.toString(age);
		}
	public void setBirthday(long birthday) {
		this.birthday = birthday;
	}

	public String getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getPatientRecord() {
		return patientRecord;
	}

	public void setPatientRecord(String patientRecord) {
		this.patientRecord = patientRecord;
	}

	public String getHospitalizationId() {
		return hospitalizationId;
	}

	public void setHospitalizationId(String hospitalizationId) {
		this.hospitalizationId = hospitalizationId;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	public String getEthnic() {
		return ethnic;
	}

	public void setEthnic(String ethnic) {
		this.ethnic = ethnic;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getKsId() {
		return ksId;
	}

	public void setKsId(String ksId) {
		this.ksId = ksId;
	}

	public String getKsName() {
		return ksName;
	}

	public void setKsName(String ksName) {
		this.ksName = ksName;
	}

	public String getDedId() {
		return dedId;
	}

	public void setDedId(String dedId) {
		this.dedId = dedId;
	}

	public String getBingrenName() {
		return bingrenName;
	}

	public void setBingrenName(String bingrenName) {
		this.bingrenName = bingrenName;
	}

	public String getBingrenNameNo() {
		return bingrenNameNo;
	}

	public void setBingrenNameNo(String bingrenNameNo) {
		this.bingrenNameNo = bingrenNameNo;
	}

	public String getBingrenSex() {
		return bingrenSex;
	}

	public void setBingrenSex(String bingrenSex) {
		this.bingrenSex = bingrenSex;
	}

	public String getBingrenAge() {
		return bingrenAge;
	}

	public void setBingrenAge(String bingrenAge) {
		this.bingrenAge = bingrenAge;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getBingrenArea() {
		return bingrenArea;
	}

	public void setBingrenArea(String bingrenArea) {
		this.bingrenArea = bingrenArea;
	}

	public String getBingrenRoom() {
		return bingrenRoom;
	}

	public void setBingrenRoom(String bingrenRoom) {
		this.bingrenRoom = bingrenRoom;
	}

	public String getBingrenTime() {
		return bingrenTime;
	}

	public void setBingrenTime(String bingrenTime) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		Long interval = new Long(bingrenTime);
		Date now = new Date(interval);  
		
		this.bingrenTime = formatter.format(now);
	}

	public String getBingrenSeniorDoc() {
		return bingrenSeniorDoc;
	}

	public void setBingrenSeniorDoc(String bingrenSeniorDoc) {
		this.bingrenSeniorDoc = bingrenSeniorDoc;
	}

	public String getBingrenOptDoc() {
		return bingrenOptDoc;
	}

	public void setBingrenOptDoc(String bingrenOptDoc) {
		this.bingrenOptDoc = bingrenOptDoc;
	}

	public String getBingrenOptName() {
		return bingrenOptName;
	}

	public void setBingrenOptName(String bingrenOptName) {
		this.bingrenOptName = bingrenOptName;
	}

	public String getBingrenOptTime() {
		return bingrenOptTime;
	}

	public void setBingrenOptTime(String bingrenOptTime) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		Long interval = new Long(bingrenOptTime);
		Date now = new Date(interval);  
		
		this.bingrenOptTime = formatter.format(now);
	}

}
