package com.duihao.promotion.ui.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.duihao.promotion.http.android.Member_info;
import com.duihao.promotion.main.android.R;
import com.duihao.promotion.service.android.MyApplication;

public class Person_Center extends AppActivity {

	Button friend_list, collect, message, change_pwd, log_off;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		// 退出是调用此方法 直接退出应用
		MyApplication.getInstance().addActivity(this);
		// View m_view = View.inflate(Person_Center.this, R.layout.pic_main,
		// null);
		Button top_title = (Button) findViewById(R.id.topic_title);
		top_title.setText("个人中心");

		View v = View.inflate(Person_Center.this, R.layout.person_center, null);

		// 根据此Activity情况设置顶部标题栏

		addContext(v);

		// 我的收藏
		collect = (Button) findViewById(R.id.collect);
		collect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent(Person_Center.this, CollectList.class);
				startActivity(it);
			}
		});

		// 消息盒子
		message = (Button) findViewById(R.id.message);
		message.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent(Person_Center.this, Message_Box.class);
				startActivity(it);

			}
		});

		// 修改密码
		change_pwd = (Button) findViewById(R.id.change_pwd);
		change_pwd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent(Person_Center.this, Change_Pwd.class);
				startActivity(it);

			}
		});

		// 注销登录
		log_off = (Button) findViewById(R.id.Log_off);
		log_off.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 删除会员登录信息 然后跳转到Member类
				Intent it = new Intent(Person_Center.this, Member_info.class);
				startActivity(it);
			}
		});

	}

	@Override
	protected boolean isShowToolBar() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean isShowImage() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean isShowPicTui() {
		// TODO Auto-generated method stub
		return true;
	}

}
