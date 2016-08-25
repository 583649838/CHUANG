package com.bestplus.chuangshangjiuzhi.adapter;

import android.content.Context;
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
import com.bestplus.chuangshangjiuzhi.entity.JianchaTixingInfo;
import com.bestplus.chuangshangjiuzhi.entity.JieguoNotifyInfo;

import java.util.List;

/**
 * Created by maomao on 2016/8/9.
 */
public class JieguoNotifyInfoAdapter extends BaseAdapter {
    public final static int CHOICE_CONTROL_ITEM = 0X6000;

    private Context context;
    private List<JieguoNotifyInfo> list;
    private Handler handler;

    public JieguoNotifyInfoAdapter(Context context, List<JieguoNotifyInfo> list, Handler handler) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.list_row_jianchatixiang, null);
            holder.textView_xingming = (TextView) convertView.findViewById(R.id.textView_xingming);
            holder.textView_jiuzhenhao = (TextView) convertView.findViewById(R.id.textView_jiuzhenhao);

            holder.imageView_chakan = (ImageView) convertView.findViewById(R.id.imageView_chakan);
            holder.textView_time = (TextView) convertView.findViewById(R.id.textView_time);

            holder.linearLayout_item = (LinearLayout) convertView.findViewById(R.id.linearLayout_item);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        setItemData(holder, position);
        return convertView;
    }

    private void setItemData(ViewHolder holder, int position) {
        // TODO Auto-generated method stub
        final int loc = position;
        holder.textView_xingming.setText(list.get(position).getItemHuanzheName() + "  <" + list.get(position).getItemJieguoName() + "报告已出！>");
        holder.textView_jiuzhenhao.setText(list.get(position).getItemHuanzheId());
        holder.textView_time.setText(list.get(position).getItemJieguoBaogaoshijian());

        holder.linearLayout_item.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (handler != null) {
                    Message msg = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putInt("data", loc);
                    msg.what = CHOICE_CONTROL_ITEM;
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }
            }
        });
    }

    private static class ViewHolder {
        TextView textView_xingming;
        TextView textView_jiuzhenhao;

        ImageView imageView_chakan;
        TextView textView_time;

        LinearLayout linearLayout_item;
    }
}
