package com.hyyft.noteeverything.plan;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;


/**
 * 用于显示timePicker和dateTimePicker
 * @author hyyft
 *
 */
public class CreateTimeDialog {
	
	Context context;
	CreateTimeDialogCallBack callBack;
	public CreateTimeDialog(Context context , CreateTimeDialogCallBack callBack){
		this.context = context;
		this.callBack = callBack;
	}

	public static final int DATE_DIALOG = 1;
	public static final int TIME_DIALOG = 2;
	protected static final String TAG = "CreateTimeDialog";
	
	public void getDate(){
		onCreateDialog(DATE_DIALOG).show();
	}
	public void getTime(){
		onCreateDialog(TIME_DIALOG).show();
	}
	
	private Dialog onCreateDialog(int id) { 
        //用来获取日期和时间的 
        Calendar calendar = Calendar.getInstance();  
         
        Dialog dialog = null; 
        switch(id) { 
            case DATE_DIALOG: 
                DatePickerDialog.OnDateSetListener dateListener =  
                    new DatePickerDialog.OnDateSetListener() {
                        @Override 
                        public void onDateSet(DatePicker datePicker,  
                                int year, int month, int dayOfMonth) { 
                        	callBack.dateDialogCallBack(""+year+"-"+(month+1)+"-"+dayOfMonth);
                        	
                        } 
                    }; 
                dialog = new DatePickerDialog(context, 
                        dateListener, 
                        calendar.get(Calendar.YEAR), 
                        calendar.get(Calendar.MONTH), 
                        calendar.get(Calendar.DAY_OF_MONTH)); 
                break; 
            case TIME_DIALOG: 
                TimePickerDialog.OnTimeSetListener timeListener =  
                    new TimePickerDialog.OnTimeSetListener() { 
                         
                        @Override 
                        public void onTimeSet(TimePicker timerPicker, 
                                int hourOfDay, int minute) { 
                        	callBack.timeDialogCallBack(""+hourOfDay+":"+minute);
                        	
                        } 
                    }; 
                    dialog = new TimePickerDialog(context , timeListener, 
                            calendar.get(Calendar.HOUR_OF_DAY), 
                            calendar.get(Calendar.MINUTE), 
                            true);   //是否为二十四制 
                break; 
            default: 
                break; 
        } 
        return dialog; 
    } 

	public interface CreateTimeDialogCallBack{
		public void dateDialogCallBack(String date);
		public void timeDialogCallBack(String time);
	}

}
