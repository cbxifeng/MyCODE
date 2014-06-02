package com.duihao.promotion.ui.android;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.duihao.promotion.frame.android.OnTaskUpdateListener;
import com.duihao.promotion.frame.android.RequestData;
import com.duihao.promotion.javavbean.android.AppConstant;
import com.duihao.promotion.javavbean.android.AppUtil;
import com.duihao.promotion.main.android.R;
import com.duihao.promotion.service.android.MyApplication;

public class ZiXun extends Activity {
	private TextView toolbarProduct, toolbarDongtai, toolbarPic, toolbarCircle,
			toolbarAbaout;
	WebView webView;
	private String str;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 退出是调用此方法 直接退出应用
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		MyApplication.getInstance().addActivity(this);
		setContentView(R.layout.zixun);
		// 产品
		toolbarProduct = (TextView) findViewById(R.id.toolbarProduct);
		// 动态
		toolbarDongtai = (TextView) findViewById(R.id.toolbarDongtai);

		// 圈子
		toolbarCircle = (TextView) findViewById(R.id.toolbarCenter);
		// 相册
		toolbarPic = (TextView) findViewById(R.id.toolbarCircle);
		// 咨询
		toolbarAbaout = (TextView) findViewById(R.id.toolbarAbaout);

		setButtonToolMemu();
		// View v = LayoutInflater.from(ZiXun.this).inflate(R.layout.zixun,
		// null);
		//
		// addContext(v);
  
		webView = (WebView) findViewById(R.id.zixun);
		initView();
		webView.getSettings().setBuiltInZoomControls(true);// 会出现放大缩小的按钮
		webView.getSettings().setSupportZoom(true);

		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setPluginsEnabled(true);
		webView.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
		webView.setWebViewClient(new MyWebViewClient());

