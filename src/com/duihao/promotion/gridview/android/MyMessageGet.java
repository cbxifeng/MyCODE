package com.duihao.promotion.gridview.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.duihao.promotion.database.android.M_database;
import com.duihao.promotion.javavbean.android.DiscussMath;
import com.duihao.promotion.main.android.R;

public class MyMessageGet extends Activity {
	ListView mymessget;
	Map<String, Object> map;
	List<Map<String, Object>> m_newslistinfo = new ArrayList<Map<String, Object>>();
	DiscussMath m_list = new DiscussMath();
	private String str;
	private String name;
	String userName;
	thresdTui th;
	// 创建数据库
	M_database database;

	SQLiteDatabase db;

	Cursor cursor;
	private String strResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.mymessget);

		SharedPreferences sp = getSharedPreferences("microblog_user",
				MODE_PRIVATE);
		userName = sp.getString("userName", "");
		database = new M_database(MyMessageGet.this, "duihao.db", null, 5);

		db = database.getWritableDatabase();

		cursor = db.rawQuery(
				"SELECT _id,pid,messae,name,time  FROM getmessage ", null);

		str = String.format("接口");
		mymessget = (ListView) findViewById(R.id.mymesslist);

		th = new thresdTui();
		th.start();
		super.onCreate(savedInstanceState);
	}

	// 请求发送
	@SuppressWarnings("unused")
	private String HttpPostClientp(String username) throws Exception {

		HttpPost httpRequest = new HttpPost("接口");
		List<NameValuePair> params = new ArrayList<NameValuePair>();

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

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/* 若状态码为200 ok */
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			try {
				// strResult = EntityUtils.toString(httpResponse
				// .getEntity());
				HttpEntity entity = httpResponse.getEntity();

				InputStream contents = entity.getContent();

				strResult = convertStreamToString(contents);

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
			// SharedPreferences sp = getSharedPreferences("microblog_user",
			// MODE_PRIVATE);
			// String userName = sp.getString("userName", "");
			try {

				if (cursor.getCount() > 0) {

					while (cursor.moveToNext()) {

						int catid = cursor.getInt(cursor.getColumnIndex("pid"));

						Log.i("要刪除的pid", "" + catid);

						db.delete("getmessage", "pid" + "=" + catid, null);
					}

				}
				s = HttpPostClientp(userName);
				Log.i("接收到的：", "  " + s);
				if (s != null) {
					if (s instanceof String) {
						String obj1 = s;

						JSONArray jsonarray = new JSONArray(obj1);
						for (int i = 0; i < jsonarray.length(); i++) {

							JSONObject object = (JSONObject) jsonarray.get(i);

							// map = new HashMap<String, Object>();
							//
							// SimpleDateFormat sdf = new SimpleDateFormat(
							// "yyyy年MM月dd日HH时mm分");
							// long time = Long.valueOf(object
							// .getString("addtime"));
							// String addtime = sdf.format(new Date(time *
							// 1000L));
							// Log.i("time", addtime);
							//
							// map.put("pid", object.getString("pid"));
							//
							// map.put("_id", object.getString("id"));
							//
							// map.put("name ",
							// object.getString("tousername "));
							// Log.i("name", object.getString("username"));
							//
							// map.put("time", getStringDateShort());
							// // Log.i("time", object.getString("time"));
							//
							// map.put("messae ", object.getString("content "));
							// // Log.i("ip", object.getString("ip"));
							// m_newslistinfo.add(map);

							ContentValues cv = new ContentValues();

							cv.put("_id", object.getString("id"));

							cv.put("pid", object.getString("pid"));

							cv.put("name", object.getString("username"));

							cv.put("messae", object.getString("content"));

							cv.put("time", getStringDateShort());

							db.insert("getmessage", "null", cv);

							// db.close();

						}
					}
				}
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
				FindAll();

				// 点击查看
				mymessget.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							final int position, long id) {
						// TODO Auto-generated method stub
						cursor.moveToPosition(position);
						final long selectId = cursor.getInt(cursor
								.getColumnIndex("_id"));
						AlertDialog dlg = new AlertDialog.Builder(
								MyMessageGet.this)

								.setTitle("                          " + "收到消息")
								.setNegativeButton("查看",
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												String name = cursor.getString(cursor
														.getColumnIndex("name"));

												String content = cursor.getString(cursor
														.getColumnIndex("messae"));
												Intent it = new Intent();
												it.putExtra("name", name);
												it.putExtra("content", content);
												int B = 2;
												it.putExtra("B", B);
												it.setClass(MyMessageGet.this,
														LookMyMessae.class);
												startActivity(it);

											}
										})
								.setNeutralButton("回复", new OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										String name = cursor.getString(cursor
												.getColumnIndex("name"));
										Intent it = new Intent();
										Log.i("回复的名字", name);
										it.setClass(MyMessageGet.this,
												SendMessage.class);
										it.putExtra("f", 1);
										it.putExtra("name", name);
										startActivity(it);

									}
								})
								.setPositiveButton("删除", new OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										db.delete("getmessage", "_id" + "="
												+ selectId, null);
										FindAll();
									}
								}).create();
						Window dialogWindow = dlg.getWindow();
						WindowManager.LayoutParams lp = dialogWindow
								.getAttributes();
						dialogWindow.setGravity(Gravity.LEFT | Gravity.CENTER);
						lp.x = 100; // 新位置X坐标
						lp.y = 100; // 新位置Y坐标
						lp.width = 100; // 宽度
						lp.height = 300; // 高度
						// lp.alpha = 0.7f; // 透明度
						dialogWindow.setAttributes(lp);
						dlg.show();
					}
				});
				break;

			}
			super.handleMessage(msg);
		}

	};

	// post请求读取流
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

	private void FindAll() {
		cursor = db.rawQuery(
				"SELECT _id,pid,messae,name,time  FROM getmessage ", null);

		String[] col = new String[] { "name", "time", "messae" };
		SimpleCursorAdapter sca = new SimpleCursorAdapter(this,
				R.layout.messget, cursor, col, new int[] { R.id.getgettext,
						R.id.getgettime, R.id.getgetcontent });
		mymessget.setAdapter(sca);
	}

	// 获得时间
	public static String getStringDateShort() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		return dateString;
	}
}
