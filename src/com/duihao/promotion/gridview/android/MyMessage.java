package com.duihao.promotion.gridview.android;

import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.duihao.promotion.main.android.R;
public class MyMessage extends ActivityGroup {

	private TabHost myTabhost;
	private TabWidget mTabWidget;
	LocalActivityManager mlam;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.mymessage);

		myTabhost = (TabHost) findViewById(android.R.id.tabhost);
		// 注意部分，不写会出异常的
		mlam = new LocalActivityManager(this, false);

		mlam.dispatchCreate(savedInstanceState);

		myTabhost.setup(mlam);

		// myTabhost.setOnTabChangedListener(this);

		myTabhost.setCurrentTab(0);

		myTabhost.addTab(myTabhost.newTabSpec("Tab_1")
				.setContent(new Intent(this, MyMessageGet.class))
				.setIndicator("收到消息"));

		myTabhost
				.addTab(myTabhost
						.newTabSpec("Tab_2")
						.setContent(new Intent(this, MyMessageCenter.class))
						.setIndicator(
								"已发消息",
								this.getResources().getDrawable(
										R.drawable.ic_launcher)));

		myTabhost
				.addTab(myTabhost
						.newTabSpec("Tab_3")
						.setContent(new Intent(this, SendMessage.class))
						.setIndicator(
								"发送消息",
								this.getResources().getDrawable(
										R.drawable.ic_launcher)));

		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mlam.dispatchResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mlam.dispatchPause(isFinishing());
	}

}
