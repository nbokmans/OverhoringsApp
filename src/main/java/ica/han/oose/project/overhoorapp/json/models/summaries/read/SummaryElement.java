/**
 * This class is intentionally left undocumented.
 *
 * @author http://javafromjson.dashingrocket.com
 * @version 1.1
 * @since 01-06-2015
 */

package ica.han.oose.project.overhoorapp.json.models.summaries.read;

import ica.han.oose.project.overhoorapp.json.models.shared.ContributorElement;
import ica.han.oose.project.overhoorapp.json.models.shared.Owner;
import ica.han.oose.project.overhoorapp.json.models.shared.TagElement;
import ica.han.oose.project.overhoorapp.json.models.shared._id;

public class SummaryElement {

    private int version;
    private long creationDate;
    private long updateDate;
    private long endDate;
    private _id _id;
    private String label;
    private String title;
    private String description;
    private ContributorElement[] contributors;
    private TagElement[] tags;
    private Owner owner;
    private Topics topics;

    public _id get_id() {
        return _id;
    }

    public void set_id(_id _id) {
        this._id = _id;
    }

    public ContributorElement[] getContributors() {
        return contributors;
    }

    public void setContributors(ContributorElement[] contributors) {
        this.contributors = contributors;
    }

    public TagElement[] getTags() {
        return tags;
    }

    public void setTags(TagElement[] tags) {
        this.tags = tags;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Topics getTopics() {
        return topics;
    }

    public void setTopics(Topics topics) {
        this.topics = topics;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}