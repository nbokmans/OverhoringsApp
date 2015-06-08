package ica.han.oose.project.overhoorapp.json.models.questions;

import ica.han.oose.project.overhoorapp.json.models.shared.Answers;
import ica.han.oose.project.overhoorapp.json.models.shared.InfoElement;
import ica.han.oose.project.overhoorapp.json.models.shared.Statement;
import ica.han.oose.project.overhoorapp.json.models.shared._id;


public class CreateQuestion {
    private Answers correctAnswers;
    private Answers[] incorrectAnswers;
    private InfoElement[] info;
    private String questionType;
    private Statement statement;
    private _id topicId;
    private long creationDate;
    private long updateDate;

    public Answers getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(Answers correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public Answers[] getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void setIncorrectAnswers(Answers[] incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }

    public InfoElement[] getInfo() {
        return info;
    }

    public void setInfo(InfoElement[] info) {
        this.info = info;
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

    public _id getTopicId() {
        return topicId;
    }

    public void setTopicId(_id topicId) {
        this.topicId = topicId;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }

}