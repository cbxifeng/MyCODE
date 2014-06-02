package com.duihao.promotion.ui.android;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.duihao.promotion.http.android.AddTopHttpPost;
import com.duihao.promotion.javavbean.android.AppConstant;
import com.duihao.promotion.main.android.R;
import com.duihao.promotion.service.android.MyApplication;

public class Add_Topic extends Activity {

	Button back, commit;
	ImageView imv, imv1;
	String pic_name;
	String img_url;
	String actionUrl = AppConstant.CIRCLE_COMMIT;
	Map<String, String> params = new HashMap<String, String>();
	Map<String, File> files = new HashMap<String, File>();
	// �Ƿ��ϴ��ɹ�
	Boolean m_boolean;
	String imageFilePath;
	protected EditText et_title;
	protected EditText et_content;
	protected EditText username;
	private String title;
	private String content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_topic);

		username = (EditText) findViewById(R.id.addusername);

		et_title = (EditText) findViewById(R.id.title);

		et_content = (EditText) findViewById(R.id.content);
		

		 content = et_content.getText().toString();

		 title = et_title.getText().toString();
		// �˳��ǵ��ô˷��� ֱ���˳�Ӧ��
		MyApplication.getInstance().addActivity(this);

		// ȡ����ӻ��� �����ϸ�����
		back = (Button) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Add_Topic.this.finish();
			}
		});
		// �ύ��ӻ��� ��ӳɹ������б����
		commit = (Button) findViewById(R.id.commit);

		commit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				params.put("userid", "14");
				params.put("username", "test");
				params.put("title", title);
				params.put("content", content);

				Log.i("ͼƬ��ַaddpic", ":" + img_url);
				if (img_url != null) {
					files.put("file", new File(img_url));
				}
				// �����߳�
				try {

					//new thread().start();
					 new ud_thread().start();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		imv = (ImageView) findViewById(R.id.thumb);
		imv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				create_dialog("imv1");
			}
		});

	}

	// ����������Ի���ķ���
	private void create_dialog(String pic) {
		// ���ͼƬ��ַ
		pic_name = pic;
		AlertDialog.Builder builder = new Builder(Add_Topic.this);
		builder.setMessage("��ѡ���ϴ���ʽ��");
		builder.setTitle("�ϴ�ͼƬ");
		builder.setPositiveButton("�����ϴ�",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

						get_native_updownload();

					}
				});

		builder.setNegativeButton("�����ϴ�",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

						get_photo_updownload();

					}
				});
		builder.create().show();

	}

	// ����һ���������ֻ��ϴ�
	private void get_native_updownload() {
		Intent intent = new Intent();
		// ACTION_GET_CONTENT���ַ����������ó������û�ѡ���ض����͵�����
		intent.setAction(Intent.ACTION_GET_CONTENT);
		// intent.setAction(Intent.ACTION_VIEW);
		intent.setType("image/*");
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		// 2Ϊ�����ʾ��
		startActivityForResult(intent, 2);
		// ���ݲ�ͬ�����ֵ

	}

	// ����һ�������������ϴ�

	private void get_photo_updownload() {

		imageFilePath = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/DCIM/Camera/" + pic_name + ".jpg";

		// ����URI��ַ
		File imageFile = new File(imageFilePath);
		Uri imageFileUri = Uri.fromFile(imageFile);
		Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		i.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageFileUri);
		startActivityForResult(i, 1);   
		img_url = imageFilePath;
	}

	// �߳�2
	class ud_thread extends Thread {
		public void run() {
			// TODO Auto-generated method stub

			try {
				//m_boolean = HttpFileUpTool.post(actionUrl, params, files.get("file"));
				Log.i("img_url", ""+img_url);
				String str=AddTopHttpPost.post(actionUrl, "test", img_url);
				
				char[] ch=str.toCharArray();
				
				Log.i("str", ""+ch[1]);
				Log.i("str", ""+str.trim().length());
				if (ch[1]=='1') {
					Message message = new Message();
					message.what = 1;
					m_handler1.sendMessage(message);
				} else {
					Message message = new Message();
					message.what = 2;
					m_handler1.sendMessage(message);

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	Handler m_handler1 = new Handler() {
		public void handleMessage(Message msg) {

			if (msg.what == 1) {

				Toast.makeText(Add_Topic.this, "�ϴ��ɹ�", Toast.LENGTH_SHORT)
						.show();
				Intent intent = new Intent(Add_Topic.this, Circle.class);
				startActivity(intent);
			}
			if (msg.what == 2) {
				Toast.makeText(Add_Topic.this, "�ϴ�ʧ��", Toast.LENGTH_SHORT)
						.show();
			}

			super.handleMessage(msg);
		}
	};
	

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {

		// TODO Auto-generated method stub
		// ��С�ֱ��ʼ����ֻ��ڴ�ѹ��
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 4;
		super.onActivityResult(requestCode, resultCode, intent);

		// ������ճɹ�

		if (resultCode == RESULT_OK && requestCode == 1) {
			// ���ͼƬ��uri�ĵ�ַ

			Bitmap bmp = BitmapFactory.decodeFile(imageFilePath, options);
			imv.setImageBitmap(bmp);
			/*
			 * if (!bmp.isRecycled()) { bmp.recycle(); // ����ͼƬ��ռ���ڴ� System.gc();
			 * // ����ϵͳ��ʱ���� }
			 */
		}
		// �ϴ��ɹ�
		if (resultCode == RESULT_OK && requestCode == 2) {

			Uri uri = intent.getData();
			// ��uri��ַת����ͼƬ·��
			String[] imgs = { MediaStore.Images.Media.DATA };
			Cursor cursor = this.managedQuery(uri, imgs, null, null, null);
			int index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			img_url = cursor.getString(index);
			Log.d("Infor", "img_url:" + img_url);
			// ����ַ��ֵ
			ContentResolver contentResolver = this.getContentResolver();
			try {
				Bitmap bitmap = BitmapFactory.decodeStream(
						contentResolver.openInputStream(uri), null, options);

				/* ��Bitmap�趨��ImageView */
				imv.setImageBitmap(bitmap);

				/*
				 * if (!bitmap.isRecycled()) { bitmap.recycle(); // ����ͼƬ��ռ���ڴ�
				 * System.gc(); // ����ϵͳ��ʱ���� }
				 */} catch (FileNotFoundException e) {
				Log.e("Exception", e.getMessage(), e);
			}

		}

	}

}
