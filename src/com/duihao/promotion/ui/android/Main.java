package com.duihao.promotion.ui.android;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.duihao.promotion.database.android.M_database;
import com.duihao.promotion.gridview.android.GridView_Optionmenu;
import com.duihao.promotion.http.android.MapMaths;
import com.duihao.promotion.http.android.NetWorkingCheck;
import com.duihao.promotion.javavbean.android.Title;
import com.duihao.promotion.main.android.R;
import com.duihao.promotion.service.android.MyApplication;

//标题部分入口文件
@SuppressWarnings("deprecation")
public class Main extends TabActivity implements OnTabChangeListener {
	Button button;
	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	private GestureDetector gestureDetector;
	View.OnTouchListener gestureListener;
	private Animation slideLeftIn;
	private Animation slideLeftOut;
	private Animation slideRightIn;
	private Animation slideRightOut;
	private ViewFlipper viewFlipper;
	List<Title> list_title = new ArrayList<Title>();
	private String[] titles;
	private int[] typeid;
	int currentView = 0;
	private static int maxTabIndex = 2;
	private TabWidget mTabWidget;
	// private AnimationTabHost tabHost;
	private int currentTabID = 0;
	int catid[] = new int[10];
	public static int mytypeid = 90;
	M_database database;
	SQLiteDatabase db;
	private TabHost tabHost;
	Intent map;
	public double shop_Latitude;
	public double shop_Longitude;
	// gridview
	private View menuView;// GridView的视图
	GridView menuGrid;
	AlertDialog menuDialog;
	public static int FlagMapMath = 0;

	/* public static int m_catid=11; */
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		String ser = CONNECTIVITY_SERVICE;

		NetWorkingCheck netw = new NetWorkingCheck(this, ser);

		netw.chek();
		// 注册广播
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.cn.mapService.receiver");
		registerReceiver(receiver, filter);
		MyApplication.getInstance().addActivity(this);

