package example.dell.jd.GDDT;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import example.dell.jd.R;

public class ShowMapActivity extends AppCompatActivity {

    private MapView mMapView;
    private AMap aMap;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new MyAMapLocationListener();
    private Marker locationMarker;
    private AMapLocation amapLocation;
    private Marker preMarker;
    private Bitmap preBitmap;

    private ImageView mIv;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_map);
        initView();
        //获取地图控件引用


        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);

        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //启动定位
        mLocationClient.startLocation();

        mIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //然后可以移动到定位点,使用animateCamera就有动画效果
                //取出经纬度
                LatLng latLng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
                aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
            }
        });

        showMarker();

        // 定义 Marker 点击事件监听
        AMap.OnMarkerClickListener markerClickListener = new AMap.OnMarkerClickListener() {
            // marker 对象被点击时回调的接口
            // 返回 true 则表示接口已响应事件，否则返回false
            @Override
            public boolean onMarkerClick(Marker marker) {

                if (preMarker != null) {
                    preMarker.setIcon(BitmapDescriptorFactory.fromBitmap(preBitmap));
                }
                //用一个变量记录之前点击过的Marker和图片
                preMarker = marker;
                ArrayList<BitmapDescriptor> icons = marker.getIcons();
                BitmapDescriptor bitmapDescriptor = icons.get(0);
                preBitmap = bitmapDescriptor.getBitmap();

                //给点击的Marker设置不同的图片，以区分
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.charge_no_net));
                return false;
            }
        };
// 绑定 Marker 被点击事件
        aMap.setOnMarkerClickListener(markerClickListener);
    }

    private void initView() {
        mMapView = (MapView) findViewById(R.id.map);
        mIv = (ImageView) findViewById(R.id.iv);
    }


    class MyAMapLocationListener implements AMapLocationListener {

        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息
                    ShowMapActivity.this.amapLocation = amapLocation;
                    //取出经纬度
                    LatLng latLng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
                    String country = amapLocation.getCountry();
                    String province = amapLocation.getProvince();
                    String city = amapLocation.getCity();
                    String street = amapLocation.getStreet();
                    String streetNum = amapLocation.getStreetNum();
                    String address = amapLocation.getAddress();


                    //添加Marker显示定位位置
                    if (locationMarker == null) {
                        //如果是空的添加一个新
                        // 的,icon方法就是设置定位图标，可以自定义
                        locationMarker = aMap.addMarker(new MarkerOptions()
                                .position(latLng)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.charge_has_net)));
                    } else {
                        //已经添加过了，修改位置即可
                        locationMarker.setPosition(latLng);
                    }


                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    }

    private void showMarker() {
        //解析json
        JsonBean jsonBean = new Gson().fromJson(JsonStr.jsonStr, JsonBean.class);
        List<JsonBean.ListBean> list = jsonBean.getList();
        for (int i = 0; i < list.size(); i++) {
            JsonBean.ListBean listBean = list.get(i);
            double lat = listBean.getLat();
            double lng = listBean.getLng();
            LatLng latLng = new LatLng(lat, lng);
            marker = aMap.addMarker(new MarkerOptions().position(latLng).title(listBean.getSite_name())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.charge_has_net)));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }
}
