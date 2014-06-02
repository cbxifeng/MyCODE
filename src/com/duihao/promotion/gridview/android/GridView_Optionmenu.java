package com.duihao.promotion.gridview.android;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.widget.SimpleAdapter;

import com.duihao.promotion.main.android.R;

public class GridView_Optionmenu {
	

	public int[] menu_image_array = { R.drawable.sc, R.drawable.zxtg, R.drawable.dy,
			R.drawable.wdsc, R.drawable.wdtg, R.drawable.wddy, R.drawable.wdxx,
			R.drawable.yhzx, R.drawable.tc, };

	public String[] menu_name_array = { "�����ղ�", "����Ԥ��", "����", "�ҵ��ղ�", "�ҵĶ���",
			"��������" };
	
	
	public GridView_Optionmenu() {
		super();
		// TODO Auto-generated constructor stub
	}



	public SimpleAdapter getMenuAdapter(Context context,
			String[] menuNameArray, int[] imageResourceArray) {

		// ΪgriView�������Դ
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

		for (int i = 0; i < menuNameArray.length; i++) {

			HashMap<String, Object> map = new HashMap<String, Object>();

			map.put("itemImage", imageResourceArray[i]);

			map.put("itemText", menuNameArray[i]);

			data.add(map);
		}
		// �������
		SimpleAdapter simperAdapter = new SimpleAdapter(context, data,

		R.layout.item_menu, new String[] { "itemImage", "itemText" },
				new int[] { R.id.item_image, R.id.item_text });

		return simperAdapter;
	}

}
