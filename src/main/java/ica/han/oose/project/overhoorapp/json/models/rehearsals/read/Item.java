/**
 * This class is intentionally left undocumented.
 *
 * @author http://javafromjson.dashingrocket.com
 * @version 1.1
 * @since 01-06-2015
 */
package ica.han.oose.project.overhoorapp.json.models.rehearsals.read;


import ica.han.oose.project.overhoorapp.json.models.questions.get.QuestionWrapper;

public class Item {
    private QuestionWrapper question;
    private Score score;
    private History[] history;
    private String questionType;

    public QuestionWrapper getQuestion() {
        return question;
    }

    public void setQuestion(QuestionWrapper question) {
        this.question = question;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public History[] getHistory() {
        return history;
    }

    public void setHistory(History[] history) {
        this.history = history;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }
}
