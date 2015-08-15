package com.applock;

//import android.support.v7.app.ActionBarActivity;
import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.applock.data.AppInfo;
import com.applock.data.AppInfoLocation;
import com.applock.lockview.GestureLockViewGroup;
import com.applock.lockview.GestureLockViewGroup.OnGestureLockViewListener;
import com.applock.service.LockTask;

public class PasswordActivity extends Activity {

	Context context;
	
	GestureLockViewGroup lockView;
	
	private LockApplication lockApplaction;
	private AppInfoLocation appInfoLocation;
	
	private ImageView pswIcon;
	private TextView pswMsg;
	
	private String packageName;
	private String className;
	
	private int[] choose;
	
	private boolean mFinish = false;  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.password_activity);
		
		context = getApplicationContext();
		
		lockApplaction = (LockApplication) getApplication();
		
		appInfoLocation = AppInfoLocation.instance();
		
		Log.e("mydebug", "////////////////  password activity");
		
		initView();
		
	}

	public void initView() {
		pswIcon = (ImageView)findViewById(R.id.psw_icon);
		pswMsg = (TextView)findViewById(R.id.psw_msg);
		
		lockView = (GestureLockViewGroup) findViewById(R.id.lockview);
		lockView.setAnswer(lockApplaction.getPassword());
		lockView.setOnGestureLockViewListener(listener);
		


		packageName = getIntent().getStringExtra("packageName");
		className = getIntent().getStringExtra("className");
		Log.e("mydebug", "//////////////  pass word package name  " + packageName);
		try {
			//通过包名拿到applicationInfo
			ApplicationInfo appInfo = getPackageManager().getPackageInfo(packageName, 0).applicationInfo;
			//应用图标  
            Drawable app_icon = appInfo.loadIcon(getPackageManager());  
//            className =  appInfo.className;
            //应用的名字  
            String app_name = appInfo.loadLabel(getPackageManager()).toString();
            pswIcon.setImageDrawable(app_icon);  
            pswMsg.setText(app_name + "被锁定");  
            Log.e("mydebug", "//////////////  pass word name  " + app_name);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			 e.printStackTrace();
		}
	}
	
	public OnGestureLockViewListener listener = new OnGestureLockViewListener(){
		@Override
		public void onBlockSelected(int cId) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGestureEvent(boolean matched) {
			// TODO Auto-generated method stub
			
			if(matched){
				Toast.makeText(context, "密码正确", Toast.LENGTH_SHORT).show();
				
				List<AppInfo> apps = appInfoLocation.getListAppInfo();
				for(int i = 0; i < apps.size(); ++i){
					if(apps.get(i).getPkgName().equals(packageName)){
						apps.get(i).setHasUnlock(true);
						apps.get(i).setIsLocked(false);
						break ;
					}
				}
				appInfoLocation.setListAppInfo(apps);
				
				mFinish = true;
				finish();  
				
				Intent launchIntent = new Intent(Intent.ACTION_VIEW);  
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
                launchIntent.setComponent(new ComponentName(packageName,  
                        className));  
                startActivity(launchIntent);
				
			}
			else{
				Toast.makeText(context, "密码错误", Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		public void onUnmatchedExceedBoundary() {
			// TODO Auto-generated method stub
			Toast.makeText(context, "错误5次...",  
                    Toast.LENGTH_SHORT).show();  
			lockView.setUnMatchExceedBoundary(5);  
		}
	};

	@Override
	public void onBackPressed() {
	}

	@Override
	public void onPause() {
		super.onPause();
		if (!mFinish) {
			finish();
		}
	}
	
}
