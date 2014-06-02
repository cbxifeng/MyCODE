package com.duihao.promotion.ui.android;

import java.util.ArrayList;
import java.util.List;

import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dodola.model.DuitangInfo;
import com.dodowaterfall.widget.ScaleImageView;
import com.duihao.promotion.frame.android.OnTaskUpdateListener;
import com.duihao.promotion.frame.android.RequestData;
import com.duihao.promotion.javavbean.android.AppConstant;
import com.duihao.promotion.main.android.R;
import com.example.android.bitmapfun.util.ImageFetcher;
import com.huewu.pla.lib.internal.PLA_AdapterView;
import com.huewu.pla.lib.internal.PLA_AdapterView.OnItemClickListener;
import com.pic.lazylist.adapter.ImageLoader;

public class PicActivity extends AppActivity implements IXListViewListener {

	private XListView mAdapterView;

	private LinearLayout waterfall_container;

	private LazyScrollView waterfall_scroll;

	static List<Bitmap> mThumbIds;

	private GridView gridView;

	Bitmap bitmap;

	public static DisplayMetrics dm;

	public static int imageCol = 3;

	static List<Bitmap> mThumbIdst;

	static ArrayList<String> murl;

	private String str;

	int row = 30;

	ArrayList<String> mlist;

	public ImageLoader imageLoader;

	private int mytypeid;;

	private StaggeredAdapter mAdapter = null;

	private ImageFetcher mImageFetcher;

	private ArrayList<Integer> height;

	private ArrayList<String> text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// View v = LayoutInflater.from(PicActivity.this).inflate(
		// R.layout.pic_main, null);
		// addContext(v);
		// Threadthum.start();
		dm = new DisplayMetrics();
		
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		
		Intent it = this.getIntent();

		mytypeid = it.getIntExtra("mytypeid", 0);

		Button jinm_title = (Button) findViewById(R.id.topic_title);

		XListView mylistview = new XListView(this);

		mylistview.setId(123);

		mylistview.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));// 大小设置

		addContext(mylistview);

		mAdapterView = (XListView) findViewById(123);

		mAdapterView.setPullLoadEnable(true);

		mAdapterView.setXListViewListener(this);

		mImageFetcher = new ImageFetcher(this, 100);

		mImageFetcher.setLoadingImage(R.drawable.empty_photo);

		jinm_title.setVisibility(8);

		initView();

		// InitLayout();
	}

	public class StaggeredAdapter extends BaseAdapter {
		private Context mContext;

		private XListView mListView;
		ArrayList<String> url;
		ArrayList<Integer> height;
		ArrayList<String> text;

		public StaggeredAdapter(Context context, XListView xListView,
				ArrayList<String> url, ArrayList<Integer> height,
				ArrayList<String> text) {

			mContext = context;

			this.url = url;

			this.height = height;

			this.text = text;

			mListView = xListView;

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder;
			if (convertView == null) {
				LayoutInflater layoutInflator = LayoutInflater.from(parent
						.getContext());
				convertView = layoutInflator.inflate(R.layout.infos_list, null);
				holder = new ViewHolder();
				holder.imageView = (ScaleImageView) convertView
						.findViewById(R.id.news_pic);
				holder.contentView = (TextView) convertView
						.findViewById(R.id.news_title);
				convertView.setTag(holder);
			}

			holder = (ViewHolder) convertView.getTag();

			holder.imageView.setImageWidth(200);

			holder.imageView.setImageHeight(height.get(position));

			holder.contentView.setText(text.get(position));

			mImageFetcher.loadImage(url.get(position), holder.imageView);

			return convertView;
		}

		class ViewHolder {
			ScaleImageView imageView;
			TextView contentView;
			TextView timeView;
		}

		@Override
		public int getCount() {
			return url.size();
		}

		@Override
		public Object getItem(int arg0) {
			return arg0;
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		public void addItemLast(List<DuitangInfo> datas) {
			// mInfos.addAll(datas);
		}

		public void addItemTop(List<DuitangInfo> datas) {
			for (DuitangInfo info : datas) {
				// mInfos.addFirst(info);
			}
		}
	}

	// 线程
	private void initView() {

		str = String.format(AppConstant.PRODUCT_PIC_URL, AppConstant.SAME_TAG,
				mytypeid);
		new RequestData(this, listeners, str);
	}

	// 图片列表
	OnTaskUpdateListener listeners = new OnTaskUpdateListener() {

		@Override
		public void getDate(Object obj, String msg) {
			// TODO Auto-generated method stub

			if (obj != null) {
				if (obj instanceof String) {

					String obj1 = (String) obj;

					try {
						JSONArray jsonarray = new JSONArray(obj1);
						murl = new ArrayList<String>();
						height = new ArrayList<Integer>();
						text = new ArrayList<String>();
						for (int i = 0; i < jsonarray.length(); i++) {

							JSONObject object = (JSONObject) jsonarray.get(i);

							murl.add(object.getString("thumb"));

							height.add(object.getInt("height"));

							text.add(object.getString("title"));
							// Log.i("图片出来 ", "" + obj1);

						}

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// Log.i("图片出来url ", "" + murl.get(0));
					Message m = handler.obtainMessage();
					Bundle bun = new Bundle();
					bun.putStringArrayList("url", murl);
					bun.putStringArrayList("text", text);
					bun.putIntegerArrayList("height", height);
					 Log.i("图片出来url ", "" + murl.size());
					 m.setData(bun);
					m.sendToTarget();

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

			Bundle bun = msg.getData();

			final ArrayList<String> url = bun.getStringArrayList("url");
			 Log.i("图片出来url1 ", "" + url.size());
			ArrayList<String> text = bun.getStringArrayList("text");

			ArrayList<Integer> height = bun.getIntegerArrayList("height");

			mAdapter = new StaggeredAdapter(PicActivity.this, mAdapterView,url,height,text);

			mAdapterView.setAdapter(mAdapter);

			mAdapterView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(PLA_AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub

					if (url != null && url.size() > 0) {
						Intent it = new Intent();
						it.setClass(PicActivity.this, GalleryActivity.class);
						it.putExtra("position", position - 1);
						it.putStringArrayListExtra("url", url);
						startActivity(it);
					}
				}
			});

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

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub

	}
}
