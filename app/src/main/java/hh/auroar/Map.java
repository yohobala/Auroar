package hh.auroar;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import hh.auroar.clusterutil.clustering.ClusterItem;
import hh.auroar.clusterutil.clustering.ClusterManager;

public class Map extends AppCompatActivity {
    //声明定位设置
    LocationClientOption locationOption = new LocationClientOption();
    //声明LocationClient类
    public LocationClient LocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    public BaiduMap mBaiduMap;
    //声明坐标
    private LatLng point;
    private double latitude;
    public double longitude;


    //布局文件声明
    //声明地图图层
    private MapView mMapView = null;
    private Button MapMarker;
    private Button test;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        //设置地图参数
        BaiduMapOptions options = new BaiduMapOptions();
        options.mapType(BaiduMap.MAP_TYPE_NORMAL);//普通2D地图
        //创建地图，并把设置参数加上
        mMapView = new MapView(this, options);
        mMapView=findViewById(R.id.bmapView);
        //设置mBbaiMap,并开启定位图层
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        // 删除百度地图LoGo
        mMapView.removeViewAt(1);


        //注册监听函数
        LocationClient = new LocationClient(getApplicationContext());
        LocationClient.registerLocationListener(myListener);

        /*统一申请权限*/
        List<String> permissionList  = new ArrayList<>();
        if(ContextCompat.checkSelfPermission(this,Manifest.
                permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(this,Manifest.
                permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(this,Manifest.
                permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(!permissionList.isEmpty()){
            String [] permissions= permissionList.toArray(new String[permissionList.
                    size()]);
            /*使用ActivityCompat 统一申请权限 */
            ActivityCompat.requestPermissions(this,permissions,1);
        }else {
            /*开始定位*/


            LocationOption();
            LocationClient.start();


        }




        //在地图上添加当前位置的标记（暂定）
        MapMarker=findViewById(R.id.Marker);
        markerclick();



        //点击标记出现新窗口
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener(){
            public boolean onMarkerClick(Marker marker) {
                Intent i=new Intent(Map.this, MainActivity.class);
                startActivity(i);
                return  true;
            }
        });

    }
    public void markerclick()
    {
        MapMarker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出提示
                Toast.makeText(Map.this,"请双击地图，生成标记",Toast.LENGTH_SHORT).show();
                BaiduMap.OnMapDoubleClickListener listener = new BaiduMap.OnMapDoubleClickListener() {
                    /**
                     * 地图双击事件监听回调函数
                     *
                     * @param point 双击的地理坐标
                     */
                    @Override
                    public void onMapDoubleClick(LatLng point) {
                        //构建Marker图标
                        BitmapDescriptor bitmap = BitmapDescriptorFactory
                                .fromResource(R.drawable.marker);
//构建MarkerOption，用于在地图上添加Marker
                        OverlayOptions option = new MarkerOptions()
                                .position(point)
                                .icon(bitmap);
//在地图上添加Marker，并显示
                        mBaiduMap.addOverlay(option);
                    }
                };
                //设置地图双击事件监听
                mBaiduMap.setOnMapDoubleClickListener(listener);


            }
        });
    }
    //定位的设置
    private void  LocationOption(){
        locationOption.setOpenGps(true); // 打开gps
        locationOption.setScanSpan(0);//设置发起定位请求的间隔，int类型，单位ms
        //如果设置为0，则代表单次定位，即仅定位一次，默认为0
        //如果设置非0，需设置1000ms以上才有效
        locationOption.setCoorType("bd09ll");//设置返回经纬度坐标类型
        locationOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy：高精度；
        //LocationMode. Battery_Saving：低功耗；
        //LocationMode. Device_Sensors：仅使用设备；
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        LocationClient.setLocOption(locationOption);

    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //获取纬度信息
            latitude = location.getLatitude();
            //获取经度信息
            longitude = location.getLongitude();
            //获取定位精度，默认值为0.0f
            float radius = location.getRadius();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
            String coorType = location.getCoorType();
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
            int errorCode = location.getLocType();
            //使地图出现自己当前位置的标记
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            //使地图中心为当前位置
            point = new LatLng(latitude,longitude);
            MapStatusUpdate status1 = MapStatusUpdateFactory.newLatLng(point);
            mBaiduMap.setMapStatus(status1);


        }
    }








    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
    @Override
    protected void onDestroy() {
        LocationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

}
