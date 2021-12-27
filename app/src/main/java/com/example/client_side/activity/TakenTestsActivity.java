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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.client_side.R;
import com.example.client_side.client.StatisticsRestClient;
import com.example.client_side.dto.GetTakenTestsResponse;
import com.example.client_side.dto.StatInTimeDto;
import com.example.client_side.dto.TakenTestsItemDto;
import com.example.client_side.utils.HttpClientUtils;
import com.example.client_side.utils.IntentExtraConstants;
import com.example.client_side.utils.LanguageHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TakenTestsActivity extends AppCompatActivity {
    private ListView listView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taken_tests_list);
        GetTakenTestsResponse response = (GetTakenTestsResponse) getIntent().getExtras().get(IntentExtraConstants.TAKEN_TESTS_LIST);
        listView = findViewById(R.id.lvTakenTests);
        listView.setAdapter(new TakenTestItemAdapter(this, response.getTests()));
        listView.setOnItemClickListener(new OnTakenTestItemClickListener());
    }

    public class TakenTestItemAdapter extends ArrayAdapter<TakenTestsItemDto> {
        public TakenTestItemAdapter(Context context, List<TakenTestsItemDto> array) {
            super(context, 0, array);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if(convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.taken_test_list_item, parent, false);
            }
            TakenTestsItemDto item = getItem(position);
            TextView tv = convertView.findViewById(R.id.takenTestItem);
            tv.setText(item.getTitle());
            return convertView;
        }
    }

    public class OnTakenTestItemClickListener implements AdapterView.OnItemClickListener {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TakenTestsItemDto item = (TakenTestsItemDto) parent.getItemAtPosition(position);
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) ->
                    ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toLocalDateTime()).create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(HttpClientUtils.getClientWithHeader())
                    .build();
            StatisticsRestClient client = retrofit.create(StatisticsRestClient.class);
            client.getMyStatistics(item.getExternalId(), LanguageHelper.getLANGUAGE_CODE()).enqueue(
                    new Callback<StatInTimeDto>() {
                        @Override
                        public void onResponse(Call<StatInTimeDto> call, Response<StatInTimeDto> response) {
                            if(response.code() == 200) {
                                Intent intent = new Intent(TakenTestsActivity.this, StatisticTestActivity.class);
                                intent.putExtra(IntentExtraConstants.TEST_IN_TIME, response.body());
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(Call<StatInTimeDto> call, Throwable t) {
                            Toast.makeText(TakenTestsActivity.this, R.string.rest_error, Toast.LENGTH_SHORT).show();
                        }
                    }
            );
        }
    }
}
