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

//���ⲿ������ļ�
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
	private View menuView;// GridView����ͼ
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
		// ע��㲥
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.cn.mapService.receiver");
		registerReceiver(receiver, filter);
		MyApplication.getInstance().addActivity(this);

		ImageButton optionmenu = (ImageButton) findViewById(R.id.optionmenu);
		optionmenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("���", "����¼�");
				openOptionsMenu();
			}
		});

		// �����ݿ����ȡTitle����
		database = new M_database(Main.this, "duihao.db", null, 5);
		ContentValues cv = new ContentValues();
		db = database.getWritableDatabase();
		// ���ݿ��ȡtitle
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
		Log.i("���ݿ��ж���������", "" + cursor.getCount());
		int j = 0;
		while (cursor.moveToNext()) {
			// tabhost��������
			titles[j] = cursor.getString(cursor.getColumnIndex("name"));
			typeid[j] = cursor.getInt(cursor.getColumnIndex("mytypeid"));
			Log.i("����5", titles[j]);
			Log.i("����6", "" + typeid[j]);

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
		// ���ù�����ı����ͷ���
		GridView_Optionmenu go = new GridView_Optionmenu();
		menuGrid.setAdapter(go.getMenuAdapter(Main.this, go.menu_name_array,
				go.menu_image_array));
		/** ����menuѡ�� **/

		menuGrid.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch (arg2) {
				case 0:// �����ղ�

					break;
				case 1:// ���߶���

					break;
				case 2:// ��ͼ

					map = new Intent(Main.this, MapMaths.class);
					startService(map);
					break;
				case 3:// �ҵ��ղ�
					Intent it3 = new Intent(Main.this, CollectList.class);

					startActivity(it3);
					break;
				case 4:// �ҵĶ���

					break;

				case 5:// ��������
					Intent intentin = new Intent();

					intentin.setClass(Main.this, AboatActivity.class);

					startActivity(intentin);
					break;
				// case 7:// �û�����
				// // ��ת֮ǰ�ж��û��Ƿ��Ѿ���¼ ���û�� ��ת�� Member_info���棬����Ѿ���¼��ת����������
				// // Person_Center����
				//
				// Intent intent1 = new Intent();
				// intent1.setClass(Main.this, Person_Center.class);
				//
				// startActivity(intent1);
				// break;
				// case 6:// �˳�
				//
				// MyApplication.getInstance().exit();
				//
				// break;

				}
			}
		});

		/*
		 * tab ����¼� mTabWidget.getChildAt(0).setOnClickListener(new
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
		return false;// ����Ϊtrue ����ʾϵͳmenu
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		openOptionsMenu();

		return super.onCreateOptionsMenu(menu);

	}

	private class TabHostTouch extends SimpleOnGestureListener {
		/** ������ҳ������� */
		// private static final int ON_TOUCH_DISTANCE = 140;

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
				return false;
			// �һ������л������һ��tab
			if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
				currentTabID = tabHost.getCurrentTab() - 1;
				if (currentTabID <= 0) {// ѭ��
					currentTabID = 0;
				}
			}
			// �󻬶����л����ұ�һ��tab
			else if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
				currentTabID = tabHost.getCurrentTab() + 1;
				if (currentTabID >= tabHost.getChildCount()) {// ѭ��
					currentTabID = tabHost.getCurrentTab() + 1;
				}
			}

			tabHost.setCurrentTab(currentTabID);

			return false;
		}
	}

	// ��Ϊ�໬���ǳ������� û�н��֮ǰ������ע������

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
				// ��ȡ�Զ������ɫ
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

	// �㲥������
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