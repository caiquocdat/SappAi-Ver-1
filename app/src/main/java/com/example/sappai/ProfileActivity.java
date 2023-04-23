package com.example.sappai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

public class ProfileActivity extends AppCompatActivity {

    ImageView backImg;
    LinearLayout languageLinear, voiceLinear;
    RelativeLayout profileRelative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mapping();
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        voiceLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

                View popupView = inflater.inflate(R.layout.popup_voice, null);

                LinearLayout englishLinear = popupView.findViewById(R.id.englishLinear);

                PopupWindow popup = new PopupWindow(
                        popupView,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );


                RenderScript rs = RenderScript.create(ProfileActivity.this);
                ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));


                View decorView = getWindow().getDecorView().getRootView();
                decorView.setDrawingCacheEnabled(true);
                Bitmap bitmap = decorView.getDrawingCache();


                Allocation input = Allocation.createFromBitmap(rs, bitmap);


                Allocation output = Allocation.createTyped(rs, input.getType());

                blur.setRadius(25f);


                blur.setInput(input);
                blur.forEach(output);


                output.copyTo(bitmap);

                popupView.setBackground(new BitmapDrawable(getResources(), bitmap));

                int margin = (int) getResources().getDimension(R.dimen.popup_margin);
                popup.setWindowLayoutMode(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                popup.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                popup.showAtLocation(view, Gravity.CENTER, 0, 0);

                englishLinear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popup.dismiss();
                    }
                });


                View activityRootView = getWindow().getDecorView().getRootView();

                activityRootView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        // Check if the touch event occurred outside of the popup window
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            if (!popup.isShowing()) {
                                return false;
                            }

                            Rect popupRect = new Rect();
                            popup.getContentView().getGlobalVisibleRect(popupRect);
                            if (!popupRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                                // Dismiss the popup window
                                popup.dismiss();
                                return true;
                            }
                        }
                        return false;
                    }
                });
            }
        });
        languageLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

                View popupView = inflater.inflate(R.layout.popup_language, null);

                LinearLayout englishLinear = popupView.findViewById(R.id.englishLinear);

                PopupWindow popup = new PopupWindow(
                        popupView,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );


                RenderScript rs = RenderScript.create(ProfileActivity.this);


                ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));


                View decorView = getWindow().getDecorView().getRootView();
                decorView.setDrawingCacheEnabled(true);
                Bitmap bitmap = decorView.getDrawingCache();


                Allocation input = Allocation.createFromBitmap(rs, bitmap);


                Allocation output = Allocation.createTyped(rs, input.getType());

                blur.setRadius(25f);


                blur.setInput(input);
                blur.forEach(output);


                output.copyTo(bitmap);

                popupView.setBackground(new BitmapDrawable(getResources(), bitmap));


                popup.setWindowLayoutMode(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                popup.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                popup.showAtLocation(view, Gravity.CENTER, 0, 0);

                englishLinear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popup.dismiss();
                    }
                });


                View activityRootView = getWindow().getDecorView().getRootView();

                activityRootView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        // Check if the touch event occurred outside of the popup window
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            if (!popup.isShowing()) {
                                return false;
                            }

                            Rect popupRect = new Rect();
                            popup.getContentView().getGlobalVisibleRect(popupRect);
                            if (!popupRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                                // Dismiss the popup window
                                popup.dismiss();
                                return true;
                            }
                        }
                        return false;
                    }
                });


            }
        });
    }

    private void mapping() {
        backImg = findViewById(R.id.backImg);
        languageLinear = findViewById(R.id.languageLinear);
        voiceLinear = findViewById(R.id.voiceLinear);
        profileRelative = findViewById(R.id.profileRelative);
    }
}