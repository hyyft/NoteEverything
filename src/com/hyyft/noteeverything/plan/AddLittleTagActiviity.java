package com.hyyft.noteeverything.plan;

import com.hyyft.noteeverything.R;
import com.hyyft.noteeverything.R.string;
import com.hyyft.noteeverything.myconst.PrefConst;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

/**
 * 加添littletag的activity
 * @author Administrator
 *
 */
public class AddLittleTagActiviity extends Activity {

	
	private Button saveBtn , noBtn;
	private EditText editText;
	private TextView textView;
	private String bigTag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addplan_addbigtag_activity);
		saveBtn = (Button)findViewById(R.id.add_addtag_save_btn);
		noBtn = (Button)findViewById(R.id.add_addtag_no_btn);
		editText = (EditText)findViewById(R.id.add_addtag_ed);
		textView = (TextView)findViewById(R.id.add_addtag_title);
		textView.setText(getResources().getString(R.string.text_add_addlittletag_textview));
		
		noBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AddLittleTagActiviity.this.finish();
			}
		});
		
		saveBtn.setOnClickListener(listener);
		
	}
	
	private OnClickListener listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String littleTag = editText.getText().toString();
			if(littleTag.equals("")){
				Toast.makeText(AddLittleTagActiviity.this, "不能为空", Toast.LENGTH_LONG).show();
				return;
			}
			addLittleTag(littleTag);
			Intent data = new Intent();
			data.putExtra("littletag", littleTag);
			AddLittleTagActiviity.this.setResult(1, data);	
			AddLittleTagActiviity.this.finish();
			}
	};
	
	private void addLittleTag(String littleTag){
		
		Intent intent = getIntent();
		bigTag = intent.getExtras().getString("bigtag");
		SharedPreferences sharedPreferences = this.getSharedPreferences(PrefConst.NAME, MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		
		int i = 1;
		while(true){
			String temp = 
			sharedPreferences.getString(bigTag+"-"+i, "none");
			if( temp.equals("none") )
				break;
			else ++i;
		}
		
		editor.putString(""+bigTag+"-"+i, littleTag);
		editor.commit();
		
	}
	
	

	
}
