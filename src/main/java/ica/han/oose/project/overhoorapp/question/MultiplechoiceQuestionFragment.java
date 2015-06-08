package ica.han.oose.project.overhoorapp.question;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import ica.han.oose.project.overhoorapp.R;
import ica.han.oose.project.overhoorapp.json.constants.QuestionType;
import ica.han.oose.project.overhoorapp.json.models.questions.CreateQuestion;
import ica.han.oose.project.overhoorapp.json.models.shared.Answers;
import ica.han.oose.project.overhoorapp.json.models.shared.Response;
import ica.han.oose.project.overhoorapp.json.models.shared.Statement;
import ica.han.oose.project.overhoorapp.json.models.shared.Text;

/**
 * show multipleChoice_Create_Questions
 *
 * @author Roy van Keijsteren
 * @version 1.0
 * @since 6/3/2015
 */
public class MultiplechoiceQuestionFragment extends QuestionFragment {

    private List<EditText> answers = new ArrayList<>();
    private EditText correctAnswer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        fragment = inflater.inflate(R.layout.fragment_create_multiplechoice_question, container, false);
        fragment.findViewById(R.id.Multi_Save).setOnClickListener(saveQuestion);
        correctAnswer = (EditText) fragment.findViewById(R.id.CorrectAnswer);
        answers.add((EditText) fragment.findViewById(R.id.Answer2));
        answers.add((EditText) fragment.findViewById(R.id.Answer3));
        answers.add((EditText) fragment.findViewById(R.id.Answer4));
        answers.add((EditText) fragment.findViewById(R.id.Answer5));
        if (questionWrapper != null) {
            ((EditText) fragment.findViewById(R.id.Multi_Question)).setText(questionWrapper.getStatement().getText().getValue());
            correctAnswer.setText(questionWrapper.getCorrectAnswers().getResponse()[0].getOption());
        }
        for (EditText et : answers) {
            if (questionWrapper != null && answers.indexOf(et) != 0 && answers.indexOf(et) <= questionWrapper.getIncorrectAnswers().length) {
                et.setText(questionWrapper.getIncorrectAnswers()[answers.indexOf(et)].getResponse()[0].getOption());
                et.setVisibility(View.VISIBLE);
            }
            et.addTextChangedListener(new CustomTextWatcher(et));
        }
        return fragment;
    }

    /**
     * handle save button action
     */
    View.OnClickListener saveQuestion = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String question = ((EditText) fragment.findViewById(R.id.Multi_Question)).getText().toString();
            correctAnswer = ((EditText) fragment.findViewById(R.id.CorrectAnswer));

//            List<String> answers = new ArrayList<>();
//            answers.add(((EditText) fragment.findViewById(R.id.Open_Question_Antwoord)).getText().toString());
            if (state == UPDATE) {
//                ((QuestionActivity) getActivity()).updateQuestionData(question, answers);
            } else if (state == CREATE) {
                CreateQuestion cq = new CreateQuestion();
                //correct answer
                Answers answer = new Answers();
                Response[] correctResponse = new Response[1];
                correctResponse[0] = new Response();
                correctResponse[0].setOption(correctAnswer.getText().toString());
                answer.setResponse(correctResponse);
                cq.setCorrectAnswers(answer);
                //incorrect answer
                final List<EditText> tempEditTextList = new ArrayList<EditText>();
                for (int i = 0; i < answers.size(); i++) {
                    final EditText et = answers.get(i);
                    if (et.getText().toString().trim().length() != 0) {
                        tempEditTextList.add(et);
                    }
                }

                final Answers[] incorrectAnswers = new Answers[tempEditTextList.size()];
                for (int i = 0; i < incorrectAnswers.length; i++) {
                    final Response[] responses = new Response[1];
                    responses[0] = new Response();
                    responses[0].setOption(tempEditTextList.get(i).getText().toString());
                }
                cq.setIncorrectAnswers(incorrectAnswers);

                Statement statement = new Statement();
                Text text = new Text();
                text.setValue(question);
                statement.setText(text);
                cq.setStatement(statement);

                cq.setQuestionType(QuestionType.SINGLEANSWER.toString().toLowerCase());
                ((QuestionActivity) getActivity()).saveQuestionData(cq);
            }
        }
    };

    @Override
    public void onStart() {
        super.onStart();

    }

    /**
     * Set next EditTextfield visible
     *
     * @param editText
     */
    private void setInputVisible(EditText editText) {
        for (EditText et : answers) {
            if (et.getId() == editText.getId()) {
                try {
                    String text = et.getText().toString();
                    if (text.equals("") || text.equals(null)) {
                        answers.get(answers.indexOf(et) + 1).setVisibility(View.INVISIBLE);
                    } else {
                        answers.get(answers.indexOf(et) + 1).setVisibility(View.VISIBLE);
                    }
                } catch (IndexOutOfBoundsException e) {
                    //continue
                }
            }
        }

    }

    /**
     * Action when user edit EditText field
     */
    private class CustomTextWatcher implements TextWatcher {
        private EditText mEditText;

        public CustomTextWatcher(EditText e) {
            mEditText = e;
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            setInputVisible(mEditText);
        }

        public void afterTextChanged(Editable s) {
        }
    }
}
