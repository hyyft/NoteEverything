package com.hyyft.noteeverything.plan;


import java.io.FileDescriptor;
import java.io.IOException;

import com.hyyft.noteeverything.R;


import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 当任务时间到达的时候，显示此activity（类似dialog的activity）。
 * @author hyyft
 *
 */
public class DialogActivity extends Activity {

	private Button yesBtn , noBtn;
	private TextView planTitleTV;
    private MediaPlayer player = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialogactivity_layout);
		yesBtn = (Button)findViewById(R.id.plan_alarm_yes_btn);
		noBtn = (Button)findViewById(R.id.plan_alarm_no_btn);
		planTitleTV = (TextView)findViewById(R.id.plan_alarm_content_tv);
		planTitleTV.setText("是否开始任务");
		yesBtn.setOnClickListener(listener);
		noBtn.setOnClickListener(listener);
		
		alarm();  //启动响铃
		
	}
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
