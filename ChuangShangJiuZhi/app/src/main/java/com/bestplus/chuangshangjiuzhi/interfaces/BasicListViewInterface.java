package com.bestplus.chuangshangjiuzhi.interfaces;

import android.view.MotionEvent;
import android.view.View;

public interface BasicListViewInterface {
	/**
	 * 手指触摸后提起触发，相当于{@link MotionEvent#ACTION_UP}
	 * 
	 * @return 返回true表示自己处理
	 * @see View#onTouchEvent(MotionEvent)
	 */
	public boolean onRefresh(MotionEvent ev);

	/**
	 * 控件底部加载更多资料
	 */
	public void onMoreLoadding();
}

