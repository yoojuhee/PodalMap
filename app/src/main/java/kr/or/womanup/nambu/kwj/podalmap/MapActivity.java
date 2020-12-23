package kr.or.womanup.nambu.kwj.podalmap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MapActivity extends AppCompatActivity {

    String[] permissionList = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    GoogleMap map;
    LocationManager locationManager;
    Button btn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        Intent intent = getIntent(); //메인에서 인텐트 받아오게.
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
        MapActivity.MapReadyCallback callback = new MapActivity.MapReadyCallback();
        mapFragment.getMapAsync(callback);
    }

    public void currentLocation(){
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
        Log.d("nambu", "위도 : "+location.getLatitude());
        Log.d("nambu", "경도 : "+location.getLongitude());

        LatLng position = new LatLng(location.getLatitude(),location.getLongitude());
        CameraUpdate update = CameraUpdateFactory.newLatLng(position);
        //카메라업데이트:카메라 옮기는 느낌으로 지도를 옮기는 것. 진짜 카메라 아님
        map.moveCamera(update);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16f);    //16배확대
        map.animateCamera(zoom);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
                return;
            }
        }
        map.setMyLocationEnabled(true); //내 위치가 파란 점으로 나옴
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

    class MapReadyCallback implements OnMapReadyCallback {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            //구글맵이 준비가 되면
            map=googleMap;
            currentLocation();
        }
    }
}