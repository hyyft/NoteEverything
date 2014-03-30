package com.hyyft.noteeverything.plan;

import com.hyyft.noteeverything.R;
import com.hyyft.noteeverything.myconst.PrefConst;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;



public class AddPlanLittleTab extends ListFragment {

	private View view;
	private ListView listView;
	private String[] littleTag;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.id.add_littletag_fragment, container);
		listView = (ListView) view.findViewById(R.id.add_litletag_list);
		showLittleTag("1");
		return view;
	}
	
	/**
	 * 获取sharedPreference的数据
	 * @param bigTag
	 */
	private void getLittleTag(String bigTag){
		int i = 1;
		SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PrefConst.NAME,Context.MODE_PRIVATE);
		while( true ){
			littleTag[i-1] = sharedPreferences.getString(bigTag+"-"+i, "none");
			if( littleTag[i-1].equals("none") )break;
			i++;
		}
	}
	/**
	 * 根据bigTag显示littleTag
	 * @param bigTag  bigTag
	 */
	public void showLittleTag(String bigTag){
		getLittleTag(bigTag);
		listView.setAdapter(new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_list_item_1, littleTag));
	}

	
}
