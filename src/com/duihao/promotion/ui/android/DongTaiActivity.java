package com.duihao.promotion.ui.android;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.R.color;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.dongtai.lazylist.LazyAdapter;
import com.duihao.promotion.frame.android.OnTaskUpdateListener;
import com.duihao.promotion.frame.android.RequestData;
import com.duihao.promotion.http.android.HttpUrl;
import com.duihao.promotion.javavbean.android.AppConstant;
import com.duihao.promotion.javavbean.android.IndustryTrendsBean;
import com.duihao.promotion.main.android.R;
import com.duihao.promotion.service.android.MyApplication;
import com.duihao.promotion.ui.android.MyListView.OnRefreshListener;

public class DongTaiActivity extends AppActivity {

	HttpUrl httpurl;
	LazyAdapter adapter;
	private Bitmap bitmap; // 图片
	MyListView listview;
	String str;// 列表解析
	private int rows = 10;

	ArrayList<IndustryTrendsBean> m_newslistinfo;
	private int myid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);

		MyListView mylistview = new MyListView(this);

		mylistview.setId(123);

		mylistview.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,

		LayoutParams.FILL_PARENT));// 大小设置

		mylistview.setBackgroundColor(color.white);

		Drawable draw = getResources().getDrawable(R.drawable.itemline);

		mylistview.setDivider(draw);

		mylistview.setDividerHeight(2);

		addContext(mylistview);

		Intent it = this.getIntent();

		myid = it.getIntExtra("mytypeid", 0);
		Log.i("video", "" + myid);
		initView();

		listview = (MyListView) findViewById(123);

		Button jinm_title = (Button) findViewById(R.id.topic_title);
		jinm_title.setVisibility(8);
		// jinm_title.setText("动态");

		// 添加更多按钮
		listview = (MyListView) findViewById(123);
		// 推荐位
		View loadMore = getLayoutInflater().inflate(R.layout.loadmore, null);

		listview.addFooterView(loadMore);

		Button loadMorebutton = (Button) findViewById(R.id.loadMoreButton);

		loadMorebutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				rows = rows + 10;
				initView();

			}
		});

		listview.setonRefreshListener(new OnRefreshListener() {

			public void onRefresh() {
				// TODO Auto-generated method stub
				new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {
						// TODO Auto-generated method stub

						try {
							Thread.sleep(100);

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						rows = rows + 10;
						initView();
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {

						new LazyAdapter(DongTaiActivity.this, m_newslistinfo)
								.notifyDataSetChanged();

						listview.onRefreshComplete();

					}

				}.execute();
			}
		});

		// 退出activity是调用
		MyApplication.getInstance().addActivity(this);
		// 给listview添加底部加载项

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent intent = new Intent();

				intent.setClass(DongTaiActivity.this, IndustryTrends.class);

				intent.putExtra("url", m_newslistinfo.get(position - 1)
						.getUrl());
				Log.i("url********", m_newslistinfo.get(position - 1).getUrl());
				intent.putExtra("mytitle", m_newslistinfo.get(position - 1)
						.getTitle());
				Log.i("MainAvtivity界面getMytypeid",
						"" + m_newslistinfo.get(position - 1).getId());

				startActivity(intent);

			}

		});

	}

	// 列表
	private void initView() {
		// 新闻列表
		Log.i("may", "" + myid);
		str = String.format(AppConstant.BUSINESS_URL, AppConstant.SAME_TAG,
				myid, rows);

		new RequestData(this, listener, str);
	}

	// 列表
	OnTaskUpdateListener listener = new OnTaskUpdateListener() {

		@Override
		public void getDate(Object obj, String msg) {

			if (obj != null) {
				if (obj instanceof String) {

					String obj1 = (String) obj;

					try {
						JSONArray jsonarray = new JSONArray(obj1);
						m_newslistinfo = new ArrayList<IndustryTrendsBean>();
						// Log.i("動態", jsonarray.toString());
						for (int i = 0; i < jsonarray.length(); i++) {

							JSONObject object = (JSONObject) jsonarray.get(i);

							IndustryTrendsBean m_list = new IndustryTrendsBean(
									object.getInt("id"),
									object.getString("title"),
									object.getString("thumb"),
									object.getString("description"),
									object.getString("addtime"),
									object.getString("url"));
							m_newslistinfo.add(m_list);

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

	private final static int HANDLER_HTTP_REQUEST_DATA_TWO = 2;
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case HANDLER_HTTP_REQUEST_DATA:

				ArrayList<IndustryTrendsBean> listinfo = (ArrayList<IndustryTrendsBean>) msg.obj;
				adapter = new LazyAdapter(DongTaiActivity.this, listinfo);
				listview.setAdapter(adapter);

				break;

			case HANDLER_HTTP_REQUEST_FAIL:

				Toast.makeText(DongTaiActivity.this, "联网请求失败",
						Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
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
		return true;
	}

}
