package kr.or.womanup.nambu.kwj.podalmap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

//이전 액티비티 : MainActivity
//다음 액티비티 : StoreListActivity
public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {

    String[] permissionList = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    Marker selectedMarker;  //마커
    TextView txt_marker;    //마커에 표시되는 텍스트
    View marker_root_view;  //마커 루트뷰
    GoogleMap gmap;
    LocationManager locationManager;
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        setContentView(R.layout.activity_map);


        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            requestPermissions(permissionList,101);
        } else{
            mapInit();
        }

        btn1 = findViewById(R.id.btn_gps_ok);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_maptostorelist = new Intent(MapActivity.this,StoreListActivity.class);
                startActivity(intent_maptostorelist);
            }
        });
    }

    public void onMapReady(GoogleMap googleMap) {
        gmap=googleMap;
        currentLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int result:grantResults){
            if (result == PackageManager.PERMISSION_DENIED) {
                return;
            }
        }
        mapInit();
    }



    public void mapInit(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment)
                fragmentManager.findFragmentById(R.id.map);
//        MapReadyCallback callback = new MapReadyCallback();
        mapFragment.getMapAsync(this);

    }

    public void currentLocation(){  //onMapReady에서 넘어옴. 위치설정을 위해 setlocation으로 넘어갈것
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_DENIED){
                return;
            }
        }
        Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location location2 = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if(location1!=null){
            setLocation(location1);
        } else if(location2!=null){
            setLocation(location2);
        }

        MapActivity.CurrentLocationListener listener = new MapActivity.CurrentLocationListener();
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)==true){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    1000,10f,listener);
            //GPS는 위성통신인데 그게 안 되는 경우엔 못받으니깐
        }
        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)==true){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    1000,10f,listener);
            //1000:초마다      10f:10미터마다
            //얘는 네트워크
        }
    }


    public void setLocation(Location location){
        //위치 설정. markerview로 갔다가 돌아오고 마커아이템 받아오는 애로 넘어갈것
        Log.d("nambu", "위도 : "+location.getLatitude());
        Log.d("nambu", "경도 : "+location.getLongitude());

        LatLng position = new LatLng(location.getLatitude(),location.getLongitude());
        CameraUpdate update = CameraUpdateFactory.newLatLng(position);
        //카메라업데이트:카메라 옮기는 느낌으로 지도를 옮기는 것. 진짜 카메라 아님
        gmap.moveCamera(update);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16f);    //16배확대
        gmap.animateCamera(zoom);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
                return;
            }
        }
        gmap.setMyLocationEnabled(true); //내 위치가 파란 점으로 나옴

        gmap.setOnMarkerClickListener(this);     //마커 안뜨면 이거랑 이 아래줄 위치를 if문 위로 올려보기...
        gmap.setOnMapClickListener(this);

        setCustomMarkerView();
        getMarkerItems();

    }

    private void setCustomMarkerView(){
        //레이아웃 인플레이터 설정(원하는대로 배치하기 위해), 텍스트뷰 설정
        marker_root_view = LayoutInflater.from(this).inflate(R.layout.marker_layout,null);
        txt_marker = marker_root_view.findViewById(R.id.txt_marker);
    }

    private void getMarkerItems(){
        //여기에 가게 리스트를 받아와야 되는데 내 컴퓨터에선 안 되니까 일단 임시 samplelist로 add해서
        //마커들 생성함
        ArrayList<MapMarkerItem> sampleList = new ArrayList();
        sampleList.add(new MapMarkerItem(37.462778, 126.904806, "가게1"));
        sampleList.add(new MapMarkerItem(37.462432, 126.90817, "가게22"));
        sampleList.add(new MapMarkerItem(37.463775, 126.903509, "가게333"));
        sampleList.add(new MapMarkerItem(37.462401, 126.905319, "가게4444"));
        sampleList.add(new MapMarkerItem(37.464509, 126.903162, "가게55555"));
        sampleList.add(new MapMarkerItem(37.462912, 126.90213, "가게666666"));
        sampleList.add(new MapMarkerItem(37.527523, 126.90213, "가게7777777"));
        sampleList.add(new MapMarkerItem(37.462912, 126.899244, "가게99"));
        sampleList.add(new MapMarkerItem(37.461604, 126.902646, "가게00"));

        for(MapMarkerItem mapMarkerItem : sampleList){
            addMarkerItem(mapMarkerItem, false);
        }
    }

    private Marker addMarker(Marker marker, boolean isSelectedMarker){
        //마커의 이름,위도, 경도 받아와서 마커아이템에 세팅하기 위한 부분. addMarkerItem으로 넘어갈예정
        String name = marker.getTitle();
        double lat = marker.getPosition().latitude;
        double lon = marker.getPosition().longitude;
        MapMarkerItem temp = new MapMarkerItem(lat,lon,name); //MapMarkerItem클래스에 정의한대로 입력
        return addMarkerItem(temp, isSelectedMarker);
    }

    private Marker addMarkerItem(MapMarkerItem mapMarkerItem, boolean isSelectedMarker){
        //addMarker에서 불러오는 부분. MapMarkerItem클래스에 정의된 형식에 맞춰서 내용이 들어가고 생성됨
        LatLng position = new LatLng(mapMarkerItem.getLat(),mapMarkerItem.getLon());
        String name = mapMarkerItem.getName();

        txt_marker.setText(name); //마커 텍스트부분에 가게이름넣기


        if (isSelectedMarker) {
            txt_marker.setBackgroundResource(R.drawable.ic_marker_purple);
            txt_marker.setTextColor(Color.WHITE);
        } else{
            txt_marker.setBackgroundResource(R.drawable.ic_marker_white);
            txt_marker.setTextColor(Color.BLACK);
        }
        MarkerOptions markerOptions = new MarkerOptions()
                .position(position)
                .title(name)
//                .zIndex(1.0f)
                .icon(BitmapDescriptorFactory.fromBitmap(
                        createDrawableFromView(this, marker_root_view)));

        return gmap.addMarker(markerOptions);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        //마커가 아니라 맵 클릭시
        changeSelectedMarker(null);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {   //마커 클릭시 실행
        CameraUpdate center = CameraUpdateFactory.newLatLng(marker.getPosition());
        gmap.animateCamera(center); //마커 누르면 카메라 위치 마커 가운데로 옮김
        changeSelectedMarker(marker);

        return true;
    }

    private void changeSelectedMarker(Marker marker){
        //새로 선언한 private. 선택/비선택시 마커색 변경
        //선택했던 마커 되돌리기
        if(selectedMarker!=null){
            addMarker(selectedMarker, false);
            selectedMarker.remove();
        }
        // 선택한 마커 표시
        if (marker != null) {
            selectedMarker = addMarker(marker, true);
            marker.remove();
        }
    }

    private Bitmap createDrawableFromView(Context context, View view) {
    // View를 Bitmap으로 변환-인터넷상 소스 그대로 써서 아예 안건드림
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }


    class CurrentLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            setLocation(location);
        }
        @Override
        public void onProviderEnabled(@NonNull String provider) {
        }
        @Override
        public void onProviderDisabled(@NonNull String provider) {
        }
    }
}