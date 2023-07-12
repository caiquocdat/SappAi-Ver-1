package com.sapai_4.ai;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.sapai_4.ai.R;
import com.sapai_4.ai.adapter.GenarateAdapter;
import com.sapai_4.ai.api.CompletionChoice;
import com.sapai_4.ai.api.CompletionRequest;
import com.sapai_4.ai.api.CompletionResponse;
import com.sapai_4.ai.api.OpenAIAPIService;
import com.sapai_4.ai.data.DBRecentManager;
import com.sapai_4.ai.model.MessageModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
//import retrofit2.Call;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GenarateActivity extends AppCompatActivity {
    String respone, desWeek, desYear, sku;
    boolean isSuccess = false;
    String option = "year";
    private BillingClient billingClient;
    RecyclerView genarateRcv;
    LinearLayout regenerateLinear, backLinear,notyficationLinear;
    List<MessageModel> messageList;
    GenarateAdapter genarateAdapter;
    String contentSearch;
    ArrayList<String> allResul;
    private ProgressDialog progressDialog;
    private OpenAIAPIService openAIAPIService;
    private Boolean check;
    int count;
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .build();
    String prompt = "Hello, how are you?";
    int maxTokens = 60;
    double temperature = 0.5;
    int n = 3;
    String apiKey = "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";
    DBRecentManager dbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genarate);
        messageList = new ArrayList<>();
        mapping();
        billingClient = BillingClient.newBuilder(GenarateActivity.this)
                .setListener(purchasesUpdatedListener)
                .enablePendingPurchases()
                .build();
        getWeek();
        getYear();
