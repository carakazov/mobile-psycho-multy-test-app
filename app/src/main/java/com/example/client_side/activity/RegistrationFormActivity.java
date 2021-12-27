package com.example.client_side.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.client_side.R;
import com.example.client_side.client.AuthRestClient;
import com.example.client_side.client.UserRestService;
import com.example.client_side.dto.ErrorResponseDto;
import com.example.client_side.dto.RegistrationDto;
import com.example.client_side.utils.HttpClientUtils;
import com.example.client_side.utils.ValidationMessageHelper;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RegistrationFormActivity extends AppCompatActivity {


    private EditText etLogin;
    private EditText etPassword;
    private EditText etRepeatedPassword;
    private EditText etName;
    private EditText etSurname;
    private EditText etMiddleName;
    private EditText etEmail;
    private RadioButton rbMale;
    private RadioButton rbFemale;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_form);
        etLogin = findViewById(R.id.editText_login);
        etPassword = findViewById(R.id.editText_pass);
        etRepeatedPassword = findViewById(R.id.editText_pass_repeat);
        etName = findViewById(R.id.editText_name);
        etSurname = findViewById(R.id.editText_surname);
        etMiddleName = findViewById(R.id.editText_middle_name);
        etEmail = findViewById(R.id.editText_email);
        rbMale = findViewById(R.id.radio_male);
        rbFemale = findViewById(R.id.radio_female);
    }

    public void registration(View view) {
        String login = etLogin.getText().toString();
        String password = etPassword.getText().toString();
        String repeatedPassword = etRepeatedPassword.getText().toString();
        String name = etName.getText().toString();
        String surname = etSurname.getText().toString();
        String middleName = etMiddleName.getText().toString();
        String email = etEmail.getText().toString();
        boolean male = rbMale.isChecked();
        boolean female = rbFemale.isChecked();
        if(validateStrings(
                login, password, repeatedPassword, name, surname, email, male, female
        )) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(HttpClientUtils.getClientWithHeader())
                    .build();
            UserRestService client = retrofit.create(UserRestService.class);
            String gender;
            if(male) {
                gender = "MALE";
            } else {
                gender = "FEMALE";
            }
            client.register(
                    new RegistrationDto()
                    .setLogin(login)
                    .setPassword(password)
                    .setEmail(email)
                    .setGender(gender)
                    .setName(name)
                    .setSurname(surname)
                    .setMiddleName(middleName)
            ).enqueue(
                    new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.code() == 200) {
                                Toast.makeText(RegistrationFormActivity.this, R.string.registration_success, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegistrationFormActivity.this, AllowedTestActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Gson gson = new Gson();
                                ErrorResponseDto errorResponse = gson.fromJson(response.errorBody().charStream(),ErrorResponseDto.class);
                                ValidationMessageHelper.setContext(RegistrationFormActivity.this);
                                ValidationMessageHelper.showValidationMessage(errorResponse);
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(RegistrationFormActivity.this, R.string.rest_error, Toast.LENGTH_SHORT).show();
                        }
                    }
            );
        }
    }

    private boolean validateStrings(String login, String password, String repeatedPassword, String name, String surname, String email, boolean isMale, boolean isFemale) {
        if(StringUtils.isEmpty(login)) {
            Toast.makeText(this, R.string.login_is_empty_message, Toast.LENGTH_SHORT).show();
            return false;
        }
        if(StringUtils.isEmpty(password)) {
            Toast.makeText(this, R.string.password_is_empty_message, Toast.LENGTH_SHORT).show();
            return false;
        }
        if(StringUtils.isEmpty(repeatedPassword)) {
            Toast.makeText(this, R.string.password_is_empty_message, Toast.LENGTH_SHORT).show();
            return false;
        }
        if(StringUtils.isEmpty(name)) {
            Toast.makeText(this, R.string.name_is_empty_message, Toast.LENGTH_SHORT).show();
            return false;
        }
        if(StringUtils.isEmpty(surname)) {
            Toast.makeText(this, R.string.surname_is_empty_message, Toast.LENGTH_SHORT).show();
            return false;
        }
        if(StringUtils.isEmpty(email)) {
            Toast.makeText(this, R.string.email_is_empty_message, Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!repeatedPassword.equals(password)) {
            Toast.makeText(this, R.string.passwords_are_not_equals, Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!isMale && !isFemale) {
            Toast.makeText(this, R.string.gender_is_empty_message, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
