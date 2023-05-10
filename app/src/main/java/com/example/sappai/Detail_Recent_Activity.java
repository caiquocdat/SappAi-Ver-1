package com.example.sappai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.sappai.adapter.MessageAdapter;
import com.example.sappai.adapter.MessageRecentAdapter;
import com.example.sappai.data.DBRecentCopyManager;
import com.example.sappai.model.MessageCopyModel;
import com.example.sappai.model.MessageModel;
import com.example.sappai.model.RecentCopyModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Detail_Recent_Activity extends AppCompatActivity {
    DBRecentCopyManager dbManager;
    String timeCreate;
    MessageRecentAdapter messageAdapter;
    ArrayList<MessageCopyModel> messageList;
    RecyclerView messageRcv;
    LinearLayout shareLinear,backLinear,deleteLinear,notyficationLinear;
    ImageView deleteImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recent);
        mapping();
        Intent intent = getIntent();
        String getTime = intent.getStringExtra("time");
        Integer getTimeDate = intent.getIntExtra("timeDate",0);
        dbManager= new DBRecentCopyManager(Detail_Recent_Activity.this);
        ArrayList<RecentCopyModel> recentList = dbManager.getAllFavourites();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        for (int i=0;i<recentList.size();i++){
            String dateString = format.format(recentList.get(i).getTimeCreateChat());
            if (dateString.compareTo(getTime)==0){
                messageList= recentList.get(i).getListMessage();
                messageAdapter = new MessageRecentAdapter(messageList, Detail_Recent_Activity.this);
                messageRcv.setAdapter(messageAdapter);
                LinearLayoutManager llm = new LinearLayoutManager(this);
                llm.setStackFromEnd(true);
                messageRcv.setLayoutManager(llm);
            }
        }
//        ArrayList<MessageCopyModel> messageList=recentList.;
        shareLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = getRecyclerViewAsBitmap(messageRcv);
                shareImage(bitmap);
            }
        });
        backLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // call back
                finish();
            }
        });
        deleteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View customDialogView = LayoutInflater.from(Detail_Recent_Activity.this).inflate(R.layout.custom_alert_clear, null);
                View dialogBackground = LayoutInflater.from(Detail_Recent_Activity.this).inflate(R.layout.dialog_background, null);
                LinearLayout yesLinear = customDialogView.findViewById(R.id.yesLinear);
                LinearLayout noLinear = customDialogView.findViewById(R.id.noLinear);
                ViewGroup rootLayout = findViewById(android.R.id.content);
                rootLayout.addView(dialogBackground);

                dialogBackground.setVisibility(View.VISIBLE);
                dialogBackground.setAlpha(0.0f);
                dialogBackground.animate().alpha(1.0f).setDuration(300).start();

                AlertDialog.Builder builder = new AlertDialog.Builder(Detail_Recent_Activity.this);
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
                        Toast.makeText(Detail_Recent_Activity.this, "Deleted Success...", Toast.LENGTH_SHORT).show();
                        dbManager.deleteFavouriteById(getTimeDate);
                        messageList.clear();
                        finish();
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
        messageRcv=findViewById(R.id.messageRcv);
        shareLinear=findViewById(R.id.shareLinear);
        backLinear=findViewById(R.id.backLinear);
        deleteImg=findViewById(R.id.deleteImg);
        deleteLinear=findViewById(R.id.deleteLinear);
        notyficationLinear=findViewById(R.id.notyficationLinear);
    }
    private Bitmap getRecyclerViewAsBitmap(RecyclerView recyclerView) {
        // Đo chiều rộng và chiều cao của ScrollView
        Bitmap bitmap = Bitmap.createBitmap(recyclerView.getChildAt(0).getWidth(),
                recyclerView.getChildAt(0).getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        recyclerView.getChildAt(0).draw(canvas);
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
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String data = intent.getStringExtra("data");
            if (data.equals("1")){
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        notyficationLinear.setVisibility(View.GONE);
                        backLinear.setVisibility(View.VISIBLE);
                        deleteLinear.setVisibility(View.VISIBLE);
                        Intent intent = new Intent("checkCopy");
                        intent.putExtra("data", "0");
                        context.sendBroadcast(intent);
                    }
                },1000);
                notyficationLinear.setVisibility(View.VISIBLE);
                backLinear.setVisibility(View.GONE);
                deleteLinear.setVisibility(View.GONE);
            }

        }
    };
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, new IntentFilter("checkCopy"));
    }
}