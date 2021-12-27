package com.example.client_side.utils;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.example.client_side.activity.AllowedTestActivity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GoToTestListOnClickListener implements View.OnClickListener {
    private final Context context;

    @Override
    public void onClick(View v) {
       Intent intent = new Intent(context, AllowedTestActivity.class);
       context.startActivity(intent);
    }
}
