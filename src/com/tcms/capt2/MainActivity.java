package com.tcms.capt2;

import android.os.Bundle;
import android.os.Handler;
import android.app.ListActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

 

public class MainActivity extends ListActivity implements OnScrollListener{

	private String TAG = "ListView";
	private LinearLayout mLoadLayout;
	private ListView     mListView;
	private NewsListAdapter mListViewAdapter = new NewsListAdapter();
	
	private int mCount = 40;
	private int mLastItem = 0;
	private boolean mUpforward = true;
	
	private final Handler mHandler = new Handler();
	
	private final LayoutParams mProgressBarLayoutParams = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
	
	private final LayoutParams mTipContentLayoutParams = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	//Log.i(TAG, "onCreate called");
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        
        /*加载项布局*/
        mLoadLayout = new LinearLayout(this);
        mLoadLayout.setMinimumHeight(60);
        mLoadLayout.setGravity(Gravity.CENTER);
        mLoadLayout.setOrientation(LinearLayout.HORIZONTAL);
        
        /*圆形进度条*/
        ProgressBar mProgressBar = new ProgressBar(this);
        mProgressBar.setPadding(0, 0, 15, 0);
        mLoadLayout.addView(mProgressBar, mProgressBarLayoutParams);
        
        /*添加提示信息*/
        TextView mTipContent = new TextView(this);
        mTipContent.setText("加载中...");
        mLoadLayout.addView(mTipContent,mTipContentLayoutParams);
        
        /*获取ListView组件,并将loader添加到组件的footer之中*/
        mListView = getListView();
        mListView.addFooterView(mLoadLayout);
        
        /*设置adapter, 并设置滑动监听事件*/
        setListAdapter(mListViewAdapter);
        mListView.setOnScrollListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		
		int newItem = firstVisibleItem + visibleItemCount - 1;
		if(mLastItem <= newItem){
			mUpforward = true;
		}else{
			mUpforward = false;
		}
		
		mLastItem = newItem;
		
		Log.i(TAG, "onScroll called, mLastItem="+mLastItem);
		
		if (mListViewAdapter.count > mCount) {
			mListView.removeFooterView(mLoadLayout);
		}
		
	}
	
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		
		Log.i(TAG, "onScrollStateChanged called, adapter.count="+mListViewAdapter.count);
		
		if(scrollState == OnScrollListener.SCROLL_STATE_IDLE){
			if(mListViewAdapter.count <= mCount && mUpforward == true && 
					mLastItem == mListViewAdapter.count){
				mHandler.postDelayed(new Runnable() {
					public void run() {
						mListViewAdapter.count += 10;
						mListViewAdapter.notifyDataSetChanged();
						mListView.setSelection(mLastItem);
					}
				}, 500);
			}
		}
		
	}

	
	//内部类
	class NewsListAdapter extends BaseAdapter{

		private int count = 10;//init value
		
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
				mTextView = new TextView(MainActivity.this);
			}else{
				mTextView = (TextView)convertView;
			}
			mTextView.setText("Item:"+position);
			mTextView.setTextSize(20f);
			mTextView.setGravity(Gravity.CENTER);
			mTextView.setHeight(60);
			
			return mTextView;
			
		}
		
	}
}
