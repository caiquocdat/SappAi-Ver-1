package com.example.sappai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.sappai.fragment.ChatFragment;
import com.example.sappai.fragment.ExploreFragment;
import com.example.sappai.fragment.RecentsFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {
    ImageView chatImg,exploreImg,searchImg,profileImg;
    LinearLayout chatBrg,exploreBrg,recentBrg;
    Drawable selectedItem,unSelectItem ;
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient.Builder()
            .callTimeout(60, TimeUnit.SECONDS)
            .build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ChatFragment fragment1= new ChatFragment();
        FragmentTransaction ft1=getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.content,fragment1,"");
        ft1.commit();
        unSelectItem=getResources().getDrawable(R.drawable.shape_item_bottom_bar);
        selectedItem = getResources().getDrawable(R.drawable.shape_item_bottom_bar_selected);
        mapping();
        callAPIChatGpt("How are you?");


        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        chatBrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatBrg.setBackground(selectedItem);
                exploreBrg.setBackground(unSelectItem);
                recentBrg.setBackground(unSelectItem);
                ChatFragment fragment1= new ChatFragment();
                FragmentTransaction ft1=getSupportFragmentManager().beginTransaction();
                ft1.replace(R.id.content,fragment1,"");
                ft1.commit();
            }
        });
        exploreBrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatBrg.setBackground(unSelectItem);
                exploreBrg.setBackground(selectedItem);
                recentBrg.setBackground(unSelectItem);
                ExploreFragment fragment1= new ExploreFragment();
                FragmentTransaction ft1=getSupportFragmentManager().beginTransaction();
                ft1.replace(R.id.content,fragment1,"");
                ft1.commit();
            }
        });

        recentBrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatBrg.setBackground(unSelectItem);
                exploreBrg.setBackground(unSelectItem);
                recentBrg.setBackground(selectedItem);
                RecentsFragment fragment1= new RecentsFragment();
                FragmentTransaction ft1=getSupportFragmentManager().beginTransaction();
                ft1.replace(R.id.content,fragment1,"");
                ft1.commit();
            }
        });
    }

    private void callAPIChatGpt(String question){
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("model","text-davinci-003");
            jsonBody.put("prompt",question);
            jsonBody.put("max_tokens",2800);
            jsonBody.put("temperature",0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(jsonBody.toString(),JSON);
        Request request = new Request.Builder()
                .url(" \n" +
                        "https://api.openai.com/v1/completions")
                .header("Authorization","Bearer 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")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    JSONObject  jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("choices");
                        String result = jsonArray.getJSONObject(0).getString("text");
                        Log.d("quocdat", "onResponse: "+result);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }else{

                }
            }
        });
    }

    private void mapping() {
        chatImg=findViewById(R.id.chatImg);
        exploreImg=findViewById(R.id.exploreImg);
        searchImg=findViewById(R.id.searchImg);
        chatBrg=findViewById(R.id.chatBrg);
        exploreBrg=findViewById(R.id.exploreBrg);
        recentBrg=findViewById(R.id.recentBrg);
        profileImg=findViewById(R.id.profileImg);
    }
}