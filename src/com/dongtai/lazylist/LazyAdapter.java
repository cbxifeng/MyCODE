package com.dongtai.lazylist;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.duihao.promotion.javavbean.android.IndustryTrendsBean;
import com.duihao.promotion.main.android.R;

public class LazyAdapter extends BaseAdapter {
	
	private Activity activity;
	private ArrayList<IndustryTrendsBean> data = new ArrayList<IndustryTrendsBean>();
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;

	public LazyAdapter(Activity a, ArrayList<IndustryTrendsBean> d) {
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

		if (convertView == null) {

			holder = new ViewHolder();

			convertView = inflater.inflate(R.layout.item, null);

			holder.title = (TextView) convertView.findViewById(R.id.m_title);

			holder.button = (Button) convertView
					.findViewById(R.id.labutton);
			
           
			holder.description = (TextView) convertView
					.findViewById(R.id.m_description);

			holder.thumb = (ImageView) convertView.findViewById(R.id.m_thumb);

			
			convertView.setTag(holder);
		} else {

			holder = (ViewHolder) convertView.getTag();
		}
		holder.title.setText(data.get(position).getTitle());
		holder.button.setVisibility(View.INVISIBLE);
		holder.button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Toast.makeText(activity, "Ô¤¶¨", Toast.LENGTH_SHORT).show();
			}
		});
		
		if(data.get(position).getDescription().trim().length()<1){
			holder.description.setText("ÔÝÎÞ½éÉÜ");
		}else{
			holder.description.setText(data.get(position).getDescription());
		}
		
		imageLoader.DisplayImage(data.get(position).getThumb(), activity,
				holder.thumb);
		return convertView;
	}

	class ViewHolder {

		TextView title;

		Button button;

		TextView description;

		ImageView thumb;

	}
}