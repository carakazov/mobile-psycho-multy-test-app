package com.example.client_side.client;

import com.example.client_side.dto.ResultDto;
import com.example.client_side.dto.TakenTestDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ResultRestClient {
    @POST("/takenTest/add")
    Call<ResultDto> calculateResult(@Body TakenTestDto takenTest);
}
