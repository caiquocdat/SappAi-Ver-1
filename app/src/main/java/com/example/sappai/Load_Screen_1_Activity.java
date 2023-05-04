package com.example.sappai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Load_Screen_1_Activity extends AppCompatActivity {
    LinearLayout continueLinear;
    TextView termTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_screen1);
        mapping();
        String text= "<font color=#969FB0>By continuing, you agree to our</font> <font color=#868788><b>Privacy Policy & Terms of Use</b></font>";
        termTv.setText(Html.fromHtml(text));
        continueLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Load_Screen_1_Activity.this, Load_Screen_2_Activity.class);
                startActivity(intent);
            }
        });
    }

    private void mapping() {
        continueLinear=findViewById(R.id.continueLinear);
        termTv=findViewById(R.id.termTv);
    }
}