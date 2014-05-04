package com.hyyft.noteeverything.dao;

import java.util.ArrayList;
import java.util.List;

import com.hyyft.noteeverything.dao.PlanDbHelperContract.DoWhatTableInfo;
import com.hyyft.noteeverything.modal.DoWhat;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DoWhatDao {
	DaoDbHelper mDbHelper;
	SQLiteDatabase db;
	public DoWhatDao(Context context ){
		mDbHelper = new DaoDbHelper(context);

	}
	
	/**
	 *  dowhat item �����
	 * @param doWhat
	 */
	public void add(DoWhat doWhat){
		db = mDbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DoWhatTableInfo.COLUMN_NAME_ORDER, doWhat.getOrder());
		values.put(DoWhatTableInfo.COLUMN_NAME_CONTENT, doWhat.getContent());
		values.put(DoWhatTableInfo.COLUMN_NAME_BIGTAG,doWhat.getBigTag());
		values.put(DoWhatTableInfo.COLUMN_NAME_LITTLETAG, doWhat.getLitleTag());
		values.put(DoWhatTableInfo.COLUMN_NAME_BEGINTIME, doWhat.getBeginTime());
		values.put(DoWhatTableInfo.COLUMN_NAME_DATE, doWhat.getDate());	
		db.insert(DoWhatTableInfo.TABLE_NAME, DoWhatTableInfo.COLUMN_NAME_CONTENT, values);
		
		db.close();
	}
	
	/**
	 * ɾ��һ��dowhat
	 * @param order  ָ��ɾ����id �� ���Ϊ0 �� ɾ��ȫ��
	 * @param date   ָ��ɾ��plan������
	 */
	public void delete( int order , String date){
		db = mDbHelper.getWritableDatabase();

//		if(order ==0 ){
//			db.delete(DoWhatTableInfo.TABLE_NAME,
//					null,
//					null);
//			db.close();
//			return;
//		}
		db.delete(DoWhatTableInfo.TABLE_NAME,
				DoWhatTableInfo.COLUMN_NAME_ORDER+"=? AND "+DoWhatTableInfo.COLUMN_NAME_DATE+"=?",
				new String[]{ String.valueOf(order) , date  });
		
		db.close();
		
	}
	/**
	 * ��ȡĳ���������е�����
	 * @param date
	 * @return
	 */
	@SuppressLint("NewApi")
	public List<DoWhat> getAll(String tableName , String date){
		db = mDbHelper.getWritableDatabase();
		List<DoWhat> list = new ArrayList<DoWhat>();
		DoWhat doWhat;
		Cursor cursor = db.query(true, tableName, null, 
				DoWhatTableInfo.COLUMN_NAME_DATE+"=?", 
				new String[]{ date }, 
				null, null, DoWhatTableInfo.COLUMN_NAME_BEGINTIME , null, null);
		while(cursor.moveToNext()){
			 doWhat = new DoWhat();
			 doWhat.setId(cursor.getInt(cursor.getColumnIndex(DoWhatTableInfo.COLUMN_NAME_ID)));
			 doWhat.setOrder(cursor.getInt(cursor.getColumnIndex(DoWhatTableInfo.COLUMN_NAME_ORDER)));
			 doWhat.setContent(cursor.getString(cursor.getColumnIndex(DoWhatTableInfo.COLUMN_NAME_CONTENT)));
			 doWhat.setBigTag(cursor.getString(cursor.getColumnIndex(DoWhatTableInfo.COLUMN_NAME_BIGTAG)));
			 doWhat.setLitleTag(cursor.getString(cursor.getColumnIndex(DoWhatTableInfo.COLUMN_NAME_LITTLETAG)));
			 doWhat.setBeginTime(cursor.getLong(cursor.getColumnIndex(DoWhatTableInfo.COLUMN_NAME_BEGINTIME)));
			 doWhat.setDate(cursor.getString(cursor.getColumnIndex(DoWhatTableInfo.COLUMN_NAME_DATE)));
			list.add(doWhat);
		}
		db.close();
		return list;	
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
		db.update(DoWhatTableInfo.TABLE_NAME, values,
				DoWhatTableInfo.COLUMN_NAME_ORDER+"=? and "
						+DoWhatTableInfo.COLUMN_NAME_DATE+"=?", 
				new String[]{ String.valueOf(order) , date });
		db.close();
	}
}
