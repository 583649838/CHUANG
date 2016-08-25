package com.bestplus.chuangshangjiuzhi.view;


import java.util.Date;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bestplus.chuangshangjiuzhi.R;
import com.bestplus.chuangshangjiuzhi.interfaces.BasicListViewInterface;


public class BasicListView extends ListView implements OnScrollListener {
    private LayoutInflater inflater;
    /**
     * 头部刷新的布局
     **/
    private LinearLayout headView;
    /**
     * 底部更多的按键
     **/
    private RelativeLayout footerView;
    /**
     * 底部更多的按键
     **/
    private TextView moreTextView;
    /**
     * 底部更多的按键
     **/
    private ProgressBar loadingView;
    /**
     * 是否获取更多中
     */
    private boolean isFetchMoreing;

    /**
     * 头部显示下拉刷新等的控件
     **/
    private TextView tipsTextview;
    /**
     * 刷新控件
     **/
    private TextView lastUpdatedTextView;
    /**
     * 箭头图标
     **/
    private ImageView arrowImageView;
    /**
     * 头部滚动条
     **/
    private ProgressBar progressBar;

    /**
     * 显示动画
     **/
    private RotateAnimation animation;
    /**
     * 头部回退显示动画
     **/
    private RotateAnimation reverseAnimation;
    /**
     * 松开更新
     **/
    private final static int RELEASE_TO_REFRESH = 0;
    /**
     * 下拉更新
     **/
    private final static int PULL_TO_REFRESH = 1;
    /**
     * 更新中
     **/
    private final static int REFRESHING = 2;
    /**
     * 无
     **/
    private final static int DONE = 3;
    /**
     * 加载中
     **/
    private final static int LOADING = 4;

    /**
     * 状态
     **/
    private int state;
    private boolean isBack;
    /**
     * 头部高度
     **/
    private int headContentHeight;
    /**
     * 开始的Y坐标
     **/
    private int startY;
    /**
     * 实际的padding的距离与界面上偏移距离的比例
     **/
    private final static int RATIO = 3;

    /**
     * 用于保证startY的值在一个完整的touch事件中只被记录一次
     **/
    private boolean isRecored;
    /**
     * 第一个item
     **/
    private int firstItemIndex;

    private boolean enableHeaderView = true;
    private boolean enableFooterView = true;

    /**
     * 如果activity没有调用setOnBookResourceListViewListener，则默认使用该实例化，防止报空指针异常，
     * 当然不设置setOnBookResourceListViewListener，回调函数onRefresh就不会起作用
     */
    private BasicListViewInterface basicListViewInterface = new BasicListViewInterface() {

        @Override
        public boolean onRefresh(MotionEvent ev) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public void onMoreLoadding() {
            // TODO Auto-generated method stub

        }
    };

    public BasicListView(Context context) {
        super(context);

    }

