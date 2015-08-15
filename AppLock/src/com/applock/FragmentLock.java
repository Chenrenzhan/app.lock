package com.applock;

import java.util.List;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.applock.data.AppInfo;
import com.applock.data.NotifyDataChange;
import com.applock.data.QueryAppInfo;
import com.applock.tab.IndicatorFragmentActivity;

public class FragmentLock extends FragmentBase {

	public FragmentLock(){
		super();
	}
	
	@Override
	protected List<AppInfo> setListAppInfo(QueryAppInfo queryAppInfo) {
		// TODO Auto-generated method stub
		return queryAppInfo.getListAppInfoLock();
	}

	@Override
	public void setTabAppSize() {
		// TODO Auto-generated method stub
		((IndicatorFragmentActivity) activity).setTabTitle(
				MainActivity.Lock, "已加锁(" + listAppInfo.size() + ")");
	}

	@Override
	protected float setEndX(int screenWidth) {
		// TODO Auto-generated method stub
		return (-screenWidth);
	}

	@Override
	protected void removeView(int position) {
		// TODO Auto-generated method stub
		AppInfo app = listAppInfo.get(position);
		queryAppInfo.addUnlockApp(app);
		queryAppInfo.removeLockApp(app);
	}

	@Override
	protected void setNotifyDataChange() {
		// TODO Auto-generated method stub
		queryAppInfo.setLockNotifyDataChange(new NotifyDataChange() {
			
			@Override
			public void notifyDataCHange() {
				// TODO Auto-generated method stub
				listAppInfo = queryAppInfo.getListAppInfoLock();
				((ListViewAdapter) adapter).notifyDataSetChanged();
			}
		});
	}

	@Override
	protected void setToastTip(int position) {
		// TODO Auto-generated method stub
		AppInfo app = listAppInfo.get(position);
		String msg= app.getAppLabel() + "解除锁定";
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void setLockStatusIcon() {
		// TODO Auto-generated method stub
		imgLock = context.getResources().getDrawable(R.drawable.ic_lock);
	}
	
}