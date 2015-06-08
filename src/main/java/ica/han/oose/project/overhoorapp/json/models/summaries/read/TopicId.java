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
import ica.han.oose.project.overhoorapp.json.models.topics.create.Topic;

public class TopicId {
    public java.lang.Integer version;
    private _id _id;
    private java.lang.Long updateDate;
    private ContributorElement[] contributors;
    private TagElement[] tags;
    private Owner owner;
    private java.lang.Long creationDate;
    private java.lang.String content;
    private java.lang.String title;
    private java.lang.Boolean isRoot;

    public _id get_id() {
        return _id;
    }

    public void set_id(_id _id) {
        this._id = _id;
    }

    public java.lang.Long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(java.lang.Long updateDate) {
        this.updateDate = updateDate;
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

    public java.lang.Long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(java.lang.Long creationDate) {
        this.creationDate = creationDate;
    }

    public java.lang.String getContent() {
        return content;
    }

    public void setContent(java.lang.String content) {
        this.content = content;
    }

    public java.lang.String getTitle() {
        return title;
    }

    public void setTitle(java.lang.String title) {
        this.title = title;
    }

    public java.lang.Boolean getIsRoot() {
        return isRoot;
    }

    public void setIsRoot(java.lang.Boolean isRoot) {
        this.isRoot = isRoot;
    }

    public java.lang.Integer getVersion() {
        return version;
    }

    public void setVersion(java.lang.Integer version) {
        this.version = version;
    }


    public Topic toTopic() {
        final Topic topic = new Topic();
        topic.setVersion(getVersion());
        topic.set_id(get_id());
        topic.setUpdateDate(getUpdateDate());
        topic.setContributors(getContributors());
        topic.setTags(getTags());
        topic.setOwner(getOwner());
        topic.setCreationDate(getCreationDate());
        topic.setContent(getContent());
        topic.setTitle(getTitle());
        topic.setIsRoot(getIsRoot());
        return topic;
    }
}