package com.duihao.baidu.test;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MKEvent;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.mapapi.map.RouteOverlay;
import com.baidu.mapapi.map.TransitOverlay;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPlanNode;
import com.baidu.mapapi.search.MKPoiInfo;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKRoute;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.duihao.promotion.main.android.R;
import com.duihao.promotion.ui.android.AboatActivity;
import com.duihao.promotion.ui.android.Main;

public class MainActivity extends Activity {

	double mLon1 = 116.400244;
	double mLat1 = 39.963175;
	double mLon2 = 116.369199;
	double mLat2 = 39.942821;
	double mLon3 = 116.425541;
	double mLat3 = 39.939723;
	double mLon4 = 116.401394;
	double mLat4 = 39.906965;
	double mLon5 = 116.402096;
	double mLat5 = 39.942057;
	private double latitudemap;
	private double longitudemap;
	MyLocationListener listener = new MyLocationListener();
	LocationVoerlay myLocationOverlay;// 定位图层
	LocationClient locationClient;// 定位相关
	LocationData locationData;// 定位数据
	PopupOverlay popupOverlay;// 泡泡图层，浏览节点时使用
	TextView popText;// 泡泡提示文字
	OnCheckedChangeListener onCheckedChangeListener;// ui相关
	Button requestutton;// 定位请求
	boolean isRequest = false;// 是否手动定位
	Boolean isFirst = true;// 手否首次定位
	private View viewPop;// 泡泡视图

	EditText start;// 起始点

	EditText end;// 终点

	Button mapcar;// 驾车

	Button mapbus;// 公交

	Button mappeo;// 步行

	private List<String> busLineIDList = null;

	int plan_number = 0;// 用于几种不同的公交方式

	int change_number = 1;// 当前的公交方式

	int nodeIndex = -2;// 节点索引,供浏览节点时使用

	MKRoute route = null;// 保存驾车/步行路线数据的变量，供浏览节点时使用

	TransitOverlay transitOverlay = null;// 保存公交路线图层数据的变量，供浏览节点时使用

	int searchType = -1;// 记录搜索的类型，区分驾车/步行和公交

	MyOverLayItem myOverLayItem = null;// 多覆盖物

	private PopupOverlay pop = null;// 弹出泡泡图层，浏览节点时使用

	private MapView mapview;// 地图视图

	MKSearch mSearch = null;// 搜索相关,搜索模块，也可去掉地图模块独立使用

	RouteOverlay routeOverlay = null;// 展示路线

	RouteOverlay overlay = null;// 公交详情

	private BMapManager bMapManager;// 百度地图加载引擎

	private String stringkey = "86E297E0a9e2d2ccba5692c3c9010f67";// 密钥

	private MapController mapController;// 地图控件，大小缩

	private ArrayList<OverlayItem> mitem;

	int busLineIndex = 0;

	Button pre;// 前后的标记
	Button next;
	Intent it;
	MKTransitRouteResult mres = null;// 用于存储多种公交的方式

