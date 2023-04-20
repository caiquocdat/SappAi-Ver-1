package com.example.sappai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.sappai.fragment.RecentsFragment;

public class RecentsOpenActivity extends AppCompatActivity {
    LinearLayout backLinear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recents_open);
        mapping();
        backLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // call back
                finish();
            }
        });
    }

    private void mapping() {
        backLinear=findViewById(R.id.backLinear);
    }
}