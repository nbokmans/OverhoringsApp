package ica.han.oose.project.overhoorapp.question;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import ica.han.oose.project.overhoorapp.NavigationDrawerActivity;
import ica.han.oose.project.overhoorapp.R;
import ica.han.oose.project.overhoorapp.endpoints.question.QuestionAPI;
import ica.han.oose.project.overhoorapp.endpoints.user.UserAPI;
import ica.han.oose.project.overhoorapp.json.constants.QuestionType;
import ica.han.oose.project.overhoorapp.json.models.questions.CreateQuestion;
import ica.han.oose.project.overhoorapp.json.models.questions.get.QuestionWrapper;
import ica.han.oose.project.overhoorapp.json.models.shared.InfoElement;
import ica.han.oose.project.overhoorapp.json.models.shared.Owner;
import ica.han.oose.project.overhoorapp.json.models.shared._id;
import ica.han.oose.project.overhoorapp.json.models.topics.create.Topic;
import ica.han.oose.project.overhoorapp.persistence.PersistenceAdapter;

/**
 * TODO: korte beschrijving van klsse
 *
 * @author Roy van Keijsteren
 * @version 1.0
 * @since 6/3/2015
 */
public class QuestionActivity extends NavigationDrawerActivity implements AbsListView.OnItemClickListener {

    private List<QuestionWrapper> questions;
    private Topic currentTopic;
    private QuestionWrapper currentQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        currentTopic = (Topic) intent.getSerializableExtra("topic");

        PersistenceAdapter persistenceAdapter = new PersistenceAdapter(this);
        questions = persistenceAdapter.getQuestions(currentTopic.get_id().get$oid(), null);
//        questions = QuestionAPI.getQuestionsByTopicID(currentTopic.get_id().get$oid());
        ShowQuestionsFragment showQuestionsFragment = new ShowQuestionsFragment();
        showQuestionsFragment.initShowQuestions(questions);

        setFragment(showQuestionsFragment);
    }

    /**
     * Creates the buttons in the menu of the screen. Also includes the methods triggerd when clicked on the buttons.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        boolean result = false;
        switch (item.getItemId()) {
            case R.id.action_add_question:
                addQuestionAlert();
                result = true;
                break;
            case R.id.action_delete_question:
                deleteQuestionAlert();
                break;
            default:
                result = false;
        }
        return result;
    }

    /**
     *
     */
    private void addQuestionAlert() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final String[] d = new String[3];
        d[0] = "Openvraag";
        d[1] = "Meerkeuze vraag";
        d[2] = "order vraag";
        builder.setTitle("Kies een vraag type")
                .setCancelable(false)
                .setItems(d, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                replaceFragment(new OpenQuestionFragment());
                                break;
                            case 1:
                                replaceFragment(new MultiplechoiceQuestionFragment());
                                break;
                            case 2:
                                replaceFragment(new OrderQuestionsFragment());
                                break;
                        }
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     *
     *
     */
    private void deleteQuestionAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Weet u zeker dat u de Question \'" + currentQuestion.getStatement().getText().getValue() + "\' wilt verwijderen?")
                .setTitle("Waarschuwing!");
        builder.setPositiveButton("Annuleer", null);
        builder.setNegativeButton("Verwijder", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                deleteQuestion();
                refresh();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        currentQuestion = (QuestionWrapper) adapterView.getAdapter().getItem(i);
        if (currentQuestion.getIncorrectAnswers().length == 0 && currentQuestion.getQType() == QuestionType.SINGLEANSWER) {
            OpenQuestionFragment openQuestionFragment = new OpenQuestionFragment();
            openQuestionFragment.initQuestion(currentQuestion);
            replaceFragment(openQuestionFragment);
        } else if (currentQuestion.getIncorrectAnswers().length >= 1 && currentQuestion.getQType() == QuestionType.SINGLEANSWER) {
            MultiplechoiceQuestionFragment multiplechoiceQuestionFragment = new MultiplechoiceQuestionFragment();
            multiplechoiceQuestionFragment.initQuestion(currentQuestion);
            replaceFragment(multiplechoiceQuestionFragment);
        } else if (currentQuestion.getQType() == QuestionType.ORDERLIST) {
            OrderQuestionsFragment orderQuestionsFragment = new OrderQuestionsFragment();
            orderQuestionsFragment.initQuestion(currentQuestion);
            replaceFragment(orderQuestionsFragment);
        }
    }

    /**
     * @param createQuestion
     */
    public void saveQuestionData(CreateQuestion createQuestion) {
        _id topicId = new _id();
        topicId.set$oid(currentTopic.get_id().get$oid());
        createQuestion.setTopicId(topicId);
        final Owner owner = new Owner();
        owner.setUid(Integer.toString(UserAPI.getUserInfo().getUserID()));
        createQuestion.setInfo(new InfoElement[0]);
        createQuestion.setCreationDate(12348912398L);
        createQuestion.setUpdateDate(123451831234L);
        QuestionAPI.createQuestion(createQuestion);
        refresh();
    }

    public void updateQuestionData(String question, List<String> correctAnswer, ArrayList<String>... incorrectAnswer) {
        //todo update shit
        ShowQuestionsFragment showQuestionsFragment = new ShowQuestionsFragment();
        showQuestionsFragment.initShowQuestions(questions);
        replaceFragment(showQuestionsFragment);
    }

    private void deleteQuestion() {
        QuestionAPI.deleteQuestion(currentQuestion.get_id().get$oid());
    }

    private void refresh() {
        PersistenceAdapter persistenceAdapter = new PersistenceAdapter(this);
        questions = persistenceAdapter.getQuestions(currentTopic.get_id().get$oid(), null);
        ShowQuestionsFragment showQuestionsFragment = new ShowQuestionsFragment();
        showQuestionsFragment.initShowQuestions(questions);
        replaceFragment(showQuestionsFragment);
    }


}
