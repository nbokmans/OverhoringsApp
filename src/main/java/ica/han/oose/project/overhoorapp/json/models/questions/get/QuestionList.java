package ica.han.oose.project.overhoorapp.json.models.questions.get;

/**
 * This class is intentionally left undocumented.
 *
 * @author http://javafromjson.dashingrocket.com
 * @version 1.1
 * @since 01-06-2015
 */
public class QuestionList {
    private QuestionWrapper[] questions;

    public QuestionWrapper[] getQuestions() {
        return questions;
    }

    public void setQuestions(QuestionWrapper[] questions) {
        this.questions = questions;
    }
}
