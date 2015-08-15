package com.applock;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import com.applock.service.LockService;
import com.applock.tab.IndicatorFragmentActivity;

public class MainActivity extends IndicatorFragmentActivity {
	
	public final static int FRAGMENT_UNLOCK = 0;
	public final static int FRAGMENT_LOCK = 1;
	
	public final static int Unlock = 0;
	public final static int Lock = 1;
	
	LockApplication lockApplaction;

	private ImageButton btnSet;
	private SetPopWindow setPopWindow;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        initView();
        
        //启动service
        startService(new Intent(this, LockService.class));
    }
    
    public void initView(){
    	btnSet = (ImageButton)findViewById(R.id.bar_set);
    	
    	setPopWindow = new SetPopWindow(this);
    	
    	btnSet.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, "set", Toast.LENGTH_SHORT).show();
//				setPopWindow.setWidth(300);  
				setPopWindow.showAsDropDown(btnSet);  
//				setPopWindow.showAsDropDown(btnSet, -300, 5);
			}
		});
    }

    @SuppressLint("NewApi")
	@Override
    protected int supplyTabs(List<TabInfo> tabs) {
        tabs.add(new TabInfo(FRAGMENT_UNLOCK, getString(R.string.tab_unlock),
                FragmentUnlock.class));
        
        tabs.add(new TabInfo(FRAGMENT_LOCK, getString(R.string.tab_lock),
        		FragmentLock.class));

        return FRAGMENT_UNLOCK;
    }
    
    protected void onResume() {
		super.onResume();
		lockApplaction = (LockApplication) getApplication();
		if (lockApplaction.isLocked) {//判断是否需要跳转到密码界面
			Intent intent = new Intent(this, LockActivity.class);
			startActivity(intent);
		}
	}

}
