package com.applock.service;

import java.util.TimerTask;

import com.applock.LockActivity;
import com.applock.PasswordActivity;
import com.applock.data.AppInfoLocation;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
//import android.content.Intent;
import android.util.Log;

public class LockTask extends TimerTask {  
    public static final String TAG = "LockTask";  
    public static String runningApp = null;
	public static String lastRunningApp = null;
//    private static final String LOCK_CLASSNAME = "com.applock.LockActivity";
    private Context mContext;  
//    String testPackageName = "com.htc.notes";  
//    String testClassName = "com.htc.notes.collection.NotesGridViewActivity";  
    
    
  
    private ActivityManager mActivityManager;  
    
    private AppInfoLocation appInfoLocation;
  
    public LockTask(Context context) {  
        mContext = context;  
        appInfoLocation = AppInfoLocation.instance();
        mActivityManager = (ActivityManager)context. getSystemService(Service.ACTIVITY_SERVICE);
    }  
  
    @Override  
    public void run() {  
        ComponentName topActivity = mActivityManager.getRunningTasks(1).get(0).topActivity;  
        String packageName = topActivity.getPackageName();  
        String className = topActivity.getClassName();  
        String label = "";
        try {
			ApplicationInfo appInfo = mContext.getPackageManager()
					.getPackageInfo(packageName, 0).applicationInfo;
			label = appInfo.loadLabel(mContext.getPackageManager()).toString();
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Log.v(TAG, "packageName " + packageName);  
        Log.v(TAG, "className " + className);  
        Log.v(TAG, "AppLabel " + label);  
        if (appInfoLocation.findApp(packageName, label)) {  
//        	runningApp = packageName;
        	
        	//解决反复出现验证页面BUG：
			//如果runningApp.equals(lastRunningApp)=true
			//则说明当前栈顶运行的程序已经解锁了
//        	if((runningApp.equals(lastRunningApp)) == false){
        		Intent intent = new Intent(mContext, PasswordActivity.class);  
//              intent.setClassName("com.applock.LockTask", LOCK_CLASSNAME);  
              intent.putExtra("packageName", packageName);
              intent.putExtra("className", className);
              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
              mContext.startActivity(intent);  
//        	}
            
        }  
    }  
}  