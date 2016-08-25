package com.bestplus.chuangshangjiuzhi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bestplus.chuangshangjiuzhi.R;
import com.bestplus.chuangshangjiuzhi.entity.YongyaoInfo;
import com.bestplus.chuangshangjiuzhi.view.MyHScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maoamo on 2016/7/18.
 */
public class YongyaoInfoAdapter extends BaseAdapter {
    Context context;
    List<YongyaoInfo> list;
    RelativeLayout mHead;

    public YongyaoInfoAdapter(Context context, RelativeLayout mHead, List<YongyaoInfo> list) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.mHead = mHead;
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parentView) {
        ViewHolder holder = null;
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

            MyHScrollView headSrcrollView = (MyHScrollView) mHead
                    .findViewById(R.id.horizontalScrollView1);
            headSrcrollView
                    .AddOnScrollChangedListener(new OnScrollChangedListenerImp(
                            scrollView1));

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        setItemData(holder, position);
        return convertView;
    }

    private void setItemData(ViewHolder holder, int position) {
        // TODO Auto-generated method stub
        holder.txt1.setText(list.get(position).getItemYongyaoxinxiZuhao());
        holder.txt2.setText(list.get(position).getItemYongyaoxinxiBianhao());
        holder.txt3.setText(list.get(position).getItemYongyaoxinxiYizhumingcheng());
        holder.txt8.setText(list.get(position).getItemYongyaoxinxiYizhuLeibie());
        holder.txt4.setText(list.get(position).getItemYongyaoxinxiPinci());
        holder.txt5.setText(list.get(position).getItemYongyaoxinxiJiliang());
        holder.txt6.setText(list.get(position).getItemYongyaoxinxiKaishishijian());
        holder.txt7.setText(list.get(position).getItemYongyaoxinxiJiesushijian());
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

    class ViewHolder {
        TextView txt1;
        TextView txt2;
        TextView txt3;
        TextView txt8;
        TextView txt4;
        TextView txt5;
        TextView txt6;
        TextView txt7;
        HorizontalScrollView scrollView;
    }
}
