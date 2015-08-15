package com.applock.service;

import java.util.List;


import com.applock.MainActivity;
import com.applock.data.AppInfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootBroadcastReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.e("BootReceiver", "BootBroadcastReceiver " +intent.getAction());  
		 if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){
			 Log.e("BootReceiver", "system boot completed");  
			Intent service = new Intent(context, LockService.class);
			context.startService(service);
			Log.e("BootReceiver", "开机自启动服务");
			 
			// context, AutoRun.class
//				Intent newIntent = new Intent(context, MainActivity.class);
//
//				/* MyActivity action defined in AndroidManifest.xml */
//				newIntent.setAction("android.intent.action.MAIN");
//
//				/* MyActivity category defined in AndroidManifest.xml */
//				newIntent.addCategory("android.intent.category.LAUNCHER");
//
//				/*
//				 * If activity is not launched in Activity environment, this flag is
//				 * mandatory to set
//				 */
//				newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//				/* if you want to start a service, follow below method */
//				context.startActivity(newIntent);
		 }
		 
	}
}