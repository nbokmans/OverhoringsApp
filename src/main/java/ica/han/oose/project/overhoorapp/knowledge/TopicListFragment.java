package ica.han.oose.project.overhoorapp.knowledge;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import ica.han.oose.project.overhoorapp.R;
import ica.han.oose.project.overhoorapp.json.models.topics.create.Topic;
import ica.han.oose.project.overhoorapp.json.wrappers.TopicId;

/**
 * Fragment that lists all Topics. Is responsible for the refreshing and has a setter to set the
 * topics to show.
 *
 * @author Jaap
 */
public class TopicListFragment extends Fragment implements AbsListView.OnItemClickListener {

    private OnFragmentInteractionListener mListener;
    private List<Topic> topics = new ArrayList<>();
    private AbsListView mListView;
    private TopicListAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    /**
     * Method to set the topics to show. Should be called from within an activity. It sets the
     * ListArray of topics and calls createTopicList() to generate the topiclist.
     *
     * @param ts the list of topics to set.
     */
    public void setTopics(List<Topic> ts) {
        topics = ts;
        mAdapter.addAll(ts);
        if (topics.size() == 0) {
            Toast.makeText(getActivity(), "Geen samenvattingen gevonden!", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Refreshes the list and adds a new topic
     *
     * @param t The topic to add to the adapter.
     */
    public void refresh(Topic t) {
        topics.add(0,t);
        mAdapter.add(t);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_topic_list, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_topic_list, container, false);
        mAdapter = new TopicListAdapter(getActivity(), R.layout.list_item_basic, android.R.id.text1, topics);
        mListView = (AbsListView) fragment.findViewById(R.id.topic_list);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            //      throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ((SummaryActivity) getActivity()).showTopic(topics.get(position));
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

}
//                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          