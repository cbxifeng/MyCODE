package com.duihao.promotion.main.android;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.duihao.promotion.ui.android.WelcomeUi;

public class UiWlcome extends Activity implements OnPageChangeListener,
		OnClickListener {
	private ImageView[] dots;
	private int currentIndex;
	// 欢迎图片id
	private static final int[] welcome_pic = { R.drawable.welcome1,
			R.drawable.welcome2, R.drawable.welcome3 };
	// 布局
	private ViewGroup main;
	// 体验按钮
	private ImageButton image;
	// 按钮标记
	private final int GON_BTN = 10;
	// 视图集合
	ArrayList<View> pageviews;
	// viewpage视图
	private ViewGroup group;

	private ViewPager viewpage;
	private ViewpageAdapter picadapter;
	private ImageView imageview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SharedPreferences sp = this.getSharedPreferences("microblog",
				MODE_PRIVATE);

		String userName = sp.getString("userName", "");
		// Log.i("userName","zheshi"+sp.getString("userName", "none")+"he"+9);

		if (userName.equals("duihao")) {

			Intent intent = new Intent();
			intent.setClass(UiWlcome.this, WelcomeUi.class);
			startActivity(intent);

		} else {

			SharedPreferences.Editor editor = sp.edit();
			editor.putString("userName", "duihao");
			editor.commit();
		}
		pageviews = new ArrayList<View>();

		LayoutInflater inflater = getLayoutInflater();

		main = (ViewGroup) inflater.inflate(R.layout.help, null);

		LinearLayout.LayoutParams parma = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);

		for (int i = 0; i < welcome_pic.length; i++) {

			RelativeLayout r1 = new RelativeLayout(this);

			r1.setLayoutParams(parma);

			r1.setBackgroundResource(welcome_pic[i]);

			if (i == (welcome_pic.length - 1)) {

				image = new ImageButton(this);

				image.setBackgroundResource(R.drawable.pic_tag_change);

				image.setOnClickListener(this);

				image.setTag(GON_BTN);

				RelativeLayout.LayoutParams r2 = new RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);

				r2.bottomMargin = 80;

				r2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

				r2.addRule(RelativeLayout.CENTER_HORIZONTAL,
						RelativeLayout.TRUE);

				r1.addView(image, r2);

			}

			pageviews.add(r1);

		}

		group = (ViewGroup) main.findViewById(R.id.viewGroup);

		viewpage = (ViewPager) main.findViewById(R.id.guidePages);

		picadapter = new ViewpageAdapter(pageviews);

		viewpage.setAdapter(picadapter);
		// 绑定回调
		viewpage.setOnPageChangeListener(this);
		// 小点初始化
		initDots();

		setContentView(main);
	}

	private void initDots() {
		dots = new ImageView[welcome_pic.length];
		for (int i = 0; i < welcome_pic.length; i++) {
			imageview = new ImageView(UiWlcome.this);
			LinearLayout.LayoutParams pam = new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			pam.setMargins(0, 0, 10, 0);
			imageview.setLayoutParams(pam);
			imageview.setMaxHeight(20);
			imageview.setMaxWidth(40);
			imageview.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			imageview.setAdjustViewBounds(true);
			// imageview.setMargins(10, 0, 10, 0);
			imageview.setBackgroundResource(R.drawable.dot);
			dots[i] = imageview;
			dots[i].setEnabled(false);
			dots[i].setTag(i);
			dots[i].setOnClickListener(this);
			group.addView(dots[i]);
		}
		currentIndex = 0;
		dots[currentIndex].setEnabled(true);
	}

	private void setCurView(int position) {

		if (position < 0 || position >= welcome_pic.length) {

			return;
		}

		viewpage.setCurrentItem(position);

	}

	private void setCurdot(int position) {

		if (position < 0 || position > welcome_pic.length - 1
				|| currentIndex == position) {
			return;
		}
		dots[position].setEnabled(true);
		dots[currentIndex].setEnabled(false);
		currentIndex = position;
	}

	class ViewpageAdapter extends PagerAdapter {
		// 界面列表
		private List<View> views;

		public ViewpageAdapter(List<View> views) {
			this.views = views;
			// TODO Auto-generated constructor stub
		}

		// 销毁arg1位置界面
		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			// TODO Auto-generated method stub

			((ViewPager) arg0).removeView(views.get(arg1));

		}

		// 初始化arg1位置界面
		@Override
		public Object instantiateItem(View arg0, int arg1) {

			// TODO Auto-generated method stub
			((ViewPager) arg0).addView(views.get(arg1));

			return views.get(arg1);
		}

		// 得到当前界面数
		@Override
		public int getCount() {

			// TODO Auto-generated method stub

			if (views != null) {
				return views.size();
			}
			return 0;
		}

		// 判断是否由对象生成界面
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return (arg0 == arg1);
		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		setCurdot(arg0);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int position = (Integer) v.getTag();

		if (position == GON_BTN) {
			Intent intent = new Intent();
			intent.setClass(UiWlcome.this, WelcomeUi.class);
			startActivity(intent);
		} else {
			setCurView(position);
			setCurdot(position);
		}
	}

}
