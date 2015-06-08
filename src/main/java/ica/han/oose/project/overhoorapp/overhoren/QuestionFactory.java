package ica.han.oose.project.overhoorapp.overhoren;

import android.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import ica.han.oose.project.overhoorapp.json.models.questions.get.QuestionWrapper;
import ica.han.oose.project.overhoorapp.json.models.shared.Answers;
import ica.han.oose.project.overhoorapp.json.models.shared.Response;

/**
 * Created by roy_000 on 4/17/2015.
 */
public class QuestionFactory {

    public Fragment getQuestion(QuestionWrapper question, int questionNumber) {
        final List<String> correctAnswers = new ArrayList<String>();
        final List<String> incorrectAnswers = new ArrayList<String>();
        for (final Response r : question.getCorrectAnswers().getResponse()) {
            correctAnswers.add(r.getOption());
        }
        for (final Answers a : question.getIncorrectAnswers()) {
            for (final Response r : a.getResponse()) {
                incorrectAnswers.add(r.getOption());
            }
        }
        switch (question.getQType()) {
            case SINGLEANSWER:
                if (question.getIncorrectAnswers().length == 0) {
                    OpenQuestion openQuestion = new OpenQuestion();
                    openQuestion.initOpenQuestion(question.getStatement().getText().getValue(), correctAnswers, 0, questionNumber);
                    return openQuestion;
                } else {
                    MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion();
                    multipleChoiceQuestion.initMultipleChoiceQuestion(question.getStatement().getText().getValue(),
                            incorrectAnswers, correctAnswers, questionNumber);
                    return multipleChoiceQuestion;
                }
            case ORDERLIST:
                OrderQuestion orderQuestion = new OrderQuestion();
                orderQuestion.initOrderQuestions(correctAnswers);
                return orderQuestion;
            default:
                return null;
        }
    }
}
