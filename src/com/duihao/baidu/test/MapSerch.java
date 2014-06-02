/**
 * 
 */
package com.duihao.baidu.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.duihao.promotion.main.android.R;

/**
 * @author Administrator
 * 
 */
public class MapSerch extends Activity {
	EditText startSerch;
	EditText endSerch;
	Button button;
	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.mapserch);

		initView();

		button.setOnClickListener(new OnClick());

	}

	/**
	 * 实例化控件
	 */
	void initView() {

		startSerch = (EditText) findViewById(R.id.mapserchstart);
		endSerch = (EditText) findViewById(R.id.mapserchend);
		button = (Button) findViewById(R.id.mapsechbutton);
		listView = (ListView) findViewById(R.id.mapserchlist);

	}

	/**
	 * 
	 * @author Administrator 点击事件方法
	 */
	private class OnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			MainActivity activity=new MainActivity();
			activity.serchBus();
		}

	}

	/**
	 * 查询方法
	 */
	private void serchMap() {
		
	}
}
