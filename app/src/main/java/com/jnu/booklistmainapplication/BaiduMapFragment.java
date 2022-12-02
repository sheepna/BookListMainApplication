package com.jnu.booklistmainapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;


import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.jnu.booklistmainapplication.Data.HttpDataLoader;
import com.jnu.booklistmainapplication.Data.ShopLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogRecord;

public class BaiduMapFragment extends Fragment {

    private MapView mapView;
    public BaiduMapFragment() {
        // Required empty public constructor
    }

    public static BaiduMapFragment newInstance() {
        BaiduMapFragment fragment = new BaiduMapFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_baidu_map, container, false);
        mapView=rootView.findViewById(R.id.bmapView);
        //地图缩放级别
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.zoom(18.0f);
        mapView.getMap().setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

        LatLng cenpt = new LatLng(22.255925,113.541112);
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(cenpt)
                .zoom(18)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        mapView.getMap().setMapStatus(MapStatusUpdateFactory.newMapStatus(mMapStatus));
        //将maker添加到地图
        //不要在主线程添加太费时的事件
        //不能在子线程更新界面
        //线程不要动界面的东西，不要更新view，由子线程切换到UI线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                //在子线程下载数据
                HttpDataLoader dataLoader=new HttpDataLoader();
                String shopJsonData= dataLoader.getHtml("http://file.nidama.net/class/mobile_develop/data/bookstore2022.json");
                ArrayList<ShopLocation> locations=dataLoader.ParseJsonData(shopJsonData);
                //在UI更新界面
                Handler mainHandler = new Handler(Looper.getMainLooper());
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //已在主线程中，可以更新UI
                        AddMarkersOnMap(locations);
                    }
                });

            }
        }).start();
        //设置点击事件
        mapView.getMap().setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(BaiduMapFragment.this.getContext(), "Marker clicked", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        return rootView;
    }

    private void AddMarkersOnMap(List<ShopLocation> locations) {
        BitmapDescriptor bitmap= BitmapDescriptorFactory.fromResource(R.mipmap.ic_lo);
        for(ShopLocation shop: locations){
            LatLng shopPoint=new LatLng(shop.getLatitude(),shop.getLongitude());
            OverlayOptions options = new MarkerOptions().position(shopPoint).icon(bitmap);//图片
            mapView.getMap().addOverlay(options);
            //文字
            mapView.getMap().addOverlay(new TextOptions().bgColor(0xAAFFFF00)
                    .fontSize(32)
                    .fontColor(0xFFFF00FF).text(shop.getName()).position(shopPoint));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mapView.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();
    }
}