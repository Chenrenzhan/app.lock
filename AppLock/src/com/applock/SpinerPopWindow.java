package com.applock;

import java.util.ArrayList;
import java.util.List;



import com.applock.QuestionActivity.IOnItemSelectListener;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class SpinerPopWindow extends PopupWindow implements OnItemClickListener {

	private Context mContext;
	private ListView mListView;
	private NormalSpinerAdapter mAdapter;
	private IOnItemSelectListener mItemSelectListener;
	
	private String[] questions;

	public SpinerPopWindow(Context context) {
		super(context);

		mContext = context;
		init();
	}

	public void setItemListener(IOnItemSelectListener listener) {
		mItemSelectListener = listener;
	}

	private void init() {
		questions = mContext.getResources().getStringArray(R.array.question);
		
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.question_popwindow, null);
		setContentView(view);
		setWidth(LayoutParams.WRAP_CONTENT);
		setHeight(LayoutParams.WRAP_CONTENT);

		setFocusable(true);
		ColorDrawable dw = new ColorDrawable(0x00);
		setBackgroundDrawable(dw);

		mListView = (ListView) view.findViewById(R.id.question_listview);

		mAdapter = new NormalSpinerAdapter(mContext, questions);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
	}

	// public void refreshData(List<String> list, int selIndex)
	// {
	// if (list != null && selIndex != -1)
	// {
	// mAdapter.refreshData(list, selIndex);
	// }
	// }

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {
		dismiss();
		 if (mItemSelectListener != null){
		 mItemSelectListener.onItemClick(pos);
		 }
	}

	private class NormalSpinerAdapter extends BaseAdapter {

		private Context myContext = null;
		String[] mArray;
//		int myScore = 0;

		// 构造函数，根据自己需要的参数可重载
		public NormalSpinerAdapter(Context context, String[] arrays) {
			myContext = context;
			mArray = arrays;
//			myScore = score;
		}

		public int getCount() {
			return mArray.length;
		}

		public Object getItem(int position) {
			return mArray[position];
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = LayoutInflater.from(myContext);
			if (convertView == null) {
				// 指定下拉框的样式，通过配置文件来画

				convertView = inflater.inflate(R.layout.question_item, null);
			}
//			ImageView iv = (ImageView) convertView
//					.findViewById(R.id.spinner_pic);
			TextView tv = (TextView) convertView
					.findViewById(R.id.qustion_item_text);

			// 获取到控件iv和tv后可对控件进行操作

			tv.setText(mArray[position]);

			return convertView;
		}
	}

}