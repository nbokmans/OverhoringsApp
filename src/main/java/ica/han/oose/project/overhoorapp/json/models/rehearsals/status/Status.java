/**
 * This class is intentionally left undocumented.
 *
 * @author http://javafromjson.dashingrocket.com
 * @version 1.1
 * @since 01-06-2015
 */
package ica.han.oose.project.overhoorapp.json.models.rehearsals.status;


public class Status {
    private int numberOfActiveItems;
    private int totalNumberOfItems;
    private long percentageCompleted;

    public int getNumberOfActiveItems() {
        return numberOfActiveItems;
    }

    public void setNumberOfActiveItems(int numberOfActiveItems) {
        this.numberOfActiveItems = numberOfActiveItems;
    }

    public int getTotalNumberOfItems() {
        return totalNumberOfItems;
    }

    public void setTotalNumberOfItems(int totalNumberOfItems) {
        this.totalNumberOfItems = totalNumberOfItems;
    }

    public long getPercentageCompleted() {
        return percentageCompleted;
    }

    public void setPercentageCompleted(long percentageCompleted) {
        this.percentageCompleted = percentageCompleted;
    }
}
