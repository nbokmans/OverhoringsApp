package ica.han.oose.project.overhoorapp.knowledge;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import ica.han.oose.project.overhoorapp.NavigationDrawerActivity;
import ica.han.oose.project.overhoorapp.R;
import ica.han.oose.project.overhoorapp.endpoints.summary.SummaryAPI;
import ica.han.oose.project.overhoorapp.endpoints.topic.TopicAPI;
import ica.han.oose.project.overhoorapp.exceptions.NameIncorrectException;
import ica.han.oose.project.overhoorapp.exceptions.NameToShortException;
import ica.han.oose.project.overhoorapp.exceptions.UnauthorizedRequestException;
import ica.han.oose.project.overhoorapp.json.constants.Post;
import ica.han.oose.project.overhoorapp.json.models.shared.ContributorElement;
import ica.han.oose.project.overhoorapp.json.models.shared.TagElement;
import ica.han.oose.project.overhoorapp.json.models.topics.create.Topic;
import ica.han.oose.project.overhoorapp.json.wrappers.Summary;
import ica.han.oose.project.overhoorapp.json.wrappers.TopicId;
import ica.han.oose.project.overhoorapp.persistence.PersistenceAdapter;
import ica.han.oose.project.overhoorapp.util.PreferenceSaver;
import ica.han.oose.project.overhoorapp.util.jsonbuilder.TopicBuilder;
import ica.han.oose.project.overhoorapp.util.requestbuilder.impl.JSONPostRequestBuilder;

/**
 * TODO: korte beschrijving van de klasse
 *
 * @author Bob Linthorst
 * @version 1.0
 * @since 28-4-2015
 */
public class SummaryActivity extends NavigationDrawerActivity {

