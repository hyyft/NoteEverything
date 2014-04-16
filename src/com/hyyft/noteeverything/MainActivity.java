package com.hyyft.noteeverything;

import com.hyyft.noteeverything.fragment.HomeFragment;
import com.hyyft.noteeverything.fragment.PlanFragment;
import com.hyyft.noteeverything.service.MainService;
import com.hyyft.noteeverything.service.MainService.MainServiceBinder;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

	private TabHost tabHost;
	private TabSpec tabSpec;
	private LayoutInflater layoutInflater;
	private MainService mainService;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tabHost = (TabHost)findViewById(android.R.id.tabhost);
		tabHost.setup();
		layoutInflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);		
		tabHost.setOnTabChangedListener(listener);		
		initTab();
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
			FragmentTransaction fTransaction = fManager.beginTransaction();
			
			
			/** Detaches the homeFragment if exists */
			if (homeFragment != null)
				fTransaction.detach(homeFragment);

			/** Detaches the planFragment if exists */
			if (planFragment != null)
				fTransaction.detach(planFragment);
			
			
			if (tabId.equalsIgnoreCase("home")) {
				if (homeFragment == null) {
					fTransaction.add(android.R.id.tabcontent, new HomeFragment(),
							"home");
				} else {
					fTransaction.attach(homeFragment);
				}
			}
			else if( tabId.equalsIgnoreCase("plan") ){
				if (planFragment == null) {
					fTransaction.add(android.R.id.tabcontent, new PlanFragment(),
							"plan");
				} else {
					fTransaction.attach(planFragment);
				}
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

}