		toolbarProduct.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				AppUtil.setButton_flag(1);// 进来就给标记来确认选择时赋值背景
				Intent product = new Intent();
				product.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				product.setClass(ZiXun.this, Main.class);
				startActivity(product);
				return false;
			}
		});
		// 动态
		toolbarDongtai.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				AppUtil.setButton_flag(2);// 进来就给标记来确认选择时赋值背景
				Intent dongtai = new Intent();
				dongtai.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				dongtai.setClass(ZiXun.this, DongTai.class);
				startActivity(dongtai);
				return false;
			}
		});
		// 相册
		toolbarPic.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				AppUtil.setButton_flag(3);// 进来就给标记来确认选择时赋值背景
				Intent center = new Intent();
				center.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				center.setClass(ZiXun.this, PicServiceTitle.class);
				startActivity(center);
				return false;
			}
		});
		// 圈子
		toolbarCircle.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				AppUtil.setButton_flag(4);// 进来就给标记来确认选择时赋值背景
				Intent person_center = new Intent();
				person_center.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				person_center.setClass(ZiXun.this, Circle.class);
				startActivity(person_center);
				return false;
			}
		});
		// 咨询
		toolbarAbaout.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				AppUtil.setButton_flag(5);// 进来就给标记来确认选择时赋值背景
				Intent about = new Intent();
				about.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				about.setClass(ZiXun.this, ZiXun.class);
				startActivity(about);
				return false;
			}
		});
	}

	// 列表
	private void initView() {
		// 新闻列表

		str = String.format(AppConstant.ABOUT_URL, AppConstant.SAME_TAG);

		new RequestData(ZiXun.this, listener, str);
	}

	// 列表
	OnTaskUpdateListener listener = new OnTaskUpdateListener() {

		@Override
		public void getDate(Object obj, String msg) {

			if (obj != null) {
				if (obj instanceof String) {

					String obj1 = (String) obj;
					String strurl = null;
					try {
						JSONObject jsonarray = new JSONObject(obj1);

					

						
							strurl = jsonarray.getString("kfurl");

						

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					Message m = new Message();

					m.what = HANDLER_HTTP_REQUEST_DATA;

					m.obj = strurl;

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

				String url = (String) msg.obj;

				webView.loadUrl(url);

				break;

			case HANDLER_HTTP_REQUEST_FAIL:

				break;
			default:
				break;
			}

			super.handleMessage(msg);
		}

	};

	public void setButtonToolMemu() {
		switch (AppUtil.getButton_flag()) {
		case 1:

			toolbarProduct.setTextColor(getResources().getColor(R.color.white));
			toolbarDongtai.setTextColor(this.getResources().getColor(
					R.color.white));
			toolbarPic
					.setTextColor(this.getResources().getColor(R.color.white));
			toolbarCircle.setTextColor(this.getResources().getColor(
					R.color.white));
			toolbarAbaout.setTextColor(this.getResources().getColor(
					R.color.white));

			toolbarProduct.setBackgroundResource(R.drawable.menu_background);
			toolbarDongtai.setBackgroundResource(R.drawable.menu_background2);
			toolbarPic.setBackgroundResource(R.drawable.menu_background2);
			toolbarCircle.setBackgroundResource(R.drawable.menu_background2);
			toolbarAbaout.setBackgroundResource(R.drawable.menu_background2);
			break;
		case 2:
			toolbarProduct.setTextColor(getResources().getColor(R.color.white));
			toolbarDongtai.setTextColor(this.getResources().getColor(
					R.color.white));
			toolbarPic
					.setTextColor(this.getResources().getColor(R.color.white));
			toolbarCircle.setTextColor(this.getResources().getColor(
					R.color.white));
			toolbarAbaout.setTextColor(this.getResources().getColor(
					R.color.white));

			toolbarProduct.setBackgroundResource(R.drawable.menu_background2);
			toolbarDongtai.setBackgroundResource(R.drawable.menu_background);
			toolbarPic.setBackgroundResource(R.drawable.menu_background2);
			toolbarCircle.setBackgroundResource(R.drawable.menu_background2);
			toolbarAbaout.setBackgroundResource(R.drawable.menu_background2);
			break;
		case 3:

			toolbarProduct.setTextColor(getResources().getColor(R.color.white));
			toolbarDongtai.setTextColor(this.getResources().getColor(
					R.color.white));
			toolbarPic
					.setTextColor(this.getResources().getColor(R.color.white));
			toolbarCircle.setTextColor(this.getResources().getColor(
					R.color.white));
			toolbarAbaout.setTextColor(this.getResources().getColor(
					R.color.white));
			toolbarProduct.setBackgroundResource(R.drawable.menu_background2);
			toolbarDongtai.setBackgroundResource(R.drawable.menu_background2);
			toolbarPic.setBackgroundResource(R.drawable.menu_background);
			toolbarCircle.setBackgroundResource(R.drawable.menu_background2);
			toolbarAbaout.setBackgroundResource(R.drawable.menu_background2);
			break;
		case 4:
			toolbarProduct.setTextColor(getResources().getColor(R.color.white));
			toolbarDongtai.setTextColor(this.getResources().getColor(
					R.color.white));
			toolbarPic
					.setTextColor(this.getResources().getColor(R.color.white));
			toolbarCircle.setTextColor(this.getResources().getColor(
					R.color.white));
			toolbarAbaout.setTextColor(this.getResources().getColor(
					R.color.white));
			toolbarProduct.setBackgroundResource(R.drawable.menu_background2);
			toolbarDongtai.setBackgroundResource(R.drawable.menu_background2);
			toolbarPic.setBackgroundResource(R.drawable.menu_background2);
			toolbarCircle.setBackgroundResource(R.drawable.menu_background);
			toolbarAbaout.setBackgroundResource(R.drawable.menu_background2);
			break;

		case 5:
			toolbarProduct.setTextColor(getResources().getColor(R.color.white));
			toolbarDongtai.setTextColor(this.getResources().getColor(
					R.color.white));
			toolbarPic
					.setTextColor(this.getResources().getColor(R.color.white));
			toolbarCircle.setTextColor(this.getResources().getColor(
					R.color.white));
			toolbarAbaout.setTextColor(this.getResources().getColor(
					R.color.white));
			toolbarProduct.setBackgroundResource(R.drawable.menu_background2);
			toolbarDongtai.setBackgroundResource(R.drawable.menu_background2);
			toolbarPic.setBackgroundResource(R.drawable.menu_background2);
			toolbarCircle.setBackgroundResource(R.drawable.menu_background2);
			toolbarAbaout.setBackgroundResource(R.drawable.menu_background);
			break;

		}
	}
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
