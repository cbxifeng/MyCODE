package com.duihao.promotion.http.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.duihao.promotion.database.android.M_database;
import com.duihao.promotion.main.android.R;
import com.duihao.promotion.service.android.MyApplication;
import com.duihao.promotion.ui.android.CollectList;


public class NewsAdd extends Activity {
	Boolean BLO = false;
	
	M_database sql;
	private int in;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.addmain);
		super.onCreate(savedInstanceState);
		MyApplication.getInstance().addActivity(this);


		sql = new M_database(this);
		Intent intent = this.getIntent();
		String url = intent.getStringExtra("url");
		Log.i("4444444", "d" + url);
		String m_title = intent.getStringExtra("title");
		in=intent.getIntExtra("in", 0);
		
		
		SQLiteDatabase date = sql.getWritableDatabase();
		ContentValues value = new ContentValues();
		value.put("inid", in);
		value.put("intn", m_title.toString());
		value.put("info", url.toString());
		
		long rowId = date.insert("webpage", null, value);
		if (rowId < 0) {
			AlertDialog dlg = new AlertDialog.Builder(NewsAdd.this)
					.setTitle("增加失败！")
					.setIcon(R.drawable.ic_launcher)
					.setNegativeButton("关闭",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
								}
							}).create();
			dlg.show();
		} else {
			Toast.makeText(NewsAdd.this, "添加成功！", Toast.LENGTH_LONG).show();
			Intent it = new Intent(NewsAdd.this, CollectList.class);
			startActivity(it);
		
		this.finish();
	}

	}
}
