package com.example.trivia.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.trivia.controller.AppController;
import com.example.trivia.model.Question;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Repository {
    String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";
    ArrayList<Question> questions = new ArrayList<>();

    public List<Question> getQuestions(final AnswerListAsyncResponse callBack) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            for (int i = 0; i < response.length(); i++) {
                try {
                    String answer = response.getJSONArray(i).getString(0);
                    boolean answerTrue = response.getJSONArray(i).getBoolean(1);
                    questions.add(new Question(answer, answerTrue));
                    System.out.println();
                    Log.d("jsonObj", "onCreate: " + questions.get(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (null != callBack) callBack.processFinished(questions);
        }, error -> {
            Log.d("jsonObj", "onCreate: Failed");
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
        return questions;
    }
}
