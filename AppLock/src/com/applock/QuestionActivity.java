package com.applock;

import java.util.ArrayList;
import java.util.List;

import com.applock.R.layout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class QuestionActivity extends Activity implements OnClickListener{

	Context context;
	private LockApplication lockApplaction;
	
	private ImageButton mBack;
	private RelativeLayout mQuestionChoose;
	private TextView mTView;  
    private ImageView mBtnDropDown;  
    private EditText mAnswerText;
    private Button mBtnFinish;
    private SpinerPopWindow mSpinerPopWindow;
    private List<String> nameList = new ArrayList<String>(); 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question_activity);
		
		context = getApplicationContext();
		
		lockApplaction = LockApplication.getInstance();
		
		setupViews();
	}
	
	private void setupViews(){  
		mBack = (ImageButton)findViewById(R.id.back);
		mBack.setOnClickListener(this);
		
		mQuestionChoose = (RelativeLayout)findViewById(R.id.choose_question_layout);
        mTView = (TextView) findViewById(R.id.text_question);  
        mTView.setOnClickListener(this);
        mBtnDropDown = (ImageView) findViewById(R.id.btn_pop);  
        mBtnDropDown.setOnClickListener(this);  
        mTView.setClickable(true);
        mQuestionChoose.setOnClickListener(this);
          
        String[] names = getResources().getStringArray(R.array.question);  
        for(int i = 0; i < names.length; i++){  
            nameList.add(names[i]);  
        }  
          
        mAnswerText = (EditText)findViewById(R.id.edit_answer);
        mAnswerText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if(s.length() > 0){
					mBtnFinish.setEnabled(true);
				}
				else{
					mBtnFinish.setEnabled(false);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
//				mBtnFinish.setEnabled(true);
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
  
        mBtnFinish = (Button)findViewById(R.id.btn_finish);
        mBtnFinish.setEnabled(false);
        mBtnFinish.setOnClickListener(this);
        
        mSpinerPopWindow = new SpinerPopWindow(this);  
//        mSpinerPopWindow.refreshData(nameList, 0);  
        mSpinerPopWindow.setItemListener(new IOnItemSelectListener(){

			@Override
			public void onItemClick(int position) {
				// TODO Auto-generated method stub
				chooseQuestion(position);
			}
        	
        } );  
    }  
  
  
    @Override  
    public void onClick(View view) {  
    	Log.e("mydebug", "-------- onClick  " + view.getId());
        switch(view.getId()){  
        case R.id.choose_question_layout:
        case R.id.btn_pop:  
        case R.id.text_question:  
            showSpinWindow();  
            break;  
            
        case R.id.btn_finish:
        	Toast.makeText(context, "finish",  Toast.LENGTH_SHORT).show();
        	String question = (String) mTView.getText();
        	Editable answer = mAnswerText.getText();
        	
        	lockApplaction.setQuestion(question);
        	lockApplaction.setAnswer(answer.toString());
        	boolean isSuccess = lockApplaction.save();
        	lockApplaction.isFirst = false;
        	if(isSuccess){
        		lockApplaction.isLocked = false;
        		startActivity(new Intent(this, MainActivity.class));
        	}
        	
        	break;
        	
        case R.id.back:
        	Log.e("mydebug", "++++++  back ");
//        	startActivity(new Intent(this, LockActivity.class));
        	QuestionActivity.this.finish();
        	break;
        }  
    }  
      
  
    private void chooseQuestion(int pos){  
        if (pos >= 0 && pos <= nameList.size()){  
            String value = nameList.get(pos);  
          
            mTView.setText(value);  
        }  
    }  
  
      
//    private SpinerPopWindow mSpinerPopWindow;  
    private void showSpinWindow(){  
        Log.e("mydebug", "showSpinWindow");  
        mSpinerPopWindow.setWidth(mTView.getWidth());  
        mSpinerPopWindow.showAsDropDown(mTView);  
    }  
  
  
//    @Override  
//    public void onItemClick(int pos) {  
//        setHero(pos);  
//    }
    
    public interface IOnItemSelectListener {
    	public void onItemClick(int position);
    }

	
}
