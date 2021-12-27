package com.example.client_side.client;

import com.example.client_side.dto.TestDto;
import com.example.client_side.dto.TestInfoDto;

import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TestRestClient {
    @GET("test/list/{languageCode}")
    Call<List<TestInfoDto>> getTestList(@Path("languageCode") String languageCode);

    @GET("test/{testId}/{languageCode}")
    Call<TestDto> getTest(@Path("testId") UUID testId, @Path("languageCode") String languageCode);

    @GET("test/default/{languageCode}")
    Call<TestDto> getDefaultTest(@Path("languageCode") String languageCode);
}