	//protected TextView textview;
	private double Longitude;// 经度
	private double Latitude;// 纬度
	Button mapserch;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState); 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		 it = getIntent();
		if(AboatActivity.FlagMap==1){
			
			Longitude = it.getDoubleExtra("Longitude", 0.0000);
			Latitude = it.getDoubleExtra("Latitude", 0.00000);
			Log.i("传為度2", "" + Longitude);
			Log.i("传徑度2", "" + Latitude);
			
			AboatActivity.FlagMap=0;
		}
		if(Main.FlagMapMath==1){
			
			Longitude = it.getDoubleExtra("Long", 0.0000);
			Latitude = it.getDoubleExtra("Lat", 0.00000);
			Log.i("传為度3", "" + Longitude);
			Log.i("传徑度3", "" + Latitude);
			Main.FlagMapMath=0;
		}
		
		if (bMapManager == null) {

			bMapManager = new BMapManager(this);// 实例化引擎

		}
		// 加载地图必须验证key，验证key用到init
		bMapManager.init(stringkey, new MKGeneralListener() {

			@Override
			public void onGetPermissionState(int arg0) {
				// TODO Auto-generated method stub返回授权错误
				if (arg0 == 300) {

					Toast.makeText(MainActivity.this, "key授权错误",
							Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onGetNetworkState(int arg0) {
				// TODO Auto-generated method stub返回网络错误

			}
		});

		setContentView(R.layout.activity_main);

		requestutton = (Button) this.findViewById(R.id.mapbutton1);

		requestutton.setOnClickListener(RequestLocal);
		
		mapserch=(Button)findViewById(R.id.serchMap);
		mapserch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(MainActivity.this, MapSerch.class);
				startActivity(intent);
			}
		});
		
		RadioGroup grounp = (RadioGroup) findViewById(R.id.radioGroup);
		onCheckedChangeListener = new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if (checkedId == R.id.defaulticon) {
					// 传入空则恢复默认图标
					changeIcon(null);
				}
				if (checkedId == R.id.customicon) {
					// 修改为自定义的
					changeIcon(getResources().getDrawable(R.drawable.icon_geo));
				}
			}
		};
		grounp.setOnCheckedChangeListener(onCheckedChangeListener);

		busLineIDList = new ArrayList<String>();

		mapview = (MylocationMapView) this.findViewById(R.id.bmapView);// 实例化视图

		mapview.setBuiltInZoomControls(true);// 开启缩放

		mapController = mapview.getController();// 实例化控件

		mapController.enableClick(true);// 设置地图是否响应点击事件

		mapview.setTraffic(true);
		// 创建 弹出泡泡图层
		creatPopView();
		mSearch = new MKSearch();
		mSearch.init(bMapManager, mkserch);
		/*
		 * 定位初始化
		 */
		locationClient = new LocationClient(this);
		locationData = new LocationData();
		locationClient.registerLocationListener(listener);
		LocationClientOption locationClientOption = new LocationClientOption();
		locationClientOption.setOpenGps(true);// 打开gps
		locationClientOption.setCoorType("bd09ll");// 设置坐标样式
		//locationClientOption.setScanSpan(5000);// 刷新
		locationClient.setLocOption(locationClientOption);
		locationClient.start();
		
		/**
		 * 定位图层初始化
		 */
		myLocationOverlay = new LocationVoerlay(mapview);
		// 设置定位数据
		myLocationOverlay.setData(locationData);

		// 添加定位图层
		mapview.getOverlays().add(myLocationOverlay);

		myLocationOverlay.enableCompass();
		// 刷新数据
		mapview.refresh();

		pre = (Button) findViewById(R.id.pre);
		next = (Button) findViewById(R.id.next);
		// pre.setVisibility(pre.INVISIBLE);
		// next.setVisibility(next.INVISIBLE);
