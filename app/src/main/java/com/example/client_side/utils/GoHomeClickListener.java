package com.example.client_side.utils;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.example.client_side.activity.MyPageActivity;
import com.example.client_side.client.UserRestService;
import com.example.client_side.dto.PersonalInfoDto;

import lombok.ToString;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GoHomeClickListener implements View.OnClickListener {
    private final Context context;
    private final Retrofit retrofit;
    private final UserRestService client;
    public GoHomeClickListener(Context context) {
        this.context = context;
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .client(HttpClientUtils.getClientWithHeader())
                .build();
        client = retrofit.create(UserRestService.class);
    }

    @Override
    public void onClick(View v) {
        client.getPersonalInfo().enqueue(new Callback<PersonalInfoDto>() {
            @Override
            public void onResponse(Call<PersonalInfoDto> call, Response<PersonalInfoDto> response) {
                Intent intent = new Intent(context, MyPageActivity.class);
                intent.putExtra(IntentExtraConstants.PERSONAL_INFO, response.body());
                context.startActivity(intent);
            }

            @Override
            public void onFailure(Call<PersonalInfoDto> call, Throwable t) {

            }
        });
    }
}
