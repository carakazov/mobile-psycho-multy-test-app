package com.example.client_side.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.client_side.R;
import com.example.client_side.dto.CustomTestDto;
import com.example.client_side.utils.IntentExtraConstants;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class AddCustomTestActivity extends AppCompatActivity {
    private EditText etTestTitle;
    private EditText etTestDescription;
    private EditText etExpectedTime;
    private RadioButton rbArih;
    private RadioButton rbGeom;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_cutom_test_layout);
        etTestTitle = findViewById(R.id.etTestName);
        etTestDescription = findViewById(R.id.etTestDescription);
        etExpectedTime = findViewById(R.id.etTestExpectedTime);
        rbArih = findViewById(R.id.rbArih);
        rbGeom = findViewById(R.id.rbGeom);
        rbArih.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(rbArih.isChecked()) {
                    rbGeom.setChecked(false);
                }
            }
        });
        rbGeom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(rbGeom.isChecked()) {
                    rbArih.setChecked(false);
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void goToQuestionsAndAnswers(View view) {
        String title = etTestTitle.getText().toString();
        String description = etTestDescription.getText().toString();
        String expectedTime = etExpectedTime.getText().toString();
        boolean arih = rbArih.isChecked();
        boolean geom = rbGeom.isChecked();
        if(StringUtils.isEmpty(title)) {
            Toast.makeText(this, R.string.title_is_null_message, Toast.LENGTH_SHORT).show();
            return;
        }
        if(StringUtils.isEmpty(description)) {
            Toast.makeText(this, R.string.description_is_null_message, Toast.LENGTH_SHORT).show();
            return;
        }
        if(StringUtils.isEmpty(expectedTime)) {
            Toast.makeText(this, R.string.expected_time_is_null_message, Toast.LENGTH_SHORT).show();
            return;
        }
        if((!arih && !geom) || (arih && geom)) {
            Toast.makeText(this, R.string.processing_type_is_not_specified_message, Toast.LENGTH_SHORT).show();
            return;
        }

        CustomTestDto customTest = new CustomTestDto();
        customTest.setTitle(title);
        customTest.setDescription(description);
        customTest.setExpectedTime(expectedTime);
        if(arih) {
            customTest.setProceedingType("AVERAGE");
        } else {
            customTest.setProceedingType("GEOMETRIC");
        }
        Intent intent = new Intent(this, AddCustomQuestionLayout.class);
        intent.putExtra(IntentExtraConstants.CUSTOM_TEST, customTest);
        intent.putExtra(IntentExtraConstants.QUESTION_COUNTER, 0);
        startActivity(intent);
        finish();
    }
}
