package com.applock.data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;

import android.graphics.drawable.Drawable;

public class AppInfo implements Serializable{
	
	/*
	 *关键字 transient声明变量，表示不系列化该变量
	 */
	
	private String pkgName;//应用包名
	private String appLabel; // 应用程序标签
	private transient  Drawable appIcon; // 应用程序图像
	private  Boolean isLocked;
	private Boolean hasUnlock; // 是否解过锁
	
	public AppInfo(){
		appIcon = null;
//		isLocked = true;
		hasUnlock = false;
	}
	
//	//重写writeObject()方法以便处理transient的成员。
//	private void writeObject(ObjectOutputStream outputStream) throws IOException{
//	    outputStream.defaultWriteObject();//使定制的writeObject()方法可以 利用自动序列化中内置的逻辑。
//	    outputStream.writeObject(pkgName);
//	    outputStream.writeObject(appLabel);
//	}
//	//重写readObject()方法以便接收transient的成员。
//	private void readObject(ObjectInputStream inputStream) throws IOException,ClassNotFoundException{
//	    inputStream.defaultReadObject();//defaultReadObject()补充自动序列化
//	    pkgName = (String) inputStream.readObject();
//	    appLabel = (String) inputStream.readObject();
//	}
	
	public Boolean equals(String pkgn, String label){
		if(pkgn == null || label == null){
			return false;
		}
		if(pkgn.equals(pkgName) && label.equals(appLabel)){
			return true;
		}

		return false;
	}
	
	public String getPkgName() {
		return pkgName;
	}

	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}
	
	public String getAppLabel() {
		return appLabel;
	}
	public Drawable getAppIcon() {
		return appIcon;
	}
	public Boolean getIsLocked() {
		return isLocked;
	}
	public void setAppLabel(String appLabel) {
		this.appLabel = appLabel;
	}
	public void setAppIcon(Drawable appIcon) {
		this.appIcon = appIcon;
	}
	public void setIsLocked(Boolean isLocked) {
		this.isLocked = isLocked;
	}

	public Boolean getHasUnlock() {
		return hasUnlock;
	}

	public void setHasUnlock(Boolean hasUnlock) {
		this.hasUnlock = hasUnlock;
	}

	@Override
	public String toString() {
		return "AppInfo [pkgName=" + pkgName + ", appLabel=" + appLabel
				+ ", isLocked=" + isLocked;
	}
}
