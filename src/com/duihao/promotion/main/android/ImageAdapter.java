package com.duihao.promotion.main.android;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class ImageAdapter extends BaseAdapter {

	private Context mContext;
	public static Integer[] mps = { R.drawable.welcome1, R.drawable.welcome2,
			R.drawable.welcome3,

	};

	public ImageAdapter(Context context) {
		mContext = context;
	}

	public int getCount() {
		return mps.length;
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView image = new ImageView(mContext);
		image.setImageResource(mps[position]);
		image.setAdjustViewBounds(true);
		//不安比列缩放
		image.setScaleType(ScaleType.CENTER_CROP);

		// 设置 图片 是图片全屏
		image.setLayoutParams(new Single_Gallery.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		return image;
	}
}