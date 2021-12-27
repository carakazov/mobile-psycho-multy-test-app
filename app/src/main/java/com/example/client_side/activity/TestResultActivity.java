package com.example.client_side.activity;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.client_side.R;
import com.example.client_side.dto.ResultDto;
import com.example.client_side.utils.IntentExtraConstants;
import com.example.client_side.utils.NavButtonsHelper;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.w3c.dom.Text;

import java.time.Duration;


public class TestResultActivity extends AppCompatActivity {
    private GraphView graph;
    private TextView tvResult;
    private TextView etTotalTime;
    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_result_layout);
        graph = findViewById(R.id.graph);
        tvResult = findViewById(R.id.tvResult);
        ResultDto result = (ResultDto) getIntent().getExtras().get(IntentExtraConstants.RESULT);
        LineGraphSeries<DataPoint> test = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 0),
                new DataPoint(result.getValue().doubleValue(), result.getValue().doubleValue())
        });
        graph.addSeries(test);
        tvResult.setText(result.getText());
        etTotalTime = findViewById(R.id.tvDuration);
        Duration duration = (Duration) getIntent().getExtras().get(IntentExtraConstants.DURATION);
        etTotalTime.setText(String.valueOf(duration.getSeconds()));
        NavButtonsHelper.renderNavButtons(
                this,
                findViewById(R.id.navButtons),
                findViewById(R.id.goHomeButton),
                findViewById(R.id.goToTestListButton)
        );
    }

}
