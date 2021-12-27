package com.example.client_side.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.client_side.R;
import com.example.client_side.client.ResultRestClient;
import com.example.client_side.dto.AnswerDto;
import com.example.client_side.dto.AnsweredQuestionDto;
import com.example.client_side.dto.QuestionDto;
import com.example.client_side.dto.ResultDto;
import com.example.client_side.dto.TakenTestDto;
import com.example.client_side.dto.TestDto;
import com.example.client_side.utils.AnsweringTestHelper;
import com.example.client_side.utils.HttpClientUtils;
import com.example.client_side.utils.IntentExtraConstants;
import com.example.client_side.utils.LanguageHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuestionLayoutActivity extends AppCompatActivity {
    private TextView question;
    private ListView answers;
    private TextView etQuestionCounter;
    private TestDto test;
    private int currentPosition;
    private QuestionDto currentQuestion;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_layout);
        question = findViewById(R.id.twQuestion);
        answers = findViewById(R.id.lvAnswers);
        String positionStr = getIntent().getExtras().get(IntentExtraConstants.QUESTION_POSITION).toString();
        currentPosition = Integer.parseInt(positionStr);
        test = (TestDto) getIntent().getExtras().get(IntentExtraConstants.TEST_NAME);
        currentQuestion = test.getQuestions().get(currentPosition);
        question.setText(currentQuestion.getText());
        if(currentPosition == 0) {
            AnsweringTestHelper.startTest(LanguageHelper.getLANGUAGE_CODE(), test.getExternalId());
        }
        answers.setAdapter(new AnswerAdapter(this, currentQuestion.getAnswers()));
        etQuestionCounter = findViewById(R.id.questionCounter);
        int currentPositionCounter = currentPosition + 1;
        String text = etQuestionCounter.getText().toString().replace("[current]", String.valueOf(currentPositionCounter)).replace("[all]", String.valueOf(test.getQuestions().size()));
        etQuestionCounter.setText(text);
    }

    public class AnswerAdapter extends ArrayAdapter<AnswerDto> {
        public AnswerAdapter(Context context, List<AnswerDto> answers) {
            super(context, 0, answers);
        }

        @NonNull
        @Override
        @RequiresApi(api = Build.VERSION_CODES.O)
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if(convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.question_layout_answer_list_item, parent, false);
            }
            AnswerDto answer = getItem(position);

            Button button = convertView.findViewById(R.id.answerButton);
            button.setText(answer.getText());
            button.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View v) {
                    AnsweringTestHelper.addAnswer(new AnsweredQuestionDto()
                            .setTestAnswerId(answer.getExternalId())
                            .setTestQuestionId(currentQuestion.getExternalId()));
                    if(currentPosition == test.getQuestions().size() - 1) {
                        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) ->
                                ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toLocalDateTime()).create();
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://10.0.2.2:8080/")
                                .addConverterFactory(GsonConverterFactory.create(gson))
                                .client(HttpClientUtils.getClientWithHeader())
                                .build();
                        ResultRestClient client = retrofit.create(ResultRestClient.class);
                        TakenTestDto takenTest = AnsweringTestHelper.getTakenTest();;
                        String test = gson.toJson(takenTest);
                        client.calculateResult(takenTest).enqueue(
                                new Callback<ResultDto>() {
                                    @Override
                                    public void onResponse(Call<ResultDto> call, Response<ResultDto> response) {
                                        Intent intent = new Intent(QuestionLayoutActivity.this, TestResultActivity.class);
                                        Duration duration = Duration.between(takenTest.getStartTime(), takenTest.getFinishTime());
                                        intent.putExtra(IntentExtraConstants.RESULT, response.body());
                                        intent.putExtra(IntentExtraConstants.DURATION, duration);
                                        startActivity(intent);
                                        finish();
                                    }

                                    @Override
                                    public void onFailure(Call<ResultDto> call, Throwable t) {
                                        Toast.makeText(QuestionLayoutActivity.this, R.string.rest_error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                        );
                    } else {
                        Intent intent = new Intent(QuestionLayoutActivity.this, QuestionLayoutActivity.class);
                        intent.putExtra(IntentExtraConstants.TEST_NAME, test);
                        intent.putExtra(IntentExtraConstants.QUESTION_POSITION, ++currentPosition);
                        startActivity(intent);
                        finish();
                    }
                }
            });
            return convertView;
        }
    }
}
