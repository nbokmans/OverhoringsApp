package ica.han.oose.project.overhoorapp.overhoren.overhoren.overhoren;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;

import ica.han.oose.project.overhoorapp.exceptions.EmptyStringException;
import ica.han.oose.project.overhoorapp.overhoren.OpenQuestion;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OpenQuestionTest {

    public OpenQuestion question;
    public ArrayList<String> answer;
    int mistakesAllowed;
    String questionString;

    @Rule
    public ExpectedException exception = ExpectedException.none();


    @Before
    public void setUp() {
        answer = new ArrayList<String>();
        answer.add("step");
        answer.add("autopet");
        mistakesAllowed = 1;
        questionString = "Welk vervoersmiddel wordt met ALLEEN de voet aangedreven";
        question = new OpenQuestion();
        question.initOpenQuestion(questionString, answer, mistakesAllowed, 0);
    }


    @Test
    public void goodOpenQuestionCorrect() {
        question.setInputAnswer(("autopet"));
        assertTrue(question.answerQuestion());
    }

    @Test
    public void spellingErrorAllowed() {
        question.setInputAnswer(("autoped"));
        assertTrue(question.answerQuestion());
    }

    @Test
    public void MissingCharacterAllowed() {
        question.setInputAnswer(("atopet"));
        assertTrue(question.answerQuestion());

    }

    @Test
    public void ExtraCharacterAllowed() {
        question.setInputAnswer(("auutopet"));
        assertTrue(question.answerQuestion());

    }

    @Test
    public void ExtraCharacterAndErrorAllowed() {
        question.initOpenQuestion(questionString, answer, 2, 0); // ArrayList<String> answer, int mistakesAllowed, String question, int questionNumber
        question.setInputAnswer(("auutupet"));
        question.answerQuestion();
    }

    @Test
    public void EmptyStringNotAllowed() {
        exception.expect(EmptyStringException.class);
        question.setInputAnswer("");
        question.answerQuestion();
    }

    @Test
    public void twoExtraCharactersNotAllowed() {
        question.setInputAnswer(("auuutpet"));
        assertFalse(question.answerQuestion());
    }

    @Test
    public void wrongOpenQuestionIncorrect() {
        question.setInputAnswer(("fiets"));
        assertFalse(question.answerQuestion());
    }
}