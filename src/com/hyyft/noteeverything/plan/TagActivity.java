package com.hyyft.noteeverything.plan;

import com.hyyft.noteeverything.R;
import com.hyyft.noteeverything.plan.AddPlanBigTag.PlanBigTagCallBack;
import com.hyyft.noteeverything.plan.AddPlanLittleTag.AddPlanLittleTagCallBack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

public class TagActivity extends FragmentActivity{

	private FragmentManager fManager ;
	private AddPlanBigTag bigTagFragment ;
	private AddPlanLittleTag littleTagFragment;
	private FragmentTransaction fTransaction;
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addplan_tagactivity_layout);
		
		// TODO Auto-generated method stub
		fManager = getSupportFragmentManager();
		
		littleTagFragment = new AddPlanLittleTag();
		bigTagFragment = new AddPlanBigTag();
		
		bigTagFragment.setPlanBigTagCallBack(new PlanBigTagCallBack() {
		
			@Override
			public void getBigTag(String bigTag , String bigTagID) {
				// TODO Auto-generated method stub
				TagActivity.this.littleTagFragment.showLittleTag(bigTag , bigTagID);
			}
		});
		littleTagFragment.setCallBack(new AddPlanLittleTagCallBack() {
			
			@Override
			public void returnTag(String bigTag, String littleTag) {
				// TODO Auto-generated method stub
				Intent data = new Intent();
				data.putExtra("bigtag", bigTag);
				data.putExtra("littletag", littleTag);
				TagActivity.this.setResult(1, data);
				TagActivity.this.finish();
			}
		});
		
		fTransaction = fManager.beginTransaction();	
		fTransaction.add(R.id.add_bigtag_fragment, bigTagFragment, "bigTagFragment");
		fTransaction.add(R.id.add_littletag_fragment, littleTagFragment, "littleTagFragment");
		
		fTransaction.commit();
		
	
	}
	
}
