package com.duihao.promotion.ui.android;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.ImageView;

import com.duihao.promotion.http.android.NetWorkingCheck;
import com.duihao.promotion.main.android.R;
import com.duihao.promotion.service.android.MyApplication;
import com.duihao.promotion.service.android.WelcomeService;

public class WelcomeUi extends Activity {

	private Handler handel = new Handler();// �ٿ��������涯������Handler
	ImageView imageView1;
	NetworkInfo info;
	// ������ͼ
	Intent service_it;

	// �������ݿ�

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// �˳��ǵ��ô˷��� ֱ���˳�Ӧ��
		MyApplication.getInstance().addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.welcomethis);
		// ���ض���
		imageView1 = (ImageView) findViewById(R.id.m_image);
		imageView1.setBackgroundResource(R.drawable.startgoin);
		handel.postDelayed(new Runnable() {// ����Handler�еķ���postDelayed�������ܶ�������

					@Override
					public void run() {
						AnimationDrawable animain = (AnimationDrawable) imageView1// AnimationDrawable����������
								.getBackground();
						animain.start();
					}
				}, 0);

		String ser = CONNECTIVITY_SERVICE;
		NetWorkingCheck netw = new NetWorkingCheck(this, ser);

		netw.chek();
		
		if (NetWorkingCheck.B) {
			
			NetWorkingCheck.B = false;
			
		} else {
			// ע��㲥
			IntentFilter filter = new IntentFilter();
			filter.addAction("com.cn.WelcomeService.receiver");
			registerReceiver(receiver, filter);
			requestMethod();

		}

		// // ��������
		//
		// MyApplication.getInstance().addActivity(this);
		// // �ж��Ƿ����� û�еĻ���ʾ����
		// ConnectivityManager manager = (ConnectivityManager) this
		// .getSystemService(CONNECTIVITY_SERVICE);
		// info = manager.getActiveNetworkInfo();
		// if (info == null) {
		//
		// Intent intent = new Intent();
		// intent.setClass(this, Main.class);
		// startActivity(intent);
		//
		// } else {
		// // new Thread(this).start();
		//
		// }

	}

	// ���񷽷�
	private void requestMethod() {
		// String url =
		// String.format("http://b2b.duihao.net/json/?f=cate&pid=1");
		service_it = new Intent(WelcomeUi.this, WelcomeService.class);
		startService(service_it);
	}

	// �㲥������
	BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals("com.cn.WelcomeService.receiver")) {
				stopService(service_it);
				Intent in = new Intent(WelcomeUi.this, Main.class);
				startActivity(in);

			}

		}

	};

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (receiver != null) {
			//unregisterReceiver(receiver);
		}
		super.onDestroy();
	}

}
