package com.applock.data;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;










import com.applock.LockApplication;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RecentTaskInfo;
import android.app.KeyguardManager;
import android.app.Service;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;

public class AppInfoLocation {
//	public final static String Shared_Preferences_Name = "AppInfoSetChosen";
	
	private Context context;
	
	private String SharedPreferencesName;
	
	private final String APP_INFO = "appinfo";
	private final String PASSWORD = "password";
	private final String QUESTION = "question";
	private final String ANSWER = "answer";
	
	private String sPassword;
	private List<Integer> iPassword;
	private String question;
	private String answer;
	private Set<String> appSet;
	private List<AppInfo> appInfos;
	
	private LockApplication lockApplication;
	
	private static AppInfoLocation appInfoSet = new AppInfoLocation();
	
	private SharedPreferences preferences;  
    private SharedPreferences.Editor editor;  
    
    private ActivityManager mActivityManager ;
    private PackageManager pkgManager;
    
    
    @SuppressLint("NewApi")
	private AppInfoLocation(){
    	this.context = LockApplication.getInstance();
    	
    	SharedPreferencesName = context.getPackageName();
    	
    	lockApplication = LockApplication.getInstance();
    	
    	preferences = context.getSharedPreferences(SharedPreferencesName, context.MODE_PRIVATE);
    	editor = preferences.edit();
    	
    	editor.putStringSet(APP_INFO,  appSet);
    	
//    	appSet = preferences.getStringSet(APP_INFO, null);
//    	appInfos = deSerializationSet(appSet);
//    	sPassword = preferences.getString(PASSWORD, "");
//    	iPassword = deSerializationPsw(sPassword);
    	
    	mActivityManager = (ActivityManager)context.getSystemService(Service.ACTIVITY_SERVICE);
    	pkgManager = context.getPackageManager();
    }
    
    @SuppressLint("NewApi")
	public List<AppInfo> getListAppInfo(){
    	appSet = preferences.getStringSet(APP_INFO, null);
    	appInfos = deSerializationSet(appSet);
    	if(appInfos == null){
    		appInfos = new ArrayList<AppInfo>();
    	}
    	return new ArrayList<AppInfo>(appInfos);
    }
    
    @SuppressLint("NewApi")
	public void setListAppInfo(List<AppInfo> apps){
    	appInfos = apps;
    	appSet = seriazeSet(appInfos);
    	editor.putStringSet(APP_INFO, appSet);
    	editor.commit();
    }
    
    public List<Integer> getPassword(){
    	sPassword = preferences.getString(PASSWORD, "");
    	iPassword = deSerializationPsw(sPassword);
    	if(iPassword == null){
    		iPassword = new ArrayList<Integer>();
    	}
    	
    	return iPassword;
    }
    
    public void setPassword(List<Integer> list){
    	iPassword = list;
    	sPassword = seriazePsw(iPassword);
    	editor.putString(PASSWORD, sPassword);
    	editor.commit();
    }
    
    public String getQuestion(){
    	question = preferences.getString(QUESTION, "");
    	return question;
    }
    
    public void setQuestion(String qs){
    	question = qs;
    	editor.putString(QUESTION, question);
    	editor.commit();
    }
    
    public String getAnswer(){
    	answer = preferences.getString(ANSWER, "");
    	return answer;
    }
    
    public void setAnswer(String aw){
    	answer = aw;
    	editor.putString(aw, answer);
    	editor.commit();
    }
	
    public static AppInfoLocation instance(){
    	
    	return appInfoSet;
    }
    
    private String seriazePsw(List<Integer> list){
    	if(list == null){
    		return "";
    	}
    	String str = "";
    	for(Integer i : list){
    		str += i;
    	}
    	return str;
    }
    private List<Integer> deSerializationPsw(String str){
    	List<Integer> list = new ArrayList<Integer>();
    	
    	for(int i = 0; i < str.length(); ++i){
    		char c = str.charAt(i);
    		if(Character.isDigit(c)){
    			list.add(c-'0');
    		}
    	}
    	return list;
    }
    
