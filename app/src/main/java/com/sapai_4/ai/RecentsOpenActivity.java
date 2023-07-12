package com.sapai_4.ai;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.sapai_4.ai.R;

public class RecentsOpenActivity extends AppCompatActivity {
    LinearLayout backLinear,shareLinear;
    TextView replyTv;
    ImageView logoImg;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recents_open);
    }
}