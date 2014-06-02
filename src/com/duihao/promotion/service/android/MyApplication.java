package com.duihao.promotion.service.android;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
//退出多个activity的类
public class MyApplication extends Application {

	private List<Activity> activityList = new LinkedList<Activity>();

	private static MyApplication instance;
	//全局变量 可以在工程里任何地方访问
	/*public List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();;*/
	
	public MyApplication() {

		super();

	}

	// 单例模式中获取唯一的MyApplication实例

	public static MyApplication getInstance() {

		if (null == instance) {

			instance = new MyApplication();

		}

		return instance;

	}

	// 添加Activity到容器中

	public void addActivity(Activity activity) {

		activityList.add(activity);

	}

	// 遍历所有Activity并finish

	public void exit() {

		for (Activity activity : activityList) {

			activity.finish();

		}

		System.exit(0);

	}

}
