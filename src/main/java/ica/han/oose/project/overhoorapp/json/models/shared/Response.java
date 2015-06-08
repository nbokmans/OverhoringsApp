/**
 * This class is intentionally left undocumented.
 *
 * @author http://javafromjson.dashingrocket.com
 * @version 1.1
 * @since 01-06-2015
 */
package ica.han.oose.project.overhoorapp.json.models.shared;

public class Response {
    private int order;
    private String orderedoption;
    private String option;

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getOrderedoption() {
        return orderedoption;
    }

    public void setOrderedoption(String orderedoption) {
        this.orderedoption = orderedoption;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
