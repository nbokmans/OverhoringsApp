package ica.han.oose.project.overhoorapp.overhoren;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ica.han.oose.project.overhoorapp.R;

/**
 * Created by coen on 15-4-2015.
 */
public class MultipleChoiceQuestion extends QuestionFragment {

    private final Button[] buttons = new Button[5];
    private List<String> correctAnswer;
    /**
     * handle answer click
     */
    View.OnClickListener answerQuestion = new View.OnClickListener() {
        public void onClick(View v) {
            Button b = (Button) v;
            if (b.getId() == R.id.NextQuestion) {
                ((QuizActivity) getActivity()).nextQuestion();
            }
            Button nextQuestions = (Button) fragment.findViewById(R.id.NextQuestion);
            nextQuestions.setVisibility(View.VISIBLE);

            //Correct answer
            if (checkAnswer(b.getText().toString())) {
                v.setBackground(getResources().getDrawable(R.drawable.textboxcorrectanswer));
                for (Button button : buttons) {
                    if (button.getId() != v.getId()) {
                        Button ba = (Button) fragment.findViewById(button.getId());
                        ba.setVisibility(View.INVISIBLE);
                        nextQuestions.setText("Goed geantwoord");
                    }
                }
            }
            // incorrect answer
            else {
                v.setBackground(getResources().getDrawable(R.drawable.textboxincorrectanswer));
                nextQuestions.setText("Slecht hoor!");
                for (Button d : buttons) {
                    for (String ca : correctAnswer) {
                        if (d.getText().equals(ca)) {
                            Button ba = (Button) fragment.findViewById(d.getId());
                            ba.setBackground(getResources().getDrawable(R.drawable.textboxcorrectanswer));
                        } else if (d.getId() != v.getId()) {
                            Button ba = (Button) fragment.findViewById(d.getId());
                            ba.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            }
            sendAnswer(0, b.getText().toString());
        }
    };
    private List<String> possibleAnswers = new ArrayList<>();
    private String question;
    private View fragment;
    private int questionNumber;

    /**
     * Call this function befor you start this activity!
     *
     * @param question
     * @param possibleAnswers
     * @param correctAnswer
     */
    public void initMultipleChoiceQuestion(String question, List<String> possibleAnswers, List<String> correctAnswer, int questionNumber) {
        this.correctAnswer = correctAnswer;
        this.possibleAnswers = possibleAnswers;
        this.question = question;
        this.questionNumber = questionNumber;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragment = inflater.inflate(R.layout.fragment_multiple_choice_question, container, false);
        ((TextView) fragment.findViewById(R.id.multiplechoiceQuestionsNumber)).setText("Vraag: " + String.valueOf(this.questionNumber));
        fragment.findViewById(R.id.NextQuestion).setOnClickListener(answerQuestion);
        buttons[0] = (Button) fragment.findViewById(R.id.MultiOptie1);
        buttons[1] = (Button) fragment.findViewById(R.id.MultiOptie2);
        buttons[2] = (Button) fragment.findViewById(R.id.MultiOptie3);
        buttons[3] = (Button) fragment.findViewById(R.id.MultiOptie4);
        buttons[4] = (Button) fragment.findViewById(R.id.MultiOptie5);

        setPersistenceAdapter(fragment.getContext());

        List<String> answers = possibleAnswers;
        for (String ca : correctAnswer) {
            answers.add(ca);
        }
        Collections.shuffle(answers);
        int i = 0;
        for (String a : answers) {
            Button b = (Button) fragment.findViewById(R.id.MultiOptie1);
            buttons[i].setVisibility(View.VISIBLE);
            buttons[i].setText(a);
            buttons[i].setOnClickListener(answerQuestion);
            i++;
        }
        return fragment;
    }

    /**
     * check if the answer equals one of the correctAnswers
     *
     * @param answer given answer
     * @return
     */
    private boolean checkAnswer(String answer) {
        boolean result = false;
        for (String ca : correctAnswer) {
            if (answer.equals(ca)) {
                result = true;
            }
        }
        return result;
    }

    /**
     * fill textviews with data set in initQuestion
     */
    @Override
    public void onStart() {
        super.onStart();
        ((TextView) getView().findViewById(R.id.MultipleChoiceQuestionTextView)).setText(this.question);
    }
}
