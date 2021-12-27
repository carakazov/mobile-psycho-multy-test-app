package com.example.client_side.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.ClientCertRequest;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.client_side.R;
import com.example.client_side.client.TakenTestRestClient;
import com.example.client_side.client.UserRestService;
import com.example.client_side.dto.ChangePersonalInfoDto;
import com.example.client_side.dto.ChangePersonalInfoParameter;
import com.example.client_side.dto.GetTakenTestsResponse;
import com.example.client_side.dto.PersonalInfoDto;
import com.example.client_side.utils.HttpClientUtils;
import com.example.client_side.utils.IntentExtraConstants;
import com.example.client_side.utils.LanguageHelper;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyPageActivity extends AppCompatActivity {
    private static final String MALE = "MALE";
    private static final String FEMALE = "FEMALE";

    private EditText etName;
    private EditText etSurname;
    private EditText etMiddleName;
    private EditText etEmail;
    private RadioButton rbMale;
    private RadioButton rbFemale;
    private Button goToCreateTest;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_page_layout);
        etName = findViewById(R.id.etName);
        etSurname = findViewById(R.id.etSurname);
        etMiddleName = findViewById(R.id.etMiddleName);
        etEmail = findViewById(R.id.etEmail);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);

        PersonalInfoDto info = (PersonalInfoDto) getIntent().getExtras().get(IntentExtraConstants.PERSONAL_INFO);
        etName.setText(info.getName());
        etSurname.setText(info.getSurname());
        etEmail.setText(info.getEmail());
        etMiddleName.setText(info.getMiddleName());
        if(MALE.equals(info.getGender())) {
            rbMale.setChecked(true);
        } else {
            rbFemale.setChecked(true);
        }
        rbMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    rbFemale.setChecked(false);
                }
            }
        });

        rbFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    rbMale.setChecked(false);
                }
            }
        });
        goToCreateTest = findViewById(R.id.goToCreateTest);
        String role = HttpClientUtils.getRole();
        if("ROLE_PSYCHOLOGIST".equals(HttpClientUtils.getRole())) {
            goToCreateTest.setVisibility(View.VISIBLE);
        } else {
            goToCreateTest.setVisibility(View.INVISIBLE);
        }
    }

    public void goToTakenTestsList(View view) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .client(HttpClientUtils.getClientWithHeader())
                .build();
        TakenTestRestClient client = retrofit.create(TakenTestRestClient.class);
        client.getTakenTestsList(LanguageHelper.getLANGUAGE_CODE()).enqueue(
                new Callback<GetTakenTestsResponse>() {
                    @Override
                    public void onResponse(Call<GetTakenTestsResponse> call, Response<GetTakenTestsResponse> response) {
                        if(response.code() == 200) {
                            Intent intent = new Intent(MyPageActivity.this, TakenTestsActivity.class);
                            intent.putExtra(IntentExtraConstants.TAKEN_TESTS_LIST, response.body());
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetTakenTestsResponse> call, Throwable t) {
                        Toast.makeText(MyPageActivity.this, R.string.rest_error, Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    public void toCreateTestForm(View view) {
        Intent intent = new Intent(this, AddCustomTestActivity.class);
        startActivity(intent);
    }

    public void changePersonalInfo(View view) {
        ChangePersonalInfoDto changeInfoDto = new ChangePersonalInfoDto();
        Map<ChangePersonalInfoParameter, String> infoToChange = new HashMap<>();
        String name = etName.getText().toString();
        String surname = etSurname.getText().toString();
        String middleName = etMiddleName.getText().toString();
        String email = etEmail.getText().toString();
        boolean isMale = rbMale.isChecked();
        boolean isFemale = rbFemale.isChecked();
        if(StringUtils.isEmpty(name)) {
            Toast.makeText(this, R.string.name_is_empty_message, Toast.LENGTH_SHORT).show();
            return;
        }
        if(StringUtils.isEmpty(surname)) {
            Toast.makeText(this, R.string.surname_is_empty_message, Toast.LENGTH_SHORT).show();
            return;
        }
        if(StringUtils.isEmpty(email)) {
            Toast.makeText(this, R.string.email_is_empty_message, Toast.LENGTH_SHORT).show();
            return;
        }
        if(!isMale && !isFemale) {
            Toast.makeText(this, R.string.gender_is_empty_message, Toast.LENGTH_SHORT).show();
            return;
        }

        infoToChange.put(ChangePersonalInfoParameter.NAME, name);
        infoToChange.put(ChangePersonalInfoParameter.SURNAME, surname);
        infoToChange.put(ChangePersonalInfoParameter.MIDDLE_NAME, middleName);
        infoToChange.put(ChangePersonalInfoParameter.EMAIL, email);
        if(rbMale.isChecked()) {
            infoToChange.put(ChangePersonalInfoParameter.GENDER, MALE);
        } else {
            infoToChange.put(ChangePersonalInfoParameter.GENDER, FEMALE);
        }
        changeInfoDto.setInfoToChange(infoToChange);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .client(HttpClientUtils.getClientWithHeader())
                .build();
        UserRestService client = retrofit.create(UserRestService.class);
        client.changeInfo(changeInfoDto).enqueue(
                new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.code() == 200) {
                            Toast.makeText(MyPageActivity.this, R.string.change_personal_info_success, Toast.LENGTH_SHORT).show();
                            client.getPersonalInfo().enqueue(new Callback<PersonalInfoDto>() {
                                @Override
                                public void onResponse(Call<PersonalInfoDto> call, Response<PersonalInfoDto> response) {
                                    PersonalInfoDto personalInfo = response.body();
                                    Intent intent = new Intent(MyPageActivity.this, MyPageActivity.class);
                                    intent.putExtra(IntentExtraConstants.PERSONAL_INFO, personalInfo);
                                    MyPageActivity.this.startActivity(intent);
                                    MyPageActivity.this.finish();
                                }

                                @Override
                                public void onFailure(Call<PersonalInfoDto> call, Throwable t) {
                                    Toast.makeText(MyPageActivity.this, R.string.rest_error, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(MyPageActivity.this, R.string.rest_error, Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}
