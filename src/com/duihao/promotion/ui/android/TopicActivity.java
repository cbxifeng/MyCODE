package com.duihao.promotion.ui.android;

import android.R.color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;

import com.duihao.promotion.main.android.R;
import com.duihao.promotion.service.android.MyApplication;

public class TopicActivity extends AppActivity {

	MyListView listview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 退出是调用此方法 直接退出应用
		MyApplication.getInstance().addActivity(this);
		MyListView mylistview = new MyListView(this);
		mylistview.setId(123);
		mylistview.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));// 大小设置
		mylistview.setBackgroundColor(color.white);
		Drawable draw = getResources().getDrawable(R.drawable.itemline);
		mylistview.setDivider(draw);
		mylistview.setDividerHeight(2);
		addContext(mylistview);

		listview = (MyListView) findViewById(123);

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