    private TopicListFragment topicListFragment;
    private SummaryListFragment summaryListFragment;
    private Summary currentSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showSummaryList();
        //showTopicList("555ee714230000b200985116");
    }

    /**
     * Loads the topics
     */
    private List<Topic> loadTopics() {
        if (currentSummary != null) {
            PreferenceSaver preferenceSaver = new PreferenceSaver();
            String username = preferenceSaver.getFromPrefs(this, "username", null);
            if (!username.isEmpty()) {
                //TODO: FIX SYNTAX ERRORS!
                //PersistenceAdapter persistenceAdapter = new PersistenceAdapter(this);
                //return persistenceAdapter.getTopics(currentSummaryID);
            }

            final ProgressDialog progressUnit = new ProgressDialog(this);
            new LoadTopicsTask(){
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    progressUnit.setTitle("Even geduld!");
                    progressUnit.setMessage("Samenvattingen laden...");
                    progressUnit.show();
                }

                @Override
                protected void onPostExecute(List<Topic> topics) {
                    super.onPostExecute(topics);
                    topicListFragment.setTopics(topics);
                    progressUnit.hide();
                }
            }.execute(currentSummary);
        }
        return null;
    }

    /**
     * Loads the summaries connected to the user and adds them to the fragment.
     */
    private void loadSummaries() {
        final ProgressDialog progressUnit = new ProgressDialog(this);
        new LoadSummariesTask(){

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressUnit.setTitle("Even geduld!");
                progressUnit.setMessage("Onderwerpen laden...");
                progressUnit.show();
            }

            @Override
            protected void onPostExecute(List<Summary> summaries) {
                super.onPostExecute(summaries);
                summaryListFragment.setSummaries(summaries);
                setProgressBarIndeterminateVisibility(false);
                progressUnit.hide();
            }
        }.execute(this);
    }

    /**
     *
     */
    public void showSummaryList() {
        summaryListFragment = new SummaryListFragment();
        loadSummaries();
        setTitle("Onderwerpen");
        replaceFragment(summaryListFragment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    /**
     *
     */
    public void showTopicList(Summary currentSummary) {
        this.currentSummary = currentSummary;
        topicListFragment = new TopicListFragment();
        loadTopics();
        replaceFragment(topicListFragment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_add_topic:
                showAddTopicDialog();
                return true;
            //case R.id.action_edit_summary_name:
            //    showChangeSummaryNameDialog();
            //    return true;
            case R.id.action_add_summary:
                showAddSummaryDialog();
                return true;
            case android.R.id.home:
                showSummaryList();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showAddSummaryDialog() {
        final Context ctx = this;
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View inflated = inflater.inflate(R.layout.dialog_create_summary, null);
        final EditText summaryName = (EditText) inflated.findViewById(R.id.summaryName);
        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setView(inflated)
                .setTitle("Titel onderwerp");
        // Adding buttons
        builder.setPositiveButton("Toevoegen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                final List<Integer> intL = new ArrayList<Integer>();
                final List<String> strL = new ArrayList<String>();

                final List<ContributorElement> contL = new ArrayList<ContributorElement>();
                final List<TagElement> tagL = new ArrayList<TagElement>();

                if (summaryName.getText().toString().isEmpty()) {
                    dialog.dismiss();
                    Toast.makeText(ctx, "Ongeldige invoer! Probeer opnieuw.", Toast.LENGTH_LONG).show();
                    showAddSummaryDialog();
                } else {
                    Thread t = new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            final Summary s = SummaryAPI.createSummary("Description placeholder", summaryName.getText().toString(), contL, tagL, new Long(23425));
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    summaryListFragment.refresh(s);
                                }
                            });
                        }
                    };
                    t.run();


                }
            }
        });

        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showAddTopicDialog() {
        // 1. Instantiate an AlertDialog.Builder with its constructor
        final Context ctx = this;
        LayoutInflater inflater = this.getLayoutInflater();
        final View inflated = inflater.inflate(R.layout.dialog_create_topic, null);
        final EditText topicName = (EditText) inflated.findViewById(R.id.topicName);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setView(inflated)
                .setTitle("Titel samenvatting");
        // Adding buttons
        builder.setPositiveButton("Toevoegen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                List<Integer> intL = new ArrayList<Integer>();
                List<String> strL = new ArrayList<String>();

                if (topicName.getText().toString().isEmpty()) {
                    dialog.dismiss();
                    Toast.makeText(ctx, "Ongeldige invoer! Probeer opnieuw.", Toast.LENGTH_LONG).show();
                    showAddTopicDialog();
                } else {
                    Topic returned = TopicAPI.createTopicInSummary(currentSummary, false,
                            topicName.getText().toString(), "Plaats hier uw tekst...", strL, intL);

                    topicListFragment.refresh(returned);
                }
            }
        });

        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Method to show the dialog to change the current selected summary's name.
     */
    private void showChangeSummaryNameDialog() {
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View inflated = inflater.inflate(R.layout.dialog_edit_summary_name, null);
        final EditText summaryName = (EditText) inflated.findViewById(R.id.summaryName);
        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setView(inflated)
                .setTitle("Wijzig naam");
        summaryName.setText(currentSummary.getRootTopic().getTitle());
        // Adding buttons
        builder.setPositiveButton("Wijzigen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                try {
                    updateSummaryRootTopic(summaryName.getText().toString());
                } catch (UnauthorizedRequestException e) {
                    e.printStackTrace();
                }
            }
        });

        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Method to change the name of the summary on the server.
     *
     * @param newName The new name of the current summary.
     * @throws UnauthorizedRequestException
     */
    private void updateSummaryRootTopic(String newName) throws UnauthorizedRequestException {
        TopicId rootTopic = currentSummary.getRootTopic();
        final TopicBuilder topicBuilder = new TopicBuilder();
        topicBuilder.setObjectID(rootTopic.getTopicObjectID()).setOwner(rootTopic.getOwnerUserID())
                .setTitle(newName).setContent(rootTopic.getContent() != null ? rootTopic.getContent() : "").setIsRoot(true);

        for (int i = 0; i < rootTopic.getTags().size(); i++) {
            topicBuilder.addTag(rootTopic.getTags().get(i).getText());
        }

        for (int i = 0; i < rootTopic.getContributors().size(); i++) {
            int t = Integer.parseInt(rootTopic.getContributors().get(i).getUid());
            topicBuilder.addContributor(t);
        }

        final JSONPostRequestBuilder b = new JSONPostRequestBuilder(Post.UPDATE_TOPIC.getUrl());
        String s = topicBuilder.toJson();
        b.setContent(topicBuilder.toJson());
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    b.submit();
                } catch (UnauthorizedRequestException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * Checks the validity of of the name of the topic.
     *
     * @param name name of the topic
     * @return true or throw error.
     */
    protected boolean checkName(String name) {
        Pattern p = Pattern.compile("[^A-Za-z0-9,',','!','.']");
        if (name.length() < 3) {
            throw new NameToShortException();
        } else if (p.matcher(name).find()) {
            throw new NameIncorrectException();
        }
        return true;
    }

    /**
     * TODO
     *
     * @param topic
     */
    public void showTopic(Topic topic) {
        Intent intent = new Intent(this, TopicActivity.class);
        intent.putExtra("topic", topic);
        startActivity(intent);
    }

    /**
     * TODO moet nog gefixed worden
     *
     * @param v
     */
    public void startQuiz(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final SummaryActivity act = this;
        builder.setMessage("Wilt u de overhoring starten?")
                .setTitle("Overhoor mij!");
        builder.setPositiveButton("Starten!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(act, ica.han.oose.project.overhoorapp.overhoren.QuizActivity.class);
                //TODO hardcoded veranderen in de topic waar deze call vandaan komt
                //intent.putExtra("TopicId", "5553b13740032a65008ce7f6");
                //startActivity(intent);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
