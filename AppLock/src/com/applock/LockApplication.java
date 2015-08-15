package com.applock;


import java.util.ArrayList;
import java.util.List;

import com.applock.data.AppInfo;
import com.applock.data.AppInfoLocation;
import com.applock.service.LockService;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

public class LockApplication extends Application {

	private static LockApplication instance;
	
	public boolean isLocked = true;
	public boolean isFirst;
//	public String password = "1";
	
	AppInfoLocation appInfoLocation;
	
	private int[] password;
	private List<Integer> listPassword;
	private String question;
	private String answer;

	LockScreenReceiver receiver ;
	IntentFilter filter ;
	@Override
	public void onCreate() {
		super.onCreate();

		instance = this;

		receiver = new LockScreenReceiver();
        filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        this.registerReceiver(receiver, filter);
        
        appInfoLocation = AppInfoLocation.instance();

        listPassword = appInfoLocation.getPassword();
        Log.e("PassWord", "PassWord is " + listPassword);
        question = appInfoLocation.getQuestion();
        answer = appInfoLocation.getAnswer();
        password =  list2Array(listPassword);
        
        isFirst();
	}
	
	@SuppressLint("NewApi")
	public boolean save(){
		if(listPassword == null || listPassword.size() == 0
				|| question == null || question.isEmpty()
				|| answer == null || answer.isEmpty()){
			return false;
		}
		else{
			appInfoLocation.setPassword(listPassword);
			Log.e("mydebug", "**********  listPassword " + listPassword);
			appInfoLocation.setQuestion(question);
			appInfoLocation.setAnswer(answer);
		}
		return true;
	}
	
	private int[] list2Array(List<Integer> listPassword){
		if(listPassword == null){
			return null;
		}
		int [] password = new int[listPassword.size()];
		for(int i = 0; i < listPassword.size(); ++i){
			password[i] = listPassword.get(i);
		}
		return password;
	}
	
	private List<Integer>  array2List(int[] password ){
		if(password == null){
			return null;
		}
		List<Integer> listPassword = new ArrayList<Integer>();
		for(int i = 0; i < password.length; ++i){
			listPassword.add(password[i]);
		}
		return listPassword;
	}
	
	public boolean isFirst(){
		if(password == null || password.length == 0){
			isFirst = true;
		}
		else{
			isFirst = false;
		}
		return isFirst;
	}
	
	@Override
	public void onTerminate() {
		super.onTerminate();
		this.unregisterReceiver(receiver);
	}
	

	class LockScreenReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.e("BootReceiver", "LockScreenReceiver " +intent.getAction());  
			/* 在这里处理广播 */
			if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
				isLocked  = true;
				Log.e("BootReceiver", "LockScreenReceiver ACTION_SCREEN_OFF");  
				List<AppInfo> apps = appInfoLocation.getListAppInfo();
				for(int i = 0; i < apps.size(); ++i){
					apps.get(i).setHasUnlock(false);
				}
				appInfoLocation.setListAppInfo(apps);
				
			}
		}
		
	}
	
	public static LockApplication getInstance(){
		return instance;
	}

	public int[] getPassword() {
		return password;
	}

	public List<Integer> getListPassword() {
		return listPassword;
	}

	public String getQuestion() {
		return question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setPassword(int[] password) {
		this.password = password;
		this.listPassword = array2List(password);
		
	}

	public void setListPassword(List<Integer> listPassword) {
		this.listPassword = listPassword;
		this.password = list2Array(listPassword);
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

}
