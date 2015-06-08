/**
 * This class is intentionally left undocumented.
 *
 * @author http://javafromjson.dashingrocket.com
 * @version 1.1
 * @since 01-06-2015
 */
package ica.han.oose.project.overhoorapp.json.models.rehearsals.create;

public class Rehearsal {
    private long endDate;
    private String label;
    private int minCorrect;
    private String[] topicIds;

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

    public int getMinCorrect() {
        return minCorrect;
    }

    public void setMinCorrect(int minCorrect) {
        this.minCorrect = minCorrect;
    }

    public String[] getTopicIds() {
        return topicIds;
    }

    public void setTopicIds(String[] topicIds) {
        this.topicIds = topicIds;
    }
}