		ImageButton optionmenu = (ImageButton) findViewById(R.id.optionmenu);
		optionmenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("点击", "点击事件");
				openOptionsMenu();
			}
		});

		// 从数据库里读取Title数据
		database = new M_database(Main.this, "duihao.db", null, 5);
		ContentValues cv = new ContentValues();
		db = database.getWritableDatabase();
		// 数据库调取title
		Cursor cursor = db.rawQuery("SELECT mytypeid FROM cate", null);
		if (cursor.getCount() == 0) {
			Cursor cursor1 = db.rawQuery("SELECT mytypeid,name FROM titleall",
					null);
			while (cursor1.moveToNext()) {
				cv.put("mytypeid",
						cursor1.getInt(cursor1.getColumnIndex("mytypeid")));
				cv.put("name",
						cursor1.getString(cursor1.getColumnIndex("name")));
				db.insert("cate", null, cv);
			}
		}
		cursor = db.rawQuery("SELECT mytypeid,name FROM cate", null);
		titles = new String[cursor.getCount()];
		typeid = new int[cursor.getCount()];
		Log.i("数据库有多少行数据", "" + cursor.getCount());
		int j = 0;
		while (cursor.moveToNext()) {
			// tabhost参数类型
			titles[j] = cursor.getString(cursor.getColumnIndex("name"));
			typeid[j] = cursor.getInt(cursor.getColumnIndex("mytypeid"));
			Log.i("测试5", titles[j]);
			Log.i("测试6", "" + typeid[j]);

			j++;
		}
		/*
		 * titles = new String[WelcomeUi.list_title.size()]; for (int i = 0; i <
		 * WelcomeUi.list_title.size(); i++) { titles[i] =
		 * WelcomeUi.list_title.get(i).getCatname();
		 * catid[i]=WelcomeUi.list_title.get(i).getCatid(); }
		 */
		tabHost = (TabHost) this.findViewById(android.R.id.tabhost);

		mTabWidget = (TabWidget) findViewById(android.R.id.tabs);

		tabHost.setOnTabChangedListener(this);

		LinearLayout view1 = (LinearLayout) getLayoutInflater().inflate(
				R.layout.tabwighet, null);
		Log.i("ddddddddddddddd", "" + titles.length);

		TextView view = ((TextView) view1.findViewById(R.id.tabtitle));
		for (int i = 0; i < titles.length; i++) {

			// view.setText(titles[i]);
			Log.i("qqqqqqqqqqqqqqqqq", "" + titles.length);
			tabHost.addTab(tabHost
					.newTabSpec(titles[i])
					.setIndicator(titles[i])
					.setContent(
							new Intent().setClass(this, MainActivity.class)
									.putExtra("typeid", typeid[i])
									.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));

		}

		//
		// gridview
		menuView = View.inflate(this, R.layout.gridview_menu, null);
		menuGrid = (GridView) menuView.findViewById(R.id.gridview);
		// 调用工具类的变量和方法
		GridView_Optionmenu go = new GridView_Optionmenu();
		menuGrid.setAdapter(go.getMenuAdapter(Main.this, go.menu_name_array,
				go.menu_image_array));
		/** 监听menu选项 **/

		menuGrid.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch (arg2) {
				case 0:// 内容收藏

					break;
				case 1:// 在线订单

					break;
				case 2:// 地图

					map = new Intent(Main.this, MapMaths.class);
					startService(map);
					break;
				case 3:// 我的收藏
					Intent it3 = new Intent(Main.this, CollectList.class);

					startActivity(it3);
					break;
				case 4:// 我的订单

					break;

				case 5:// 关于我们
					Intent intentin = new Intent();

					intentin.setClass(Main.this, AboatActivity.class);

					startActivity(intentin);
					break;
				// case 7:// 用户中心
				// // 跳转之前判断用户是否已经登录 如果没有 跳转到 Member_info界面，如果已经登录跳转到个人中心
				// // Person_Center界面
				//
				// Intent intent1 = new Intent();
				// intent1.setClass(Main.this, Person_Center.class);
				//
				// startActivity(intent1);
				// break;
				// case 6:// 退出
				//
				// MyApplication.getInstance().exit();
				//
				// break;

				}
			}
		});

		/*
		 * tab 点击事件 mTabWidget.getChildAt(0).setOnClickListener(new
		 * OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub tabHost.setCurrentTab(0); } });
		 */
		// tabHost.setOpenAnimation(true);
		tabHost.setCurrentTab(0);
		onTabChanged(titles[0]);
		gestureDetector = new GestureDetector(new TabHostTouch());

	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {

		if (menuDialog == null) {

			menuDialog = new AlertDialog.Builder(this).setView(menuView).show();

		} else {

			menuDialog.show();

		}
		return false;// 返回为true 则显示系统menu
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		openOptionsMenu();

		return super.onCreateOptionsMenu(menu);

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

	// 因为侧滑老是出现问题 没有解决之前所以先注销掉，

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

				mTabWidget.getChildAt(i).setBackgroundResource(
						R.drawable.bottom_press);

				TextView textview = (TextView) tabHost.getTabWidget()
						.getChildAt(i).findViewById(android.R.id.title);
				// 获取自定义的颜色
				textview.setTextColor(this.getResources().getColor(
						R.color.white));

				textview.setTextSize(12);

			} else {
				mTabWidget.getChildAt(i).setBackgroundResource(
						R.drawable.bottom_normal);
				TextView textview = (TextView) tabHost.getTabWidget()
						.getChildAt(i).findViewById(android.R.id.title);
				textview.setTextColor(Color.BLACK);
				textview.setTextSize(12);
			}
		}

	}

	// 广播接收器
	BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals("com.cn.mapService.receiver")) {
				stopService(map);
				shop_Longitude = intent.getDoubleExtra("Long", 0.000);
				shop_Latitude = intent.getDoubleExtra("Lat", 0.000);
				Log.i("Long", "" + shop_Longitude);
				Log.i("Lat", "" + shop_Latitude);

				Intent in = new Intent(Main.this,
						com.duihao.baidu.test.MainActivity.class);
				in.putExtra("Long", shop_Longitude);
				in.putExtra("Lat", shop_Latitude);
				Log.i("shop_Longitude", "" + shop_Longitude);
				Log.i("shop_Latitude", "" + shop_Latitude);
				FlagMapMath = 1;
				startActivity(in);

			}

		}

	};

}