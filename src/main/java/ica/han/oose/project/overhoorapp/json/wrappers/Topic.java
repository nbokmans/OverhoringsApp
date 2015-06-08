package ica.han.oose.project.overhoorapp.json.wrappers;

import java.util.Arrays;
import java.util.List;

import ica.han.oose.project.overhoorapp.json.models.shared.ContributorElement;
import ica.han.oose.project.overhoorapp.json.models.shared.TagElement;
import ica.han.oose.project.overhoorapp.json.models.shared._id;
import ica.han.oose.project.overhoorapp.json.models.topics.read.TopicElement;

/**
 * Provide a wrapper class for TopicElements.
 *
 * @author Niels Bokmans
 * @version 1.1
 * @since 17-04-2015
 */
public class Topic {

    /**
     * Stores the TopicElement instance.
     */
    private TopicElement topic;

    /**
     * Creates a new Topic object.
     *
     * @param topic The TopicElement object.
     */
    public Topic(final TopicElement topic) {
        this.topic = topic;
    }

    /**
     * Returns the topic's object ID.
     *
     * @return the topic's object ID.
     */
    public String getTopicObjectID() {
        return topic.get_id().get$oid();
    }

    /**
     * Sets the topic's object ID.
     *
     * @param objId The new object ID.
     */
    public void setTopicObjectID(String objId) {
        final _id id = new _id();
        id.set$oid(objId);
        topic.set_id(id);
    }

    /**
     * Returns a list containing all the contributors to this topic.
     *
     * @return a list containing all the contributors to this topic.
     */
    public List<ContributorElement> getContributors() {
        return Arrays.asList(topic.getContributors());
    }

    /**
     * Returns the update time of the topic in an UNIX timestamp.
     * Example: http://www.unixtimestamp.com
     *
     * @return the update time of the topic in an UNIX timestamp.
     */
    public long getUpdateDate() {
        return topic.getUpdateDate();
    }

    /**
     * Returns a list containing all the tags added to this topic.
     *
     * @return a list containing all the tags added to this topic.
     */
    public List<TagElement> getTags() {
        return Arrays.asList(topic.getTags());
    }

    /**
     * Returns the topic's owner user ID.
     *
     * @return the topic's owner user ID.
     */
    public int getOwnerUserID() {
        return Integer.parseInt(topic.getOwner().getUid());
    }

    /**
     * Returns the creation date of the topic in an UNIX timestamp.
     * Example: http://www.unixtimestamp.com
     *
     * @return the creation date of the topic in an UNIX timestamp.
     */
    public long getCreationDate() {
        return topic.getCreationDate();
    }

    /**
     * Returns the content (text) of this topic.
     *
     * @return the content (text) of this topic.
     */
    public String getContent() {
        return topic.getContent();
    }

    /**
     * Returns the title of this topic.
     *
     * @return the title of this topic.
     */
    public String getTitle() {
        return topic.getTitle();
    }

    /**
     * Returns whether or not this topic is the root topic.
     *
     * @return <tt>true</tt> if this topic is the root topic
     * <tt>false</tt> if it is not.
     */
    public boolean isRootTopic() {
        return topic.getIsRoot();
    }

    /**
     * Determines if the topic contains a certain tag.
     *
     * @param tag Tag to check the topic for.
     * @return <tt>true</tt> if the topic contains the specified tag,
     * <tt>false</tt> if the topic does not.
     */
    public boolean containsTag(final String tag) {
        for (final TagElement tagElement : getTags()) {
            if (tagElement.getText().equalsIgnoreCase(tag)) {
                return true;
            }
        }
        return false;
    }

        public TopicId getTopicObject() {
        final ica.han.oose.project.overhoorapp.json.models.summaries.read.TopicId ele = new ica.han.oose.project.overhoorapp.json.models.summaries.read.TopicId();
        ele.setTags(topic.getTags());
        ele.set_id(topic.get_id());
        ele.setContent(topic.getContent());
        ele.setVersion(topic.getVersion());
        ele.setContributors(topic.getContributors());
        ele.setOwner(topic.getOwner());
        ele.setCreationDate(topic.getCreationDate());
        ele.setIsRoot(topic.getIsRoot());
        ele.setUpdateDate(topic.getUpdateDate());
        ele.setTitle(topic.getTitle());
        return new TopicId(ele);
    }

}
