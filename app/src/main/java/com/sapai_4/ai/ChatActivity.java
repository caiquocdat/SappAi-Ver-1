package com.sapai_4.ai;

import androidx.annotation.NonNull;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.billingclient.api.AcknowledgePurchaseResponseListener;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.sapai_4.ai.R;
import com.sapai_4.ai.adapter.MessageAdapter;
import com.sapai_4.ai.data.DBRecentCopyManager;
import com.sapai_4.ai.model.MessageCopyModel;
import com.sapai_4.ai.model.MessageModel;
import com.sapai_4.ai.model.RecentCopyModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
    ImageView sendImg, speakChatImg;
    TextToSpeech t1;
    ArrayList<MessageModel> messageList;
    ArrayList<MessageCopyModel> messageListCopy;
    MessageAdapter messageAdapter;
    LinearLayout backLinear, speakLinear, notyficationLinear, explainText;
    RelativeLayout chatRel;
    private ProgressDialog progressDialog;
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .build();
    DBRecentCopyManager dbManager;
    Boolean check = false;
    Date dateCreateChat;
    String respone, desWeek, desYear, sku;
    boolean isSuccess = false;
    String option = "year";
    private BillingClient billingClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        messageList = new ArrayList<>();
        messageListCopy = new ArrayList<>();
        mapping();

        SharedPreferences sharedPreferences = getSharedPreferences("checkSpeak", Context.MODE_PRIVATE);

        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS) {
                    int language = t1.setLanguage(Locale.ENGLISH);
                }
            }
        });

        //setup recycler view
        messageAdapter = new MessageAdapter(messageList, ChatActivity.this);
        chatRcv.setAdapter(messageAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        chatRcv.setLayoutManager(llm);


        Intent intent = getIntent();
        String getContent = intent.getStringExtra("content");
        String receivedDate = intent.getStringExtra("date");
        dateCreateChat=new Date();
        if (getContent != "") {
            addToChat(getContent, MessageModel.SENT_BY_ME);
            callAPICopy(getContent+" no title");
            getContent = "";
        }
        chatEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int lastPosition = messageAdapter.getItemCount() - 1;
                chatRcv.smoothScrollToPosition(lastPosition);
            }
        });

        chatRel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                chatEdt.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(chatEdt.getWindowToken(), 0);
                return false;
            }
        });
        chatRcv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                chatEdt.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(chatEdt.getWindowToken(), 0);
                return false;
            }
        });
        speakChatImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int value = sharedPreferences.getInt("check", 1);
                if (value == 0) {
//                    String reply = messageListCopy.get(messageListCopy.size() - 1).getMessage();
//                    t1.speak(reply, TextToSpeech.QUEUE_FLUSH, null);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("check", 1);
                    editor.apply();
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.item_loudspeaker_blue);
                    speakChatImg.setImageBitmap(bitmap);
                } else {
                    if (t1 != null) {
                        t1.stop();
                    }
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("check", 0);
                    editor.apply();
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.item_unloudspeaker_blue);
                    speakChatImg.setImageBitmap(bitmap);
                }
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
            check = false;
            String question = chatEdt.getText().toString().trim();
            if (question.length() > 0) {
                addToChat(question, MessageModel.SENT_BY_ME);
                chatEdt.setText("");
                callAPICopy(question+" no title");
            } else {
                check = true;

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
        speakChatImg = findViewById(R.id.speakChatImg);
        chatRel = findViewById(R.id.chatRel);
        notyficationLinear = findViewById(R.id.notyficationLinear);
        speakLinear = findViewById(R.id.speakLinear);
        explainText = findViewById(R.id.explainText);
    }

    void addToChat(String message, String sentBy) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageList.add(new MessageModel(message, sentBy));
                messageListCopy.add(new MessageCopyModel(message, sentBy));
                messageAdapter.notifyDataSetChanged();
                chatRcv.smoothScrollToPosition(messageAdapter.getItemCount());
                if (sentBy.equals(MessageModel.SENT_BY_BOT)) {
                    sendImg.setEnabled(true);
                    SharedPreferences sharedPreferences = getSharedPreferences("checkSpeak", Context.MODE_PRIVATE);
                    int value = sharedPreferences.getInt("check", 1);
                    if (value == 0) {
                        if (t1 != null) {
                            t1.stop();

                        }
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.item_unloudspeaker_blue);
                        speakChatImg.setImageBitmap(bitmap);
                    } else {
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.item_loudspeaker_blue);
                        speakChatImg.setImageBitmap(bitmap);
                        t1.speak(message, TextToSpeech.QUEUE_FLUSH, null);
                    }

                }
            }
        });
    }
    private PurchasesUpdatedListener purchasesUpdatedListener = new PurchasesUpdatedListener() {
        @Override
        public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> Purchase) {

        }
    };
    AcknowledgePurchaseResponseListener acknowledgePurchaseResponseListener = new AcknowledgePurchaseResponseListener() {
        @Override
        public void onAcknowledgePurchaseResponse(@NonNull BillingResult billingResult) {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                isSuccess = true;
                Toast.makeText(ChatActivity.this, "Subscribed", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private boolean verifyValidSignature(String signedData, String signature) {
        try {
            //for old playconsole
            // To get key go to Developer Console > Select your app > Development Tools > Services & APIs.
            //for new play console
            //To get key go to Developer Console > Select your app > Monetize > Monetization setup

            String base64Key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAi8bOfBgwB9A/lMS1Z+0dWDMcrHy/HYj5+OcXPH51UU+o/B0usbi3X3VnLp2EcP3rSXWQfPQuOuM1UqrZVzZtXVGtbcCaD2miXSgv0gxwuzOX3ocGbv3q9XLYCVa8xy2GbeuKfwGwbpoq//LwcgaGMWFFjx+HaABzPv8GkQkreLz8mrhymym4RhRXV2ygki6OHmgn+h54sC4tAwLQyfnWGBXj8fFyr0u/cX3ip6FYlfdRNYtAhFSUeM4Em76kK57I3WkRdbkDK4lh1473zhICeBqXHOs4RR2zTusPyfv3o+b+kuT0/WLEWuuBf6PrZcokusCYfs7rvHFs4Bw1lZqsdwIDAQAB";
            return Security.verifyPurchase(base64Key, signedData, signature);
        } catch (IOException e) {
            return false;
        }
    }

    private void getWeek() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                billingClient.startConnection(new BillingClientStateListener() {
                    @Override
                    public void onBillingServiceDisconnected() {

                    }

                    @Override
                    public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                        List<QueryProductDetailsParams.Product> productList = new ArrayList<>();
                        productList.add(QueryProductDetailsParams.Product.newBuilder()
                                .setProductId("com.sappai.weekly")
                                .setProductType(BillingClient.ProductType.SUBS)
                                .build()
                        );
                        QueryProductDetailsParams params = QueryProductDetailsParams.newBuilder()
                                .setProductList(productList)
                                .build();
                        billingClient.queryProductDetailsAsync(params, (billingResult1, productDetailsList) -> {
                            for (ProductDetails productDetails : productDetailsList) {
                                respone = productDetails.getSubscriptionOfferDetails().get(0).getPricingPhases()
                                        .getPricingPhaseList().get(0).getFormattedPrice();
                                sku = productDetails.getName();
                                String ds = productDetails.getDescription();
                                desWeek = sku + " " + ds + " " + " Price " + respone;
                                Log.d("test2", "onBillingSetupFinished: " + desWeek);

//                                String offerToken = productDetails.getSubscriptionOfferDetails()
//                                        .get(0).getOfferToken();
//                                List<BillingFlowParams.ProductDetailsParams> productDetailsParams = new ArrayList<>();
//                                productDetailsParams.add(BillingFlowParams.ProductDetailsParams.newBuilder()
//                                        .setProductDetails(productDetails)
//                                        .setOfferToken(offerToken)
//                                        .build()
//                                );
//                                BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
//                                        .setProductDetailsParamsList(productDetailsParams)
//                                        .build();
//                                billingClient.launchBillingFlow(IAPActivity.this, billingFlowParams);

                            }

                        });
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }


                            }
                        });
                    }
                });
            }
        });
    }

    private void getYear() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                billingClient.startConnection(new BillingClientStateListener() {
                    @Override
                    public void onBillingServiceDisconnected() {

                    }

                    @Override
                    public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                        List<QueryProductDetailsParams.Product> productList = new ArrayList<>();
                        productList.add(QueryProductDetailsParams.Product.newBuilder()
                                .setProductId("com.sappai.yearly")
                                .setProductType(BillingClient.ProductType.SUBS)
                                .build()
                        );
                        QueryProductDetailsParams params = QueryProductDetailsParams.newBuilder()
                                .setProductList(productList)
                                .build();
                        billingClient.queryProductDetailsAsync(params, (billingResult1, productDetailsList) -> {
                            for (ProductDetails productDetails : productDetailsList) {
                                respone = productDetails.getSubscriptionOfferDetails().get(0).getPricingPhases()
                                        .getPricingPhaseList().get(0).getFormattedPrice();
                                sku = productDetails.getName();
                                String ds = productDetails.getDescription();
                                desYear = sku + " " + ds + " " + " Price " + respone;
                                Log.d("test2", "onBillingSetupFinished: " + desYear);

//                                String offerToken = productDetails.getSubscriptionOfferDetails()
//                                        .get(0).getOfferToken();
//                                List<BillingFlowParams.ProductDetailsParams> productDetailsParams = new ArrayList<>();
//                                productDetailsParams.add(BillingFlowParams.ProductDetailsParams.newBuilder()
//                                        .setProductDetails(productDetails)
//                                        .setOfferToken(offerToken)
//                                        .build()
//                                );
//                                BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
//                                        .setProductDetailsParamsList(productDetailsParams)
//                                        .build();
//                                billingClient.launchBillingFlow(IAPActivity.this, billingFlowParams);

                            }

                        });
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }


                            }
                        });
                    }
                });
            }
        });
    }



    void addResponse(String response) {
        messageListCopy.add(new MessageCopyModel(response, MessageCopyModel.SENT_BY_BOT));
//        RecentCopyModel recentCopyModel= new RecentCopyModel(1,new Date(),messageListCopy);
//        dbManager= new DBRecentCopyManager(ChatActivity.this);
//        dbManager.insertRecent(recentCopyModel);
        for (int i = 0; i < messageListCopy.size(); i++) {
            Log.d("QuocDat", "addResponse: " + messageListCopy.size() + "-" + messageListCopy.get(i).getMessage());
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

//                        addResponse(result.trim());
//                        messageListCopy.add(new MessageCopyModel(result, MessageModel.SENT_BY_BOT));
//                        for (int i = 0; i < messageListCopy.size(); i++) {
//                            Log.d("QuocDat", "onResponse: "+messageListCopy.size()+"-" + messageListCopy.get(i).getMessage().trim());
//                        }

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
        sendImg.setEnabled(false);


        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("model", "text-davinci-003");
            jsonBody.put("prompt", question);
            jsonBody.put("max_tokens", 2800);
            jsonBody.put("temperature", 0.7);
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
                        if (desYear == null && desWeek == null) {
                            Intent intent = new Intent(ChatActivity.this, Load_Screen_4_Activity.class);
                            startActivity(intent);
                        }
                        String result = jsonArray.getJSONObject(0).getString("text");
                        addResponse(result.trim());
                        check = true;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    addResponse("Failed to load response due to " + response.body().toString());
                }
            }
        });


    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String data = intent.getStringExtra("data");
            if (data.equals("1")) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        notyficationLinear.setVisibility(View.GONE);
                        backLinear.setVisibility(View.VISIBLE);
                        speakLinear.setVisibility(View.VISIBLE);
                        Intent intent = new Intent("checkCopy");
                        intent.putExtra("data", "0");
                        context.sendBroadcast(intent);
                    }
                }, 1000);
                notyficationLinear.setVisibility(View.VISIBLE);
                backLinear.setVisibility(View.GONE);
                speakLinear.setVisibility(View.GONE);
            }

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, new IntentFilter("checkCopy"));

    }

    @Override
    protected void onStop() {
        if (check == true) {
            RecentCopyModel recentCopyModel = new RecentCopyModel(1, dateCreateChat, messageListCopy);
            dbManager = new DBRecentCopyManager(ChatActivity.this);
            dbManager.insertRecent(recentCopyModel);
        }
        if (t1 != null) {
            t1.stop();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (t1 != null) {
            t1.stop();
            t1.shutdown();
        }
        if (billingClient != null) {
            billingClient.endConnection();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        billingClient = BillingClient.newBuilder(ChatActivity.this)
                .setListener(purchasesUpdatedListener)
                .enablePendingPurchases()
                .build();
        getWeek();
        getYear();
    }
}