package com.duihao.promotion.ui.android;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import wholepicache.ImageLoader;
import wholepicache.MemoryCache;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView;

import com.duihao.promotion.http.android.HttpUrl;
import com.duihao.promotion.main.android.R;

public class GalleryActivity extends Activity {
	public int position; 
	ArrayList<String> url=new ArrayList<String>();
	PicActivity grid;
	MemoryCache memoryCache = new MemoryCache();
	protected int index=0;
	MyGalleryt galllery;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.gallery);
		grid = new PicActivity();
		galllery = (MyGalleryt) findViewById(R.id.mygallery);
		Intent intent = getIntent();
		
		position = intent.getIntExtra("position",0); // 
		
		url=intent.getStringArrayListExtra("url");
		
		Log.i("position", ""+position);
		
		ImageAdapter imgAdapter = new ImageAdapter(GalleryActivity.this);
		galllery.setAdapter(imgAdapter); 
		galllery.setSelection(position); 

		Timer timer = new Timer();
		timer.schedule(task, 7000, 7000);
		
		Animation an = AnimationUtils.loadAnimation(this, R.anim.scale); // Gallery鍔ㄧ敾
		galllery.setAnimation(an);
	}

	/**
	* 定时器，实现自动播放
	*/
	private TimerTask task = new TimerTask() {
	@Override
	public void run() {
	Message message = new Message();
	message.what = 2;
	index = galllery.getSelectedItemPosition();
	index++;
	if(index>galllery.getCount()||index==galllery.getCount()){
		index=0;
	}
	handler.sendMessage(message);
	}
	};
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
		super.handleMessage(msg);
		switch (msg.what) {
		case 2:
		galllery.setSelection(index);
		break;
		default:
		break;
		}
		}
		};
	public class ImageAdapter extends BaseAdapter {
		private Context mContext;
		private int mPos;
		private HttpUrl httpurl;
		private ImageLoader imageLoader;
		public ImageAdapter(Context context) {
			mContext = context;
			imageLoader=new ImageLoader(mContext);
		}

		public void setOwnposition(int ownposition) {
			this.mPos = ownposition;
		}

		public int getOwnposition() {
			return mPos;
		}

		@Override
		public int getCount() {
			return url.size();
		}

		@Override
		public Object getItem(int position) {
			mPos = position;
			return position;
		}
    
		@Override
		public long getItemId(int position) {
			mPos = position;
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			mPos = position;
			ImageView imageview = new ImageView(mContext);
			
			imageview.setScaleType(ImageView.ScaleType.FIT_CENTER);
            
			imageview.setLayoutParams(new MyGalleryt.LayoutParams(

			LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//
//			             httpurl = new HttpUrl();
//						httpurl.httpUrlPic(url.get(position));

			//Log.i("显示大图", "" + url.get(position));

//			if (url.get(position).trim()== null) {
//				
//				imageview.setBackgroundResource(R.drawable.a);
//				
//			} else {
//
//				imageview.setImageBitmap(httpurl.httpUrlPic(url.get(position)));
//
//			}
//			if(position>url.size()-1){
//				position=1;
//			}
			imageLoader.DisplayImage(url.get(position), GalleryActivity.this,
					imageview);
			
			return imageview;
		}
	}
}