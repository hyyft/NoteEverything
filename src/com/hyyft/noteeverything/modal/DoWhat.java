package com.hyyft.noteeverything.modal;

import java.util.Calendar;
 

/**
 * 每天做了什么事情
 * @author Administrator
 *
 */
public class DoWhat { 

	private int id;
	
	private int  order;
	private String content;
	
	private String bigTag;
	private String littleTag;

	private long beginTime;
	//private long endTime;
    private String date;
	
	
	public void setId(int id){
		this.id = id;
	}
	public int getId(){
		return this.id;
	}
	
	
	public void  setOrder(int order){
		this.order = order;
	}
	public  int  getOrder(){
		return this.order;
	}
	
	
	
	public  void setBigTag(String bigTag){
		this.bigTag = bigTag;
	}
	public String  getBigTag(){
		return this.bigTag;
	}
	
	public  void setLitleTag(String litleTag){
		this.littleTag = litleTag;
	}
	public String  getLitleTag(){
		return this.littleTag;
	}
	
	public  void setContent(String content){
		this.content = content;
	}
	public String  getContent(){
		return this.content;
	}
	
	public  void setBeginTime(long beginTime){
		this.beginTime = beginTime;
	}
	public long getBeginTime(){
		return this.beginTime;
	}
	
//	public  void setEndTime(long endTime){
//		this.endTime = endTime;
//	}
//	public long getEndTime(){
//		return this.endTime;
//	}
	
	public  void setDate(String date){
		this.date = date;
	}
	public String  getDate(){
		return this.date ;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		Calendar mCalendar=Calendar.getInstance();
		mCalendar.setTimeInMillis(beginTime);
		return id+":"+":"+order+":"+":"+bigTag+":"+littleTag+":"+content+
				":"+mCalendar.get(Calendar.HOUR_OF_DAY)+"|"+mCalendar.get(Calendar.MINUTE)+":"+date;
	}
	
	
	
	
}
