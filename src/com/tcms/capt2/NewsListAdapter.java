package com.tcms.capt2;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

class NewsListAdapter extends BaseAdapter{

	private int count = 10;
	
	@Override
	public int getCount() {
		return count;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView mTextView;
		if(convertView == null){
			return null;
		}
		return null;
	}
	
}