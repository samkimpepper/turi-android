package com.example.turibuildtest0405.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.turibuildtest0405.R;
import com.example.turibuildtest0405.activity.UserActivity;
import com.example.turibuildtest0405.dto.post.MyPostDto;
import com.example.turibuildtest0405.util.CommonUtil;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class ImageGridAdapter extends BaseAdapter {
    Context context;
    ArrayList<MyPostDto> data = new ArrayList<>();
    ImageDetailClickCallbackListener callbackListener;

    ImageView ivPostImage;

    public ImageGridAdapter(ImageDetailClickCallbackListener callbackListener) {
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
            view = inflater.inflate(R.layout.grid_view_image_item, viewGroup, false);
        }

        ivPostImage = view.findViewById(R.id.ivMyPost);

        MyPostDto dto = data.get(i);

        Glide.with(view).load(dto.getPostImageUrl()).into(ivPostImage);
//        new Thread() {
//            public void run() {
//                try {
//                    ivPostImage.setImageBitmap(CommonUtil.resizeImage(context.getApplicationContext(), dto.getPostImageUrl(), 100));
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//            }
//        }.start();


        LinearLayout detail = view.findViewById(R.id.linearLayoutMyPost);
        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callbackListener.callback(data.get(i));
            }
        });

        return view;
    }

    public void addItem(MyPostDto post) {
        data.add(post);
    }

    public void clear() {
        data.clear();
    }
}
