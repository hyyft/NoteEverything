package com.hyyft.noteeverything.plan;


import java.io.FileDescriptor;
import java.io.IOException;

import com.hyyft.noteeverything.R;
import com.hyyft.noteeverything.service.MainService;
import com.hyyft.noteeverything.service.MainService.MainServiceBinder;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * ������ʱ�䵽���ʱ����ʾ��activity������dialog��activity����
 * @author hyyft
 *
 */
public class DialogActivity extends Activity {

	private Button yesBtn , noBtn;
	private TextView planTitleTV;
    private MediaPlayer player = null;
    private MainService mainService;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		setContentView(R.layout.dialogactivity_layout);
		yesBtn = (Button)findViewById(R.id.plan_alarm_yes_btn);
		noBtn = (Button)findViewById(R.id.plan_alarm_no_btn);
		planTitleTV = (TextView)findViewById(R.id.plan_alarm_content_tv);
		planTitleTV.setText("�Ƿ�ʼ����:\n"+intent.getStringExtra("TEXT"));
		yesBtn.setOnClickListener(listener);
		noBtn.setOnClickListener(listener);
		
		alarm();  //��������
		
	}
	
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Intent intent = new Intent(this, MainService.class);
		bindService(intent, sConnection, BIND_AUTO_CREATE);
	}



	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
	 /**
     * sConnection������������mainService
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
			mainService.updateAlarm();
		}
	};


	private OnClickListener listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if( v.getId() == R.id.plan_alarm_yes_btn ){
				player.stop();
				finish();
			}
			else if( v.getId() == R.id.plan_alarm_no_btn){
				player.stop();
				finish();
			}
		}
	};
	
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if( keyCode == KeyEvent.KEYCODE_BACK ){
			player.stop();
			finish();
		}
		return false;
	}



	private void alarm(){

		// TODO Auto-generated method stub
		if( player == null )
			player = new MediaPlayer();
		else player.reset();
		FileDescriptor fd= getResources().openRawResourceFd(R.raw.m_001).getFileDescriptor();
		try {
			player.setDataSource(fd);		
			player.prepare();
			player.start();
			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
}
