package ica.han.oose.project.overhoorapp.json.models.questions.create;

import java.util.List;

import ica.han.oose.project.overhoorapp.json.models.shared.Statement;
import ica.han.oose.project.overhoorapp.json.models.shared.TopicId;

/**
 * This class is intentionally left undocumented.
 *
 * @author http://javafromjson.dashingrocket.com
 * @version 1.1
 * @since 01-06-2015
 */
public class Question {
    private Answers correctAnswers;
    private List<Answers> incorrectAnswers;
    private List<Object> info;
    private String questionType;
    private Statement statement;
    private TopicId topicId;

    public Answers getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(Answers correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public List<Answers> getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void setIncorrectAnswers(List<Answers> incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }

    public List<Object> getInfo() {
        return info;
    }

    public void setInfo(List<Object> info) {
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

    public void setStatement(final Statement statement) {
        this.statement = statement;
    }

    public TopicId getTopicId() {
        return topicId;
    }

    public void setTopicId(final TopicId topicId) {
        this.topicId = topicId;
    }
}