package com.sapai_4.ai;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.sapai_4.ai.R;
import com.sapai_4.ai.adapter.FavouritesAdapter;
import com.sapai_4.ai.data.DBManager;
import com.sapai_4.ai.model.Favourites;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Slogan_Activity extends AppCompatActivity {
    LinearLayout gennerateLinear, subtractionLinear, plusLinear,backLinear,addFavouritesLinear,backgroundLinear;
    ImageView lightImg,iconImg,startImg;
    TextView countTv,titleTv,descripTv,funnyTv,wittyTv,friendlyTv,disappointedTv,politeTv,creativeTv,professionalTv;
    EditText contentEdt,addCustomEdt;
    ScrollView itemScr;
    int count;
    String content;
    Favourites favourites;
    DBManager dbManager;
    SQLiteDatabase sqLiteDatabase;
    FavouritesAdapter favouritesAdapter;
    Context context=Slogan_Activity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slogan);
        mapping();
        if(countTv.getText().toString().equals("1")){
            subtractionLinear.setVisibility(View.INVISIBLE);
        }
        dbManager= new DBManager(this);
        int id=dbManager.getLastItemId();
        Favourites favouritesModel=new Favourites(id+1,titleTv.getText().toString(),
                descripTv.getText().toString(),ImageToByte(iconImg));
        byte[] imageBytes = ImageToByte(iconImg);
        ArrayList<Favourites> favouritesList = dbManager.getAllFavourites();
        favouritesAdapter=new FavouritesAdapter(favouritesList,this);
        try {
            Favourites fv= dbManager.getCurrenFavourite(titleTv.getText().toString());
            if (fv==null){
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.item_start_light);
                startImg.setImageBitmap(bitmap);
            }else{
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.item_start_yellow);
                startImg.setImageBitmap(bitmap);
            }
        }catch (Exception e){

        }
        itemScr.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                contentEdt.clearFocus();
                addCustomEdt.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(contentEdt.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(addCustomEdt.getWindowToken(), 0);
                return false;
            }
        });
        funnyTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCustomEdt.setText(funnyTv.getText().toString().trim());
            }
        });
        wittyTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCustomEdt.setText(wittyTv.getText().toString().trim());
            }
        });
        friendlyTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCustomEdt.setText(friendlyTv.getText().toString().trim());
            }
        });
        disappointedTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCustomEdt.setText(disappointedTv.getText().toString().trim());
            }
        });
        politeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCustomEdt.setText(politeTv.getText().toString().trim());
            }
        });
        creativeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCustomEdt.setText(creativeTv.getText().toString().trim());
            }
        });
        professionalTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCustomEdt.setText(professionalTv.getText().toString().trim());
            }
        });
        backgroundLinear.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                contentEdt.clearFocus();
                addCustomEdt.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(contentEdt.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(addCustomEdt.getWindowToken(), 0);
                return false;
            }
        });

        addFavouritesLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Favourites fv= dbManager.getCurrenFavourite(titleTv.getText().toString());
                    if (fv==null){
                        dbManager.insertFavourite(favouritesModel);
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.item_start_yellow);
                        startImg.setImageBitmap(bitmap);

                    }else{
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.item_start_light);
                        startImg.setImageBitmap(bitmap);
                        dbManager.deleteFavouriteById(titleTv.getText().toString());
                        favouritesList.clear();
                        favouritesList.addAll(dbManager.getAllFavourites());
                        favouritesAdapter.notifyDataSetChanged();
                    }
                }catch (Exception e){

                }
            }
        });
        backLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        subtractionLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count > 1) {
                    plusLinear.setVisibility(View.VISIBLE);
                    count = Integer.parseInt(countTv.getText().toString());
                    count = count - 1;
                    countTv.setText(count + "");
                }
                if(count==1){
                    subtractionLinear.setVisibility(View.INVISIBLE);
                }
            }
        });
        plusLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count < 10) {
                    subtractionLinear.setVisibility(View.VISIBLE);
                    count = Integer.parseInt(countTv.getText().toString());
                    count = count + 1;
                    countTv.setText(count + "");
                }
                if(count==10){
                    plusLinear.setVisibility(View.INVISIBLE);
                }
            }
        });

        lightImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View customDialogView = LayoutInflater.from(Slogan_Activity.this).inflate(R.layout.custom_alert_dialog_light, null);
                View dialogBackground = LayoutInflater.from(Slogan_Activity.this).inflate(R.layout.dialog_background, null);
                Button okBtn = customDialogView.findViewById(R.id.okBtn);
                ViewGroup rootLayout = findViewById(android.R.id.content);
                rootLayout.addView(dialogBackground);

                dialogBackground.setVisibility(View.VISIBLE);
                dialogBackground.setAlpha(0.0f);
                dialogBackground.animate().alpha(1.0f).setDuration(300).start();


                AlertDialog.Builder builder = new AlertDialog.Builder(Slogan_Activity.this);

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
                //show AlertDialog nằm ở bottom
                AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                Window window = alertDialog.getWindow();
                WindowManager.LayoutParams layoutParams = window.getAttributes();
                layoutParams.gravity = Gravity.BOTTOM;
                window.setAttributes(layoutParams);
                alertDialog.show();
                okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

            }
        });
        gennerateLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content = contentEdt.getText().toString();
                if (content.isEmpty()) {
                    View customDialogView = LayoutInflater.from(Slogan_Activity.this).inflate(R.layout.custom_alert_dialog, null);
                    View dialogBackground = LayoutInflater.from(Slogan_Activity.this).inflate(R.layout.dialog_background, null);

                    ViewGroup rootLayout = findViewById(android.R.id.content);
                    rootLayout.addView(dialogBackground);

                    dialogBackground.setVisibility(View.VISIBLE);
                    dialogBackground.setAlpha(0.0f);
                    dialogBackground.animate().alpha(1.0f).setDuration(300).start();

                    AlertDialog.Builder builder = new AlertDialog.Builder(Slogan_Activity.this);

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

                }else{
                    Intent intent = new Intent(Slogan_Activity.this, GenarateActivity.class);
                    intent.putExtra("content_genarate", contentEdt.getText().toString().trim());
                    intent.putExtra("count",countTv.getText().toString().trim());
                    intent.putExtra("activity",titleTv.getText().toString());
                    intent.putExtra("type",addCustomEdt.getText().toString());
                    intent.putExtra("name_charater","");
                    startActivity(intent);
                    contentEdt.setText("");
                    addCustomEdt.setText("");
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
        subtractionLinear = findViewById(R.id.subtractionLinear);
        plusLinear = findViewById(R.id.plusLinear);
        lightImg = findViewById(R.id.lightImg);
        countTv = findViewById(R.id.countTv);
        contentEdt = findViewById(R.id.contentEdt);
        backLinear=findViewById(R.id.backLinear);
        titleTv=findViewById(R.id.titleTv);
        descripTv=findViewById(R.id.descripTv);
        iconImg=findViewById(R.id.iconImg);
        startImg=findViewById(R.id.startImg);
        addFavouritesLinear = findViewById(R.id.addFavouritesLinear);
        addCustomEdt=findViewById(R.id.addCustomEdt);
        backgroundLinear=findViewById(R.id.backgroundLinear);
        funnyTv=findViewById(R.id.funnyTv);
        wittyTv=findViewById(R.id.wittyTv);
        friendlyTv=findViewById(R.id.friendlyTv);
        disappointedTv=findViewById(R.id.disappointedTv);
        politeTv=findViewById(R.id.politeTv);
        creativeTv=findViewById(R.id.creativeTv);
        professionalTv=findViewById(R.id.professionalTv);
        itemScr=findViewById(R.id.itemScr);

    }
}