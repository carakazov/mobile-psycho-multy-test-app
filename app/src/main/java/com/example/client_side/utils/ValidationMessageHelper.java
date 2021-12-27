package com.example.client_side.utils;

import android.content.Context;
import android.widget.Toast;

import androidx.navigation.ActionOnlyNavDirections;

import com.example.client_side.dto.ErrorResponseDto;

import lombok.Getter;
import lombok.Setter;

public class ValidationMessageHelper {
    @Setter
    private static Context context;

    public static void showValidationMessage(ErrorResponseDto errorResponseDto) {
        StringBuilder builder = new StringBuilder();
        errorResponseDto.getExceptions().forEach(item -> {
            if(item.getMessage() != null) {
                builder.append(item.getMessage()).append("\n");
            }
        });
        Toast.makeText(context, builder.toString(), Toast.LENGTH_LONG).show();
    }
}
