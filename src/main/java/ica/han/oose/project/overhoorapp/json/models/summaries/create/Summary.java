/**
 * This class is intentionally left undocumented.
 *
 * @author http://javafromjson.dashingrocket.com
 * @version 1.1
 * @since 01-06-2015
 */
package ica.han.oose.project.overhoorapp.json.models.summaries.create;

import ica.han.oose.project.overhoorapp.json.models.shared.ContributorElement;
import ica.han.oose.project.overhoorapp.json.models.shared.TagElement;

public class Summary {

    private ContributorElement[] contributors;
    private TagElement[] tags;
    private String label;
    private String description;
    private long endDate;

    public TagElement[] getTags() {
        return tags;
    }

    public void setTags(TagElement[] tags) {
        this.tags = tags;
    }

    public ContributorElement[] getContributors() {
        return contributors;
    }

    public void setContributors(ContributorElement[] contributors) {
        this.contributors = contributors;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }
}
