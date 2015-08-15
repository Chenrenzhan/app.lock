package com.applock.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.applock.LockApplication;

//单例类
public class QueryAppInfo {
	private Context context;
	
//	private AppInfoLocation appInfoSet;
	
	private NotifyDataChange lockNotifyDataChange;
	private NotifyDataChange unlockNotifyDataChange;
	
	private  List<AppInfo> listAppInfo;
	private List<AppInfo> listAppInfoUnlock;
	private List<AppInfo> listAppInfoLock;
	private List<AppInfo> pListInfo;
	
	private static QueryAppInfo appInfoList = new QueryAppInfo();
	
	private AppInfoLocation appInfoLocation = AppInfoLocation.instance();
	
	//私有构造函数
	private QueryAppInfo(){
		this.context = LockApplication.getInstance();
		this.listAppInfo = null;
		this.listAppInfo = new ArrayList<AppInfo>();
		
		queryAppInfo();
		
		sortInfo();
		
//		sortInfo();
	}

	public static QueryAppInfo instance(){
//		appInfoList = new AppInfoList();
		return appInfoList;
	}
	
	public  List<AppInfo> update(){
//		new AppInfoList();
		listAppInfo = null;
		
		listAppInfo = new ArrayList<AppInfo>();
		
		queryAppInfo();
		sortInfo();
		
		return listAppInfo;
	}
	
	public void removeLockApp(int position){
		listAppInfoLock.remove(position);
		lockNotifyDataChange.notifyDataCHange();
		appInfoLocation.setListAppInfo(listAppInfoLock);
	}
	
	public void removeLockApp(AppInfo app){
		listAppInfoLock.remove(app);
		lockNotifyDataChange.notifyDataCHange();
		appInfoLocation.setListAppInfo(listAppInfoLock);
	}
	
	public void addLockApp(AppInfo app){
		Log.e("mydebug", "**********  addLockApp");
		listAppInfoLock.add(app);
		lockNotifyDataChange.notifyDataCHange();
		appInfoLocation.setListAppInfo(listAppInfoLock);
	}
	
	public void removeUnlockApp(int position){
		listAppInfoUnlock.remove(position);
		unlockNotifyDataChange.notifyDataCHange();
	}
	
	public void removeUnlockApp(AppInfo app){
		listAppInfoUnlock.remove(app);
		unlockNotifyDataChange.notifyDataCHange();
	}
	
	public void addUnlockApp(AppInfo app){
		listAppInfoUnlock.add(app);
		unlockNotifyDataChange.notifyDataCHange();
	}
	
	public void sortInfo(){
		listAppInfoUnlock = null;
		listAppInfoUnlock = new ArrayList<AppInfo>();
		
		listAppInfoLock = null;
		listAppInfoLock = new ArrayList<AppInfo>();
		
		pListInfo= appInfoLocation.getListAppInfo();
		for(AppInfo appInfo : listAppInfo){
			String pkgName = appInfo.getPkgName();
			String label = appInfo.getAppLabel();
			
			boolean isLock = false;
			
			for(AppInfo app : pListInfo){
				if(app.equals(pkgName, label)){
					isLock = true;
					
//					appInfo.setIsLocked(true);
					listAppInfoLock.add(appInfo);
					pListInfo.remove(app);
					break;
				}
			}
			
			if(!isLock){
//				appInfo.setIsLocked(false);
				listAppInfoUnlock.add(appInfo);
			}
			
		}
	}
	
	// 获得所有启动Activity的信息
    public void queryAppInfo() {  
    	
        PackageManager pm = context.getPackageManager(); // 获得PackageManager对象  
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);  
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);  
        
        // 通过查询，获得所有ResolveInfo对象.  
        List<ResolveInfo> resolveInfos = pm  
                .queryIntentActivities(mainIntent, PackageManager.GET_ACTIVITIES);  
        
        
        // 调用系统排序 ， 根据name排序  
        // 该排序很重要，否则只能显示系统应用，而不能列出第三方应用程序  
        Collections.sort(resolveInfos,new ResolveInfo.DisplayNameComparator(pm));  
        if (listAppInfo != null) {  
            listAppInfo.clear();  
            
            for (ResolveInfo reInfo : resolveInfos) {  
                String activityName = reInfo.activityInfo.name; // 获得该应用程序的启动Activity的name  
               final  String pkgName = reInfo.activityInfo.packageName; // 获得应用程序的包名  
                String appLabel = (String) reInfo.loadLabel(pm); // 获得应用程序的Label  
                Drawable icon = reInfo.loadIcon(pm); // 获得应用程序图标  
                
                // 创建一个AppInfo对象，并赋值  
                AppInfo appInfo = new AppInfo();  
                appInfo.setAppLabel(appLabel);  
                appInfo.setPkgName(pkgName);  
                appInfo.setAppIcon(icon);  
                listAppInfo.add(appInfo); // 添加至列表中  
            }  
        }  
    }

	public List<AppInfo> getListAppInfo() {
		if(listAppInfo == null){
			listAppInfo = new ArrayList<AppInfo>();
		}
		return listAppInfo;
	}

	public void setListAppInfo(List<AppInfo> listAppInfo) {
		this.listAppInfo = listAppInfo;
	}
	
	public List<AppInfo> getListAppInfoUnlock() {
		return listAppInfoUnlock;
	}

	public List<AppInfo> getListAppInfoLock() {
		return listAppInfoLock;
	}
	
	public void setListAppInfoUnlock(List<AppInfo> listAppInfoUnlock) {
		this.listAppInfoUnlock = listAppInfoUnlock;
	}

	public void setListAppInfoLock(List<AppInfo> listAppInfoLock) {
		this.listAppInfoLock = listAppInfoLock;
	}
	
	public void setLockNotifyDataChange(NotifyDataChange lockNotifyDataChange) {
		this.lockNotifyDataChange = lockNotifyDataChange;
	}

	public void setUnlockNotifyDataChange(NotifyDataChange unlockNotifyDataChange) {
		this.unlockNotifyDataChange = unlockNotifyDataChange;
	}

}
