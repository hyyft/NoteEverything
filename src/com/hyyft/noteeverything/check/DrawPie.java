package com.hyyft.noteeverything.check;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import com.hyyft.noteeverything.dao.DaoDbHelper;
import com.hyyft.noteeverything.dao.DayPlanDao;
import com.hyyft.noteeverything.dao.DoWhatDao;
import com.hyyft.noteeverything.dao.PlanDbHelperContract;
import com.hyyft.noteeverything.modal.DayPlan;
import com.hyyft.noteeverything.modal.DoWhat;


import android.R.integer;
import android.content.Context;
import android.graphics.Color;
import android.text.format.Time;
import android.util.Log;


/**
 * ��Բ��ͼ
 * @author Administrator
 *
 */
public class DrawPie {

	private List<String> bigTagList;
	public static final int DRAW_PLANTODO=1;
    public static final int DRAW_ACTULLY=2;
    public static final int DRAW_DO=3;
    
    private int count = 1;
	private Context context;
	private GraphicalView graphicalView;
	private CategorySeries dataset; 
	private DefaultRenderer renderer;
	private int[] colors = {Color.BLUE , Color.GREEN , Color.RED , Color.YELLOW , Color.CYAN , Color.GRAY
							,Color.DKGRAY , Color.LTGRAY , Color.MAGENTA , Color.TRANSPARENT , Color.BLACK , Color.parseColor("#0099CC")};
	public DrawPie( Context context , List<String> bigTagList){
		this.context = context;
		this.bigTagList = bigTagList;
	}
	
	
	
