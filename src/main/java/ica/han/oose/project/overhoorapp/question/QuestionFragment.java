package ica.han.oose.project.overhoorapp.question;

import android.os.Bundle;
import android.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import ica.han.oose.project.overhoorapp.R;
import ica.han.oose.project.overhoorapp.json.models.questions.get.QuestionWrapper;

/**
 * TODO: korte beschrijving van klsse
 *
 * @author Roy van Keijsteren
 * @version 1.0
 * @since 6/6/2015
 */
public class QuestionFragment extends Fragment{

    public int state;
    public QuestionWrapper questionWrapper = null;
    public int CREATE = 0;
    public int UPDATE = 1;
    public View fragment;

    /**
     * init question
     *
     * @param question
     */
    public void initQuestion(QuestionWrapper question) {
        this.questionWrapper = question;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_single_question, menu);
    }
}
