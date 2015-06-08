package ica.han.oose.project.overhoorapp.endpoints.rehearsal;

import java.util.ArrayList;
import java.util.List;

import ica.han.oose.project.overhoorapp.endpoints.Shared;
import ica.han.oose.project.overhoorapp.json.constants.Get;
import ica.han.oose.project.overhoorapp.json.constants.Post;
import ica.han.oose.project.overhoorapp.json.models.rehearsals.read.Item;
import ica.han.oose.project.overhoorapp.json.models.rehearsals.read.QuestionAnswer;
import ica.han.oose.project.overhoorapp.json.models.rehearsals.read.Rehearsal;
import ica.han.oose.project.overhoorapp.json.models.rehearsals.read.RehearsalList;
import ica.han.oose.project.overhoorapp.json.models.rehearsals.read.RehearsalWrapper;
import ica.han.oose.project.overhoorapp.util.genson.Deserializer;
import ica.han.oose.project.overhoorapp.util.genson.Serializer;
import ica.han.oose.project.overhoorapp.util.jsonbuilder.RehearsalBuilder;
import ica.han.oose.project.overhoorapp.util.requestbuilder.impl.GetRequestBuilder;
import ica.han.oose.project.overhoorapp.util.requestbuilder.impl.JSONPostRequestBuilder;

/**
 * Provides easy access to endpoints related to the Rehearsals collection.
 *
 * @author Niels
 * @version 1.0
 * @since 2-6-2015
 */
public class RehearsalAPI {

    /**
     * Private constructor to prevent instantiation.
     */
    private RehearsalAPI() {

    }

    /**
     * Creates a Rehearsal.
     *
     * @param topicObjectID  The topic's object ID to create a Rehearsal from.
     * @param label          The label to add to this rehearsal.
     * @param minimalCorrect The amount of times a question needs to be answered correctly for it
     *                       not to be rehearsed.
     * @param endDate        The end date and time of this rehearsal in UNIX time.
     * @return A RehearsalWrapper object.
     * @see ica.han.oose.project.overhoorapp.json.models.rehearsals.read.RehearsalWrapper
     */
    public static RehearsalWrapper createRehearsal(final String topicObjectID, final String label,
                                                   final int minimalCorrect, final long endDate) {
        final JSONPostRequestBuilder builder = new JSONPostRequestBuilder(Post.CREATE_REHEARSAL_FROM_TOPIC_IDS.getUrl());
        final RehearsalBuilder rehearsalBuilder = new RehearsalBuilder();
        rehearsalBuilder.setLabel(label);
        rehearsalBuilder.setMinCorrect(minimalCorrect);
        rehearsalBuilder.setEndDate(endDate);
        rehearsalBuilder.setTopicID(topicObjectID);
        builder.setContent(rehearsalBuilder.toJson());
        Shared.execute(builder);
        return (RehearsalWrapper) Deserializer.deserialize(builder.getResult(), RehearsalWrapper.class);
    }

    /**
     * Resets the rehearsal.
     *
     * @param rehearsalObjectID The object ID of the rehearsal to reset.
     */
    public static void reset(final String rehearsalObjectID) {
        final GetRequestBuilder builder = new GetRequestBuilder(Get.RESET_REHEARSAL_BY_ID.getUrl(rehearsalObjectID));
        Shared.execute(builder);
    }

    /**
     * Retrieves the current active question for a rehearsal.
     *
     * @param rehearsalObjectID The object ID of the rehearsal to retrieve the current active
     *                          question of.
     */
    public static void getCurrentActiveQuestion(final String rehearsalObjectID) {
        final GetRequestBuilder builder = new GetRequestBuilder(Get.REHEARSAL_CURRENT_QUESTION_BY_ID.getUrl(rehearsalObjectID));
        Shared.execute(builder);
    }

    /**
     * Retrieves the status for a rehearsal.
     *
     * @param rehearsalObjectID The object ID of the rehearsal to retrieve the status of.
     */
    public static void getStatus(final String rehearsalObjectID) {
        final GetRequestBuilder builder = new GetRequestBuilder(Get.REHEARSAL_STATUS_BY_ID.getUrl(rehearsalObjectID));
        Shared.execute(builder);
    }

    //todo documentatie
    public static RehearsalList getRehearsalList() {
        final GetRequestBuilder builder = new GetRequestBuilder(Get.MY_REHEARSALS_INDEX.getUrl());
        Shared.execute(builder);
        RehearsalList rehearsalList = (RehearsalList) Deserializer.deserialize(builder.getResult(), RehearsalList.class);
        if (rehearsalList == null) {
            rehearsalList = new RehearsalList();
            Rehearsal[] r = {};
            rehearsalList.setRehearsals(r);
        }
        return rehearsalList;
    }

    //todo documentatie
    public static List<Rehearsal> getRehearsalsForTopicID(final String topicObjectID) {
        final List<Rehearsal> rehearsalList = new ArrayList<>();
        final Rehearsal[] rehearsals = getRehearsalList().getRehearsals();
        for (final Rehearsal r : rehearsals) {
            if (r.getItems().length > 0) {
                for (final Item i : r.getItems()) {
                    if (i.getQuestion().getTopicId().get$oid().equals(topicObjectID)) {
                        rehearsalList.add(r);
                    }
                }
            }
        }
        return rehearsalList;
    }

    //todo documentatie
    public static void sendAnswerForRehearsal(final String rehearsalObjectID, final QuestionAnswer answer) {
        final JSONPostRequestBuilder builder = new JSONPostRequestBuilder(Post.ANSWER_QUESTION_IN_REHEARSAL.getUrl(rehearsalObjectID));
        builder.setContent(Serializer.serialize(answer));
        Shared.execute(builder);
    }
}
