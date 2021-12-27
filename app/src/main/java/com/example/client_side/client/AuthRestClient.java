package com.example.client_side.client;

import com.example.client_side.dto.AuthRequestDto;
import com.example.client_side.dto.AuthResponseDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthRestClient {
    @POST("auth")
    Call<AuthResponseDto> login(@Body AuthRequestDto authRequest);
}
