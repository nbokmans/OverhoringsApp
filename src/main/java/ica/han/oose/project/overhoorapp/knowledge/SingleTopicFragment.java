package ica.han.oose.project.overhoorapp.knowledge;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import ica.han.oose.project.overhoorapp.R;
import ica.han.oose.project.overhoorapp.exceptions.UnauthorizedRequestException;
import ica.han.oose.project.overhoorapp.json.constants.Post;
import ica.han.oose.project.overhoorapp.json.models.topics.create.Topic;
import ica.han.oose.project.overhoorapp.persistence.PersistenceAdapter;
import ica.han.oose.project.overhoorapp.util.requestbuilder.impl.JSONDeleteRequestBuilder;

/**
 * Shows the topic name and the information saved in it
 *
 * @author Coen Smid
 * @version1.0
 * @since 1-5-2015
 */

public class SingleTopicFragment extends Fragment {

    private Topic topic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);

        TopicActivity act = (TopicActivity) getActivity();
        topic = act.getCurrentTopic();
        getActivity().setTitle(topic.getTitle());

        final View fragment = inflater.inflate(R.layout.fragment_show_topic, container, false);
        final PersistenceAdapter persistenceAdapter = new PersistenceAdapter(fragment.getContext());
        TextView topicContentEditText = (TextView) fragment.findViewById(R.id.ShowTopic_Content);
        topicContentEditText.setText(Html.fromHtml(topic.getContent()));

        Switch offlineAvailable = (Switch) fragment.findViewById(R.id.offlineAvailable);
        offlineAvailable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    persistenceAdapter.setTopicOffline(topic);
                } else {
                    persistenceAdapter.deleteOfflineTopic(topic);
                }
            }
        });


        if (persistenceAdapter.checkTopicOffline(topic)) { //TODO Willie adapter
            offlineAvailable.setChecked(true);
        } else {
            offlineAvailable.setChecked(false);
        }

        //FloatingActionButton fab = (FloatingActionButton) fragment.findViewById(R.id.fab);
        return fragment;
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_single_topic, menu);
    }
}
