package com.bestplus.chuangshangjiuzhi.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bestplus.chuangshangjiuzhi.R;
import com.bestplus.chuangshangjiuzhi.entity.JianchaInfo;

import java.util.List;

/**
 * Created by maoamo on 2016/7/17.
 */
public class JianchaInfoAdapter extends BaseAdapter {
    private final static int MAX_SHOW_CHARAC = 20;
    Context context;
    List<JianchaInfo> list;
    Handler handler;

    public JianchaInfoAdapter(Context context, List<JianchaInfo> list, Handler handler) {
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
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.tablerow_three, null);
            holder.first = (TextView) convertView.findViewById(R.id.tv_first);
            holder.second = (TextView) convertView.findViewById(R.id.tv_second);
            holder.third = (TextView) convertView.findViewById(R.id.tv_third);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        setItemData(holder,position);
        return convertView;
    }
    private void setItemData(ViewHolder holder, int position) {
        // TODO Auto-generated method stub

        holder.first.setText(list.get(position).getItemHuanzheName());
        holder.second.setText(list.get(position).getItemJianchaShijian());

        String tmp = list.get(position).getItemJianchaNeirong();
        if (tmp != null && !"".equals(tmp)) {
            if (tmp.length() > MAX_SHOW_CHARAC) {
                tmp = tmp.substring(0, MAX_SHOW_CHARAC) + "...";
            }
        }

        holder.third.setText(tmp);
    }
    private static class ViewHolder {
        TextView first;
        TextView second;
        TextView third;
    }

}
