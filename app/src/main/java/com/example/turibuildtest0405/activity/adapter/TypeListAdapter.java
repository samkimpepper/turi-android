package com.example.turibuildtest0405.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.turibuildtest0405.R;
import com.example.turibuildtest0405.dto.PlaceDto;
import com.example.turibuildtest0405.dto.post.PostSearchDto;

import java.util.ArrayList;

public class TypeListAdapter extends BaseAdapter {
    Context context;
    ArrayList<PlaceDto> data = new ArrayList<>();
    TypeDetailClickCallbackListener callbackListener;

    TextView tvMainTitle, tvSubTitle, tvEtc;

    public TypeListAdapter(TypeDetailClickCallbackListener callbackListener) {
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
            view = inflater.inflate(R.layout.list_view_type_item, viewGroup, false);
        }

        tvMainTitle = view.findViewById(R.id.tv_MainTitle);
        tvSubTitle = view.findViewById(R.id.tv_SubTitle);
        tvEtc = view.findViewById(R.id.tv_Etc);

        PlaceDto place = data.get(i);

        tvMainTitle.setText(place.getPlaceName());
        tvSubTitle.setText(place.getRoadAddress());

        tvEtc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callbackListener.callback(data.get(i));
            }
        });

        return view;
    }

    public void addItem(PlaceDto place) {
        data.add(place);
    }

    public void clear() {
        data.clear();
    }
}
