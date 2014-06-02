package com.duihao.promotion.ui.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.duihao.promotion.main.android.R;
import com.duihao.promotion.service.android.MyApplication;

public class Change_Pwd extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.change_password);
		// 退出是调用此方法 直接退出应用
		MyApplication.getInstance().addActivity(this);
	}

}
