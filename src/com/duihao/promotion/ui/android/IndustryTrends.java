package com.duihao.promotion.ui.android;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.duihao.promotion.http.android.HttpUrl;
import com.duihao.promotion.http.android.NewsAdd;
import com.duihao.promotion.javavbean.android.IndustryTrendsBeanInfo;
import com.duihao.promotion.main.android.R;
import com.duihao.promotion.service.android.MyApplication;

public class IndustryTrends extends Activity {

	private IndustryTrendsBeanInfo it;
	private String strResult;
	HttpUrl httpurl;
	private TextView x_text;
	private TextView x_content;
	private TextView m_jiage;
	private String v = null;
	private String m = null;
	private int vid;
	private ImageView image;
	private Button button;// 收藏
	private Button back;

	public static String m_title;
	String str = null;// 解析地址
	private HttpResponse httpResponse = null;
	private HttpEntity httpEntity = null;
	HttpGet httpGet;
	public static String url;


	int id;
	public static Boolean Bon = false;
	private static WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.web_view);
		MyApplication.getInstance().addActivity(this);

		// news = new IndustryTrendsBeanInfo();
		button = (Button) findViewById(R.id.x_shoucang);

		Button m_back = (Button) findViewById(R.id.m_back);

		m_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent back = new Intent(IndustryTrends.this, Main.class);
				startActivity(back);
			}
		});
		webView = (WebView) findViewById(R.id.web_view);
		back = (Button) findViewById(R.id.m_back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*Intent back = new Intent(IndustryTrends.this,
						DongTaiActivity.class);
				startActivity(back);*/
				IndustryTrends.this.finish();
			}
		});

		if (Bon) {

			
			Log.i("**************", "1111" + url);
			webView.loadUrl(url);

			Bon = false;

		} else {

			// 获得传过来的数据
			Intent intent = this.getIntent();
			url = intent.getStringExtra("url");
			Log.i("33333333", "d" + url);
			m_title = intent.getStringExtra("mytitle");

			Log.i("接收的值：", url);
			// 添加收藏

			webView.loadUrl(url);

		}

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent();
				it.putExtra("in", 1);
				it.putExtra("url", url);
				it.putExtra("title", m_title);
				it.setClass(IndustryTrends.this, NewsAdd.class);
				startActivity(it);
			}
		});
		webView.getSettings().setBuiltInZoomControls(true);// 会出现放大缩小的按钮
		webView.getSettings().setSupportZoom(true);

		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setPluginsEnabled(true);
		webView.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
		webView.setWebViewClient(new MyWebViewClient());

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {

			webView.goBack();

		} else {
			IndustryTrends.this.finish();
		}
		return true;

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
