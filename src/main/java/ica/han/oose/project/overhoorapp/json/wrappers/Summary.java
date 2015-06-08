package ica.han.oose.project.overhoorapp.json.wrappers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ica.han.oose.project.overhoorapp.json.models.shared.ContributorElement;
import ica.han.oose.project.overhoorapp.json.models.shared.TagElement;
import ica.han.oose.project.overhoorapp.json.models.summaries.read.ChildElement;
import ica.han.oose.project.overhoorapp.json.models.summaries.read.SummaryElement;

/**
 * @author Niels
 * @version 1.0
 * @since 28-5-2015
 */
public class Summary {

    /**
     * Stores the SummaryElement instance.
     */
    private SummaryElement summary;

    /**
     * Creates a new Summary object.
     *
     * @param summary The Summary object.
     */
    public Summary(final SummaryElement summary) {
        this.summary = summary;
    }

    /**
     * Returns the Summary's object ID.
     *
     * @return The Summary's object ID.
     */
    public String getSummaryID() {
        return summary.get_id().get$oid();
    }

    public List<ContributorElement> getContributors() {
        return Arrays.asList(summary.getContributors());
    }

    public int getOwnerUID() {
        return Integer.parseInt(summary.getOwner().getUid());
    }

    public List<TagElement> getTags() {
        return Arrays.asList(summary.getTags());
    }

    public TopicId getRootTopic() {
        return new TopicId(summary.getTopics().getTopicId());
    }

    public List<TopicId> getChildrenTopics() {
        final List<TopicId> children = new ArrayList<TopicId>();
        for (final ChildElement ele : summary.getTopics().getChildren()) {
            children.add(new TopicId(ele.getTopicId()));
        }
        return children;
    }

    /**
     * @return The summary's title
     */
    public String getTitle() {
        return summary.getTitle();
    }

}
