package com.bestplus.chuangshangjiuzhi.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bestplus.chuangshangjiuzhi.R;
import com.bestplus.chuangshangjiuzhi.entity.HuanzheStatus;

import java.util.List;

/**
 * Created by maoamo on 2016/7/23.
 */
public class HuanzheStatusAdapter2 extends BaseAdapter {
    public final static int CHOICE_CONTROL_HEAD = 0X6000;
    public final static int CHOICE_CONTROL_1 = 0X6001;
    public final static int CHOICE_CONTROL_TAIL = 0X6002;

    private Context context;
    private List<HuanzheStatus> list;
    private Handler handler;

    public HuanzheStatusAdapter2(Context context, List<HuanzheStatus> list, Handler handler) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.list = list;
        this.handler = handler;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_row_huanzhezhongxin, null);
            holder.textView_xingming = (TextView) convertView.findViewById(R.id.textView_xingming);
            holder.textView_jiuzhenhao = (TextView) convertView.findViewById(R.id.textView_jiuzhenhao);
            holder.textView_guangzhu = (TextView) convertView.findViewById(R.id.textView_guangzhu);
            holder.imageView_chakan = (ImageView) convertView.findViewById(R.id.imageView_chakan);

            holder.linearLayout_head = (LinearLayout) convertView.findViewById(R.id.linearLayout_head);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        setItemData(holder,position);
        return convertView;
    }
    private void setItemData(ViewHolder holder, int position) {
        // TODO Auto-generated method stub
        final int loc = position;
        holder.textView_xingming.setText(list.get(position).getItemHuanzheName());
        holder.textView_jiuzhenhao.setText(list.get(position).getItemHuanzheId());

        if(list.get(position).getItemGuangzhu()) {
            holder.textView_guangzhu.setText("取消关注");
            holder.textView_guangzhu.setBackgroundColor(Color.RED);
        }
        else {
            holder.textView_guangzhu.setText("关 注");
            holder.textView_guangzhu.setBackgroundColor(context.getResources().getColor(android.R.color.holo_blue_dark));
        }

        holder.textView_guangzhu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(handler!=null){
                    Message msg = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putInt("data", loc);
                    msg.what = CHOICE_CONTROL_1;
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }
            }
        });

        holder.imageView_chakan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(handler!=null){
                    Message msg = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putInt("data", loc);
                    msg.what = CHOICE_CONTROL_TAIL;
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }
            }
        });

        holder.linearLayout_head.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(handler!=null){
                    Message msg = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putInt("data", loc);
                    msg.what = CHOICE_CONTROL_HEAD;
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }
            }
        });
    }

    private static class ViewHolder {
        TextView textView_xingming;
        TextView textView_jiuzhenhao;
        TextView textView_guangzhu;
        ImageView imageView_chakan;

        LinearLayout linearLayout_head;
    }
}
