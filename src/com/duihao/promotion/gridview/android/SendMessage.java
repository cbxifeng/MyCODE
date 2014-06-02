package com.duihao.promotion.gridview.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.duihao.promotion.database.android.M_database;
import com.duihao.promotion.http.android.Member_info;
import com.duihao.promotion.main.android.R;

public class SendMessage extends Activity {
	AutoCompleteTextView sendedit;
	EditText sendmessedit;
	Button sendnutton;
	Button missbutton;
	private String strResult;
	static String[] COUNTRIES = { "qq.com", "163.com", "126.com", "souhu.com",
			"yahoo.com", };
	// �������ݿ�
	M_database database;

	SQLiteDatabase db;
	private String na;
	private int f;
	thresdTui thread;

	private String userId;
	private String userName;

	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// �������뷨
		InputMethodManager imm = (InputMethodManager) getApplicationContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		// ��ʾ�����������뷨
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		setContentView(R.layout.sendmessage);
		// ��ô�����������
		intent = this.getIntent();
		na = intent.getStringExtra("name");
		// Log.i("���ܵ��Ļظ�������", na);
		f = intent.getIntExtra("f", 0);

		// Intent intent = this.getIntent();
		// na = intent.getStringExtra("name");
		// f = intent.getIntExtra("f", 0);

		database = new M_database(SendMessage.this, "duihao.db", null, 5);

		db = database.getWritableDatabase();

		sendedit = (AutoCompleteTextView) findViewById(R.id.sendedit);

		if (f == 1) {

			sendedit.setText(na.toString());

		}

		sendmessedit = (EditText) findViewById(R.id.sendmessedit);

		sendnutton = (Button) findViewById(R.id.sendnutton);

		missbutton = (Button) findViewById(R.id.missbutton);

