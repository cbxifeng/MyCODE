package com.duihao.promotion.javavbean.android;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class HttpFileUpTool {
	/**
	 * ͨ��ƴ�ӵķ�ʽ�����������ݣ�ʵ�ֲ��������Լ��ļ�����
	 * 
	 * @param actionUrl
	 * @param params
	 * @param files
	 * @return
	 * @throws IOException
	 */
	static int i = 1;
	static char c;

	public static Boolean post(String actionUrl, Map<String, String> params,
			File file) throws IOException {

		String BOUNDARY = java.util.UUID.randomUUID().toString();
		String PREFIX = "--", LINEND = "\r\n";
		String MULTIPART_FROM_DATA = "multipart/form-data";
		String CHARSET = "UTF-8";
		HttpClient client = new DefaultHttpClient();
		URL uri = new URL(actionUrl);
		HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
		conn.setReadTimeout(5 * 1000); // ������ʱ��
		conn.setDoInput(true);// ��������
		conn.setDoOutput(true);// �������
		conn.setUseCaches(false); // ������ʹ�û���
		conn.setRequestMethod("POST");
		conn.setRequestProperty("connection", "keep-alive");
		conn.setRequestProperty("Charsert", "UTF-8");
		conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
				+ ";boundary=" + BOUNDARY);
		
		// ������ƴ�ı����͵Ĳ���
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			sb.append(PREFIX);
			sb.append(BOUNDARY);
			sb.append(LINEND);
			sb.append("Content-Disposition: form-data; name=\""
					+ entry.getKey() + "\"" + LINEND);
			sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
			sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
			sb.append(LINEND);
			sb.append(entry.getValue());
			sb.append(LINEND);
		}

		DataOutputStream outStream = new DataOutputStream(
				conn.getOutputStream());
		outStream.write(sb.toString().getBytes());
		InputStream in = null;
//		// �����ļ�����

		StringBuilder sb1 = new StringBuilder();
		sb1.append(PREFIX);
		sb1.append(BOUNDARY);
		sb1.append(LINEND);
		sb1.append("Content-Disposition: form-data; name=\"file\"; filename=\""
				+ file.getName() + "\"" + LINEND);
		sb1.append("Content-Type: application/octet-stream; charset=" + CHARSET
				+ LINEND);
		sb1.append(LINEND);
		outStream.write(sb1.toString().getBytes());

		InputStream is = new FileInputStream(file);
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = is.read(buffer)) != -1) {
			// ��ÿ�ζ�ȡ����ʱ��û�ж������һ��֮ǰ ÿ��len��ֵ����1024���ֽڣ�
			// ���һ��Ϊʵ���ļ��Ĵ�С�����û������ lenΪ-1
			Log.i("len����ֵ�ǣ�", "" + len);
			outStream.write(buffer, 0, len);
		}

		is.close();
		outStream.write(LINEND.getBytes());

		// ���������־
		byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
		outStream.write(end_data);
		outStream.flush();

		// �õ���Ӧ��
		int res = conn.getResponseCode();
		
		Log.i("���󷵻�ֵ", ""+res);
		
		if (res == 200) {
			in = conn.getInputStream();
			int ch;
			StringBuilder sb2 = new StringBuilder();
			while ((ch = in.read()) != -1) {
				sb2.append((char) ch);
			}

			Log.i("���󷵻�ֵ", sb2.toString());
			c = sb2.toString().charAt(sb2.toString().length() - 1);

//			outStream.close();
			conn.disconnect();
		}

		if (c == '1') {
			return true;
		} else {
			return false;
		}
		// return in.toString();
	}
}
