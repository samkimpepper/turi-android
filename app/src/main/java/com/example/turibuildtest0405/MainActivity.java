package com.example.turibuildtest0405;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.turibuildtest0405.activity.EnjoyActivity;
import com.example.turibuildtest0405.activity.FoodActivity;
import com.example.turibuildtest0405.activity.PostActivity;
import com.example.turibuildtest0405.activity.SettingActivity;
import com.example.turibuildtest0405.activity.SpotActivity;
import com.example.turibuildtest0405.activity.StayActivity;
import com.example.turibuildtest0405.activity.TestActivity;
import com.example.turibuildtest0405.activity.UserActivity;
import com.example.turibuildtest0405.activity.UserInfoActivity;

public class MainActivity extends AppCompatActivity {
    ImageButton imgbtnFood, imgbtnSpot, imgbtnStay, imgbtnEnjoy, imgbtnSetting, imgbtnUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgbtnFood = (ImageButton)findViewById(R.id.imgbtn_Food);
        imgbtnSpot = (ImageButton)findViewById(R.id.imgbtn_Spot);
        imgbtnStay = (ImageButton)findViewById(R.id.imgbtn_Stay);
        imgbtnEnjoy = (ImageButton)findViewById(R.id.imgbtn_Enjoy);
        imgbtnSetting = (ImageButton)findViewById(R.id.imgbtn_Setting);
        imgbtnUser = (ImageButton)findViewById(R.id.imgbtn_User);

        imgbtnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
            }
        });

        imgbtnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                startActivity(intent);
            }
        });

        imgbtnFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FoodActivity.class);
                startActivity(intent);
            }
        });

        imgbtnSpot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SpotActivity.class);
                startActivity(intent);
            }
        });

        imgbtnStay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StayActivity.class);
                startActivity(intent);
            }
        });

        imgbtnEnjoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EnjoyActivity.class);
                startActivity(intent);
            }
        });
//
//        Button btnMoveSearch = findViewById(R.id.btn_search_test);
//        btnMoveSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), PostActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        Button btnMoveLogin = findViewById(R.id.btnMoveLogin);
//        btnMoveLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), TestActivity.class);
//                startActivity(intent);
//            }
//        });

    }


}