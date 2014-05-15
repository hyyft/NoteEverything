package com.hyyft.noteeverything.weather;


import android.content.Context;
import android.os.AsyncTask;

/**
 * ���ڻ�ȡ������asyncTast����
 * @author Administrator
 *
 */
public class WeatherAsyncTast extends AsyncTask<String, String, Weather[]> {

	private GetWeather getWeather;
	private Weather[] weathers;
	private GetWeatherTast getWeatherTast;
	private Context context;
	
	public WeatherAsyncTast(GetWeatherTast getWeatherTast , Context context){
		this.getWeatherTast = getWeatherTast;
		this.context = context;
	}
	@Override
	protected Weather[] doInBackground(String... params) {
		// TODO Auto-generated method stub
		getWeather = new GetWeather(context);
		weathers = new Weather[4];
		try {
			weathers = getWeather.getWeather();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
		return weathers;
	}

	@Override
	protected void onPostExecute(Weather[] result) {
		// TODO Auto-generated method stub
		getWeatherTast.updateWeather(result);
	}
	
	
	/**
	 * WeatherAsyncTast�Ļص��ӿ�
	 * @author Administrator
	 *
	 */
	public interface GetWeatherTast{
		/**
		 * ��WeatherAsyncTast.onPostExecute�лص�,���������ؼ�UI
		 * @param weathers
		 */
		public void updateWeather(Weather[] weathers);
	}
}
