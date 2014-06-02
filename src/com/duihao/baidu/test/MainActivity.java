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
	LocationVoerlay myLocationOverlay;// ��λͼ��
	LocationClient locationClient;// ��λ���
	LocationData locationData;// ��λ����
	PopupOverlay popupOverlay;// ����ͼ�㣬����ڵ�ʱʹ��
	TextView popText;// ������ʾ����
	OnCheckedChangeListener onCheckedChangeListener;// ui���
	Button requestutton;// ��λ����
	boolean isRequest = false;// �Ƿ��ֶ���λ
	Boolean isFirst = true;// �ַ��״ζ�λ
	private View viewPop;// ������ͼ

	EditText start;// ��ʼ��

	EditText end;// �յ�

	Button mapcar;// �ݳ�

	Button mapbus;// ����

	Button mappeo;// ����

	private List<String> busLineIDList = null;

	int plan_number = 0;// ���ڼ��ֲ�ͬ�Ĺ�����ʽ

	int change_number = 1;// ��ǰ�Ĺ�����ʽ

	int nodeIndex = -2;// �ڵ�����,������ڵ�ʱʹ��

	MKRoute route = null;// ����ݳ�/����·�����ݵı�����������ڵ�ʱʹ��

	TransitOverlay transitOverlay = null;// ���湫��·��ͼ�����ݵı�����������ڵ�ʱʹ��

	int searchType = -1;// ��¼���������ͣ����ּݳ�/���к͹���

	MyOverLayItem myOverLayItem = null;// �า����

	private PopupOverlay pop = null;// ��������ͼ�㣬����ڵ�ʱʹ��

	private MapView mapview;// ��ͼ��ͼ

	MKSearch mSearch = null;// �������,����ģ�飬Ҳ��ȥ����ͼģ�����ʹ��

	RouteOverlay routeOverlay = null;// չʾ·��

	RouteOverlay overlay = null;// ��������

	private BMapManager bMapManager;// �ٶȵ�ͼ��������

	private String stringkey = "86E297E0a9e2d2ccba5692c3c9010f67";// ��Կ

	private MapController mapController;// ��ͼ�ؼ�����С��

	private ArrayList<OverlayItem> mitem;

	int busLineIndex = 0;

	Button pre;// ǰ��ı��
	Button next;
	Intent it;
	MKTransitRouteResult mres = null;// ���ڴ洢���ֹ����ķ�ʽ

	//protected TextView textview;
	private double Longitude;// ����
	private double Latitude;// γ��
	Button mapserch;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState); 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		 it = getIntent();
		if(AboatActivity.FlagMap==1){
			
			Longitude = it.getDoubleExtra("Longitude", 0.0000);
			Latitude = it.getDoubleExtra("Latitude", 0.00000);
			Log.i("�����2", "" + Longitude);
			Log.i("������2", "" + Latitude);
			
			AboatActivity.FlagMap=0;
		}
		if(Main.FlagMapMath==1){
			
			Longitude = it.getDoubleExtra("Long", 0.0000);
			Latitude = it.getDoubleExtra("Lat", 0.00000);
			Log.i("�����3", "" + Longitude);
			Log.i("������3", "" + Latitude);
			Main.FlagMapMath=0;
		}
		
		if (bMapManager == null) {

			bMapManager = new BMapManager(this);// ʵ��������

		}
		// ���ص�ͼ������֤key����֤key�õ�init
		bMapManager.init(stringkey, new MKGeneralListener() {

			@Override
			public void onGetPermissionState(int arg0) {
				// TODO Auto-generated method stub������Ȩ����
				if (arg0 == 300) {

					Toast.makeText(MainActivity.this, "key��Ȩ����",
							Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onGetNetworkState(int arg0) {
				// TODO Auto-generated method stub�����������

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
					// �������ָ�Ĭ��ͼ��
					changeIcon(null);
				}
				if (checkedId == R.id.customicon) {
					// �޸�Ϊ�Զ����
					changeIcon(getResources().getDrawable(R.drawable.icon_geo));
				}
			}
		};
		grounp.setOnCheckedChangeListener(onCheckedChangeListener);

		busLineIDList = new ArrayList<String>();

		mapview = (MylocationMapView) this.findViewById(R.id.bmapView);// ʵ������ͼ

		mapview.setBuiltInZoomControls(true);// ��������

		mapController = mapview.getController();// ʵ�����ؼ�

		mapController.enableClick(true);// ���õ�ͼ�Ƿ���Ӧ����¼�

		mapview.setTraffic(true);
		// ���� ��������ͼ��
		creatPopView();
		mSearch = new MKSearch();
		mSearch.init(bMapManager, mkserch);
		/*
		 * ��λ��ʼ��
		 */
		locationClient = new LocationClient(this);
		locationData = new LocationData();
		locationClient.registerLocationListener(listener);
		LocationClientOption locationClientOption = new LocationClientOption();
		locationClientOption.setOpenGps(true);// ��gps
		locationClientOption.setCoorType("bd09ll");// ����������ʽ
		//locationClientOption.setScanSpan(5000);// ˢ��
		locationClient.setLocOption(locationClientOption);
		locationClient.start();
		
		/**
		 * ��λͼ���ʼ��
		 */
		myLocationOverlay = new LocationVoerlay(mapview);
		// ���ö�λ����
		myLocationOverlay.setData(locationData);

		// ��Ӷ�λͼ��
		mapview.getOverlays().add(myLocationOverlay);

		myLocationOverlay.enableCompass();
		// ˢ������
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

		GeoPoint geoPoint = new GeoPoint((int) (cLat * 1E6), (int) (cLon * 1E6));// ����һ����γ��

		mapController.setCenter(geoPoint);// �������ĵ�

		mapController.setZoom(12);// ���÷Ŵ���
		
		//mSearch.transitSearch("����", stNone, enNode);
		
	}
	


	// ��ѯʵ����������

	MKSearchListener mkserch = new MKSearchListener() {

		@Override
		public void onGetAddrResult(MKAddrInfo arg0, int arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		// ���ع�����������Ϣ�������
		public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {
			// TODO Auto-generated method stub
			if (arg1 == MKEvent.ERROR_ROUTE_ADDR) {
				Toast.makeText(MainActivity.this, "�������յ��޷�ʶ����Ҫѡ���������з�ʽ",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (arg1 != 0 || arg0 == null) {

				Toast.makeText(MainActivity.this, "������ϸδ�ҵ�ƥ��Ľ��",
						Toast.LENGTH_SHORT).show();
				return;
			}
			searchType = 3;
			overlay = new RouteOverlay(MainActivity.this, mapview);

			overlay.setData(arg0.getBusRoute()); // ��ȡ��������·
			// �������ͼ��
			mapview.getOverlays().clear();
			// ����µ�ͼ��
			mapview.getOverlays().add(overlay);
			// ˢ����Ч
			mapview.refresh();
			// �ص�ָ�������
			mapview.getController().animateTo(arg0.getBusRoute().getStart());
			// �����ݴ浽ȫ��
			route = arg0.getBusRoute();
			//Disprote(textview);
			// ������·�ڵ�����
			nodeIndex = 0;

		}

		@Override
		// ���ؼݳ�·���������
		public void onGetDrivingRouteResult(MKDrivingRouteResult arg0, int arg1) {
			// TODO Auto-generated method stub
			// �������յ��޷�ʶ����Ҫѡ����������ʡ��
			if (arg1 == MKEvent.ERROR_ROUTE_ADDR) {
				Toast.makeText(MainActivity.this, "�������յ��޷�ʶ����Ҫѡ���������з�ʽ",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (arg1 != 0 || arg0 == null) {

				Toast.makeText(MainActivity.this, "�ݳ�·��δ�ҵ�ƥ��Ľ��",
						Toast.LENGTH_SHORT).show();
				return;
			}
			searchType = 0;
			routeOverlay = new RouteOverlay(MainActivity.this, mapview);
			routeOverlay.setData(arg0.getPlan(0).getRoute(0));
			// �������ͼ��
			mapview.getOverlays().clear();
			// ���·��ͼ��
			mapview.getOverlays().add(routeOverlay);
			// ִ��ˢ��ʹ��Ч
			mapview.refresh();
			// ʹ��zoomToSpan()���ŵ�ͼ��ʹ·������ȫ��ʾ�ڵ�ͼ��
			mapview.getController().zoomToSpan(routeOverlay.getLatSpanE6(),
					routeOverlay.getLonSpanE6());
			// �ƶ���ͼ�����
			mapview.getController().animateTo(arg0.getStart().pt);
			// ��·�����ݱ����ȫ�ֱ���
			route = arg0.getPlan(0).getRoute(0);
			//Disprote(textview);
			// ����·�߽ڵ��������ڵ����ʱʹ��
			nodeIndex = -1;
		}

		@Override
		public void onGetPoiDetailSearchResult(int arg0, int arg1) {
			// TODO Auto-generated method stub

		}

		@Override //����poi�������
		public void onGetPoiResult(MKPoiResult arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			if (arg1 == MKEvent.ERROR_ROUTE_ADDR) {
				Toast.makeText(MainActivity.this, "�������յ��޷�ʶ����Ҫѡ���������з�ʽ",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (arg1 != 0 || arg0 == null) {

				Toast.makeText(MainActivity.this, "poi·��δ�ҵ�ƥ��Ľ��",
						Toast.LENGTH_SHORT).show();
				return;
			}
			// �ҵ�����·��poi node
			MKPoiInfo curPoi = null;
			int toalePoiNum = arg0.getCurrentNumPois();
			// ��������api�ҵ�poiΪ��������·
			busLineIDList.clear();
			for (int idex = 0; idex < toalePoiNum; idex++) {
				if (2 == arg0.getPoi(idex).ePoiType) {
					// poi���ͣ�0����ͨ�㣬1������վ��2��������·��3������վ��4��������·
					curPoi = arg0.getPoi(idex);
					// ʹ��poi��uid���𹫽��������
					busLineIDList.add(curPoi.uid);

				}
			}
			SearchNextBusline();
			// û���ҵ�������Ϣ
			if (curPoi == null) {
				Toast.makeText(MainActivity.this, "��Ǹ��δ�ҵ����", Toast.LENGTH_LONG)
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
		// ���ع����������
		public void onGetTransitRouteResult(MKTransitRouteResult arg0, int arg1) {
			// TODO Auto-generated method stub
			if (arg1 == MKEvent.ERROR_ROUTE_ADDR) {
				Toast.makeText(MainActivity.this, "�������յ��޷�ʶ����Ҫѡ���������з�ʽ",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (arg1 != 0 || arg0 == null) {

				Toast.makeText(MainActivity.this, "����δ�ҵ�ƥ��Ľ��",
						Toast.LENGTH_SHORT).show();
				return;
			}
			searchType = 1;
			mres = arg0;
			plan_number = arg0.getNumPlan();
			transitOverlay = new TransitOverlay(MainActivity.this, mapview);
			transitOverlay.setData(arg0.getPlan(0));
			// �������ͼ��
			mapview.getOverlays().clear();
			// ���·��ͼ��
			mapview.getOverlays().add(transitOverlay);
			// ִ��ˢ��ʹ��Ч
			mapview.refresh();
			// ʹ��zoomToSpan()���ŵ�ͼ��ʹ·������ȫ��ʾ�ڵ�ͼ��
			mapview.getController().zoomToSpan(transitOverlay.getLatSpanE6(),
					transitOverlay.getLonSpanE6());
			// �ƶ���ͼ�����
			mapview.getController().animateTo(arg0.getStart().pt);
			//Disprote(textview);
			// ����·�߽ڵ��������ڵ����ʱʹ��
			nodeIndex = 0;

		}

		@Override
		// ���ز���·���������
		public void onGetWalkingRouteResult(MKWalkingRouteResult arg0, int arg1) {
			// TODO Auto-generated method stub
			if (arg1 == MKEvent.ERROR_ROUTE_ADDR) {
				Toast.makeText(MainActivity.this, "�������յ��޷�ʶ����Ҫѡ���������з�ʽ",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (arg1 != 0 || arg0 == null) {

				Toast.makeText(MainActivity.this, "����δ�ҵ�ƥ��Ľ��",
						Toast.LENGTH_SHORT).show();
				return;
			}
			searchType = 2;
			routeOverlay = new RouteOverlay(MainActivity.this, mapview);
			routeOverlay.setData(arg0.getPlan(0).getRoute(0));
			// �������ͼ��
			mapview.getOverlays().clear();
			// ����µ�ͼ��
			mapview.getOverlays().add(routeOverlay);
			// ˢ��
			mapview.refresh();
			// ʹ��zoomToSpan�ǵ�ͼ��ȫ��ʾ
			mapview.getController().zoomToSpan(routeOverlay.getLatSpanE6(),
					routeOverlay.getLonSpanE6());
			// �ƶ���ͼ�����
			mapview.getController().animateTo(arg0.getStart().pt);
			// �����ݱ��浽ȫ�ֱ���
			route = arg0.getPlan(0).getRoute(0);
		//	Disprote(textview);
			// ����·�߽ڵ��������ڵ����ʱʹ��
			nodeIndex = -1;
		}

	};

	// ·�߲�ѯ�������
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
			text.setText("һ����" + plan_number + "�ֳ˳���ʽ" + "\n" + "���Ƿ�ʽ"
					+ change_number + "\n");
			for (int i = 0; i < transitOverlay.getAllItem().size() - 1; i++) {
				text.setText(text.getText().toString()
						+ transitOverlay.getItem(i).getTitle() + "\n");
			}
		}
	}

	// ��ѯ��һ����·
	private void SearchNextBusline() {
		if (busLineIndex > busLineIDList.size()) {
			busLineIndex = 0;
		}
		if (busLineIndex >= 0 && busLineIndex < busLineIDList.size()
				&& busLineIDList.size() > 0) {
			mSearch.busLineSearch("����", busLineIDList.get(busLineIndex));
			busLineIndex++;
		}
	}

	private void pathonListeners(View v) {
		route = null;
		routeOverlay = null;
		transitOverlay = null;
		// ������յ��name���и�ֵ��Ҳ����ֱ�Ӷ����긳ֵ����ֵ�����򽫸��������������
		MKPlanNode stNone = new MKPlanNode();
		stNone.name = start.getText().toString();
		MKPlanNode enNode = new MKPlanNode();
		enNode.name = end.getText().toString();
		if (mapcar.equals(v)) {

			mSearch.drivingSearch("����", stNone, "����", enNode);

		} else if (mapbus.equals(v)) {

			mSearch.transitSearch("����", stNone, enNode);

		} else if (mappeo.equals(v)) {

			mSearch.walkingSearch("����", stNone, "����", enNode);

		}
	}

	// ����ͼ��
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

		// ����Դ,׼��overlay ����
		GeoPoint p1 = new GeoPoint((int) (Latitude * 1E6),
				(int) (Longitude * 1E6));
		OverlayItem item1 = new OverlayItem(p1, "����λ��", "");
		
		/**
		 * ����overlayͼ�꣬�粻���ã���ʹ�ô���ItemizedOverlayʱ��Ĭ��ͼ��.
		 */


		// ��item ��ӵ�overlay��
		myOverLayItem.addItem(item1);
		// ��������item���Ա�overlay��reset���������
		mitem = new ArrayList<OverlayItem>();
		mitem.addAll(myOverLayItem.getAllItem());
		// ��overlay �����MapView��
		mapview.getOverlays().add(myOverLayItem);
		// ˢ�µ�ͼ
		mapview.refresh();

	}

	// ǰ����·
	class pre_Onclilk implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Toast.makeText(MainActivity.this, "��һ��", Toast.LENGTH_SHORT).show();
			SearchNode(0);
		}
	}

	class next_Onclilk implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Toast.makeText(MainActivity.this, "��һ��", Toast.LENGTH_SHORT).show();
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
				// ������
				nodeIndex++;
				if (nodeIndex <= -1 || route == null
						|| nodeIndex >= route.getNumSteps())
					return;
				// �ƶ���ָ������������
				mapview.getController().animateTo(
						route.getStep(nodeIndex).getPoint());
				// ��������
				Toast.makeText(MainActivity.this,
						route.getStep(nodeIndex).getContent(), 1000).show();
			}
			// ��һ���ڵ�
			if (porn == 1) {
				// ������
				nodeIndex--;
				if (nodeIndex <= -1 || route == null
						|| nodeIndex >= route.getNumSteps())
					return;
				// �ƶ���ָ������������
				mapview.getController().animateTo(
						route.getStep(nodeIndex).getPoint());
				// ��������
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
			// ��һ���ڵ�
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
		// ��һ���ڵ�

	}

	void BuschangePlan(int i) {
		transitOverlay = new TransitOverlay(MainActivity.this, mapview);
		// �˴���չʾһ��������Ϊʾ��
		transitOverlay.setData(mres.getPlan(i));

		plan_number = mres.getNumPlan();
		// �������ͼ��
		mapview.getOverlays().clear();
		// ���·��ͼ��
		mapview.getOverlays().add(transitOverlay);
		// ִ��ˢ��ʹ��Ч
		mapview.refresh();
		// ʹ��zoomToSpan()���ŵ�ͼ��ʹ·������ȫ��ʾ�ڵ�ͼ��
		mapview.getController().zoomToSpan(transitOverlay.getLatSpanE6(),
				transitOverlay.getLonSpanE6());
		// �ƶ���ͼ�����
		mapview.getController().animateTo(mres.getStart().pt);
		// ����·�߽ڵ��������ڵ����ʱʹ��
		//Disprote(textview);
	}

	/**
	 * 
	 * @author Administrator ��λsdk����
	 */
	private class MyLocationListener implements BDLocationListener {

		

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub

			Log.i("����", "" + location.getLatitude());
			Log.i("ά��", "" + location.getLongitude());
			if (location == null)
				return;

			latitudemap=locationData.latitude = location.getLatitude();// γ��
			longitudemap=locationData.longitude = location.getLongitude();// ����
			
			
			/**
			 * �������ʾ��λ����Ȧ,��accuracy����Ϊ0
			 */
			locationData.accuracy = location.getRadius();// ��ȡ�뾶
			locationData.direction = location.getDerect();// ��λ
			// ���¶�λ����
			myLocationOverlay.setData(locationData);
			// ˢ�µ�ͼ
			mapview.refresh();
			// �״ζ�λ�����ֶ���λ���ƶ�����λ��
			if (isRequest || isFirst) {
				// �ƶ���ͼ����λ��
				mapController.animateTo(new GeoPoint(
						(int) (locationData.latitude * 1e6),
						(int) (locationData.longitude * 1e6)));
				isRequest = false;
			}
			// �״����
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

	// �̳�MyLocationOverlay��дdispatchTapʵ�ֵ������
	public class LocationVoerlay extends MyLocationOverlay {// ��λͼ��

		public LocationVoerlay(MapView mapview) {

			super(mapview);
		}

		@Override
		protected boolean dispatchTap() {
			// TODO Auto-generated method stub
			// �������¼�����������
			popText.setBackgroundResource(R.drawable.pop);
			popText.setText("�ҵ�λ��");
			popupOverlay.showPopup(BMapUtil.getBitmapFromView(popText),
					new GeoPoint((int) (latitudemap * 1e6),
							(int) (longitudemap * 1e6)), 10);
			return true;
		}
   
	}
	//·�߲�ѯ   
	void serchBus() {
		// TODO Auto-generated method stub
		MKPlanNode enNode= new MKPlanNode();
		enNode.pt =new GeoPoint((int)(Latitude*1e6), (int)(Longitude*1e6)) ;
		//stNone.name="˫��";
		MKPlanNode stNone= new MKPlanNode();
		//enNode.name="����·";
		stNone.pt = new GeoPoint((int)(latitudemap * 1e6), (int)(longitudemap*1e6)) ;
		Log.i("1", "" + Longitude);
		Log.i("2", "" + Latitude);
		Log.i("3", "" + locationData.latitude);
		Log.i("4", "" + locationData.longitude);
		mSearch.walkingSearch("����", stNone, "����",enNode);
	}
	OnClickListener RequestLocal = new OnClickListener() {// ��λ����ť�¼�

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			reQuestLocalClick();
		}

	};

	/**
	 * �ֶ�������һ������
	 */
	private void reQuestLocalClick() {
		// TODO Auto-generated method stub
		isRequest = true;
		locationClient.requestLocation();
		Toast.makeText(MainActivity.this, "���ڶ�λ����", Toast.LENGTH_SHORT).show();
	}

	/**
	 * �޸�ͼ��
	 */
	private void changeIcon(Drawable drawable) {
		// ������drawableΪnullʱ��ʹ��Ĭ��
		myLocationOverlay.setMarker(drawable);
		// ˢ��
		mapview.refresh();
	}

	private void creatPopView() {// ��������ͼ��
		viewPop = LayoutInflater.from(MainActivity.this).inflate(
				R.layout.popview, null);
		popText = (TextView) viewPop.findViewById(R.id.textcache);
		// ���ݵ����Ӧ�ص�
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
	static PopupOverlay pop = null;// �������pop

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
		// ��������
		if (!super.onTouchEvent(arg0)) {
			if (pop != null && arg0.getAction() == MotionEvent.ACTION_UP) {
				pop.hidePop();
			}
		}
		return true;
	}

}