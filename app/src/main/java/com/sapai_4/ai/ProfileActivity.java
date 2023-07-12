package com.sapai_4.ai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sapai_4.ai.api.OnItemClickListener;
import com.sapai_4.ai.data.DBRecentCopyManager;
import com.sapai_4.ai.R;
import com.sapai_4.ai.adapter.LanguageAdapter;
import com.sapai_4.ai.model.LanguageModel;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    ImageView backImg;
    LinearLayout languageLinear, voiceLinear,clearLinear,backLinear;
    RelativeLayout profileRelative;
    Boolean sound = true;
    DBRecentCopyManager dbManager;
    private RecyclerView languageRcv;
    private LanguageAdapter languageAdapter;
    private ArrayList<LanguageModel> listLanguage;

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
                View customDialogView = LayoutInflater.from(ProfileActivity.this).inflate(R.layout.popup_voice, null);
                View dialogBackground = LayoutInflater.from(ProfileActivity.this).inflate(R.layout.dialog_background, null);

//                LinearLayout englishLinear = popupView.findViewById(R.id.englishLinear);
                TextView englishLinear = customDialogView.findViewById(R.id.englishLinear);
                ImageView soundImg = customDialogView.findViewById(R.id.soundImg);
                SharedPreferences sharedPreferences = getSharedPreferences("checkSpeak", Context.MODE_PRIVATE);
                int value = sharedPreferences.getInt("check", 1);
                if (value==0) {
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.item_mute);
                    soundImg.setImageBitmap(bitmap);
                }else{
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.item_volum);
                    soundImg.setImageBitmap(bitmap);
                }


                ViewGroup rootLayout = findViewById(android.R.id.content);
                rootLayout.addView(dialogBackground);

                dialogBackground.setVisibility(View.VISIBLE);
                dialogBackground.setAlpha(0.0f);
                dialogBackground.animate().alpha(1.0f).setDuration(300).start();
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setView(customDialogView);

                final AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                customDialogView.setOnClickListener(new View.OnClickListener() {
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


                englishLinear.setOnClickListener(new View.OnClickListener() {
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
                alertDialog.show();
            }
        });
        languageLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View customDialogView = LayoutInflater.from(ProfileActivity.this).inflate(R.layout.custom_alert_language, null);
                View dialogBackground = LayoutInflater.from(ProfileActivity.this).inflate(R.layout.dialog_background, null);
                listLanguage = new ArrayList<>();
                LanguageModel LanguageModel = new LanguageModel(0, "English");
                LanguageModel LanguageModel1 = new LanguageModel(1, "Albanian");
                LanguageModel LanguageModel2 = new LanguageModel(2, "Arabic");
                LanguageModel LanguageModel3 = new LanguageModel(3, "Armenian");
                LanguageModel LanguageModel4 = new LanguageModel(4, "Azerbaijan");
                LanguageModel LanguageModel5 = new LanguageModel(5, "Belarusian");
                LanguageModel LanguageModel6 = new LanguageModel(6, "Bosnian");
                LanguageModel LanguageModel7 = new LanguageModel(7, "Bulgarian");
                LanguageModel LanguageModel8 = new LanguageModel(8, "Catalan");

                listLanguage.add(LanguageModel);
                listLanguage.add(LanguageModel1);
                listLanguage.add(LanguageModel2);
                listLanguage.add(LanguageModel3);
                listLanguage.add(LanguageModel4);
                listLanguage.add(LanguageModel5);
                listLanguage.add(LanguageModel6);
                listLanguage.add(LanguageModel7);
                listLanguage.add(LanguageModel8);
                languageRcv = customDialogView.findViewById(R.id.languageRcv);
                languageAdapter = new LanguageAdapter(listLanguage, ProfileActivity.this);
                languageRcv.setLayoutManager(new LinearLayoutManager(ProfileActivity.this));
                languageRcv.setAdapter(languageAdapter);
                ViewGroup rootLayout = findViewById(android.R.id.content);
                rootLayout.addView(dialogBackground);

                dialogBackground.setVisibility(View.VISIBLE);
                dialogBackground.setAlpha(0.0f);
                dialogBackground.animate().alpha(1.0f).setDuration(300).start();

                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setView(customDialogView);

                final AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                languageAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        dialogBackground.animate().alpha(0.0f).setDuration(300).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                rootLayout.removeView(dialogBackground);
                            }
                        }).start();
                        alertDialog.dismiss();
                    }
                });

                customDialogView.setOnClickListener(new View.OnClickListener() {
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

                alertDialog.show();
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