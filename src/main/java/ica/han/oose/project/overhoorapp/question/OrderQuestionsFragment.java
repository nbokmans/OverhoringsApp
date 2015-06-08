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
 * TODO: korte beschrijving van klsse
 *
 * @author Roy van Keijsteren
 * @version 1.0
 * @since 6/3/2015
 */
public class OrderQuestionsFragment extends QuestionFragment {

    private View fragment;
    private List<EditText> answers = new ArrayList<>();
    private EditText correctAnswer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        fragment = inflater.inflate(R.layout.fragment_create_order_question, container, false);
        fragment.findViewById(R.id.Open_Question_Save).setOnClickListener(saveQuestion);
        fragment.findViewById(R.id.Order_Answer1);
        correctAnswer = (EditText) fragment.findViewById(R.id.CorrectAnswer);
        answers.add((EditText) fragment.findViewById(R.id.Order_Answer1));
        answers.add((EditText) fragment.findViewById(R.id.Order_Answer2));
        answers.add((EditText) fragment.findViewById(R.id.Order_Answer3));
        answers.add((EditText) fragment.findViewById(R.id.Order_Answer4));
        if (questionWrapper != null) {
            ((EditText) fragment.findViewById(R.id.Order_Vraag)).setText(questionWrapper.getStatement().getText().getValue());
        }
        for (EditText et : answers) {
            if (questionWrapper != null) {
                et.setText(questionWrapper.getCorrectAnswers().getResponse()[answers.indexOf(et)].getOption());
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
                Answers[] incorrectAnswers = new Answers[1];
                incorrectAnswers[0] = new Answers();
                List<Response> temporaryResponse = new ArrayList<>();
                Response incorrect = new Response();
                for (EditText et : answers) {
                    if (et.getText().toString().trim().length() != 0) {
                        int i = answers.indexOf(et);
                        incorrect.setOption(et.getText().toString());
                        incorrect.setOrder(i);
                        temporaryResponse.add(incorrect);
                    }
                    else{
                        incorrectAnswers[0].setResponse(temporaryResponse.toArray(new Response[temporaryResponse.size()]));
                        break;
                    }
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
}
