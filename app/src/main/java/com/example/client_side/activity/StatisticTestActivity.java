package com.example.client_side.activity;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.client_side.R;
import com.example.client_side.dto.StatInTimeDto;
import com.example.client_side.utils.IntentExtraConstants;
import com.example.client_side.utils.NavButtonsHelper;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class StatisticTestActivity extends AppCompatActivity {
    private GraphView graph;
    private TextView legend;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_statistic);
        StatInTimeDto stat = (StatInTimeDto) getIntent().getExtras().get(IntentExtraConstants.TEST_IN_TIME);
        Set<String> result = new HashSet<>(stat.getResultsInTime().values());
        int currentPosition = 0;
        Map<String, Integer> resultLegend = new HashMap<>();
        for(String res : result) {
            resultLegend.put(res, currentPosition);
            currentPosition++;
        }
        List<DataPoint> points = new ArrayList<>();
        AtomicInteger number = new AtomicInteger();
        stat.getResultsInTime().values().forEach(item -> {
            int value = resultLegend.get(item);
            DataPoint point = new DataPoint(number.get(), value);
            number.getAndIncrement();
            points.add(point);
        });
        DataPoint[] series = points.toArray(new DataPoint[0]);
        graph = findViewById(R.id.stat_graph);
        graph.addSeries(new LineGraphSeries(series));
        legend = findViewById(R.id.legend);

        StringBuilder legendBuilder = new StringBuilder();
        resultLegend.entrySet().forEach(entry -> legendBuilder.append(entry.getKey()).append(" - ").append(entry.getValue()).append("\n"));
        legend.setText(legend.getText().toString().replace("[dec]", legendBuilder.toString()));
        NavButtonsHelper.renderNavButtons(
                this,
                findViewById(R.id.navButtons),
                findViewById(R.id.goHomeButton),
                findViewById(R.id.goToTestListButton)
        );
    }


}
