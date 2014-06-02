package com.duihao.promotion.main.android;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.duihao.promotion.service.android.MyApplication;
import com.duihao.promotion.ui.android.WelcomeUi;

public class Welcome extends Activity {

	Single_Gallery m_gy;
	int currentIndex_ = 0;
	private ImageView[] imageviews;
	private int gridview_horSpac = 26;// 设置信息圆点的间隔距离
	private int gridview_xpadding = 10;
	private int gridview_ypadding = 5;
	private String str;

	@SuppressLint("CommitPrefEdits")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		// 退出是调用此方法 直接退出应用
		MyApplication.getInstance().addActivity(this);
		setContentView(R.layout.welcome);

		SharedPreferences sp = this.getSharedPreferences("microblog",
				MODE_PRIVATE);

		String userName = sp.getString("userName", "");
		// Log.i("userName","zheshi"+sp.getString("userName", "none")+"he"+9);

		if (userName.equals("duihao")) {

			Intent intent = new Intent();
			intent.setClass(Welcome.this, WelcomeUi.class);
			startActivity(intent);

		} else {

			SharedPreferences.Editor editor = sp.edit();
			editor.putString("userName", "duihao");
			editor.commit();
		}
		m_gy = (Single_Gallery) findViewById(R.id.m_wel_gy);
		m_gy.setAdapter(new ImageAdapter(Welcome.this));
		m_gy.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// 使用for循环，给所有的gallery索引添加图片
				for (int i = 0; i < imageviews.length; i++) {
					imageviews[i].setImageResource(R.drawable.black_ball);
				}
				// 为被选中的gallery的索引添加图片
				imageviews[position].setImageResource(R.drawable.red_ball);

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}

		});

		LinearLayout viewgroup = (LinearLayout) findViewById(R.id.linearlayout_id);
		imageviews = setGalleryIndex(viewgroup);

	}

	// 设置左右滑动索引
	private ImageView[] setGalleryIndex(LinearLayout viewgroup) {
		
		ImageView[] images = new ImageView[ImageAdapter.mps.length];

		for (int i = 0; i < images.length; i++) {
			
			ImageView newimage = new ImageView(this);
			
			newimage.setLayoutParams(new LayoutParams(30, 20));
			
			newimage.setPadding(5, 0, 5, 0);
			
			images[i] = newimage;
			if (i == 0) {
				// 默认选择第一张
				images[i].setImageResource(R.drawable.red_ball);
			} else {
				images[i].setImageResource(R.drawable.black_ball);
			}
			viewgroup.addView(images[i]);
			if (i == images.length - 1) {
				Button bt = new Button(this);
				bt.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT));
				bt.setPadding(20, 0, 20, 0);

				bt.setTextColor(Color.RED);
				bt.setBackgroundResource(R.drawable.jin);
				bt.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent();
						intent.setClass(Welcome.this, WelcomeUi.class);
						startActivity(intent);
					}
				});
				viewgroup.addView(bt);
			}

		}

		return images;
	}

}
