package com.duihao.promotion.ui.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;

import com.duihao.promotion.gridview.android.GridView_Optionmenu;
import com.duihao.promotion.gridview.android.MyMessage;
import com.duihao.promotion.main.android.R;
import com.duihao.promotion.service.android.MyApplication;

public class YuDing extends Activity {

	// gridview
	private View menuView;// GridView的视图
	GridView menuGrid;
	AlertDialog menuDialog;
	private String url;
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		// 退出是调用此方法 直接退出应用
		MyApplication.getInstance().addActivity(this);
		setContentView(R.layout.yuding);

		webView = (WebView) findViewById(R.id.yuding_view);

		Intent intent = this.getIntent();

		url = intent.getStringExtra("url");

		webView.loadUrl(url);

		// 返回
		Button back = (Button) findViewById(R.id.back);

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				YuDing.this.finish();
			}
		});

		// 菜单
		Button optionmenu = (Button) findViewById(R.id.optionmenu);

		optionmenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("dddd", "ddsasdf");
				openOptionsMenu();
			}
		});

		webView.getSettings().setBuiltInZoomControls(true);// 会出现放大缩小的按钮
		webView.getSettings().setSupportZoom(true);

		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setPluginsEnabled(true);
		webView.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
		webView.setWebViewClient(new MyWebViewClient());

		menuView = View.inflate(this, R.layout.gridview_menu, null);
		menuGrid = (GridView) menuView.findViewById(R.id.gridview);
		// 调用工具类的变量和方法
		GridView_Optionmenu go = new GridView_Optionmenu();
		menuGrid.setAdapter(go.getMenuAdapter(YuDing.this, go.menu_name_array,
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

					Intent map = new Intent(YuDing.this, com.duihao.baidu.test.MainActivity.class);
					startActivity(map);
					break;
				case 3:// 我的订单
					Intent it3 = new Intent(YuDing.this, CollectList.class);

					startActivity(it3);
					break;
				case 4:// 我的投诉

					break;
				case 5:// 我的消息
					Intent intentme = new Intent();

					intentme.setClass(YuDing.this, MyMessage.class);

					startActivity(intentme);
					break;
				case 6:// 关于我们
					Intent intentin = new Intent();

					intentin.setClass(YuDing.this, AboatActivity.class);

					startActivity(intentin);
					break;
				case 7:// 用户中心
						// 跳转之前判断用户是否已经登录 如果没有 跳转到 Member_info界面，如果已经登录跳转到个人中心
						// Person_Center界面

					Intent intent1 = new Intent();
					intent1.setClass(YuDing.this, Person_Center.class);

					startActivity(intent1);
					break;
				case 8:// 退出

					MyApplication.getInstance().exit();

					break;

				}
			}
		});

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

	final class MyWebViewClient extends WebViewClient {
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}

		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			Log.d("WebView", "onPageStarted");
			super.onPageStarted(view, url, favicon);
		}

		public void onPageFinished(WebView view, String url) {
			Log.d("WebView", "onPageFinished ");
			view.loadUrl("javascript:window.local_obj.showSource('<head>'+"
					+ "document.getElementsByTagName('html')[0].innerHTML+'</head>');");
			super.onPageFinished(view, url);
		}
	}

	final class InJavaScriptLocalObj {
		public void showSource(String html) {
			Log.d("HTML", html);
		}
	}

}
