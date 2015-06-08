package ica.han.oose.project.overhoorapp.overhoren;

import java.util.ArrayList;

import ica.han.oose.project.overhoorapp.exceptions.ArrayListUnexpectedLengthException;
import ica.han.oose.project.overhoorapp.exceptions.DuplicateAnswerFoundException;
import ica.han.oose.project.overhoorapp.exceptions.EmptyStringException;

/**
 * Created by coen on 15-4-2015.
 * <p/>
 * Not yet implement!!
 */
public class ListQuestion {

    private String question;
    private ArrayList<String> answer;
    private int answersRequired;

    public ListQuestion(String question, int answersRequired, ArrayList<String> answer) {
        this.question = question;
        this.answer = answer;
        this.answersRequired = answersRequired;
    }

    public boolean answerQuestion(ArrayList<String> inputAnswer) {
        checkForErrors(inputAnswer, answersRequired);
        for (int i = 0; i < answersRequired; i++) {
            if (!answer.contains(inputAnswer.get(i)))
                return false;
        }

        return true;
    }

    private void checkForErrors(ArrayList<String> arrayList, int expectedLength) {

        if (arrayList.size() != expectedLength) {
            throw new ArrayListUnexpectedLengthException();
        }

        if (arrayList.contains("")) { // Check for empty String
            throw new EmptyStringException();

        }
        if (arrayList.get(0).equals(arrayList.get(1)) || arrayList.get(0).equals(arrayList.get(2)) || arrayList.get(1).equals(arrayList.get(2))) {
            throw new DuplicateAnswerFoundException();

        }

    }

}
