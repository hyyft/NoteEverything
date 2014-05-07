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
 * ������ʾtimePicker��dateTimePicker
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
	public static final int PLANTIME_DIALOG = 3;
	protected static final String TAG = "CreateTimeDialog";
	private int tag = 0;
	
	public void getDate(){
		onCreateDialog(DATE_DIALOG).show();
	}
	public void getTime(){
		onCreateDialog(TIME_DIALOG).show();
	}
	public void getPlanTime(  ){
		onCreateDialog(PLANTIME_DIALOG).show();
	}
	
	public void getTime1(){
		onCreateDialog(TIME_DIALOG).show();
		tag = 1;
	}
	
	private Dialog onCreateDialog(int id) { 
        //������ȡ���ں�ʱ��� 
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
                        	if(tag == 0)callBack.timeDialogCallBack(""+hourOfDay+":"+minute);
                        	else callBack.timeDialogCallBack(hourOfDay, minute);
                        } 
                    }; 
                    dialog = new TimePickerDialog(context , timeListener, 
                            calendar.get(Calendar.HOUR_OF_DAY), 
                            calendar.get(Calendar.MINUTE), 
                            true);   //�Ƿ�Ϊ��ʮ���� 
                break; 
            case PLANTIME_DIALOG:
            	TimePickerDialog.OnTimeSetListener ptimeListener =  
                new TimePickerDialog.OnTimeSetListener() { 
                     
                    @Override 
                    public void onTimeSet(TimePicker timerPicker, 
                            int hourOfDay, int minute) { 
                    	callBack.ptimeDialogCallBack(hourOfDay , minute);
                    	
                    } 
                }; 
                dialog = new TimePickerDialog(context , ptimeListener, 
                        0, 
                        0, 
                        true);   //�Ƿ�Ϊ��ʮ���� 
            break; 
            default: 
                break; 
        } 
        return dialog; 
    } 

	public interface CreateTimeDialogCallBack{
		public void dateDialogCallBack(String date);
		public void timeDialogCallBack(String time);
		public void timeDialogCallBack(int hour , int minute);
		public void ptimeDialogCallBack(int hour , int minute);
	}

}
