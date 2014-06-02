package com.duihao.promotion.gridview.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.duihao.promotion.http.android.Member_info;
import com.duihao.promotion.http.android.Web_View;
import com.duihao.promotion.main.android.R;

public class Set_info extends Activity {
	Button m_back = null;
	TextView m_member = null;
	TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_info);
		tv = (TextView) findViewById(R.id.m_flag);
		m_member = (TextView) findViewById(R.id.m_member);
		isLogin();
		m_back = (Button) findViewById(R.id.m_back);
		m_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(Set_info.this, Web_View.class);
				startActivity(intent);

			}
		});

		m_member.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isLogin()) {
					AlertDialog dig = new AlertDialog.Builder(Set_info.this)
							.setTitle("注销登录")
							.setPositiveButton("取消",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub

										}

									})
							.setNegativeButton("確定",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {

											SharedPreferences sp = getSharedPreferences(
													"microblog_user", MODE_PRIVATE);
											sp.edit().clear().commit();
											isLogin();

										}
									}).show();

				} else {
					Intent intent = new Intent();
					intent.setClass(Set_info.this, Member_info.class);
					startActivity(intent);
				}
			}
		});
	}

	@Override
	protected void onPause() {
		Set_info.this.finish();
		super.onPause();
	}

	//
	protected boolean isLogin() {
		SharedPreferences sp = getSharedPreferences("microblog_user", MODE_PRIVATE);
		String userName = sp.getString("userName", "");
		if (userName.equals("")) {
			tv.setText("未登录");
			m_member.setText("登录账号");
			return false;
		} else {
			// 跳转到评论界面

			tv.setText("已登录");
			m_member.setText("注销登录");
			return true;
		}
	}

}
