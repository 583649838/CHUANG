package com.bestplus.chuangshangjiuzhi.view;

import com.bestplus.chuangshangjiuzhi.R;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyTitleView {
	private RelativeLayout layout;
	private TextView centerTextView;
	private ImageView leftImageView, rightImageView;
	private TextView rightTextView;

	public MyTitleView(RelativeLayout relativelayout, String centerText,
			boolean showLeftImageView, boolean showRightImageView) {
		// TODO Auto-generated constructor stub
		layout = relativelayout;
		init(centerText, showLeftImageView, showRightImageView, false);
	}

	public MyTitleView(RelativeLayout relativelayout, String centerText,
			boolean showLeftImageView, boolean showRightImageView,
			boolean showRightTextView) {
		// TODO Auto-generated constructor stub
		layout = relativelayout;
		init(centerText, showLeftImageView, showRightImageView,
				showRightTextView);
	}

	private void init(String centerText, boolean showLeftImageView,
			boolean showRightImageView, boolean showRightTextView) {
		// TODO Auto-generated method stub
		centerTextView = (TextView) layout.findViewById(R.id.title);
		leftImageView = (ImageView) layout.findViewById(R.id.leftIcon);
		rightImageView = (ImageView) layout.findViewById(R.id.rightIcon);
		rightTextView = (TextView) layout.findViewById(R.id.rightText);

		if (centerText != null && !"".equals(centerText)) {
			if (centerText.length() > 10) {
				centerText = centerText.substring(0, 10) + "...";
			}
			centerTextView.setText(centerText);
		}
		if (showLeftImageView)
			leftImageView.setVisibility(View.VISIBLE);
		if (showRightImageView)
			rightImageView.setVisibility(View.VISIBLE);
		if (showRightTextView)
			rightTextView.setVisibility(View.VISIBLE);
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
	public ImageView getLeftImageView() {
		return leftImageView;
	}

	/**
	 * 获取右边边的按钮，默认是返回按钮
	 * 
	 * @return
	 */
	public ImageView getRightImageView() {
		return rightImageView;
	}

	public TextView getRightTextView() {
		return rightTextView;
	}

    /**
     * 修改中间的标题文本
     */
    public void changeCenterText(String newString){
        if (newString != null && !"".equals(newString)) {
            if (newString.length() > 10) {
                newString = newString.substring(0, 10) + "...";
            }
            centerTextView.setText(newString);
        }
    }
}
