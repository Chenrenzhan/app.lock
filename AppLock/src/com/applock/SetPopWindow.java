package com.applock;

import java.util.ArrayList;
import java.util.List;







import com.applock.QuestionActivity.IOnItemSelectListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class SetPopWindow extends PopupWindow {

	private Context mContext;
	
	private View view; 
	
	private TextView changePsw;
	private TextView changeQuestion;
//	private ListView mListView;
//	private NormalSpinerAdapter mAdapter;
//	private IOnItemSelectListener mItemSelectListener;
	
//	private String[] questions;

	public SetPopWindow(Context context) {
		super(context);

		mContext = context;
		init();
	}

//	public void setItemListener(IOnItemSelectListener listener) {
//		mItemSelectListener = listener;
//	}

	private void init() {

		LayoutInflater inflater = (LayoutInflater) mContext  
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
        view = inflater.inflate(R.layout.set_popwindow, null);  
        int h = ((Activity) mContext).getWindowManager().getDefaultDisplay().getHeight();  
        int w = ((Activity) mContext).getWindowManager().getDefaultDisplay().getWidth();  
        // 设置SelectPicPopupWindow的View  
        this.setContentView(view);  
        // 设置SelectPicPopupWindow弹出窗体的宽  
        this.setWidth(w / 2 + 50);  
        // 设置SelectPicPopupWindow弹出窗体的高  
        this.setHeight(LayoutParams.WRAP_CONTENT);  
        // 设置SelectPicPopupWindow弹出窗体可点击  
        this.setFocusable(true);  
        this.setOutsideTouchable(true);  
        // 刷新状态  
        this.update();  
        // 实例化一个ColorDrawable颜色为半透明  
        ColorDrawable dw = new ColorDrawable(0000000000);  
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作  
        this.setBackgroundDrawable(dw);  
		
		changePsw = (TextView)view.findViewById(R.id.change_psw);
		changeQuestion = (TextView)view.findViewById(R.id.change_question);
		
		changePsw.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(mContext, "修改图形密码", Toast.LENGTH_SHORT).show();
				mContext.startActivity(new Intent(mContext, LockActivity.class));
				((Activity) mContext).finish();
			}
		});
		
		changeQuestion.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(mContext, "修改密保问题", Toast.LENGTH_SHORT).show();
				mContext.startActivity(new Intent(mContext, QuestionActivity.class));
				((Activity) mContext).finish();
			}
		});
	}
	
	/** 
     * 显示popupWindow 
     *  
     * @param parent 
     */  
    public void showPopupWindow(View parent) {  
        if (!this.isShowing()) {  
            // 以下拉方式显示popupwindow  
            this.showAsDropDown(parent, parent.getLayoutParams().width / 2, 18);  
        } else {  
            this.dismiss();  
        }  
    }  

}