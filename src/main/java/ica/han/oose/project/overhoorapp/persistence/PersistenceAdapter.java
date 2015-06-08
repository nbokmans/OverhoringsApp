package ica.han.oose.project.overhoorapp.persistence;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ica.han.oose.project.overhoorapp.database.DBHelper;
import ica.han.oose.project.overhoorapp.endpoints.question.QuestionAPI;
import ica.han.oose.project.overhoorapp.endpoints.rehearsal.RehearsalAPI;
import ica.han.oose.project.overhoorapp.endpoints.summary.SummaryAPI;
import ica.han.oose.project.overhoorapp.endpoints.topic.TopicAPI;
import ica.han.oose.project.overhoorapp.json.constants.Get;
import ica.han.oose.project.overhoorapp.json.models.questions.get.QuestionWrapper;
import ica.han.oose.project.overhoorapp.json.models.rehearsals.read.QuestionAnswer;
import ica.han.oose.project.overhoorapp.json.models.rehearsals.read.Rehearsal;
import ica.han.oose.project.overhoorapp.json.models.shared._id;
import ica.han.oose.project.overhoorapp.json.models.summaries.read.SummaryElement;
import ica.han.oose.project.overhoorapp.json.models.topics.create.Topic;
import ica.han.oose.project.overhoorapp.json.wrappers.Summary;
import ica.han.oose.project.overhoorapp.json.wrappers.TopicId;
import ica.han.oose.project.overhoorapp.util.Config;
import ica.han.oose.project.overhoorapp.util.genson.Deserializer;
import ica.han.oose.project.overhoorapp.util.requestbuilder.impl.GetRequestBuilder;

/**
 * Checks if server connection is availabe,
 * then gets the data from the database or server.
 * Depending on the serverconnection.
 *
 * @author Wilko Zonnenberg
 * @version 1.0
 * @since 22-5-2015
 */
public class PersistenceAdapter {

    private DBHelper db;
    private String url = Config.BASE_URL;
    private List<QuestionWrapper> questionList;
    private List<Topic> topicList;
    private List<Summary> summaryList;
    private Topic topic;
    private Summary summary;
    private boolean result = false;

    public PersistenceAdapter(Context context) {
        db = new DBHelper(context);
    }

    public List<Summary> getSummaries() {
        if (checkServerConnection()) {
            summaryList = SummaryAPI.getMySummaries();
            for (int i = 0; i < summaryList.size(); i++) {
                refreshSummary(summaryList.get(i).getSummaryID());
            }
        } else {

            summaryList = db.getAllRowsSummary();
        }
        if (summaryList == null) {
            return summaryList = new ArrayList<>();
        }
        return summaryList;
    }

    public void refreshSummary(String summaryID) {
        if (db.getRowTopic(summaryID) != null) {
            db.deleteRowTopicTopicID(summaryID);

            getServerSummary(summaryID);
            for (int i = 0; i < summary.getTags().size(); i++) {

                if (db.checkTagName(summary.getTags().get(i).getText())) {
                    db.insertRowTag(summary.getTags().get(i).getText());
                }
            }
            for (int i = 0; i < summary.getTags().size(); i++) {
                db.insertRowTopicTag(summary.getTags().get(i).getText(), summaryID);
            }
        }
    }

    private void getServerSummary(final String summaryID) {
        final Thread fetchQuestionThread = new Thread(new Runnable() {
            @Override
            public void run() {
                final GetRequestBuilder getSummaries = new GetRequestBuilder(Get.SUMMARY_BY_ID.getUrl(summaryID));
                getSummaries.submit();
                final SummaryElement summ = (SummaryElement) Deserializer.deserialize(getSummaries.getResult().substring(11, getSummaries.getResult().length() - 1), SummaryElement.class);
                summary = new Summary(summ);
            }
        });
        fetchQuestionThread.start();
        try {
            fetchQuestionThread.join();
        } catch (InterruptedException e) {
            Log.e("PersistenceAdapter", e.getMessage(), e);
        }
    }

    public List<Topic> getTopics(final Summary currentSummary) {
        if (checkServerConnection()) {
            for(TopicId t : currentSummary.getChildrenTopics()){
                final Topic topic = TopicAPI.getTopicByID(t.getTopicObjectID());
                if (topic != null) {
                    topicList.add(topic);
                    refreshTopic(topic.get_id().get$oid());
                    refreshQuestions(topic.get_id().get$oid());
                }
            }
            return topicList;
        } else {
            return getTopicsWhenOffline(currentSummary);
        }
    }

