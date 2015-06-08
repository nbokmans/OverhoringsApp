package ica.han.oose.project.overhoorapp.overhoren;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ica.han.oose.project.overhoorapp.R;
import ica.han.oose.project.overhoorapp.exceptions.EmptyStringException;

/* @author Roy van Keijsteren & Coen Smid
        * @version 1.0
        * @since 4/28/2015
*/

public class OpenQuestion extends QuestionFragment {
    public static final String VOLGENDE_VRAAG = "Volgende vraag";
    public static final String ANTWOORD = "Antwoord: ";
    public static final String VRAAG = "Vraag: ";
    private List<String> answer;
    private int mistakesAllowed;
    private String question;
    private String inputAnswer;
    private boolean touch = false;
    private int questionNumber;

    /**
     * @param answer          correct answer
     * @param mistakesAllowed
     * @param question
     * @param questionNumber
     */
    public void initOpenQuestion(String question, List<String> answer, int mistakesAllowed, int questionNumber) {
        this.answer = answer;
        this.mistakesAllowed = mistakesAllowed;
        this.question = question;
        this.questionNumber = questionNumber;
    }

    /**
     * create new fragment with OnClickHandler
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return new fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View fragment = inflater.inflate(R.layout.fragment_open_question, container, false);
        EditText et = (EditText) fragment.findViewById(R.id.OpenQuestionAnswer);
        et.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                return false;
            }
        });

        setPersistenceAdapter(fragment.getContext());

        final Button mButton = (Button) fragment.findViewById(R.id.OpenQuestionSendAnswer);
        mButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText e = (EditText) fragment.findViewById(R.id.OpenQuestionAnswer);
                        inputAnswer = e.getText().toString();
                        Boolean question = false;
                        try {
                            question = answerQuestion();
                        } catch (EmptyStringException ese) {
                            Toast.makeText(getActivity(), "Input veld is leeg", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (touch) {
                            ((QuizActivity) getActivity()).nextQuestion();
                        }
                        if (question) {
                            ((TextView) fragment.findViewById(R.id.OpenQuestionShowCorrectAnswer)).setBackground(getResources().getDrawable(R.drawable.textboxcorrectanswer));
                            touch = true;
                        } else {
                            ((TextView) fragment.findViewById(R.id.OpenQuestionShowCorrectAnswer)).setBackground(getResources().getDrawable(R.drawable.textboxincorrectanswer));
                            touch = true;
                        }
                        ((Button) fragment.findViewById(R.id.OpenQuestionSendAnswer)).setText(VOLGENDE_VRAAG);
                        ((TextView) fragment.findViewById(R.id.OpenQuestionShowCorrectAnswer)).setVisibility(View.VISIBLE);
                        TextView correctAnswer = ((TextView) fragment.findViewById(R.id.OpenQuestionShowCorrectAnswer));
                        correctAnswer.setText(ANTWOORD + answer.get(0));
                        sendAnswer(0, inputAnswer);
                    }
                }
        );
        return fragment;
    }

    /**
     * fill textviews with data set in initQuestion
     */
    @Override
    public void onStart() {
        super.onStart();
        ((TextView) getView().findViewById(R.id.OpenquestionTextView)).setText(this.question);
        TextView openQuestionNumber = ((TextView) getView().findViewById(R.id.openQuestionNumber));
        openQuestionNumber.setText(VRAAG + String.valueOf(this.questionNumber));
    }

    /**
     * check if this.inputAnswer is equal with one of the correct answers
     */
    public boolean answerQuestion() {
        if (inputAnswer.equals("") || inputAnswer == null)
            throw new EmptyStringException();
        for (String s : answer) {
            if (SpellingsCheck.checkAnswer(inputAnswer, s, mistakesAllowed)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Only for Junit test purpose!!!
     *
     * @param inputAnswer
     */
    public void setInputAnswer(String inputAnswer) {
        this.inputAnswer = inputAnswer;
    }
}




