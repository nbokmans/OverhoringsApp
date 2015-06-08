package ica.han.oose.project.overhoorapp.knowledge;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import ica.han.oose.project.overhoorapp.NavigationDrawerActivity;
import ica.han.oose.project.overhoorapp.R;
import ica.han.oose.project.overhoorapp.endpoints.topic.TopicAPI;
import ica.han.oose.project.overhoorapp.json.models.shared.ContributorElement;
import ica.han.oose.project.overhoorapp.json.models.shared.TagElement;
import ica.han.oose.project.overhoorapp.json.models.topics.create.Topic;
import ica.han.oose.project.overhoorapp.overhoren.RehearsalActivity;
import ica.han.oose.project.overhoorapp.question.QuestionActivity;
import ica.han.oose.project.overhoorapp.util.jsonbuilder.TopicBuilder;

public class TopicActivity extends NavigationDrawerActivity implements AbsListView.OnItemClickListener {
    private Topic currentTopic;

    public Topic getCurrentTopic() {
        return currentTopic;
    }
    private EditTopicFragment editFragment;
    private SingleTopicFragment readFragment;

    /**
     * Method that does the initial creation of the fragment. This method starts showing the selected topic.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        currentTopic = (Topic)i.getSerializableExtra("topic");
    }

    @Override
    protected void onStart() {
        super.onStart();
        showTopic();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Starts the fragment that allows you to edit topics.
     *
     */
    public void editTopic() {
        editFragment = new EditTopicFragment();
        editFragment.setTopic(currentTopic);
        replaceFragment(editFragment);
    }

    /**
     * Starts the fragment that shows the relevant information of the topic.
     */
    private void showTopic() {
        readFragment = new SingleTopicFragment();
        replaceFragment(readFragment);
    }


    /**
     * Starts the quiz. Allows you to select topics to quiz.
     *
     * @param v
     */

    public void startRehearsal(View v) {
        Intent intent = new Intent(this, RehearsalActivity.class);
        //intent.putExtra("topicObjId", currentTopic.get_id().get$oid());
        intent.putExtra("topic", currentTopic);
        startActivity(intent);
    }
    /**
     * Edits an existing topic
     *
     *
     * @param title   The new topicname.
     * @param content The new content of the topic.
     */
    public void editTopicData(String title, String content) {
        final TopicBuilder topicBuilder = new TopicBuilder();
        topicBuilder.setObjectID(currentTopic.get_id().get$oid()).setOwner(Integer.parseInt(currentTopic.getOwner().getUid()))
                .setTitle(title).setContent(content).setIsRoot(currentTopic.getIsRoot()).setVersion(currentTopic.getVersion());

        for(ContributorElement cont : currentTopic.getContributors()){
            topicBuilder.addContributor(Integer.parseInt(cont.getUid()));
        }

        for(TagElement tag : currentTopic.getTags()){
            topicBuilder.addTag(tag.getText());
        }

        TopicAPI.updateTopic(topicBuilder);
        String json = topicBuilder.toJson();

        currentTopic.setTitle(title);
        currentTopic.setContent(content);
        showTopic();
    }

    /**
     * Creates the buttons in the menu of the screen. Also includes the methods triggerd when clicked on the buttons.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_topic:
                showMessage();
                return true;
            case R.id.action_edit_topic:
                editTopic();
                return true;
            case R.id.action_add_question:
                Intent intent = new Intent(this, QuestionActivity.class);
                intent.putExtra("topic", currentTopic);
                startActivity(intent);
                return true;
            case R.id.action_accept_edit:
                editTopicData(editFragment.getNewTitle(), editFragment.getNewContent());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     *
     */
    private void showMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Weet u zeker dat u de samenvatting \'" + getTitle() + "\' wilt verwijderen?")
                .setTitle("Waarschuwing!");
        builder.setPositiveButton("Annuleer", null);
        builder.setNegativeButton("Verwijder", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                deleteTopic(currentTopic.get_id().get$oid());
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * @param topicObjId
     */
    private void deleteTopic(final String topicObjId) {
        TopicAPI.deleteTopicByID(topicObjId);
        Intent intent = new Intent(this, SummaryActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}