package com.example.client_side.client;

import com.example.client_side.dto.StatInTimeDto;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface StatisticsRestClient {
    @GET("statistics/{testId}/{languageCode}")
    Call<StatInTimeDto> getMyStatistics(@Path("testId") UUID testId, @Path("languageCode") String languageCode);

}
