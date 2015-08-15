package com.applock;

//import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.applock.lockview.GestureLockViewGroup;
import com.applock.lockview.GestureLockViewGroup.OnGestureLockViewListener;

public class LockActivity extends Activity {

	Context context;
	
	GestureLockViewGroup lockView;
	
	private LockApplication lockApplaction;
	
//	List<Integer> answer = Arrays.asList(1,2,3,6,9);
	
	int[] answer = new int[]{1,2,3,6,9};
	
	private TextView tvTip;
	private TextView tvForgetPsw;
	
	private int[] choose;
	private int time;
	
	private boolean mFinish = false;  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lock_acvtivity);
		
		context = getApplicationContext();
		
		lockApplaction = (LockApplication) getApplication();
		
		
		time = 0;
		
		initView();
		
	}

	public void initView() {
		tvTip = (TextView)findViewById(R.id.start_tip);
		tvForgetPsw = (TextView)findViewById(R.id.forget_psw);
		
		lockView = (GestureLockViewGroup) findViewById(R.id.lockview);
		lockView.setAnswer(lockApplaction.getPassword());
		lockView.setOnGestureLockViewListener(listener);
		
		tvForgetPsw.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, QuestionActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);
				LockActivity.this.finish();
			}
		});
	}
	
	public OnGestureLockViewListener listener = new OnGestureLockViewListener(){
		@Override
		public void onBlockSelected(int cId) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGestureEvent(boolean matched) {
			// TODO Auto-generated method stub
			if(lockApplaction.isFirst){
				if(time == 0){
					choose = lockView.getChoose();
					lockView.setAnswer(choose);
					tvTip.setText("请再次绘制图形密码");
					time = 1;
					return ;
				}
				
				if(matched){
					Toast.makeText(context, "密码正确", Toast.LENGTH_SHORT).show();
					startActivity(new Intent(LockActivity.this, QuestionActivity.class));
					
					lockApplaction.setPassword(choose);
					Log.e("mydebug", "--------  password " + choose);
					
					tvTip.setText("请绘制图形密码");
					
//					lockApplaction.isLocked = false;
//					LockActivity.this.finish();
				}
				else{
					Toast.makeText(context, "两次图形密码不一样，请重新绘制", Toast.LENGTH_SHORT).show();
					tvTip.setText("两次图形密码不一样，请重新绘制");
					time = 0;
					lockView.setAnswer(null);
				}
				return ;
			}
			
			
			if(matched){
				Toast.makeText(context, "密码正确", Toast.LENGTH_SHORT).show();
//				startActivity(new Intent(LockActivity.this, QuestionActivity.class));
				
//				String packageName = getIntent().getStringExtra("packageName");
//				if((packageName != null) && (!packageName.isEmpty())){
////					Intent intent = new Intent()
//					
//				}
				//这里赋值，终于解决了反复弹出验证页面的BUG
//				LockTask.lastRunningApp = LockTask.runningApp;
//	            finish();  
				
				mFinish = true;
				lockApplaction.isLocked = false;
//				LockActivity.this.finish();
				finish();  
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
