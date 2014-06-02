package com.duihao.promotion.gridview.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.duihao.promotion.main.android.R;

public class LookMyMessae extends Activity {
TextView lookname;
TextView lookcontent;
Button lookbutton;
private String name;
private String content;
private int B;
TextView nameoher;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.lookmessage);
		Intent it=this.getIntent();
		
		name =it.getStringExtra("name");
		  
		content=it.getStringExtra("content");
		
		B=it.getIntExtra("B",0);
		Log.i("回复的名字B", " "+B);
		lookname=(TextView)findViewById(R.id.lookname);
		
		lookname.setText(name);
		
		lookcontent=(TextView)findViewById(R.id.lookcontent);
		
		lookcontent.setText(content);
		  
		nameoher=(TextView)findViewById(R.id.nameoher);
		
		lookbutton=(Button)findViewById(R.id.lookreback);
        if(B==2){
			
			lookbutton.setText("回复");
			nameoher.setText("发送人 :");
			
		}else{
			lookbutton.setText("关闭");
		}
		
		lookbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(B==2){
					Intent it = new Intent();
					Log.i("回复的名字", name);
					it.setClass(LookMyMessae.this,
							SendMessage.class);
					it.putExtra("f", 1);
					it.putExtra("name",
							name);
					startActivity(it);
				}else{
					LookMyMessae.this.finish();
				}
				
			}
		});
	}

}
