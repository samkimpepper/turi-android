package com.example.turibuildtest0405.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.turibuildtest0405.R;
import com.example.turibuildtest0405.activity.adapter.TypeDetailClickCallbackListener;
import com.example.turibuildtest0405.activity.adapter.TypeListAdapter;
import com.example.turibuildtest0405.dto.PlaceDto;
import com.example.turibuildtest0405.dto.ResponseDto;
import com.example.turibuildtest0405.dto.post.PostSearchDto;
import com.example.turibuildtest0405.util.turiapi.RetrofitService;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StayActivity extends AppCompatActivity implements MapView.CurrentLocationEventListener {
    RetrofitService retrofitService;
    double x, y;
    List<PlaceDto> placeDtoList;
    MapView mMapView;

    SlidingUpPanelLayout layout;
    ListView listView;
    TypeListAdapter adapter;

    TypeDetailClickCallbackListener callbackListener = new TypeDetailClickCallbackListener() {
        @Override
        public void callback(PlaceDto place) {
            retrofitService.postApi.getSamePlacePost(place.getPlaceId()).enqueue(new Callback<ResponseDto.DataList<PostSearchDto>>() {
                @Override
                public void onResponse(Call<ResponseDto.DataList<PostSearchDto>> call, Response<ResponseDto.DataList<PostSearchDto>> response) {
                    Log.d("*****getSamePlacePost", "onResponse: " + response.body().getData().get(0).getPlaceName());
                    List<PostSearchDto> data = response.body().getData();
                    ArrayList<PostSearchDto> dataArrayList = new ArrayList<>();
                    dataArrayList.addAll(data);
                    Intent intent = new Intent(getApplicationContext(), SamePlacePostActivity.class);
                    intent.putExtra("PostSearchDto", dataArrayList);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<ResponseDto.DataList<PostSearchDto>> call, Throwable t) {

                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stay);

        mMapView = new MapView(this);
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.StaymapView);
        mapViewContainer.addView(mMapView);
        mMapView.setCurrentLocationEventListener(this);

        retrofitService = RetrofitService.getInstance(getApplicationContext());

        if(checkLocationService()) {
            permissionCheck();
        } else {

        }

        mMapView.setZoomLevel(4, true);
        //startTracking();

        layout = findViewById(R.id.stay_panel);
        listView = findViewById(R.id.StaylistView);

        adapter = new TypeListAdapter(callbackListener);
        listView.setAdapter(adapter);

    }

    private void startTracking() {
        mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
    }

    private void stopTracking() {
        mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
    }

    private void callNearPlacesApi(MapView mapView) {
        retrofitService.placeApi.getNearPlaces(String.valueOf(x), String.valueOf(y), "stay").enqueue(new Callback<ResponseDto.DataList<PlaceDto>>() {
            @Override
            public void onResponse(Call<ResponseDto.DataList<PlaceDto>> call, Response<ResponseDto.DataList<PlaceDto>> response) {
                placeDtoList = response.body().getData();
                Log.d("StayActivity", "onResponse: " + response.body().getMessage() + placeDtoList.get(0).getPlaceName());

                createMarker(mapView);

                adapter.clear();
                for(PlaceDto place : placeDtoList) {
                    adapter.addItem(place);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseDto.DataList<PlaceDto>> call, Throwable t) {

            }
        });
    }

    private void createMarker(MapView mapView) {
        ArrayList<MapPOIItem> markerArr = new ArrayList<>();
        for(PlaceDto place : placeDtoList) {

            MapPoint point = MapPoint.mapPointWithGeoCoord(place.getY(), place.getX());
            Log.d("##########", "createMarker: "+place.getX() + place.getY());
            MapPOIItem marker = new MapPOIItem();
            marker.setTag(0);
            marker.setItemName(place.getPlaceName());
            marker.setMapPoint(point);
            markerArr.add(marker);
        }
        mapView.addPOIItems(markerArr.toArray(new MapPOIItem[markerArr.size()]));
    }

    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint mapPoint, float v) {
        MapPoint.GeoCoordinate geoCoordinate = mapPoint.getMapPointGeoCoord();
        x = geoCoordinate.longitude;
        y = geoCoordinate.latitude;
        Log.d("*********", "onCurrentLocationUpdate: " + x + y);
        //stopTracking();
        callNearPlacesApi(mapView);
        stopTracking();
        // 이거 처음에만 하고 그만하려면 어떻게 해야할까..
    }

    private boolean checkLocationService() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {

    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1000) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "위치 권한이 승인되었습니다", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "위치 권한이 거절되었습니다", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void permissionCheck() {
        SharedPreferences preference = getPreferences(MODE_PRIVATE);
        boolean isFirstCheck = preference.getBoolean("isFirstPermissionCheck", true);

        // 권한 없는 상태
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("현재 위치를 확인하시려면 위치 권한을 허용해주세요.");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(StayActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            } else {
                // 권한 있는 상태
                if(isFirstCheck) {
                    preference.edit().putBoolean("isFirstPermissionCheck", false).apply();
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("현재 위치를 확인하시려면 설정에서 위치 권한을 허용해주세요.");
                    builder.setPositiveButton("설정으로 이동", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:$packageName"));
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();
                }
            }
        } else {
            startTracking();
        }
    }
}