package com.example.turibuildtest0405.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.turibuildtest0405.R;
import com.example.turibuildtest0405.dto.post.PostSearchDto;

import java.util.ArrayList;

public class SearchResultAdapter extends BaseAdapter {
    Context context;
    ArrayList<PostSearchDto> data = new ArrayList<>();
    DetailClickCallbackListener2 callbackListener;

    TextView tvPostId, tvPlaceName, tvRoadAddress, tvPostType, tvContent;


    public SearchResultAdapter(DetailClickCallbackListener2 callbackListener) {
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
            view = inflater.inflate(R.layout.list_view_search_item2, viewGroup, false);
        }

        tvPostId = view.findViewById(R.id.tvPostId);
        tvPlaceName = view.findViewById(R.id.ItvPlaceName);
        tvRoadAddress = view.findViewById(R.id.ItvRoadAddress);
        tvPostType = view.findViewById(R.id.tvPostType);
        tvContent = view.findViewById(R.id.tvContent);

        PostSearchDto dto = data.get(i);

        tvPostId.setText(dto.getPostId().toString());
        tvPlaceName.setText(dto.getPlaceName());
        tvRoadAddress.setText(dto.getRoadAddress());
        tvPostType.setText(dto.getPostType());
        tvContent.setText(dto.getContent());

        // 디테일 클릭 이벤트 정의
        LinearLayout detail = view.findViewById(R.id.linearLayoutDetail2);
        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callbackListener.callback(data.get(i));
            }
        });

        return view;
    }

    public void addItem(PostSearchDto dto) {
        data.add(dto);
    }
}
