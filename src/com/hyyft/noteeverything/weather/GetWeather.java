package com.hyyft.noteeverything.weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hyyft.noteeverything.modal.CustomerHttpClient;
import com.hyyft.noteeverything.myconst.PrefConst;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.format.Time;
import android.util.Log;

public class GetWeather {

	private static final String TAG = "GetWeather";
	private JSONObject jsonObject;
	private JSONArray jsonArray;
	private Weather[] weather;
	private String cityName;
	private Context context;
	
	
	public GetWeather(Context context){
		this.context = context;
	}
	
	public  Weather[] getWeather() throws Exception{
		cityName = getLocation();
		
		weather = new Weather[4];
		HttpClient client = CustomerHttpClient.getHttpClient();
		HttpGet httpGet = 
				new HttpGet("http://api.map.baidu.com/telematics/v3/weather?location="+cityName+"" +
						"&output=json&ak=gHaKUba6IsofT4WO6qgU6l3V");
		
		StringBuilder builder = new StringBuilder();
		
		try {
			HttpResponse response = client.execute(httpGet);
			BufferedReader reader = 
					new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			 for (String s = reader.readLine(); s != null; s = reader.readLine()) {
		            builder.append(s);
			 }
			 jsonObject = new JSONObject(builder.toString());
			 jsonArray = jsonObject.getJSONArray("results").getJSONObject(0).getJSONArray("weather_data");
			 
		} catch (JSONException e) {
			// TODO: handle exception
			Log.e(TAG, "��ȡjsonʧ��"); 
		}
		catch (IOException e1) {
			// TODO: handle exception
			Log.e(TAG, "��ȡjsonʧ��");
		}
		analyDateJson();
		writeWeather();
		return weather;
	}
	
	private void writeWeather(){
		SharedPreferences sharedPreferences = context.getSharedPreferences(PrefConst.NAME, Context.MODE_PRIVATE);
		Time time = new Time();
		time.setToNow();
		Editor editor = sharedPreferences.edit();
		editor.putString(PrefConst.W_UPDATE_DATE , ""+time.year+time.month+time.monthDay);
		editor.putString(PrefConst.W_ADDR, cityName);
		for(int i=0 ;i < weather.length ;i++){
			editor.putString(PrefConst.W_ICON+"-"+i, weather[i].getIcon());
			editor.putString(PrefConst.W_TEMHIGHT+"-"+i, weather[i].getTemHight());
			editor.putString(PrefConst.W_TEMLOW+"-"+i, weather[i].getTemLow());
			editor.putString(PrefConst.W_TEMNOW+"-"+i, weather[i].getTemNow());
			editor.putString(PrefConst.W_WEATHER+"-"+i, weather[i].getWeather());
			editor.putString(PrefConst.W_WIND+"-"+i, weather[i].getWind());
			
		}
		editor.commit();
		//Log.i("HomeFragment" , sharedPreferences.getString(PrefConst.W_WEATHER+"-"+0, "0000"));
	}
	
	private String getLocation() throws Exception{
		String city=null;
		HttpClient client = CustomerHttpClient.getHttpClient();
		HttpGet httpGet = new HttpGet("http://api.map.baidu.com/location/ip?ak=gHaKUba6IsofT4WO6qgU6l3V");
		StringBuilder builder = new StringBuilder();
		try {
			HttpResponse response = client.execute(httpGet);
			BufferedReader reader = 
					new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			 for (String s = reader.readLine(); s != null; s = reader.readLine()) {
		            builder.append(s);
			 }
			 //Log.i(TAG , reader.toString());
			 JSONObject json = new JSONObject(builder.toString()).getJSONObject("content").getJSONObject("address_detail");
			 city = analyticJson(json , "city").toString();
			 
			 //Log.i(TAG , city.substring(0, city.length()-1) );
		} catch (Exception e) {
			// TODO: handle exception
			city = "����";
			Log.e(TAG, "Locationʧ��"); 
			return city;
		}
		return city.substring(0, city.length()-1);
	}
	
