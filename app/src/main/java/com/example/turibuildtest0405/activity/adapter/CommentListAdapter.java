package com.example.turibuildtest0405.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.turibuildtest0405.R;
import com.example.turibuildtest0405.dto.comment.PostCommentDto;
import com.example.turibuildtest0405.util.CommonUtil;

import java.util.ArrayList;

public class CommentListAdapter extends BaseAdapter {
    Context context;
    ArrayList<PostCommentDto> data = new ArrayList<>();

    ImageView ivProfileImage;
    TextView tvNickname, tvContent;

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
            view = inflater.inflate(R.layout.list_view_comment_item, viewGroup, false);
        }

        ivProfileImage = view.findViewById(R.id.ivCommentProfileImage);
        tvNickname = view.findViewById(R.id.tvCommentNickname);
        tvContent = view.findViewById(R.id.tvCommentContent);

        PostCommentDto dto = data.get(i);

        //ivProfileImage.setImageBitmap(CommonUtil.resizeImage(context, dto.getProfileImageUrl(), 90));
        Glide.with(view).load(dto.getProfileImageUrl()).into(ivProfileImage);
        tvNickname.setText(dto.getNickname());
        tvContent.setText(dto.getContent());

        return view;
    }

    public void addItem(PostCommentDto dto) {
        data.add(dto);
    }

    public void focus() {

    }
}
