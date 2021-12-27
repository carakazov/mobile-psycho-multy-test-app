package com.example.client_side.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.client_side.R;
import com.example.client_side.dto.TestDto;
import com.example.client_side.utils.IntentExtraConstants;

public class ChooseGenderActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_gender_layout);
    }

    public void OnGenderClick(View view) {
        int questionPosition = 0;
        TestDto test = (TestDto) getIntent().getExtras().get(IntentExtraConstants.TEST_NAME);
        Intent intent = new Intent(this, QuestionLayoutActivity.class);
        intent.putExtra(IntentExtraConstants.TEST_NAME, test);
        intent.putExtra(IntentExtraConstants.QUESTION_POSITION, questionPosition);
        startActivity(intent);
        finish();
    }
}
