package com.fedorvlasov.lazylist;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.duihao.promotion.javavbean.android.NewsListInfo;
import com.duihao.promotion.main.android.R;
import com.duihao.promotion.ui.android.YuDing;
//产品适配器 解决内存溢出
public class LazyAdapter extends BaseAdapter {

	private Activity activity;
	private List<NewsListInfo> data = new ArrayList<NewsListInfo>();
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;

	public LazyAdapter(Activity a, ArrayList<NewsListInfo> d) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext());
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		String addtime;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");

		long lcc_time = Long.valueOf(data.get(position).getAddtime());

		addtime = sdf.format(new Date(lcc_time * 1000L));

		if (convertView == null) {

			holder = new ViewHolder();

			convertView = inflater.inflate(R.layout.item, null);

			holder.title = (TextView) convertView.findViewById(R.id.m_title);

			holder.button = (Button) convertView
					.findViewById(R.id.labutton);

			holder.description = (TextView) convertView
					.findViewById(R.id.m_description);

			holder.thumb = (ImageView) convertView.findViewById(R.id.m_thumb);

			holder.price = (TextView) convertView.findViewById(R.id.m_price);
			
			holder.price1 = (TextView) convertView.findViewById(R.id.m_price1);

			convertView.setTag(holder);
		} else {

			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.title.setText(data.get(position).getTitle());
		
		final String dingyue=data.get(position).getYuding();
		
		  final int id=data.get(position).getId();
		  
		holder.button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it=new Intent();
				
				it.setClass(activity, YuDing.class);
				
				it.putExtra("url", dingyue);
				
				//it.putExtra("id", id);
				
				activity.startActivity(it);
				
			}
		});
		
		if(data.get(position).getDescription().trim().length()<1){
			holder.description.setText("  ");
		}else{
			holder.description.setText(data.get(position).getDescription());
		}
		
		
		if(data.get(position).getPrice().trim().length()<1){
			holder.price.setText(" ");
		}else{
			holder.price.setText("现价"+data.get(position).getPrice());
		}
		
		if(data.get(position).getPrice_old().trim().length()<1){
			holder.price1.setText(" ");
		}else{
			holder.price1.setText(" / 原价"+data.get(position).getPrice_old());
		}
		
		imageLoader.DisplayImage(data.get(position).getThumb(), activity,
				holder.thumb);
		return convertView;
	}

	class ViewHolder {
		
		TextView price1;
		
		TextView price;

		TextView title;

		Button button;

		TextView description;

		ImageView thumb;

	}
}