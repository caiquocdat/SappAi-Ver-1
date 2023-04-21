package com.example.sappai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sappai.fragment.RecentsFragment;

public class RecentsOpenActivity extends AppCompatActivity {
    LinearLayout backLinear,shareLinear;
    TextView replyTv;
    ImageView logoImg;

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
        shareLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reply=replyTv.getText().toString();
                Intent sIntent= new Intent(Intent.ACTION_SEND);
                sIntent.setType("text/plain");
                sIntent.putExtra(Intent.EXTRA_TEXT,reply);
                startActivity(sIntent);
            }
        });
    }

    private void mapping() {
        backLinear=findViewById(R.id.backLinear);
        shareLinear=findViewById(R.id.shareLinear);
        replyTv=findViewById(R.id.replyTv);
        logoImg=findViewById(R.id.logoImg);
    }
}