	private String analyticJson(JSONObject json , String tag){
		String realTem = "";
		try{
			realTem=json.getString(tag);  
		}catch(JSONException exception){
			Log.i(TAG , "����ʧ��");
		}
		
		return realTem;
	}

	private void analyDateJson(){
		JSONObject info;
		for(int i=0 ; i<4 ; i++){
			
			try {
				info = jsonArray.getJSONObject(i);
//				Log.i(TAG , info.getString("date") );
				weather[i] = new Weather();
				weather[i].setDate(info.getString("date").split("\\(")[0]);
				if (i == 0) {
					String temNow = info.getString("date").split("\\��")[1];
					weather[i].setTemNow(temNow.substring(0,
							temNow.length() - 1));
				}
				weather[i].setAddr(cityName);
				weather[i].setTemHight(info.getString("temperature"));
				weather[i].setWeather(info.getString("weather"));
				weather[i].setWind(info.getString("wind"));
				weather[i].setIcon(getIcon(weather[i].getWeather()) );
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	private String getIcon(String weather){
		
		String id="";
		if( weather.equals("��") ){
			id = "w_00_d";
		}else if( weather.equals("����") ){
			id = "w_01_d";
		}
		else if( weather.equals("��") ){
			id = "w_02_d";
		}
		else if( weather.equals("����") ){
			id = "w_03_d";
		}
		else if( weather.equals("������") ){
			id = "w_04_d";
		}
		else if( weather.equals("��������б���") ){
			id = "w_05_d";
		}
		else if( weather.equals("���ѩ") ){
			id = "w_06_d";
		}
		else if( weather.equals("С��") ){
			id = "w_07_d";
		}
		else if( weather.equals("����") ){
			id = "w_08_d";
		}
		else if( weather.equals("����") ){
			id = "w_09_d";
		}
		else if( weather.equals("����") ){
			id = "w_10_d";
		}
		else if( weather.equals("����") ){
			id = "w_11_d";
		}
		else if( weather.equals("�ش���") ){
			id = "w_12_d";
		}
		else if( weather.equals("��ѩ") ){
			id = "w_13_d";
		}
		else if( weather.equals("Сѩ") ){
			id = "w_14_d";
		}
		else if( weather.equals("��ѩ") ){
			id = "w_15_d";
		}
		else if( weather.equals("��ѩ") ){
			id = "w_16_d";
		}
		else if( weather.equals("��ѩ") ){
			id = "w_17_d";
		}
		else if( weather.equals("��") ){
			id = "w_18_d";
		}
		else if( weather.equals("����") ){
			id = "w_19_d";
		}
		else if( weather.equals("ɳ����") ){
			id = "w_20_d";
		}
		else if( weather.equals("С��ת����") ){
			id = "w_21_d";
		}
		else if( weather.equals("����ת����") ){
			id = "w_22_d";
		}
		else if( weather.equals("����ת����") ){
			id = "w_23_d";
		}
		else if( weather.equals("����ת����") ){
			id = "w_24_d";
		}
		else if( weather.equals("����ת�ش���") ){
			id = "w_25_d";
		}
		else if( weather.equals("Сѩת��ѩ") ){
			id = "w_26_d";
		}
		else if( weather.equals("��ѩת��ѩ") ){
			id = "w_27_d";
		}
		else if( weather.equals("��ѩת��ѩ") ){
			id = "w_28_d";
		}
		else if( weather.equals("����") ){
			id = "w_29_d";
		}
		else if( weather.equals("��ɳ") ){
			id = "w_30_d";
		}
		else if( weather.equals("ǿɳ����") ){
			id = "w_31_d";
		}
		else if( weather.equals("��") ){
			id = "w_53_d";
		}
		else id = "w_no_d";
		
		return id;
	}
	
}
