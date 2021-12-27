package com.example.client_side.utils;

import android.graphics.Paint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.client_side.dto.AnswerDto;
import com.example.client_side.dto.AnsweredQuestionDto;
import com.example.client_side.dto.TakenTestDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AnsweringTestHelper {
    private static TakenTestDto takenTest;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void startTest(String languageCode, UUID testId) {
        takenTest = new TakenTestDto();
        takenTest.setStartDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
        takenTest.setLanguageCode(languageCode);
        takenTest.setTestExternalId(testId);
        takenTest.setStartTime(LocalDateTime.now());
        takenTest.setAnswers(new ArrayList<>());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void addAnswer(AnsweredQuestionDto answer) {
        if(takenTest.getAnswers().stream().anyMatch(
                item -> item.getTestQuestionId().equals(answer.getTestQuestionId()))) {
            takenTest.getAnswers().stream().filter(
                    item -> item.getTestQuestionId().equals(answer.getTestQuestionId())
            ).findFirst().get().setTestAnswerId(answer.getTestAnswerId());
        } else {
            takenTest.getAnswers().add(answer);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static TakenTestDto getTakenTest() {
        takenTest.setFinishDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
        takenTest.setFinishTime(LocalDateTime.now());
        return takenTest;
    }
}
