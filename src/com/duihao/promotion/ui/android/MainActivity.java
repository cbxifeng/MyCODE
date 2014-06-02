package com.duihao.promotion.ui.android;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.R.color;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.Toast;

import com.duihao.promotion.database.android.M_database;
import com.duihao.promotion.frame.android.OnTaskUpdateListener;
import com.duihao.promotion.frame.android.RequestData;
import com.duihao.promotion.http.android.HttpUrl;
import com.duihao.promotion.http.android.NetWorkingCheck;
import com.duihao.promotion.http.android.Web_View;
import com.duihao.promotion.javavbean.android.AppConstant;
import com.duihao.promotion.javavbean.android.NewsListInfo;
import com.duihao.promotion.javavbean.android.Title;
import com.duihao.promotion.javavbean.android.TuiJianPicBean;
import com.duihao.promotion.main.android.R;
import com.duihao.promotion.service.android.MyApplication;
import com.duihao.promotion.ui.android.MyListView.OnRefreshListener;
import com.fedorvlasov.lazylist.LazyAdapter;

public class MainActivity extends AppActivity {

	LazyAdapter adapter;

	HttpUrl httpurl;

	Drawable m_draw1, m_draw2, m_draw3, m_draw4;

	private Gallery mGallery;

	private MyGallery pictureGallery = null;

	String catids;

	Handler han;
	String str;// �б����

	List<Title> m_titles = new ArrayList<Title>();

	ArrayList<NewsListInfo> m_newslistinfo;

	String[] news_list = new String[20];
	int[] catid = new int[20];

	// ͼƬ
	private Bitmap bitmap;

	// ���]ͼƬ
	private Bitmap tuibitmap;

	// �������ݿ�
	M_database database;

	SQLiteDatabase db;

	NewsListInfo m_list;

	int typeid;

	public static int[] m_webview_id = new int[20];

	public static int[] m_webview_catid = new int[20];

	// ���Ʊ�������ı���
	public int j = 0;

	// Ĭ��ֵ��ָ��Ĭ����ʾ�Ǹ������б�
	public static List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

	private int rows = 10;
	int[] menu_image_array = { R.drawable.sc, R.drawable.zxtg, R.drawable.dy,
			R.drawable.wdsc, R.drawable.wdtg, R.drawable.wddy, R.drawable.wdxx,
			R.drawable.yhzx, R.drawable.tc, };

	String[] menu_name_array = { "�����ղ�", "���߶���", "��ͼ", "�ҵĶ���", "�ҵ�Ͷ��", "�ҵ���Ϣ",
			"��������", "�û�����", "�˳�" };

	AlertDialog menuDialog;

	private View menuView;// GridView����ͼ

	GridView menuGrid;

	NetworkInfo info;// �������

	MyListView listview;

	TuiJianPicBean m_listpic = new TuiJianPicBean();

	private String strt;// �Ƽ�λ

	Intent it;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		// View v = LayoutInflater.from(MainActivity.this).inflate(
		// R.layout.activity_main, null);
		// addContext(v);
		// setContentView(R.layout.activity_main);

		it = this.getIntent();

		typeid = it.getIntExtra("typeid", 0);

		Log.i("���¸�ֵ��ֵMac��", "" + typeid);

		initView();

		database = new M_database(MainActivity.this, "duihao.db", null, 5);

		db = database.getWritableDatabase();
		MyListView mylistview = new MyListView(this);

		mylistview.setId(123);