//		pre.setOnClickListener(new pre_Onclilk());
//		next.setOnClickListener(new next_Onclilk());

		
		
		
		// createPaopao();

		initOverlay();

		double cLat = 39.945;
		double cLon = 116.404;

		GeoPoint geoPoint = new GeoPoint((int) (cLat * 1E6), (int) (cLon * 1E6));// 设置一个经纬度

		mapController.setCenter(geoPoint);// 设置中心点

		mapController.setZoom(12);// 设置放大倍数
		
		//mSearch.transitSearch("北京", stNone, enNode);
		
	}
	


	// 查询实例化参数类

	MKSearchListener mkserch = new MKSearchListener() {

		@Override
		public void onGetAddrResult(MKAddrInfo arg0, int arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		// 返回公交车详情信息搜索结果
		public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {
			// TODO Auto-generated method stub
			if (arg1 == MKEvent.ERROR_ROUTE_ADDR) {
				Toast.makeText(MainActivity.this, "起点或者终点无法识别，需要选择其他出行方式",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (arg1 != 0 || arg0 == null) {

				Toast.makeText(MainActivity.this, "公交详细未找到匹配的结果",
						Toast.LENGTH_SHORT).show();
				return;
			}
			searchType = 3;
			overlay = new RouteOverlay(MainActivity.this, mapview);

			overlay.setData(arg0.getBusRoute()); // 获取公交车线路
			// 清除其他图层
			mapview.getOverlays().clear();
			// 添加新的图层
			mapview.getOverlays().add(overlay);
			// 刷新生效
			mapview.refresh();
			// 回到指定的起点
			mapview.getController().animateTo(arg0.getBusRoute().getStart());
			// 将数据存到全局
			route = arg0.getBusRoute();
			//Disprote(textview);
			// 重置线路节点索引
			nodeIndex = 0;

		}

		@Override
		// 返回驾乘路线搜索结果
		public void onGetDrivingRouteResult(MKDrivingRouteResult arg0, int arg1) {
			// TODO Auto-generated method stub
			// 起点或者终点无法识别，需要选择其他具体省市
			if (arg1 == MKEvent.ERROR_ROUTE_ADDR) {
				Toast.makeText(MainActivity.this, "起点或者终点无法识别，需要选择其他出行方式",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (arg1 != 0 || arg0 == null) {

				Toast.makeText(MainActivity.this, "驾车路线未找到匹配的结果",
						Toast.LENGTH_SHORT).show();
				return;
			}
			searchType = 0;
			routeOverlay = new RouteOverlay(MainActivity.this, mapview);
			routeOverlay.setData(arg0.getPlan(0).getRoute(0));
			// 清除其他图层
			mapview.getOverlays().clear();
			// 添加路线图层
			mapview.getOverlays().add(routeOverlay);
			// 执行刷新使生效
			mapview.refresh();
			// 使用zoomToSpan()绽放地图，使路线能完全显示在地图上
			mapview.getController().zoomToSpan(routeOverlay.getLatSpanE6(),
					routeOverlay.getLonSpanE6());
			// 移动地图到起点
			mapview.getController().animateTo(arg0.getStart().pt);
			// 将路线数据保存给全局变量
			route = arg0.getPlan(0).getRoute(0);
			//Disprote(textview);
			// 重置路线节点索引，节点浏览时使用
			nodeIndex = -1;
		}

		@Override
		public void onGetPoiDetailSearchResult(int arg0, int arg1) {
			// TODO Auto-generated method stub

		}

		@Override //返回poi搜索结果
		public void onGetPoiResult(MKPoiResult arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			if (arg1 == MKEvent.ERROR_ROUTE_ADDR) {
				Toast.makeText(MainActivity.this, "起点或者终点无法识别，需要选择其他出行方式",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (arg1 != 0 || arg0 == null) {

				Toast.makeText(MainActivity.this, "poi路线未找到匹配的结果",
						Toast.LENGTH_SHORT).show();
				return;
			}
			// 找到公交路线poi node
			MKPoiInfo curPoi = null;
			int toalePoiNum = arg0.getCurrentNumPois();
			// 遍历所有api找到poi为公交的线路
			busLineIDList.clear();
			for (int idex = 0; idex < toalePoiNum; idex++) {
				if (2 == arg0.getPoi(idex).ePoiType) {
					// poi类型，0：普通点，1：公交站，2：公交线路，3：地铁站，4：地铁线路
					curPoi = arg0.getPoi(idex);
					// 使用poi的uid发起公交详情检索
					busLineIDList.add(curPoi.uid);

				}
			}
			SearchNextBusline();
			// 没有找到公交信息
			if (curPoi == null) {
				Toast.makeText(MainActivity.this, "抱歉，未找到结果", Toast.LENGTH_LONG)
						.show();

				return;
			}
		}

		@Override
		public void onGetShareUrlResult(MKShareUrlResult arg0, int arg1,
				int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		// 返回公交搜索结果
		public void onGetTransitRouteResult(MKTransitRouteResult arg0, int arg1) {
			// TODO Auto-generated method stub
			if (arg1 == MKEvent.ERROR_ROUTE_ADDR) {
				Toast.makeText(MainActivity.this, "起点或者终点无法识别，需要选择其他出行方式",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (arg1 != 0 || arg0 == null) {

				Toast.makeText(MainActivity.this, "公交未找到匹配的结果",
						Toast.LENGTH_SHORT).show();
				return;
			}
			searchType = 1;
			mres = arg0;
			plan_number = arg0.getNumPlan();
			transitOverlay = new TransitOverlay(MainActivity.this, mapview);
			transitOverlay.setData(arg0.getPlan(0));
			// 清除其他图层
			mapview.getOverlays().clear();
			// 添加路线图层
			mapview.getOverlays().add(transitOverlay);
			// 执行刷新使生效
			mapview.refresh();
			// 使用zoomToSpan()绽放地图，使路线能完全显示在地图上
			mapview.getController().zoomToSpan(transitOverlay.getLatSpanE6(),
					transitOverlay.getLonSpanE6());
			// 移动地图到起点
			mapview.getController().animateTo(arg0.getStart().pt);
			//Disprote(textview);
			// 重置路线节点索引，节点浏览时使用
			nodeIndex = 0;

		}

		@Override
		// 返回步行路线搜索结果
		public void onGetWalkingRouteResult(MKWalkingRouteResult arg0, int arg1) {
			// TODO Auto-generated method stub
			if (arg1 == MKEvent.ERROR_ROUTE_ADDR) {
				Toast.makeText(MainActivity.this, "起点或者终点无法识别，需要选择其他出行方式",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (arg1 != 0 || arg0 == null) {

				Toast.makeText(MainActivity.this, "步行未找到匹配的结果",
						Toast.LENGTH_SHORT).show();
				return;
			}
			searchType = 2;
			routeOverlay = new RouteOverlay(MainActivity.this, mapview);
			routeOverlay.setData(arg0.getPlan(0).getRoute(0));
			// 清除其他图层
			mapview.getOverlays().clear();
			// 添加新的图层
			mapview.getOverlays().add(routeOverlay);
			// 刷新
			mapview.refresh();
			// 使用zoomToSpan是地图完全显示
			mapview.getController().zoomToSpan(routeOverlay.getLatSpanE6(),
					routeOverlay.getLonSpanE6());
			// 移动地图到起点
			mapview.getController().animateTo(arg0.getStart().pt);
			// 将数据保存到全局变量
			route = arg0.getPlan(0).getRoute(0);
		//	Disprote(textview);
			// 重置路线节点索引，节点浏览时使用
			nodeIndex = -1;
		}

	};

	// 路线查询点击方法
	OnClickListener pathonListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			pathonListeners(v);
		}
	};

	private void Disprote(TextView text) {

		text.setText("");

		if (searchType == 3) {
			for (int i = 0; i < route.getNumSteps() - 1; i++) {
				text.setText(text.getText().toString() + route.getStep(i)
						+ "\n");
			}
		}
		if (searchType == 1) {
			text.setText("一共有" + plan_number + "种乘车方式" + "\n" + "这是方式"
					+ change_number + "\n");
			for (int i = 0; i < transitOverlay.getAllItem().size() - 1; i++) {
				text.setText(text.getText().toString()
						+ transitOverlay.getItem(i).getTitle() + "\n");
			}
		}
	}

	// 查询下一条线路
	private void SearchNextBusline() {
		if (busLineIndex > busLineIDList.size()) {
			busLineIndex = 0;
		}
		if (busLineIndex >= 0 && busLineIndex < busLineIDList.size()
				&& busLineIDList.size() > 0) {
			mSearch.busLineSearch("北京", busLineIDList.get(busLineIndex));
			busLineIndex++;
		}
	}

	private void pathonListeners(View v) {
		route = null;
		routeOverlay = null;
		transitOverlay = null;
		// 对起点终点的name进行赋值，也可以直接对坐标赋值，赋值坐标则将根据坐标进行搜索
		MKPlanNode stNone = new MKPlanNode();
		stNone.name = start.getText().toString();
		MKPlanNode enNode = new MKPlanNode();
		enNode.name = end.getText().toString();
		if (mapcar.equals(v)) {

			mSearch.drivingSearch("北京", stNone, "北京", enNode);

		} else if (mapbus.equals(v)) {

			mSearch.transitSearch("北京", stNone, enNode);

		} else if (mappeo.equals(v)) {

			mSearch.walkingSearch("北京", stNone, "北京", enNode);

		}
	}

	// 泡泡图层
	// private void createPaopao() {
	// PopupClickListener popupClickListener = new PopupClickListener() {
	//
	// @Override
	// public void onClickedPopup(int arg0) {
	// // TODO Auto-generated method stub
	// }
	// };
	// pop = new PopupOverlay(mapview, popupClickListener);
	// }

	public void initOverlay() {

		Drawable dr = getResources().getDrawable(R.drawable.icon_marka);

		myOverLayItem = new MyOverLayItem(dr, mapview);

		// 数据源,准备overlay 数据
		GeoPoint p1 = new GeoPoint((int) (Latitude * 1E6),
				(int) (Longitude * 1E6));
		OverlayItem item1 = new OverlayItem(p1, "店铺位置", "");
		
		/**
		 * 设置overlay图标，如不设置，则使用创建ItemizedOverlay时的默认图标.
		 */


		// 将item 添加到overlay中
		myOverLayItem.addItem(item1);
		// 保存所有item，以便overlay在reset后重新添加
		mitem = new ArrayList<OverlayItem>();
		mitem.addAll(myOverLayItem.getAllItem());
		// 将overlay 添加至MapView中
		mapview.getOverlays().add(myOverLayItem);
		// 刷新地图
		mapview.refresh();

	}

	// 前后线路
	class pre_Onclilk implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Toast.makeText(MainActivity.this, "上一个", Toast.LENGTH_SHORT).show();
			SearchNode(0);
		}
	}

	class next_Onclilk implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Toast.makeText(MainActivity.this, "下一个", Toast.LENGTH_SHORT).show();
			SearchNode(1);
		}
	}

	public class MyOverLayItem extends ItemizedOverlay<OverlayItem> {

		public MyOverLayItem(Drawable arg0, MapView arg1) {
			super(arg0, arg1);
			// TODO Auto-generated constructor stub

		}

	}

	public void SearchNode(int porn) {

		switch (searchType) {
		case 3:
			if (porn == 0) {
				// 索引减
				nodeIndex++;
				if (nodeIndex <= -1 || route == null
						|| nodeIndex >= route.getNumSteps())
					return;
				// 移动到指定索引的坐标
				mapview.getController().animateTo(
						route.getStep(nodeIndex).getPoint());
				// 弹出泡泡
				Toast.makeText(MainActivity.this,
						route.getStep(nodeIndex).getContent(), 1000).show();
			}
			// 下一个节点
			if (porn == 1) {
				// 索引加
				nodeIndex--;
				if (nodeIndex <= -1 || route == null
						|| nodeIndex >= route.getNumSteps())
					return;
				// 移动到指定索引的坐标
				mapview.getController().animateTo(
						route.getStep(nodeIndex).getPoint());
				// 弹出泡泡
				Toast.makeText(MainActivity.this,
						route.getStep(nodeIndex).getContent(), 1000).show();
			}
			break;
		case 1:
			if (porn == 0) {
				change_number++;
				if (change_number >= plan_number)
					change_number = 0;
				BuschangePlan(change_number);
			//	Disprote(textview);
			}
			// 下一个节点
			if (porn == 1) {
				change_number--;
				if (change_number < 0)
					change_number = plan_number - 1;
				BuschangePlan(change_number);
			//	Disprote(textview);
			}
			break;

		default:
			break;
		}
		// 上一个节点

	}

	void BuschangePlan(int i) {
		transitOverlay = new TransitOverlay(MainActivity.this, mapview);
		// 此处仅展示一个方案作为示例
		transitOverlay.setData(mres.getPlan(i));

		plan_number = mres.getNumPlan();
		// 清除其他图层
		mapview.getOverlays().clear();
		// 添加路线图层
		mapview.getOverlays().add(transitOverlay);
		// 执行刷新使生效
		mapview.refresh();
		// 使用zoomToSpan()绽放地图，使路线能完全显示在地图上
		mapview.getController().zoomToSpan(transitOverlay.getLatSpanE6(),
				transitOverlay.getLonSpanE6());
		// 移动地图到起点
		mapview.getController().animateTo(mres.getStart().pt);
		// 重置路线节点索引，节点浏览时使用
		//Disprote(textview);
	}

	/**
	 * 
	 * @author Administrator 定位sdk监听
	 */
	private class MyLocationListener implements BDLocationListener {

		

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub

			Log.i("经度", "" + location.getLatitude());
			Log.i("维度", "" + location.getLongitude());
			if (location == null)
				return;

			latitudemap=locationData.latitude = location.getLatitude();// 纬度
			longitudemap=locationData.longitude = location.getLongitude();// 经度
			
			
			/**
			 * 如果不显示定位精度圈,将accuracy设置为0
			 */
			locationData.accuracy = location.getRadius();// 获取半径
			locationData.direction = location.getDerect();// 定位
			// 更新定位数据
			myLocationOverlay.setData(locationData);
			// 刷新地图
			mapview.refresh();
			// 首次定位或者手动定位，移动到定位点
			if (isRequest || isFirst) {
				// 移动地图到定位点
				mapController.animateTo(new GeoPoint(
						(int) (locationData.latitude * 1e6),
						(int) (locationData.longitude * 1e6)));
				isRequest = false;
			}
			// 首次完成
			isFirst = false;
			
		}

		@Override
		public void onReceivePoi(BDLocation popLocation) {
			// TODO Auto-generated method stub
			if (popLocation == null) {
				return;
			}
		}

	}

	// 继承MyLocationOverlay重写dispatchTap实现点击处理
	public class LocationVoerlay extends MyLocationOverlay {// 定位图层

		public LocationVoerlay(MapView mapview) {

			super(mapview);
		}

		@Override
		protected boolean dispatchTap() {
			// TODO Auto-generated method stub
			// 处理点击事件，弹出泡泡
			popText.setBackgroundResource(R.drawable.pop);
			popText.setText("我的位置");
			popupOverlay.showPopup(BMapUtil.getBitmapFromView(popText),
					new GeoPoint((int) (latitudemap * 1e6),
							(int) (longitudemap * 1e6)), 10);
			return true;
		}
   
	}
	//路线查询   
	void serchBus() {
		// TODO Auto-generated method stub
		MKPlanNode enNode= new MKPlanNode();
		enNode.pt =new GeoPoint((int)(Latitude*1e6), (int)(Longitude*1e6)) ;
		//stNone.name="双井";
		MKPlanNode stNone= new MKPlanNode();
		//enNode.name="大望路";
		stNone.pt = new GeoPoint((int)(latitudemap * 1e6), (int)(longitudemap*1e6)) ;
		Log.i("1", "" + Longitude);
		Log.i("2", "" + Latitude);
		Log.i("3", "" + locationData.latitude);
		Log.i("4", "" + locationData.longitude);
		mSearch.walkingSearch("北京", stNone, "北京",enNode);
	}
	OnClickListener RequestLocal = new OnClickListener() {// 定位请求按钮事件

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			reQuestLocalClick();
		}

	};

	/**
	 * 手动触发第一次请求
	 */
	private void reQuestLocalClick() {
		// TODO Auto-generated method stub
		isRequest = true;
		locationClient.requestLocation();
		Toast.makeText(MainActivity.this, "正在定位……", Toast.LENGTH_SHORT).show();
	}

	/**
	 * 修改图标
	 */
	private void changeIcon(Drawable drawable) {
		// 当传入drawable为null时，使用默认
		myLocationOverlay.setMarker(drawable);
		// 刷新
		mapview.refresh();
	}

	private void creatPopView() {// 创建泡泡图层
		viewPop = LayoutInflater.from(MainActivity.this).inflate(
				R.layout.popview, null);
		popText = (TextView) viewPop.findViewById(R.id.textcache);
		// 泡泡点击响应回调
		PopupClickListener popupClickListener = new PopupClickListener() {

			@Override
			public void onClickedPopup(int arg0) {
				// TODO Auto-generated method stub
				Log.v("click", "clickapoapo");
			}
		};
		popupOverlay = new PopupOverlay(mapview, popupClickListener);
		MylocationMapView.pop = popupOverlay;

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub

		mapview.onResume();

		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		mapview.onPause();
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		mapview.destroy();
		super.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapview.onSaveInstanceState(outState);

	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mapview.onRestoreInstanceState(savedInstanceState);
	}

}

class MylocationMapView extends MapView {
	static PopupOverlay pop = null;// 点击弹出pop

	public MylocationMapView(Context arg0, AttributeSet arg1, int arg2) {
		super(arg0, arg1, arg2);
		// TODO Auto-generated constructor stub
	}

	public MylocationMapView(Context arg0, AttributeSet arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public MylocationMapView(Context arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		// 消隐泡泡
		if (!super.onTouchEvent(arg0)) {
			if (pop != null && arg0.getAction() == MotionEvent.ACTION_UP) {
				pop.hidePop();
			}
		}
		return true;
	}

}