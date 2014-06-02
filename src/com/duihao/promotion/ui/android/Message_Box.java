package com.duihao.promotion.ui.android;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.TextView;

import com.duihao.promotion.main.android.R;
import com.duihao.promotion.service.android.MyApplication;

public class Message_Box extends TabActivity implements OnTabChangeListener {

	Button back, invite;
	private TabHost tabHost;
	private TabWidget mTabWidget;
	private String[] titles = { "收件箱", "发件箱" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_box);
		// 退出是调用此方法 直接退出应用
		MyApplication.getInstance().addActivity(this);
		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabWidget = (TabWidget) findViewById(android.R.id.tabs);
		tabHost.setOnTabChangedListener(this);
		LinearLayout view1 = (LinearLayout) getLayoutInflater().inflate(
				R.layout.tabwighet, null);
		TextView view = ((TextView) view1.findViewById(R.id.tabtitle));
		for (int i = 0; i < titles.length; i++) {

			// view.setText(titles[i]);
			Log.i("qqqqqqqqqqqqqqqqq", "" + titles.length);
			tabHost.addTab(tabHost
					.newTabSpec(titles[i])
					.setIndicator(titles[i])
					.setContent(
							new Intent().setClass(this, Send_Mail.class)
									.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));

		}

		tabHost.setCurrentTab(0);
		onTabChanged(titles[0]);
		back = (Button) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Message_Box.this.finish();
			}
		});

	}

	@Override
	public void onTabChanged(String tabId) {
		// TODO Auto-generated method stub

	};

}
