package ica.han.oose.project.overhoorapp.question;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import ica.han.oose.project.overhoorapp.R;
import ica.han.oose.project.overhoorapp.json.constants.QuestionType;
import ica.han.oose.project.overhoorapp.json.models.questions.CreateQuestion;
import ica.han.oose.project.overhoorapp.json.models.questions.get.QuestionWrapper;
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
public class OpenQuestionFragment extends QuestionFragment {

    /**
     * handle savebutton action
     */
    View.OnClickListener saveQuestion = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String question = ((EditText) fragment.findViewById(R.id.Open_Question_Vraag)).getText().toString();
            List<String> answers = new ArrayList<>();
            answers.add(((EditText) fragment.findViewById(R.id.Open_Question_Antwoord)).getText().toString());
            if (state == UPDATE) {
                ((QuestionActivity) getActivity()).updateQuestionData(question, answers);
            } else if (state == CREATE) {
                CreateQuestion cq = new CreateQuestion();
                //correct answer
                Answers answer = new Answers();
                Response[] response = new Response[1];
                response[0] = new Response();
                response[0].setOption(answers.get(0));
                answer.setResponse(response);
                //incorrect answer
                cq.setIncorrectAnswers(new Answers[0]);
                Statement statement = new Statement();
                Text text = new Text();
                text.setValue(question);
                statement.setText(text);
                cq.setCorrectAnswers(answer);
                cq.setStatement(statement);

                cq.setQuestionType(QuestionType.SINGLEANSWER.toString().toLowerCase());
                ((QuestionActivity) getActivity()).saveQuestionData(cq);
            }
        }
    };



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.state = CREATE;
        fragment = inflater.inflate(R.layout.fragment_create_open_question, container, false);
        fragment.findViewById(R.id.Open_Question_Save).setOnClickListener(saveQuestion);
        if (questionWrapper != null) {
            this.state = UPDATE;
            ((EditText) fragment.findViewById(R.id.Open_Question_Vraag)).setText(this.questionWrapper.getStatement().getText().getValue());
            ((EditText) fragment.findViewById(R.id.Open_Question_Antwoord)).setText(this.questionWrapper.getCorrectAnswers().getResponse()[0].getOption());
        }
        return fragment;
    }
}
