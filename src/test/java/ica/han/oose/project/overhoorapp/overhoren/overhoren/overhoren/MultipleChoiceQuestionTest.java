package ica.han.oose.project.overhoorapp.overhoren.overhoren.overhoren;

import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

import ica.han.oose.project.overhoorapp.overhoren.MultipleChoiceQuestion;

public class MultipleChoiceQuestionTest {

    public MultipleChoiceQuestion question;
    String answer;
    List<String> correctAnswer;
    List<String> possibleAnswers = new ArrayList<>();
    String questionString;

    @Before
    public void setUp() throws Exception {
        answer = "auto";
        possibleAnswers.add("auto");
        possibleAnswers.add("fiets");
        possibleAnswers.add("bliep");
        possibleAnswers.add("groentesoep");
        correctAnswer.add("fiets");
        questionString = "Wat is het beste vervoersmiddel?";
        question = new MultipleChoiceQuestion();
        question.initMultipleChoiceQuestion(questionString, possibleAnswers, correctAnswer, 1);
    }
}