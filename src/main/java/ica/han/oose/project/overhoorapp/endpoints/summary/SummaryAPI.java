package ica.han.oose.project.overhoorapp.endpoints.summary;

import java.util.List;

import ica.han.oose.project.overhoorapp.endpoints.Shared;
import ica.han.oose.project.overhoorapp.exceptions.SummaryNotFoundException;
import ica.han.oose.project.overhoorapp.json.constants.Get;
import ica.han.oose.project.overhoorapp.json.constants.Post;
import ica.han.oose.project.overhoorapp.json.models.shared.ContributorElement;
import ica.han.oose.project.overhoorapp.json.models.shared.TagElement;
import ica.han.oose.project.overhoorapp.json.models.summaries.read.Summaries;
import ica.han.oose.project.overhoorapp.json.models.summaries.read.SummaryWrapper;
import ica.han.oose.project.overhoorapp.json.models.summaries.update.Topic;
import ica.han.oose.project.overhoorapp.json.wrappers.Summary;
import ica.han.oose.project.overhoorapp.json.wrappers.TopicId;
import ica.han.oose.project.overhoorapp.util.genson.Deserializer;
import ica.han.oose.project.overhoorapp.util.genson.Serializer;
import ica.han.oose.project.overhoorapp.util.requestbuilder.impl.GetRequestBuilder;
import ica.han.oose.project.overhoorapp.util.requestbuilder.impl.JSONDeleteRequestBuilder;
import ica.han.oose.project.overhoorapp.util.requestbuilder.impl.JSONPostRequestBuilder;

/**
 * Provides easy access to endpoints related to the Summaries collection.
 *
 * @author Niels
 * @version 1.0
 * @since 2-6-2015
 */
public class SummaryAPI {

    /**
     * Private constructor to prevent instantiation.
     */
    private SummaryAPI() {

    }

    /**
     * Returns a list containing all summaries for the account that is currently logged in.
     *
     * @return A List of Summary objects containing all summaries for the account that is currently
     * logged in.
     */
    public static List<Summary> getMySummaries() {
        final GetRequestBuilder builder = new GetRequestBuilder(Get.MY_SUMMARIES_INDEX.getUrl());
        Shared.execute(builder);
        final Summaries s = (Summaries) Deserializer.deserialize(builder.getResult(), Summaries.class);
        return s.getSummaryList();
    }

    /**
     * Returns the summary for a topic's object ID.
     *
     * @param topicObjectID The topic object ID.
     * @return The summary.
     */
    public static Summary getSummaryByTopicID(final String topicObjectID) {
        final List<Summary> summaries = getMySummaries();
        for (final Summary s : summaries) {
            if (s.getRootTopic().getTopicObjectID().equals(topicObjectID)) {
                return s;
            } else {
                for (final TopicId topic : s.getChildrenTopics()) {
                    if (topic.getTopicObjectID().equals(topicObjectID)) {
                        return s;
                    }
                }
            }
        }
        throw new SummaryNotFoundException(topicObjectID);
    }

    /**
     * Creates a summary.
     *
     * @param description  A brief description of the summary.
     * @param label        A label for the summary.
     * @param contributors A list containing all contributors to the summary.
     * @param tags         A list containing all the tags to add to this summary.
     * @param endDate      The end date and time of this summary in UNIX time.
     * @return
     */
    public static Summary createSummary(final String description, final String label,
                                        final List<ContributorElement> contributors,
                                        final List<TagElement> tags, final long endDate) {
        final JSONPostRequestBuilder builder = new JSONPostRequestBuilder(Post.CREATE_SUMMARY.getUrl());
        ica.han.oose.project.overhoorapp.json.models.summaries.create.Summary summary =
                new ica.han.oose.project.overhoorapp.json.models.summaries.create.Summary();
        summary.setDescription(description);
        summary.setLabel(label);
        summary.setEndDate(endDate);
        summary.setContributors(contributors.toArray(new ContributorElement[contributors.size()]));
        summary.setTags(tags.toArray(new TagElement[tags.size()]));
        builder.setContent(Serializer.serialize(summary));
        Shared.execute(builder);
        final SummaryWrapper ele = (SummaryWrapper) Deserializer.deserialize(builder.getResult(), SummaryWrapper.class);
        return new Summary(ele.getSummary());
    }

    /**
     * Deletes a topic from a summary.
     *
     * @param summaryObjectID The summary object id to remove the topic from.
     * @param topic           The topic to remove from the summary.
     */
    public static void deleteTopicFromSummary(final String summaryObjectID, final Topic topic) {
        final JSONDeleteRequestBuilder builder = new JSONDeleteRequestBuilder(Post.DELETE_TOPIC_FROM_SUMMARY.getUrl(summaryObjectID));
        builder.setContent(Serializer.serialize(topic));
        Shared.execute(builder);
    }

    /**
     * Deletes a Summary by object ID.
     *
     * @param summaryObjectID The object ID.
     */

    public static void deleteSummary(final String summaryObjectID) {
        final JSONDeleteRequestBuilder builder = new JSONDeleteRequestBuilder(Post.DELETE_SUMMARY.getUrl(summaryObjectID));
        Shared.execute(builder);
    }
}
