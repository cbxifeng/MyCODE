package com.duihao.promotion.ui.android;

import android.app.Activity;
import android.os.Bundle;

import com.duihao.promotion.main.android.R;
import com.duihao.promotion.service.android.MyApplication;

public class Send_Mail extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send_mail);
		// �˳��ǵ��ô˷��� ֱ���˳�Ӧ��
		MyApplication.getInstance().addActivity(this);
	}

}
