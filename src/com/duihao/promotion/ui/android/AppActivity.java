package com.duihao.promotion.ui.android;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.duihao.promotion.javavbean.android.AppUtil;
import com.duihao.promotion.main.android.R;
import com.duihao.promotion.service.android.MyApplication;

//��Ϊ���࣬ͨ�õĲ��ִ�������
public abstract class AppActivity extends Activity {
	// �ײ�4�����
	private TextView toolbarProduct, toolbarDongtai, toolbarPic, toolbarCircle,
			toolbarAbaout;

	protected ImageView image;
	private Button button;
	// private String titletext;// ���ñ���ı���
	com.duihao.promotion.database.android.M_database database;
	SQLiteDatabase db;

	protected abstract boolean isShowToolBar();// �Ƿ���ʾ�ײ���ť

	protected abstract boolean isShowImage();

	protected abstract boolean isShowPicTui();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
    
		database = new com.duihao.promotion.database.android.M_database(
				AppActivity.this, "duihao.db", null, 5);
		db = database.getReadableDatabase();
       
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.titlechangemain);
		// �˳��ǵ��ô˷��� ֱ���˳�Ӧ��
		MyApplication.getInstance().addActivity(this);

		// ��Ʒ
		toolbarProduct = (TextView) findViewById(R.id.toolbarProduct);
		// ��̬
		toolbarDongtai = (TextView) findViewById(R.id.toolbarDongtai);

		// Ȧ��
		toolbarCircle = (TextView) findViewById(R.id.toolbarCenter);
		// ���
		toolbarPic = (TextView) findViewById(R.id.toolbarCircle);
		// ��ѯ
		toolbarAbaout = (TextView) findViewById(R.id.toolbarAbaout);
		setButtonToolMemu();

		// �ж��Ƿ���ʾ�ײ�4��ֱ���ѡ�кͲ�ѡʱ��������ֵ����
		if (isShowToolBar()) {
			// ��Ʒ
			toolbarProduct.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					AppUtil.setButton_flag(1);// �����͸������ȷ��ѡ��ʱ��ֵ����
					Intent product = new Intent();
					product.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					product.setClass(AppActivity.this, Main.class);
					startActivity(product);
					return false;
				}
			});
			// ��̬
			toolbarDongtai.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					AppUtil.setButton_flag(2);// �����͸������ȷ��ѡ��ʱ��ֵ����
					Intent dongtai = new Intent();
					dongtai.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					dongtai.setClass(AppActivity.this, DongTai.class);
					startActivity(dongtai);
					return false;
				}
			});
			// ���
			toolbarPic.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					AppUtil.setButton_flag(3);// �����͸������ȷ��ѡ��ʱ��ֵ����
					Intent center = new Intent();
					center.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					center.setClass(AppActivity.this, PicServiceTitle.class);
					startActivity(center);
					return false;
				}
			});
			// Ȧ��
			toolbarCircle.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					AppUtil.setButton_flag(4);// �����͸������ȷ��ѡ��ʱ��ֵ����
					Intent person_center = new Intent();
					person_center.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					person_center.setClass(AppActivity.this, Circle.class);
					startActivity(person_center);
					return false;
				}
			});
			// ��ѯ
			toolbarAbaout.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					AppUtil.setButton_flag(5);// �����͸������ȷ��ѡ��ʱ��ֵ����
					Intent about = new Intent();
					about.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					about.setClass(AppActivity.this,ZiXun.class);
					startActivity(about);
					return false;
				}
			});

		} else {
			// ���ء�8��ռλ��4ռλ��Ĭ��0�ǲ����� VISIBLE (0), INVISIBLE(4), GONE(8).
			View missview = findViewById(R.id.button_menuid);
			missview.setVisibility(8);
		}
		// �Ƽ�ͼ
		if (isShowImage()) {
			// TuiJianPicBean m_list=new TuiJianPicBean();
			// Bitmap bitmap = HttpUrl.httpUrlPic(m_list.getThumb());
			//
			// image=(ImageView)findViewById(R.id.tui_m_img);
			// image.setImageBitmap(bitmap);
		} else {
			View image1 = findViewById(R.id.picimage);
			image1.setVisibility(8);
		}

		// ��ѡͼƬ
		if (isShowPicTui()) {
			button = (Button) findViewById(R.id.topic_title);
		} else {
			View pic = findViewById(R.id.pic_main);
			pic.setVisibility(8);
		}
		super.onCreate(savedInstanceState);
	}

	// ��Ӳ���
	public void addContext(View view) {
		ViewGroup addlayoutid = (ViewGroup) findViewById(R.id.addlayoutid);
		addlayoutid.addView(view);
	}

	/**
	 * ���ñ�������
	 * 
	 * @param titletext
	 */
	// protected void setCentreText(String titletext) {
	// this.titletext = titletext;
	// }

	public void setButtonToolMemu() {
		switch (AppUtil.getButton_flag()) {
		case 1:

			toolbarProduct.setTextColor(getResources().getColor(R.color.white));
			toolbarDongtai.setTextColor(this.getResources().getColor(
					R.color.white));
			toolbarPic
					.setTextColor(this.getResources().getColor(R.color.white));
			toolbarCircle.setTextColor(this.getResources().getColor(
					R.color.white));
			toolbarAbaout.setTextColor(this.getResources().getColor(
					R.color.white));

			toolbarProduct.setBackgroundResource(R.drawable.menu_background);
			toolbarDongtai.setBackgroundResource(R.drawable.menu_background2);
			toolbarPic.setBackgroundResource(R.drawable.menu_background2);
			toolbarCircle.setBackgroundResource(R.drawable.menu_background2);
			toolbarAbaout.setBackgroundResource(R.drawable.menu_background2);
			break;
		case 2:
			toolbarProduct.setTextColor(getResources().getColor(R.color.white));
			toolbarDongtai.setTextColor(this.getResources().getColor(
					R.color.white));
			toolbarPic
					.setTextColor(this.getResources().getColor(R.color.white));
			toolbarCircle.setTextColor(this.getResources().getColor(
					R.color.white));
			toolbarAbaout.setTextColor(this.getResources().getColor(
					R.color.white));

			toolbarProduct.setBackgroundResource(R.drawable.menu_background2);
			toolbarDongtai.setBackgroundResource(R.drawable.menu_background);
			toolbarPic.setBackgroundResource(R.drawable.menu_background2);
			toolbarCircle.setBackgroundResource(R.drawable.menu_background2);
			toolbarAbaout.setBackgroundResource(R.drawable.menu_background2);
			break;
		case 3:

			toolbarProduct.setTextColor(getResources().getColor(R.color.white));
			toolbarDongtai.setTextColor(this.getResources().getColor(
					R.color.white));
			toolbarPic
					.setTextColor(this.getResources().getColor(R.color.white));
			toolbarCircle.setTextColor(this.getResources().getColor(
					R.color.white));
			toolbarAbaout.setTextColor(this.getResources().getColor(
					R.color.white));
			toolbarProduct.setBackgroundResource(R.drawable.menu_background2);
			toolbarDongtai.setBackgroundResource(R.drawable.menu_background2);
			toolbarPic.setBackgroundResource(R.drawable.menu_background);
			toolbarCircle.setBackgroundResource(R.drawable.menu_background2);
			toolbarAbaout.setBackgroundResource(R.drawable.menu_background2);
			break;
		case 4:
			toolbarProduct.setTextColor(getResources().getColor(R.color.white));
			toolbarDongtai.setTextColor(this.getResources().getColor(
					R.color.white));
			toolbarPic
					.setTextColor(this.getResources().getColor(R.color.white));
			toolbarCircle.setTextColor(this.getResources().getColor(
					R.color.white));
			toolbarAbaout.setTextColor(this.getResources().getColor(
					R.color.white));
			toolbarProduct.setBackgroundResource(R.drawable.menu_background2);
			toolbarDongtai.setBackgroundResource(R.drawable.menu_background2);
			toolbarPic.setBackgroundResource(R.drawable.menu_background2);
			toolbarCircle.setBackgroundResource(R.drawable.menu_background);
			toolbarAbaout.setBackgroundResource(R.drawable.menu_background2);
			break;

		case 5:
			toolbarProduct.setTextColor(getResources().getColor(R.color.white));
			toolbarDongtai.setTextColor(this.getResources().getColor(
					R.color.white));
			toolbarPic
					.setTextColor(this.getResources().getColor(R.color.white));
			toolbarCircle.setTextColor(this.getResources().getColor(
					R.color.white));
			toolbarAbaout.setTextColor(this.getResources().getColor(
					R.color.white));
			toolbarProduct.setBackgroundResource(R.drawable.menu_background2);
			toolbarDongtai.setBackgroundResource(R.drawable.menu_background2);
			toolbarPic.setBackgroundResource(R.drawable.menu_background2);
			toolbarCircle.setBackgroundResource(R.drawable.menu_background2);
			toolbarAbaout.setBackgroundResource(R.drawable.menu_background);
			break;

		}
	}
}
