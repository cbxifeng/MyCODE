package com.duihao.promotion.frame.android;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.util.Log;

public class RequestData {
	// �����б���߳�
	private Context context;

	private OnTaskUpdateListener listener;
	String discuss;
	private String str;

	public RequestData(Context context, OnTaskUpdateListener listener,
			String str) {

		this.str = str;
		Log.i("pistr", " " + str);
		this.context = context;
		this.listener = listener;
		thread thd = new thread(str);
		thd.start();

	}

	private class thread extends Thread {

		public thread(String str) {

		}

		@Override
		public void run() {

			Log.i("pidddddd", " " + str);
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
					Log.i("��������", result.toString());
					
					// ��ʽ���ַ����������������
					inputStream.close();
					reader.close();
				}  
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (listener != null) {

				listener.getDate(result.toString(), null);

			}
		}

	};

}
