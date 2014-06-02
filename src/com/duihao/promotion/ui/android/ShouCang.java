package com.duihao.promotion.ui.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.duihao.promotion.database.android.M_database;
import com.duihao.promotion.main.android.R;
import com.duihao.promotion.service.android.MyApplication;

public class ShouCang extends Activity {
	M_database sql;
	String url, m_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shoucang);
		// 退出是调用此方法 直接退出应用
		MyApplication.getInstance().addActivity(this);
		sql = new M_database(this);
		Intent intent = this.getIntent();
		url = intent.getStringExtra("url");
		m_title = intent.getStringExtra("title");

	}

	private class clockof implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			ShouCang.this.finish();
		}

	}

	private class clock implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			SQLiteDatabase date = sql.getWritableDatabase();
			ContentValues value = new ContentValues();

			value.put("intn", url);
			value.put("info", m_title);
			long rowId = date.insert("webpage", null, value);
			if (rowId < 0) {
				AlertDialog dlg = new AlertDialog.Builder(ShouCang.this)
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
				Toast.makeText(ShouCang.this, "添加成功!", Toast.LENGTH_LONG)
						.show();
				Intent it = new Intent(ShouCang.this, CollectList.class);
				startActivity(it);
			}

		}

	}

}