    private List<Topic> getTopicsWhenOffline(Summary currentSummary) {
        List<TopicId> topicid = db.getRowTopicSummaryID(currentSummary.getSummaryID());
        List<Topic> topic = new ArrayList<>();
        for(int i = 0; i < topicid.size();i++){
            topic.add(topicid.get(i).getTopic().toTopic());
        }
        return topic;
    }


    public void refreshTopic(final String topicID) {
        db.deleteRowTopicTopicID(topicID);
        db.deleteQuestionTopicID(topicID);
        TopicAPI.getTopicByID(topicID);
        for (int i = 0; i < topic.getTags().length; i++) {
            if (db.checkTagName(topic.getTags()[i].getText())) {
                db.insertRowTag(topic.getTags()[i].getText());
            }
        }
        db.insertRowTopic(topic.getTitle(), SummaryAPI.getSummaryByTopicID(topic.get_id().get$oid()).getSummaryID(), topic.getContent(), topicID, 0);
        for (int i = 0; i < topic.getTags().length; i++) {
            db.insertRowTopicTag(topic.getTags()[i].getText(), topicID);
        }
    }

    public boolean setTopicOffline(final Topic topic){
        if(!checkServerConnection()){
            Log.v("setTopicOffline", "Geen internet");
            return false;
        }
        Summary summaryByTopicID = SummaryAPI.getSummaryByTopicID(topic.get_id().get$oid());
        TopicId topicId = summaryByTopicID.getRootTopic();
        db.insertRowTopic(topicId.getTitle(), null, topicId.getContent(), topicId.getTopicObjectID(), 1);
        db.insertRowTopic(topic.getTitle(), summaryByTopicID.getSummaryID(), topic.getContent(), topic.get_id().get$oid(), 0);
        List<Rehearsal> rehearsalList = RehearsalAPI.getRehearsalsForTopicID(topicId.getTopicObjectID());
        for(int i = 0; i <rehearsalList.size();i++){
            setRehearsalOffline(rehearsalList.get(i), topicId);
        }
        return true;
    }


    public boolean checkTopicOffline(final Topic topic) {
        return db.getRowTopic(topic.get_id().get$oid()) != null;
    }

    public boolean deleteOfflineTopic(final Topic topic){
        return db.deleteRowTopicTopicID(topic.get_id().get$oid()) && db.deleteRowRehearsalTopicID(topic.get_id().get$oid()) &&
        db.deleteQuestionTopicID(topic.get_id().get$oid());
    }

    public void startOfflineRehearsal(Rehearsal rehearsal, Topic topic){
        for(int i = 0; i < rehearsal.getQuestionIds().length; i++) {
            db.insertRowRehearsal(rehearsal.get_id().get$oid(), topic.get_id().get$oid(), rehearsal.getQuestionIds()[i], 1);
        }
        setQuestionsRehearsal(topic.toTopicId(), rehearsal);
    }

    public void createRehearsal(Topic topic, String label, long endDate, int minimalCorrect){
        if(checkServerConnection()){
            RehearsalAPI.createRehearsal(topic.get_id().get$oid(), label, minimalCorrect, endDate);
        }else{
            Rehearsal rehearsal = new Rehearsal();
            rehearsal = setRehearsal(label, endDate, minimalCorrect, rehearsal);
            startOfflineRehearsal(rehearsal, topic);
        }
    }

    private Rehearsal setRehearsal(String label, long endDate, int minimalCorrect, Rehearsal rehearsal) {
        int randomNum = (int) Math.random();
        _id id = new _id();
        id.set$oid(Integer.toString(randomNum));
        rehearsal.set_id(id);
        rehearsal.setLabel(label);
        rehearsal.setEndDate(endDate);
        rehearsal.setMinCorrect(minimalCorrect);
        return rehearsal;
    }


    private void setRehearsalOffline(Rehearsal rehearsalOffline, TopicId topic){
        for(int i = 0; i < rehearsalOffline.getQuestionIds().length; i++) {
            db.insertRowRehearsal(rehearsalOffline.get_id().get$oid(), topic.getTopicObjectID(), rehearsalOffline.getQuestionIds()[i], 0);
        }
        setQuestionsOffline(topic);
    }

    public void setQuestionsOffline(TopicId topic) {
        ica.han.oose.project.overhoorapp.json.models.topics.create.Topic top = TopicAPI.getTopicByID(topic.getTopicObjectID());
        List<QuestionWrapper> question = QuestionAPI.getQuestionsByTopicID(top.get_id().get$oid());
        insertQuestions(top, question);
    }

