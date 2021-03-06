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
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.turibuildtest0405.R;
import com.example.turibuildtest0405.activity.adapter.SearchResultAdapter;
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

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodActivity extends AppCompatActivity implements MapView.CurrentLocationEventListener, MapView.MapViewEventListener{

    RetrofitService retrofitService;
    double x, y;
    MapView mMapView;

    EditText edtSearch;
    ImageView btnSearch;

    SlidingUpPanelLayout layout;
    ListView listView;
    TypeListAdapter adapter;

    TypeDetailClickCallbackListener callbackListener = new TypeDetailClickCallbackListener() {
        @Override
        public void callback(PlaceDto place) {
            retrofitService.postApi.getSamePlacePost(place.getPlaceId()).enqueue(new Callback<ResponseDto.DataList<PostSearchDto>>() {
                @Override
                public void onResponse(Call<ResponseDto.DataList<PostSearchDto>> call, Response<ResponseDto.DataList<PostSearchDto>> response) {
                    Log.d("*****FOODSamePlacePost", "onResponse: " + response.body().getData().get(0).getPlaceName());
                    List<PostSearchDto> data = response.body().getData();
                    ArrayList<PostSearchDto> dataArrayList = new ArrayList<>();
                    dataArrayList.addAll(data);

                    Intent intent = new Intent(getApplicationContext(), SamePlacePostActivity.class);
                    intent.putExtra("PostSearchDto", dataArrayList);
                    intent.putExtra("PlaceDto", place);
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
        setContentView(R.layout.activity_food);

        edtSearch = findViewById(R.id.FoodEdtSearch);
        btnSearch = findViewById(R.id.FoodImgBtnSearch);
        setSearchEvent();

        mMapView = new MapView(this);

        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.FoodmapView);
        mapViewContainer.addView(mMapView);
        mMapView.setCurrentLocationEventListener(this);
        mMapView.setMapViewEventListener(this);

        retrofitService = RetrofitService.getInstance(getApplicationContext());

        if(checkLocationService()) {
            permissionCheck();
        }

        mMapView.setZoomLevel(4, true);

        layout = findViewById(R.id.food_panel);
        listView = findViewById(R.id.FoodlistView);

        adapter = new TypeListAdapter(callbackListener);
        listView.setAdapter(adapter);
    }

    private void setSearchEvent() {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyword = edtSearch.getText().toString();
                if(StringUtils.isBlank(keyword)) {
                    Toast.makeText(FoodActivity.this, "???????????? ???????????????.", Toast.LENGTH_SHORT).show();
                    return;
                }

                retrofitService.placeApi.getPlaceSearchResults(keyword, "food").enqueue(new Callback<ResponseDto.DataList<PlaceDto>>() {
                    @Override
                    public void onResponse(Call<ResponseDto.DataList<PlaceDto>> call, Response<ResponseDto.DataList<PlaceDto>> response) {
                        ResponseDto.DataList<PlaceDto> responseDto = response.body();

                        if(StringUtils.isEmpty(responseDto.getMessage())) {
                            Toast.makeText(FoodActivity.this, "?????? ?????? ??????", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        List<PlaceDto> places = responseDto.getData();
                        if(places.size() == 0) {
                            Toast.makeText(FoodActivity.this, "?????? ?????? ??????", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        adapter.clear();
                        for(PlaceDto place : places) {
                            adapter.addItem(place);
                        }
                        adapter.notifyDataSetChanged();

                        // ?????? ????????? ????????? ??? ????????? ???????????????.
                        createMarker(mMapView, places);
                        moveMapPoint(places.get(0).getX(), places.get(0).getY());
                    }

                    @Override
                    public void onFailure(Call<ResponseDto.DataList<PlaceDto>> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void startTracking() {
        mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
    }

    private void stopTracking() {
        mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
    }

    private void callNearPlacesApi(MapView mapView) {
        // ?????? ???????????? ??????
        retrofitService.placeApi.getNearPlaces(String.valueOf(x), String.valueOf(y), "food").enqueue(new Callback<ResponseDto.DataList<PlaceDto>>() {
            @Override
            public void onResponse(Call<ResponseDto.DataList<PlaceDto>> call, Response<ResponseDto.DataList<PlaceDto>> response) {
                List<PlaceDto> placeDtoList = response.body().getData();
                //Log.d("FoodActivity", "onResponse: " + response.body().getMessage() + placeDtoList.get(0).getPlaceName());
                if(placeDtoList.size() == 0) {
                    return;
                }

                createMarker(mapView, placeDtoList);

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

    private void createMarker(MapView mapView, List<PlaceDto> placeDtoList) {
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

    private void moveMapPoint(double longitude, double latitude) {
        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude);
        mMapView.setMapCenterPoint(mapPoint, true);
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
                Toast.makeText(this, "?????? ????????? ?????????????????????", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "?????? ????????? ?????????????????????", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void permissionCheck() {
        SharedPreferences preference = getPreferences(MODE_PRIVATE);
        boolean isFirstCheck = preference.getBoolean("isFirstPermissionCheck", true);

        // ?????? ?????? ??????
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("?????? ????????? ?????????????????? ?????? ????????? ??????????????????.");
                builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(FoodActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
                    }
                });
                builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            } else {
                // ?????? ?????? ??????
                if(isFirstCheck) {
                    preference.edit().putBoolean("isFirstPermissionCheck", false).apply();
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("?????? ????????? ?????????????????? ???????????? ?????? ????????? ??????????????????.");
                    builder.setPositiveButton("???????????? ??????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:$packageName"));
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
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

    // ??????????????? MapViewEventListener ???????????????
    @Override
    public void onMapViewInitialized(MapView mapView) {

    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {
        Log.d("####??????!!", "onMapViewDragEnded: ");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MapPoint.GeoCoordinate geoCoordinate = mapPoint.getMapPointGeoCoord();
                x = geoCoordinate.longitude;
                y = geoCoordinate.latitude;
                Log.d("*********", "onMapViewDragEnded: " + x + y);
                //stopTracking();
                callNearPlacesApi(mapView);
            }
        }, 2000);
    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {
        Log.d("####??????!!", "onMapViewDragEnded: ");
    }
}