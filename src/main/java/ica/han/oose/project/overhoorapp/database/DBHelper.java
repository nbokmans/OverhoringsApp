package ica.han.oose.project.overhoorapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ica.han.oose.project.overhoorapp.endpoints.user.UserAPI;
import ica.han.oose.project.overhoorapp.exceptions.NoSummaryInsertException;
import ica.han.oose.project.overhoorapp.json.models.questions.get.QuestionWrapper;
import ica.han.oose.project.overhoorapp.json.models.rehearsals.read.Item;
import ica.han.oose.project.overhoorapp.json.models.rehearsals.read.QuestionAnswer;
import ica.han.oose.project.overhoorapp.json.models.rehearsals.read.Rehearsal;
import ica.han.oose.project.overhoorapp.json.models.shared.Answers;
import ica.han.oose.project.overhoorapp.json.models.shared.Response;
import ica.han.oose.project.overhoorapp.json.models.shared.Statement;
import ica.han.oose.project.overhoorapp.json.models.shared.TagElement;
import ica.han.oose.project.overhoorapp.json.models.shared.Text;
import ica.han.oose.project.overhoorapp.json.models.shared.TopicId;
import ica.han.oose.project.overhoorapp.json.models.shared._id;
import ica.han.oose.project.overhoorapp.json.models.summaries.read.SummaryElement;
import ica.han.oose.project.overhoorapp.json.models.topics.create.Topic;
import ica.han.oose.project.overhoorapp.json.wrappers.Summary;


/**
 * Database helper, used to CRUD the database.
 *
 * @author Wilko Zonnenberg
 * @version 1.0
 * @since 28-4-2015
 */
public class DBHelper {


    /**
     * Tables
     */
    private static final String TABLEKEY_ACCOUNT = "Account";
    private static final String TABLEKEY_TOPIC = "Topic";
    private static final String TABLEKEY_QUESTION = "Question";
    private static final String TABLEKEY_CORRECTANSWER = "CorrectAnswer";
    private static final String TABLEKEY_INCORRECTANSWER = "IncorrectAnswer";
    private static final String TABLEKEY_ANSWERVALUE = "AnswerValue";
    private static final String TABLEKEY_TYPE = "Type";
    private static final String TABLEKEY_TAG = "Tag";
    private static final String TABLEKEY_TOPICTAG = "TopicTag";
    private static final String TABLEKEY_REHEARSAL = "Rehearsal";
    private static final String TABLEKEY_REHEARSALINFO = "RehearsalInfo";


    /**
     * Table columns
     */
    private static final String KEY_USERNAME = "Username";
    private static final String KEY_PASSWORD = "Password";
    private static final String KEY_TOPICID = "TopicID";
    private static final String KEY_TOPICNAME = "TopicName";
    private static final String KEY_PARENT_TOPIC_ID = "ParentTopicID";
    private static final String KEY_QUESTIONID = "QuestionID";
    private static final String KEY_QUESTION = "Question";
    private static final String KEY_ANSWER = "Answer";
    private static final String KEY_TYPENAME = "TypeName";
    private static final String KEY_TAGNAME = "TagName";
    private static final String KEY_TOPIC = "Topic";
    private static final String KEY_USERID = "UserID";
    private static final String KEY_SUMMARY = "Summary";
    private static final String KEY_KEYVALUE = "KeyValue";
    private static final String KEY_REHEARSALID = "RehearsalID";
    private static final String KEY_SAVEDONSERVER = "SavedOnServer";
    private static final String KEY_LABEL = "Label";
    private static final String KEY_MINIMALCORRECT = "MinimalCorrect";
    private static final String KEY_ENDDATE = "EndDate";


    /**
     * Database create sql script
     */
    private static final String DATABASE_CREATE_SQL_ACCOUNT =
            /// create tables + foreignkeys
            "CREATE TABLE IF NOT EXISTS [" + TABLEKEY_ACCOUNT + "]"
                    + " ([" + KEY_USERNAME + "] TEXT PRIMARY KEY, "
                    + "[" + KEY_PASSWORD + "] TEXT NOT NULL,"
                    + "[" + KEY_USERID + "] TEXT NOT NULL"
                    + "); ";

    private static final String DATABASE_CREATE_SQL_TOPIC =
            "CREATE TABLE IF NOT EXISTS [" + TABLEKEY_TOPIC + "]"
                    + " ([" + KEY_TOPICID + "] TEXT PRIMARY KEY, "
                    + "[" + KEY_TOPICNAME + "] TEXT NOT NULL, "
                    + "[" + KEY_PARENT_TOPIC_ID + "] TEXT,"
                    + "[" + KEY_TOPIC + "]TEXT NOT NULL,"
                    + "[" + KEY_SUMMARY + "]INTEGER NOT NULL,"
                    + "[" + KEY_USERID + "]INTEGER NOT NULL,"
                    + "FOREIGN KEY (" + KEY_PARENT_TOPIC_ID + ") REFERENCES " + TABLEKEY_TOPIC + "(" + KEY_TOPICID + "),"
                    + "FOREIGN KEY (" + KEY_USERID + ") REFERENCES " + TABLEKEY_ACCOUNT + "(" + KEY_USERID + ")"
                    + "); ";

    private static final String DATABASE_CREATE_SQL_TYPE =
            "CREATE TABLE IF NOT EXISTS [" + TABLEKEY_TYPE + "]"
                    + " ([" + KEY_TYPENAME + "] TEXT PRIMARY KEY "
                    + "); ";


