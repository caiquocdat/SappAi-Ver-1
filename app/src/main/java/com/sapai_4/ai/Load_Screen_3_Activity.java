package com.sapai_4.ai;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.LinearLayout;

import com.sapai_4.ai.R;

public class Load_Screen_3_Activity extends AppCompatActivity {
    LinearLayout continueLinear;
    String[] permissions = new String[]{
            Manifest.permission.POST_NOTIFICATIONS
    };

    private static final int PERMISSION_REQUEST_CODE = 200;
    boolean permission_post_notification = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_screen3);
        mapping();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (this.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.POST_NOTIFICATIONS}, 22);
            }
        }

    }
    private void request_notification_api13_permission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (this.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 22);
            }
        }
    }

    private void requesPermissionNotification() {
        if (ContextCompat.checkSelfPermission(Load_Screen_3_Activity.this, permissions[0]) == PackageManager.PERMISSION_GRANTED) {
            permission_post_notification = true;
        } else {
            if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {

            } else {

            }
            requestPermissionLauncherNotification.launch(permissions[0]);
        }
    }

    private ActivityResultLauncher<String> requestPermissionLauncherNotification =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGrandted -> {
                if (isGrandted) {
                    permission_post_notification = true;
                } else {
                    permission_post_notification = false;
                    showPermissionDialog("Notification Permission");
                }
            });

    public void showPermissionDialog(String permission_desc) {
        new AlertDialog.Builder(
                Load_Screen_3_Activity.this
        ).setTitle("Alter for Permission")
                .setPositiveButton("Setting", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }

    private void mapping() {
        continueLinear = findViewById(R.id.continueLinear);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences("CheckPermission", Context.MODE_PRIVATE);
        int value = sharedPreferences.getInt("check", 0);

        continueLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (value == 0) {
//                    View customDialogView = LayoutInflater.from(Load_Screen_3_Activity.this).inflate(R.layout.custom_alert_notification, null);
//                    View dialogBackground = LayoutInflater.from(Load_Screen_3_Activity.this).inflate(R.layout.dialog_background, null);
//                    LinearLayout yesLinear = customDialogView.findViewById(R.id.yesLinear);
//                    LinearLayout noLinear = customDialogView.findViewById(R.id.noLinear);
//                    ViewGroup rootLayout = findViewById(android.R.id.content);
//                    rootLayout.addView(dialogBackground);
//
//                    dialogBackground.setVisibility(View.VISIBLE);
//                    dialogBackground.setAlpha(0.0f);
//                    dialogBackground.animate().alpha(1.0f).setDuration(300).start();
//
//                    AlertDialog.Builder builder = new AlertDialog.Builder(Load_Screen_3_Activity.this);
//                    builder.setView(customDialogView);
//
//                    final AlertDialog alertDialog = builder.create();
//                    alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//                    noLinear.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            dialogBackground.animate().alpha(0.0f).setDuration(300).withEndAction(new Runnable() {
//                                @Override
//                                public void run() {
//                                    rootLayout.removeView(dialogBackground);
//                                }
//                            }).start();
//                            alertDialog.dismiss();
//                        }
//                    });
//                    yesLinear.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            dialogBackground.animate().alpha(0.0f).setDuration(300).withEndAction(new Runnable() {
//                                @Override
//                                public void run() {
//                                    rootLayout.removeView(dialogBackground);
//                                }
//                            }).start();
//                            alertDialog.dismiss();
//                            SharedPreferences.Editor editor = sharedPreferences.edit();
//                            editor.putInt("check", 1);
//                            editor.apply();
//                            Intent intent = new Intent(Load_Screen_3_Activity.this, Load_Screen_4_Activity.class);
//                            startActivity(intent);
//
//                        }
//                    });
//
//
//                    customDialogView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            dialogBackground.animate().alpha(0.0f).setDuration(300).withEndAction(new Runnable() {
//                                @Override
//                                public void run() {
//                                    rootLayout.removeView(dialogBackground);
//                                }
//                            }).start();
//                            alertDialog.dismiss();
//                        }
//                    });
//                    alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                        @Override
//                        public void onCancel(DialogInterface dialogInterface) {
//                            dialogBackground.animate().alpha(0.0f).setDuration(300).withEndAction(new Runnable() {
//                                @Override
//                                public void run() {
//                                    rootLayout.removeView(dialogBackground);
//                                }
//                            }).start();
//                            alertDialog.dismiss();
//                        }
//                    });
//
//                    alertDialog.show();
//                }else{
//                    Intent intent = new Intent(Load_Screen_3_Activity.this, Load_Screen_4_Activity.class);
//                    startActivity(intent);
//                }
                Intent intent = new Intent(Load_Screen_3_Activity.this, Load_Screen_4_Activity.class);
                startActivity(intent);
            }
        });

    }
    private void requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NOTIFICATION_POLICY) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_NOTIFICATION_POLICY)) {

        }

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_NOTIFICATION_POLICY}, 123);
    }


    public void onBackPressed() {
        // Không làm gì, để ngăn người dùng quay lại màn hình trước đó
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Permission granted, you can now display notifications
        } else {
            // Permission denied, handle accordingly
        }
    }
}