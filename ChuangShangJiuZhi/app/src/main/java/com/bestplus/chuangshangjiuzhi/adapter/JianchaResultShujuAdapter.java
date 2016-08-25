package com.bestplus.chuangshangjiuzhi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bestplus.chuangshangjiuzhi.R;
import com.bestplus.chuangshangjiuzhi.entity.JianchaResultDetail;
import com.bestplus.chuangshangjiuzhi.entity.JianchaResultShuju;

import java.util.List;

/**
 * Created by maoamo on 2016/7/17.
 */
public class JianchaResultShujuAdapter extends BaseAdapter {
    Context context;
    List<JianchaResultShuju> list;

    public JianchaResultShujuAdapter(Context context, List<JianchaResultShuju> list) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
//        System.out.println("JianchaResultShujuAdapter  getCount count: " + list.size());
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
//        System.out.println("JianchaResultShujuAdapter  getItem position: " + position);
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
//        System.out.println("JianchaResultShujuAdapter  getItemId position: " + position);
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        System.out.println("JianchaResultShujuAdapter  getView position: " + position);
        // TODO Auto-generated method stub
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.table_jianchajiegou, null);
            holder.first = (TextView) convertView.findViewById(R.id.textview_jianchaxiangmu);
            holder.second = (TextView) convertView.findViewById(R.id.textview_cedingjiegou);
            holder.third = (ImageView) convertView.findViewById(R.id.view_up_down);
            holder.fourth = (TextView) convertView.findViewById(R.id.textview_danwei);
            holder.fifth = (TextView) convertView.findViewById(R.id.textview_cankaofanwei);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        setItemData(holder,position);
        return convertView;
    }

    private void setItemData(ViewHolder holder, int position) {
        // TODO Auto-generated method stub

        holder.first.setText(list.get(position).getItemJianyanxiangmu());
        holder.second.setText(list.get(position).getItemJianyanjieguo());
        if(1 == list.get(position).getItemJianyanjieguoUpdown()){
            holder.third.setImageResource(R.drawable.arrow_down);
        }else if(2 == list.get(position).getItemJianyanjieguoUpdown()){
            holder.third.setImageResource(R.drawable.arrow_up);
        }
        holder.fourth.setText(list.get(position).getItemJianyandanwei());
        holder.fifth.setText(list.get(position).getItemJianyancankao());
    }

    private static class ViewHolder {
        TextView first;
        TextView second;
        ImageView third;
        TextView fourth;
        TextView fifth;
    }
}
