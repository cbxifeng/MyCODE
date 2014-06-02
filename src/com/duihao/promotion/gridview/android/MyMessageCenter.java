package com.duihao.promotion.gridview.android;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.duihao.promotion.database.android.M_database;
import com.duihao.promotion.main.android.R;

public class MyMessageCenter extends Activity {
	ListView messlist;
	// �������ݿ�
			com.duihao.promotion.database.android.M_database database;

			SQLiteDatabase db;
			
			Cursor cursor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.received);
		
		database = new M_database(MyMessageCenter.this, "duihao.db", null, 5);

		
		
		messlist = (ListView) findViewById(R.id.messlist);
		
		
		 
		
		// ����¼�
		messlist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				cursor.moveToPosition(position);

				final long selectId = cursor.getInt(cursor
						.getColumnIndex("_id"));
				AlertDialog dlg = new AlertDialog.Builder(MyMessageCenter.this)

						.setTitle("                          " + "�ѷ���Ϣ")
						.setNegativeButton("�鿴",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated
										// method
										// stub
										String name=cursor.getString(cursor
												.getColumnIndex("name"));
										
										String content=cursor.getString(cursor
												.getColumnIndex("messae"));
										Intent it=new Intent();
										it.putExtra("name", name);
										it.putExtra("content", content);
										it.setClass(MyMessageCenter.this, LookMyMessae.class);
										startActivity(it);
									}
								}).setPositiveButton("ɾ��", new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								
								 db.delete("message", "_id" + "=" + selectId,
											 null);
								 FindAll();
							}
							
						}).create();
				
				Window dialogWindow = dlg.getWindow();
				WindowManager.LayoutParams lp = dialogWindow.getAttributes();
				dialogWindow.setGravity(Gravity.LEFT | Gravity.CENTER);
				lp.x = 100; // ��λ��X����
				lp.y = 100; // ��λ��Y����
				lp.width = 100; // ���
				lp.height = 300; // �߶�
				// lp.alpha = 0.7f; // ͸����
				dialogWindow.setAttributes(lp);
				dlg.show();
				 
			}
			
		});
		FindAll();
		super.onCreate(savedInstanceState);
	}

private void FindAll(){
	
	db = database.getWritableDatabase();
	 cursor = db.rawQuery(
			 "SELECT _id,name,messae,time FROM message "
			, null);
	String[] col = new String[] { "name",
			"time", "messae" };
	SimpleCursorAdapter sca = new SimpleCursorAdapter(this, R.layout.getmessage,
			cursor, col, new int[] { R.id.gettext, R.id.gettime,
					R.id.getcontent });
	messlist.setAdapter(sca);
}
	// ���ʱ��
	public static String getStringDateShort() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

}
