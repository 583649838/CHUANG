package com.bestplus.chuangshangjiuzhi.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bestplus.chuangshangjiuzhi.R;
import com.bestplus.chuangshangjiuzhi.entity.YongyaoInfo;
import com.bestplus.chuangshangjiuzhi.entity.YongyaoInfoGroup;
import com.bestplus.chuangshangjiuzhi.view.MyHScrollView;

import java.util.List;

/**
 * Created by maoamo on 2016/7/25.
 */
public class YongyaoInfoGroupAdpater extends BaseExpandableListAdapter {
    public final static int CHOICE_CONTROL_ITEM = 0X6000;
    Context context;
    List<YongyaoInfoGroup> list;
    RelativeLayout mHead;
    private Handler handler;

    public YongyaoInfoGroupAdpater(Context context, RelativeLayout mHead, List<YongyaoInfoGroup> list, Handler handler) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.list = list;
        this.mHead = mHead;
        this.handler = handler;
    }

    @Override
    public int getGroupCount() {
//        System.out.println("getGroupCount(): " + list.size());
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
//        System.out.println("getChildrenCount(" + groupPosition +"): " + list.get(groupPosition).getChildCount());
        return list.get(groupPosition).getChildCount();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getChild(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
//        System.out.println("getGroupView() -- groupPosition: " + groupPosition);
        ViewHolder holder = null;
        System.out.println("groupPosition: " + groupPosition + "isExpanded: " + isExpanded);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.table_my_hscrollview_item, null);
            holder = new ViewHolder();

            MyHScrollView scrollView1 = (MyHScrollView) convertView
                    .findViewById(R.id.horizontalScrollView1);

            holder.scrollView = scrollView1;
            holder.txt1 = (TextView) convertView
                    .findViewById(R.id.textView1);
            holder.txt2 = (TextView) convertView
                    .findViewById(R.id.textView2);
            holder.txt3 = (TextView) convertView
                    .findViewById(R.id.textView3);
            holder.txt4 = (TextView) convertView
                    .findViewById(R.id.textView4);
            holder.txt5 = (TextView) convertView
                    .findViewById(R.id.textView5);
            holder.txt6 = (TextView) convertView
                    .findViewById(R.id.textView6);
            holder.txt7 = (TextView) convertView
                    .findViewById(R.id.textView7);

            MyHScrollView headSrcrollView = (MyHScrollView) mHead.findViewById(R.id.horizontalScrollView1);
            headSrcrollView.AddOnScrollChangedListener(new OnScrollChangedListenerImp(scrollView1));

            convertView.setBackgroundColor(context.getResources().getColor(R.color.main_textview_checked));

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        setGroupItemData(holder, groupPosition);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
//        System.out.println("getChildView() -- groupPosition: " + groupPosition);
//        System.out.println("getChildView() -- childPosition: " + childPosition);

        ViewHolder holder = null;
        System.out.println("groupPosition: " + groupPosition + "childPosition: " + childPosition);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.table_my_hscrollview_item, null);
            holder = new ViewHolder();

            MyHScrollView scrollView1 = (MyHScrollView) convertView
                    .findViewById(R.id.horizontalScrollView1);

            holder.scrollView = scrollView1;
            holder.txt1 = (TextView) convertView
                    .findViewById(R.id.textView1);
            holder.txt2 = (TextView) convertView
                    .findViewById(R.id.textView2);
            holder.txt3 = (TextView) convertView
                    .findViewById(R.id.textView3);
            holder.txt8 = (TextView) convertView
                    .findViewById(R.id.textView8);
            holder.txt4 = (TextView) convertView
                    .findViewById(R.id.textView4);
            holder.txt5 = (TextView) convertView
                    .findViewById(R.id.textView5);
            holder.txt6 = (TextView) convertView
                    .findViewById(R.id.textView6);
            holder.txt7 = (TextView) convertView
                    .findViewById(R.id.textView7);

            MyHScrollView headSrcrollView = (MyHScrollView) mHead.findViewById(R.id.horizontalScrollView1);
            headSrcrollView.AddOnScrollChangedListener(new OnScrollChangedListenerImp(scrollView1));

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        convertView.setBackgroundColor(Color.LTGRAY);

        final int loc = groupPosition;

        if(childPosition == 0)
        if (handler != null) {
            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putInt("data", loc);
            msg.what = CHOICE_CONTROL_ITEM;
            msg.setData(bundle);
            handler.sendMessage(msg);
        }

        setChildItemData(holder, groupPosition, childPosition);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    class OnScrollChangedListenerImp implements MyHScrollView.OnScrollChangedListener {
        MyHScrollView mScrollViewArg;

        public OnScrollChangedListenerImp(MyHScrollView scrollViewar) {
            mScrollViewArg = scrollViewar;
        }

        @Override
        public void onScrollChanged(int l, int t, int oldl, int oldt) {
            mScrollViewArg.smoothScrollTo(l, t);
        }
    }
    private void setGroupItemData(ViewHolder holder, int position) {
        // TODO Auto-generated method stub
        holder.txt1.setText(list.get(position).getItemYongyaoxinxiZuhao());
    }

    private void setChildItemData(ViewHolder holder, int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        //holder.txt1.setText(list.get(groupPosition).getChild(childPosition).getItemYongyaoxinxiZuhao());
        holder.txt1.setText(" ");
        holder.txt2.setText(list.get(groupPosition).getChild(childPosition).getItemYongyaoxinxiBianhao());
        holder.txt3.setText(list.get(groupPosition).getChild(childPosition).getItemYongyaoxinxiYizhumingcheng());
        holder.txt8.setText(list.get(groupPosition).getChild(childPosition).getItemYongyaoxinxiYizhuLeibie());
        holder.txt4.setText(list.get(groupPosition).getChild(childPosition).getItemYongyaoxinxiPinci());
        holder.txt5.setText(list.get(groupPosition).getChild(childPosition).getItemYongyaoxinxiJiliang());
        holder.txt6.setText(list.get(groupPosition).getChild(childPosition).getItemYongyaoxinxiKaishishijian());
        holder.txt7.setText(list.get(groupPosition).getChild(childPosition).getItemYongyaoxinxiJiesushijian());
    }

    class ViewHolder {
        TextView txt1;
        TextView txt2;
        TextView txt3;
        TextView txt8;
        TextView txt4;
        TextView txt5;
        TextView txt6;
        TextView txt7;
        MyHScrollView scrollView;
    }
}
