package com.example.client_side.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.client_side.R;
import com.example.client_side.dto.CustomAnswerDto;
import com.example.client_side.dto.CustomQuestionDto;
import com.example.client_side.dto.CustomTestDto;
import com.example.client_side.utils.IntentExtraConstants;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddCustomQuestionLayout extends AppCompatActivity {
    private EditText etQuestion;
    private EditText etFirstAnswer;
    private EditText etFirstAnswerValue;
    private EditText etSecondAnswer;
    private EditText etSecondAnswerValue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_custom_question_layout);
        etQuestion = findViewById(R.id.etQuestion);
        etFirstAnswer = findViewById(R.id.etFirstAnswer);
        etFirstAnswerValue = findViewById(R.id.etFirstAnswerValue);
        etSecondAnswer = findViewById(R.id.etSecondAnswer);
        etSecondAnswerValue = findViewById(R.id.etSecondAnswerValue);
    }

    public void goToTheResults(View view) {
        String question = etQuestion.getText().toString();
        String firstAnswer = etFirstAnswer.getText().toString();
        String firstAnswerValue = etFirstAnswerValue.getText().toString();
        String secondAnswer = etSecondAnswer.getText().toString();
        String secondAnswerValue = etSecondAnswerValue.getText().toString();
        double firstAnswerValueDouble;
        double secondAnswerValueDouble;
        if(StringUtils.isEmpty(question)) {
            Toast.makeText(this, R.string.question_is_null_message, Toast.LENGTH_SHORT).show();
            return;
        }
        if(StringUtils.isEmpty(firstAnswer) || StringUtils.isEmpty(secondAnswer)) {
            Toast.makeText(this, R.string.answer_is_null_message, Toast.LENGTH_SHORT).show();
            return;
        }
        if(StringUtils.isEmpty(firstAnswerValue) || StringUtils.isEmpty(secondAnswerValue)) {
            Toast.makeText(this, R.string.answer_value_is_null, Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            firstAnswerValueDouble = Double.parseDouble(firstAnswerValue);
            secondAnswerValueDouble = Double.parseDouble(secondAnswerValue);
        } catch (Exception e) {
            Toast.makeText(this, R.string.answer_value_NaN, Toast.LENGTH_SHORT).show();
            return;
        }
        CustomQuestionDto questionObj = new CustomQuestionDto();

        CustomAnswerDto firstAnswerObj = new CustomAnswerDto();
        firstAnswerObj.setText(firstAnswer);
        firstAnswerObj.setValue(firstAnswerValueDouble);

        CustomAnswerDto secondAnswerObj = new CustomAnswerDto();
        secondAnswerObj.setText(secondAnswer);
        secondAnswerObj.setValue(secondAnswerValueDouble);

        List<CustomAnswerDto> answers = new ArrayList<>();
        answers.add(firstAnswerObj);
        answers.add(secondAnswerObj);

        questionObj.setText(question);
        questionObj.setAnswers(answers);

        CustomTestDto customTest = (CustomTestDto) getIntent().getExtras().get(IntentExtraConstants.CUSTOM_TEST);
        customTest.getQuestions().add(questionObj);
        int questionCounter = Integer.parseInt(getIntent().getExtras().get(IntentExtraConstants.QUESTION_COUNTER).toString());
        if(questionCounter < 2) {
            Intent intent = new Intent(this, AddCustomQuestionLayout.class);
            intent.putExtra(IntentExtraConstants.CUSTOM_TEST, customTest);
            intent.putExtra(IntentExtraConstants.QUESTION_COUNTER, ++questionCounter);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, AddCustomResultActivity.class);
            int resultCounter = 0;
            intent.putExtra(IntentExtraConstants.RESULT_COUNTER, resultCounter);
            intent.putExtra(IntentExtraConstants.CUSTOM_TEST, customTest);
            startActivity(intent);
        }
        finish();
    }
}
