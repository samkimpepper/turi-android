package com.example.turibuildtest0405.activity.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.turibuildtest0405.R;
import com.example.turibuildtest0405.dto.post.PostSearchDto;
import com.example.turibuildtest0405.util.CommonUtil;

import java.util.ArrayList;

public class SamePlacePostListAdapter extends BaseAdapter {
    Context context;

    ArrayList<PostSearchDto> data = new ArrayList<>();
    DetailClickCallbackListener2 callbackListener;

    TextView tvNickname, tvContent;
    ImageView ivPostImage, ivProfileImage;

    public SamePlacePostListAdapter(DetailClickCallbackListener2 callbackListener) {
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
            view = inflater.inflate(R.layout.list_view_same_place_post_item, viewGroup, false);
        }

        tvNickname = view.findViewById(R.id.SametvNickname);
        tvContent = view.findViewById(R.id.SametvContent);
        ivPostImage = view.findViewById(R.id.SamepostImage);
        ivProfileImage = view.findViewById(R.id.SameProfileImage);

        PostSearchDto post = data.get(i);

        tvNickname.setText(post.getNickname());
        tvContent.setText(post.getContent());
        ivPostImage.setImageBitmap(CommonUtil.resizeImage(context, post.getPostImageUrl(), 90));
        //Glide.with(view).load(post.getPostImageUrl()).into(ivPostImage);
        Log.d("SameAdapterPostImage", "getView: " + post.getPostImageUrl());
        if(post.getProfileImageUrl() != null) {
            Glide.with(view).load(post.getProfileImageUrl()).into(ivProfileImage);
        }

        LinearLayout detail = view.findViewById(R.id.SamelinearLayout);
        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callbackListener.callback(data.get(i));
            }
        });

        return view;
    }

    public void addItem(PostSearchDto post) {
        data.add(post);
    }

    public void clear() {
        data.clear();;
    }
}
