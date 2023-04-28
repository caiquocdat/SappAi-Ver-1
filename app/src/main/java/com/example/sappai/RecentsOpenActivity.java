package com.example.sappai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sappai.fragment.RecentsFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class RecentsOpenActivity extends AppCompatActivity {
    LinearLayout backLinear,shareLinear;
    TextView replyTv;
    ImageView logoImg;
    ScrollView scrollView;

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
//                String reply=replyTv.getText().toString();
//                Intent sIntent= new Intent(Intent.ACTION_SEND);
//                sIntent.setType("text/plain");
//                sIntent.putExtra(Intent.EXTRA_TEXT,reply);
//                startActivity(sIntent);
                Bitmap bitmap = getScrollViewAsBitmap(scrollView);
                shareImage(bitmap);
            }
        });
    }
    private Bitmap getScrollViewAsBitmap(ScrollView scrollView) {
        // Đo chiều rộng và chiều cao của ScrollView
        Bitmap bitmap = Bitmap.createBitmap(scrollView.getChildAt(0).getWidth(),
                scrollView.getChildAt(0).getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        scrollView.getChildAt(0).draw(canvas);
        return bitmap;
    }
    private void shareImage(Bitmap bitmap) {
        // Lưu bitmap vào bộ nhớ ngoài
        String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "title", null);
        Uri bitmapUri = Uri.parse(bitmapPath);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
        startActivity(Intent.createChooser(intent, "Share"));
    }


    private void mapping() {
        backLinear=findViewById(R.id.backLinear);
        shareLinear=findViewById(R.id.shareLinear);
        replyTv=findViewById(R.id.replyTv);
        logoImg=findViewById(R.id.logoImg);
        scrollView=findViewById(R.id.scrollView);
    }
}