package com.example.sappai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Load_Screen_4_Activity extends AppCompatActivity {
    LinearLayout freeLinear,payLinear,continueLinear;
    Drawable selectedItem,unSelectItem ;
    TextView freeTv,skipTv,moneyFreeTv,moneyTv,timeFreeTv,timeTv;
    ImageView checkYearImg,checkWeekImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_screen4);
        SharedPreferences sharedPreferencesCheckApp = getSharedPreferences("CheckApp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencesCheckApp.edit();
        editor.putInt("check", 1);
        editor.apply();
        mapping();
        unSelectItem=getResources().getDrawable(R.drawable.shape_unselecte_package);
        selectedItem = getResources().getDrawable(R.drawable.shape_selected_package);
        freeLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                freeLinear.setBackground(selectedItem);
                payLinear.setBackground(unSelectItem);
                checkWeekImg.setVisibility(View.VISIBLE);
                checkYearImg.setVisibility(View.GONE);
                freeTv.setTextColor(Color.argb(255,255,255,255));
                moneyFreeTv.setTextColor(Color.parseColor("#FFFFFF"));
                timeFreeTv.setTextColor(Color.parseColor("#FFFFFF"));
                moneyTv.setTextColor(Color.parseColor("#969FB0"));
                timeTv.setTextColor(Color.parseColor("#969FB0"));
            }
        });
        payLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payLinear.setBackground(selectedItem);
                freeLinear.setBackground(unSelectItem);
                checkWeekImg.setVisibility(View.GONE);
                checkYearImg.setVisibility(View.VISIBLE);
                freeTv.setTextColor(Color.parseColor("#0073F7"));
                moneyFreeTv.setTextColor(Color.parseColor("#969FB0"));
                timeFreeTv.setTextColor(Color.parseColor("#969FB0"));
                moneyTv.setTextColor(Color.parseColor("#FFFFFF"));
                timeTv.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });
        skipTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Load_Screen_4_Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        continueLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Load_Screen_4_Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void mapping() {
        freeLinear=findViewById(R.id.freeLinear);
        payLinear=findViewById(R.id.payLinear);
        freeTv=findViewById(R.id.freeTv);
        skipTv=findViewById(R.id.skipTv);
        continueLinear=findViewById(R.id.continueLinear);
        moneyFreeTv=findViewById(R.id.moneyFreeTv);
        moneyTv=findViewById(R.id.moneyTv);
        timeFreeTv=findViewById(R.id.timeFreeTv);
        timeTv=findViewById(R.id.timeTv);
        checkWeekImg=findViewById(R.id.checkWeekImg);
        checkYearImg=findViewById(R.id.checkYearImg);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences sharedPreferences = getSharedPreferences("CheckPermission", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("check", 1);
        editor.apply();
    }
    public void onBackPressed() {
        // Không làm gì, để ngăn người dùng quay lại màn hình trước đó
    }
}