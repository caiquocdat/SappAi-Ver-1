package com.example.sappai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Load_Screen_3_Activity extends AppCompatActivity {
    LinearLayout continueLinear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_screen3);
        mapping();
        if (!NotificationManagerCompat.from(this).areNotificationsEnabled()) {
            Intent intent = new Intent();
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");

            // Thay "your_package_name" bằng tên package của ứng dụng của bạn
            intent.putExtra("android.provider.extra.APP_PACKAGE", "com.example.sappai");
            startActivity(intent);
        }
        continueLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Load_Screen_3_Activity.this, Load_Screen_4_Activity.class);
                startActivity(intent);
            }
        });
    }
    private void mapping() {
        continueLinear=findViewById(R.id.continueLinear);
    }
}