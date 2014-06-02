package com.duihao.promotion.http.android;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class HttpUrl {
	static Bitmap bitmap;

	// static Bitmap tuijianbitmap;
	static SoftReference<Bitmap> soft_bitmap;

	public HttpUrl() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static Bitmap httpUrlPic(final String str) {
		// ͼƬ�������߳�
		Thread Threadthum = new Thread() {

			@Override
			public void run() {
				try {
					URL url = new URL(str);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setDoInput(true);
					conn.connect();
					InputStream inputstream = conn.getInputStream();
					bitmap = BitmapFactory.decodeStream(inputstream);
					Log.i("�Ƿ�����ɹ�bitmap"," "+ bitmap.getByteCount());
					SoftReference<Bitmap> soft_bitmap = new SoftReference<Bitmap>(
							bitmap);
					Log.i("bitmap�Ĵ�С�ǣ�"," "+soft_bitmap.get().getByteCount());

					// inputstream.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				super.run();
			}

		};
		Threadthum.start();
		return bitmap;
	}

	// ����http
	public static String getNet_data(String str) {
		StringBuffer result = new StringBuffer();
		HttpResponse httpResponse = null;
		HttpEntity httpEntity = null;
		HttpClient httpClient = new DefaultHttpClient();
		InputStream inputStream = null;
		try {

			// ��÷��ʵ�ַ
			HttpGet httpGet = new HttpGet(str);
			// ���ͷ�������
			httpResponse = httpClient.execute(httpGet);
			// �����������
			httpEntity = httpResponse.getEntity();
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				// ���������
				inputStream = httpEntity.getContent();
				// ��ȡ����
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(inputStream));
				String line;
				while ((line = reader.readLine()) != null) {
					result.append(line);
				}

				// ��ʽ���ַ����������������
				inputStream.close();
				reader.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

}
