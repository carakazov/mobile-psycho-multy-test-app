package com.example.client_side.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.client_side.R;
import com.example.client_side.dto.TestDto;
import com.example.client_side.dto.TestInfoDto;
import com.example.client_side.client.TestRestClient;
import com.example.client_side.utils.GoHomeClickListener;
import com.example.client_side.utils.GoToTestListOnClickListener;
import com.example.client_side.utils.HttpClientUtils;
import com.example.client_side.utils.IntentExtraConstants;
import com.example.client_side.utils.LanguageHelper;
import com.example.client_side.utils.LocaleHelper;
import com.example.client_side.utils.NavButtonsHelper;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AllowedTestActivity extends AppCompatActivity {
    private Retrofit retrofit;
    private TestRestClient client;
    private ListView listView;
    private Button authButton;
    private LinearLayout navButtons;
    private Button goHomeButton;
    private Button goToListButton;
    private Button langButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allowed_tests);
        listView = findViewById(R.id.listView);
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(HttpClientUtils.getClientWithHeader())
                .build();
        client = retrofit.create(TestRestClient.class);
        getAllowedTests();
        authButton = findViewById(R.id.authButton);
        if(!StringUtils.isEmpty(HttpClientUtils.getToken())) {
            authButton.setText(R.string.logout_text);
            authButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HttpClientUtils.setToken(null);
                    HttpClientUtils.setRole(null);
                    Intent intent = new Intent(AllowedTestActivity.this, AllowedTestActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
        listView.setOnItemClickListener(new TestItemOnClickListener(client));
        NavButtonsHelper.renderNavButtons(
                this,
                findViewById(R.id.navButtons),
                findViewById(R.id.goHomeButton),
                findViewById(R.id.goToTestListButton)
        );
        langButton = findViewById(R.id.langButton);
        langButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LocaleHelper.getLanguage(AllowedTestActivity.this).equals("en-us")) {
                    LocaleHelper.setLocale(AllowedTestActivity.this, "ru");
                    Locale locale = new Locale("ru");
                    Locale.setDefault(locale);
                    Configuration configuration = new Configuration();
                    configuration.locale = locale;
                    getBaseContext().getResources().updateConfiguration(configuration, null);
                    LanguageHelper.setLANGUAGE_CODE("RUS");
                } else {
                    LocaleHelper.setLocale(AllowedTestActivity.this, "en-us");
                    Locale locale = new Locale("en-us");
                    Locale.setDefault(locale);
                    Configuration configuration = new Configuration();
                    configuration.locale = locale;
                    getBaseContext().getResources().updateConfiguration(configuration, null);
                    LanguageHelper.setLANGUAGE_CODE("ENG");
                }
                Intent intent = new Intent(AllowedTestActivity.this, AllowedTestActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void changeLanguage(View view) {
        if (LocaleHelper.getLanguage(this).equals("en-us")) {
            LocaleHelper.setLocale(this, "ru");
            Locale locale = new Locale("ru");
            Locale.setDefault(locale);
            Configuration configuration = new Configuration();
            configuration.locale = locale;
            getBaseContext().getResources().updateConfiguration(configuration, null);
        } else {
            LocaleHelper.setLocale(this, "en-us");
            Locale locale = new Locale("en-us");
            Locale.setDefault(locale);
            Configuration configuration = new Configuration();
            configuration.locale = locale;
            getBaseContext().getResources().updateConfiguration(configuration, null);
        }
    }

    public void goToAuthForm(View view) {
        Intent intent = new Intent(this, AuthFormActivity.class);
        startActivity(intent);
    }

    private void getAllowedTests() {
        client.getTestList(LanguageHelper.getLANGUAGE_CODE()).enqueue(new Callback<List<TestInfoDto>>() {
            @Override
            public void onResponse(Call<List<TestInfoDto>> call, Response<List<TestInfoDto>> response) {
                TestItemAdapter testItemAdapter = new TestItemAdapter(AllowedTestActivity.this, response.body());
                listView.setAdapter(testItemAdapter);
            }

            @Override
            public void onFailure(Call<List<TestInfoDto>> call, Throwable t) {
                Toast.makeText(AllowedTestActivity.this, R.string.rest_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static class TestItemAdapter extends ArrayAdapter<TestInfoDto> {
        public TestItemAdapter(Context context, List<TestInfoDto> users) {
            super(context, 0, users);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.allowed_test_item, parent, false);
            }

            TestInfoDto testInfo = getItem(position);

            TextView titleTextView = convertView.findViewById(R.id.testName);
            TextView expectedTimeTextView = convertView.findViewById(R.id.expectedTime);

            titleTextView.setText(testInfo.getTitle());
            expectedTimeTextView.setText(testInfo.getExpectedTime());

            return convertView;
        }
    }

    public class TestItemOnClickListener implements AdapterView.OnItemClickListener {
        private final TestRestClient client;

        public TestItemOnClickListener(TestRestClient client) {
            this.client = client;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TestInfoDto info = (TestInfoDto) parent.getItemAtPosition(position);
            if(position == 0) {
                client.getDefaultTest(LanguageHelper.getLANGUAGE_CODE()).enqueue(
                        new Callback<TestDto>() {
                            @Override
                            public void onResponse(Call<TestDto> call, Response<TestDto> response) {
                                if(response.code() == 200) {
                                    TestDto test = response.body();
                                    if(test.getIsGenderDepending()) {
                                        Intent intent = new Intent(AllowedTestActivity.this, TestDescpriptionAnctivity.class);
                                        intent.putExtra(IntentExtraConstants.TEST_NAME, test);
                                        startActivity(intent);
                                    } else {
                                        int questionPosition = 0;
                                        Intent intent = new Intent(AllowedTestActivity.this, TestDescpriptionAnctivity.class);
                                        intent.putExtra(IntentExtraConstants.TEST_NAME, test);
                                        intent.putExtra(IntentExtraConstants.QUESTION_POSITION, questionPosition);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<TestDto> call, Throwable t) {
                                Toast.makeText(AllowedTestActivity.this, R.string.rest_error, Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            } else {
                client.getTest(info.getExternalId(), "ENG").enqueue(
                        new Callback<TestDto>() {
                            @Override
                            public void onResponse(Call<TestDto> call, Response<TestDto> response) {
                                if(response.code() == 200) {
                                    TestDto test = response.body();
                                    if(test.getIsGenderDepending()) {
                                        Intent intent = new Intent(AllowedTestActivity.this, TestDescpriptionAnctivity.class);
                                        intent.putExtra(IntentExtraConstants.TEST_NAME, test);
                                        startActivity(intent);
                                    } else {
                                        int questionPosition = 0;
                                        Intent intent = new Intent(AllowedTestActivity.this, TestDescpriptionAnctivity.class);
                                        intent.putExtra(IntentExtraConstants.TEST_NAME, test);
                                        intent.putExtra(IntentExtraConstants.QUESTION_POSITION, questionPosition);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<TestDto> call, Throwable t) {
                                Toast.makeText(AllowedTestActivity.this, R.string.rest_error, Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        }
    }
}
