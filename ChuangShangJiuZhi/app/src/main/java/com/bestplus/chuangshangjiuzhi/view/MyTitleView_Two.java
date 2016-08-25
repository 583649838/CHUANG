package com.bestplus.chuangshangjiuzhi.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bestplus.chuangshangjiuzhi.R;

public class MyTitleView_Two {
    private RelativeLayout layout;
    private TextView centerTextView;
    private ImageView leftImageView, rightImageView;
    private TextView leftTextView, rightTextView;

    public MyTitleView_Two(RelativeLayout relativelayout,
                           int centerTextId,
                           int leftImageId, int leftTextId,
                           int rightImageId, int rightTextId) {
        // TODO Auto-generated constructor stub
        layout = relativelayout;
        init(centerTextId,
                leftImageId, leftTextId,
                rightImageId, rightTextId);
    }

    public MyTitleView_Two(RelativeLayout relativelayout,
                           int centerTextId,
                           int leftImageId, int leftTextId,
                           int rightImageId, String rightText) {
        // TODO Auto-generated constructor stub
        layout = relativelayout;
        init2(centerTextId,
                leftImageId, leftTextId,
                rightImageId, rightText);
    }
    private void init2(int centerTextId, int leftImageId, int leftTextId,
                      int rightImageId, String rightText) {
        // TODO Auto-generated method stub
        centerTextView = (TextView) layout.findViewById(R.id.title);
        leftTextView = (TextView) layout.findViewById(R.id.leftText);
        leftImageView = (ImageView) layout.findViewById(R.id.leftIcon);
        rightImageView = (ImageView) layout.findViewById(R.id.rightIcon);
        rightTextView = (TextView) layout.findViewById(R.id.rightText);

        centerTextView.setText(centerTextId);
        String centerText = centerTextView.getText().toString();
        if (centerText != null && !"".equals(centerText)) {
            if (centerText.length() > 10) {
                centerText = centerText.substring(0, 10) + "...";
            }
            centerTextView.setText(centerText);
        }

        leftImageView.setImageResource(leftImageId);
        leftTextView.setText(leftTextId);

        rightImageView.setImageResource(rightImageId);
        rightTextView.setText(rightText);

    }

    private void init(int centerTextId, int leftImageId, int leftTextId,
                      int rightImageId, int rightTextId) {
        // TODO Auto-generated method stub
        centerTextView = (TextView) layout.findViewById(R.id.title);
        leftTextView = (TextView) layout.findViewById(R.id.leftText);
        leftImageView = (ImageView) layout.findViewById(R.id.leftIcon);
        rightImageView = (ImageView) layout.findViewById(R.id.rightIcon);
        rightTextView = (TextView) layout.findViewById(R.id.rightText);

        centerTextView.setText(centerTextId);
        String centerText = centerTextView.getText().toString();
        if (centerText != null && !"".equals(centerText)) {
            if (centerText.length() > 10) {
                centerText = centerText.substring(0, 10) + "...";
            }
            centerTextView.setText(centerText);
        }

        leftImageView.setImageResource(leftImageId);
        leftTextView.setText(leftTextId);

        rightImageView.setImageResource(rightImageId);
        rightTextView.setText(rightTextId);

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

    public TextView getLeftTextView() {
        return leftTextView;
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
    public void changeCenterText(String newString) {
        if (newString != null && !"".equals(newString)) {
            if (newString.length() > 10) {
                newString = newString.substring(0, 10) + "...";
            }
            centerTextView.setText(newString);
        }
    }
}
