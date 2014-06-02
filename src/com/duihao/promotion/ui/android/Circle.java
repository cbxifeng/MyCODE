package com.duihao.promotion.ui.android;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.R.color;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.duihao.promotion.frame.android.OnTaskUpdateListener;
import com.duihao.promotion.frame.android.RequestData;
import com.duihao.promotion.http.android.HttpUrl;
import com.duihao.promotion.javavbean.android.AppConstant;
import com.duihao.promotion.javavbean.android.Circle_List;
import com.duihao.promotion.main.android.R;
import com.duihao.promotion.service.android.MyApplication;
import com.duihao.promotion.ui.android.MyListView.OnRefreshListener;

public class Circle extends AppActivity {

	View view;
	Button add_topic;
	// 自定义listview
	MyListView mylistview;
	List<Circle_List> lists;
	// 接口
	String str;
	Bitmap bitmap;
	private static int row = 10;
	private final static int HANDLER_HTTP_REQUEST_DATA = 0;

	private final static int HANDLER_HTTP_REQUEST_FAIL = 1;

	private final static int HANDLER_HTTP_REQUEST_DATA_TWO = 2;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		// 退出是调用此方法 直接退出应用
		MyApplication.getInstance().addActivity(this);
		view = View.inflate(Circle.this, R.layout.circle, null);
		addContext(view);
		// 动态创建自定义listview
		/*
		 * MyListView mylistview = new MyListView(this); mylistview.setId(10);
		 * mylistview.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
		 * LayoutParams.FILL_PARENT));
		 */// 大小设置
		mylistview = (MyListView) view.findViewById(R.id.mylistview);
		mylistview.setBackgroundColor(color.white);
		Drawable draw = getResources().getDrawable(R.drawable.itemline);
		mylistview.setDivider(draw);
		mylistview.setDividerHeight(2);
		// addContext(mylistview);
		// 新闻列表接口
		str = AppConstant.CIRCLE_LIST;
		initView();
		// 添加更多按钮
		// mylistview = (MyListView) findViewById(10);
		View loadMore = getLayoutInflater().inflate(R.layout.loadmore, null);
		mylistview.addFooterView(loadMore);
		Button loadMorebutton = (Button) findViewById(R.id.loadMoreButton);
		loadMorebutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				row +=10;
				initView();

			}
		});

		mylistview.setonRefreshListener(new OnRefreshListener() {

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
						row += 10;
						initView();
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {

						new MyAdapter(Circle.this).notifyDataSetChanged();

						mylistview.onRefreshComplete();

					}

				}.execute();
			}
		});

		mylistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				
				  Intent intent = new Intent();
				  
				 intent.setClass(Circle.this, CurclePin.class);
				  
				 intent.putExtra("id", lists.get(position).getId());
				  
				  startActivity(intent);
				 

			}

		});

		// 添加话题
		add_topic = (Button) findViewById(R.id.add_topic);
		add_topic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent(Circle.this, Add_Topic.class);
				startActivity(it);

			}
		});

	}

	// 列表
	private void initView() {
		Log.i("row", "" + row);
		String temp = String.format(str, row);
		Log.i("000000000", temp);

		new RequestData(this, listener, temp);
	}

	// 列表
	OnTaskUpdateListener listener = new OnTaskUpdateListener() {

		@Override
		public void getDate(Object obj, String msg) {
			// TODO Auto-generated method stub
			if (obj != null) {
				if (obj instanceof String) {

					String obj1 = (String) obj;

					try {
						JSONArray jsonarray = new JSONArray(obj1);
						lists = new ArrayList<Circle_List>();
						for (int i = 0; i < jsonarray.length(); i++) {

							JSONObject object = (JSONObject) jsonarray.get(i);

							Circle_List m_list = new Circle_List(
									object.getInt("id"),
									object.getString("title"),
									object.getString("username"),
									object.getString("pics"),
									object.getInt("gentie"));
							lists.add(m_list);

							int h = 0;

						}

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					Message m = new Message();

					m.what = HANDLER_HTTP_REQUEST_DATA;

					m.obj = lists;

					handler.sendMessage(m);

				}
			} else {
				// if(msg != null)
				handler.sendEmptyMessage(HANDLER_HTTP_REQUEST_FAIL);
			}

		}

	};

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case HANDLER_HTTP_REQUEST_DATA:

				ArrayList<Circle_List> listinfo = (ArrayList<Circle_List>) msg.obj;
				Log.i("lists", "" + lists.size());
				mylistview.setAdapter(new MyAdapter(Circle.this));

				break;

			case HANDLER_HTTP_REQUEST_FAIL:

				Toast.makeText(Circle.this, "联网请求失败", Toast.LENGTH_SHORT)
						.show();
				break;
			default:
				break;
			}

			super.handleMessage(msg);
		}

	};

	// 自定义listview适配器
	class MyAdapter extends BaseAdapter {
		// 获取view
		LayoutInflater laout;

		// 构造方法获取上下文
		public MyAdapter(Context context) {

			laout = LayoutInflater.from(context);

		}

		@Override
		public int getCount() {

			return lists.size();
		}

		@Override
		public Object getItem(int position) {

			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;

			String edittime;

			if (lists.get(position).getPics() != null) {

				bitmap = HttpUrl.httpUrlPic(lists.get(position).getPics());
			}
			if (convertView == null) {

				holder = new ViewHolder();

				convertView = laout.inflate(R.layout.circle_list_item, null);

				holder.Pics = (ImageView) convertView.findViewById(R.id.thumb);

				holder.Title = (TextView) convertView
						.findViewById(R.id.topic_title);

				holder.Username = (TextView) convertView
						.findViewById(R.id.topic_userid);

				holder.Gentie = (TextView) convertView
						.findViewById(R.id.gentie);

				convertView.setTag(holder);

			} else {

				holder = (ViewHolder) convertView.getTag();
			}

			holder.Title.setText(lists.get(position).getTitle());

			holder.Username.setText(lists.get(position).getUsername());

			holder.Gentie.setText("跟帖人数： " + lists.get(position).getGentie());

			holder.Pics.setImageBitmap(bitmap);

			return convertView;
		}

		class ViewHolder {
			TextView Title;

			TextView Username;

			ImageView Pics;

			TextView Gentie;

		}
	}

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
