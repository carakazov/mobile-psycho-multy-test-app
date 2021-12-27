package com.example.client_side.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.client_side.R;
import com.example.client_side.client.CustomTestRestClient;
import com.example.client_side.dto.CustomResultDto;
import com.example.client_side.dto.CustomTestDto;
import com.example.client_side.dto.ErrorResponseDto;
import com.example.client_side.utils.HttpClientUtils;
import com.example.client_side.utils.IntentExtraConstants;
import com.example.client_side.utils.LanguageHelper;
import com.example.client_side.utils.ValidationMessageHelper;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddCustomResultActivity extends AppCompatActivity {
    private EditText etResult;
    private EditText etMinBorder;
    private EditText etMaxBorder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_custom_result_layout);
        etResult = findViewById(R.id.etResult);
        etMinBorder = findViewById(R.id.etMinBorder);
        etMaxBorder = findViewById(R.id.etMaxBorder);
    }

    public void saveCustomTest(View view) {
        String result = etResult.getText().toString();
        String minBorderText = etMinBorder.getText().toString();
        String maxBorderTest = etMaxBorder.getText().toString();

        if(StringUtils.isEmpty(result)) {
            Toast.makeText(this, R.string.result_is_null_message, Toast.LENGTH_SHORT).show();
            return;
        }
        if(StringUtils.isEmpty(minBorderText)) {
            Toast.makeText(this, R.string.input_result_min_border, Toast.LENGTH_SHORT).show();
            return;
        }
        if(StringUtils.isEmpty(maxBorderTest)) {
            Toast.makeText(this, R.string.input_result_max_border, Toast.LENGTH_SHORT).show();
            return;
        }
        double minBorder;
        double maxBorder;
        try {
            minBorder = Double.parseDouble(minBorderText);
            maxBorder = Double.parseDouble(maxBorderTest);
            CustomTestDto customTestDto = (CustomTestDto) getIntent().getExtras().get(IntentExtraConstants.CUSTOM_TEST);
            CustomResultDto customResult = new CustomResultDto();
            customResult.setMaxBorder(maxBorder);
            customResult.setMinBorder(minBorder);
            customResult.setText(result);
            customTestDto.getResults().add(customResult);
            int resultCounter = Integer.parseInt(getIntent().getExtras().get(IntentExtraConstants.RESULT_COUNTER).toString());
            if(resultCounter < 2) {
                Intent intent = new Intent(this, AddCustomResultActivity.class);
                intent.putExtra(IntentExtraConstants.CUSTOM_TEST, customTestDto);
                intent.putExtra(IntentExtraConstants.RESULT_COUNTER, ++resultCounter);
                startActivity(intent);
            } else {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://10.0.2.2:8080")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(HttpClientUtils.getClientWithHeader())
                        .build();
                CustomTestRestClient client = retrofit.create(CustomTestRestClient.class);
                customTestDto.setLanguageCode(LanguageHelper.getLANGUAGE_CODE());
                client.createCustomTest(customTestDto).enqueue(
                        new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if(response.code() == 200) {
                                    Toast.makeText(AddCustomResultActivity.this, R.string.test_create, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(AddCustomResultActivity.this, AllowedTestActivity.class);
                                    startActivity(intent);
                                } else {
                                    Gson gson = new Gson();
                                    ErrorResponseDto errorResponse = gson.fromJson(response.errorBody().charStream(),ErrorResponseDto.class);
                                    ValidationMessageHelper.setContext(AddCustomResultActivity.this);
                                    ValidationMessageHelper.showValidationMessage(errorResponse);
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(AddCustomResultActivity.this, R.string.rest_error, Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        } catch (Exception e) {
            Toast.makeText(this, R.string.border_value_is_NaN, Toast.LENGTH_SHORT).show();
        }
    }
}
