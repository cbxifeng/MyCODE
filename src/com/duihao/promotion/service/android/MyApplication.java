package com.duihao.promotion.service.android;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
//�˳����activity����
public class MyApplication extends Application {

	private List<Activity> activityList = new LinkedList<Activity>();

	private static MyApplication instance;
	//ȫ�ֱ��� �����ڹ������κεط�����
	/*public List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();;*/
	
	public MyApplication() {

		super();

	}

	// ����ģʽ�л�ȡΨһ��MyApplicationʵ��

	public static MyApplication getInstance() {

		if (null == instance) {

			instance = new MyApplication();

		}

		return instance;

	}

	// ���Activity��������

	public void addActivity(Activity activity) {

		activityList.add(activity);

	}

	// ��������Activity��finish

	public void exit() {

		for (Activity activity : activityList) {

			activity.finish();

		}

		System.exit(0);

	}

}