		sendedit.setThreshold(1);
		final MyAdapter adapter = new MyAdapter(this);
		sendedit.setAdapter(adapter);
		sendedit.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String input = s.toString();
				// �������ַ����һ���ǡ�@����ʱ�����ƥ���б�
				if (input.length() > 0) {
					if (input.substring(input.length() - 1, input.length())
							.equals("@")) {
						// ƥ��ɹ���ˢ���б�
						adapter.mList.clear();
						for (int i = 0; i < COUNTRIES.length; i++) {
							adapter.mList.add(input + COUNTRIES[i]);
						}
					}
				} else {
					adapter.mList.clear();
				}
				adapter.notifyDataSetChanged();
				sendedit.showDropDown();

			}
		});

		// ����
		sendnutton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				try {
					if (sendedit.getText().toString().trim().length() == 0) {
						Toast.makeText(SendMessage.this, "������Ժ��˺�",
								Toast.LENGTH_SHORT).show();
					} else {

						isLogin();

						// sendedit.setText(" ");
						// sendmessedit.setText(" ");
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		// ȡ��
		missbutton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent();
				it.setClass(SendMessage.this, MyMessage.class);
				startActivity(it);
			}
		});

		super.onCreate(savedInstanceState);
	}

	// �ж��Ƿ��½
	protected boolean isLogin() {
		SharedPreferences sp = getSharedPreferences("microblog_user",
				MODE_PRIVATE);
		userName = sp.getString("userName", "");
		userId = sp.getString("userId", "");

		Log.i("userId���� ", " " + userId.length());

		if (userName.equals("")) {
			AlertDialog loginalert = new AlertDialog.Builder(SendMessage.this)
					.setTitle("                                    ���ȵ�¼")
					.setMessage("           " + "����δ��¼���޷�������������")
					.setPositiveButton("ȷ��",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									Intent intent = new Intent();

									// intent.setClass(Web_View.this,
									// DiscussActivity.class);

									intent.setClass(SendMessage.this,
											Member_info.class);
									intent.putExtra("fs", 1);
									startActivityForResult(intent, 1);
									SendMessage.this.finish();

								}
							}).create();
			loginalert.show();
			return false;
		} else {

			// �߳�����

			thread = new thresdTui();
			thread.start();
		}
		return true;

	}

	// ������
	@SuppressWarnings("unused")
	private String HttpPostClientp(String content, String tousername,
			String postdo, String userid, String username) throws Exception {

		HttpPost httpRequest = new HttpPost("�ӿ�");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		Log.i("fde zhi ", " " + f);
		// if(f==1){
		// params.add(new BasicNameValuePair("pid", pid));
		// }
		params.add(new BasicNameValuePair("content", content));// ��������

		params.add(new BasicNameValuePair("tousername", tousername));// �û���

		params.add(new BasicNameValuePair("username", username));// �û���

		params.add(new BasicNameValuePair("do", postdo));

		params.add(new BasicNameValuePair("userid", userid));

		try {
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			Log.i("run", "  sssssssssssssssssssss");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/* �������󲢵ȴ���Ӧ */
		HttpResponse httpResponse = null;
		try {
			httpResponse = new DefaultHttpClient().execute(httpRequest);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/* ��״̬��Ϊ200 ok */
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			try {
				// strResult = EntityUtils.toString(httpResponse
				// .getEntity());
				HttpEntity entity = httpResponse.getEntity();

				InputStream contents = entity.getContent();

				strResult = convertStreamToString(contents);

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return strResult;

	}

	class thresdTui extends Thread {
		String s;

		@Override
		public void run() {

			try {

				String toname = sendedit.getText().toString();
				s = HttpPostClientp(sendmessedit.getText().toString(), toname,
						"1", userId, userName);
				Log.i("s etde zhi", "zhes" + s);
				Log.i("userId etde zhi", "   " + userId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Message msg = new Message();
			msg.obj = s;
			msg.what = 1;
			han.sendMessage(msg);
			super.run();
		}

	};

	Handler han = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				String get = (String) msg.obj;
				Log.i("post����ֵ", "  " + get);

				Log.i("post����ֵ", "���� " + get.length());

				Log.i("neirong", sendmessedit.getText().toString());

				Log.i("mingzi", sendedit.getText().toString());

				char[] ch = get.toCharArray();
				Log.i("����", " ch1��ֵ" + ch[0]);
				Log.i("����", " ch2��ֵ" + ch[1]);
				if (ch[1] == '1') {

					db = database.getWritableDatabase();

					ContentValues cv = new ContentValues();

					Cursor cursor = db.rawQuery(
							"SELECT name,messae,time  FROM message", null);

					cv.put("name", sendedit.getText().toString());

					cv.put("messae", sendmessedit.getText().toString());

					cv.put("time", getStringDateShort());

					db.insert("message", "null", cv);

					db.close();
					Toast.makeText(SendMessage.this, "���ͳɹ�", Toast.LENGTH_SHORT)
							.show();
					Intent it = new Intent();
					it.setClass(SendMessage.this, MyMessage.class);
					startActivity(it);

				} else {
					AlertDialog dlg = new AlertDialog.Builder(SendMessage.this)
							.setTitle("����ʧ�ܣ�����������Ƿ���ȷ��")
							.setMessage("����������...")
							.setNegativeButton("�ر�",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method
											// stub

										}
									}).create();
					dlg.show();
				}
			}
			super.handleMessage(msg);
		}

	};

	@Override
	protected void onPause() {
		SendMessage.this.finish();
		super.onPause();
	}

	// post�����ȡ��
	private String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));

		StringBuilder sb = new StringBuilder();

		String line = null;

		try {

			while ((line = reader.readLine()) != null) {

				sb.append(line);

			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				is.close();

			} catch (IOException e) {

				e.printStackTrace();

			}

		}

		return sb.toString();

	}

	class MyAdapter extends BaseAdapter implements Filterable {
		List mList;
		private Context mContext;
		private MyFilter mFilter;

		// public MyAdapter(SendMessage sendMessage) {
		// // TODO Auto-generated constructor stub
		// }

		public MyAdapter(Context context) {
			mContext = context;
			mList = new ArrayList();
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mList == null ? 0 : mList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mList == null ? null : mList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if (convertView == null) {
				TextView tv = new TextView(mContext);
				tv.setTextColor(Color.BLUE);
				tv.setTextSize(20);
				convertView = tv;
			}
			TextView txt = (TextView) convertView;
			txt.setText(mList.get(position).toString());
			return txt;
		}

		@Override
		public Filter getFilter() {
			// TODO Auto-generated method stub
			if (mFilter == null) {
				mFilter = new MyFilter();
			}
			return mFilter;
		}

		// �Զ�����
		private class MyFilter extends Filter {

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				// TODO Auto-generated method stub
				FilterResults results = new FilterResults();
				if (mList == null) {
					mList = new ArrayList();
				}
				results.values = mList;
				results.count = mList.size();

				return results;
			}

			@Override
			protected void publishResults(CharSequence constraint,
					FilterResults results) {
				// TODO Auto-generated method stub
				if (results.count > 0) {
					notifyDataSetChanged();
				} else {
					notifyDataSetInvalidated();
				}
			}

		}

	}

	// ���ʱ��
	public static String getStringDateShort() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		return dateString;
	}
}
