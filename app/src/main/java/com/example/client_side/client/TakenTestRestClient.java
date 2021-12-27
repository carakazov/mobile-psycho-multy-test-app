package com.example.client_side.client;

import com.example.client_side.dto.GetTakenTestsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TakenTestRestClient {
    @GET("/takenTest/list/{languageCode}")
    Call<GetTakenTestsResponse> getTakenTestsList(@Path("languageCode") String code);
}