    public void setQuestionsRehearsal(TopicId topic, Rehearsal rehearsal) {
        List<QuestionWrapper> questions = db.getRowsQuestionTopicID(topic.getTopicObjectID());
        for(int i = 0; i < questions.size(); i++) {
            db.insertRowRehearsal(rehearsal.get_id().get$oid(), topic.getTopicObjectID(),questions.get(i).get_id().get$oid(), 0);
        }
    }

    private void insertQuestions(Topic top, List<QuestionWrapper> question) {
        for(int i = 0; i < question.size();i++) {
            QuestionWrapper question1 = question.get(i);
            db.insertRowQuestion(question1.getStatement().getText().getValue(), top.get_id().get$oid(),question1.getQuestionType(),question1.get_id().get$oid());
            insertCorrectAnswers(question1);
            insertIncorrectAnswers(question1);
        }
    }

    private void insertIncorrectAnswers(QuestionWrapper question1) {
        for(int k = 0; k < question1.getIncorrectAnswers().length;k++){
            db.insertRowIncorrectAnswer(question1.getIncorrectAnswers()[0].getResponse()[k].getOption(), question1.get_id().get$oid());
        }
    }

    private void insertCorrectAnswers(QuestionWrapper question1) {
        for(int l = 0; l < question1.getCorrectAnswers().getResponse().length;l++){
            db.insertRowCorrectAnswer(question1.getCorrectAnswers().getResponse()[l].getOption(), question1.get_id().get$oid());
        }
    }

    public List<QuestionWrapper> getQuestions(final String topicID, final String RehearsalID) {
        if (checkServerConnection()) {
            questionList = QuestionAPI.getQuestionsByTopicID(topicID);
        } else {
            questionList = db.getRowsQuestionTopicID(topicID);
        }

        return questionList;
    }

    public List<Rehearsal> getRehearsals(final Topic topic) {
        List<Rehearsal> rehearsalList;
        if (checkServerConnection()){
            rehearsalList = RehearsalAPI.getRehearsalsForTopicID(topic.get_id().get$oid());
        }else{
            rehearsalList =  db.getNotSentToServerRehearsals();
        }

        return rehearsalList;
    }


    public List<Rehearsal> getOfflineRehearsals(final Topic topic){
        return db.getOfflineOnlyRehearsals(topic);
    }

    private void sendAnswerQuestion(Rehearsal rehearsal,QuestionAnswer questionAnswer) {
        if (checkServerConnection()) {
            RehearsalAPI.sendAnswerForRehearsal(rehearsal.get_id().get$oid(), questionAnswer);
            sendDBAnswerQuestions(rehearsal);
        } else {
            db.insertRowAnswerValue(questionAnswer.getQuestionId().toString(), questionAnswer.getAnswers().getResponse()[0].getOption());
        }
    }

    private void sendDBAnswerQuestions(Rehearsal rehearsal) {
        List<QuestionAnswer> questionAnswer = db.getAnswerQuestion();
        if(questionAnswer.isEmpty() ){
            for(int i = 0; i < questionAnswer.size(); i++){
                RehearsalAPI.sendAnswerForRehearsal(rehearsal.get_id().get$oid(),questionAnswer.get(i));
            }
        }

    }

    public void refreshQuestions(final String topicID) {
        db.deleteQuestionTopicID(topicID);
        questionList = QuestionAPI.getQuestionsByTopicID(topicID);
        for (int i = 0; i < questionList.size(); i++) {
            QuestionWrapper question = questionList.get(i);
            if (!db.checkTypeName(question.getQType().name())) {
                db.insertRowType(question.getQType().name());
            }
            db.insertRowQuestion(question.getStatement().getText().getValue(), topicID,
                    question.getQType().name(), question.get_id().get$oid());
        }
    }

    /**
     * Checks if server connection is available
     *
     * @return True if connection succeeded
     */
    public boolean checkServerConnection() {
        final Thread fetchQuestionThread = connectToServer();
        fetchQuestionThread.start();
        try {
            fetchQuestionThread.join();
        } catch (InterruptedException e) {
            Log.v("PersistenceAdapter", String.valueOf(e));
        }
        return result;
    }

    private Thread connectToServer() {
        return new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL(PersistenceAdapter.this.url);   // Change to "http://google.com" for www  test.
                        final HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                        urlc.setConnectTimeout(3 * 1000);          // 3s.
                        urlc.connect();
                        if(urlc.getResponseCode() == 200){result = true;}
                    } catch (MalformedURLException e1) {
                        Log.v("PersistenceAdapter", String.valueOf(e1));
                        result = false;
                    } catch (IOException e) {
                        Log.v("PersistenceAdapter", String.valueOf(e));
                        result = false;
                    }
                }
            });
    }
}
