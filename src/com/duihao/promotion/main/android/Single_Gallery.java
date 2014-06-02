package com.duihao.promotion.main.android;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.Gallery;

//重写Gallery类实现滑动开机画面效果
public class Single_Gallery extends Gallery {

	private static final int TOUCH_STATE_REST = 0;
	private static final int TOUCH_STATE_SCROLLING = 1;
	private int mTouchState = TOUCH_STATE_REST;
	private int mTouchSlop;
	private float mLastMotionX;
	private float mLastMotionY;

	public Single_Gallery(Context context, AttributeSet attrs) {
		super(context, attrs);

		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,

	float velocityY) {

		int kEvent;
		if (isScrollingLeft(e1, e2)) {
			// Check if scrolling left
			kEvent = KeyEvent.KEYCODE_DPAD_LEFT;
		} else {
			// Otherwise scrolling right
			kEvent = KeyEvent.KEYCODE_DPAD_RIGHT;
		}
		onKeyDown(kEvent, null);

		return true;

	}

	private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2)

	{

		return e2.getX() > e1.getX();

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		// TODO Auto-generated method stub

		return super.onTouchEvent(event);

	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {

		final int action = ev.getAction();

		if ((action == MotionEvent.ACTION_MOVE) &&

		(mTouchState != TOUCH_STATE_REST)) {

			return true;

		}

		final float x = ev.getX();

		final float y = ev.getY();

		switch (action) {

		case MotionEvent.ACTION_MOVE:

			final int xDiff = (int) Math.abs(mLastMotionX - x);

			if (xDiff > 0) {

				mTouchState = TOUCH_STATE_SCROLLING;

			}

			break;

		case MotionEvent.ACTION_DOWN:

			mLastMotionX = x;

			mLastMotionY = y;

			mTouchState = TOUCH_STATE_REST;

			break;

		case MotionEvent.ACTION_CANCEL:

		case MotionEvent.ACTION_UP:

			mTouchState = TOUCH_STATE_REST;

			break;
		}
		return mTouchState != TOUCH_STATE_REST;
	}

}
