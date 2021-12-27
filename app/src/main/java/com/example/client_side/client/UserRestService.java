package com.example.client_side.client;

import com.example.client_side.dto.ChangePersonalInfoDto;
import com.example.client_side.dto.PersonalInfoDto;
import com.example.client_side.dto.RegistrationDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserRestService {
    @POST("/user")
    Call<Void> register(@Body RegistrationDto registration);
    @GET("/user/info")
    Call<PersonalInfoDto> getPersonalInfo();
    @POST("/user/update")
    Call<Void> changeInfo(@Body ChangePersonalInfoDto changePersonalInfoDto);
}
