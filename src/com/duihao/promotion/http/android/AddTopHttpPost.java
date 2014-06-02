package com.duihao.promotion.http.android;

import java.io.File;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class AddTopHttpPost {

	

	public static String post(String url, String username,
			String file) {
		HttpClient httpclient = new DefaultHttpClient();

		httpclient.getParams().setParameter(
				CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

		HttpPost post = new HttpPost("http://b2b.duihao.net/json/?f=community&userid=14");

		File files = new File(file);

		MultipartEntity mpentity = new MultipartEntity();

		ContentBody cbfile = new FileBody(files, "image/jpg");

		ContentBody cbmessage = null;

		ContentBody cbid = null;

		try {
			cbmessage = new StringBody(username);

			//cbid = new StringBody(userid);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mpentity.addPart("file", cbfile);

		mpentity.addPart("username", cbmessage);

		//mpentity.addPart("userid", cbid);

		post.setEntity(mpentity);

		HttpResponse httpreson;
		 String str = null;
		try {
			httpreson = httpclient.execute(post);
			int sco=httpreson.getStatusLine().getStatusCode();
			 Log.i("jinlaile ", ""+sco);
			if (sco == 200) {
                 Log.i("jinlaile ", "½øÀ´ÁË¶î");
				HttpEntity resEntity = httpreson.getEntity();
				
				//InputStream content = resEntity.getContent();
				
				//str=content.toString();
				
				str=EntityUtils.toString( resEntity );
				
				Log.i("jinlaile ", ""+str);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return str;

	}
}
