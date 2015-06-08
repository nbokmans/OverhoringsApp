package ica.han.oose.project.overhoorapp.overhoren;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ica.han.oose.project.overhoorapp.NavigationDrawerActivity;
import ica.han.oose.project.overhoorapp.R;
import ica.han.oose.project.overhoorapp.json.models.questions.get.QuestionWrapper;
import ica.han.oose.project.overhoorapp.knowledge.SummaryActivity;
import ica.han.oose.project.overhoorapp.persistence.PersistenceAdapter;
import ica.han.oose.project.overhoorapp.statistics.StatisticsActivity;

public class QuizActivity extends NavigationDrawerActivity {

    final String QUESTION_NUMBER = "QUESTION_NUMBER";
    final String topic_id = "TOPIC_ID";
    ArrayList<Fragment> fragmentList;
    private QuestionFactory questionFactory;
    private int questionNumber = 0;
    private String topicId;

    /**
     * start new quiz
     * Login--> questionsSelections--> add questions to list --> start quiz
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentList = new ArrayList<Fragment>();

        getQuizdata();
        List<QuestionWrapper> questions = new ArrayList<>();
        questions = getQuestions();
        addQuestionsToList(questions);
        startQuiz();

    }

    /**
     * Get topic ID when a new quiz start, or data from cashe for continue.
     *
     * @return quizdata available
     */
    private void getQuizdata() {

        if (getIntent().hasExtra("TopicId")) {
            Bundle extras = getIntent().getExtras();
            topicId = extras.getString("TopicId");
        } else //get data from cashe
        {
            try {
                questionNumber = Integer.parseInt(getCacheData(QUESTION_NUMBER));
                topicId = getCacheData(topic_id);
            } catch (NullPointerException npe) {
                final Intent intent = new Intent(this, SummaryActivity.class);
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Quiz")
                        .setMessage("geen actuele quiz aanwezig")
                        .setPositiveButton("terug", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(intent);
                            }
                        })
                        .show();
            }
        }
    }


    /**
     * view next question from fragmentList
     */
    public void nextQuestion() {
        if (fragmentList.size() > questionNumber) {
            replaceFragment(fragmentList.get(questionNumber));
            questionNumber++;
        } else {
            Intent intent = new Intent(this, StatisticsActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_quiz_stop, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_stop_quiz:
                setCacheData(QUESTION_NUMBER, Integer.toString(questionNumber - 1));
                setCacheData(topic_id, topicId);

                Intent intent = new Intent(this, StatisticsActivity.class);
                startActivity(intent);

        }
        return true;
    }

    /**
     * start the quiz
     */
    public void startQuiz() {
        nextQuestion();
    }

    /**
     * add this.questions to fragmentList
     */
    public void addQuestionsToList(List<QuestionWrapper> questions) {
        Collections.shuffle(questions);
        for (QuestionWrapper q : questions) {
            fragmentList.add(questionFactory.getQuestion(q, fragmentList.size() + 1));
        }
    }

    /**
     * get Questions from the server
     */
    private List<QuestionWrapper> getQuestions() {
        PersistenceAdapter persistenceAdapter = new PersistenceAdapter(this);
        List<QuestionWrapper> questions = persistenceAdapter.getQuestions(topicId, "sg");
        return questions;
    }

    private void setCacheData(String settingName, String data) {
        try {
            FileOutputStream fos = openFileOutput(settingName, Context.MODE_PRIVATE);
            String setting = settingName + "=" + data + ";";
            fos.write(setting.getBytes());
            fos.close();
        } catch (IOException e) {
            Log.e("QuizActivity", e.getMessage(), e);
        }
    }

    private String getCacheData(String settingName) {
        String[] result = null;
        try {
            int ch;
            StringBuilder stringBuilder = new StringBuilder("");
            FileInputStream fos = openFileInput(settingName);
            while ((ch = fos.read()) != -1) {
                stringBuilder.append((char) ch);
            }
            String[] parts = stringBuilder.toString().split(";");
            for (String part : parts) {
                if (part.contains(settingName)) {
                    result = part.split("=");
                }
            }
            if (result == null) {
                throw new NullPointerException();
            }
        } catch (IOException e) {
            Log.e("QuizActivity", e.getMessage(), e);
        }
        return result != null && result.length >= 1 ? result[1] : null;
    }
}
