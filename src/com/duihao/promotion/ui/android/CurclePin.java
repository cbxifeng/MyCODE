package com.duihao.promotion.ui.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ParseException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import com.duihao.promotion.javavbean.android.AppConstant;
import com.duihao.promotion.main.android.R;

public class CurclePin extends Activity {
	private WebView webView;
	private int id;
	private String str;
	EditText quantext;
	String strResult;
	Button quanbutton;
	protected String content;
	Handlers hanle=new Handlers();;
	thread th;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.web_view1);

		quantext = (EditText) findViewById(R.id.quan_Text);

		quanbutton = (Button) findViewById(R.id.quan_button);

		webView = (WebView) findViewById(R.id.m_web_view);

		Intent intent = this.getIntent();

		id = intent.getIntExtra("id", 0);

		str = String.format(AppConstant.CIRCLE_PINLU, id, AppConstant.USERID);

		webView.loadUrl(str);

		webView.getSettings().setBuiltInZoomControls(true);// 会出现放大缩小的按钮
		webView.getSettings().setSupportZoom(true);

		webView.getSettings().setJavaScriptEnabled(true);

		webView.getSettings().setPluginsEnabled(true);

		webView.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");

		webView.setWebViewClient(new MyWebViewClient());

		quanbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
          content=quantext.getText().toString();
          th=new thread();
          th.start();
			}
		});
		super.onCreate(savedInstanceState);
	}

	private class thread extends Thread{
		String quan=null;
		@Override
		public void run() {

			try {
				Log.i("内容", "content"+content);
				 quan=HttpPostClientp(String.valueOf(id),content,"14","test");
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Message msg=new Message();
			msg.what=1;
			msg.obj=quan;
			
			
			
			hanle.sendMessage(msg);
			
         super.run();
		}

	};

	// 评论请求
	@SuppressWarnings("unused")
	private String HttpPostClientp(final String id, final String content,
			final String userid, final String username)
			throws ClientProtocolException, IOException {

		HttpPost httpRequest = new HttpPost(AppConstant.CIRCLE_DIS);
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("id", id));// 文章id

		params.add(new BasicNameValuePair("content", content));// 评论内容

		params.add(new BasicNameValuePair("userid", userid));// 用户id

		params.add(new BasicNameValuePair("username", username));// 用户名

		try {
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			Log.i("run", "  sssssssssssssssssssss");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/* 发送请求并等待响应 */
		HttpResponse httpResponse = null;
		try {
			httpResponse = new DefaultHttpClient().execute(httpRequest);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/* 若状态码为200 ok */
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			try {
				// strResult = EntityUtils.toString(httpResponse
				// .getEntity());
				HttpEntity entity = httpResponse.getEntity();

			//	InputStream contents = entity.getContent();

				strResult = EntityUtils.toString( entity );
				 Log.i("结果strResult",""+strResult);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return strResult;
	}

	private String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Log.i("qq8555", sb.toString());
		return sb.toString();

	}

	private  class Handlers extends Handler {
		

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			
			case 1:
				
          String it=(String) msg.obj;
          
          Log.i("结果",""+it);
			}
			super.handleMessage(msg);
		}

	};

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
