package ica.han.oose.project.overhoorapp.endpoints.question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ica.han.oose.project.overhoorapp.endpoints.Shared;
import ica.han.oose.project.overhoorapp.json.constants.Get;
import ica.han.oose.project.overhoorapp.json.constants.Post;
import ica.han.oose.project.overhoorapp.json.models.questions.CreateQuestion;
import ica.han.oose.project.overhoorapp.json.models.questions.get.QuestionList;
import ica.han.oose.project.overhoorapp.json.models.questions.get.QuestionWrapper;
import ica.han.oose.project.overhoorapp.util.genson.Deserializer;
import ica.han.oose.project.overhoorapp.util.genson.Serializer;
import ica.han.oose.project.overhoorapp.util.requestbuilder.impl.GetRequestBuilder;
import ica.han.oose.project.overhoorapp.util.requestbuilder.impl.JSONDeleteRequestBuilder;
import ica.han.oose.project.overhoorapp.util.requestbuilder.impl.JSONPostRequestBuilder;

/**
 * Provides easy access to endpoints related to the Questions collection.
 *
 * @author Niels Bokmans
 * @version 1.0
 * @since 1-6-2015
 */
public class QuestionAPI {

    /**
     * Private constructor to prevent instantiation.
     */
    private QuestionAPI() {
    }

    /**
     * Returns a Question object.
     * todo: Deserializeren naar Question object.
     *
     * @param questionObjectID The question to look up in the database.
     * @return A Question object.
     */
    public static String getQuestion(final String questionObjectID) {
        final GetRequestBuilder builder = new GetRequestBuilder(Get.QUESTION_INFO_BY_ID.getUrl(questionObjectID));
        Shared.execute(builder);
        return builder.getResult();
    }

    /**
     * Returns a list containing all questions for the currently logged in user.
     *
     * @return A List containing all Questions for the currently logged in user.
     */
    public static List<QuestionWrapper> getQuestions() {
        final GetRequestBuilder builder = new GetRequestBuilder(Get.MY_QUESTIONS_INDEX.getUrl());
        Shared.execute(builder);
        final QuestionList questionList = (QuestionList) Deserializer.deserialize(builder.getResult(), QuestionList.class);
        return Arrays.asList(questionList.getQuestions());
    }

    /**
     * Returns a list containing all questions by a specific topic object ID.
     *
     * @param topicID The topic object ID to filter on.
     * @return A List containing all Questions by a specific topic object ID.
     */
    public static List<QuestionWrapper> getQuestionsByTopicID(final String topicID) {
        final List<QuestionWrapper> questions = getQuestions();
        final List<QuestionWrapper> filteredList = new ArrayList<QuestionWrapper>();
        for (final QuestionWrapper q : questions) {
            if (q.getTopicId().get$oid().equals(topicID)) {
                filteredList.add(q);
            }
        }
        return filteredList;
    }

    /**
     * Creates a new question.
     * todo: return Question object.
     *
     * @param question A built CreateQuestion
     * @return The new Question in JSON format.
     */
    public static String createQuestion(final CreateQuestion question) {
        final JSONPostRequestBuilder builder = new JSONPostRequestBuilder(Post.CREATE_QUESTION.getUrl());
        final String json = Serializer.serializeSkipNulls(question).replaceAll(",\"order\":0", "");
        builder.setContent(json);
        System.out.println("TAG" + json);
        Shared.execute(builder);
        return builder.getResult();
    }

    /**
     * Deletes a question by object ID.
     *
     * @param questionObjectID The question object ID to delete.
     */
    public static void deleteQuestion(final String questionObjectID) {
        final JSONDeleteRequestBuilder builder = new JSONDeleteRequestBuilder(Post.DELETE_QUESTION.getUrl(questionObjectID));
        Shared.execute(builder);
    }

    /**
     * Updates a question.
     *
     * @param question The (updated) question.
     */
    public void updateQuestion(final QuestionWrapper question) {
        final JSONPostRequestBuilder builder = new JSONPostRequestBuilder(Post.UPDATE_TOPIC.getUrl(question.get_id().get$oid()));
        builder.setContent(Serializer.serialize(question));
        Shared.execute(builder);
    }
}
