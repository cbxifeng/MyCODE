package com.duihao.promotion.ui.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.duihao.promotion.main.android.R;
import com.duihao.promotion.service.android.MyApplication;

public class Find_Friend extends Activity {

	Button back, ensure;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.find_friend);
		// �˳��ǵ��ô˷��� ֱ���˳�Ӧ��
		MyApplication.getInstance().addActivity(this);
		back = (Button) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Find_Friend.this.finish();

			}
		});

	};

}
