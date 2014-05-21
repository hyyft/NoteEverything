package com.hyyft.noteeverything.plan;


import com.hyyft.noteeverything.R;
import com.hyyft.noteeverything.dao.DayPlanDao;
import com.hyyft.noteeverything.global.NoteGlobal;
import com.hyyft.noteeverything.modal.DayPlan;
import com.hyyft.noteeverything.myconst.PrefConst;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
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

public class AddPlanActivity extends Activity {

	private TextView dateTextView , timeTextView  , tagTextView ,planTimetTextView;
	private EditText titlText , contentText;
	private Button btn_save , btn_giveup;
	private NoteGlobal noteGlobal;
	private String datesString;
	private PlanTextViewListener tvListener;
	private long mtime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		noteGlobal = (NoteGlobal)getApplication();
        setContentView(R.layout.addplan_layout);
        findView();
        setTime();
        setTag();
        btn_save.setOnClickListener(listener);
        btn_giveup.setOnClickListener(listener);
        
        tvListener = new PlanTextViewListener(this);
        dateTextView.setOnClickListener(tvListener);
        timeTextView.setOnClickListener(tvListener);
        planTimetTextView.setOnClickListener(tvListener);
        tagTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onTagTextView();
			}
		});
        
	}
	
	/**
	 * 保存和取消按钮响应函数
	 */
	private OnClickListener listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if( v.getId() == R.id.add_btn_save ){
				DayPlan dayPlan = new DayPlan();
				
				dayPlan.setTitle( titlText.getText().toString() );
				dayPlan.setContent(contentText.getText().toString() );
				dayPlan.setOrder(noteGlobal.maxPlanOrder+1);
				
				dayPlan.setBigTag(tagTextView.getText().toString().split("-")[0]);
				dayPlan.setLitleTag(tagTextView.getText().toString().split("-")[1]);
				
				dayPlan.setDate(dateTextView.getText().toString());
				dayPlan.setIsFinish((short)0);
				
				Time time = new Time();
				time.setToNow();
				time.hour = 0;
				time.minute = 0;
				time.second = 0;
				mtime = new DateTransfrom().timeToLong(dayPlan.getDate(), timeTextView.getText().toString());
				if( mtime<time.toMillis(false) ){
					dialog("不可以制定往日计划，日期要大于或等于今天！", "错误提示");
					return;
				}
				if(dayPlan.getTitle().equals("")){
					dialog("计划的标题不可为空！", "错误提示");
					return;
				}
					
				dayPlan.setPlanBeginTime(mtime);
				dayPlan.setRealBeginTime(-1);
				dayPlan.setRealTime(0);
				dayPlan.setPlanTime(Integer.valueOf(planTimetTextView.getText().toString()));
				if( datesString.equals(dayPlan.getDate()) )
					noteGlobal.AddAPlan(dayPlan);
				DayPlanDao dbDao = new DayPlanDao(AddPlanActivity.this);
				dbDao.add(dayPlan);
				 
				setResult(1);
			}
			else if( v.getId() == R.id.add_btn_giveup ){
				finish();
				setResult(0);
			}
			else Log.i("AddPlanActivity" , "无法获取按钮");
			finish();
		}
	};
	
	/**
	 * 设置标签
	 */
	private void setTag(){
		SharedPreferences shPreferences = this.getSharedPreferences(PrefConst.NAME, MODE_PRIVATE);
		String bigTag = shPreferences.getString(PrefConst.DATELY_TAG, "无");
		String littleTag = shPreferences.getString(PrefConst.DATELY_TAG+"-"+"1", "无");
		tagTextView.setText(bigTag+"-"+littleTag);
	}
	
	/**
	 * 查找控件
	 */
	private void findView(){
		dateTextView = (TextView)findViewById(R.id.add_date_textview);
		timeTextView = (TextView)findViewById(R.id.add_time_textview);
		tagTextView = (TextView)findViewById(R.id.add_tag_textview);
		planTimetTextView = (TextView)findViewById(R.id.add_plantime_textview);
		titlText = (EditText)findViewById(R.id.add_title_edittext);
		contentText = (EditText)findViewById(R.id.add_content_edittext);
		btn_save = (Button)findViewById(R.id.add_btn_save);
		btn_giveup = (Button)findViewById(R.id.add_btn_giveup);
		
	}

	//设置是时间标签
	private void setTime(){
		Time time = new Time();
		time.setToNow();
		mtime = time.toMillis(true);
		datesString = time.year+"-"+(time.month+1)+"-"+time.monthDay;
		dateTextView.setText(datesString);
		timeTextView.setText(time.hour+":"+time.minute);
		
	}
	
	private void onTagTextView(){
		
		Intent intent = new Intent(this , TagActivity.class);
	    startActivityForResult(intent, 1);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case 1:
			if( resultCode == 1 )
			tagTextView.setText(data.getStringExtra("bigtag")+"-"+data.getStringExtra("littletag"));
			break;

		default:
			break;
		}
	}
	
	protected void dialog(String Message, String Title) {
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage(Message);
		builder.setTitle(Title);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

}
