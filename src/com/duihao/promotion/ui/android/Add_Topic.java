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
	// 是否上传成功
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
		// 退出是调用此方法 直接退出应用
		MyApplication.getInstance().addActivity(this);

		// 取消添加话题 返回上个界面
		back = (Button) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Add_Topic.this.finish();
			}
		});
		// 提交添加话题 添加成功返回列表界面
		commit = (Button) findViewById(R.id.commit);

		commit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				params.put("userid", "14");
				params.put("username", "test");
				params.put("title", title);
				params.put("content", content);

				Log.i("图片地址addpic", ":" + img_url);
				if (img_url != null) {
					files.put("file", new File(img_url));
				}
				// 开启线程
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

	// 定义个创建对话框的方法
	private void create_dialog(String pic) {
		// 获得图片地址
		pic_name = pic;
		AlertDialog.Builder builder = new Builder(Add_Topic.this);
		builder.setMessage("请选择上传方式：");
		builder.setTitle("上传图片");
		builder.setPositiveButton("本地上传",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

						get_native_updownload();

					}
				});

		builder.setNegativeButton("拍照上传",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

						get_photo_updownload();

					}
				});
		builder.create().show();

	}

	// 定义一个方法，手机上传
	private void get_native_updownload() {
		Intent intent = new Intent();
		// ACTION_GET_CONTENT”字符串常量，该常量让用户选择特定类型的数据
		intent.setAction(Intent.ACTION_GET_CONTENT);
		// intent.setAction(Intent.ACTION_VIEW);
		intent.setType("image/*");
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		// 2为请求标示符
		startActivityForResult(intent, 2);
		// 根据不同情况赋值

	}

	// 定义一个方法，拍照上传

	private void get_photo_updownload() {

		imageFilePath = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/DCIM/Camera/" + pic_name + ".jpg";

		// 创建URI地址
		File imageFile = new File(imageFilePath);
		Uri imageFileUri = Uri.fromFile(imageFile);
		Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		i.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageFileUri);
		startActivityForResult(i, 1);   
		img_url = imageFilePath;
	}

	// 线程2
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

				Toast.makeText(Add_Topic.this, "上传成功", Toast.LENGTH_SHORT)
						.show();
				Intent intent = new Intent(Add_Topic.this, Circle.class);
				startActivity(intent);
			}
			if (msg.what == 2) {
				Toast.makeText(Add_Topic.this, "上传失败", Toast.LENGTH_SHORT)
						.show();
			}

			super.handleMessage(msg);
		}
	};
	

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {

		// TODO Auto-generated method stub
		// 缩小分辨率减少手机内存压力
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 4;
		super.onActivityResult(requestCode, resultCode, intent);

		// 如果拍照成功

		if (resultCode == RESULT_OK && requestCode == 1) {
			// 获得图片的uri的地址

			Bitmap bmp = BitmapFactory.decodeFile(imageFilePath, options);
			imv.setImageBitmap(bmp);
			/*
			 * if (!bmp.isRecycled()) { bmp.recycle(); // 回收图片所占的内存 System.gc();
			 * // 提醒系统及时回收 }
			 */
		}
		// 上传成功
		if (resultCode == RESULT_OK && requestCode == 2) {

			Uri uri = intent.getData();
			// 将uri地址转换成图片路径
			String[] imgs = { MediaStore.Images.Media.DATA };
			Cursor cursor = this.managedQuery(uri, imgs, null, null, null);
			int index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			img_url = cursor.getString(index);
			Log.d("Infor", "img_url:" + img_url);
			// 给地址赋值
			ContentResolver contentResolver = this.getContentResolver();
			try {
				Bitmap bitmap = BitmapFactory.decodeStream(
						contentResolver.openInputStream(uri), null, options);

				/* 将Bitmap设定到ImageView */
				imv.setImageBitmap(bitmap);

				/*
				 * if (!bitmap.isRecycled()) { bitmap.recycle(); // 回收图片所占的内存
				 * System.gc(); // 提醒系统及时回收 }
				 */} catch (FileNotFoundException e) {
				Log.e("Exception", e.getMessage(), e);
			}

		}

	}

}
