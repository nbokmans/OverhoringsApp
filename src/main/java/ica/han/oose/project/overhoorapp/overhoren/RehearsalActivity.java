package ica.han.oose.project.overhoorapp.overhoren;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;

import java.util.List;

import ica.han.oose.project.overhoorapp.NavigationDrawerActivity;
import ica.han.oose.project.overhoorapp.R;
import ica.han.oose.project.overhoorapp.json.models.rehearsals.read.Rehearsal;
import ica.han.oose.project.overhoorapp.json.models.topics.create.Topic;
import ica.han.oose.project.overhoorapp.knowledge.TopicActivity;
import ica.han.oose.project.overhoorapp.persistence.PersistenceAdapter;

//import ica.han.oose.project.overhoorapp.json.models.topics.create.Topic;
//import ica.han.oose.project.overhoorapp.json.wrappers.Topic;

/**
 * Created by coen on 4-6-2015.
 */
public class RehearsalActivity extends NavigationDrawerActivity implements AbsListView.OnItemClickListener {

    private Topic currentTopic;
    private List<Rehearsal> rehearsalList;
    PersistenceAdapter persistenceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    protected void onStart() {
        super.onStart();
        RehearsalFragment fragment = new RehearsalFragment();

        Intent i = getIntent();
        currentTopic = (Topic)i.getSerializableExtra("topic");

        persistenceAdapter = new PersistenceAdapter(this);
        rehearsalList = persistenceAdapter.getRehearsals(currentTopic);

        fragment.initFragment(rehearsalList);

        replaceFragment(fragment);
    }



    private void showCreateRehearsalInput(){
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View inflated = inflater.inflate(R.layout.diaglog_create_rehearsal, null);
        final EditText rehearsalName = (EditText) inflated.findViewById(R.id.rehearsalName);
        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setView(inflated)
                .setTitle("Naam van de overhoring");
        // Adding buttons
        builder.setPositiveButton("Toevoegen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                createRehearsal(rehearsalName.getText().toString());
            }
        });

        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void createRehearsal(String rehearsalName){
        persistenceAdapter.createRehearsal(currentTopic, rehearsalName, 1533156045605L, 1);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


        Intent intent = new Intent(this, QuizActivity.class);
        //intent.putExtra("topicObjId", currentTopic.get_id().get$oid());
        intent.putExtra("TopicId", currentTopic.get_id().toString());
        startActivity(intent);

    }



    /**
     * Handles the buttons in the top menu.
     * Addrehearsal lets the user create a new rehearsal
     * The home button activates a previous activity in order to navigate back.
     * @param item The item that is selected.
     * @return boolean if function called
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_rehearsal:
                showCreateRehearsalInput();
                return true;
            case android.R.id.home:
                Intent intent = new Intent(this, TopicActivity.class);
                intent.putExtra("topic", currentTopic);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
