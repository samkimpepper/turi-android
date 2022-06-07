package com.example.turibuildtest0405.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.turibuildtest0405.R;
import com.example.turibuildtest0405.activity.adapter.SamePlacePostListAdapter;
import com.example.turibuildtest0405.dto.post.PostSearchDto;

import java.util.ArrayList;

public class SamePlacePostActivity extends AppCompatActivity {
    ListView listView;
    SamePlacePostListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_same_place_post);

        ArrayList<PostSearchDto> postList = (ArrayList<PostSearchDto>) getIntent().getSerializableExtra("PostSearchDto");

        listView = findViewById(R.id.SamelistView);
        adapter = new SamePlacePostListAdapter();

        listView.setAdapter(adapter);
    }
}