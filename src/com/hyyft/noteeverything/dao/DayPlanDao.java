package com.hyyft.noteeverything.dao;

import java.util.ArrayList;
import java.util.List;

import com.hyyft.noteeverything.dao.PlanDbHelperContract.PlanTableInfo;
import com.hyyft.noteeverything.modal.DayPlan;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DayPlanDao {
	DaoDbHelper mDbHelper;
	SQLiteDatabase db;
	public DayPlanDao(Context context ){
		mDbHelper = new DaoDbHelper(context);

	}
	
	/**
	 * dayplan �����
	 * @param dayPlan
	 */
	public void add(DayPlan dayPlan){
		db = mDbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(PlanTableInfo.COLUMN_NAME_LEVEL, dayPlan.getLevel());
		values.put(PlanTableInfo.COLUMN_NAME_ORDER, dayPlan.getOrder());
		values.put(PlanTableInfo.COLUMN_NAME_TITLE, dayPlan.getTitle());
		values.put(PlanTableInfo.COLUMN_NAME_CONTENT, dayPlan.getContent());
		values.put(PlanTableInfo.COLUMN_NAME_BIGTAG, dayPlan.getBigTag());
		values.put(PlanTableInfo.COLUMN_NAME_LITTLETAG, dayPlan.getLitleTag());
		values.put(PlanTableInfo.COLUMN_NAME_PLANBEGINTIME, dayPlan.getPlanBeginTime());
		values.put(PlanTableInfo.COLUMN_NAME_REALBEGINTIME, dayPlan.getRealBeginTime());
		values.put(PlanTableInfo.COLUMN_NAME_REALTIME, dayPlan.getRealTime());
		values.put(PlanTableInfo.COLUMN_NAME_PLANTIME, dayPlan.getPlanTime());
		values.put(PlanTableInfo.COLUMN_NAME_ISFINISH, dayPlan.getIsFinish());
		values.put(PlanTableInfo.COLUMN_NAME_DATE, dayPlan.getDate());	
		db.insert(PlanTableInfo.PLAN_TABLE_NAME, PlanTableInfo.COLUMN_NAME_TITLE, values);
		
		db.close();
	}
	
	/**
	 * ɾ��һ��dayplan
	 * @param order  ָ��ɾ����id �� ���Ϊ0 �� ɾ��ȫ��
	 * @param date   ָ��ɾ��plan������
	 */
	public void delete( int order , String date){
		db = mDbHelper.getWritableDatabase();

		if(order ==0 ){
			db.delete(PlanTableInfo.PLAN_TABLE_NAME,
					null,
					null);
			db.close();
			return;
		}
		db.delete(PlanTableInfo.PLAN_TABLE_NAME,
				PlanTableInfo.COLUMN_NAME_ORDER+"=? AND "+PlanTableInfo.COLUMN_NAME_DATE+"=?",
//				PlanTableInfo.COLUMN_NAME_ID+"=?",
				new String[]{ String.valueOf(order) , date  });
		
		db.close();
		
	}
	/**
	 * ��ȡĳ��ļƻ�
	 * @param date
	 * @return
	 */
	@SuppressLint("NewApi")
	public List<DayPlan> getAll(String tableName , String date){
		db = mDbHelper.getWritableDatabase();
		List<DayPlan> list = new ArrayList<DayPlan>();
		DayPlan dayPlan;
		Cursor cursor = db.query(true, tableName, null, 
				PlanTableInfo.COLUMN_NAME_DATE+"=?", 
				new String[]{ date }, 
				null, null, PlanTableInfo.COLUMN_NAME_PLANBEGINTIME , null, null);
		while(cursor.moveToNext()){
			dayPlan = new DayPlan();
			dayPlan.setId(cursor.getInt(cursor.getColumnIndex(PlanTableInfo.COLUMN_NAME_ID)));
			dayPlan.setLevel(cursor.getString(cursor.getColumnIndex(PlanTableInfo.COLUMN_NAME_LEVEL)));
			dayPlan.setTitle(cursor.getString(cursor.getColumnIndex(PlanTableInfo.COLUMN_NAME_TITLE)));
			dayPlan.setOrder(cursor.getInt(cursor.getColumnIndex(PlanTableInfo.COLUMN_NAME_ORDER)));
			dayPlan.setContent(cursor.getString(cursor.getColumnIndex(PlanTableInfo.COLUMN_NAME_CONTENT)));
			dayPlan.setBigTag(cursor.getString(cursor.getColumnIndex(PlanTableInfo.COLUMN_NAME_BIGTAG)));
			dayPlan.setLitleTag(cursor.getString(cursor.getColumnIndex(PlanTableInfo.COLUMN_NAME_LITTLETAG)));
			dayPlan.setPlanBeginTime(cursor.getLong(cursor.getColumnIndex(PlanTableInfo.COLUMN_NAME_PLANBEGINTIME)));
			dayPlan.setRealBeginTime(cursor.getLong(cursor.getColumnIndex(PlanTableInfo.COLUMN_NAME_REALBEGINTIME)));
			dayPlan.setPlanTime(cursor.getInt(cursor.getColumnIndex(PlanTableInfo.COLUMN_NAME_PLANTIME)));
			dayPlan.setRealTime(cursor.getInt(cursor.getColumnIndex(PlanTableInfo.COLUMN_NAME_REALTIME)));
			dayPlan.setIsFinish(cursor.getShort(cursor.getColumnIndex(PlanTableInfo.COLUMN_NAME_ISFINISH)));
			dayPlan.setDate(cursor.getString(cursor.getColumnIndex(PlanTableInfo.COLUMN_NAME_DATE)));
			list.add(dayPlan);
		}
		db.close();
		return list;	
	}
	
	@SuppressLint("NewApi")
	public int getMaxPlanOrder(String date){
		db = mDbHelper.getWritableDatabase();
		
		int order;
		Cursor cursor = db.query(true, PlanTableInfo.PLAN_TABLE_NAME, new String[]{"MAX("+PlanTableInfo.COLUMN_NAME_ORDER+")"}, 
				PlanTableInfo.COLUMN_NAME_DATE+"=?", 
				new String[]{ date }, 
				null, null, null , null, null);
		if(cursor.moveToNext()){
			order = cursor.getInt(0);
		}
		else{
			order = 0;
		}
			
		db.close();
		return order;	
	}
	
	
	
	
	
	
	/**
	 * ����ĳ��ļƻ�
	 * @param tableName
	 * @param values
	 * @param index
	 * @param date
	 */
	public void update(ContentValues values , int order , String date){
		db = mDbHelper.getWritableDatabase();
		db.update(PlanTableInfo.PLAN_TABLE_NAME, values,
				PlanTableInfo.COLUMN_NAME_ORDER+"=? and "
						+PlanTableInfo.COLUMN_NAME_DATE+"=?", 
				new String[]{ String.valueOf(order) , date });
		db.close();
	}
}