    public BasicListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        if (enableHeaderView) {
            inflater = LayoutInflater.from(context);
            headView = (LinearLayout) inflater.inflate(
                    R.layout.basic_list_head, null);
            arrowImageView = (ImageView) headView
                    .findViewById(R.id.head_arrowImageView);
            arrowImageView.setMinimumWidth(70);
            arrowImageView.setMinimumHeight(50);
            progressBar = (ProgressBar) headView
                    .findViewById(R.id.head_progressBar);
            tipsTextview = (TextView) headView
                    .findViewById(R.id.head_tipsTextView);
            lastUpdatedTextView = (TextView) headView
                    .findViewById(R.id.head_lastUpdatedTextView);
            measureView(headView);
            headContentHeight = headView.getMeasuredHeight();
            headView.setPadding(0, -1 * headContentHeight, 0, 0);
            headView.invalidate();

            /** 列表添加头部 **/
            addHeaderView(headView, null, false);
            // 添加监听
            setOnScrollListener(this);
            // 箭头翻转动画
            animation = new RotateAnimation(0, -180,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            animation.setInterpolator(new LinearInterpolator());
            animation.setDuration(250);
            animation.setFillAfter(true);
            reverseAnimation = new RotateAnimation(-180, 0,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f);

            reverseAnimation.setInterpolator(new LinearInterpolator());
            reverseAnimation.setDuration(200);
            reverseAnimation.setFillAfter(true);
        }
        if (enableFooterView) {
            footerView = (RelativeLayout) LayoutInflater.from(context).inflate(
                    R.layout.basic_list_footer, null);
            moreTextView = (TextView) footerView
                    .findViewById(R.id.book_source_list_footer_more);
            loadingView = (ProgressBar) footerView
                    .findViewById(R.id.book_source_list_footer_loading);
            footerView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isFetchMoreing && state != REFRESHING) {

                        isFetchMoreing = true;
                        moreTextView.setText("加载更多中...");
                        loadingView.setVisibility(View.VISIBLE);
                        basicListViewInterface.onMoreLoadding();
                    }
                }
            });
            addFooterView(footerView);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (enableHeaderView) {
            final int action = ev.getAction();
            cancelLongPress();
            switch (action) {
                case MotionEvent.ACTION_DOWN: {
                    if (firstItemIndex == 0 && state != REFRESHING) {
                        isRecored = true;
                        startY = (int) ev.getY();
                    }
                    break;
                }

                case MotionEvent.ACTION_MOVE: {
                    if (isRecored && !isFetchMoreing) {
                        int tempY = (int) ev.getY();
                        headView.setPadding(0, (tempY - startY) / RATIO
                                - headContentHeight, 0, 0);
                        // 当状态是松手释放刷新
                        if (state == RELEASE_TO_REFRESH) {
                            // 往上推了，推到了屏幕足够掩盖head的程度，但是还没有推到全部掩盖的地步
                            setSelection(0);
                            if (((tempY - startY) / RATIO < headContentHeight)
                                    && (tempY - startY) > 0) {
                                state = PULL_TO_REFRESH;
                                changeHeaderViewByState();

                            }
                            // 一下子推到顶了
                            else if (tempY - startY <= 0) {
                                state = DONE;
                                changeHeaderViewByState();
                            }
                            // 往下拉了，或者还没有上推到屏幕顶部掩盖head的地步
                            else {
                                // 不用进行特别的操作，只用更新paddingTop的值就行了
                            }
                        }
                        // 还没有到达显示松开刷新的时候,DONE或者是PULL_TO_REFRESH状态
                        if (state == PULL_TO_REFRESH) {
                            setSelection(0);
                            // 下拉到可以进入RELEASE_TO_REFRESH的状态
                            if ((tempY - startY) / RATIO >= headContentHeight) {
                                state = RELEASE_TO_REFRESH;
                                isBack = true;
                                changeHeaderViewByState();
                            }
                            // 上推到顶了
                            else if (tempY - startY <= 0) {
                                state = DONE;
                                changeHeaderViewByState();
                            }
                        }

                        // DONE状态下
                        if (state == DONE) {
                            if (tempY - startY > 0) {
                                state = PULL_TO_REFRESH;
                                changeHeaderViewByState();
                            }
                        }
                    }
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    if (state != REFRESHING && state != LOADING) {
                        if (state == DONE) {
                            // 什么都不做
                        }
                        if (state == PULL_TO_REFRESH) {
                            state = DONE;
                            changeHeaderViewByState();
                        }

                        if (state == RELEASE_TO_REFRESH) {
                            state = REFRESHING;
                            changeHeaderViewByState();
                            basicListViewInterface.onRefresh(ev);
                        }
                    }
                    isRecored = false;
                    break;
                }
            }
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        firstItemIndex = firstVisibleItem;

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // TODO Auto-generated method stub

    }

    /**
     * 一个MeasureSpec封装了父布局传递给子布局的布局要求，每个MeasureSpec代表了一组宽度和高度的要求。
     * 一个MeasureSpec由大小和模式组成
     * 。它有三种模式：UNSPECIFIED(未指定),父元素部队自元素施加任何束缚，子元素可以得到任意想要的大小
     * ；EXACTLY(完全)，父元素决定自元素的确切大小
     * ，子元素将被限定在给定的边界里而忽略它本身大小；AT_MOST(至多)，子元素至多达到指定大小的值。
     * <p/>
     * 　　它常用的三个函数： 　　1.static int getMode(int
     * measureSpec):根据提供的测量值(格式)提取模式(上述三个模式之一) 　　2.static int getSize(int
     * measureSpec):根据提供的测量值(格式)提取大小值(这个大小也就是我们通常所说的大小) 　　3.static int
     * makeMeasureSpec(int size,int mode):根据提供的大小值和模式创建一个测量值(格式)
     * <p/>
     * measureSpec方法通常在ViewGroup中用到，它可以根据模式(MeasureSpec里面的三个)可以调节子元素的大小。
     * <p/>
     * 等onCreate方法执行完了,我们定义的控件才会被度量(measure),所以我们在onCreate方法里面通过view.getHeight()
     * 获取控件的高度或者宽度肯定是0,因为它自己还没有被度量,也就是说他自己都不知道自己有多高,而你这时候去获取它的尺寸,肯定是不行的.
     * <p/>
     * 所以一般使用一下方法 int w =
     * View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED); int h =
     * View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
     * imageView.measure(w, h); int height =imageView.getMeasuredHeight(); int
     * width =imageView.getMeasuredWidth();
     * textView.append("\n"+height+","+width);
     *
     * @param child
     */
    private void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
                    MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    /**
     * 当状态改变时候，调用该方法，以更新界面
     */
    private void changeHeaderViewByState() {
        switch (state) {
            case RELEASE_TO_REFRESH:
                arrowImageView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                tipsTextview.setVisibility(View.VISIBLE);
                lastUpdatedTextView.setVisibility(View.VISIBLE);

                arrowImageView.clearAnimation();
                arrowImageView.startAnimation(animation);

                tipsTextview.setText("松开刷新");
                break;
            case PULL_TO_REFRESH:
                progressBar.setVisibility(View.GONE);
                tipsTextview.setVisibility(View.VISIBLE);
                lastUpdatedTextView.setVisibility(View.VISIBLE);
                arrowImageView.clearAnimation();
                arrowImageView.setVisibility(View.VISIBLE);
                // 是由RELEASE_To_REFRESH状态转变来的
                if (isBack) {
                    isBack = false;
                    arrowImageView.clearAnimation();
                    arrowImageView.startAnimation(reverseAnimation);

                    tipsTextview.setText("下拉刷新");
                } else {
                    tipsTextview.setText("下拉刷新");
                }

                break;

            case REFRESHING:

                headView.setPadding(0, 0, 0, 0);
                progressBar.setVisibility(View.VISIBLE);
                arrowImageView.clearAnimation();
                arrowImageView.setVisibility(View.GONE);
                tipsTextview.setText("正在刷新...");
                lastUpdatedTextView.setVisibility(View.VISIBLE);
                break;
            case DONE:
                headView.setPadding(0, -1 * headContentHeight, 0, 0);
                progressBar.setVisibility(View.GONE);
                arrowImageView.clearAnimation();
                arrowImageView.setImageResource(R.drawable.pull_down_arrow);
                tipsTextview.setText("下拉刷新");
                lastUpdatedTextView.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * 设置监听器
     *
     * @param listener
     */
    public void setOnBookResourceListViewListener(BasicListViewInterface listener) {
        basicListViewInterface = listener;
    }

    public void setEnableHeaderView(boolean enableHeaderView) {
        this.enableHeaderView = enableHeaderView;
    }

    public void setEnableFooterView(boolean enableFooterView) {
        this.enableFooterView = enableFooterView;
    }

    public void refreshComplete() {
        mUIHandler.sendEmptyMessage(0);
    }

    public void moreLoaddingComplete() {
        mUIHandler.sendEmptyMessage(1);
    }

    private Handler mUIHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0: {
                    state = DONE;
                    lastUpdatedTextView.setText("最近更新:"
                            + new Date().toLocaleString());
                    changeHeaderViewByState();
                    return;
                }
                case 1: {
                    isFetchMoreing = false;
                    moreTextView.setText("点击加载更多");
                    loadingView.setVisibility(View.GONE);
                    return;
                }
            }
        }

    };
}


