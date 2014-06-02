package com.duihao.promotion.ui.android;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;
import android.widget.Toast;

import com.duihao.promotion.ui.android.GalleryActivity.ImageAdapter;

public class MyGalleryt extends Gallery {
	boolean isFirst = false;

	boolean isLast = false;

	public MyGalleryt(Context context) {
		super(context);
	}

	public MyGalleryt(Context context, AttributeSet paramAttributeSet) {
		super(context, paramAttributeSet);
	}

	/** 鏄惁鍚戝乏婊戝姩锛坱rue - 鍚戝乏婊戝姩锛�false - 鍚戝彸婊戝姩锛� */
	private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
		return e2.getX() - e1.getX() > (PicActivity.dm.widthPixels / 2);
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		ImageAdapter ia = (ImageAdapter) this.getAdapter();

		int p = ia.getOwnposition();
		int count = ia.getCount();
		int kEvent = 0;
		if (isScrollingLeft(e1, e2)) {
			if (p == 0 && isFirst) {
				Toast.makeText(this.getContext(), "首页了", Toast.LENGTH_SHORT)
						.show();
			} else if (p == 0) {
				isFirst = true;
			} else {
				isLast = false;
			}

			kEvent = KeyEvent.KEYCODE_DPAD_LEFT;
		} else if (e1.getX() - e2.getX() > (PicActivity.dm.widthPixels / 2)) {
			if (p == count - 1 && isLast) {
				Toast.makeText(this.getContext(), "最后一张了", Toast.LENGTH_SHORT)
						.show();
			} else if (p == count - 1) {
				isLast = true;
			} else {
				isFirst = false;
			}

			kEvent = KeyEvent.KEYCODE_DPAD_RIGHT;
		}

		onKeyDown(kEvent, null);
		return true;
	}
}
