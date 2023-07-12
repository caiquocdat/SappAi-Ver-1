package com.sapai_4.ai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.sapai_4.ai.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        SharedPreferences sharedPreferences = getSharedPreferences("CheckPermission", Context.MODE_PRIVATE);
        int value = sharedPreferences.getInt("check", 0);
        SharedPreferences sharedPreferencesCheckApp = getSharedPreferences("CheckApp", Context.MODE_PRIVATE);

        int valueCheckApp = sharedPreferencesCheckApp.getInt("check", 0);

        final Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (valueCheckApp == 0) {
                    Intent intent = new Intent(SplashActivity.this, Load_Screen_1_Activity.class);
                    startActivity(intent);
                }else if(valueCheckApp==1&&value==0){
                    Intent intent = new Intent(SplashActivity.this, Load_Screen_3_Activity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(SplashActivity.this, Load_Screen_4_Activity.class);
                    startActivity(intent);
                }
            }
        },1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(SplashActivity.this, Load_Screen_1_Activity.class);
//                startActivity(intent);
//                finish();
//            }
//        }, 1000);
    }

    public void onBackPressed() {
        // Không làm gì, để ngăn người dùng quay lại màn hình trước đó
    }
}