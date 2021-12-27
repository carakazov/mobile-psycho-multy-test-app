package com.example.client_side.client;

import com.example.client_side.dto.CustomTestDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CustomTestRestClient {
    @POST("/custom")
    Call<Void> createCustomTest(@Body CustomTestDto dto);
}
