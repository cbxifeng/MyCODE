package com.duihao.promotion.http.android;

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
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.duihao.promotion.gridview.android.MyMessage;
import com.duihao.promotion.main.android.R;
import com.duihao.promotion.service.android.MyApplication;
import com.duihao.promotion.ui.android.Main;


public class Member_info extends Activity {
	// 登陆页
	EditText m_user_number = null;// 用户名
	EditText m_user_password = null;// 密码
	Button m_register = null;
	Button m_login = null;
	Button m_back;
	private String strResult;
	int C;
	private String uname;
	private String upass;
	static String log;
	thresdTui thread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// // 隐藏输入法
		// InputMethodManager imm = (InputMethodManager)
		// getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		// // 显示或者隐藏输入法
		// imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		setContentView(R.layout.member_info);

		MyApplication.getInstance().addActivity(this);

		m_back = (Button) findViewById(R.id.m_back);
		m_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 实现的功能实际上是返回上个界面的功能
				Intent intent = new Intent();
				intent.setClass(Member_info.this, Main.class);
				startActivity(intent);

			}
		});
		m_user_number = (EditText) findViewById(R.id.m_user_number);
		m_user_number.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				m_user_number.setHint(" ");
			}
		});
		m_user_password = (EditText) findViewById(R.id.m_user_password);
		m_user_password.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				m_user_password.setHint(" ");
			}
		});

		// 跳转注册页
		m_register = (Button) findViewById(R.id.m_register);
		m_register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent() {
				};
				it.setClass(Member_info.this, RegisterActivity.class);
				startActivity(it);

				// Toast.makeText(getApplicationContext(), "注册成功",
				// Toast.LENGTH_SHORT).show();
			}
		});
		// 登录
		m_login = (Button) findViewById(R.id.m_login);
		m_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				thread = new thresdTui();
				thread.start();

			}
		});

	}

	// post请求

	private String HttpPostClient(final String username, final String userpass,
			final String url) throws ClientProtocolException, IOException {

		HttpPost httpRequest = new HttpPost(url);
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("username", username));

		params.add(new BasicNameValuePair("password", userpass));
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

				InputStream content = entity.getContent();

				strResult = convertStreamToString(content);

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

	class thresdTui extends Thread {
		String s;

		@Override
		public void run() {
			uname = m_user_number.getText().toString();
			upass = m_user_password.getText().toString();
			String url = "接口";
			try {
				s = HttpPostClient(uname, upass, url);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Message msg = new Message();
			msg.obj = s;
			msg.what = 1;
			han.sendMessage(msg);
			super.run();
		}

	};

	Handler han = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				String get = (String) msg.obj;
				// Log.i("get", "  " + get.length());
				char[] ch = get.toCharArray();
				// Log.i("ch1", "  " + ch[0]);
				// Log.i("ch2", "  " + ch[1]);
				// Log.i("uname", "  " +uname);
				// Log.i("upass", "  " + upass);
				if (ch[1] != '-') {

					SharedPreferences sp = getSharedPreferences(
							"microblog_user", MODE_PRIVATE);
					SharedPreferences.Editor editor = sp.edit();
					editor.putString("userName", uname);

					// Log.i("uname", "  " +uname);

					editor.putString("upass", upass);

					editor.putString("userId", get);
					Log.i("get存储", "  " + get);

					editor.commit();
					// Member_info.this.finish();
					// startActivity(new Intent(Member_info.this,
					// MainActivity.class));

					Intent in = new Intent();
					in.setClass(Member_info.this, MyMessage.class);
					startActivity(in);
					Member_info.this.finish();

				} else {
					AlertDialog dlg = new AlertDialog.Builder(Member_info.this)
							.setTitle("联网失败，请检查账号或密码是否正确！")
							.setMessage("请重新登录...")
							.setNegativeButton("关闭",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method
											// stub

										}
									}).create();
					dlg.show();
				}
			}
			super.handleMessage(msg);
		}

	};

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

		return sb.toString();

	}

}
