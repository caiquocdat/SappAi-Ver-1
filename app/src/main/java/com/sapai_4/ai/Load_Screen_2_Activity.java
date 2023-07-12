package com.sapai_4.ai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.sapai_4.ai.R;

public class Load_Screen_2_Activity extends AppCompatActivity {
    LinearLayout continueLinear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_screen2);
        mapping();
        continueLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Load_Screen_2_Activity.this, Load_Screen_3_Activity.class);
                startActivity(intent);
            }
        });
    }

    private void mapping() {
        continueLinear=findViewById(R.id.continueLinear);
    }
    @Override
    public void onBackPressed() {
        // Không làm gì, để ngăn người dùng quay lại màn hình trước đó
    }
}