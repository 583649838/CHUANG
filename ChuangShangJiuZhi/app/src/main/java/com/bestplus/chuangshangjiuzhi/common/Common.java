package com.bestplus.chuangshangjiuzhi.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bestplus.chuangshangjiuzhi.R;
import com.bestplus.chuangshangjiuzhi.adapter.BingrenInfoAdapter;
import com.bestplus.chuangshangjiuzhi.entity.BingrenInfo;
import com.bestplus.chuangshangjiuzhi.view.CalendarView;
import com.bestplus.chuangshangjiuzhi.view.CalendarView.OnItemClickListener;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;


@SuppressLint("NewApi")
public class Common {
	public static CalendarView calendar;
	public static ImageButton calendarLeft;
	public static TextView calendarCenter;
	public static ImageButton calendarRight;
	public static SimpleDateFormat format;
	public static PopupWindow popupWindow = null;

	// 日期popupwindow
	public static void showDatePopupWindow(Context context, final TextView view) {

		// 一个自定义的布局，作为显示的内容
		View contentView = LayoutInflater.from(context).inflate(
				R.layout.popupwindow_calendar, null);
		
		if(popupWindow != null){
			popupWindow.dismiss();
			popupWindow = null;
		}

		popupWindow = new PopupWindow(contentView,
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);

		popupWindow.setTouchable(true);

		popupWindow.setTouchInterceptor(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				return false;
				// 这里如果返回true的话，touch事件将被拦截
				// 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
			}
		});

		// 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
		// 我觉得这里是API的一个bug
		popupWindow.setBackgroundDrawable(context.getResources().getDrawable(
				R.drawable.bg_border_gray));

		// 设置好参数之后再show
		popupWindow.showAsDropDown(view);

		format = new SimpleDateFormat("yyyy-MM-dd");

		calendarLeft = (ImageButton) contentView
				.findViewById(R.id.calendarLeft);
		calendarRight = (ImageButton) contentView
				.findViewById(R.id.calendarRight);
		calendarCenter = (TextView) contentView
				.findViewById(R.id.calendarCenter);
		calendar = (CalendarView) contentView.findViewById(R.id.calendar);
		try {
			// 设置日历日期
			Date date = format.parse("2015-01-01");
			calendar.setCalendarData(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// 获取日历中年月 ya[0]为年，ya[1]为月（格式大家可以自行在日历控件中改）
		String[] ya = calendar.getYearAndmonth().split("-");
		calendarCenter.setText(ya[0] + "年" + ya[1] + "月");
		calendarLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 点击上一月 同样返回年月
				String leftYearAndmonth = calendar.clickLeftMonth();
				String[] ya = leftYearAndmonth.split("-");
				calendarCenter.setText(ya[0] + "年" + ya[1] + "月");
			}
		});

		calendarRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 点击下一月
				String rightYearAndmonth = calendar.clickRightMonth();
				String[] ya = rightYearAndmonth.split("-");
				calendarCenter.setText(ya[0] + "年" + ya[1] + "月");
			}
		});

		// 设置控件监听，可以监听到点击的每一天（大家也可以在控件中根据需求设定）
		calendar.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void OnItemClick(Date selectedStartDate,
					Date selectedEndDate, Date downDate) {
				if (calendar.isSelectMore()) {
					// Toast.makeText(
					// getApplicationContext(),
					// format.format(selectedStartDate) + "到"
					// + format.format(selectedEndDate),
					// Toast.LENGTH_SHORT).show();
				} else {
					// Toast.makeText(getApplicationContext(),
					// format.format(downDate), Toast.LENGTH_SHORT).show();
					view.setText(format.format(downDate));
					popupWindow.dismiss();
				}
			}
		});
	}

	public static void showGoodsPopupWindow(Context context,  View v) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.popupwindow_items, null);
		
		if(popupWindow != null){
			popupWindow.dismiss();
			popupWindow = null;
		}
		
		popupWindow = new PopupWindow(view,
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
		popupWindow.setTouchable(true);
		popupWindow.setTouchInterceptor(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return false;
			}
		});
		
		popupWindow.setBackgroundDrawable(context.getResources().getDrawable(
				R.drawable.bg_border_gray));
		popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);

		ListView listview = (ListView) view
				.findViewById(R.id.list);
		List<BingrenInfo> list=new ArrayList<BingrenInfo>();
		BingrenInfoAdapter adapter = new BingrenInfoAdapter(context,list);
		listview.setAdapter(adapter);
	}
		
	public static void showGoodsPopupWindow(Context context,  View v, List<BingrenInfo> items) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.popupwindow_items, null);
		
		if(popupWindow != null){
			popupWindow.dismiss();
			popupWindow = null;
		}
		
		popupWindow = new PopupWindow(view,
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT-10, true);
		popupWindow.setTouchable(true);
		popupWindow.setTouchInterceptor(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return false;
			}
		});
		
		popupWindow.setBackgroundDrawable(context.getResources().getDrawable(
				R.drawable.bg_border_gray));
		popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);

		ListView listview = (ListView) view
				.findViewById(R.id.list);
		List<BingrenInfo> list = items;
		BingrenInfoAdapter adapter = new BingrenInfoAdapter(context,list);
		listview.setAdapter(adapter);
	}
	

	public static void DismissGoodsChockOutPopupWindow(){
		if(popupWindow != null){
			popupWindow.dismiss();
			popupWindow = null;
		}
	}
}
