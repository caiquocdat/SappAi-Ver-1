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
import com.sapai_4.ai.adapter.LanguageCodeAdapter;
import com.sapai_4.ai.api.OnItemClickListener;
import com.sapai_4.ai.data.DBManager;
import com.sapai_4.ai.model.Favourites;
import com.sapai_4.ai.model.LanguageCodeModel;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Write_Code_Activity extends AppCompatActivity {
    LinearLayout gennerateLinear, subtractionLinear, plusLinear, backLinear, addFavouritesLinear, languageLinear, backgroundLinear;
    ImageView lightImg, iconImg, startImg;
    TextView countTv, titleTv, descripTv, languageTv;
    EditText contentEdt, addCustomEdt;
    int count;
    ScrollView itemScr;
    String content;
    Favourites favourites;
    DBManager dbManager;
    SQLiteDatabase sqLiteDatabase;
    FavouritesAdapter favouritesAdapter;
    Context context = Write_Code_Activity.this;
    private RecyclerView languageRcv;
    private LanguageCodeAdapter languageCodeAdapter;
    private ArrayList<LanguageCodeModel> listLanguage;
    int checkClick=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_code);
        mapping();
//        String data = (String) getIntent().getSerializableExtra("languageCode");
//        if (data==null){
//            languageTv.setText("Dart");
//        } else {
//            languageTv.setText(data);
//        }

        dbManager = new DBManager(this);
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
        itemScr.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                contentEdt.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(contentEdt.getWindowToken(), 0);
                return false;
            }
        });
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
        backLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        languageLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View customDialogView = LayoutInflater.from(Write_Code_Activity.this).inflate(R.layout.custom_alert_language_code, null);
                View dialogBackground = LayoutInflater.from(Write_Code_Activity.this).inflate(R.layout.dialog_background, null);
                listLanguage = new ArrayList<>();
                LanguageCodeModel languageCodeModel = new LanguageCodeModel(0, "Dart");
                LanguageCodeModel languageCodeModel1 = new LanguageCodeModel(1, "Java");
                LanguageCodeModel languageCodeModel2 = new LanguageCodeModel(2, "JavaScript");
                LanguageCodeModel languageCodeModel3 = new LanguageCodeModel(3, "Python");
                LanguageCodeModel languageCodeModel4 = new LanguageCodeModel(4, "C");
                LanguageCodeModel languageCodeModel5 = new LanguageCodeModel(5, "C#");
                LanguageCodeModel languageCodeModel6 = new LanguageCodeModel(6, "C++");
                LanguageCodeModel languageCodeModel7 = new LanguageCodeModel(7, "PHP");
                LanguageCodeModel languageCodeModel8 = new LanguageCodeModel(8, "Shell");
                LanguageCodeModel languageCodeModel9 = new LanguageCodeModel(9, "Ruby");
                LanguageCodeModel languageCodeModel10 = new LanguageCodeModel(10, "Go");
                LanguageCodeModel languageCodeModel11 = new LanguageCodeModel(11, "Swift");
                LanguageCodeModel languageCodeModel12 = new LanguageCodeModel(12, "HTML/CSS");
                LanguageCodeModel languageCodeModel13 = new LanguageCodeModel(13, "SQL");

                listLanguage.add(languageCodeModel);
                listLanguage.add(languageCodeModel1);
                listLanguage.add(languageCodeModel2);
                listLanguage.add(languageCodeModel3);
                listLanguage.add(languageCodeModel4);
                listLanguage.add(languageCodeModel5);
                listLanguage.add(languageCodeModel6);
                listLanguage.add(languageCodeModel7);
                listLanguage.add(languageCodeModel8);
                listLanguage.add(languageCodeModel9);
                listLanguage.add(languageCodeModel10);
                listLanguage.add(languageCodeModel11);
                listLanguage.add(languageCodeModel12);
                listLanguage.add(languageCodeModel13);
                languageRcv = customDialogView.findViewById(R.id.languageRcv);
                languageCodeAdapter = new LanguageCodeAdapter(listLanguage, Write_Code_Activity.this);
                languageRcv.setLayoutManager(new LinearLayoutManager(Write_Code_Activity.this));
                languageRcv.setAdapter(languageCodeAdapter);
                ViewGroup rootLayout = findViewById(android.R.id.content);
                rootLayout.addView(dialogBackground);

                dialogBackground.setVisibility(View.VISIBLE);
                dialogBackground.setAlpha(0.0f);
                dialogBackground.animate().alpha(1.0f).setDuration(300).start();

                AlertDialog.Builder builder = new AlertDialog.Builder(Write_Code_Activity.this);
                builder.setView(customDialogView);

                final AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                languageCodeAdapter.setOnItemClickListener(new OnItemClickListener() {
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


//                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//
//                View popupView = inflater.inflate(R.layout.popup_language_code, null);
//
//                LinearLayout englishLinear = popupView.findViewById(R.id.englishLinear);
//
//                PopupWindow popup = new PopupWindow(
//                        popupView,
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT
//                );
//
//
//                RenderScript rs = RenderScript.create(Write_Code_Activity.this);
//
//
//                ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
//
//
//                View decorView = getWindow().getDecorView().getRootView();
//                decorView.setDrawingCacheEnabled(true);
//                Bitmap bitmap = decorView.getDrawingCache();
//
//
//                Allocation input = Allocation.createFromBitmap(rs, bitmap);
//
//
//                Allocation output = Allocation.createTyped(rs, input.getType());
//
//                blur.setRadius(25f);
//
//
//                blur.setInput(input);
//                blur.forEach(output);
//
//
//                output.copyTo(bitmap);
//
//                popupView.setBackground(new BitmapDrawable(getResources(), bitmap));
//
//
//                popup.setWindowLayoutMode(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                popup.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//                popup.showAtLocation(view, Gravity.CENTER, 0, 0);
//
//                englishLinear.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        popup.dismiss();
//                    }
//                });
//
//
//                View activityRootView = getWindow().getDecorView().getRootView();
//
//                activityRootView.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//                        // Check if the touch event occurred outside of the popup window
//                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                            if (!popup.isShowing()) {
//                                return false;
//                            }
//
//                            Rect popupRect = new Rect();
//                            popup.getContentView().getGlobalVisibleRect(popupRect);
//                            if (!popupRect.contains((int) event.getRawX(), (int) event.getRawY())) {
//                                // Dismiss the popup window
//                                popup.dismiss();
//                                return true;
//                            }
//                        }
//                        return false;
//                    }
//                });

        });


        gennerateLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content = contentEdt.getText().toString();
                if (content.isEmpty()) {
                    View customDialogView = LayoutInflater.from(Write_Code_Activity.this).inflate(R.layout.custom_alert_dialog, null);
                    View dialogBackground = LayoutInflater.from(Write_Code_Activity.this).inflate(R.layout.dialog_background, null);

                    ViewGroup rootLayout = findViewById(android.R.id.content);
                    rootLayout.addView(dialogBackground);

                    dialogBackground.setVisibility(View.VISIBLE);
                    dialogBackground.setAlpha(0.0f);
                    dialogBackground.animate().alpha(1.0f).setDuration(300).start();

                    AlertDialog.Builder builder = new AlertDialog.Builder(Write_Code_Activity.this);

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
                    if(languageTv.getText().toString().trim().equals("Select language")) {
                        Intent intent = new Intent(Write_Code_Activity.this, GenarateActivity.class);
                        intent.putExtra("content_genarate", contentEdt.getText().toString().trim());
                        intent.putExtra("count", "1");
                        intent.putExtra("activity", titleTv.getText().toString());
                        intent.putExtra("type", "Java");
                        intent.putExtra("name_charater", "");
                        startActivity(intent);
                        contentEdt.setText("");
                    }else{
                        Intent intent = new Intent(Write_Code_Activity.this, GenarateActivity.class);
                        intent.putExtra("content_genarate", contentEdt.getText().toString().trim());
                        intent.putExtra("count", "1");
                        intent.putExtra("activity", titleTv.getText().toString());
                        intent.putExtra("type", languageTv.getText().toString());
                        intent.putExtra("name_charater", "");
                        startActivity(intent);
                        contentEdt.setText("");
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
//        subtractionLinear = findViewById(R.id.subtractionLinear);
//        plusLinear = findViewById(R.id.plusLinear);
//        lightImg = findViewById(R.id.lightImg);
//        countTv = findViewById(R.id.countTv);
        contentEdt = findViewById(R.id.contentEdt);
        backLinear = findViewById(R.id.backLinear);
        titleTv = findViewById(R.id.titleTv);
        descripTv = findViewById(R.id.descripTv);
        languageTv = findViewById(R.id.languageTv);
        iconImg = findViewById(R.id.iconImg);
        startImg = findViewById(R.id.startImg);
        addFavouritesLinear = findViewById(R.id.addFavouritesLinear);
        languageLinear = findViewById(R.id.languageLinear);
        backgroundLinear = findViewById(R.id.backgroundLinear);
        itemScr = findViewById(R.id.itemScr);
//        addCustomEdt=findViewById(R.id.addCustomEdt);

    }
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String data = intent.getStringExtra("data");
            int check = intent.getIntExtra("check",0);
            if (data==null){
                languageTv.setText("Select language");
            } else {
                languageTv.setText(data);

            }
        }
    };
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, new IntentFilter("languageCode"));
    }
}