package com.example.sappai;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup.MarginLayoutParams;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sappai.adapter.MessageAdapter;
import com.example.sappai.data.DBRecentCopyManager;
import com.example.sappai.data.DBRecentManager;
import com.example.sappai.model.MessageCopyModel;
import com.example.sappai.model.MessageModel;
import com.example.sappai.model.RecentCopyModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
    ImageView sendImg, deleteChatImg;
    ArrayList<MessageModel> messageList;
    ArrayList<MessageCopyModel> messageListCopy;
    MessageAdapter messageAdapter;
    LinearLayout backLinear;
    RelativeLayout chatRel;
    private ProgressDialog progressDialog;
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .build();
    DBRecentCopyManager dbManager;
    Boolean check=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        messageList = new ArrayList<>();
        messageListCopy = new ArrayList<>();
        mapping();
        //setup recycler view
        messageAdapter = new MessageAdapter(messageList, ChatActivity.this);
        chatRcv.setAdapter(messageAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        chatRcv.setLayoutManager(llm);

        Intent intent = getIntent();
        String getContent = intent.getStringExtra("content");
        if (getContent != "") {
            addToChat(getContent, MessageModel.SENT_BY_ME);
            callAPICopy(getContent);
            getContent = "";
        }
        chatRel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                chatEdt.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(chatEdt.getWindowToken(), 0);
                return false;
            }
        });
//        chatEdt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                if(b) {
//                    Toast.makeText(ChatActivity.this, "Leave...", Toast.LENGTH_SHORT).show();
//                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//                }
//            }
//        });

        sendImg.setOnClickListener((v) -> {
            check=false;
            String question = chatEdt.getText().toString().trim();
            if (question.length() > 0) {
                addToChat(question, MessageModel.SENT_BY_ME);
                chatEdt.setText("");
                callAPICopy(question);
            } else {
                View customDialogView = LayoutInflater.from(ChatActivity.this).inflate(R.layout.custom_alert_dialog, null);
                View dialogBackground = LayoutInflater.from(ChatActivity.this).inflate(R.layout.dialog_background, null);

                ViewGroup rootLayout = findViewById(android.R.id.content);
                rootLayout.addView(dialogBackground);

                dialogBackground.setVisibility(View.VISIBLE);
                dialogBackground.setAlpha(0.0f);
                dialogBackground.animate().alpha(1.0f).setDuration(300).start();

                AlertDialog.Builder builder = new AlertDialog.Builder(ChatActivity.this);
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

                alertDialog.show();

//                builder.setView(customDialogView)
//                        .setOnDismissListener(new DialogInterface.OnDismissListener() {
//                            @Override
//                            public void onDismiss(DialogInterface dialogInterface) {
//                                dialogBackground.animate().alpha(0.0f).setDuration(300).withEndAction(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        rootLayout.removeView(dialogBackground);
//                                    }
//                                }).start();
//                            }
//                        })
//                        .show();
            }
        });

//        deleteChatImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                messageList.clear();
//                messageAdapter.notifyDataSetChanged();
//                chatRcv.smoothScrollToPosition(0);
//            }
//        });

        backLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View customDialogView = LayoutInflater.from(ChatActivity.this).inflate(R.layout.custom_alert_leave_chat, null);
                View dialogBackground = LayoutInflater.from(ChatActivity.this).inflate(R.layout.dialog_background, null);
                LinearLayout exitLinear = customDialogView.findViewById(R.id.exitLinear);
                LinearLayout stayLinear = customDialogView.findViewById(R.id.stayLinear);
                ViewGroup rootLayout = findViewById(android.R.id.content);
                rootLayout.addView(dialogBackground);

                dialogBackground.setVisibility(View.VISIBLE);
                dialogBackground.setAlpha(0.0f);
                dialogBackground.animate().alpha(1.0f).setDuration(300).start();

                AlertDialog.Builder builder = new AlertDialog.Builder(ChatActivity.this);
                builder.setView(customDialogView);

                final AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                int paddingLeft = (int) getResources().getDimension(R.dimen.popup_margin);
                int paddingRight = (int) getResources().getDimension(R.dimen.popup_margin);
                customDialogView.setPadding(paddingLeft, 0, paddingRight, 0);
                alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                alertDialog.show();

                stayLinear.setOnClickListener(new View.OnClickListener() {
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
                exitLinear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
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
        chatRcv = findViewById(R.id.chatRcv);
        chatEdt = findViewById(R.id.chatEdt);
        sendImg = findViewById(R.id.sendImg);
        backLinear = findViewById(R.id.backLinear);
        deleteChatImg = findViewById(R.id.deleteChatImg);
        chatRel = findViewById(R.id.chatRel);
    }

    void addToChat(String message, String sentBy) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageList.add(new MessageModel(message, sentBy));
                messageListCopy.add(new MessageCopyModel(message, sentBy));
                messageAdapter.notifyDataSetChanged();
                chatRcv.smoothScrollToPosition(messageAdapter.getItemCount());
            }
        });
    }

    void addResponse(String response) {
        messageListCopy.add(new MessageCopyModel(response, MessageCopyModel.SENT_BY_BOT));
//        RecentCopyModel recentCopyModel= new RecentCopyModel(1,new Date(),messageListCopy);
//        dbManager= new DBRecentCopyManager(ChatActivity.this);
//        dbManager.insertRecent(recentCopyModel);
        for (int i=0;i<messageListCopy.size();i++){
            Log.d("QuocDat", "addResponse: "+messageListCopy.size()+"-"+messageListCopy.get(i).getMessage());
        }
        messageListCopy.remove(messageListCopy.size() - 1);
        messageList.remove(messageList.size() - 1);
        addToChat(response, MessageModel.SENT_BY_BOT);
    }
    void addCopyResponse(String response) {
        messageList.remove(messageList.size() - 1);
        addToChat(response, MessageModel.SENT_BY_BOT);
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(ChatActivity.this, R.style.TransparentProgressDialog);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }


    void callAPI(String question) {
        //okhttp
        messageList.add(new MessageModel("Typing... ", MessageModel.SENT_BY_BOT));
        showProgressDialog();

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
                progressDialog.dismiss();
                addResponse("Failed to load response due to " + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("choices");
                        String result = jsonArray.getJSONObject(0).getString("text");
//                        messageListCopy.add(new MessageCopyModel(result, MessageModel.SENT_BY_BOT));
                        addResponse(result.trim());
                        messageListCopy.add(new MessageCopyModel(result, MessageModel.SENT_BY_BOT));
                        for (int i = 0; i < messageListCopy.size(); i++) {
                            Log.d("QuocDat", "onResponse: "+messageListCopy.size()+"-" + messageListCopy.get(i).getMessage().trim());
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    progressDialog.dismiss();
                    addResponse("Failed to load response due to " + response.body().toString());
                }
            }
        });


    }
    void callAPICopy(String question) {
        //okhttp
        messageList.add(new MessageModel("Typing... ", MessageModel.SENT_BY_BOT));
        showProgressDialog();

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
                progressDialog.dismiss();
                addResponse("Failed to load response due to " + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("choices");
                        String result = jsonArray.getJSONObject(0).getString("text");
                        addResponse(result.trim());
                        check=true;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    progressDialog.dismiss();
                    addResponse("Failed to load response due to " + response.body().toString());
                }
            }
        });


    }

    @Override
    protected void onStop() {
        if(check==true) {
            RecentCopyModel recentCopyModel = new RecentCopyModel(1, new Date(), messageListCopy);
            dbManager = new DBRecentCopyManager(ChatActivity.this);
            dbManager.insertRecent(recentCopyModel);
        }
        super.onStop();
    }


}