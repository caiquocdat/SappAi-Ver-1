package com.example.sappai;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class Content_Write_Activity extends AppCompatActivity {
    LinearLayout gennerateLinear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_write);
        mapping();
        gennerateLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View customDialogView = LayoutInflater.from(Content_Write_Activity.this).inflate(R.layout.custom_alert_dialog, null);
                View dialogBackground = LayoutInflater.from(Content_Write_Activity.this).inflate(R.layout.dialog_background, null);

                ViewGroup rootLayout = findViewById(android.R.id.content);
                rootLayout.addView(dialogBackground);

                dialogBackground.setVisibility(View.VISIBLE);
                dialogBackground.setAlpha(0.0f);
                dialogBackground.animate().alpha(1.0f).setDuration(300).start();

                AlertDialog.Builder builder = new AlertDialog.Builder(Content_Write_Activity.this);

                builder.setView(customDialogView)
                        .setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialogInterface) {
                                dialogBackground.animate().alpha(0.0f).setDuration(300).withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        rootLayout.removeView(dialogBackground);
                                    }
                                }).start();
                            }
                        }).show();
                //show AlertDialog nằm ở bottom
//                AlertDialog alertDialog = builder.create();
//                Window window = alertDialog.getWindow();
//                WindowManager.LayoutParams layoutParams = window.getAttributes();
//                layoutParams.gravity = Gravity.BOTTOM;
//                window.setAttributes(layoutParams);
//                alertDialog.show();

            }
        });
    }

    private void mapping() {
        gennerateLinear=findViewById(R.id.gennerateLinear);
    }
}