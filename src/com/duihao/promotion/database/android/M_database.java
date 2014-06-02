package com.duihao.promotion.database.android;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class M_database extends SQLiteOpenHelper {

	public M_database(Context context) {
		super(context, "duihao.db", null, 5);
	}

	public M_database(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			// 两张表便于存储数据
			// 已订阅
			StringBuffer cate = new StringBuffer();
			cate.append("CREATE TABLE " + "cate" + " ("

			+ "mytypeid" + " INTEGER PRIMARY KEY," + "name" + " text,"
					+ "model" + " text," + "type" + " text," + "modelid"
					+ " text," + "parentid" + " text);");
			db.execSQL(cate.toString());
			// 緩存新闻
			StringBuffer content = new StringBuffer();
			content.append("CREATE TABLE " + "content" + " ("

			+ "id" + " INTEGER PRIMARY KEY," + "title" + " text,"
					+ "description" + " text," + "thumb" + " text," + "catid"
					+ "  INTEGER ," + "addtime" + " text," + "parentid"
					+ " INTEGER," + "bitmap" + " BLOB," + "edittime"
					+ " text);"

			);
			db.execSQL(content.toString());

			// "info"内容，"intn"内容名

			StringBuffer sql = new StringBuffer();
			sql.append("CREATE TABLE " + "webpage" + " (" + "_id"
					+ " INTEGER PRIMARY KEY autoincrement," + " inid "
					+ " text," + " intn " + " text, " + " info " + " text);");
			db.execSQL(sql.toString());

			// 全部可订阅
			StringBuffer sql3 = new StringBuffer();
			sql3.append("CREATE TABLE " + "titleall" + " (" + "mytypeid"
					+ " INTEGER PRIMARY KEY," + "name" + " text," + "model"
					+ " text," + "type" + " text," + "modelid" + " text,"
					+ "parentid" + " text);");
			db.execSQL(sql3.toString());

			// 已发消息
			StringBuffer sql4 = new StringBuffer();
			sql4.append("CREATE TABLE " + "message" + " (" + "_id"
					+ " INTEGER PRIMARY KEY autoincrement," + "name" + " text,"
					+ "messae" + " text," + "time" + " text);");
			db.execSQL(sql4.toString());

			// 接受消息
			StringBuffer sql5 = new StringBuffer();
			sql5.append("CREATE TABLE " + "getmessage" + " (" + "_id"
					+ " INTEGER PRIMARY KEY ," + "name" + " text," + "pid"
					+ " text," + "messae" + " text," + "time" + " text);");
			db.execSQL(sql5.toString());

		} catch (Exception e) {

		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + "cate");
		onCreate(db);

	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		super.onOpen(db);
	}

}
