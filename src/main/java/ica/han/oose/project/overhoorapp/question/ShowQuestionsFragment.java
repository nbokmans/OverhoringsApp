package ica.han.oose.project.overhoorapp.question;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;

import java.util.List;

import ica.han.oose.project.overhoorapp.R;
import ica.han.oose.project.overhoorapp.json.models.questions.get.QuestionWrapper;

/**
 * TODO: korte beschrijving van klsse
 *
 * @author Roy van Keijsteren
 * @version 1.0
 * @since 6/3/2015
 */
public class ShowQuestionsFragment extends Fragment {

    private List<QuestionWrapper> questions;
    private View fragment;

    public void initShowQuestions(List<QuestionWrapper> questions) {
        this.questions = questions;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_question_list, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        fragment = inflater.inflate(R.layout.fragment_show_questions, container, false);
        QuestionListAdapter adapter = new QuestionListAdapter(getActivity(), R.layout.list_item_basic, android.R.id.text1, this.questions);
        AbsListView mListView = (AbsListView) fragment.findViewById(R.id.showQuestionsList);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener((AdapterView.OnItemClickListener) getActivity());
        return fragment;
    }
}