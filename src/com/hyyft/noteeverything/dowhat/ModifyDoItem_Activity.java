package com.hyyft.noteeverything.dowhat;

import java.util.Calendar;

import com.hyyft.noteeverything.R;
import com.hyyft.noteeverything.R.string;
import com.hyyft.noteeverything.dao.PlanDbHelperContract;
import com.hyyft.noteeverything.global.NoteGlobal;
import com.hyyft.noteeverything.modal.DoWhat;
import com.hyyft.noteeverything.plan.CreateTimeDialog;
import com.hyyft.noteeverything.plan.CreateTimeDialog.CreateTimeDialogCallBack;

import android.R.integer;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ModifyDoItem_Activity extends Activity{

	
	private TextView beginTimeTextView ,  tagtTextView;
	private Button saveButton , giveUpButton;
	private EditText contentEditText;
	private NoteGlobal noteGlobal ;
	private DoWhat doItem;
	private DoWhat doTemp;
	private int index = -1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		doTemp = new DoWhat();
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.dofragment_add_doitem);
		findView();
		
		beginTimeTextView.setOnClickListener(textViewListener);
		tagtTextView.setOnClickListener(textViewListener);
		
		saveButton.setOnClickListener(btnListener);
		giveUpButton.setOnClickListener(btnListener);
		
		index = getIntent().getIntExtra("index", -1);	
		initDoItem(index);
		
	}
	
	/**
	 * init doitem 的部分数据
	 */
	private void initDoItem(int index){
		if (index==-1) return;
		noteGlobal = (NoteGlobal) ModifyDoItem_Activity.this.getApplication();
		doItem = noteGlobal.doList.get(index);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(doItem.getBeginTime());
		beginTimeTextView.setText(calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE));
		tagtTextView.setText(doItem.getBigTag()+"-"+doItem.getLitleTag());
		contentEditText.setText(doItem.getContent());
	}
	
	/**
	 * 查找资源id
	 */
	private void findView(){
		beginTimeTextView = (TextView)findViewById(R.id.do_tv_begin_time);
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
						String dateString = doItem.getDate();
						calendar.set(Integer.valueOf(dateString.split("-")[0]).intValue(),
								Integer.valueOf(dateString.split("-")[1]).intValue() - 1, 
								Integer.valueOf(dateString.split("-")[2]).intValue(), 
								hour, 
								minute);
						doTemp.setBeginTime(calendar.getTimeInMillis());
						beginTimeTextView.setText(""+hour+"-"+minute);
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
				new CreateTimeDialog(ModifyDoItem_Activity.this , callBack1).getTime1();
				break;
			case R.id.do_tv_tag:
				Intent intent = new Intent(ModifyDoItem_Activity.this , TagActivity.class  );
				ModifyDoItem_Activity.this.startActivityForResult(intent, 1);
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
				doItem.setBeginTime(doTemp.getBeginTime());
				
				noteGlobal = (NoteGlobal) ModifyDoItem_Activity.this.getApplication();
				doItem.setContent(contentEditText.getText().toString());
				
				ContentValues values = new ContentValues();
				values.put(PlanDbHelperContract.DoWhatTableInfo.COLUMN_NAME_BEGINTIME, doItem.getBeginTime());
				values.put(PlanDbHelperContract.DoWhatTableInfo.COLUMN_NAME_BIGTAG, doItem.getBigTag());
				values.put(PlanDbHelperContract.DoWhatTableInfo.COLUMN_NAME_LITTLETAG, doItem.getLitleTag());
				values.put(PlanDbHelperContract.DoWhatTableInfo.COLUMN_NAME_CONTENT, doItem.getContent());
				
				noteGlobal.UpdateDoItem(values, index, doItem.getDate());
				
				
				Intent data =new Intent();
				setResult(1, data);
				ModifyDoItem_Activity.this.finish();
				break;
			case R.id.do_btn_giveup:
				ModifyDoItem_Activity.this.finish();
				break;

			default:
				break;
			}
			
		}
	};

	
	

	}
