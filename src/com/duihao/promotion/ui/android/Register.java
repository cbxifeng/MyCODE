package com.duihao.promotion.ui.android;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.duihao.promotion.javavbean.android.AppConstant;
import com.duihao.promotion.main.android.R;
import com.duihao.promotion.service.android.MyApplication;

public class Register extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		// 退出是调用此方法 直接退出应用
		MyApplication.getInstance().addActivity(this);
		WebView m_register = (WebView) findViewById(R.id.m_register);
		m_register.loadUrl(AppConstant.M_REGISTER);

	}

}
