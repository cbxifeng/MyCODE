package com.duihao.promotion.ui.android;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.TabActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.duihao.promotion.frame.android.OnTaskUpdateListener;
import com.duihao.promotion.frame.android.RequestData;
import com.duihao.promotion.javavbean.android.AppConstant;
import com.duihao.promotion.javavbean.android.PicTitle;
import com.duihao.promotion.main.android.R;

public class PicServiceTitle extends TabActivity implements OnTabChangeListener {
	List<PicTitle> m_newslistinfo;
	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	private GestureDetector gestureDetector;
	private TabWidget mTabWidget;
	private TabHost tabHost;
	private String[] titles;
	private int[] mytypid;
	private int currentTabID = 0;
	private String str;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.picservicetitle);
		super.onCreate(savedInstanceState);

		initView();

		Button m_back = (Button) findViewById(R.id.back);

		m_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*
				 * Intent back = new Intent(Web_View.this, Main.class);
				 * startActivity(back);
				 */
				PicServiceTitle.this.finish();
			}
		});
		tabHost = (TabHost) this.findViewById(android.R.id.tabhost);

		mTabWidget = (TabWidget) this.findViewById(android.R.id.tabs);

		tabHost.setOnTabChangedListener(this);

		gestureDetector = new GestureDetector(new TabHostTouch());

	}

	private class TabHostTouch extends SimpleOnGestureListener {
		/** 滑动翻页所需距离 */
		// private static final int ON_TOUCH_DISTANCE = 140;

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
				return false;
			// 右滑动，切换到左边一个tab
			if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
				currentTabID = tabHost.getCurrentTab() - 1;
				if (currentTabID <= 0) {// 循环
					currentTabID = 0;
				}
			}
			// 左滑动，切换到右边一个tab
			else if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
				currentTabID = tabHost.getCurrentTab() + 1;
				if (currentTabID >= tabHost.getChildCount()) {// 循环
					currentTabID = tabHost.getCurrentTab() + 1;
				}
			}

			tabHost.setCurrentTab(currentTabID);

			return false;
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		if (gestureDetector.onTouchEvent(event)) {
			event.setAction(MotionEvent.ACTION_CANCEL);
		}
		return super.dispatchTouchEvent(event);
	}

	@Override
	public void onTabChanged(String tabId) {
		// TODO Auto-generated method stub
		for (int i = 0; i < mTabWidget.getChildCount(); i++) {
			if (titles[i] == tabId) {

				/*
				 * mTabWidget.getChildAt(i).setBackgroundResource(
				 * R.drawable.bottom_press);
				 */

				TextView textview = (TextView) tabHost.getTabWidget()
						.getChildAt(i).findViewById(android.R.id.title);
				// 获取自定义的颜色
				textview.setTextColor(this.getResources().getColor(
						R.color.white));

			} else {
				/*
				 * mTabWidget.getChildAt(i).setBackgroundResource(
				 * R.drawable.bottom_normal);
				 */
				TextView textview = (TextView) tabHost.getTabWidget()
						.getChildAt(i).findViewById(android.R.id.title);
				textview.setTextColor(Color.BLACK);

			}
		}
	}

	// 列表
	private void initView() {
		// 新闻列表

		str = String.format(AppConstant.PROCUCT_PIC, AppConstant.USERID);

		new RequestData(this, listener, str);

	}

	// 列表
	OnTaskUpdateListener listener = new OnTaskUpdateListener() {

		@Override
		public void getDate(Object obj, String msg) {
			// TODO Auto-generated method stub

			ContentValues cv = new ContentValues();

			if (obj != null) {
				if (obj instanceof String) {

					String obj1 = (String) obj;

					try {
						JSONArray jsonarray = new JSONArray(obj1);
						m_newslistinfo = new ArrayList<PicTitle>();
						for (int i = 0; i < jsonarray.length(); i++) {

							JSONObject object = (JSONObject) jsonarray.get(i);

							PicTitle m_list = new PicTitle(
									object.getString("name"),
									object.getString("description"),
									object.getInt("mytypeid"));
							m_newslistinfo.add(m_list);

						}

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					Message m = new Message();

					m.what = HANDLER_HTTP_REQUEST_DATA;

					m.obj = m_newslistinfo;

					handler.sendMessage(m);

				}
			} else {
				// if(msg != null)
				handler.sendEmptyMessage(HANDLER_HTTP_REQUEST_FAIL);
			}

		}

	};

	private final static int HANDLER_HTTP_REQUEST_DATA = 0;

	private final static int HANDLER_HTTP_REQUEST_FAIL = 1;

	private final static int HANDLER_HTTP_REQUEST_DATA_TWO = 2;
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case HANDLER_HTTP_REQUEST_DATA:

				ArrayList<PicTitle> listinfo = (ArrayList<PicTitle>) msg.obj;
				Log.i("picInfo", "" + listinfo.size());
				titles = new String[listinfo.size()];
				mytypid = new int[listinfo.size()];
				for (int i = 0; i < listinfo.size(); i++) {
					titles[i] = listinfo.get(i).getName();
					mytypid[i] = listinfo.get(i).getMytypeid();
				}
				Log.i("picInfo", "" + titles[0]);
				for (int i = 0; i < titles.length; i++) {

					// view.setText(titles[i]);
					Log.i("qqqqqqqqqqqqqqqqq", "" + titles.length);
					tabHost.addTab(tabHost
							.newTabSpec(titles[i])
							.setIndicator(titles[i])
							.setContent(
									new Intent()
											.setClass(PicServiceTitle.this,
													PicActivity.class)
											.putExtra("mytypeid", mytypid[i])
											.addFlags(
													Intent.FLAG_ACTIVITY_CLEAR_TOP)));

				}
				tabHost.setCurrentTab(0);

				onTabChanged(titles[0]);
				break;

			case HANDLER_HTTP_REQUEST_FAIL:

				Toast.makeText(PicServiceTitle.this, "联网请求失败",
						Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}

			super.handleMessage(msg);
		}

	};

}
