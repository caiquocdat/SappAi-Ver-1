package com.example.sappai;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sappai.data.DBRecentCopyManager;

public class ProfileActivity extends AppCompatActivity {

    ImageView backImg;
    LinearLayout languageLinear, voiceLinear,clearLinear,backLinear;
    RelativeLayout profileRelative;
    Boolean sound = true;
    DBRecentCopyManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mapping();
        dbManager = new DBRecentCopyManager(ProfileActivity.this);
        backLinear.setOnClickListener(new View.OnClickListener() {
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

//                LinearLayout englishLinear = popupView.findViewById(R.id.englishLinear);
                TextView englishLinear = popupView.findViewById(R.id.englishLinear);
                ImageView soundImg = popupView.findViewById(R.id.soundImg);
                SharedPreferences sharedPreferences = getSharedPreferences("checkSpeak", Context.MODE_PRIVATE);
                int value = sharedPreferences.getInt("check", 1);
                if (value==0) {
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.item_mute);
                    soundImg.setImageBitmap(bitmap);
                }else{
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.item_volum);
                    soundImg.setImageBitmap(bitmap);
                }


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
                soundImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int value = sharedPreferences.getInt("check", 1);
                        if (value==1) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("check", 0);
                            editor.apply();
                            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.item_mute);
                            soundImg.setImageBitmap(bitmap);
                        }else{
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("check", 1);
                            editor.apply();
                            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.item_volum);
                            soundImg.setImageBitmap(bitmap);
                        }
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
        clearLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View customDialogView = LayoutInflater.from(ProfileActivity.this).inflate(R.layout.custom_alert_clear, null);
                View dialogBackground = LayoutInflater.from(ProfileActivity.this).inflate(R.layout.dialog_background, null);
                LinearLayout yesLinear = customDialogView.findViewById(R.id.yesLinear);
                LinearLayout noLinear = customDialogView.findViewById(R.id.noLinear);
                ViewGroup rootLayout = findViewById(android.R.id.content);
                rootLayout.addView(dialogBackground);

                dialogBackground.setVisibility(View.VISIBLE);
                dialogBackground.setAlpha(0.0f);
                dialogBackground.animate().alpha(1.0f).setDuration(300).start();

                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setView(customDialogView);

                final AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                int paddingLeft = (int) getResources().getDimension(R.dimen.popup_margin);
                int paddingRight = (int) getResources().getDimension(R.dimen.popup_margin);
                customDialogView.setPadding(paddingLeft, 0, paddingRight, 0);
                alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                alertDialog.show();

                noLinear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBackground.animate().alpha(0.0f).setDuration(300).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                rootLayout.removeView(dialogBackground);
                            }
                        }).start();
                        alertDialog.dismiss();
                    }
                });
                yesLinear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dbManager.deleteAllData();
                        dialogBackground.animate().alpha(0.0f).setDuration(300).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                rootLayout.removeView(dialogBackground);
                            }
                        }).start();
                        alertDialog.dismiss();
                    }
                });

                alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        dialogBackground.animate().alpha(0.0f).setDuration(300).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                rootLayout.removeView(dialogBackground);
                            }
                        }).start();
                        alertDialog.dismiss();
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
        clearLinear = findViewById(R.id.clearLinear);
        backLinear = findViewById(R.id.backLinear);
    }
}