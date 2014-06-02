package com.duihao.promotion.ui.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.color;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;

import com.duihao.promotion.database.android.M_database;
import com.duihao.promotion.http.android.HttpUrl;
import com.duihao.promotion.main.android.R;
import com.duihao.promotion.service.android.MyApplication;

public class Product extends AppActivity {

	private Button m_jx, m_yl, m_mn, m_qq, m_sy;
	public List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	MyListView pic_lists;
	M_database database;
	SQLiteDatabase db;
	Bitmap bitmap = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
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
		// 退出是调用此方法 直接退出应用
		MyApplication.getInstance().addActivity(this);
		pic_lists = (MyListView) findViewById(123);
		getList();
		setAdapter();
		// Button button = (Button) findViewById(R.id.m_title);
		// button.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// Log.i("button 点击事件", "ddddd");
		// }
		// });
		// m_jx = (Button) findViewById(R.id.m_jx);
		// m_yl = (Button) findViewById(R.id.m_yl);
		// m_mn = (Button) findViewById(R.id.m_mn);
		// m_sy = (Button) findViewById(R.id.m_sy);
		// m_qq = (Button) findViewById(R.id.m_qq);
		// m_jx.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// Log.i("点击事件", "ddd");
		//
		// m_jx.setTextColor(PicActivity.this.getResources().getColor(
		// R.color.blue));
		// m_yl.setTextColor(PicActivity.this.getResources().getColor(
		// android.R.color.black));
		// m_mn.setTextColor(PicActivity.this.getResources().getColor(
		// android.R.color.black));
		// m_qq.setTextColor(PicActivity.this.getResources().getColor(
		// android.R.color.black));
		// m_sy.setTextColor(PicActivity.this.getResources().getColor(
		// android.R.color.black));
		// m_jx.setBackgroundResource(R.drawable.bottom_press);
		// m_yl.setBackgroundResource(R.drawable.bottom_normal);
		// m_mn.setBackgroundResource(R.drawable.bottom_normal);
		// m_qq.setBackgroundResource(R.drawable.bottom_normal);
		// m_sy.setBackgroundResource(R.drawable.bottom_normal);
		//
		// }
		// });
		//
		// m_yl.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// Log.i("点击事件", "ddd2");
		// m_yl.setTextColor(PicActivity.this.getResources().getColor(
		// R.color.blue));
		// m_jx.setTextColor(PicActivity.this.getResources().getColor(
		// android.R.color.black));
		// m_mn.setTextColor(PicActivity.this.getResources().getColor(
		// android.R.color.black));
		// m_qq.setTextColor(PicActivity.this.getResources().getColor(
		// android.R.color.black));
		// m_sy.setTextColor(PicActivity.this.getResources().getColor(
		// android.R.color.black));
		// m_yl.setBackgroundResource(R.drawable.bottom_press);
		// m_jx.setBackgroundResource(R.drawable.bottom_normal);
		// m_mn.setBackgroundResource(R.drawable.bottom_normal);
		// m_qq.setBackgroundResource(R.drawable.bottom_normal);
		// m_sy.setBackgroundResource(R.drawable.bottom_normal);
		//
		// }
		// });
		//
		// m_mn.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// m_mn.setTextColor(PicActivity.this.getResources().getColor(
		// R.color.blue));
		// m_yl.setTextColor(PicActivity.this.getResources().getColor(
		// android.R.color.black));
		// m_jx.setTextColor(PicActivity.this.getResources().getColor(
		// android.R.color.black));
		// m_qq.setTextColor(PicActivity.this.getResources().getColor(
		// android.R.color.black));
		// m_sy.setTextColor(PicActivity.this.getResources().getColor(
		// android.R.color.black));
		// m_mn.setBackgroundResource(R.drawable.bottom_press);
		// m_yl.setBackgroundResource(R.drawable.bottom_normal);
		// m_jx.setBackgroundResource(R.drawable.bottom_normal);
		// m_qq.setBackgroundResource(R.drawable.bottom_normal);
		// m_sy.setBackgroundResource(R.drawable.bottom_normal);
		//
		// }
		// });
		//
		// m_qq.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// m_qq.setTextColor(PicActivity.this.getResources().getColor(
		// R.color.blue));
		// m_yl.setTextColor(PicActivity.this.getResources().getColor(
		// android.R.color.black));
		// m_mn.setTextColor(PicActivity.this.getResources().getColor(
		// android.R.color.black));
		// m_jx.setTextColor(PicActivity.this.getResources().getColor(
		// android.R.color.black));
		// m_sy.setTextColor(PicActivity.this.getResources().getColor(
		// android.R.color.black));
		// m_qq.setBackgroundResource(R.drawable.bottom_press);
		// m_yl.setBackgroundResource(R.drawable.bottom_normal);
		// m_mn.setBackgroundResource(R.drawable.bottom_normal);
		// m_jx.setBackgroundResource(R.drawable.bottom_normal);
		// m_sy.setBackgroundResource(R.drawable.bottom_normal);
		//
		// }
		// });
		//
		// m_sy.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// m_sy.setTextColor(PicActivity.this.getResources().getColor(
		// R.color.blue));
		// m_yl.setTextColor(PicActivity.this.getResources().getColor(
		// android.R.color.black));
		// m_mn.setTextColor(PicActivity.this.getResources().getColor(
		// android.R.color.black));
		// m_qq.setTextColor(PicActivity.this.getResources().getColor(
		// android.R.color.black));
		// m_jx.setTextColor(PicActivity.this.getResources().getColor(
		// android.R.color.black));
		// m_sy.setBackgroundResource(R.drawable.bottom_press);
		// m_yl.setBackgroundResource(R.drawable.bottom_normal);
		// m_mn.setBackgroundResource(R.drawable.bottom_normal);
		// m_qq.setBackgroundResource(R.drawable.bottom_normal);
		// m_jx.setBackgroundResource(R.drawable.bottom_normal);
		//
		// }
		// });
		//
	}

	// 获得listview数据源
	private void getList() {
		database = new M_database(Product.this, "duihao.db", null, 5);
		db = database.getWritableDatabase();
		Cursor cursor = db
				.rawQuery(
						"SELECT title,edittime,thumb,parentid FROM content where parentid =" + 11,
						null);
		Log.i("cursor", " 是否有值" + cursor.getCount());
		while (cursor.moveToNext()) {
			Map<String, Object> map = new HashMap<String, Object>();
			String in = cursor.getString(cursor.getColumnIndex("thumb"));
			bitmap = HttpUrl.httpUrlPic(in);
			map.put("m_img1", bitmap);
			map.put("m_img2", bitmap);
			map.put("m_img3", bitmap);
			map.put("title", cursor.getString(cursor.getColumnIndex("title")));
			map.put("edittime",
					cursor.getString(cursor.getColumnIndex("edittime")));
			list.add(map);
		}
		Log.i("99999", "list 是否有值");

	}

	// Adapter 适配数据
	private void setAdapter() {
		SimpleAdapter adapter = new SimpleAdapter(Product.this, list,
				R.layout.pic_list_item, new String[] { "title", "m_img1",
						"m_img2", "m_img3", "edittime" }, new int[] {
						R.id.m_title, R.id.m_img1, R.id.m_img2, R.id.m_img3,
						R.id.m_edittime });

		pic_lists.setAdapter(adapter);
		Log.i("添加数据", "ccc");
		//
		adapter.setViewBinder(new ViewBinder() {
			public boolean setViewValue(View view, Object data,
					String textRepresentation) {
				// 判断是否为我们要处理的对象
				if (view instanceof ImageView && data instanceof Bitmap) {
					ImageView iv = (ImageView) view;
					iv.setImageBitmap((Bitmap) data);
					return true;
				} else
					return false;
			}
		});
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
		return true;
	}
}
