package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapp.model.Question;

import java.util.List;

public class QuizActivity extends AppCompatActivity {
    private List<Question> questionList;
    private int currentQuestionIndex = 0, score = 0;
    private TextView questionText;
    private RadioButton option1, option2, option3, option4;
    private RadioGroup optionsGroup;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionText = findViewById(R.id.question_text);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        optionsGroup = findViewById(R.id.options_group);
        nextButton = findViewById(R.id.nextbutton);

        DBHelper dbHelper = new DBHelper(this);
        questionList = dbHelper.getAllQuestions();
        displayQuestion();

        nextButton.setOnClickListener(view -> {
            checkAnswer();
            currentQuestionIndex++;
            if (currentQuestionIndex < questionList.size()) {
                displayQuestion();
            } else {
                Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                intent.putExtra("score", score);
                intent.putExtra("total", questionList.size());
                startActivity(intent);
                finish();
            }
        });
    }

    private void displayQuestion() {
        Question currentQuestion = questionList.get(currentQuestionIndex);
        questionText.setText(currentQuestion.getQuestion());
        option1.setText(currentQuestion.getOption1());
        option2.setText(currentQuestion.getOption2());
        option3.setText(currentQuestion.getOption3());
        option4.setText(currentQuestion.getOption4());
        optionsGroup.clearCheck();
    }

    private void checkAnswer() {
        int selectedAnswer = optionsGroup.indexOfChild(findViewById(optionsGroup.getCheckedRadioButtonId())) + 1;
        if (selectedAnswer == questionList.get(currentQuestionIndex).getAnswer()) {
            score++;
        }
    }
}
