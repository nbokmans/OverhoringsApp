package ica.han.oose.project.overhoorapp.endpoints.topic;

import android.util.Log;

import org.apache.http.HttpStatus;

import java.io.IOException;
import java.util.List;

import ica.han.oose.project.overhoorapp.endpoints.Shared;
import ica.han.oose.project.overhoorapp.endpoints.user.UserAPI;
import ica.han.oose.project.overhoorapp.json.constants.Get;
import ica.han.oose.project.overhoorapp.json.constants.Post;
import ica.han.oose.project.overhoorapp.json.models.shared.ContributorElement;
import ica.han.oose.project.overhoorapp.json.models.shared.Owner;
import ica.han.oose.project.overhoorapp.json.models.shared._id;
import ica.han.oose.project.overhoorapp.json.models.summaries.update.Target;
import ica.han.oose.project.overhoorapp.json.models.summaries.update.TargetWrapper;
import ica.han.oose.project.overhoorapp.json.models.topics.create.Create;
import ica.han.oose.project.overhoorapp.json.models.topics.create.Topic;
import ica.han.oose.project.overhoorapp.json.wrappers.Summary;
import ica.han.oose.project.overhoorapp.util.genson.Deserializer;
import ica.han.oose.project.overhoorapp.util.genson.Serializer;
import ica.han.oose.project.overhoorapp.util.jsonbuilder.TopicBuilder;
import ica.han.oose.project.overhoorapp.util.requestbuilder.impl.GetRequestBuilder;
import ica.han.oose.project.overhoorapp.util.requestbuilder.impl.JSONDeleteRequestBuilder;
import ica.han.oose.project.overhoorapp.util.requestbuilder.impl.JSONPostRequestBuilder;

/**
 * Provides easy access to endpoints related to the Topics collection.
 *
 * @author Niels
 * @version 1.0
 * @since 2-6-2015
 */
public class TopicAPI {

    /**
     * JSON format for adding and removing contributors from a topic.
     */
    private static final String USERID_INSERT = "{{\"uid\": \"%d\"}}";

    /**
     * Private constructor to prevent instantiation.
     */
    private TopicAPI() {

    }

    /**
     * Retrieves a topic by a topic's object ID.
     *
     * @param topicObjectID The topic's object ID.
     * @return The topic.
     */
    public static Topic getTopicByID(final String topicObjectID) {
        final GetRequestBuilder builder = new GetRequestBuilder(Get.TOPIC_BY_ID.getUrl(topicObjectID));
        Shared.execute(builder);
        final Create ele = (Create) Deserializer.deserialize(builder.getResult(), Create.class);
        if (ele != null) {
            return ele.getTopic();
        }
        return null;
    }

    /**
     * Creates a new topic and adds it to a summary.
     *
     * @param summary      The summary to add the topic to.
     * @param isRoot       <tt>true</tt> if the new topic is the root topic of the summary, <tt>false</tt>
     *                     otherwise.
     * @param title        The title for the topic.
     * @param content      The content of the topic.
     * @param tagElements  The tags for the topic.
     * @param contributors A list containing all contributors to the topic.
     * @return The newly created topic.
     */
    public static Topic createTopicInSummary(final Summary summary, final boolean isRoot, final String title, final String content,
                                             final List<String> tagElements, final List<Integer> contributors) {
        final JSONPostRequestBuilder builder = new JSONPostRequestBuilder(Post.CREATE_TOPIC.getUrl());
        final TopicBuilder topicBuilder = new TopicBuilder();
        topicBuilder.setTitle(title).setContent(content).setIsRoot(isRoot);
        for (final Integer uid : contributors) {
            topicBuilder.addContributor(uid);
        }
        for (final String tag : tagElements) {
            topicBuilder.addTag(tag);
        }
        final String contentt = topicBuilder.toJson();
        builder.setContent(topicBuilder.toJson());
        Shared.execute(builder);
        final Create topic = (Create) Deserializer.deserialize(builder.getResult(), Create.class);
        addTopicToSummary(summary.getSummaryID(), summary.getRootTopic().getTopicObjectID(),
                topic.getTopic().get_id().get$oid());
        return topic.getTopic();
    }

