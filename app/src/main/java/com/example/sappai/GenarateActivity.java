package com.example.sappai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.sappai.adapter.GenarateAdapter;
import com.example.sappai.adapter.MessageAdapter;
import com.example.sappai.api.CompletionChoice;
import com.example.sappai.api.CompletionRequest;
import com.example.sappai.api.CompletionResponse;
import com.example.sappai.api.OpenAIAPIService;
import com.example.sappai.model.MessageModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    RecyclerView genarateRcv;
    LinearLayout regenerateLinear, backLinear;
    List<MessageModel> messageList;
    GenarateAdapter genarateAdapter;
    String contentSearch;
    ArrayList<String> allResul;
    private ProgressDialog progressDialog;
    private OpenAIAPIService openAIAPIService;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genarate);
        messageList = new ArrayList<>();
        mapping();
        //setup recycler view
        genarateAdapter = new GenarateAdapter(messageList);
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
        int count = Integer.parseInt(getCount);
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
                finish();
            }
        });

        regenerateLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        addToChat(response, MessageModel.SENT_BY_BOT);
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(GenarateActivity.this, R.style.TransparentProgressDialog);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    void callAPI(String question, int count) {
        //okhttp
//        messageList.add(new MessageModel("Typing... ", MessageModel.SENT_BY_BOT));
        showProgressDialog();
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
                progressDialog.dismiss();
                addResponseCopy("Failed to load response due to " + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("choices");
                        for (int i = 0; i < count; i++) {
                            String result = jsonArray.getJSONObject(i).getString("text");
                            addResponseCopy(result.trim());
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    addResponseCopy("Failed to load response due to ");
                }
            }
        });
    }

    private void mapping() {
        genarateRcv = findViewById(R.id.genarateRcv);
        regenerateLinear = findViewById(R.id.regennerateLinear);
        backLinear = findViewById(R.id.backLinear);
    }
}