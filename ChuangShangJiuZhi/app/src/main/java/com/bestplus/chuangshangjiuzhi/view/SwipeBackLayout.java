package com.bestplus.chuangshangjiuzhi.view;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.Scroller;

import com.bestplus.chuangshangjiuzhi.R;


/**
 * 
 * @author xiaanming
 * 
 * @blog http://blog.csdn.net/xiaanming
 * 
 */
public class SwipeBackLayout extends FrameLayout {
	private static final String TAG = SwipeBackLayout.class.getSimpleName();
	private View mContentView;
	private int mTouchSlop;
	private int downX;
	private int downY;
	private int tempX;
	private Scroller mScroller;
	private int viewWidth;
	private boolean isSilding;
	private boolean isFinish;
	private Drawable mShadowDrawable;
	private Activity mActivity;
	private List<ViewPager> mViewPagers = new LinkedList<ViewPager>();
	private List<HorizontalScrollView> mHorizontalScrollViews = new LinkedList<HorizontalScrollView>();

	public SwipeBackLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SwipeBackLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
		mScroller = new Scroller(context);

		mShadowDrawable = getResources().getDrawable(R.drawable.shadow_left);
	}

	public void attachToActivity(Activity activity) {
		mActivity = activity;
		TypedArray a = activity.getTheme().obtainStyledAttributes(
				new int[] { android.R.attr.windowBackground });
		int background = a.getResourceId(0, 0);
		a.recycle();

		ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();
		ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
		decorChild.setBackgroundResource(background);
		decor.removeView(decorChild);
		addView(decorChild);
		setContentView(decorChild);
		decor.addView(this);
	}

	private void setContentView(View decorChild) {
		mContentView = (View) decorChild.getParent();
	}

	/**
	 * 事件拦截操作
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// 处理ViewPager冲突问题
		ViewPager mViewPager = getTouchViewPager(mViewPagers, ev);
		HorizontalScrollView mHorizontalScrollView = getTouchHorizontalScrollView(
				mHorizontalScrollViews, ev);

		if (mViewPager != null && mViewPager.getCurrentItem() != 0) {
			return super.onInterceptTouchEvent(ev);
		}
		
		if(mHorizontalScrollView != null){
			return super.onInterceptTouchEvent(ev);
		}

		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downX = tempX = (int) ev.getRawX();
			downY = (int) ev.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			int moveX = (int) ev.getRawX();
			// 满足此条件屏蔽SildingFinishLayout里面子类的touch事件
			if (moveX - downX > mTouchSlop
					&& Math.abs((int) ev.getRawY() - downY) < mTouchSlop) {
				return true;
			}
			break;
		}

		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:
			int moveX = (int) event.getRawX();
			int deltaX = tempX - moveX;
			tempX = moveX;
			if (moveX - downX > mTouchSlop
					&& Math.abs((int) event.getRawY() - downY) < mTouchSlop) {
				isSilding = true;
			}

			if (moveX - downX >= 0 && isSilding) {
				mContentView.scrollBy(deltaX, 0);
			}
			break;
		case MotionEvent.ACTION_UP:
			isSilding = false;
			if (mContentView.getScrollX() <= -viewWidth / 2) {
				isFinish = true;
				scrollRight();
			} else {
				scrollOrigin();
				isFinish = false;
			}
			break;
		}

		return true;
	}

	/**
	 * 获取SwipeBackLayout里面的ViewPager的集合
	 * 
	 * @param mViewPagers
	 * @param parent
	 */
	private void getAllViewPager(List<ViewPager> mViewPagers, ViewGroup parent) {
		int childCount = parent.getChildCount();
		for (int i = 0; i < childCount; i++) {
			View child = parent.getChildAt(i);
			if (child instanceof ViewPager) {
				mViewPagers.add((ViewPager) child);
			} else if (child instanceof ViewGroup) {
				getAllViewPager(mViewPagers, (ViewGroup) child);
			}
		}
	}

	/**
	 * 获取SwipeBackLayout里面的orizontalScrollView的集合
	 * 
	 * @param mViewPagers
	 * @param parent
	 */
	private void getAllHorizontalScrollView(
			List<HorizontalScrollView> mHorizontalScrollViews, ViewGroup parent) {
		int childCount = parent.getChildCount();
		for (int i = 0; i < childCount; i++) {
			View child = parent.getChildAt(i);
			if (child instanceof HorizontalScrollView) {
				mHorizontalScrollViews.add((HorizontalScrollView) child);
			} else if (child instanceof ViewGroup) {
				getAllHorizontalScrollView(mHorizontalScrollViews,
						(ViewGroup) child);
			}
		}
	}
	
	
	/**
	 * 返回我们touch的ViewPager
	 * @param mViewPagers
	 * @param ev
	 * @return
	 */
	private ViewPager getTouchViewPager(List<ViewPager> mViewPagers,
			MotionEvent ev) {
		if (mViewPagers == null || mViewPagers.size() == 0) {
			return null;
		}
		//Rect mRect = new Rect();
		for (ViewPager v : mViewPagers) {
			//--------------
			int[] location = new  int[2] ;
			v.getLocationInWindow(location);
			int wA = location[0];
			int hA = location[1];
			
			//--------------
			//v.getHitRect(mRect);
			//(wA,hA)-(wA+w,hA+h)
			
			int w = v.getWidth();
			int h = v.getHeight();
			
			int evX = (int) ev.getX();
			int evY = (int) ev.getY();
			
			if(evX >= wA && evX <= wA+w && evY >= hA && evY <= hA+h){
				return v;
			}
			/*
			if (mRect.contains((int) ev.getX(), (int) ev.getY())) {
				return v;
			}*/
		}
		return null;
	}

	private HorizontalScrollView getTouchHorizontalScrollView(
			List<HorizontalScrollView> mHorizontalScrollViews, MotionEvent ev) {
		if (mHorizontalScrollViews == null
				|| mHorizontalScrollViews.size() == 0) {
			return null;
		}

		for (HorizontalScrollView v : mHorizontalScrollViews) {
			int[] location = new  int[2] ;
			v.getLocationInWindow(location);
			int wA = location[0];
			int hA = location[1];
			
			int w = v.getWidth();
			int h = v.getHeight();
			
			int evX = (int) ev.getX();
			int evY = (int) ev.getY();
			if(evX >= wA && evX <= wA+w && evY >= hA && evY <= hA+h){
				return v;
			}
		}
		return null;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (changed) {
			viewWidth = this.getWidth();

			getAllViewPager(mViewPagers, this);
			getAllHorizontalScrollView(mHorizontalScrollViews, this);
		}
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		if (mShadowDrawable != null && mContentView != null) {

			int left = mContentView.getLeft()
					- mShadowDrawable.getIntrinsicWidth();
			int right = left + mShadowDrawable.getIntrinsicWidth();
			int top = mContentView.getTop();
			int bottom = mContentView.getBottom();

			mShadowDrawable.setBounds(left, top, right, bottom);
			mShadowDrawable.draw(canvas);
		}

	}


	/**
	 * 滚动出界面
	 */
	private void scrollRight() {
		final int delta = (viewWidth + mContentView.getScrollX());
		// 调用startScroll方法来设置一些滚动的参数，我们在computeScroll()方法中调用scrollTo来滚动item
		mScroller.startScroll(mContentView.getScrollX(), 0, -delta + 1, 0,
				Math.abs(delta));
		postInvalidate();
	}

	/**
	 * 滚动到起始位置
	 */
	private void scrollOrigin() {
		int delta = mContentView.getScrollX();
		mScroller.startScroll(mContentView.getScrollX(), 0, -delta, 0,
				Math.abs(delta));
		postInvalidate();
	}

	@Override
	public void computeScroll() {
		// 调用startScroll的时候scroller.computeScrollOffset()返回true，
		if (mScroller.computeScrollOffset()) {
			mContentView.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			postInvalidate();

			if (mScroller.isFinished() && isFinish) {
				mActivity.finish();
			}
		}
	}


}
