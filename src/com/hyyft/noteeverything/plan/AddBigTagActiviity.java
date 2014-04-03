package com.hyyft.noteeverything.plan;

import com.hyyft.noteeverything.R;
import com.hyyft.noteeverything.myconst.PrefConst;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View.OnClickListener;

/**
 * 加添bigtag的activity
 * @author Administrator
 *
 */
public class AddBigTagActiviity extends Activity {

	
	private Button saveBtn , noBtn;
	private EditText editText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addplan_addbigtag_activity);
		saveBtn = (Button)findViewById(R.id.add_addtag_save_btn);
		noBtn = (Button)findViewById(R.id.add_addtag_no_btn);
		editText = (EditText)findViewById(R.id.add_addtag_ed);
		
		noBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AddBigTagActiviity.this.finish();
			}
		});
		
		saveBtn.setOnClickListener(listener);
		
	}
	
	private OnClickListener listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String bigTag = editText.getText().toString();
			if(bigTag.equals("")){
				Toast.makeText(AddBigTagActiviity.this, "不能为空", Toast.LENGTH_LONG).show();
				return;
			}
			addBigTag(bigTag);
			Intent data = new Intent();
			data.putExtra("bigtag", bigTag);
			AddBigTagActiviity.this.setResult(1, data);	
			AddBigTagActiviity.this.finish();
			}
	};
	
	private void addBigTag(String bigTag){
		SharedPreferences sharedPreferences = this.getSharedPreferences(PrefConst.NAME, MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		int count = sharedPreferences.getInt(PrefConst.BIGTAG_COUNT, 0);
		
		editor.putString(""+(count+1), bigTag);
		editor.remove(PrefConst.BIGTAG_COUNT);
		editor.putInt(PrefConst.BIGTAG_COUNT, count+1);
		editor.commit();
		
	}
	
	

	
}
