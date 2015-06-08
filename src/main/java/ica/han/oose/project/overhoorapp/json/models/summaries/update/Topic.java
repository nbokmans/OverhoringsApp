/**
 * This class is intentionally left undocumented.
 *
 * @author http://javafromjson.dashingrocket.com
 * @version 1.1
 * @since 01-06-2015
 */
package ica.han.oose.project.overhoorapp.json.models.summaries.update;

import ica.han.oose.project.overhoorapp.json.models.shared.ContributorElement;
import ica.han.oose.project.overhoorapp.json.models.shared.Owner;
import ica.han.oose.project.overhoorapp.json.models.shared._id;

public class Topic {

    private ContributorElement[] contributors;
    private _id _id;
    private java.lang.Integer version;
    private Owner owner;

    public _id get_id() {
        return _id;
    }

    public void set_id(_id _id) {
        this._id = _id;
    }

    public java.lang.Integer getVersion() {
        return version;
    }

    public void setVersion(java.lang.Integer version) {
        this.version = version;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public ContributorElement[] getContributors() {
        return contributors;
    }

    public void setContributors(ContributorElement[] contributors) {
        this.contributors = contributors;
    }
}