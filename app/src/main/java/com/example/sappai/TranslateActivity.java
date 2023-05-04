package com.example.sappai;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sappai.adapter.FavouritesAdapter;
import com.example.sappai.data.DBManager;
import com.example.sappai.model.Favourites;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class TranslateActivity extends AppCompatActivity {

    LinearLayout gennerateLinear, subtractionLinear, plusLinear,backLinear,languageLinear,addFavouritesLinear;
    ImageView lightImg,iconImg,startImg;
    TextView countTv,titleTv,descripTv,languageTv;
    EditText contentEdt;
    int count;
    String content;
    Favourites favourites;
    DBManager dbManager;
    SQLiteDatabase sqLiteDatabase;
    FavouritesAdapter favouritesAdapter;
    Context context=TranslateActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        mapping();
        dbManager= new DBManager(this);
        mapping();
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

        addFavouritesLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Favourites fv= dbManager.getCurrenFavourite(titleTv.getText().toString());
                    if (fv==null){
                        Toast.makeText(context, "Added...", Toast.LENGTH_SHORT).show();
                        dbManager.insertFavourite(favouritesModel);
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.item_start_yellow);
                        startImg.setImageBitmap(bitmap);

                    }else{
                        Toast.makeText(context, "Deleted...", Toast.LENGTH_SHORT).show();
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


                RenderScript rs = RenderScript.create(TranslateActivity.this);


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

                }else{
                    Intent intent = new Intent(TranslateActivity.this, GenarateActivity.class);
                    intent.putExtra("content_genarate", contentEdt.getText().toString().trim());
                    intent.putExtra("count","1");
                    intent.putExtra("activity",titleTv.getText().toString());
                    intent.putExtra("type",languageTv.getText().toString());
                    intent.putExtra("name_charater","");
                    contentEdt.setText("");
                    startActivity(intent);
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
        backLinear=findViewById(R.id.backLinear);
        languageLinear=findViewById(R.id.languageLinear);
        titleTv=findViewById(R.id.titleTv);
        languageTv=findViewById(R.id.languageTv);
        descripTv=findViewById(R.id.descripTv);
        iconImg=findViewById(R.id.iconImg);
        startImg=findViewById(R.id.startImg);
        addFavouritesLinear = findViewById(R.id.addFavouritesLinear);
    }
}