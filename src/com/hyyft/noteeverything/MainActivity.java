package com.hyyft.noteeverything;

import com.hyyft.noteeverything.backupandrestore.BackUpActivity;
import com.hyyft.noteeverything.backupandrestore.RestoreActivity;
import com.hyyft.noteeverything.fragment.DoFragment;
import com.hyyft.noteeverything.fragment.HomeFragment;
import com.hyyft.noteeverything.fragment.PlanFragment;
import com.hyyft.noteeverything.fragment.CheckFragment;
import com.hyyft.noteeverything.service.MainService;
import com.hyyft.noteeverything.service.MainService.MainServiceBinder;
import com.hyyft.noteeverything.setting.SettingActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

	public static TabHost tabHost;
	public static int index = 0;
	private TabSpec tabSpec;
	private LayoutInflater layoutInflater;
	public static MainService mainService;
	private GestureDetector gestureDetector; 
	private OnTouchListener gestureListener; 
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tabHost = (TabHost)findViewById(android.R.id.tabhost);
		tabHost.setup();
		layoutInflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);		
		tabHost.setOnTabChangedListener(listener);	
		initTab();
		
		gestureDetector = new GestureDetector(new GestureListener(this, 0));
		gestureListener = new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (gestureDetector.onTouchEvent(event)) {
					return true;
				}
				return false;
			}
		};
		
	}
	
	
	
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if (gestureDetector.onTouchEvent(ev)) {  
			ev.setAction(MotionEvent.ACTION_CANCEL);  
        } 
		
		return super.dispatchTouchEvent(ev);
	}




	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Intent intent = new Intent(this, MainService.class);
		startService(intent);
		bindService(intent, sConnection, BIND_AUTO_CREATE);
		
		//mainService.Log();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		unbindService(sConnection);
	}


    /**
     * sConnection变量用于连接mainService
     */
	private ServiceConnection sConnection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			mainService = ((MainServiceBinder)service).getService();
		}
	};
	
	/**
	 * 响应tab变化事件 
	 */
	private OnTabChangeListener listener = new OnTabChangeListener() {

		@Override
		public void onTabChanged(String tabId) {
			// TODO Auto-generated method stub
			FragmentManager fManager = getSupportFragmentManager();
			HomeFragment homeFragment = (HomeFragment)fManager.findFragmentByTag("home");
			PlanFragment planFragment = (PlanFragment)fManager.findFragmentByTag("plan");
			DoFragment doFragment = (DoFragment)fManager.findFragmentByTag("do");
			CheckFragment checkFragment = (CheckFragment)fManager.findFragmentByTag("check");
			FragmentTransaction fTransaction = fManager.beginTransaction();
			
			
			/** Detaches the homeFragment if exists */
			if (homeFragment != null)
				fTransaction.detach(homeFragment);

			/** Detaches the planFragment if exists */
			if (planFragment != null)
				fTransaction.detach(planFragment);
			
			if (doFragment != null)
				fTransaction.detach(doFragment);

			
			if (checkFragment != null)
				fTransaction.detach(checkFragment);
			
			
			if (tabId.equalsIgnoreCase("home")) {
				if (homeFragment == null) {
					fTransaction.add(android.R.id.tabcontent, new HomeFragment(),
							"home");
				} else {
					fTransaction.attach(homeFragment);
				}
				index = 0;
			}
			else if( tabId.equalsIgnoreCase("plan") ){
				if (planFragment == null) {
					fTransaction.add(android.R.id.tabcontent, new PlanFragment(),
							"plan");
				} else {
					fTransaction.attach(planFragment);
				}
				index = 1;
			}
			else if( tabId.equalsIgnoreCase("do") ){
				if (doFragment == null) {
					fTransaction.add(android.R.id.tabcontent, new DoFragment(),
							"do");
				} else {
					fTransaction.attach(doFragment);
				}
				index = 2;
			}
			else if( tabId.equalsIgnoreCase("check") ){
				if (checkFragment == null) {
					fTransaction.add(android.R.id.tabcontent, new CheckFragment(),
							"check");
				} else {
					fTransaction.attach(checkFragment);
				}
				index = 3;
			}
			fTransaction.commit();
		}
	};
	
	
	/**
	 * 初始化tab
	 */
	private void initTab(){
		tabSpec = tabHost.newTabSpec("home").setIndicator(getView(getResources().getString(R.string.text_home))) 
				.setContent(new DummyTabContent(getBaseContext()));
		
		tabHost.addTab(tabSpec);
		
		tabSpec = tabHost.newTabSpec("plan").setIndicator(getView(getResources().getString(R.string.text_plan)))
				.setContent(new DummyTabContent(getBaseContext()));
		tabHost.addTab(tabSpec);
		
		tabSpec = tabHost.newTabSpec("do").setIndicator(getView(getResources().getString(R.string.text_do)))
				.setContent(new DummyTabContent(getBaseContext()));
		tabHost.addTab(tabSpec);
		
		tabSpec = tabHost.newTabSpec("check").setIndicator(getView(getResources().getString(R.string.text_check)))
				.setContent(new DummyTabContent(getBaseContext()));
		tabHost.addTab(tabSpec);
	}
	
	/**
	 * 得到显示tabwidget的View对象
	 * @param tabName  tabwidget title
	 * @return
	 */
	private View getView(String tabName){
		
		View view = layoutInflater.inflate(R.layout.tab_item, null);
		TextView textView = (TextView)view.findViewById(R.id.tab_textview);
		textView.setText("  "+tabName+"  ");
		textView.setBackgroundResource(R.drawable.tab_item_style);
		return view;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
//		ActionBar actionBar = getActionBar();
//		actionBar.setHomeButtonEnabled(true);
		return true;
	}




	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		String title = item.getTitle().toString();
		Log.i("MainActivity", title);
		if(title.equals(this.getString(R.string.text_backup))){
			Intent intent = new Intent(this , BackUpActivity.class);
			startActivity(intent);
		}
		else if (title.equals(this.getString(R.string.text_restore))) {
			Intent intent = new Intent(this , RestoreActivity.class);
			startActivity(intent);
		}
		else if (title.equals(this.getString(R.string.text_set))) {
			Intent intent = new Intent(this , SettingActivity.class);
			startActivity(intent);
		}
		else if (title.equals(this.getString(R.string.text_help))) {
			
		}
		else if (title.equals(this.getString(R.string.text_quit))) {
			MainActivity.this.finish();
		}
		return true;
	}
	
	

}
