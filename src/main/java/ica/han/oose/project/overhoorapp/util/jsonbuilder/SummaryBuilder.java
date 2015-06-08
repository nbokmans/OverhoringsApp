package ica.han.oose.project.overhoorapp.util.jsonbuilder;

import java.util.ArrayList;
import java.util.List;

import ica.han.oose.project.overhoorapp.exceptions.MissingValueException;
import ica.han.oose.project.overhoorapp.json.models.shared.ContributorElement;
import ica.han.oose.project.overhoorapp.json.models.shared.TagElement;
import ica.han.oose.project.overhoorapp.json.models.summaries.read.SummaryElement;
import ica.han.oose.project.overhoorapp.util.genson.Serializer;


/**
 * Utility for converting Java objects to JSON.
 *
 * @author Niels Bokmans
 * @version 1.0
 * @since 19-5-2015
 */
public class SummaryBuilder {

    /**
     * Stores the SummaryElement instance.
     */
    private SummaryElement summary;

    /**
     * Stores the tags for this summary.
     */
    private List<TagElement> tags = new ArrayList<TagElement>();

    /**
     * Stores the contributors for this summary.
     */
    private List<ContributorElement> contributors = new ArrayList<>();

    /**
     * Creates a new SummaryBuilder
     */
    public SummaryBuilder() {
        this.summary = new SummaryElement();
    }

    /**
     * Adds a contributor to a summary. Currently does not work.
     *
     * @param userId User ID of contributor to add to the summary.
     * @return A Summary object with the new contributor.
     */
    public SummaryBuilder addContributor(final int userId) {
        final ContributorElement[] contributorsArray = new ContributorElement[0];
        summary.setContributors(contributorsArray);
        return this;
    }

    /**
     * Adds a tag to the summary.
     *
     * @param tag The tag to add to the summary.
     * @return A Summary object with the new tag.
     */
    public SummaryBuilder addTag(final String tag) {
        final TagElement newTag = new TagElement();
        newTag.setText(tag);
        tags.add(newTag);
        return this;
    }

    /**
     * Sets the summary's end date.
     *
     * @param endDate The end date to add to the summary.
     * @return A summary object with the new end date.
     */
    public SummaryBuilder setEndDate(final long endDate) {
        return this;
    }

    public SummaryBuilder setLabel(final String label) {
        summary.setLabel(label);
        return this;
    }

    public SummaryBuilder setDescription(final String description) {
        summary.setDescription(description);
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
        return Serializer.serialize(summary);
        //   }
        //  throw new MissingValueException();
    }

    /**
     * Allows the builder to check whether it's ready to convert it's object into JSON.
     *
     * @return <tt>true</tt> if the contributors, description, endDate, label and tags are set,
     * <tt>false</tt> otherwise.
     */
    private boolean checkBuilder() {
        final List<String> missingValues = new ArrayList<>();
        if (summary.getContributors() == null)
            missingValues.add("Contributors");
        if (summary.getTags() == null)
            missingValues.add("Tags");
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

    /**
     * Converts the tag list to an array.
     */
    private void convertTagListToArray() {
        TagElement[] tagElements = new TagElement[tags.size()];
        tags.toArray(tagElements);
        summary.setTags(tagElements);
    }

    /**
     * Converts the contributor list to an array.
     */
    private void convertContributorListToArray() {
        ContributorElement[] contributorElements = new ContributorElement[contributors.size()];
        contributors.toArray(contributorElements);
        summary.setContributors(contributorElements);
    }
}
