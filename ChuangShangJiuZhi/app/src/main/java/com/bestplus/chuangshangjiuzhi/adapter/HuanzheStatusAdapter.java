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
public class HuanzheStatusAdapter extends BaseAdapter {
    public final static int CHOICE_CONTROL_HEAD = 0X6000;
    public final static int CHOICE_CONTROL_1 = 0X6001;
    public final static int CHOICE_CONTROL_2 = 0X6002;
    public final static int CHOICE_CONTROL_3 = 0X6003;
    public final static int CHOICE_CONTROL_TAIL = 0X6004;
    private Context context;
    private List<HuanzheStatus> list;
    private Handler handler;

    public HuanzheStatusAdapter(Context context, List<HuanzheStatus> list, Handler handler) {
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
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_row_guanzhuliebiao, null);
            holder.textView_xingming = (TextView) convertView.findViewById(R.id.textView_xingming);
            holder.textView_jiuzhenhao = (TextView) convertView.findViewById(R.id.textView_jiuzhenhao);
            holder.imageView_jianyan = (ImageView) convertView.findViewById(R.id.imageView_jianyan);
            holder.imageView_yizhu = (ImageView) convertView.findViewById(R.id.imageView_yizhu);
            holder.imageView_yingxiang = (ImageView) convertView.findViewById(R.id.imageView_yingxiang);
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

        if(list.get(position).getItemJianchaStatus()) {
            holder.imageView_jianyan.setImageResource(R.drawable.tixiang_jianyan);
            holder.imageView_jianyan.setOnClickListener(new View.OnClickListener() {

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
        }
        else
            holder.imageView_jianyan.setImageResource(R.drawable.tixiang_empty);

        if(list.get(position).getItemYizhuStaus()) {
            holder.imageView_yizhu.setImageResource(R.drawable.tixiang_yizhu);
            holder.imageView_yizhu.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    if(handler!=null){
                        Message msg = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putInt("data", loc);
                        msg.what = CHOICE_CONTROL_2;
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    }
                }
            });
        }
        else
            holder.imageView_yizhu.setImageResource(R.drawable.tixiang_empty);

        if(list.get(position).getItemYingxiangStatus()) {
            holder.imageView_yingxiang.setImageResource(R.drawable.tixiang_yingxiang);
            holder.imageView_yingxiang.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    if(handler!=null){
                        Message msg = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putInt("data", loc);
                        msg.what = CHOICE_CONTROL_3;
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    }
                }
            });
        }
        else
            holder.imageView_yingxiang.setImageResource(R.drawable.tixiang_empty);

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
        ImageView imageView_jianyan;
        ImageView imageView_yizhu;
        ImageView imageView_yingxiang;
        ImageView imageView_chakan;

        LinearLayout linearLayout_head;
    }
}
