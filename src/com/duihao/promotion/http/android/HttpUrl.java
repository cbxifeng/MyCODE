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
		// 图片解析的线程
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
					Log.i("是否解析成功bitmap"," "+ bitmap.getByteCount());
					SoftReference<Bitmap> soft_bitmap = new SoftReference<Bitmap>(
							bitmap);
					Log.i("bitmap的大小是："," "+soft_bitmap.get().getByteCount());

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

	// 请求http
	public static String getNet_data(String str) {
		StringBuffer result = new StringBuffer();
		HttpResponse httpResponse = null;
		HttpEntity httpEntity = null;
		HttpClient httpClient = new DefaultHttpClient();
		InputStream inputStream = null;
		try {

			// 获得访问地址
			HttpGet httpGet = new HttpGet(str);
			// 发送访问请求
			httpResponse = httpClient.execute(httpGet);
			// 获得请求数据
			httpEntity = httpResponse.getEntity();
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				// 获得输入流
				inputStream = httpEntity.getContent();
				// 读取数据
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(inputStream));
				String line;
				while ((line = reader.readLine()) != null) {
					result.append(line);
				}

				// 格式化字符串，避免解析错误
				inputStream.close();
				reader.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

}
