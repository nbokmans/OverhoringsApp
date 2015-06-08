/**
 * This class is intentionally left undocumented.
 *
 * @author http://javafromjson.dashingrocket.com
 * @version 1.1
 * @since 01-06-2015
 */
package ica.han.oose.project.overhoorapp.json.models.rehearsals.read;

import ica.han.oose.project.overhoorapp.json.models.shared.Owner;
import ica.han.oose.project.overhoorapp.json.models.shared._id;

public class Rehearsal {
    private _id id;
    private Item[] corrects;
    private long creationDate;
    private Item[] incorrects;
    private Item[] items;
    private String label;
    private int minCorrect;
    private String[] questionIds;
    private Owner owner;
    private long updateDate;
    private long endDate;
    private double version;

    public _id get_id() {
        return id;
    }

    public void set_id(_id id) {
        this.id = id;
    }

    public Item[] getCorrects() {
        return corrects;
    }

    public void setCorrects(Item[] corrects) {
        this.corrects = corrects;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }

    public Item[] getIncorrects() {
        return incorrects;
    }

    public void setIncorrects(Item[] incorrects) {
        this.incorrects = incorrects;
    }

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getMinCorrect() {
        return minCorrect;
    }

    public void setMinCorrect(int minCorrect) {
        this.minCorrect = minCorrect;
    }

    public String[] getQuestionIds() {
        return questionIds;
    }

    public void setQuestionIds(String[] questionIds) {
        this.questionIds = questionIds;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
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

    public double getVersion() {
        return version;
    }

    public void setVersion(double version) {
        this.version = version;
    }
}