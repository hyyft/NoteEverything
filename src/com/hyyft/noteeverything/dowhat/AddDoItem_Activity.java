package com.hyyft.noteeverything.dowhat;

import java.util.Calendar;

import com.hyyft.noteeverything.R;
import com.hyyft.noteeverything.global.NoteGlobal;
import com.hyyft.noteeverything.modal.DoWhat;
import com.hyyft.noteeverything.plan.CreateTimeDialog;
import com.hyyft.noteeverything.plan.CreateTimeDialog.CreateTimeDialogCallBack;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddDoItem_Activity extends Activity{

	
	private TextView beginTimeTextView ,  tagtTextView;
	private Button saveButton , giveUpButton;
	private EditText contentEditText;
	private NoteGlobal noteGlobal ;
	private DoWhat doItem;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		
		
		initDoItem();
		super.onCreate(savedInstanceState);
		Time time = new Time();
		setContentView(R.layout.dofragment_add_doitem);
		findView();
		
		beginTimeTextView.setOnClickListener(textViewListener);
		tagtTextView.setOnClickListener(textViewListener);
		tagtTextView.setText("日常-吃饭");
		time.setToNow();
		beginTimeTextView.setText(""+time.hour+":"+time.minute);
		
		saveButton.setOnClickListener(btnListener);
		giveUpButton.setOnClickListener(btnListener);
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
	}
	
	/**
	 * init doitem 的部分数据
	 */
	private void initDoItem(){
		Calendar calendar = Calendar.getInstance();
		doItem = new DoWhat();
		doItem.setDate(calendar.get(Calendar.YEAR)+"-"
				+(calendar.get(Calendar.MONTH)+1)+"-"
				+calendar.get(Calendar.DATE));
		Log.i("yuan" , doItem.getDate());
		doItem.setBeginTime(calendar.getTimeInMillis());
	}
	
	/**
	 * 查找资源id
	 */
	private void findView(){
		beginTimeTextView = (TextView)findViewById(R.id.do_tv_begin_time);
//		endTimeTextView = (TextView)findViewById(R.id.do_tv_end_time);
		tagtTextView = (TextView)findViewById(R.id.do_tv_tag);
		saveButton = (Button)findViewById(R.id.do_btn_save);
		giveUpButton = (Button)findViewById(R.id.do_btn_giveup);
		contentEditText = (EditText)findViewById(R.id.do_et_content);
		
	}
	
	/**
	 * 所有textView的监听函数
	 */
	private OnClickListener textViewListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.do_tv_begin_time:
				
				CreateTimeDialogCallBack callBack1 = new CreateTimeDialogCallBack() {
					
					@Override
					public void timeDialogCallBack(String time) {
						// TODO Auto-generated method stub
						
						
					}
					
					@Override
					public void timeDialogCallBack(int hour, int minute) {
						// TODO Auto-generated method stub
						
						Calendar calendar = Calendar.getInstance();
						calendar.set(calendar.get(Calendar.YEAR),
								calendar.get(Calendar.MONTH), 
								calendar.get(Calendar.DATE), 
								hour, 
								minute,
								0);
						doItem.setBeginTime(calendar.getTimeInMillis());
						beginTimeTextView.setText(""+hour+":"+minute);
					}
					
					@Override
					public void ptimeDialogCallBack(int hour, int minute) {
						// TODO Auto-generated method stub
						
						
					}
					
					@Override
					public void dateDialogCallBack(String date) {
						// TODO Auto-generated method stub
						
					}
				};
				new CreateTimeDialog(AddDoItem_Activity.this , callBack1).getTime1();
				break;
			case R.id.do_tv_tag:
				Intent intent = new Intent(AddDoItem_Activity.this , TagActivity.class  );
				AddDoItem_Activity.this.startActivityForResult(intent, 1);
				break;

			default:
				break;
			}
		}
	};
	
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case 1:
			if( resultCode == 1 ){
				tagtTextView.setText(data.getStringExtra("bigtag")+"-"+data.getStringExtra("littletag"));
			}
			break;

		default:
			break;
		}
	}

	/**
	 * 所有Button的监听函数
	 */
	private OnClickListener btnListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.do_btn_save:
				doItem.setBigTag(tagtTextView.getText().toString().split("-")[0]);
				doItem.setLitleTag(tagtTextView.getText().toString().split("-")[1]);
				
				noteGlobal = (NoteGlobal) AddDoItem_Activity.this.getApplication();
				doItem.setContent(contentEditText.getText().toString());
				doItem.setOrder(noteGlobal.getMaxDoItemOrder(doItem.getDate()));
				noteGlobal.AddDoItem(doItem);
				setResult(1);
				AddDoItem_Activity.this.finish();
				break;
			case R.id.do_btn_giveup:
				AddDoItem_Activity.this.finish();
				break;

			default:
				break;
			}
			
		}
	};

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	

}
