package ica.han.oose.project.overhoorapp.util.jsonbuilder;

import java.util.ArrayList;
import java.util.List;

import ica.han.oose.project.overhoorapp.exceptions.MissingValueException;
import ica.han.oose.project.overhoorapp.json.models.shared.ContributorElement;
import ica.han.oose.project.overhoorapp.json.models.shared.Owner;
import ica.han.oose.project.overhoorapp.json.models.shared.TagElement;
import ica.han.oose.project.overhoorapp.json.models.shared._id;
import ica.han.oose.project.overhoorapp.json.models.topics.read.TopicElement;
import ica.han.oose.project.overhoorapp.util.genson.Serializer;


/**
 * Utility for converting Java objects to JSON.
 *
 * @author Niels Bokmans
 * @version 1.0
 * @since 19-5-2015
 */
public class TopicBuilder {

    /**
     * Stores the TopicElement instance.
     */
    private TopicElement topic;

    /**
     * Stores the tags for this topic.
     */
    private List<TagElement> tags = new ArrayList<>();

    /**
     * Stores the contributors for this topic.
     */
    private List<ContributorElement> contributors = new ArrayList<>();


    /**
     * Creates a new TopicBuilder
     */
    public TopicBuilder() {
        this.topic = new TopicElement();
        topic.setContributors(new ContributorElement[0]);
        topic.setIsRoot(true);
        topic.setVersion(1);
        topic.setTags(new TagElement[0]);
        topic.setCreationDate(1427704363828L);
        topic.setUpdateDate(1427744363828L);
    }

    /**
     * Sets the object ID of the TopicElement.
     *
     * @param objectID The object ID to add to the TopicElement.
     * @return A TopicBuilder object with the object ID added to it.
     */
    public TopicBuilder setObjectID(final String objectID) {
        final _id id = new _id();
        id.set$oid(objectID);
        topic.set_id(id);
        return this;
    }

    /**
     * Sets the version of the TopicElement.
     * Default: <tt>1.0</tt>.
     *
     * @param version The version to add to the TopicElement.
     * @return A TopicBuilder object with the version added to it.
     */
    public TopicBuilder setVersion(final int version) {
        topic.setVersion(version);
        return this;
    }

    /**
     * Sets the owner of the TopicElement.
     *
     * @param ownerUID The user ID of the owner.
     * @return A TopicBuilder object with the Owner added to it.
     */
    public TopicBuilder setOwner(final int ownerUID) {
        final Owner owner = new Owner();
        owner.setUid(Integer.toString(ownerUID));
        topic.setOwner(owner);
        return this;
    }

    /**
     * Sets the title of the TopicElement.
     *
     * @param title The title to add to the TopicElement.
     * @return A TopicBuilder object with the content.
     */
    public TopicBuilder setTitle(final String title) {
        topic.setTitle(title);
        return this;
    }

    /**
     * Sets the content of the TopicElement.
     *
     * @param content The content to add to the TopicElement.
     * @return A TopicBuilder object with the content.
     */
    public TopicBuilder setContent(final String content) {
        topic.setContent(content);
        return this;
    }

    /**
     * Sets isRoot value of object.
     * Default: <tt>true</tt>.
     *
     * @param isRoot Whether the TopicElement is the root topic or not.
     * @return A TopicBuilder object with the isRoot value.
     */
    public TopicBuilder setIsRoot(final boolean isRoot) {
        topic.setIsRoot(isRoot);
        return this;
    }

    /**
     * Adds a contributor to a topic. Currently does not work.
     *
     * @param userId User ID of contributor to add to the topic.
     * @return A TopicBuilder object with the new contributor.
     */
    public TopicBuilder addContributor(final int userId) {
        final ContributorElement[] contributorArray = new ContributorElement[0];
        topic.setContributors(contributorArray);
        return this;
    }

    /**
     * Adds a tag to the summary.
     *
     * @param tag The tag to add to the summary.
     * @return A TopicBuilder object with the new tag.
     */
    public TopicBuilder addTag(final String tag) {
        final TagElement newTag = new TagElement();
        newTag.setText(tag);
        tags.add(newTag);
        return this;
    }


    /**
     * Serializes the object into JSON format ready to be sent to the server.
     *
     * @return The serialized object in JSON format.
     * @see ica.han.oose.project.overhoorapp.util.requestbuilder.impl.JSONPostRequestBuilder
     */
    public String toJson() {
        convertTagListToArray();
        convertContributorListToArray();
        // if (checkBuilder()) {
        return Serializer.serialize(topic);
        //}
        // throw new MissingValueException();
    }

    /**
     * Allows the builder to check whether it's ready to convert it's object into JSON.
     *
     * @return <tt>true</tt> if the contributors, description, endDate, label and tags are set,
     * <tt>false</tt> otherwise.
     */
    private boolean checkBuilder() {
        final List<String> missingValues = new ArrayList<>();
        if (!hasTags())
            missingValues.add("Tags");
        if (!isObjIdNull())
            missingValues.add("Object ID");
        if (!hasContent())
            missingValues.add("Content");
        if (!hasContributors())
            missingValues.add("Contributors");
        if (!hasAnOwner())
            missingValues.add("Owner");
        if (!hasTitle())
            missingValues.add("Title");
        if (!missingValues.isEmpty()) {
            throw new MissingValueException(getExceptionString(missingValues));
        }
        return true;
    }

    private String getExceptionString(List<String> missingValues) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < missingValues.size(); i++) {
            sb.append(missingValues.get(i));
            if (i != missingValues.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }


    private boolean hasTitle() {
        return topic.getTitle() != null;
    }

    private boolean hasAnOwner() {
        return topic.getOwner() != null;
    }

    private boolean hasContributors() {
        return topic.getContributors() != null;
    }

    private boolean hasContent() {
        return topic.getContent() != null;
    }

    private boolean isObjIdNull() {
        return topic.get_id() != null;
    }

    private boolean hasTags() {
        return topic.getTags() != null;
    }

    /**
     * Converts the tag list to an array.
     */
    private void convertTagListToArray() {
        TagElement[] tagElements = new TagElement[tags.size()];
        tags.toArray(tagElements);
        topic.setTags(tagElements);
    }

    /**
     * Converts the contributor list to an array.
     */
    private void convertContributorListToArray() {
        ContributorElement[] contributorElements = new ContributorElement[contributors.size()];
        contributors.toArray(contributorElements);
        topic.setContributors(contributorElements);
    }

    public TopicElement getTopic() {
        return topic;
    }
}
