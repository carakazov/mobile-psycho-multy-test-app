package com.example.client_side.utils;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.client_side.R;

public class NavButtonsHelper {
    public static void renderNavButtons(Context context, LinearLayout navButtons, Button goHomeButton, Button goToListButton) {
        if(HttpClientUtils.getToken() != null) {
            goHomeButton.setOnClickListener(new GoHomeClickListener(context));
            goToListButton.setOnClickListener(new GoToTestListOnClickListener(context));
            goToListButton.setVisibility(View.VISIBLE);
        } else {
            goToListButton.setOnClickListener(new GoToTestListOnClickListener(context));
            goHomeButton.setVisibility(View.INVISIBLE);
            goToListButton.setVisibility(View.VISIBLE);
        }
    }
}
