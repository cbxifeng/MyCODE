package com.duihao.promotion.http.android;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.duihao.promotion.main.android.R;

public class RegisterActivity extends Activity {
	WebView webregister;
//注册页面
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.register_main);
		webregister = (WebView) findViewById(R.id.webregist);

		webregister.getSettings().setBuiltInZoomControls(true);// 会出现放大缩小的按钮
		webregister.getSettings().setSupportZoom(true);

		webregister.getSettings().setJavaScriptEnabled(true);
		webregister.getSettings().setPluginsEnabled(true);
		webregister.addJavascriptInterface(new InJavaScriptLocalObj(),
				"local_obj");
		webregister.setWebViewClient(new MyWebViewClient());
		webregister.loadUrl("接口");
		super.onCreate(savedInstanceState);
	}

	// webview接收网页基本配置
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