//        SharedPreferences sharedPreferences = getSharedPreferences("checkCopy", Context.MODE_PRIVATE);
//        int check = sharedPreferences.getInt("check", 0);
//        Toast.makeText(this, ""+check, Toast.LENGTH_SHORT).show();
//        if (check==1){
//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    notyficationLinear.setVisibility(View.GONE);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putInt("check", 0);
//                    editor.apply();
//                }
//            },3000);
//        }
        //setup recycler view
        genarateAdapter = new GenarateAdapter(messageList,GenarateActivity.this);
        genarateRcv.setAdapter(genarateAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        genarateRcv.setLayoutManager(llm);
        Intent intent = getIntent();
        String getContent = intent.getStringExtra("content_genarate");
        String getCount = intent.getStringExtra("count");
        String activity = intent.getStringExtra("activity");
        String type = intent.getStringExtra("type");
        String name_charater = intent.getStringExtra("name_charater");
        if (activity.toUpperCase().equals("Write a paragraph".toUpperCase())) {
            contentSearch = "Generate well-written paragraph on any given subject such as " + getContent + " with style " + type;
        } else if (activity.equalsIgnoreCase("Summarize")) {
            contentSearch = "Summary about " + getContent + " with style " + type;
        } else if (activity.equalsIgnoreCase("Improve")) {
            contentSearch = "Rewrite content to make it better and more readable with style " + type + ". " + getContent;
        } else if (activity.equalsIgnoreCase("Translate")) {
            contentSearch = "Translate text from one language to " + type + ". " + getContent;
        } else if (activity.equalsIgnoreCase("Lyrics")) {
            contentSearch = "Generate lyrics of a song about " + getContent + " with style " + type;
        } else if (activity.equalsIgnoreCase("Poem")) {
            contentSearch = "Generate poem about " + getContent + " with style " + type;
        } else if (activity.equalsIgnoreCase("Story")) {
            contentSearch = "Generate stories from themes " + getContent;
        } else if (activity.equalsIgnoreCase("Short Movie")) {
            contentSearch = "Generate script for a movie about  " + getContent;
        } else if (activity.equalsIgnoreCase("Company Bio")) {
            contentSearch = "Telling the story of " + getContent + " " + name_charater + " company with style " + type;
        } else if (activity.equalsIgnoreCase("Name Generator")) {
            contentSearch = "Come up with a great name for my brand or product. I work on " + getContent;
        } else if (activity.equalsIgnoreCase("Slogan")) {
            contentSearch = "Create a catchy slogan for my business. My business work on " + getContent;
        } else if (activity.equalsIgnoreCase("Advertisements")) {
            contentSearch = "Promote about " + getContent + " with style " + type;
        } else if (activity.equalsIgnoreCase("Job Post")) {
            contentSearch = "Write a job description that attracts ideal candidates for position " + getContent + " with style " + type;
        } else if (activity.equalsIgnoreCase("Birthday")) {
            contentSearch = "Send sincere birthday wish for my loved for " + getContent + " with content " + type;
        } else if (activity.equalsIgnoreCase("Apology")) {
            contentSearch = "Make an apology for the mistake of " + getContent + " with style " + type;
        } else if (activity.equalsIgnoreCase("Invitation")) {
            contentSearch = "Write the perfect event invitation. " + getContent + " event with style " + type;
        } else if (activity.equalsIgnoreCase("Pick Up Line")) {
            contentSearch = "Create conversation starters for online dating. Customize my message: " + getContent + ". With style " + type;
        } else if (activity.equalsIgnoreCase("Speech")) {
            contentSearch = "Communicate a thought or message effectively. Speech about " + getContent + " with style " + type;
        } else if (activity.equalsIgnoreCase("Email")) {
            contentSearch = " Write a well-drafted email with a neat structure with subject "+getContent+ "  and style "+type;
        } else if (activity.equalsIgnoreCase("Email Subject")) {
            contentSearch = "Create the best email subject lines to entice people to open emails. Email about " + getContent + " with style " + type;
        } else if (activity.equalsIgnoreCase("Improve Email")) {
            contentSearch = "Make my emails better and more readable with subject " + getContent;
        } else if (activity.equalsIgnoreCase("Tweet")) {
            contentSearch = "Handmade Tweets will grab your readers' attention. Tweet about " + getContent + " with style " + type;
        } else if (activity.equalsIgnoreCase("into Tweet")) {
            contentSearch = "Adjust long text to 280 characters. The text is  " + getContent;
        } else if (activity.equalsIgnoreCase("Linkedin Post")) {
            contentSearch = "Create an attention-grabbing post on LinkedIn. With content is " + getContent + " style " + type;
        } else if (activity.equalsIgnoreCase("Instagram Caption")) {
            contentSearch = "Write good instagram captions to help your audience find. Caption is " + getContent + " with style " + type;
        } else if (activity.equalsIgnoreCase("Tiktok Captions")) {
            contentSearch = "Create view-generating captions for viral TikTok. Caption is " + getContent + " with style " + type;
        } else if (activity.equalsIgnoreCase("Viral Video")) {
            contentSearch = "Create a list of ideas that can go viral. With " + getContent;
        } else if (activity.equalsIgnoreCase("Write Code")) {
            contentSearch = "Write simple websites and apps. About " + getContent + " using " + type + " language";
        } else if (activity.equalsIgnoreCase("Explain Code")) {
            contentSearch = "Explaining a complex piece of code below: " + getContent;
        } else if (activity.equalsIgnoreCase("Recipe")) {
            contentSearch = "Get recipes for the dish below: " + getContent;
        } else if (activity.equalsIgnoreCase("Diet Plan")) {
            contentSearch = "Create a custom meal plan based on your preferences below: " + getContent;
        } else if (activity.equalsIgnoreCase("To Emoji")) {
            contentSearch = "Turn the following movie titles into emojis: " + getContent;
        } else if (activity.equalsIgnoreCase("Tell Joke")) {
            contentSearch = "Write a funny joke to tell your friends. About " + getContent;
        } else if (activity.equalsIgnoreCase("Sentence")) {
            contentSearch = "Complete your sentences at random. The incomplete sentence is: " + getContent;
        } else if (activity.equalsIgnoreCase("Them Fight")) {
            contentSearch = "Character " + getContent + " fighting character " + name_charater + ". Who is the winner";
        } else if (activity.equalsIgnoreCase("Conversation")) {
            contentSearch = "Create a conversation between the following two characters. Character 1 is " + getContent + ", character 2 is " + name_charater;
        } else if (activity.equalsIgnoreCase("Up Words")) {
            contentSearch = "Create a definition for the following word: " + getContent;
        }


//        Toast.makeText(this, type + "-" + contentSearch + "-" + activity, Toast.LENGTH_SHORT).show();
        Log.d("QuocDat", "onCreate: "+contentSearch);
        count = Integer.parseInt(getCount);
//        for (int i = 1; i <= count; i++) {
//            callAPI(getContent);
//        }

//        for (int i = 1; i <= count; i++) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    callAPI(getContent);
//                }
//            }).start();
//        }
//        String test="Tự viết một bài thơ buồn giống thơ Tố Hữu";
//        callAPI(contentSearch);
        callAPI(contentSearch, count);
//        callAPIUseRetrofit();
//        Handler handler=new Handler();
//        int delay = 1000; // in milliseconds
//        for (int i = 1; i <= count; i++) {
//            final int value = i;
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    // Update UI with progress
//                    callAPI(getContent);
//                }
//            }, delay);
//        }

        backLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check==true){
                    finish();
                }
            }
        });

        regenerateLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check==true) {
                    boolean running = true;
                    messageList.clear();
                    genarateAdapter.notifyDataSetChanged();
                    genarateRcv.smoothScrollToPosition(0);
//                Handler handler = new Handler();
//                Runnable runnable = new Runnable() {
//                    @Override
//                    public void run() {
//                        int i;
//                        // Thực hiện tác vụ đồng bộ ở đây
//                        for (i=1;i<=count;i++) {
//                        }   callAPI(getContent);
//                        handler.postDelayed(this, 1000); // Lặp lại tác vụ sau 1 giây
//                    }
//                };
//                handler.post(runnable);

                    callAPI(contentSearch, count);
                }

            }
        });
    }

    public OpenAIAPIService getOpenAIAPIService() {
        if (openAIAPIService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.openai.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            openAIAPIService = retrofit.create(OpenAIAPIService.class);
        }

        return openAIAPIService;
    }

    private void callAPIUseRetrofit() {
        messageList.add(new MessageModel("Typing... ", MessageModel.SENT_BY_BOT));
        CompletionRequest request = new CompletionRequest(prompt, maxTokens, temperature, n);
        retrofit2.Call<CompletionResponse> call = getOpenAIAPIService().getChatCompletion("Bearer " + apiKey, request);
        call.enqueue(new retrofit2.Callback<CompletionResponse>() {
            @Override
            public void onResponse(retrofit2.Call<CompletionResponse> call, retrofit2.Response<CompletionResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(GenarateActivity.this, "ola", Toast.LENGTH_SHORT).show();
                    List<CompletionChoice> choices = response.body().getChoices();
                    for (CompletionChoice choice : choices) {
                        String generatedText = choice.getText();
                        Toast.makeText(GenarateActivity.this, "" + choices.size(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<CompletionResponse> call, Throwable t) {

            }
        });
    }

    void addToChat(String message, String sentBy) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (desYear == null && desWeek == null) {
                    Intent intent = new Intent(GenarateActivity.this, Load_Screen_4_Activity.class);
                    startActivity(intent);
                }
                if (sentBy.equals(MessageModel.SENT_BY_BOT)) {
                    regenerateLinear.setEnabled(true);
                }
                messageList.add(new MessageModel(message, sentBy));
                genarateAdapter.notifyDataSetChanged();
                genarateRcv.smoothScrollToPosition(genarateAdapter.getItemCount());
            }
        });
    }

    void addResponse(String response) {
        messageList.remove(messageList.size() - 1);
        addToChat(response, MessageModel.SENT_BY_BOT);
    }

    void addResponseCopy(String response) {
        messageList.remove(messageList.size() - 1);
        addToChat(response, MessageModel.SENT_BY_BOT);
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(GenarateActivity.this, R.style.TransparentProgressDialog);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    void callAPI(String question, int count) {
        check=false;
        //okhttp
        for(int i=0;i<count;i++) {
            messageList.add(new MessageModel("Typing... ", MessageModel.SENT_BY_BOT));
        }
        regenerateLinear.setEnabled(false);
//        showProgressDialog();
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("model", "text-davinci-003");
            jsonBody.put("prompt", question);
            jsonBody.put("max_tokens", 3800);
            jsonBody.put("temperature", 0.5);
            jsonBody.put("n", count);

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

                addResponseCopy("Failed to load response due to " + e.getMessage());
                check=true;
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("choices");
                        for (int i = 0; i < count; i++) {
                            String result = jsonArray.getJSONObject(i).getString("text");
//                            if (desYear == null && desWeek == null) {
//                                Intent intent = new Intent(GenarateActivity.this, Load_Screen_4_Activity.class);
//                                startActivity(intent);
//                            }
                            addResponseCopy(result.trim());
                        }
                        check=true;



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    addResponseCopy("Failed to load response due to ");
                    check=true;
                }
            }
        });
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
                    Intent intent = new Intent("checkCopy");
                    intent.putExtra("data", "0");
                    context.sendBroadcast(intent);
                }
            },1000);
            notyficationLinear.setVisibility(View.VISIBLE);
            backLinear.setVisibility(View.GONE);
        }

        }
    };
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, new IntentFilter("checkCopy"));
    }
    private PurchasesUpdatedListener purchasesUpdatedListener = new PurchasesUpdatedListener() {
        @Override
        public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> Purchase) {

        }
    };
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
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
//                                if (desYear == null && desWeek == null) {
//                                    Intent intent = new Intent(GenarateActivity.this, Load_Screen_4_Activity.class);
//                                    startActivity(intent);
//                                }
//                                callAPI(contentSearch, count);




                            }
                        });
                    }
                });
            }
        });
    }

    private void mapping() {
        genarateRcv = findViewById(R.id.genarateRcv);
        regenerateLinear = findViewById(R.id.regennerateLinear);
        backLinear = findViewById(R.id.backLinear);
        notyficationLinear = findViewById(R.id.notyficationLinear);
    }
    public void onBackPressed() {
        // Không làm gì, để ngăn người dùng quay lại màn hình trước đó
        if (check==true){
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (billingClient != null) {
            billingClient.endConnection();
        }
    }
}