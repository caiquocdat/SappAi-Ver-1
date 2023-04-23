package com.example.sappai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sappai.adapter.MessageAdapter;
import com.example.sappai.model.MessageModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatActivity extends AppCompatActivity {
    RecyclerView chatRcv;
    EditText chatEdt;
    ImageView sendImg,deleteChatImg;
    List<MessageModel> messageList;
    MessageAdapter messageAdapter;
    LinearLayout backLinear;
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        messageList = new ArrayList<>();
        mapping();
        //setup recycler view
        messageAdapter = new MessageAdapter(messageList);
        chatRcv.setAdapter(messageAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        chatRcv.setLayoutManager(llm);

        Intent intent = getIntent();
        String getContent = intent.getStringExtra("content");
        if (getContent != "") {
        addToChat(getContent, MessageModel.SENT_BY_ME);
            callAPI(getContent);
            getContent = "";
        }

        sendImg.setOnClickListener((v) -> {
            String question = chatEdt.getText().toString().trim();
            if(question.length()>1) {
                addToChat(question, MessageModel.SENT_BY_ME);
                chatEdt.setText("");
                callAPI(question);
            }
        });

        deleteChatImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageList.clear();
                messageAdapter.notifyDataSetChanged();
                chatRcv.smoothScrollToPosition(0);
            }
        });

        backLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void mapping() {
        chatRcv = findViewById(R.id.chatRcv);
        chatEdt = findViewById(R.id.chatEdt);
        sendImg = findViewById(R.id.sendImg);
        backLinear=findViewById(R.id.backLinear);
        deleteChatImg=findViewById(R.id.deleteChatImg);
    }

    void addToChat(String message, String sentBy) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageList.add(new MessageModel(message, sentBy));
                messageAdapter.notifyDataSetChanged();
                chatRcv.smoothScrollToPosition(messageAdapter.getItemCount());
            }
        });
    }

    void addResponse(String response) {
        messageList.remove(messageList.size() - 1);
        addToChat(response, MessageModel.SENT_BY_BOT);
    }

    void callAPI(String question) {
        //okhttp
        messageList.add(new MessageModel("Typing... ", MessageModel.SENT_BY_BOT));

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("model", "text-davinci-003");
            jsonBody.put("prompt", question);
            jsonBody.put("max_tokens", 2800);
            jsonBody.put("temperature", 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(jsonBody.toString(), JSON);
        Request request = new Request.Builder()
                .url(" \n" +
                        "https://api.openai.com/v1/completions")
                .header("Authorization", "Bearer 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")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                addResponse("Failed to load response due to " + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("choices");
                        String result = jsonArray.getJSONObject(0).getString("text");
                        addResponse(result.trim());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    addResponse("Failed to load response due to " + response.body().toString());
                }
            }
        });


    }
}