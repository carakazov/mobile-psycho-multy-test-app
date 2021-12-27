package com.example.client_side.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.client_side.R;
import com.example.client_side.dto.AuthRequestDto;
import com.example.client_side.dto.AuthResponseDto;
import com.example.client_side.client.AuthRestClient;
import com.example.client_side.utils.HttpClientUtils;

import org.apache.commons.lang3.StringUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthFormActivity extends AppCompatActivity {
    private EditText etLogin;
    private EditText etPassword;
    private Retrofit retrofit;
    private AuthRestClient client;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_form);
        etLogin = findViewById(R.id.etLogin);
        etPassword = findViewById(R.id.etPassword);
    }

    public void login(View view) {
        String login = etLogin.getText().toString();
        String password = etPassword.getText().toString();

        if(validateStrings(login, password)) {
            getToken(login, password);
        }
    }

    public void goToRegistrationForm(View view) {
        Intent intent = new Intent(this, RegistrationFormActivity.class);
        startActivity(intent);
    }

    private boolean validateStrings(String login, String password) {
        if(StringUtils.isEmpty(login)) {
            Toast.makeText(this, R.string.login_is_empty_message, Toast.LENGTH_SHORT).show();
            return false;
        } else if(StringUtils.isEmpty(password)) {
            Toast.makeText(this, R.string.password_is_empty_message, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void getToken(String login, String password) {
        AuthRequestDto request = new AuthRequestDto().setLogin(login).setPassword(password);
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        client = retrofit.create(AuthRestClient.class);
        client.login(request).enqueue(new Callback<AuthResponseDto>() {
            @Override
            public void onResponse(Call<AuthResponseDto> call, Response<AuthResponseDto> response) {
                if(response.code() == 401) {
                    Toast.makeText(AuthFormActivity.this, R.string.error_403, Toast.LENGTH_LONG).show();
                } else {
                    HttpClientUtils.setToken(response.body().getToken());
                    HttpClientUtils.setRole(response.body().getRole());
                    Intent intent = new Intent(AuthFormActivity.this, AllowedTestActivity.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onFailure(Call<AuthResponseDto> call, Throwable t) {
                Toast.makeText(AuthFormActivity.this, R.string.rest_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
