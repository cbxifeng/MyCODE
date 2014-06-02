package com.duihao.promotion.http.android;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.duihao.promotion.javavbean.android.NewsDetailInfo;
import com.duihao.promotion.main.android.R;
import com.duihao.promotion.service.android.MyApplication;

public class Web_View extends Activity {
	HttpUrl httpurl;
	HttpGet httpGet;
	ImageView image;
	public static String title;// 标题
	TextView timelength;// 内容
	private HttpResponse httpResponse = null;
	private HttpEntity httpEntity = null;
	// List<NewsDetailInfo> list = new ArrayList<NewsDetailInfo>();
	static NewsDetailInfo it;
	int id;
	static NewsDetailInfo news;
	public static String url;
	public static String m_title;
	private static WebView webView;
	private EditText m_editText;
	private Button m_comment;
	private View menuView;
	GridView menuGrid;
	AlertDialog menuDialog;
	Button m_back;
	String str = null;// 解析地址
	// 根据此项判断是否需要解析加载网页，如果是从收藏页面跳转过来的 不需解析 直接加载。
	public static Boolean Bon = false;

	private Button button;// 收藏

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.web_view);
		MyApplication.getInstance().addActivity(this);

		news = new NewsDetailInfo();
		// webView = (WebView) findViewById(R.id.m_web_view);

		webView = (WebView) findViewById(R.id.web_view);

		button = (Button) findViewById(R.id.x_shoucang);// 收藏

		m_back = (Button) findViewById(R.id.m_back);

		m_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		if (Bon) {

			webView.loadUrl(url);

			Bon = false;

		} else {

			// 获得穿过来的数据
			Intent intent = this.getIntent();
			url = intent.getStringExtra("myurl");
			title = intent.getStringExtra("mytitle");
			webView.loadUrl(url);

		}

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent();
				it.putExtra("in", 2);

				try {

					it.putExtra("url", url);

					it.putExtra("title", title);

					it.putExtra("in", 2);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				it.setClass(Web_View.this, NewsAdd.class);
				startActivity(it);

			}
		});

		webView.getSettings().setBuiltInZoomControls(true);// 会出现放大缩小的按钮
		webView.getSettings().setSupportZoom(true);

		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setPluginsEnabled(true);
		webView.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
		webView.setWebViewClient(new MyWebViewClient());

		super.onCreate(savedInstanceState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add("menu");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {

			webView.goBack();

		} else {
			Web_View.this.finish();
		}
		return true;

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
