package com.xinbo.base;

import java.util.List;

import android.widget.BaseAdapter;

public abstract class AbstractAdapter extends BaseAdapter {
	public void addAll(List list) {
		notifyDataSetChanged();
	}

	public void add(List list) {
		notifyDataSetChanged();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

}
