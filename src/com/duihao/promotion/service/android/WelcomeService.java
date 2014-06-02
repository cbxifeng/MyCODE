package com.duihao.promotion.service.android;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.duihao.promotion.database.android.M_database;
import com.duihao.promotion.http.android.HttpUrl;
import com.duihao.promotion.http.android.NetWorkingCheck;
import com.duihao.promotion.javavbean.android.AppConstant;
import com.duihao.promotion.javavbean.android.Title;

public class WelcomeService extends Service {
	Tthread thread;
	M_database database;
	SQLiteDatabase db;
	List<Title> list_title = new ArrayList<Title>();
	Title m_title = null;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		
	
		if (thread == null) {
			thread = new Tthread();
			thread.start();
		} else {
			thread = null;
			thread = new Tthread();
		}
		super.onStart(intent, startId);

	}

	private class Tthread extends Thread {

		@Override
		public void run() {
			int i = 0;
			// 解析结构
			// 如果有网更新数据库内容
			// 创建数据库

			database = new M_database(WelcomeService.this, "duihao.db", null, 5);
			ContentValues cv = new ContentValues();
			db = database.getWritableDatabase();

			String src = String.format(AppConstant.PRODUCT_TITLE_URL,
					AppConstant.SAME_TAG);

			try {
				String result = HttpUrl.getNet_data(src);
				if (result != null && result.startsWith("\ufeff")) {
					result = result.substring(1);
				}
				System.out.print(result);
				JSONArray jsonarray = new JSONArray(result);
				// 定义个中间变量 用于传输数据

				for (int j = 0; j < jsonarray.length(); j++) {
					JSONObject object = (JSONObject) jsonarray.get(j);
					Title m_title = new Title(object.getInt("mytypeid"),
							object.getString("name"), object.getString("model"));
					System.out.println(m_title.getMytypeid());

					list_title.add(m_title);
					// 给cate表存入数据 主要是title 和catid的值
					cv.put("mytypeid", object.getInt("mytypeid"));
					cv.put("name", object.getString("name"));
					cv.put("model", object.getString("model"));
					// 对比表内容是否更新表
					db.insert("titleall", "null", cv);

				}
				db.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			Message msg = m_handler.obtainMessage();
			msg.what = 0;
			m_handler.sendMessage(msg);

			super.run();
		}

	};

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	private Handler m_handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			if (msg.what == 0) {
				thread = null;
				Intent it = new Intent();
				it.setAction("com.cn.WelcomeService.receiver");
				sendBroadcast(it);

			}

			super.handleMessage(msg);
		}

	};

}
