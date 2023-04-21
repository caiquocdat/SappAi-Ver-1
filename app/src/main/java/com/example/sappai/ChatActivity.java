package com.example.sappai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sappai.adapter.MessageAdapter;
import com.example.sappai.model.MessageModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

public class ChatActivity extends AppCompatActivity {
    RecyclerView chatRcv;
    TextView welcomeTextView;
    EditText chatEdt;
    ImageView sendImg;
    List<MessageModel> messageList;
    MessageAdapter messageAdapter;
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

        chatRcv = findViewById(R.id.chatRcv);
        chatEdt = findViewById(R.id.chatEdt);
        sendImg = findViewById(R.id.sendImg);

        //setup recycler view
        messageAdapter = new MessageAdapter(messageList);
        chatRcv.setAdapter(messageAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        chatRcv.setLayoutManager(llm);

        sendImg.setOnClickListener((v)->{
//            String question = messageEditText.getText().toString().trim();
//            addToChat(question,Message.SENT_BY_ME);
//            messageEditText.setText("");
//            callAPI(question);
            welcomeTextView.setVisibility(View.GONE);
        });
    }
}