	/**
	 * 序列化对象
	 * 
	 * @param appInfo
	 * @return
	 * @throws IOException
	 */
	private String serialize(AppInfo appInfo) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				byteArrayOutputStream);
		objectOutputStream.writeObject(appInfo);
		String setStr = byteArrayOutputStream.toString("ISO-8859-1");
		setStr = java.net.URLEncoder.encode(setStr, "UTF-8");
		objectOutputStream.close();
		byteArrayOutputStream.close();
		return setStr;
	}

	/**
	 * 反序列化对象
	 * 
	 * @param str
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private AppInfo deSerialization(String str) throws IOException,
			ClassNotFoundException {
		String redStr = java.net.URLDecoder.decode(str, "UTF-8");
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
				redStr.getBytes("ISO-8859-1"));
		ObjectInputStream objectInputStream = new ObjectInputStream(
				byteArrayInputStream);
		AppInfo appInfo = (AppInfo) objectInputStream.readObject();
		objectInputStream.close();
		byteArrayInputStream.close();
//		Log.e("mydebug", "??????  name " + appInfo.getAppLabel() + "  isLock " + appInfo.getIsLocked());
//		appInfo.setIsLocked(true);
		return appInfo;
	}
	
	private Set<String> seriazeSet(List<AppInfo> appInfos){
		if(appInfos == null){
			return null;
		}
		Set<String> set = new HashSet<String>();
		
		for(AppInfo appInfo : appInfos){
			try {
				set.add(serialize(appInfo));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e("mydebug", " throw error " + e.toString());
				e.printStackTrace();
			}
		}
		
		return set;
	}
	
	private List<AppInfo> deSerializationSet(Set<String> appSet){
		if(appSet == null){
			return null;
		}
		List<AppInfo> list = new ArrayList<AppInfo>();
		for(String app : appSet){
			try {
				list.add(deSerialization(app));
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	
	public boolean findApp(String pkgName, String label){
		getListAppInfo();
		if(appInfos == null){
			return false;
		}
		for(AppInfo app : appInfos){
			if(app.equals(pkgName, label) 
					&& (app.getIsLocked() == null || app.getIsLocked() == true)){
				app.setIsLocked(false);
				return true;
			}
		}
		return false;
	}
	
	public void checkBackgroundRunning(){
		
		List<AppInfo> listApp = getListAppInfo();
//		if(lockApplication.isLocked == true){
//			for(int i = 0; i < listApp.size(); ++i){
//				listApp.get(i).setIsLocked(true);
//			}
//			setListAppInfo(listApp);
//			return;
//		}

		List<RecentTaskInfo> listRecentTaskInfo = mActivityManager
				.getRecentTasks(10, ActivityManager.RECENT_IGNORE_UNAVAILABLE);

		for (int i = 0; i < listApp.size(); ++i) {
			boolean isLock = true;
//			Log.e("mydebug", "||||||||||||||  111 isLock  " + listApp.get(i).getIsLocked());
			for (RecentTaskInfo rti : listRecentTaskInfo) {
				
				Intent intent = rti.baseIntent;
				ResolveInfo resolveInfo = pkgManager.resolveActivity(intent,
						PackageManager.MATCH_DEFAULT_ONLY);
				String pkgName = resolveInfo.activityInfo.packageName;
				String label = (String) resolveInfo.loadLabel(pkgManager);

				// listApp.get(i).setIsLocked(true);
				if (listApp.get(i).getHasUnlock() == true 
						&& listApp.get(i).equals(pkgName, label)) {
					Log.e("mydebug", "||||||||||||||  2222 label " + label
							+ "   package name " + pkgName);
					// listApp.get(i).setIsLocked(false);
					isLock = false;
					break;
				}
			}
			listApp.get(i).setIsLocked(isLock);
			setListAppInfo(listApp);
		}

	}
}
