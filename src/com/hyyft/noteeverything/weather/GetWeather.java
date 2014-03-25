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

import android.util.Log;

public class GetWeather {

	private static final String TAG = "GetWeather";
	private JSONObject jsonObject;
	private JSONArray jsonArray;
	private Weather[] weather;
	private String cityName;
	
	
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
			Log.e(TAG, "ªÒ»°json ß∞‹"); 
		}
		catch (IOException e1) {
			// TODO: handle exception
			Log.e(TAG, "ªÒ»°json ß∞‹");
		}
		analyDateJson();

		return weather;
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
//			 Log.i(TAG , "ªÒ»°Œª÷√≥…π¶");
			 JSONObject json = new JSONObject(builder.toString()).getJSONObject("content").getJSONObject("address_detail");
			 city = analyticJson(json , "city").toString();
			 
			 Log.i(TAG , city.substring(0, city.length()-1) );
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "Location ß∞‹"); 
			return null;
		}
		return city.substring(0, city.length()-1);
	}
	
	private String analyticJson(JSONObject json , String tag){
		String realTem = "";
		try{
			realTem=json.getString(tag);  
		}catch(JSONException exception){
			Log.i(TAG , "Ω‚Œˆ ß∞‹");
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
					String temNow = info.getString("date").split("\\£∫")[1];
					weather[i].setTemNow(temNow.substring(0,
							temNow.length() - 1));
				}
				weather[i].setAddr(cityName);
				weather[i].setTemHight(info.getString("temperature"));
				weather[i].setWeather(info.getString("weather"));
				weather[i].setWind(info.getString("wind"));
				weather[i].setIcon(getIcon(weather[i].getWeather()) );
				Log.i("HomeFragment" , weather[i].getWeather());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private String getIcon(String weather){
		
		String id="";
		if( weather.equals("«Á") ){
			id = "w_00_d";
		}else if( weather.equals("∂‡‘∆") ){
			id = "w_01_d";
		}
		else if( weather.equals("“ı") ){
			id = "w_02_d";
		}
		else if( weather.equals("’Û”Í") ){
			id = "w_03_d";
		}
		else if( weather.equals("¿◊’Û”Í") ){
			id = "w_04_d";
		}
		else if( weather.equals("¿◊’Û”Í∞È”–±˘±¢") ){
			id = "w_05_d";
		}
		else if( weather.equals("”Íº–—©") ){
			id = "w_06_d";
		}
		else if( weather.equals("–°”Í") ){
			id = "w_07_d";
		}
		else if( weather.equals("÷–”Í") ){
			id = "w_08_d";
		}
		else if( weather.equals("¥Û”Í") ){
			id = "w_09_d";
		}
		else if( weather.equals("±©”Í") ){
			id = "w_10_d";
		}
		else if( weather.equals("¥Û±©”Í") ){
			id = "w_11_d";
		}
		else if( weather.equals("Ãÿ¥Û±©”Í") ){
			id = "w_12_d";
		}
		else if( weather.equals("’Û—©") ){
			id = "w_13_d";
		}
		else if( weather.equals("–°—©") ){
			id = "w_14_d";
		}
		else if( weather.equals("÷–—©") ){
			id = "w_15_d";
		}
		else if( weather.equals("¥Û—©") ){
			id = "w_16_d";
		}
		else if( weather.equals("±©—©") ){
			id = "w_17_d";
		}
		else if( weather.equals("ŒÌ") ){
			id = "w_18_d";
		}
		else if( weather.equals("∂≥”Í") ){
			id = "w_19_d";
		}
		else if( weather.equals("…≥≥æ±©") ){
			id = "w_20_d";
		}
		else if( weather.equals("–°”Í◊™÷–”Í") ){
			id = "w_21_d";
		}
		else if( weather.equals("÷–”Í◊™¥Û”Í") ){
			id = "w_22_d";
		}
		else if( weather.equals("¥Û”Í◊™±©”Í") ){
			id = "w_23_d";
		}
		else if( weather.equals("±©”Í◊™¥Û±©”Í") ){
			id = "w_24_d";
		}
		else if( weather.equals("¥Û±©”Í◊™Ãÿ¥Û±©”Í") ){
			id = "w_25_d";
		}
		else if( weather.equals("–°—©◊™÷–—©") ){
			id = "w_26_d";
		}
		else if( weather.equals("÷–—©◊™¥Û—©") ){
			id = "w_27_d";
		}
		else if( weather.equals("¥Û—©◊™±©—©") ){
			id = "w_28_d";
		}
		else if( weather.equals("∏°≥æ") ){
			id = "w_29_d";
		}
		else if( weather.equals("—Ô…≥") ){
			id = "w_30_d";
		}
		else if( weather.equals("«ø…≥≥æ±©") ){
			id = "w_31_d";
		}
		else if( weather.equals("ˆ≤") ){
			id = "w_53_d";
		}
		else id = "w_no_d";
		
		return id;
	}
	
}
