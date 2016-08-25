package com.bestplus.chuangshangjiuzhi.view;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bestplus.chuangshangjiuzhi.R;

/**
 * @author rx
 * @version 创建时间：2012-9-5 下午4:07:46 类说明
 */
public class TitleView {
	private RelativeLayout layout;
	private TextView centerTextView;
	private Button leftButton, rightButton;
	private ProgressBar progressBar;
	private RelativeLayout searchRl;
	private TextView search_edit;
	private TextView left_sign , right_sign;
	private String left_sign_content="",right_sign_content="";

	public TitleView(RelativeLayout relativelayout, String centerText,
			boolean showLeftButton, boolean showRightButton, String rightText) {
		layout = relativelayout;
		init(centerText, showLeftButton, showRightButton, rightText);
	}
	public TitleView(RelativeLayout relativelayout, String centerText,
			boolean showLeftButton, boolean showRightButton,String rightText, String left_sign_content ,String right_sign_content) {
		layout = relativelayout;
		this.left_sign_content = left_sign_content;
		this.right_sign_content =right_sign_content;
		init(centerText, showLeftButton, showRightButton, rightText);
	}
	public TitleView(RelativeLayout relativelayout, String centerText,
			boolean showLeftButton) {
		layout = relativelayout;
		init(centerText, showLeftButton, false, null);
	}
	public TitleView(RelativeLayout relativelayout, String centerText,
			boolean showLeftButton,	boolean showrightButton) {
		layout = relativelayout;
		init(centerText, showLeftButton, showrightButton, null);
	}

	public TitleView(RelativeLayout relativelayout, String centerText) {
		layout = relativelayout;
		init(centerText, false, false, null);
	}
	
	private void init(String centerText, boolean showLeftButton,
			boolean showRightButton, String rightText) {
		centerTextView = (TextView) layout.findViewById(R.id.title_center_text);
		progressBar = (ProgressBar) layout.findViewById(R.id.title_progress);
		left_sign=(TextView)layout.findViewById(R.id.title_left_sign);
		right_sign=(TextView)layout.findViewById(R.id.title_right_sign);
		left_sign.setText(left_sign_content);
		right_sign.setText(right_sign_content);
		if (centerText != null && !"".equals(centerText))
			centerTextView.setText(centerText);
		if (showLeftButton) {
//			leftButton.setText(R.string.back);
			leftButton.setVisibility(View.VISIBLE);
		}

		if (showRightButton) {// && rightText != null && !"".equals(rightText)
			rightButton.setText(rightText);
			rightButton.setVisibility(View.VISIBLE);
		}
	}
	/**
	 * 获取中间的TextView控件
	 * 
	 * @return
	 */
	public TextView getTextView() {
		return centerTextView;
	}
	/**
	 * 获取左边的按钮，默认是返回按钮
	 * 
	 * @return
	 */
	public Button getLeftButton() {
		return leftButton;
	}
	/**
	 * 获取右边的正常按钮按钮
	 * 
	 * @return
	 */
	public ProgressBar getProgressBar() {
		return progressBar;
	}
	
	/**
	 * 获取加载条
	 * */
	public Button getRightButton() {
		return rightButton;
	}
	
	/**
	 * 获取中间的搜索框控件
	 * 
	 * @return
	 */
	public RelativeLayout getRelativeLayout() {
		return searchRl;
	}
	
	/**
	 * 获取中间的搜索框
	 * @return
	 */
	public TextView getSearchEdit(){
		return search_edit;
	}

}
