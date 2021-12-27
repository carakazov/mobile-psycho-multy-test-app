package com.example.client_side.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.client_side.R;
import com.example.client_side.dto.TestDto;
import com.example.client_side.utils.IntentExtraConstants;

public class TestDescpriptionAnctivity extends AppCompatActivity {
    private TextView desc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_description_layout);
        desc = findViewById(R.id.test_description);
        desc.setText(((TestDto)getIntent().getExtras().get(IntentExtraConstants.TEST_NAME)).getDescription());
    }

    public void goToTest(View view) {
        Intent intent = new Intent(this, QuestionLayoutActivity.class);
        intent.putExtra(IntentExtraConstants.TEST_NAME, (TestDto)getIntent().getExtras().get(IntentExtraConstants.TEST_NAME));
        intent.putExtra(IntentExtraConstants.QUESTION_POSITION, 0);
        startActivity(intent);
        finish();
    }
}