    private static final String DATABASE_CREATE_SQL_REHEARSAL =
            "CREATE TABLE IF NOT EXISTS [" + TABLEKEY_REHEARSAL + "]"
                    + "([" + KEY_TOPICID + "] TEXT,"
                    + "[" + KEY_QUESTIONID + "] TEXT,"
                    + "[" + KEY_REHEARSALID + "] TEXT,"
                    + "PRIMARY KEY (" + KEY_TOPICID + ", " + KEY_QUESTIONID + ", " + KEY_REHEARSALID + "),"
                    + "FOREIGN KEY (" + KEY_TOPICID + ") REFERENCES " + TABLEKEY_TOPIC + "(" + KEY_TOPICID + "),"
                    + "FOREIGN KEY (" + KEY_QUESTIONID + ") REFERENCES " + TABLEKEY_QUESTION + "(" + KEY_QUESTIONID + ")"
                    + "); ";


    private static final String DATABASE_CREATE_SQL_REHEARSALINFO =
            "CREATE TABLE IF NOT EXISTS [" + TABLEKEY_REHEARSALINFO + "]"
                    + "([" + KEY_REHEARSALID + "] TEXT PRIMARY KEY,"
                    + "[" + KEY_LABEL + "] TEXT,"
                    + "[" + KEY_ENDDATE + "] TEXT,"
                    + "[" + KEY_MINIMALCORRECT + "] TEXT,"
                    + "FOREIGN KEY (" + KEY_REHEARSALID + ") REFERENCES " + TABLEKEY_REHEARSAL + "(" + KEY_REHEARSALID + ")"
                    + "); ";

    private static final String DATABASE_CREATE_SQL_QUESTION =
            "CREATE TABLE IF NOT EXISTS [" + TABLEKEY_QUESTION + "]"
                    + " ([" + KEY_QUESTIONID + "] TEXT PRIMARY KEY, "
                    + "[" + KEY_QUESTION + "] TEXT NOT NULL, "
                    + "[" + KEY_TOPICID + "] TEXT NOT NULL, "
                    + "[" + KEY_TYPENAME + "] TEXT NOT NULL, "
                    + "FOREIGN KEY (" + KEY_TOPICID + ") REFERENCES " + TABLEKEY_TOPIC + "(" + KEY_TOPICID + "),"
                    + "FOREIGN KEY (" + KEY_TYPENAME + ") REFERENCES " + TABLEKEY_TYPE + "(" + KEY_TYPENAME + ")"
                    + "); ";

    private static final String DATABASE_CREATE_SQL_CORRECTANSWER =
            "CREATE TABLE IF NOT EXISTS[" + TABLEKEY_CORRECTANSWER + "]"
                    + " ([" + KEY_ANSWER + "] TEXT, "
                    + "[" + KEY_QUESTIONID + "] TEXT,"
                    + "PRIMARY KEY (" + KEY_ANSWER + "," + KEY_QUESTIONID + "),"
                    + "FOREIGN KEY (" + KEY_QUESTIONID + ") REFERENCES " + TABLEKEY_QUESTION + "(" + KEY_QUESTIONID + ")"
                    + "); ";


    private static final String DATABASE_CREATE_SQL_ANSWERVALUE =
            "CREATE TABLE IF NOT EXISTS [" + TABLEKEY_ANSWERVALUE + "]"
                    + " ([" + KEY_ANSWER + "] TEXT, "
                    + "[" + KEY_QUESTIONID + "] TEXT,"
                    + "[" + KEY_KEYVALUE + "] INTEGER NULL,"
                    + "PRIMARY KEY (" + KEY_ANSWER + "," + KEY_QUESTIONID + ")"
                    + "FOREIGN KEY (" + KEY_QUESTIONID + ") REFERENCES " + TABLEKEY_REHEARSAL + "(" + KEY_QUESTIONID + ")"
                    + "); ";

    private static final String DATABASE_CREATE_SQL_INCORRECTANSWER =
            "CREATE TABLE IF NOT EXISTS [" + TABLEKEY_INCORRECTANSWER + "]"
                    + " ([" + KEY_ANSWER + "] TEXT, "
                    + "[" + KEY_QUESTIONID + "] TEXT,"
                    + "PRIMARY KEY (" + KEY_ANSWER + "," + KEY_QUESTIONID + ")"
                    + "FOREIGN KEY (" + KEY_QUESTIONID + ") REFERENCES " + TABLEKEY_QUESTION + "(" + KEY_QUESTIONID + ")"
                    + "); ";

    private static final String DATABASE_CREATE_SQL_TAG =
            "CREATE TABLE IF NOT EXISTS [" + TABLEKEY_TAG + "]"
                    + " ([" + KEY_TAGNAME + "] TEXT, "
                    + " [" + KEY_TOPICID + "] TEXT, "
                    + "PRIMARY KEY (" + KEY_TAGNAME + "," + KEY_TOPICID + ")"
                    + "); ";

    private static final String DATABASE_CREATE_SQL_TOPICTAG =
            "CREATE TABLE IF NOT EXISTS [" + TABLEKEY_TOPICTAG + "]"
                    + " ([" + KEY_TOPICID + "] TEXT, "
                    + "[" + KEY_TAGNAME + "] TEXT, "
                    + "PRIMARY KEY (" + KEY_TOPICID + "," + KEY_TAGNAME + ")"
                    + "FOREIGN KEY (" + KEY_TAGNAME + ") REFERENCES " + TABLEKEY_TAG + "(" + KEY_TAGNAME + "),"
                    + "FOREIGN KEY (" + KEY_TOPICID + ") REFERENCES " + TABLEKEY_TOPIC + "(" + KEY_TOPICID + ")"
                    + "); ";

    /**
     * Database version
     */
    public static final int DATABASE_VERSION = 3;

