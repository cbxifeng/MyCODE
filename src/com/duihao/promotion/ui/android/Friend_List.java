package com.duihao.promotion.ui.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.duihao.promotion.main.android.R;
import com.duihao.promotion.service.android.MyApplication;

public class Friend_List extends Activity {

	Button back, add;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_list);
		// 退出是调用此方法 直接退出应用
		MyApplication.getInstance().addActivity(this);
		back = (Button) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Friend_List.this.finish();

			}
		});
		add = (Button) findViewById(R.id.add);
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent(Friend_List.this, Find_Friend.class);
				startActivity(it);
			}
		});

	}

}
