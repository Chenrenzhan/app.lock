package com.applock.service;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.applock.data.AppInfoLocation;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class LockService extends Service {  
    private Timer mTimer;  
    public static final int FOREGROUND_ID = 0;  
    
    AppInfoLocation appInfoLocation;
  
    private void startTimer() {  
        if (mTimer == null) {  
            mTimer = new Timer();  
            LockTask lockTask = new LockTask(this);  
            mTimer.schedule(lockTask, 0L, 1000L);  
        }  
    }  
  
    public IBinder onBind(Intent intent) {  
    	Log.e("BootReceiver", "onBind");
        return null;  
    }  
  
    public void onCreate() {  
        super.onCreate();  
        startForeground(FOREGROUND_ID, new Notification());  
        Log.e("BootReceiver", "onCreate");
        appInfoLocation = AppInfoLocation.instance();
    }  
  
    public int onStartCommand(Intent intent, int flags, int startId) {  
        startTimer();  
        checkBackgroundRunning();
    	Log.e("BootReceiver", "onStartCommand");

    	super.onStartCommand(intent, flags, startId);  
        
        return START_STICKY;    
    }  
  
    public void onDestroy() {  
        stopForeground(true);  
        mTimer.cancel();  
        mTimer.purge();  
        mTimer = null;  
		super.onDestroy();
	}

	public void checkBackgroundRunning(){
		Timer timer = new Timer();
		TimerTask timerTask = new TimerTask(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				appInfoLocation.checkBackgroundRunning();
			}
			
		};
		timer.schedule(timerTask, 1000L, 3000L);
	}

}