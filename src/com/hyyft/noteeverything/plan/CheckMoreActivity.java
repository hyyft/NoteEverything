package com.hyyft.noteeverything.plan;


import com.hyyft.noteeverything.R;
import com.hyyft.noteeverything.dao.DayPlanDao;
import com.hyyft.noteeverything.global.NoteGlobal;
import com.hyyft.noteeverything.modal.DayPlan;
import com.hyyft.noteeverything.myconst.PrefConst;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CheckMoreActivity extends Activity {

	private static final String TAG = "CheckMoreActivity";
	private TextView  timeTextView  , tagTextView ,planTimetTextView , statusTextView;
	private TextView titlText , contentText;
	private TextView rplanTimetTextView , rtimeTextView;
	private Button btn_back ;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        setContentView(R.layout.checkplan_layout);
        findView();
        btn_back.setOnClickListener(listener);
        Intent intent = getIntent();
        setView(intent);
        
	}
	
	private void setView(Intent intent) {
		// TODO Auto-generated method stub
		int index = intent.getIntExtra("index", -1);
		if(index == -1){
			Log.e(TAG, "index传递错误");
			return;
		}
		DayPlan dayPlan = new DayPlan();
		NoteGlobal noteGlobal = (NoteGlobal)getApplication();
		dayPlan = noteGlobal.planList.get(index);
		
		if(dayPlan.getIsFinish() == 0)statusTextView.setText("未开始");
		else if(dayPlan.getIsFinish() == 1)statusTextView.setText("完成");
		else if( dayPlan.getIsFinish() == 2 )statusTextView.setText("进行中");
		
		Time time = new Time();
		time.set(dayPlan.getPlanBeginTime());
		
		timeTextView.setText(time.year+"-"+(time.month+1)+"-"+time.monthDay+" "+
								time.hour+":"+time.minute);
		titlText.setText(dayPlan.getTitle());
		contentText.setText(dayPlan.getContent());
		tagTextView.setText(dayPlan.getBigTag()+"-"+dayPlan.getLitleTag());
		planTimetTextView.setText(""+dayPlan.getPlanTime());
		rplanTimetTextView.setText(""+dayPlan.getRealTime());
		
		if( dayPlan.getRealBeginTime()<=0 ){
			rtimeTextView.setText("未开始");
		}
		else{
			time.set(dayPlan.getRealBeginTime());
			rtimeTextView.setText(time.year+"-"+(time.month+1)+"-"+time.monthDay+" "+
					time.hour+":"+time.minute);
		}
		
		
	}

	/**
	 * 保存和取消按钮响应函数
	 */
	private OnClickListener listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}
	};
	
	
	
	/**
	 * 查找控件
	 */
	private void findView(){
		timeTextView = (TextView)findViewById(R.id.plan_check_tv_begintime);
		tagTextView = (TextView)findViewById(R.id.plan_check_tag_tv);
		planTimetTextView = (TextView)findViewById(R.id.plan_check_plantime_tv);
		titlText = (TextView)findViewById(R.id.plan_check_vtitle_tv);
		contentText = (TextView)findViewById(R.id.plan_check_vcontent_tv);
		btn_back = (Button)findViewById(R.id.plan_check_btn_back);
		statusTextView = (TextView)findViewById(R.id.plan_check_tv_sttatus);
		rplanTimetTextView = (TextView)findViewById(R.id.plan_check_realplantime_tv);
		rtimeTextView = (TextView)findViewById(R.id.plan_check_tv_realbegintime);
		
	}

	
	
	
}
