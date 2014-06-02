package com.duihao.promotion.http.android;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.duihao.promotion.service.android.MyApplication;

public class NetWorkingCheck {
	NetworkInfo info;// �������
	Context context;
	String str;
	public static boolean B=false;
	public NetWorkingCheck(Context context,String str) {
		this.str=str;
		this.context=context;
	
	}
	public void chek(){
		ConnectivityManager manager = (ConnectivityManager) context
				
				.getSystemService(str);

		info = manager.getActiveNetworkInfo();
		if (info == null) {
			B=true;
			AlertDialog.Builder builder = new Builder(context);
			builder.setMessage("û�������������������");
			builder.setTitle("��ʾ");
			builder.setPositiveButton("����",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
							context.startActivity(new Intent(
									android.provider.Settings.ACTION_SETTINGS));
						}
					});
			builder.setNegativeButton("ȡ��",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							MyApplication.getInstance().exit();
							dialog.dismiss();
							
						}
					});
			builder.create().show();

		}
		
	
		// TODO Auto-generated constructor stub
	}

}
