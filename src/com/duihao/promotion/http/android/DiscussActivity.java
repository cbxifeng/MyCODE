package com.duihao.promotion.http.android;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.duihao.promotion.frame.android.OnTaskUpdateListener;
import com.duihao.promotion.frame.android.RequestData;
import com.duihao.promotion.javavbean.android.AppConstant;
import com.duihao.promotion.main.android.R;

public class DiscussActivity extends Activity {
	// 评论页
	ListView discusslist;
	Map<String, Object> map;
	List<Map<String, Object>> m_newslistinfo = new ArrayList<Map<String, Object>>();
	public static int pid;
	public static int pcatid;
	private String str;
	private TextView titles;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.discuss);
		discusslist = (ListView) findViewById(R.id.discusslist);
		titles = (TextView) findViewById(R.id.titles);
		titles.setText(Web_View.news.getTitle());
		Intent intent = this.getIntent();
		pid = intent.getIntExtra("id", 0);

		pcatid = intent.getIntExtra("catids", 0);

		str = String
				.format(AppConstant.PRODUCT_TITLE_URL,
						pcatid, pid);

		initView();
	}

	private void initView() {

		new RequestData(this, listener, str);
	}

	OnTaskUpdateListener listener = new OnTaskUpdateListener() {

		@Override
		public void getDate(Object obj, String msg) {
			// TODO Auto-generated method stub

			if (obj != null) {
				if (obj instanceof String) {
					String obj1 = (String) obj;
					try {
						JSONArray jsonarray = new JSONArray(obj1);
						for (int i = 0; i < jsonarray.length(); i++) {

							JSONObject object = (JSONObject) jsonarray.get(i);
							// DiscussMath m_list = new
							// DiscussMath(object.getString("model"),
							// object.getInt("id"),
							// object.getInt("catid"),
							// object.getInt("commentid"),
							// object.getString("title"),
							// object.getString("comment"),
							// object.getInt("userid"),
							// object.getString("addtime"),
							// object.getString("ip"),
							// object.getInt("cid"),
							// object.getInt("states"),
							// object.getInt("posts"),
							// object.getString("username"),
							// object.getInt("modelid"));

							map = new HashMap<String, Object>();

							SimpleDateFormat sdf = new SimpleDateFormat(
									"yyyy年MM月dd日HH时mm分");
							long time = Long.valueOf(object
									.getString("addtime"));
							String addtime = sdf.format(new Date(time * 1000L));
							Log.i("time", addtime);
							map.put("comment", object.getString("comment"));

							map.put("title", object.getString("title"));

							map.put("name", object.getString("username"));
							Log.i("name", object.getString("username"));

							map.put("time", addtime);
							// Log.i("time", object.getString("time"));

							map.put("ip", object.getString("ip"));
							Log.i("ip", object.getString("ip"));
							m_newslistinfo.add(map);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Message m = new Message();
					m.what = HANDLER_HTTP_REQUEST_DATA;
					m.obj = m_newslistinfo;
					handler.sendMessage(m);
				}
			} else {
				// if(msg != null)
				handler.sendEmptyMessage(HANDLER_HTTP_REQUEST_FAIL);
			}

		}

	};

	private final static int HANDLER_HTTP_REQUEST_DATA = 0;
	private final static int HANDLER_HTTP_REQUEST_FAIL = 1;

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case HANDLER_HTTP_REQUEST_DATA:

				List<Map<String, Object>> listinfo = (List<Map<String, Object>>) msg.obj;

				SimpleAdapter adapter = new SimpleAdapter(DiscussActivity.this,
						listinfo, R.layout.discusslist, new String[] { "name", "time", "comment" }, new int[] {
								R.id.name, R.id.time, R.id.comment });

				discusslist.setAdapter(adapter);
				break;
			case HANDLER_HTTP_REQUEST_FAIL:
				Toast.makeText(DiscussActivity.this, "联网请求失败",
						Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}

			super.handleMessage(msg);
		}

	};

}
