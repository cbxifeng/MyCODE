/**
 * 
 */
package com.duihao.promotion.gridview.android;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.duihao.promotion.database.android.M_database;
import com.duihao.promotion.main.android.R;
import com.duihao.promotion.service.android.MyApplication;
import com.duihao.promotion.ui.android.Main;

/**
 * @author Administrator
 * 
 */
public class TitliMove extends Activity {
	private Button button;
	private Cursor cursor;
	private Cursor cursor1;
	private M_database date;
	long rowId;
	SQLiteDatabase db;
	ListView alllist;
	ListView checklist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.content_custommizable);
		MyApplication.getInstance().addActivity(this);
		super.onCreate(savedInstanceState);
		date = new M_database(this);
		checklist = (ListView) findViewById(R.id.check);
		alllist = (ListView) findViewById(R.id.alist);
		FindAll();

		checklist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				cursor.moveToPosition(position);

				final int selectId = cursor.getInt(cursor
						.getColumnIndex("catid"));
				Log.i("selectid", "" + selectId);
				AlertDialog dig = new AlertDialog.Builder(TitliMove.this)
						.setTitle("取消订阅")
						.setPositiveButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub

									}

								})
						.setNegativeButton("確定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										SQLiteDatabase db = date
												.getWritableDatabase();
										ContentValues values = new ContentValues();
										int catid = cursor.getInt(cursor
												.getColumnIndex("catid"));
										String catname = cursor.getString(cursor
												.getColumnIndex("catname"));
										values.put("catid", catid);
										values.put("catname", catname);
										Log.i("数据库value：",
												"" + values.toString());

										// 刪除
										if (selectId != 11 && selectId != 12) {
											long rowIds = db.insert("titleall",
													null, values);
											long rowId = db.delete("cate",
													"catid" + " = " + selectId,
													null);
										}

										Log.i("hhhhhhhhhhh",
												String.valueOf(rowId));
										if (rowId < 0) {
											Toast.makeText(TitliMove.this,
													"删除失败！", Toast.LENGTH_SHORT)
													.show();
										}

										FindAll();

									}
								}).show();

			}

		});

		alllist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				cursor1.moveToPosition(position);

				final int selectId = cursor1.getInt(cursor1
						.getColumnIndex("catid"));
				Log.i("selectid", "" + selectId);
				AlertDialog dig = new AlertDialog.Builder(TitliMove.this)
						.setTitle("确认订阅")
						.setPositiveButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub

									}

								})
						.setNegativeButton("確定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										SQLiteDatabase db = date
												.getWritableDatabase();

										// 刪除
										ContentValues values = new ContentValues();
										int catid = cursor1.getInt(cursor1
												.getColumnIndex("catid"));
										String catname = cursor1.getString(cursor1
												.getColumnIndex("catname"));
										values.put("catid", catid);
										values.put("catname", catname);
										Log.i("数据库value：",
												"" + values.toString());

										// 刪除
										if (selectId != 11) {
											long rowIds = db.insert("cate",
													null, values);

											long rowId = db.delete("titleall",
													"catid" + " = " + selectId,
													null);
										}
										Log.i("hhhhhhhhhhh",
												String.valueOf(rowId));
										if (rowId < 0) {
											Toast.makeText(TitliMove.this,
													"删除失败！", Toast.LENGTH_SHORT)
													.show();
										}

										FindAll();

									}
								}).show();

			}
		});

		button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent();
				it.setClass(TitliMove.this, Main.class);
				startActivity(it);
				TitliMove.this.finish();
			}
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void FindAll() {
		SQLiteDatabase db = date.getReadableDatabase();
		ArrayList<Object> arr = new ArrayList<Object>();
		cursor = db.rawQuery("SELECT catname,catid FROM cate ", null);
		int j = 0;
		Log.i("数据库daxiao：", "" + cursor.getCount());
		while (cursor.moveToNext()) {
			String titles[] = new String[cursor.getCount()];
			titles[j] = cursor.getString(cursor.getColumnIndex("catname"));
			arr.add(titles[j]);
			Log.i("数据库值：", "" + titles[j]);
			j++;
		}

		ArrayAdapter<String> adapter = new ArrayAdapter(TitliMove.this,
				android.R.layout.simple_expandable_list_item_1, arr);

		checklist.setAdapter(adapter);
		ArrayList<Object> arr1 = new ArrayList<Object>();
		cursor1 = db.rawQuery("SELECT catname,catid FROM titleall", null);
		int i = 0;
		Log.i("数据库1111：", "" + cursor1.getCount());
		while (cursor1.moveToNext()) {
			String titles[] = new String[cursor1.getCount()];
			titles[i] = cursor1.getString(cursor1.getColumnIndex("catname"));
			arr1.add(titles[i]);
			Log.i("数据库值111：", "" + titles[i]);
			i++;
		}

		ArrayAdapter<String> adapter1 = new ArrayAdapter(TitliMove.this,
				android.R.layout.simple_expandable_list_item_1, arr1);

		alllist.setAdapter(adapter1);

	}

}
