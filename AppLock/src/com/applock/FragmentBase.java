package com.applock;

import java.util.ArrayList;
import java.util.List;

import com.applock.data.AppInfo;
import com.applock.data.QueryAppInfo;
import com.applock.tab.IndicatorFragmentActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public abstract class FragmentBase extends Fragment {

	protected Context context;
	protected Activity activity;
	
	protected QueryAppInfo queryAppInfo;
	protected List<AppInfo> listAppInfo;
	
    protected View rootView;
	protected ListView listView ;  
    protected Adapter adapter;  
    protected ProgressBar progressBar;  
    protected LinearLayout ll_loading;
    
    protected Drawable imgLock;
    
    protected TranslateAnimation myAnimationTranslate;
    protected int screenWidth;

	public FragmentBase(){
		super();
//		listAppInfo = new ArrayList<AppInfo>();
		
	}
	
	@Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) {  
		if (null != rootView) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        } else {
        	rootView = inflater.inflate(R.layout.app_list, null);
        	initView(rootView);// 控件初始化
        }

        return rootView;  
    }  
	
	public void initView(View rootView){
		  ll_loading = (LinearLayout) rootView.findViewById(R.id.ll_loading);
		  ll_loading.setVisibility(View.VISIBLE);
	}
	
	public void initListView() {
		
		listView = (ListView) rootView.findViewById(R.id.listview);
		listView.setVisibility(View.VISIBLE);

		setLockStatusIcon();
		
//		queryAppInfo.sortInfo();
		setNotifyDataChange();
		listAppInfo = setListAppInfo(queryAppInfo);
		
		adapter = new ListViewAdapter();
		listView.setAdapter((ListAdapter) adapter);
		
		setTabAppSize();
		
//		initAnimation();

		setItemClick(listView);
	}

	public void initAnimation(final int position) {
		DisplayMetrics metric = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
		screenWidth = metric.widthPixels; // 屏幕宽度（像素）
		int height = metric.heightPixels; // 屏幕高度（像素）
		float density = metric.density; // 屏幕密度（0.75 / 1.0 / 1.5）
		int densityDpi = metric.densityDpi; // 屏幕密度DPI（120 / 160 / 240）

		float endX = setEndX(screenWidth);
//		TranslateAnimation myAnimationTranslate;
		myAnimationTranslate = new TranslateAnimation(0f, endX, 0f, 0f);
		myAnimationTranslate.setFillAfter(true);
		myAnimationTranslate.setDuration(300);
		myAnimationTranslate.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
//				listAppInfo.remove(position);
//				((BaseAdapter) adapter).notifyDataSetChanged();
//				if(listAppInfo.size() != 1)
				removeView(position);
				
//				setTabAppSize();
				myAnimationTranslate.cancel();
			}
		});
	}
	
	protected abstract void setLockStatusIcon();
	protected abstract void setNotifyDataChange();
	protected abstract void removeView(int position);
	protected abstract float setEndX(int screenWidth);

	protected abstract List<AppInfo> setListAppInfo(QueryAppInfo queryAppInfo);
	public abstract void setTabAppSize();
	protected abstract void setToastTip(int position);

	protected void setItemClick(ListView listView) {
		// TODO Auto-generated method stub
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				setToastTip(position);
//				Toast.makeText(context, "item " + position, Toast.LENGTH_SHORT).show();
				
				
				final int pos = position;
//				if(listAppInfo.size() == 1){
//					removeView(position);
//				}
//				else{
					initAnimation(position);
					view.startAnimation(myAnimationTranslate);
//				}
				
//				removeView(position);
			}
		});
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity.getApplicationContext();
        this.activity = activity;
        
        if(listAppInfo == null){
    		listAppInfo = new ArrayList<AppInfo>();
    		
    		LoadAppInfoTask asyncTask = new LoadAppInfoTask();  
            asyncTask.execute(); 
        	
    	}
    }
	
	private class LoadAppInfoTask extends AsyncTask<String, Integer, String> {  
        //onPreExecute方法用于在执行后台任务前做一些UI操作  
        @Override  
        protected void onPreExecute() {  
        }  
          
        //doInBackground方法内部执行后台任务,不可在此方法内修改UI  
        @Override  
        protected String doInBackground(String... params) {  
        	queryAppInfo = QueryAppInfo.instance();
            return null;  
        }  
          
        //onProgressUpdate方法用于更新进度信息  
        @Override  
        protected void onProgressUpdate(Integer... progresses) {  
        }  
          
        //onPostExecute方法用于在执行完后台任务后更新UI,显示结果  
        @Override  
        protected void onPostExecute(String result) {  
        	ll_loading.setVisibility(View.INVISIBLE);
        	initListView(); //用ListView显示数据
        	
        }  
          
        //onCancelled方法用于在取消执行中的任务时更改UI  
        @Override  
        protected void onCancelled() {  
            
        }  
    }  
	
	public class ListViewAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listAppInfo.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return listAppInfo.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}
		
		
		public void notifyDataSetChanged(){
			setTabAppSize();
			super.notifyDataSetChanged();
			
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			System.out.println("getView at " + position);
			LayoutInflater infater = (LayoutInflater) context.getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			View view = null;
			ViewHolder holder = null;
			if (convertView == null || convertView.getTag() == null) {
				view = infater.inflate(R.layout.app_list_item, null);
				holder = new ViewHolder(view);
				view.setTag(holder);
			} 
			else{
				view = convertView ;
				holder = (ViewHolder) convertView.getTag() ;
			}
			final AppInfo appInfo = (AppInfo) getItem(position);
			holder.icon.setImageDrawable(appInfo.getAppIcon());
			holder.label.setText(appInfo.getAppLabel());
			holder.lock.setBackgroundDrawable(imgLock);//context.getResources().getDrawable(R.drawable.ic_unlock));
			
			holder.position = position;
			
			return view;
		}
	}
	
	public class ViewHolder{
		ImageView icon;
		TextView label;
		ImageView lock;
		
		int position;
		
		public ViewHolder(View view){
			icon = (ImageView)view.findViewById(R.id.app_icon);
			label = (TextView)view.findViewById(R.id.app_label);
			lock = (ImageView)view.findViewById(R.id.lock);
			position = 0;
		}
	}
}