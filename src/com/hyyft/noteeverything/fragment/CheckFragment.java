package com.hyyft.noteeverything.fragment;

import java.util.ArrayList;
import java.util.List;

import com.hyyft.noteeverything.R;
import com.hyyft.noteeverything.check.DrawPie;
import com.hyyft.noteeverything.myconst.PrefConst;
import com.hyyft.noteeverything.plan.CreateTimeDialog;
import com.hyyft.noteeverything.plan.CreateTimeDialog.CreateTimeDialogCallBack;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class CheckFragment extends Fragment implements OnItemSelectedListener , 
														CreateTimeDialogCallBack{

	
	private static final int PLANTEXTVIEW = 1;
	private static final int DOTEXTVIEW = 2;
	private  int whichSelected = PLANTEXTVIEW;
	
	private static final int PLANDOTEXTVIEW = 1;
	private static final int ACTUALLDOTEXTVIEW = 2;
	private static final String TAG = "CheckFragment";
	private  int whichSelected2 = PLANDOTEXTVIEW;
	
	private int drawWhich = DrawPie.DRAW_PLANTODO;
	
	private TextView dateTextView , planTextView , doTextView;
	private TextView planDoTextView , actullyDoTextView;
	private Spinner bigTagSpinner;
	private LinearLayout  drawLayout , btnLayout;
	private List<String> bigTagList;
	private View subvView;
	private ArrayAdapter<String> adapter;
	private Drawable selectedBg ,notSelectedBg;
	
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Resources resources = getResources();
		selectedBg = resources.getDrawable(R.drawable.check_btn_selected);
		notSelectedBg = resources.getDrawable(R.drawable.check_btn_notselected);
	}



	//private Layout
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.checkfragment_layout, container, false);
		
		subvView = LayoutInflater.from(getActivity()).inflate(R.layout.check_plan_and_actually, null);
		findView(view);
		
		initView();
		return view;
	}
	
	
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//bigTagSpinner.setSelection(0);
		
		
	}



	private void findView(View view){
		dateTextView = (TextView)view.findViewById(R.id.check_tv_date);
		planTextView = (TextView)view.findViewById(R.id.check_tv_plan);
		doTextView = (TextView)view.findViewById(R.id.check_tv_do);
		bigTagSpinner = (Spinner)view.findViewById(R.id.check_spinner_bigtag);
		drawLayout = (LinearLayout)view.findViewById(R.id.check_layout_draw);
		btnLayout = (LinearLayout)view.findViewById(R.id.check_layout_btn);
		planDoTextView = (TextView)subvView.findViewById(R.id.check_tv_plantodo);
		actullyDoTextView = (TextView)subvView.findViewById(R.id.check_tv_actuallytodo);
		
	}
	@SuppressLint("NewApi")
	private void initView(){
		
		Time time = new Time();
		time.setToNow();
		dateTextView.setText(""+time.year+"-"+(time.month+1)+"-"+time.monthDay);
		planTextView.setBackground(selectedBg);
		doTextView.setBackground(notSelectedBg);
		
		btnLayout.addView(subvView);
		planDoTextView.setBackground(selectedBg);
		actullyDoTextView.setBackground(notSelectedBg);
		
	
		drawLayout.removeAllViews();
		//Log.i(TAG, "执行initView");
		drawLayout.addView(new DrawPie(getActivity(), bigTagList)
				.drawPlan(dateTextView.getText().toString(), "全部" , DrawPie.DRAW_PLANTODO) );
		
		adapter = getBigTag();
		bigTagSpinner.setAdapter(adapter);
		bigTagSpinner.setSelection(0);
		drawWhich = PLANDOTEXTVIEW;
		whichSelected = PLANTEXTVIEW;
		whichSelected2 = PLANDOTEXTVIEW;
		
		bigTagSpinner.setOnItemSelectedListener(this);
		dateTextView.setOnClickListener(textViewListener);
		planTextView.setOnClickListener(textViewListener);
		doTextView.setOnClickListener(textViewListener);
		planDoTextView.setOnClickListener(textViewListener);
		actullyDoTextView.setOnClickListener(textViewListener);
		
	}
	
	/**
	 * 
	 * @return 返回一个给Spinner使用的adapter
	 */
	private ArrayAdapter<String> getBigTag(){
		bigTagList = new ArrayList<String>();
		SharedPreferences sharedPreferences = 
				getActivity().getSharedPreferences(PrefConst.NAME,Context.MODE_PRIVATE);
		//int count = sharedPreferences.getInt(PrefConst.BIGTAG_COUNT, 0);
		String temp;
		int i = 1;
		bigTagList.add("全部");
		while( true ){
			temp = sharedPreferences.getString(""+i, "none");
			//Log.i("yuan" , temp);
			if( temp.equals("none") )break;
			else bigTagList.add(new String(temp));
			i++;
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),  
                android.R.layout.simple_spinner_item, bigTagList); 
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
		return adapter;
	}
	
	
	/**
	 * 用于监听所有textView click的Listener
	 */
	private OnClickListener textViewListener = new OnClickListener() {
		
		@SuppressLint("NewApi")
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.check_tv_date:
				new CreateTimeDialog(getActivity(), CheckFragment.this).getDate();
				break;
			case R.id.check_tv_plan:
				if( whichSelected == DOTEXTVIEW ){
					whichSelected = PLANTEXTVIEW;
					planTextView.setBackground(selectedBg);
					doTextView.setBackground(notSelectedBg);
					
					Resources resources = getResources();
					selectedBg = resources.getDrawable(R.drawable.check_btn_selected);
					notSelectedBg = resources.getDrawable(R.drawable.check_btn_notselected);
					
					btnLayout.addView(subvView );
					planDoTextView.setBackground(selectedBg);
					actullyDoTextView.setBackground(notSelectedBg);
					
					drawLayout.removeAllViews();
					drawLayout.addView(new DrawPie(getActivity(), bigTagList)
							.drawPlan(dateTextView.getText().toString(), "全部" ,DrawPie.DRAW_PLANTODO) );
					drawWhich = DrawPie.DRAW_PLANTODO;
					bigTagSpinner.setSelection(0);
				}
				
				
				break;
			case R.id.check_tv_do:
				if( whichSelected == PLANTEXTVIEW ){
					whichSelected = DOTEXTVIEW;
					planTextView.setBackground(notSelectedBg);
					doTextView.setBackground(selectedBg);
					btnLayout.removeView(subvView);
					drawLayout.removeAllViews();
					drawLayout.addView(new DrawPie(getActivity(), bigTagList)
					.drawDowhat(dateTextView.getText().toString(), "全部" ) );
					drawWhich = DrawPie.DRAW_DO;
					bigTagSpinner.setSelection(0);
				}
				break;
				
			case R.id.check_tv_plantodo:
				//Toast.makeText(getActivity(), "hahah",Toast.LENGTH_LONG).show();
				if( whichSelected2 == ACTUALLDOTEXTVIEW ){
					whichSelected2 = PLANDOTEXTVIEW;
					
					planDoTextView.setBackground(selectedBg);
					actullyDoTextView.setBackground(notSelectedBg);
					
					drawLayout.removeAllViews();
					drawLayout.addView(new DrawPie(getActivity(), bigTagList)
							.drawPlan(dateTextView.getText().toString(), "全部" ,DrawPie.DRAW_PLANTODO) );
					drawWhich = DrawPie.DRAW_PLANTODO;
					bigTagSpinner.setSelection(0);
				}
				break;
			case R.id.check_tv_actuallytodo:
				//Toast.makeText(getActivity(), "hahah",Toast.LENGTH_LONG).show();
				if( whichSelected2 == PLANDOTEXTVIEW ){
					whichSelected2 = ACTUALLDOTEXTVIEW;
					
					planDoTextView.setBackground(notSelectedBg);
					actullyDoTextView.setBackground(selectedBg);
					
					drawLayout.removeAllViews();
					drawLayout.addView(new DrawPie(getActivity(), bigTagList)
							.drawPlan(dateTextView.getText().toString(), "全部" ,DrawPie.DRAW_ACTULLY) );
					drawWhich = DrawPie.DRAW_ACTULLY;
					bigTagSpinner.setSelection(0);
				}
				break;

			default:
				break;
			}
		}
	};
	
	
	
	

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		//Log.i("hyyft" , "onItemSelected");
		drawLayout.removeAllViews();
		if( drawWhich == DrawPie.DRAW_ACTULLY ||  drawWhich == DrawPie.DRAW_PLANTODO){
		drawLayout.addView(new DrawPie(getActivity(), bigTagList)
				.drawPlan(dateTextView.getText().toString(),
						adapter.getItem(position) , drawWhich) 
						);
		}
		else {
			drawLayout.addView(new DrawPie(getActivity(), bigTagList)
			.drawDowhat(dateTextView.getText().toString(),
					adapter.getItem(position)  )
					);
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
	

	@SuppressLint("NewApi")
	@Override
	public void dateDialogCallBack(String date) {
		// TODO Auto-generated method stub
		dateTextView.setText(date);
		whichSelected = PLANTEXTVIEW;
		planTextView.setBackground(selectedBg);
		doTextView.setBackground(notSelectedBg);
		
		btnLayout.addView(subvView );
		
		drawLayout.removeAllViews();
		drawLayout.addView(new DrawPie(getActivity(), bigTagList)
				.drawPlan(dateTextView.getText().toString(), "全部" ,DrawPie.DRAW_PLANTODO) );
		drawWhich = DrawPie.DRAW_PLANTODO;
		bigTagSpinner.setSelection(0);
		
	}

	@Override
	public void timeDialogCallBack(String time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void timeDialogCallBack(int hour, int minute) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ptimeDialogCallBack(int hour, int minute) {
		// TODO Auto-generated method stub
		
	}
	
	

	
}