    /**
     * Adds a topic to a summary.
     *
     * @param summary     The summary to add the topic to.
     * @param targetTopic The topic to add the new topic to.
     * @param newTopic    The new topic.
     */
    private static void addTopicToSummary(final String summary, final String targetTopic, final String newTopic) {
        final JSONPostRequestBuilder builder = new JSONPostRequestBuilder(Post.ADD_EXISTING_TOPIC_TO_SUMMARY.getUrl(summary));
        final TargetWrapper targetWrapper = new TargetWrapper();
        targetWrapper.setPrepend(false);
        targetWrapper.setTarget(getTarget(targetTopic));
        targetWrapper.setTopic(getTopic(newTopic));
        final String serialized = Serializer.serialize(targetWrapper);
        builder.setContent(serialized);
        Shared.execute(builder);
    }

    /**
     * Creates a new Topic object.
     *
     * @param topicId The topic's object ID.
     * @return The topic.
     */
    private static ica.han.oose.project.overhoorapp.json.models.summaries.update.Topic getTopic(String topicId) {
        final ica.han.oose.project.overhoorapp.json.models.summaries.update.Topic updateTopic
                = new ica.han.oose.project.overhoorapp.json.models.summaries.update.Topic();
        final _id id2 = new _id();
        id2.set$oid(topicId);
        final Owner owner = new Owner();
        owner.setUid(Integer.toString(UserAPI.getUserInfo().getUserID()));
        updateTopic.set_id(id2);
        updateTopic.setOwner(owner);
        updateTopic.setVersion(1);
        updateTopic.setContributors(new ContributorElement[0]);
        return updateTopic;
    }

    /**
     * Creates a new Target object.
     *
     * @param summary The summary's object ID.
     * @return The target.
     */
    private static Target getTarget(final String summary) {
        final Target target = new Target();
        final _id id = new _id();
        final Owner owner = new Owner();
        id.set$oid(summary);
        owner.setUid(Integer.toString(UserAPI.getUserInfo().getUserID()));
        target.setVersion(1);
        target.setOwner(owner);
        target.set_id(id);
        target.setContributors(new ContributorElement[0]);
        return target;
    }

    /**
     * Deletes a topic by object ID.
     *
     * @param topicObjectID The object ID of the topic to delete.
     * @return <tt>true</tt> if successfull, <tt>false</tt> if not.
     */
    public static boolean deleteTopicByID(final String topicObjectID) {
        final JSONDeleteRequestBuilder builder = new JSONDeleteRequestBuilder(Post.DELETE_TOPIC.getUrl(topicObjectID));
        builder.setContent("{}");
        Shared.execute(builder);
        try {
            if (builder.getConnection() != null) {
                return builder.getConnection().getResponseCode() == HttpStatus.SC_NO_CONTENT;
            }
        } catch (IOException e) {
            Log.e("TopicAPI", e.getMessage(), e);
        }
        return false;
    }

    /**
     * Adds a contributor to a topic.
     *
     * @param topicObjectID     The object id of the topic to add the contributor to.
     * @param contributorUserID The user id of the contributor to add to the topic.
     */
    public static void addContributor(final String topicObjectID, final int contributorUserID) {
        final JSONPostRequestBuilder builder = new JSONPostRequestBuilder(Post.TOPIC_CONTRIBUTOR.getUrl(topicObjectID));
        builder.setContent(String.format(USERID_INSERT, contributorUserID));
        Shared.execute(builder);
    }

    /**
     * Deletes a contributor from a topic.
     *
     * @param topicObjectID     The object id of the topic to delete the contributor from.
     * @param contributorUserID The user id of the contributor to delete from the topic.
     */
    public static void deleteContributor(final String topicObjectID, final int contributorUserID) {
        final JSONDeleteRequestBuilder builder = new JSONDeleteRequestBuilder(Post.TOPIC_CONTRIBUTOR.getUrl(topicObjectID));
        builder.setContent(String.format(USERID_INSERT, contributorUserID));
        Shared.execute(builder);
    }

    /**
     * Updates a topic.
     *
     * @param topicBuilder A TopicBuilder that has been built.
     */
    public static void updateTopic(final TopicBuilder topicBuilder) {
        final JSONPostRequestBuilder builder = new JSONPostRequestBuilder(Post.UPDATE_TOPIC.getUrl());
        builder.setContent(topicBuilder.toJson());
        Shared.execute(builder);
    }
}
