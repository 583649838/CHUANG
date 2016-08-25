package com.bestplus.chuangshangjiuzhi.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bestplus.chuangshangjiuzhi.R;
import com.bestplus.chuangshangjiuzhi.entity.BingrenInfo;

public class BingrenInfoAdapter extends BaseAdapter {
	Context context;
	List<BingrenInfo> list;

	public BingrenInfoAdapter(Context context, List<BingrenInfo> list) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
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
					R.layout.tablerow_six, null);
			holder.first = (TextView) convertView.findViewById(R.id.tv_first);
			holder.second = (TextView) convertView.findViewById(R.id.tv_second);
			holder.third = (TextView) convertView.findViewById(R.id.tv_third);
			holder.fourth = (TextView) convertView.findViewById(R.id.tv_fourth);
			holder.fifth = (TextView) convertView.findViewById(R.id.tv_fifth);
			holder.sixth = (TextView) convertView.findViewById(R.id.tv_sixth);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		setItemData(holder,position);
		return convertView;
	}
	private void setItemData(ViewHolder holder, int position) {
		// TODO Auto-generated method stub
	 
		holder.first.setText(list.get(position).getItemHuanzheId());
		holder.second.setText(list.get(position).getItemHuanzheName());
		holder.third.setText(list.get(position).getItemXingbie());
		holder.fourth.setText(list.get(position).getItemNianling());
		holder.fifth.setText(list.get(position).getItemHuanZheDianhua());
		holder.sixth.setText(list.get(position).getItemShenfengzhenghaoma());
	}
	private static class ViewHolder {
		TextView first;
		TextView second;
		TextView third;
		TextView fourth;
		TextView fifth;
		TextView sixth;
	}

}
