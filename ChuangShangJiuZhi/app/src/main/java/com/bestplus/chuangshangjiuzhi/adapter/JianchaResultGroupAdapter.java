package com.bestplus.chuangshangjiuzhi.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bestplus.chuangshangjiuzhi.R;
import com.bestplus.chuangshangjiuzhi.entity.JianchaResultGroup;
import com.bestplus.chuangshangjiuzhi.entity.JianchaResultShuju;

import java.util.List;

/**
 * Created by maoamo on 2016/7/25.
 * 检查结果列表分组显示--适配器实现
 */
public class JianchaResultGroupAdapter extends BaseExpandableListAdapter {
    public final static int CHOICE_CONTROL_ITEM = 0X6000;
    Context context;
    List<JianchaResultGroup> list;
    private Handler handler;

    public JianchaResultGroupAdapter(Context context, List<JianchaResultGroup> list, Handler handler) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.list = list;
        this.handler = handler;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
//        return list.get(groupPosition).getChildCount();
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition);
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
        ViewGroupHolder holder = null;
        System.out.println("groupPosition: " + groupPosition + "isExpanded: " + isExpanded);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_group_head, null);
            holder = new ViewGroupHolder();

            holder.txt1 = (TextView) convertView.findViewById(R.id.textView_no);
            holder.txt2 = (TextView) convertView.findViewById(R.id.textView_mingcheng);
            holder.txt3 = (TextView) convertView.findViewById(R.id.textView_shijian);

            convertView.setTag(holder);
        } else {
            holder = (ViewGroupHolder) convertView.getTag();
        }

        setGroupItemData(holder, groupPosition);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewChildHolder holder = null;
        System.out.println("groupPosition: " + groupPosition + "childPosition: " + childPosition);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_child_jianchaxiangxi, null);
            holder = new ViewChildHolder();

            holder.txt1 = (TextView) convertView.findViewById(R.id.textView_songjianshijian);
            holder.txt2 = (TextView) convertView.findViewById(R.id.textView_jianchashijian);
            holder.txt3 = (TextView) convertView.findViewById(R.id.textView_songjianyisheng);

            holder.listView = (ListView) convertView.findViewById(R.id.list);

            convertView.setTag(holder);
        } else {
            holder = (ViewChildHolder) convertView.getTag();
        }

        convertView.setBackgroundColor(Color.WHITE);
//        final int loc = groupPosition;
//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(handler!=null){
//                    Message msg = new Message();
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("data", loc);
//                    msg.what = CHOICE_CONTROL_ITEM;
//                    msg.setData(bundle);
//                    handler.sendMessage(msg);
//                }
//            }
//        });

        final int loc = groupPosition;

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


    private void setGroupItemData(ViewGroupHolder holder, int position) {
        // TODO Auto-generated method stub
        holder.txt1.setText(list.get(position).getItemZuhao());
        holder.txt2.setText(list.get(position).getItemMingcheng());
        holder.txt3.setText(list.get(position).getItemShijian());
    }

    private void setChildItemData(ViewChildHolder holder, int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        holder.txt1.setText(list.get(groupPosition).getItemShijian());
        holder.txt2.setText(list.get(groupPosition).getItemShijian());
        holder.txt3.setText(list.get(groupPosition).getItemSongjianyisheng());

        final List<JianchaResultShuju> dataList = list.get(groupPosition).getItems();
        JianchaResultShujuAdapter adapter = new JianchaResultShujuAdapter(context, dataList);
        holder.listView.setAdapter(adapter);

    }

    class ViewGroupHolder {
        TextView txt1;
        TextView txt2;
        TextView txt3;
        LinearLayout view;
    }

    class ViewChildHolder {
        TextView txt1;
        TextView txt2;
        TextView txt3;

        ListView listView;
        LinearLayout view;
    }
}