	public GraphicalView drawPlan(String date , String bigTag , int tag){
		dataset = buildCategoryDataset(date, bigTag ,tag);
		renderer = buildCategoryRenderer(date , bigTag , count);
		graphicalView=ChartFactory.getPieChartView(context, dataset, renderer);
		return graphicalView;
	}
	public GraphicalView drawDowhat(String date  , String bigTag){
		dataset = buildCategoryDataset(date, bigTag , DRAW_DO);
		renderer = buildCategoryRenderer(date , bigTag , count);
		graphicalView=ChartFactory.getPieChartView(context, dataset, renderer);
		return graphicalView;
	}
	
	
	private CategorySeries buildCategoryDataset(String date , String bigTag , int tag ) {
		CategorySeries series = new CategorySeries(bigTag);
		Map<String, Integer > map ;
		
		if(tag == DRAW_PLANTODO )map = getPlanSituation(bigTag ,date);
		else if(tag == DRAW_ACTULLY) map = getRealSituation(bigTag , date);
		else map = getDoWhat(bigTag , date);
		
		Iterator<Map.Entry<String,Integer>> iterator = map.entrySet().iterator();
		if(  !iterator.hasNext() ) {
			series.add("������",1);
			return series;
		}
		while(iterator.hasNext()){
			++count;
			
			 Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) iterator.next();
			 series.add(entry.getKey() , entry.getValue().intValue());
		}
		--count;
		return series;
	}
	
	private DefaultRenderer buildCategoryRenderer(String date , String bigTag , int n) {
        DefaultRenderer renderer = new DefaultRenderer();
       
        renderer.setLegendTextSize(20);//�������½Ǳ�ע�����ִ�С
        renderer.setLabelsColor(Color.BLACK);
      //renderer.setZoomButtonsVisible(true);//������ʾ�Ŵ���С��ť  
        renderer.setZoomEnabled(false);//���ò�����Ŵ���С.  
          renderer.setChartTitleTextSize(30);//����ͼ���������ִ�С
          renderer.setChartTitle(bigTag);//����ͼ��ı���  Ĭ���Ǿ��ж�����ʾ
          renderer.setLabelsTextSize(20);//��ͼ�ϱ�����ֵ������С
          renderer.setPanEnabled(false);//�����Ƿ����ƽ��
          renderer.setDisplayValues(true);//�Ƿ���ʾֵ
          renderer.setClickEnabled(true);//�����Ƿ���Ա����
        renderer.setMargins(new int[] { 20, 30, 15,0 });
        //margins - an array containing the margin size values, in this order: top, left, bottom, right
        for (int j=1 ; j<=n ; j++) {
          SimpleSeriesRenderer r = new SimpleSeriesRenderer();
          r.setColor(colors[j]);
          renderer.addSeriesRenderer(r);
        }
        return renderer;
      }
	
	private Map<String, Integer> getPlanSituation(String bigTag , String date){
		Map<String, Integer > map ;
		if( bigTag == "ȫ��" ){
			map = getPlanALl(date);
		}
		else {
			map = getPlanByBigTag(bigTag, date);
		}
		
		return map;
	}
	
	private Map<String, Integer> getRealSituation(String bigTag , String date){
		Map<String, Integer > map ;
		if( bigTag == "ȫ��" ){
			map = getRealALl(date);
		}
		else {
			map = getRealByBigTag(bigTag, date);
		}
		
		return map;
	}
	
	private Map<String, Integer> getDoWhat(String bigTag , String date){
		Map<String, Integer > map ;
		if( bigTag == "ȫ��" ){
			map = getDoALl(date);
		}
		else {
			map = getDoByBigTag(bigTag, date);
		}
		
		return map;
	}
	
	
	
	private Map<String, Integer> getPlanALl(String date){
		//�ڸô���ȡ���мƻ������
		Map<String, Integer> map = new HashMap<String, Integer>();
		DayPlanDao dao = new DayPlanDao(context);
		Integer integer;
		List<DayPlan> list = dao.getAll(PlanDbHelperContract.PlanTableInfo.PLAN_TABLE_NAME, date);
		for(int i=0 ; i < list.size() ; i++){
			if( (integer = map.get(list.get(i).getBigTag()) )== null )
				map.put( list.get(i).getBigTag(), list.get(i).getPlanTime() );
			else 
				map.put(list.get(i).getBigTag(), integer.intValue()+list.get(i).getPlanTime());			
		}
		return map;
	}
	private Map<String, Integer> getPlanByBigTag(String bigTag , String date){
		//�ڸô���ȡ����bigtag����ļƻ�
		Map<String, Integer> map = new HashMap<String, Integer>();
		DayPlanDao dao = new DayPlanDao(context);
		Integer integer;
		List<DayPlan> list = dao.getAll(PlanDbHelperContract.PlanTableInfo.PLAN_TABLE_NAME, date);
		for(int i=0 ; i < list.size() ; i++){
			if( ! list.get(i).getBigTag().equals(bigTag))continue;
			if( (integer = map.get(list.get(i).getLitleTag()) ) == null )
				map.put( list.get(i).getLitleTag(), list.get(i).getPlanTime() );
			else 
				map.put(list.get(i).getLitleTag(), integer.intValue()+list.get(i).getPlanTime());			
		}
		return map;
	}
	
	private Map<String, Integer> getRealALl(String date){
		//�ڸô���ȡ���мƻ������
		Map<String, Integer> map = new HashMap<String, Integer>();
		DayPlanDao dao = new DayPlanDao(context);
		Integer integer;
		List<DayPlan> list = dao.getAll(PlanDbHelperContract.PlanTableInfo.PLAN_TABLE_NAME, date);
		for(int i=0 ; i < list.size() ; i++){
			if( (integer = map.get(list.get(i).getBigTag()) )== null )
				map.put( list.get(i).getBigTag(), list.get(i).getRealTime() );
			else 
				map.put(list.get(i).getBigTag(), integer.intValue()+list.get(i).getRealTime());			
		}
		return map;
	}
	private Map<String, Integer> getRealByBigTag(String bigTag , String date){

		//�ڸô���ȡ����bigtag����ļƻ�
		Map<String, Integer> map = new HashMap<String, Integer>();
		DayPlanDao dao = new DayPlanDao(context);
		Integer integer;
		List<DayPlan> list = dao.getAll(PlanDbHelperContract.PlanTableInfo.PLAN_TABLE_NAME, date);
		for(int i=0 ; i < list.size() ; i++){
			if( ! list.get(i).getBigTag().equals(bigTag))continue;
			//Log.i("yuan" , ""+list.get(i).getRealTime());
			if( (integer = map.get(list.get(i).getLitleTag()) ) == null )
				map.put( list.get(i).getLitleTag(), list.get(i).getRealTime() );
			else 
				map.put(list.get(i).getLitleTag(), integer.intValue()+list.get(i).getRealTime());			
		}
		return map;
	}
	
	private Map<String, Integer> getDoALl(String date){
		//�ڸô���ȡ���мƻ������
		Map<String, Integer> map = new HashMap<String, Integer>();
		DoWhatDao dao = new DoWhatDao(context);
		Integer integer;
		List<DoWhat> list = dao.getAll(PlanDbHelperContract.DoWhatTableInfo.TABLE_NAME, date);
		int i=0;
		for( ; i < list.size()-1 ; i++){
			
			if( (integer = map.get(list.get(i).getBigTag()) )== null ){
					map.put( list.get(i).getBigTag(), (int)( list.get(i+1).getBeginTime() - list.get(i).getBeginTime())/60000 );
			}
			else 
				map.put(list.get(i).getBigTag(), integer.intValue()+(int)( list.get(i+1).getBeginTime() - list.get(i).getBeginTime())/60000 );			
		}
		if(i!=0)
			map.put(list.get(i).getBigTag(), getEnd(date, list.get(i).getBeginTime()));
		return map;
	}
	
	private int getEnd(String date , long beginTime){
		String today ; int useTime;
		Time time = new Time();
		time.setToNow();
		today = time.year+"-"+(time.month+1)+"-"+time.monthDay;
		if(today.equals(date)){
			useTime = (int)( time.toMillis(false) - beginTime)/60000;
			//Log.i("DraPie", ""+useTime);
		}else{
			time.year = Integer.valueOf( date.split("-")[0] ).intValue();
			time.month = Integer.valueOf( date.split("-")[1] ).intValue() - 1;
			time.monthDay = Integer.valueOf( date.split("-")[2] ).intValue();
			time.hour = 23;
			time.minute = 59;
			time.second = 60;
			useTime = (int)(time.toMillis(false) - beginTime)/60000;
			
		}
		return useTime;
	}
	private Map<String, Integer> getDoByBigTag(String bigTag , String date){

		//�ڸô���ȡ����bigtag����ļƻ�
		Map<String, Integer> map = new HashMap<String, Integer>();
		DoWhatDao dao = new DoWhatDao(context);
		Integer integer;
		List<DoWhat> list = dao.getAll(PlanDbHelperContract.DoWhatTableInfo.TABLE_NAME, date);
		for(int i=0 ; i < list.size()-1 ; i++){
			if( ! list.get(i).getBigTag().equals(bigTag))continue;
			if( (integer = map.get(list.get(i).getLitleTag()) ) == null )
				map.put( list.get(i).getLitleTag(), (int)( list.get(i+1).getBeginTime() - list.get(i).getBeginTime())/60000 );
			else 
				map.put(list.get(i).getLitleTag(), integer.intValue()+(int)( list.get(i+1).getBeginTime() - list.get(i).getBeginTime())/60000);			
		}
		return map;
	}
}