    /**
     * Stores the DatabaseHelper instance.
     */
    private DatabaseHelper databaseHelper;

    /**
     * Stores the SQLiteDatabase instance.
     */
    private SQLiteDatabase db;

    /**
     * Stores the currently logged in user ID.
     */
    private int userID = UserAPI.getUserInfo().getUserID();


    /**
     * Constructor to create a database.
     *
     * @param context The context, when in activity set "this" as context.
     */
    public DBHelper(Context context) {
        databaseHelper = new DatabaseHelper(context, "Summarized.IODatabase");
        open();


    }

    /**
     * Open Database connection
     *
     * @return DBAdapter
     */
    public DBHelper open() {
        db = databaseHelper.getWritableDatabase();
        return this;
    }

    /**
     * Drops all tables in the database, All tables will have to be created again!
     */
    public void dropDatabaseTables() {
        databaseHelper.dropDatabase(db);
    }

    /**
     * Close the database connection.
     */
    public void close() {
        databaseHelper.close();
    }

    /**
     * Inserts a row in Account
     *
     * @param username username
     * @param password password
     * @param userID   userid
     * @return long, if -1l the sql statement has failed
     */
    public long insertRowAccount(String username, String password, String userID) {
        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_USERNAME, username);
        initialValues.put(KEY_PASSWORD, password);
        initialValues.put(KEY_USERID, userID);
        // Insert it into the database.
        return db.insert(TABLEKEY_ACCOUNT, null, initialValues);
    }

    /**
     * Inserts a row in the Rehearsal table.
     *
     * @param rehearsalID   The rehearsal object ID.
     * @param topicID       The topic object ID
     * @param questionID    The question object ID.
     * @param savedOnServer 0 if the row is not on the server, 1 if the row is on the server.
     * @return long, if -1l the sql statement has failed
     */
    public long insertRowRehearsal(String rehearsalID, String topicID, String questionID, int savedOnServer) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_REHEARSALID, rehearsalID);
        initialValues.put(KEY_TOPICID, topicID);
        initialValues.put(KEY_QUESTIONID, questionID);
        initialValues.put(KEY_SAVEDONSERVER, savedOnServer);
        // Insert it into the database.
        return db.insert(TABLEKEY_REHEARSAL, null, initialValues);
    }

    /**
     * Inserts a row in the Topic table.
     *
     * @param topicName     name of the topic.
     * @param parentTopicID id of the (optional) parent topic.
     * @param topic         topic, description of the subject, holds the knowledge.
     * @return long, if -1l the sql statement has failed.
     */
    public long insertRowTopic(String topicName, String parentTopicID, String topic, String topicID, int summary) {
        if (summary != 0 && parentTopicID != null) {
            throw new NoSummaryInsertException("No summaryID inserted");
        }
        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TOPICNAME, topicName);
        initialValues.put(KEY_PARENT_TOPIC_ID, parentTopicID);
        initialValues.put(KEY_TOPIC, topic);
        initialValues.put(KEY_TOPICID, topicID);
        initialValues.put(KEY_SUMMARY, summary);
        initialValues.put(KEY_USERID, userID);
        // Insert it into the database.
        return db.insert(TABLEKEY_TOPIC, null, initialValues);
    }

    /**
     * Inserts a row in the QuestionTable
     *
     * @param question   The question string
     * @param topicID    Id of the connected topic
     * @param typeName   The question type.
     * @param questionID questiondi
     * @return long, if -1l the sql statement has failed
     */
    public long insertRowQuestion(String question, String topicID, String typeName, String questionID) {
        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_QUESTION, question);
        initialValues.put(KEY_TOPICID, topicID);
        initialValues.put(KEY_TYPENAME, typeName);
        initialValues.put(KEY_QUESTIONID, questionID);
        // Insert it into the database.
        return db.insert(TABLEKEY_QUESTION, null, initialValues);
    }

    /**
     * Inserts a row in the IncorrectAnswer table
     *
     * @param answer     The answer (string)
     * @param questionid Questionid of the connected question
     * @return long, if -1l the sql statement has failed
     */
    public long insertRowIncorrectAnswer(String answer, String questionid) {
        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_ANSWER, answer);
        initialValues.put(KEY_QUESTIONID, questionid);
        // Insert it into the database.
        return db.insert(TABLEKEY_INCORRECTANSWER, null, initialValues);
    }

    /**
     * Inserts a row in the AnswerValue table
     *
     * @param answer     The answer (string)
     * @param questionid Questionid of the connected question
     * @return long, if -1l the sql statement has failed
     */
    public long insertRowAnswerValue(String answer, String questionid) {
        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_QUESTION, answer);
        initialValues.put(KEY_QUESTIONID, questionid);
        // Insert it into the database.
        return db.insert(TABLEKEY_ANSWERVALUE, null, initialValues);
    }

    /**
     * Inserts a row in the CorrectAnswert table
     *
     * @param answer     the answer
     * @param questionid QuestionID of the connected question
     * @return long, if -1l the sql statement has failed
     */
    public long insertRowCorrectAnswer(String answer, String questionid) {
        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_QUESTIONID, questionid);
        initialValues.put(KEY_ANSWER, answer);
        // Insert it into the database.
        return db.insert(TABLEKEY_CORRECTANSWER, null, initialValues);
    }

    /**
     * Inserts a row in the Type table
     *
     * @param typeName Name of the questionType
     * @return long, if -1l the sql statement has failed
     */
    public long insertRowType(String typeName) {
        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TYPENAME, typeName);
        // Insert it into the database.
        return db.insert(TABLEKEY_TYPE, null, initialValues);
    }


    /**
     * Inserts a row to the TopicTag table
     *
     * @param tagName Name of the tag connected to a Topic
     * @param topicid Topicid of the summary connected to a Tagname
     * @return long, if -1l the sql statement has failed
     */
    public long insertRowTopicTag(String tagName, String topicid) {
        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TAGNAME, tagName);
        initialValues.put(KEY_TOPICID, topicid);
        // Insert it into the database.
        return db.insert(TABLEKEY_TOPICTAG, null, initialValues);
    }


    /**
     * Inserts a row in the Tag table
     *
     * @param tagName Name of the tag
     * @return long, if -1l the sql statement has failed
     */
    public long insertRowTag(String tagName) {
        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TAGNAME, tagName);
        // Insert it into the database.
        return db.insert(TABLEKEY_TAG, null, initialValues);
    }

    //todo
    public boolean deleteRowRehearsalTopicID(String topicid) {
        String where = KEY_TOPICID + "= '" + topicid + "'";
        return db.delete(TABLEKEY_REHEARSAL, where, null) != 0;
    }


    /**
     * Deletes a row from the topic table
     *
     * @param topicID the topicid of the row which will be deleted
     * @return boolean, true if succeeded
     */
    public boolean deleteRowTopicTopicID(String topicID) {
        String where = KEY_TOPICID + "= '" + topicID + "'";
        return db.delete(TABLEKEY_TOPIC, where, null) != 0;
    }

    /**
     * Deletes a row from the Question table
     *
     * @param topicID the Questionid of the row which will be deleted
     * @return boolean, true if succeeded
     */
    public boolean deleteQuestionTopicID(String topicID) {
        String where = KEY_TOPICID + "= '" + topicID + "'";
        deleteAnswers(topicID);
        return db.delete(TABLEKEY_QUESTION, where, null) != 0;
    }

    //todo
    private void deleteAnswers(String topicID) {
        Cursor query1 = db.query(true, TABLEKEY_QUESTION, null, KEY_TOPICID + "= '" + topicID + "'", null, null, null, null, null);
        if (query1.getCount() != 0) {
            do {
                deleteCorrectAnswers(query1);
                deleteIncorrectAnswers(query1);
            } while (query1.moveToNext());
        }
    }

    //todo
    private void deleteCorrectAnswers(Cursor query1) {
        Cursor query2 = db.query(true, TABLEKEY_CORRECTANSWER, null, KEY_QUESTIONID + "= '" + query1.getString(query1.getColumnIndex(KEY_QUESTIONID)) + "'", null, null, null, null, null);
        if (query2.getCount() != 0) {
            do {
                db.delete(TABLEKEY_CORRECTANSWER, KEY_QUESTIONID + "=" + query2.getString(query2.getColumnIndex(KEY_QUESTIONID)), null);
            } while (query2.moveToNext());
        }
    }

    //todo
    private void deleteIncorrectAnswers(Cursor query1) {
        Cursor query3 = db.query(true, TABLEKEY_INCORRECTANSWER, null, KEY_QUESTIONID + "= '" + query1.getString(query1.getColumnIndex(KEY_QUESTIONID)) + "'", null, null, null, null, null);
        if (query3.getCount() != 0) {
            do {
                db.delete(TABLEKEY_CORRECTANSWER, KEY_QUESTIONID + "=" + query3.getString(query3.getColumnIndex(KEY_QUESTIONID)), null);
            } while (query3.moveToNext());
        }
    }

    /**
     * sets array with incorrect answers in questionElement
     *
     * @param questionElement the questionelement that has to be filled
     * @param query           query
     */
    private void setIncorrectAnswer(QuestionWrapper questionElement, Cursor query) {
        List<Answers> incorrectAnswerList = new ArrayList<>();
        do {
            final Answers incorrectAnswerElement = new Answers();
            Response[] response = new Response[query.getCount()];
            if (query.getCount() != 0) {
                int i = 0;
                createResponse(query, response, i);
                incorrectAnswerElement.setResponse(response);
                incorrectAnswerList.add(incorrectAnswerElement);
            }
        } while (query.moveToNext());
        Answers[] incorrectAnswerElements = new Answers[incorrectAnswerList.size()];
        incorrectAnswerList.toArray(incorrectAnswerElements);
        questionElement.setIncorrectAnswers(incorrectAnswerElements);
    }

    //todo
    private void createResponse(Cursor query, Response[] response, int i) {
        do {
            Response response1 = new Response();
            response1.setOption(query.getString(query.getColumnIndex(KEY_ANSWER)));
            response[i] = response1;
            i++;
        } while (query.moveToNext());
    }


    /**
     * Returns rows with all columns selected with the where clause
     *
     * @param questionID What QuestionID will be returned
     * @return returns questionelement
     */
    public QuestionWrapper getRowQuestion(Integer questionID) {
        Cursor queryQuestion = db.query(true, TABLEKEY_QUESTION, null, KEY_QUESTIONID + "= '" + questionID + "'", null, null, null, null, null);
        if (queryQuestion != null) {
            queryQuestion.moveToFirst();
            QuestionWrapper questionElement = new QuestionWrapper();
            setIDQuestion(queryQuestion, questionElement);
            setQuestionStatement(queryQuestion, questionElement);
            setTopicIDQuestion(queryQuestion, questionElement);
            Cursor queryCorrectAnswer = db.query(true, TABLEKEY_CORRECTANSWER, null, KEY_QUESTIONID + "= '" + queryQuestion.getString(queryQuestion.getColumnIndex(KEY_QUESTIONID)) + "'", null, null, null, null, null);
            setCorrectAnswer(questionElement, queryCorrectAnswer);
            Cursor queryIncorrectAnswer = db.query(true, TABLEKEY_INCORRECTANSWER, null, KEY_QUESTIONID + "= '" + queryQuestion.getString(queryQuestion.getColumnIndex(KEY_QUESTIONID)) + "'", null, null, null, null, null);
            setIncorrectAnswer(questionElement, queryIncorrectAnswer);
            return questionElement;
        } else return null;

    }

    /**
     * checks if typename exists
     *
     * @param typename name of the type
     * @return true if exists
     */
    public boolean checkTypeName(String typename) {
        Cursor queryType = db.query(true, TABLEKEY_QUESTION, null, KEY_TYPENAME + "= '" + typename + "'", null, null, null, null, null);
        return queryType != null;
    }

    //todo
    public boolean checkTagName(String tagname) {
        Cursor queryTag = db.query(true, TABLEKEY_TAG, null, KEY_TYPENAME + "= '" + tagname + "'", null, null, null, null, null);
        return queryTag != null;
    }

    /**
     * sets topicID in questionElement
     *
     * @param query           query
     * @param questionElement the questionelement that has to be filled
     */
    private void setTopicIDQuestion(Cursor query, QuestionWrapper questionElement) {
        final TopicId topicId = new TopicId();
        topicId.set$oid(query.getString(query.getColumnIndex(KEY_TOPICID)));
        questionElement.setQuestionType(query.getString(query.getColumnIndex(KEY_TYPENAME)));
    }

    /**
     * sets statement in questionelement
     *
     * @param query           query
     * @param questionElement the questionelement that has to be filled
     */
    private void setQuestionStatement(Cursor query, QuestionWrapper questionElement) {
        final Statement statement = new Statement();
        final Text text = new Text();
        text.setValue(query.getString(query.getColumnIndex(KEY_QUESTION)));
        statement.setText(text);
        questionElement.setStatement(statement);
    }

    //todo
    public Cursor getNotSentToServerRehearsalsCursor() {
        String[] columns = {KEY_REHEARSALID};
        Cursor query = db.query(true, TABLEKEY_REHEARSAL, columns, KEY_SAVEDONSERVER + "=" + 0, null, null, null, null, null);
        if (query.getCount() != 0) {
            return query;
        }
        return null;
    }

    //todo
    public List<Rehearsal> checkNotSentToServerRehearsals() {
        Cursor query = getNotSentToServerRehearsalsCursor();
        List<Rehearsal> rehearsalList = new ArrayList<>();
        do {
            Cursor query2 = db.query(true, TABLEKEY_REHEARSAL, null, KEY_SAVEDONSERVER + "=" + 0 + " AND " + KEY_REHEARSALID + "=" + query.getString(query.getColumnIndex(KEY_REHEARSALID)), null, null, null, null, null);
            if (query2.getCount() != 0) {
                Rehearsal rehearsal = new Rehearsal();
                rehearsalList.add(setRehearsal(query.getString(query.getColumnIndex(KEY_REHEARSALID)), query2, rehearsal));
            }
        } while (query.moveToNext());
        return rehearsalList;
    }

    //todo
    public List<Rehearsal> getOfflineOnlyRehearsals(Topic topic) {
        List<Rehearsal> rehearsalList = new ArrayList<>();
        Cursor query2 = db.query(true, TABLEKEY_REHEARSAL, null, KEY_TOPICID + "=" + topic.get_id().get$oid(), null, null, null, null, null);
        if (query2.getCount() != 0) {
            do {
                Rehearsal rehearsal = new Rehearsal();

                rehearsalList.add(setRehearsal(query2.getString(query2.getColumnIndex(KEY_REHEARSALID)), query2, rehearsal));
            } while (query2.moveToNext());
        }
        return rehearsalList;
    }

    //todo
    public List<Rehearsal> getNotSentToServerRehearsals() {
        Cursor query = getNotSentToServerRehearsalsCursor();
        List<Rehearsal> rehearsalList = new ArrayList<>();
        do {
            Cursor query2 = db.query(true, TABLEKEY_REHEARSAL, null, KEY_REHEARSALID + "=" + query.getString(query.getColumnIndex(KEY_REHEARSALID)), null, null, null, null, null);
            if (query2.getCount() != 0) {
                Rehearsal rehearsal = new Rehearsal();
                rehearsalList.add(setRehearsal(query.getString(query.getColumnIndex(KEY_REHEARSALID)), query2, rehearsal));
            }
        } while (query.moveToNext());
        return rehearsalList;
    }

    //todo
    private Rehearsal setRehearsal(String rehearsalID, Cursor query2, Rehearsal rehearsal) {
        List<String> questionIDs = new ArrayList<>();
        List<Item> corrects = new ArrayList<>();
        List<Item> incorrects = new ArrayList<>();
        do {
            questionIDs.add(query2.getString(query2.getColumnIndex(KEY_QUESTIONID)));
            QuestionWrapper ele = getQuestionElement(query2);
            Cursor query3 = db.query(true, TABLEKEY_ANSWERVALUE, null, KEY_QUESTIONID + "=" + ele.get_id().get$oid() + " AND " + KEY_REHEARSALID + "=" + rehearsalID, null, null, null, null, null);
            do {
                addToCorrectOrIncorrect(corrects, incorrects, ele, query3);
            } while (query3.moveToNext());
        } while (query2.moveToNext());
        insertRehearsalRequirments(rehearsal);
        String[] questionid = new String[questionIDs.size()];
        questionIDs.toArray(questionid);
        Item[] correct = new Item[corrects.size()];
        corrects.toArray(correct);
        Item[] incorrect = new Item[incorrects.size()];
        incorrects.toArray(incorrect);
        rehearsal.setCorrects(correct);
        rehearsal.setIncorrects(incorrect);
        return rehearsal;
    }

    //todo
    private void insertRehearsalRequirments(Rehearsal rehearsal) {
        insertRowRehearsalInfo(rehearsal.getLabel(), rehearsal.getEndDate(), rehearsal.getMinCorrect());
    }

    //todo
    private Long insertRowRehearsalInfo(String label, long endDate, int minCorrect) {
        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_LABEL, label);
        initialValues.put(KEY_ENDDATE, endDate);
        initialValues.put(KEY_MINIMALCORRECT, minCorrect);
        // Insert it into the database.
        return db.insert(TABLEKEY_TOPICTAG, null, initialValues);
    }

    //todo
    private void addToCorrectOrIncorrect(List<Item> corrects, List<Item> incorrects, QuestionWrapper ele, Cursor query3) {
        Item item = new Item();
        item.setQuestion(ele);
        if (getRehearsalAnswerCorrect(query3, ele.get_id())) {
            corrects.add(item);
        } else {
            incorrects.add(item);
        }
    }

    //todo
    private boolean getRehearsalAnswerCorrect(Cursor query3, _id id) {
        if (query3.getCount() != 0) {
            do {
                Cursor query = db.query(true, TABLEKEY_CORRECTANSWER, null, KEY_QUESTIONID + "=" + id.get$oid(), null, null, null, null, null);
                do {
                    if (query3.getString(query3.getColumnIndex(KEY_QUESTIONID)) == query.getString(query.getColumnIndex(KEY_ANSWER))) {
                        return true;
                    }
                } while (query.moveToNext());
            } while (query3.moveToFirst());
        }
        return false;
    }

    /**
     * Returns rows with all columns selected with the where clause
     *
     * @param topicID What QuestionID will be returned
     * @return returns questionelement
     */
    public List<QuestionWrapper> getRowsQuestionTopicID(String topicID) {
        Cursor queryQuestion = db.query(true, TABLEKEY_QUESTION, null, KEY_TOPICID + "=" + topicID, null, null, null, null, null);
        ArrayList<QuestionWrapper> arrayList = new ArrayList<>();
        if (queryQuestion != null) {
            queryQuestion.moveToFirst();
            do {
                QuestionWrapper questionElement = new QuestionWrapper();
                setIDQuestion(queryQuestion, questionElement);
                setQuestionStatement(queryQuestion, questionElement);
                setTopicIDQuestion(queryQuestion, questionElement);
                Cursor queryCorrectAnswer = db.query(true, TABLEKEY_CORRECTANSWER, null, KEY_QUESTIONID + "=" + queryQuestion.getString(queryQuestion.getColumnIndex(KEY_QUESTIONID)), null, null, null, null, null);
                setCorrectAnswer(questionElement, queryCorrectAnswer);
                Cursor queryIncorrectAnswer = db.query(true, TABLEKEY_INCORRECTANSWER, null, KEY_QUESTIONID + "=" + queryQuestion.getString(queryQuestion.getColumnIndex(KEY_QUESTIONID)), null, null, null, null, null);
                setIncorrectAnswer(questionElement, queryIncorrectAnswer);
                arrayList.add(questionElement);
            } while (queryQuestion.moveToNext());
        }
        return arrayList;
    }

    //todo
    private QuestionWrapper getQuestionElement(Cursor queryQuestion) {
        QuestionWrapper questionElement = new QuestionWrapper();
        setIDQuestion(queryQuestion, questionElement);
        setQuestionStatement(queryQuestion, questionElement);
        setTopicIDQuestion(queryQuestion, questionElement);
        setCorrectAnswer(queryQuestion, questionElement);
        setIncorrectAnswer(queryQuestion, questionElement);
        return questionElement;
    }

    //todo
    private void setIncorrectAnswer(Cursor queryQuestion, QuestionWrapper questionElement) {
        Cursor queryIncorrectAnswer = db.query(true, TABLEKEY_INCORRECTANSWER, null, KEY_QUESTIONID + "=" + queryQuestion.getString(queryQuestion.getColumnIndex(KEY_QUESTIONID)), null, null, null, null, null);
        if (queryIncorrectAnswer.getCount() != 0) {
            queryIncorrectAnswer.moveToFirst();
            setIncorrectAnswer(questionElement, queryIncorrectAnswer);
        }
    }

    //todo
    private void setCorrectAnswer(Cursor queryQuestion, QuestionWrapper questionElement) {
        Cursor queryCorrectAnswer = db.query(true, TABLEKEY_CORRECTANSWER, null, KEY_QUESTIONID + "=" + queryQuestion.getString(queryQuestion.getColumnIndex(KEY_QUESTIONID)), null, null, null, null, null);
        if (queryCorrectAnswer.getCount() != 0) {
            queryCorrectAnswer.moveToFirst();
            setCorrectAnswer(questionElement, queryCorrectAnswer);
        }
    }


    /**
     * sets ID in QuestionElement
     *
     * @param query           query
     * @param questionElement the questionelement that has to be filled
     */
    private void setIDQuestion(Cursor query, QuestionWrapper questionElement) {
        final _id id = new _id();
        id.set$oid(query.getString(query.getColumnIndex(KEY_QUESTIONID)));
        questionElement.set_id(id);
    }

    /**
     * Sets CorrectAnswerarray in QuestionElement
     *
     * @param questionElement the questionelement that has to be filled
     * @param query           query
     */
    private void setCorrectAnswer(QuestionWrapper questionElement, Cursor query) {
        Response[] response = new Response[query.getCount()];
        do {
            if (query.getCount() != 0) {
                int i = 0;
                createResponse(query, response, i);
            }
        } while (query.moveToNext());
        Answers correctAnswerElements = new Answers();
        correctAnswerElements.setResponse(response);
        questionElement.setCorrectAnswers(correctAnswerElements);
    }


    /**
     * Sets title in topicElement
     *
     * @param queryTopic   query
     * @param topicElement topicElement
     */
    private void setTitleTopic(Cursor queryTopic, ica.han.oose.project.overhoorapp.json.models.summaries.read.TopicId topicElement) {
        if (queryTopic.getCount() != 0) {
            queryTopic.moveToFirst();
            topicElement.setTitle(queryTopic.getString(queryTopic.getColumnIndex(KEY_TOPICNAME)));
        }
    }

    /**
     * gets array with tagElements
     *
     * @param queryTopic querytopic
     * @param queryTag   querytag
     * @return returns array with TagElements
     */
    private TagElement[] setTagElementTopic(Cursor queryTopic, Cursor queryTag) {
        List<TagElement> tagElementList = new ArrayList<>();
        do {
            final TagElement tagElement = new TagElement();
            tagElement.setText(queryTopic.getString(queryTopic.getColumnIndex(KEY_TAGNAME)));
            tagElementList.add(tagElement);
        } while (queryTag.moveToNext());
        TagElement[] tagElement = new TagElement[tagElementList.size()];
        tagElementList.toArray(tagElement);
        return tagElement;
    }


    /**
     * Returns rows with all columns selected with the where clause
     *
     * @param ID What QuestionID will be returned
     * @return returns TopicElement
     */
    public ica.han.oose.project.overhoorapp.json.wrappers.TopicId getRowTopic(String ID) {

        Cursor queryTopic = db.query(true, TABLEKEY_TOPIC, null,
                KEY_TOPICID + "= '" + ID + "'", null, null, null, null, null);
        if (queryTopic.getCount() != 0) {
            queryTopic.moveToFirst();
            ica.han.oose.project.overhoorapp.json.models.summaries.read.TopicId topicElement = new ica.han.oose.project.overhoorapp.json.models.summaries.read.TopicId();
            setIDTopic(queryTopic, topicElement);
            setTitleTopic(queryTopic, topicElement);
            Cursor queryTag = db.query(true, TABLEKEY_TOPICTAG, null,
                    KEY_QUESTIONID + "=" + queryTopic.getString(queryTopic.getColumnIndex(KEY_TOPICID)), null, null, null, null, null);
            topicElement.setTags(setTagElementTopic(queryTopic, queryTag));
            return new ica.han.oose.project.overhoorapp.json.wrappers.TopicId(topicElement);
        } else return null;

    }

    /**
     * Returns rows with all columns selected with the summaryid
     *
     * @param id What Summaryid the topics will have
     * @return returns TopicElement
     */
    public List<ica.han.oose.project.overhoorapp.json.wrappers.TopicId> getRowTopicSummaryID(String id) {
        Cursor queryTopic = db.query(true, TABLEKEY_TOPIC, null,
                KEY_PARENT_TOPIC_ID + "=" + id, null, null, null, null, null);
        List<ica.han.oose.project.overhoorapp.json.wrappers.TopicId> arrayList = new ArrayList<>();
        if (queryTopic != null) {
            queryTopic.moveToFirst();
            do {
                ica.han.oose.project.overhoorapp.json.models.summaries.read.TopicId topicElement = new ica.han.oose.project.overhoorapp.json.models.summaries.read.TopicId();
                setIDTopic(queryTopic, topicElement);
                setTitleTopic(queryTopic, topicElement);
                Cursor queryTag = db.query(true, TABLEKEY_TOPICTAG, null,
                        KEY_TOPICID + "=" + queryTopic.getString(queryTopic.getColumnIndex(KEY_TOPICID)), null, null, null, null, null);
                topicElement.setTags(setTagElementTopic(queryTopic, queryTag));
                arrayList.add(new ica.han.oose.project.overhoorapp.json.wrappers.TopicId(topicElement));
            } while (queryTopic.moveToNext());
        }
        return arrayList;
    }


    //todo
    private void setIDTopic(Cursor queryTopic, ica.han.oose.project.overhoorapp.json.models.summaries.read.TopicId topicElement) {
        if (queryTopic.getCount() != 0) {
            queryTopic.moveToNext();
            final _id id = new _id();
            id.set$oid(queryTopic.getString(queryTopic.getColumnIndex(KEY_TOPICID)));
            topicElement.set_id(id);
        }
    }

    //todo
    public List<QuestionAnswer> getAnswerQuestion() {
        List<QuestionAnswer> questionAnswerList = new ArrayList<>();
        Cursor query = db.query(true, TABLEKEY_ANSWERVALUE, null,
                null, null, null, null, null, null);
        if (query.getCount() != 0) {
            do {
                QuestionAnswer questionAnswer = new QuestionAnswer();
                questionAnswer = setQuestionAnswer(query, questionAnswer);
                questionAnswerList.add(questionAnswer);
            } while (query.moveToNext());

            return questionAnswerList;
        }
        return null;
    }

    //todo
    private QuestionAnswer setQuestionAnswer(Cursor query, QuestionAnswer questionAnswer) {
        Answers answers = new Answers();
        Cursor query2 = db.query(true, TABLEKEY_ANSWERVALUE, null,
                KEY_QUESTIONID + "=" + query.getString(query.getColumnIndex(KEY_QUESTIONID)), null, null, null, null, null);
        Response[] response = new Response[query2.getCount()];
        answers.setResponse(response);
        questionAnswer.setAnswers(answers);
        return questionAnswer;
    }

    /**
     * Returns all rows with all columns from the selected table
     *
     * @return returns cursor with the table
     */
    public List<Summary> getAllRowsSummary() {
        Cursor querySummary = db.query(true, TABLEKEY_TOPIC, null,
                KEY_SUMMARY + "=" + 1 + " AND " + KEY_USERID + "=" + userID, null, null, null, null, null);
        ArrayList arrayList = new ArrayList();
        if (querySummary != null) {
            querySummary.moveToFirst();
            do {
                if (querySummary.getCount() != 0) {
                    SummaryElement summaryElement = new SummaryElement();
                    setIDSummary(querySummary, summaryElement);
                    summaryElement.setTitle(querySummary.getString(querySummary.getColumnIndex(KEY_TOPICNAME)));
                    Cursor tag = db.query(true, TABLEKEY_TOPICTAG, null,
                            KEY_TOPICID + "=" + querySummary.getString(querySummary.getColumnIndex(KEY_TOPICID)), null, null, null, null, null);
                    setTagSummaryElement(summaryElement, tag);
                    arrayList.add(summaryElement);
                }
            } while (querySummary.moveToNext());
        }
        return getSummaryFromSummaryElement(arrayList);
    }

    //todo
    private void setTagSummaryElement(SummaryElement summaryElement, Cursor tag) {
        List<TagElement> tagElementList = new ArrayList<>();
        do {
            final TagElement tagElement = new TagElement();
            tagElement.setText(tag.getString(tag.getColumnIndex(KEY_TAGNAME)));
            tagElementList.add(tagElement);
        } while (tag.moveToNext());
        TagElement[] tagElement = new TagElement[tagElementList.size()];
        tagElementList.toArray(tagElement);
        summaryElement.setTags(tagElement);
    }

    //todo
    private void setIDSummary(Cursor querySummary, SummaryElement summaryElement) {
        final _id id = new _id();
        if (querySummary.getCount() != 0) {
            querySummary.moveToFirst();
            id.set$oid(querySummary.getString(querySummary.getColumnIndex(KEY_TOPICID)));
            summaryElement.set_id(id);
        }
    }


    /**
     * Creates a topic with a topicelement
     *
     * @param arrayList arraylist with topicelements
     * @return array with topics
     */
    private List<Summary> getSummaryFromSummaryElement(ArrayList arrayList) {
        List<Summary> summaryList = new ArrayList();
        for (int i = 0; i < arrayList.size(); i++) {
            Summary summary = new Summary((SummaryElement) arrayList.get(i));
            summaryList.add(summary);
        }
        return summaryList;
    }


    /////////////////////////////////////////////////////////////////////
    //	Private Helper Classes:
    /////////////////////////////////////////////////////////////////////

    /**
     * Private class which handles database creation and upgrading.
     * Used to handle low-level database access.
     */
    public class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context, String databaseName) {
            super(context, databaseName, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(DATABASE_CREATE_SQL_ACCOUNT);
            _db.execSQL(DATABASE_CREATE_SQL_TOPIC);
            _db.execSQL(DATABASE_CREATE_SQL_QUESTION);
            _db.execSQL(DATABASE_CREATE_SQL_ANSWERVALUE);
            _db.execSQL(DATABASE_CREATE_SQL_INCORRECTANSWER);
            _db.execSQL(DATABASE_CREATE_SQL_CORRECTANSWER);
            _db.execSQL(DATABASE_CREATE_SQL_TYPE);
            _db.execSQL(DATABASE_CREATE_SQL_TAG);
            _db.execSQL(DATABASE_CREATE_SQL_TOPICTAG);
            _db.execSQL(DATABASE_CREATE_SQL_TOPICTAG);
            _db.execSQL(DATABASE_CREATE_SQL_REHEARSAL);
            _db.execSQL(DATABASE_CREATE_SQL_REHEARSALINFO);

        }

        //todo
        public void dropDatabase(SQLiteDatabase db) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLEKEY_ACCOUNT);
            db.execSQL("DROP TABLE IF EXISTS " + TABLEKEY_TOPIC);
            db.execSQL("DROP TABLE IF EXISTS " + TABLEKEY_QUESTION);
            db.execSQL("DROP TABLE IF EXISTS " + TABLEKEY_CORRECTANSWER);
            db.execSQL("DROP TABLE IF EXISTS " + TABLEKEY_INCORRECTANSWER);
            db.execSQL("DROP TABLE IF EXISTS " + TABLEKEY_ANSWERVALUE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLEKEY_TYPE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLEKEY_TAG);
            db.execSQL("DROP TABLE IF EXISTS " + TABLEKEY_TOPICTAG);


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("DBAdapter", "Upgrading application's database from version " + oldVersion
                    + " to " + newVersion + ", which will destroy all old data!");

            // Destroy old database:
            db.execSQL("DROP TABLE IF EXISTS " + TABLEKEY_ACCOUNT);
            db.execSQL("DROP TABLE IF EXISTS " + TABLEKEY_TOPIC);
            db.execSQL("DROP TABLE IF EXISTS " + TABLEKEY_QUESTION);
            db.execSQL("DROP TABLE IF EXISTS " + TABLEKEY_CORRECTANSWER);
            db.execSQL("DROP TABLE IF EXISTS " + TABLEKEY_INCORRECTANSWER);
            db.execSQL("DROP TABLE IF EXISTS " + TABLEKEY_ANSWERVALUE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLEKEY_TYPE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLEKEY_TAG);
            db.execSQL("DROP TABLE IF EXISTS " + TABLEKEY_TOPICTAG);

            // Recreate new database:
            onCreate(db);
        }
    }
}
