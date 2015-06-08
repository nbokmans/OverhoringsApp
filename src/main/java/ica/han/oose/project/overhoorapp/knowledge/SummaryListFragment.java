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
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import ica.han.oose.project.overhoorapp.R;
import ica.han.oose.project.overhoorapp.json.wrappers.Summary;

/**
 * Fragment that lists all Topics. Is responsible for the refreshing and has a setter to set the
 * topics to show.
 *
 * @author Jaap
 */
public class SummaryListFragment extends Fragment implements AbsListView.OnItemClickListener {

    private OnFragmentInteractionListener mListener;
    private List<Summary> summaries = new ArrayList<Summary>();
    private View view;
    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private SummaryListAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
//        mAdapter = new SummaryListAdapter(getActivity(), R.layout.list_item_basic, android.R.id.text1, summaries);
    }

    /**
     * Method to set the topics to show. Should be called from within an activity. It sets the
     * ListArray of topics and calls createTopicList() to generate the topiclist.
     *
     * @param ts the list of topics to set.
     */
    public void setSummaries(List<Summary> ts) {
        summaries = ts;
        mAdapter.addAll(ts);
    }

    /**
     * Method to refresh the list. Creates a new adapter and applies the new adapter to the ListView.
     */
    public void refresh(Summary s) {
        summaries.add(0, s);
        mAdapter.add(s);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_summary_list, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mAdapter = new SummaryListAdapter(getActivity(), R.layout.list_item_basic, android.R.id.text1, summaries);
        view = inflater.inflate(R.layout.fragment_summary_list, container, false);
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

        return view;
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
        ((SummaryActivity) getActivity()).showTopicList(summaries.get(position));
        TextView txt = (TextView) view.findViewById(R.id.list_item_basic_textview);
        getActivity().setTitle(txt.getText().toString());
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
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
