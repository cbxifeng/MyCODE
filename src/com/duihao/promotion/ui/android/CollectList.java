package com.duihao.promotion.ui.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.duihao.promotion.database.android.M_database;
import com.duihao.promotion.http.android.Web_View;
import com.duihao.promotion.main.android.R;
import com.duihao.promotion.service.android.MyApplication;

public class CollectList extends Activity {
	private Cursor cursor;
	private M_database date;
	ListView list;
	long rowId;
	SQLiteDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.collectmain);
		super.onCreate(savedInstanceState);
		MyApplication.getInstance().addActivity(this);
		date = new M_database(this);

		list = (ListView) findViewById(R.id.collectlist);

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				cursor.moveToPosition(position);

				/*
				 * final long selectId = cursor.getInt(cursor
				 * .getColumnIndex(DateBaseSystem.TABLE_FIELD_ID));
				 */
				if (cursor.getInt(cursor.getColumnIndex("inid")) == 1) {

					// 设置了一个静态变量url，所以不需要传递值 直接跳转；
					Intent it = new Intent(CollectList.this,
							IndustryTrends.class);
					IndustryTrends.m_title = cursor.getString(cursor
							.getColumnIndex("intn"));
					IndustryTrends.url = cursor.getString(cursor
							.getColumnIndex("info"));
					IndustryTrends.Bon = true;
					startActivity(it);
				} else if (cursor.getInt(cursor.getColumnIndex("inid")) == 2) {

					// 设置了一个静态变量url，所以不需要传递值 直接跳转；
					Intent it = new Intent(CollectList.this, Web_View.class);
					Web_View.Bon = true;
					Web_View.title = cursor.getString(cursor
							.getColumnIndex("intn"));

					Log.i("tut", Web_View.title);

					Web_View.url = cursor.getString(cursor
							.getColumnIndex("info"));

					Log.i("ddddd", Web_View.url);

					startActivity(it);
				}

			}

		});

		list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {

				cursor.moveToPosition(position);
				final long selectId = cursor.getInt(cursor
						.getColumnIndex("_id"));
				db = date.getWritableDatabase();
				AlertDialog dig = new AlertDialog.Builder(CollectList.this)
						.setTitle("确认删除")
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
										rowId = db.delete("webpage", "_id"
												+ " = " + selectId, null);
										FindAll();
									}
								}).create();
				dig.show();

				if (rowId < 0) {
					Toast.makeText(CollectList.this, "删除失败！",
							Toast.LENGTH_SHORT).show();
				}
				return true;
			}

		});

		FindAll();
	}

	@SuppressWarnings("deprecation")
	private void FindAll() {
		SQLiteDatabase db = date.getReadableDatabase();

		String[] cuol = new String[] { "_id", "intn", "info", "inid" };
		cursor = db.query("webpage", cuol, null, null, null, null, null);
		System.out.print(cuol.toString());
		startManagingCursor(cursor);
		String[] col = new String[] { "intn" };

		SimpleCursorAdapter sca = new SimpleCursorAdapter(this,
				R.layout.collectitem, cursor, col, new int[] { R.id.title, });
		System.out.print("HHHHHHHHHHHHH" + sca.toString());
		list.setAdapter(sca);
	}
}
