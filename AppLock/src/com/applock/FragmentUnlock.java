package com.applock;

import java.util.List;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.applock.data.AppInfo;
import com.applock.data.NotifyDataChange;
import com.applock.data.QueryAppInfo;
import com.applock.tab.IndicatorFragmentActivity;

public class FragmentUnlock extends FragmentBase {

	public FragmentUnlock(){
		super();
	}
	
	@Override
	protected List<AppInfo> setListAppInfo(QueryAppInfo queryAppInfo) {
		// TODO Auto-generated method stub
		return queryAppInfo.getListAppInfoUnlock();
	}

	@Override
	public void setTabAppSize() {
		// TODO Auto-generated method stub
		((IndicatorFragmentActivity) activity).setTabTitle(
				MainActivity.Unlock, "未加锁(" + listAppInfo.size() + ")");
	}

	@Override
	protected float setEndX(int screenWidth) {
		// TODO Auto-generated method stub
		return screenWidth;
	}
	@Override
	protected void removeView(int position) {
		// TODO Auto-generated method stub
		AppInfo app = listAppInfo.get(position);
		queryAppInfo.addLockApp(app);
		queryAppInfo.removeUnlockApp(app);
	}

	@Override
	protected void setNotifyDataChange() {
		// TODO Auto-generated method stub
		queryAppInfo.setUnlockNotifyDataChange(new NotifyDataChange() {
			
			@Override
			public void notifyDataCHange() {
				// TODO Auto-generated method stub
				listAppInfo = queryAppInfo.getListAppInfoUnlock();
				((ListViewAdapter) adapter).notifyDataSetChanged();
			}
		});
	}

	@Override
	protected void setToastTip(int position) {
		// TODO Auto-generated method stub
		AppInfo app = listAppInfo.get(position);
		String msg= app.getAppLabel() + "已加入保护应用列表";
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void setLockStatusIcon() {
		// TODO Auto-generated method stub
		imgLock = context.getResources().getDrawable(R.drawable.ic_unlock);
	}

	

	
}