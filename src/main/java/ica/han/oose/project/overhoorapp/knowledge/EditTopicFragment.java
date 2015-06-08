package ica.han.oose.project.overhoorapp.knowledge;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import ica.han.oose.project.overhoorapp.R;
import ica.han.oose.project.overhoorapp.json.models.topics.create.Topic;

/**
 * TODO: korte beschrijving van de klasse
 *
 * @author Bob Linthorst
 * @version 1.0
 * @since 30-4-2015
 */


public class EditTopicFragment extends Fragment {

    View fragment;
    private Topic currentTopic;

    EditText newTitle;
    EditText newContent;

    public String getNewTitle(){
        return newTitle.getText().toString();
    }

    public String getNewContent(){
        return newContent.getText().toString();
    }


    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return Method that does the initial creation of the fragment. Includes making the button and making the listener for the button.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        fragment = inflater.inflate(R.layout.fragment_edit_topic, container, false);
        return fragment;
    }

    /**
     * Methods that start when the fragment is visible to the user. Sets the name of the topic and the content of the topic.
     */
    @Override
    public void onStart() {
        super.onStart();

        newTitle = ((EditText) getView().findViewById(R.id.topicName));
        newTitle.setText(currentTopic.getTitle());

        newContent = ((EditText) getView().findViewById(R.id.topicContent));
        newContent.setText(currentTopic.getContent());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_single_topic_edit, menu);
    }

    public void setTopic(Topic topic)
    {
        currentTopic = topic;
    }

}
