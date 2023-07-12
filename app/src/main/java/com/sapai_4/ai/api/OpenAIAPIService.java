package com.sapai_4.ai.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface OpenAIAPIService {
    @Headers("Content-Type: application/json")
    @POST("v1/completions")
    Call<CompletionResponse> getChatCompletion(
            @Header("Authorization") String apiKey,
            @Body CompletionRequest request
    );
}
