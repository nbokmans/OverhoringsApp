package ica.han.oose.project.overhoorapp.json.models.questions.get;

import ica.han.oose.project.overhoorapp.json.constants.QuestionType;
import ica.han.oose.project.overhoorapp.json.models.shared.Answers;
import ica.han.oose.project.overhoorapp.json.models.shared.Owner;
import ica.han.oose.project.overhoorapp.json.models.shared.Statement;
import ica.han.oose.project.overhoorapp.json.models.shared.TopicId;
import ica.han.oose.project.overhoorapp.json.models.shared._id;

/**
 * This class is intentionally left undocumented.
 *
 * @author http://javafromjson.dashingrocket.com
 * @version 1.1
 * @since 01-06-2015
 */
public class QuestionWrapper {
    private _id _id;
    private Answers correctAnswers;
    private long creationDate;
    private Answers[] incorrectAnswers;
    private Object[] info;
    private Owner owner;
    private String questionType;
    private Statement statement;
    private TopicId topicId;
    private long updateDate;
    private int version;

    public _id get_id() {
        return _id;
    }

    public void set_id(_id _id) {
        this._id = _id;
    }

    public Answers getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(Answers correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }

    public Answers[] getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void setIncorrectAnswers(Answers[] incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }

    public Object[] getInfo() {
        return info;
    }

    public void setInfo(Object[] info) {
        this.info = info;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    public TopicId getTopicId() {
        return topicId;
    }

    public void setTopicId(TopicId topicId) {
        this.topicId = topicId;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public QuestionType getQType() {
        for (QuestionType type : QuestionType.values()) {
            if (type.name().equalsIgnoreCase(questionType)) {
                return type;
            }
        }
        return QuestionType.SINGLEANSWER;
    }
}
