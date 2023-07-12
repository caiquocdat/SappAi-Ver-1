package com.sapai_4.ai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.sapai_4.ai.R;
import com.sapai_4.ai.adapter.FavouritesAdapter;
import com.sapai_4.ai.adapter.LanguageAdapter;
import com.sapai_4.ai.api.OnItemClickListener;
import com.sapai_4.ai.data.DBManager;
import com.sapai_4.ai.model.Favourites;
import com.sapai_4.ai.model.LanguageModel;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class TranslateActivity extends AppCompatActivity {

    LinearLayout gennerateLinear, subtractionLinear, plusLinear, backLinear, languageLinear, addFavouritesLinear, backgroundLinear;
    ImageView lightImg, iconImg, startImg;
    TextView countTv, titleTv, descripTv, languageTv;
    EditText contentEdt;
    ScrollView itemScr;
    int count;
    String content;
    Favourites favourites;
    DBManager dbManager;
    SQLiteDatabase sqLiteDatabase;
    FavouritesAdapter favouritesAdapter;
    Context context = TranslateActivity.this;
    private RecyclerView languageRcv;
    private LanguageAdapter languageAdapter;
    private ArrayList<LanguageModel> listLanguage;
    int checkClick = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        mapping();
//        String data = (String) getIntent().getSerializableExtra("language");
//        if (data==null){
//            languageTv.setText("English");
//        } else {
//            languageTv.setText(data);
//        }

        dbManager = new DBManager(this);
        mapping();
        int id = dbManager.getLastItemId();
        Favourites favouritesModel = new Favourites(id + 1, titleTv.getText().toString(),
                descripTv.getText().toString(), ImageToByte(iconImg));
        byte[] imageBytes = ImageToByte(iconImg);
        ArrayList<Favourites> favouritesList = dbManager.getAllFavourites();
        favouritesAdapter = new FavouritesAdapter(favouritesList, this);
        try {
            Favourites fv = dbManager.getCurrenFavourite(titleTv.getText().toString());
            if (fv == null) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.item_start_light);
                startImg.setImageBitmap(bitmap);
            } else {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.item_start_yellow);
                startImg.setImageBitmap(bitmap);
            }
        } catch (Exception e) {

        }
        backgroundLinear.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                contentEdt.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(contentEdt.getWindowToken(), 0);
                return false;
            }
        });

        addFavouritesLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Favourites fv = dbManager.getCurrenFavourite(titleTv.getText().toString());
                    if (fv == null) {
                        dbManager.insertFavourite(favouritesModel);
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.item_start_yellow);
                        startImg.setImageBitmap(bitmap);

                    } else {
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.item_start_light);
                        startImg.setImageBitmap(bitmap);
                        dbManager.deleteFavouriteById(titleTv.getText().toString());
                        favouritesList.clear();
                        favouritesList.addAll(dbManager.getAllFavourites());
                        favouritesAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {

                }
            }
        });
        itemScr.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                contentEdt.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(contentEdt.getWindowToken(), 0);
                return false;
            }
        });
        backLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        languageLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View customDialogView = LayoutInflater.from(TranslateActivity.this).inflate(R.layout.custom_alert_language_code, null);
                View dialogBackground = LayoutInflater.from(TranslateActivity.this).inflate(R.layout.dialog_background, null);
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
                languageAdapter = new LanguageAdapter(listLanguage, TranslateActivity.this);
                languageRcv.setLayoutManager(new LinearLayoutManager(TranslateActivity.this));
                languageRcv.setAdapter(languageAdapter);
                ViewGroup rootLayout = findViewById(android.R.id.content);
                rootLayout.addView(dialogBackground);

                dialogBackground.setVisibility(View.VISIBLE);
                dialogBackground.setAlpha(0.0f);
                dialogBackground.animate().alpha(1.0f).setDuration(300).start();

                AlertDialog.Builder builder = new AlertDialog.Builder(TranslateActivity.this);
                builder.setView(customDialogView);

                final AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                registerReceiver(mReceiver, new IntentFilter("language"));
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
        gennerateLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content = contentEdt.getText().toString();
                if (content.isEmpty()) {
                    View customDialogView = LayoutInflater.from(TranslateActivity.this).inflate(R.layout.custom_alert_dialog, null);
                    View dialogBackground = LayoutInflater.from(TranslateActivity.this).inflate(R.layout.dialog_background, null);

                    ViewGroup rootLayout = findViewById(android.R.id.content);
                    rootLayout.addView(dialogBackground);

                    dialogBackground.setVisibility(View.VISIBLE);
                    dialogBackground.setAlpha(0.0f);
                    dialogBackground.animate().alpha(1.0f).setDuration(300).start();

                    AlertDialog.Builder builder = new AlertDialog.Builder(TranslateActivity.this);

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
                            });
                    AlertDialog alertDialog = builder.create();
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
                    alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    Window window = alertDialog.getWindow();
                    WindowManager.LayoutParams layoutParams = window.getAttributes();
                    layoutParams.gravity = Gravity.CENTER;
                    window.setAttributes(layoutParams);
                    alertDialog.show();

                } else {
                    if (languageTv.getText().toString().trim().equals("Select language")) {
                        Intent intent = new Intent(TranslateActivity.this, GenarateActivity.class);
                        intent.putExtra("content_genarate", contentEdt.getText().toString().trim());
                        intent.putExtra("count", "1");
                        intent.putExtra("activity", titleTv.getText().toString());
                        intent.putExtra("type", "English");
                        intent.putExtra("name_charater", "");
                        contentEdt.setText("");
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(TranslateActivity.this, GenarateActivity.class);
                        intent.putExtra("content_genarate", contentEdt.getText().toString().trim());
                        intent.putExtra("count", "1");
                        intent.putExtra("activity", titleTv.getText().toString());
                        intent.putExtra("type", languageTv.getText().toString());
                        intent.putExtra("name_charater", "");
                        contentEdt.setText("");
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private byte[] ImageToByte(ImageView iconImg) {
        Bitmap bitmap = ((BitmapDrawable) iconImg.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] imageBytes = stream.toByteArray();


        return imageBytes;
    }

    private void mapping() {
        gennerateLinear = findViewById(R.id.gennerateLinear);
        contentEdt = findViewById(R.id.contentEdt);
        backLinear = findViewById(R.id.backLinear);
        languageLinear = findViewById(R.id.languageLinear);
        titleTv = findViewById(R.id.titleTv);
        languageTv = findViewById(R.id.languageTv);
        descripTv = findViewById(R.id.descripTv);
        iconImg = findViewById(R.id.iconImg);
        startImg = findViewById(R.id.startImg);
        addFavouritesLinear = findViewById(R.id.addFavouritesLinear);
        backgroundLinear = findViewById(R.id.backgroundLinear);
        itemScr = findViewById(R.id.itemScr);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String data = intent.getStringExtra("data");
            int check = intent.getIntExtra("check", 0);
            if (data == null) {
                languageTv.setText("Select language");
            } else {
                checkClick = check;
                languageTv.setText(data);
            }


        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, new IntentFilter("language"));
    }
}