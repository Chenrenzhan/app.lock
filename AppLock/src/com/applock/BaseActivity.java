package com.applock;

import android.app.Activity;
import android.content.Intent;

public class BaseActivity extends Activity {

	LockApplication lockApplaction;

	protected void onResume() {
		super.onResume();
		lockApplaction = (LockApplication) getApplication();
		if (lockApplaction.isLocked) {//判断是否需要跳转到密码界面
			Intent intent = new Intent(this, LockActivity.class);
			startActivity(intent);
		}
	}

}
