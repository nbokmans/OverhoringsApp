package ica.han.oose.project.overhoorapp.overhoren;

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
import ica.han.oose.project.overhoorapp.json.models.rehearsals.read.Rehearsal;

/**
 * Created by coen on 4-6-2015.
 */
public class RehearsalFragment extends Fragment {

    private List<Rehearsal> rehearsalList;

    /*

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
     */


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);

        final View fragment = inflater.inflate(R.layout.fragment_show_rehearsal, container, false);


        RehearsalAdapter adapter = new RehearsalAdapter(getActivity(), R.layout.list_item_basic, android.R.id.text1, rehearsalList);
        AbsListView mListView = (AbsListView) fragment.findViewById(R.id.rehearsal_list);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener((AdapterView.OnItemClickListener) getActivity());


        return fragment;
    }

    public void initFragment(List<Rehearsal> rehearsalList){
        this.rehearsalList = rehearsalList;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_rehearsal, menu);
    }


}
