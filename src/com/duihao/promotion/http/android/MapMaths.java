package com.duihao.promotion.http.android;

import org.json.JSONObject;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.duihao.promotion.javavbean.android.AppConstant;

public class MapMaths extends Service {
	m_thread th;
	public double shop_Latitude;
	public double shop_Longitude;

	class m_thread extends Thread {

		public void run() {
			Log.i("m_thread线程：", "ffffffff");
			String src = String.format(AppConstant.ABOUT_URL,
					AppConstant.SAME_TAG);
			Log.i("m_thread线程：", src);
			try {
				String result = HttpUrl.getNet_data(src);
				Log.i("数据:", result);
				Log.i("m_thread", result);
				JSONObject jsonarray = new JSONObject(result);
				shop_Latitude = jsonarray.getDouble("com_mapy");
				shop_Longitude = jsonarray.getDouble("com_mapx");

			} catch (Exception e) {
				e.printStackTrace();
			}
			Message msg = new Message();
			msg.what = 0;
			m_handler.sendMessage(msg);

			super.run();

		}

	};

	private Handler m_handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			if (msg.what == 0) {
				
				Intent it = new Intent();
				
				Log.i("sssss", ""+shop_Longitude);
				Log.i("hhhh", ""+shop_Latitude);
				it.setAction("com.cn.mapService.receiver");
				it.putExtra("Long", shop_Longitude);
				it.putExtra("Lat", shop_Latitude);
				sendBroadcast(it);
				
//				Intent intent = new Intent();
//				intent.setClass(MapMaths.this,
//						com.duihao.baidu.test.MainActivity.class);
//				Log.i("传经度", "" + shop_Latitude);
//				Log.i("传维度", "" + shop_Longitude);
//				FlagMapMath = 1;
//				intent.putExtra("Longitude", shop_Longitude);
//				intent.putExtra("Latitude", shop_Latitude);
//				startActivity(intent);

			}

			super.handleMessage(msg);
		}

	};

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
		th = new m_thread();
		th.start();
		super.onStart(intent, startId);
	}
	
}
