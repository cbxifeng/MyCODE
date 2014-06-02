package com.duihao.promotion.ui.android;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongtai.lazylist.ImageLoader;
import com.duihao.promotion.http.android.HttpUrl;
import com.duihao.promotion.javavbean.android.AppConstant;
import com.duihao.promotion.main.android.R;
import com.duihao.promotion.service.android.MyApplication;
public class AboatActivity extends AppActivity {

	Button member_ship, conect_us, bt;
	View view;
	String tv_str, bt_str, imageurl;
	ImageView image;
	double shop_Longitude;// 店铺纬度
	double shop_Latitude;// 店铺经度
	ImageLoader imageloader;
	public static int FlagMap=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 退出是调用此方法 直接退出应用
		MyApplication.getInstance().addActivity(this);
		m_thread mt1 = new m_thread();
		mt1.start();
		// setContentView(R.layout.aboutactivity);
		// 添加布局
		view = View.inflate(AboatActivity.this, R.layout.aboutactivity, null);
		addContext(view);

		conect_us = (Button) findViewById(R.id.connect_us);
	
	}

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
				tv_str = jsonarray.getString("com_about");
				bt_str = jsonarray.getString("com_name");
				imageurl = jsonarray.getString("com_apppic");
				shop_Latitude = jsonarray.getDouble("com_mapy");
				shop_Longitude = jsonarray.getDouble("com_mapx");
				Log.i("imageur", imageurl);
				Log.i("imageur", tv_str);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Message msg = m_handler.obtainMessage();
			msg.what = 0;
			m_handler.sendMessage(msg);

			super.run();

		}

	};

	private Handler m_handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			if (msg.what == 0) {
				
				TextView text = (TextView) findViewById(R.id.percontent);
				
				text.setText(tv_str);
				image = (ImageView) findViewById(R.id.perthumb);
				
				imageloader=new ImageLoader(AboatActivity.this);
				
				imageloader.DisplayImage(imageurl, AboatActivity.this, image);

				conect_us.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent();
						intent.setClass(AboatActivity.this, com.duihao.baidu.test.MainActivity.class);
						Log.i("传经度", "" + shop_Latitude);
						Log.i("传维度", "" + shop_Longitude);
						FlagMap=1;
						intent.putExtra("Longitude", shop_Longitude);
						intent.putExtra("Latitude", shop_Latitude);
						startActivity(intent);

					}
				});

			}

			super.handleMessage(msg);
		}

	};

	@Override
	protected boolean isShowToolBar() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean isShowImage() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean isShowPicTui() {
		// TODO Auto-generated method stub
		return false;
	}

}
