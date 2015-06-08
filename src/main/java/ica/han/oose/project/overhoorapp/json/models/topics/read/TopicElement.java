/**
 * This class is intentionally left undocumented.
 *
 * @author http://javafromjson.dashingrocket.com
 * @version 1.1
 * @since 01-06-2015
 */
package ica.han.oose.project.overhoorapp.json.models.topics.read;

import ica.han.oose.project.overhoorapp.json.models.shared.ContributorElement;
import ica.han.oose.project.overhoorapp.json.models.shared.Owner;
import ica.han.oose.project.overhoorapp.json.models.shared.TagElement;
import ica.han.oose.project.overhoorapp.json.models.shared._id;

/**
 * This class is intentionally left undocumented.
 *
 * @author http://javafromjson.dashingrocket.com
 * @version 1.0
 * @see ica.han.oose.project.overhoorapp.json.wrappers.Topic
 * @since 16 april 2015
 */
public class TopicElement {
    private _id _id;
    private ContributorElement[] contributors;
    private long updateDate;
    private TagElement[] tags;
    private Owner owner;
    private long creationDate;
    private String content;
    private String title;
    private boolean isRoot;
    private int version;

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

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

    public TagElement[] getTags() {
        return tags;
    }

    public void setTags(TagElement[] tags) {
        this.tags = tags;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean getIsRoot() {
        return isRoot;
    }

    public void setIsRoot(boolean root) {
        this.isRoot = root;
    }

}