		mylistview.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));// ��С����

		mylistview.setBackgroundColor(color.white);

		Drawable draw = getResources().getDrawable(R.drawable.itemline);

		mylistview.setDivider(draw);

		mylistview.setDividerHeight(2);

		addContext(mylistview);

		// image = (ImageView) findViewById(R.id.tui_m_img);
		// image.setBackgroundResource(R.drawable.p1);

		String ser = CONNECTIVITY_SERVICE;
		NetWorkingCheck netw = new NetWorkingCheck(this, ser);

		netw.chek();

		// ��Ӹ��ఴť
		listview = (MyListView) findViewById(123);
		// �Ƽ�λ
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

						new LazyAdapter(MainActivity.this, m_newslistinfo)
								.notifyDataSetChanged();

						listview.onRefreshComplete();

					}

				}.execute();
			}
		});

		// �˳�activity�ǵ���
		MyApplication.getInstance().addActivity(this);
		// ��listview��ӵײ�������

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent intent = new Intent();
				intent.putExtra("main", "m");
				intent.setClass(MainActivity.this, Web_View.class);
				//
				// intent.putExtra("catid", m_newslistinfo.get(position)
				// .getMytypeid());

				intent.putExtra("myurl", m_newslistinfo.get(position - 1)
						.getUrl());
				intent.putExtra("mytitle", m_newslistinfo.get(position - 1)
						.getTitle());
				Log.i("Main���������id", ""
						+ m_newslistinfo.get(position - 1).getId());

				startActivity(intent);

			}

		});

		// menuView = View.inflate(this, R.layout.gridview_menu, null);
		//
		// menuGrid = (GridView) menuView.findViewById(R.id.gridview);
		//
		// menuGrid.setAdapter(getMenuAdapter(menu_name_array,
		// menu_image_array));
		// /** ����menuѡ�� **/
		//
		// menuGrid.setOnItemClickListener(new OnItemClickListener() {
		// public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
		// long arg3) {
		// switch (arg2) {
		// case 0:// �����ղ�
		//
		// break;
		// case 1:// ���߶���
		//
		// break;
		// case 2:// ��ͼ
		//
		// Intent map = new Intent(MainActivity.this,
		// MapMainActivity.class);
		// startActivity(map);
		// break;
		// case 3:// �ҵĶ���
		// Intent it3 = new Intent(MainActivity.this,
		// CollectList.class);
		//
		// startActivity(it3);
		// break;
		// case 4:// �ҵ�Ͷ��
		//
		// break;
		// case 5:// �ҵ���Ϣ
		// Intent intentme = new Intent();
		//
		// intentme.setClass(MainActivity.this, MyMessage.class);
		//
		// startActivity(intentme);
		// break;
		// case 6:// ��������
		// Intent intentin = new Intent();
		//
		// intentin.setClass(MainActivity.this, TheSoftIntroduce.class);
		//
		// startActivity(intentin);
		// break;
		// case 7:// �û�����
		// // ��ת֮ǰ�ж��û��Ƿ��Ѿ���¼ ���û�� ��ת�� Member_info���棬����Ѿ���¼��ת����������
		// // Person_Center����
		//
		// Intent intent1 = new Intent();
		// intent1.setClass(MainActivity.this, Person_Center.class);
		//
		// startActivity(intent1);
		// break;
		// case 8:// �˳�
		//
		// MyApplication.getInstance().exit();
		//
		// break;
		// }
		// }
		// });

	}

	// �б�
	private void initView() {
		// �����б�

		str = String.format(AppConstant.PRODUCKT_LIST_URL, rows,
				AppConstant.SAME_TAG, typeid);

		new RequestData(this, listener, str);

	}

	// �б�
	OnTaskUpdateListener listener = new OnTaskUpdateListener() {

		@Override
		public void getDate(Object obj, String msg) {
			// TODO Auto-generated method stub

			ContentValues cv = new ContentValues();

			// �жϱ����Ƿ������� �������ֱ�ӵ��ã����û�� ����.
			// Cursor cursor = db
			// .rawQuery(
			// "SELECT title,description,addtime,bitmap,edittime,thumb,id,catid FROM content where parentid="
			// + Main.m_catid, null);

			if (obj != null) {
				if (obj instanceof String) {

					String obj1 = (String) obj;

					try {
						if(obj1!=null&&obj1.startsWith("\ufeff")){
							
							obj1=obj1.substring(1);
							
						}
						
						JSONArray jsonarray = new JSONArray(obj1);
						
						m_newslistinfo = new ArrayList<NewsListInfo>();
						
						for (int i = 0; i < jsonarray.length(); i++) {

							JSONObject object = (JSONObject) jsonarray.get(i);

							NewsListInfo m_list = new NewsListInfo(
									object.getInt("id"),
									object.getString("title"),
									object.getString("description"),
									object.getString("thumb"),
									object.getString("mytypeid"),
									object.getString("addtime"),
									object.getString("endtime"),
									object.getString("price"),
									object.getString("status"),
									object.getString("url"),
									object.getString("yuding"),
									object.getString("price_old"));
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

				ArrayList<NewsListInfo> listinfo = (ArrayList<NewsListInfo>) msg.obj;
				// Log.i("NewsListInfo", "" + listinfo.size());
				adapter = new LazyAdapter(MainActivity.this, listinfo);
				listview.setAdapter(adapter);

				break;

			case HANDLER_HTTP_REQUEST_FAIL:

				Toast.makeText(MainActivity.this, "��������ʧ��", Toast.LENGTH_SHORT)
						.show();
				break;
			default:
				break;
			}

			super.handleMessage(msg);
		}

	};

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // TODO Auto-generated method stub
	//
	// menu.add("menu");
	//
	// return super.onCreateOptionsMenu(menu);
	//
	// }
	//
	// @Override
	// public boolean onMenuOpened(int featureId, Menu menu) {
	//
	// if (menuDialog == null) {
	//
	// menuDialog = new AlertDialog.Builder(this).setView(menuView).show();
	//
	// } else {
	//
	// menuDialog.show();
	//
	// }
	// return false;// ����Ϊtrue ����ʾϵͳmenu
	// }

	// �Զ��˵���
	// private SimpleAdapter getMenuAdapter(String[] menuNameArray,
	// int[] imageResourceArray) {
	//
	// // ΪgriView�������Դ
	// ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String,
	// Object>>();
	//
	// for (int i = 0; i < menuNameArray.length; i++) {
	//
	// HashMap<String, Object> map = new HashMap<String, Object>();
	//
	// map.put("itemImage", imageResourceArray[i]);
	//
	// map.put("itemText", menuNameArray[i]);
	//
	// data.add(map);
	// }
	// // �������
	// SimpleAdapter simperAdapter = new SimpleAdapter(this, data,
	//
	// R.layout.item_menu, new String[] { "itemImage", "itemText" },
	// new int[] { R.id.item_image, R.id.item_text });
	//
	// return simperAdapter;
	// }

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
