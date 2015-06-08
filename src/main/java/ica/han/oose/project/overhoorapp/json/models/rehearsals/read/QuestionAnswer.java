/**
 * This class is intentionally left undocumented.
 *
 * @author http://javafromjson.dashingrocket.com
 * @version 1.1
 * @since 01-06-2015
 */
package ica.han.oose.project.overhoorapp.json.models.rehearsals.read;

import ica.han.oose.project.overhoorapp.json.models.shared.Answers;
import ica.han.oose.project.overhoorapp.json.models.shared._id;

public class QuestionAnswer {
    private Answers answers;
    private _id questionId;

    public _id getQuestionId() {
        return questionId;
    }

    public void setQuestionId(_id questionId) {
        this.questionId = questionId;
    }

    public Answers getAnswers() {
        return answers;
    }

    public void setAnswers(Answers answers) {
        this.answers = answers;
    }
}
