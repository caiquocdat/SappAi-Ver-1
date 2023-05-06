package com.example.sappai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
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
    LinearLayout shareLinear,backLinear;
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
                dbManager.deleteFavouriteById(getTimeDate);
                messageList.clear();
                Intent intent1= new Intent(Detail_Recent_Activity.this,MainActivity.class);
                startActivity(intent1);
            }
        });

    }

    private void mapping() {
        messageRcv=findViewById(R.id.messageRcv);
        shareLinear=findViewById(R.id.shareLinear);
        backLinear=findViewById(R.id.backLinear);
        deleteImg=findViewById(R.id.deleteImg);
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
}