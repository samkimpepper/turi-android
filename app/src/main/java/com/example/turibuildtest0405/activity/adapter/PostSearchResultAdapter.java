package com.example.turibuildtest0405.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.turibuildtest0405.R;
import com.example.turibuildtest0405.dto.kakaomap.MapSearchKeywordResult;

import java.util.ArrayList;

public class PostSearchResultAdapter extends BaseAdapter {
    Context context;
    //LayoutInflater layoutInflater;
    ArrayList<MapSearchKeywordResult.Place> data = new ArrayList<>();
    DetailClickCallbackListener callbackListener;

    TextView tvPlaceName, tvCategoryName, tvRoadAddress;

    public PostSearchResultAdapter(DetailClickCallbackListener callbackListener) {
        this.callbackListener = callbackListener;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        context = viewGroup.getContext();

        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_view_search_item, viewGroup, false);
        }


        tvPlaceName  = view.findViewById(R.id.tvPlaceName);
        tvCategoryName = view.findViewById(R.id.tvCategoryName);
        tvRoadAddress = view.findViewById(R.id.tvRoadAddress);

        MapSearchKeywordResult.Place place = data.get(i);

        tvPlaceName.setText(place.getPlace_name());
        tvCategoryName.setText(place.getCategory_name());
        tvRoadAddress.setText(place.getRoad_address_name());

        LinearLayout detail = view.findViewById(R.id.linearLayoutDetail);
        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                callbackListener.callback(data.get(i));
            }
        });

        return view;
    }

    public void addItem(MapSearchKeywordResult.Place place) {
        data.add(place);
    }

    public void clear() {
        data.clear();
    }
}
