/**
 * This class is intentionally left undocumented.
 *
 * @author http://javafromjson.dashingrocket.com
 * @version 1.1
 * @since 01-06-2015
 */
package ica.han.oose.project.overhoorapp.json.models.summaries.update;

public class TargetWrapper {

    private Target target;
    private Topic topic;
    private boolean prepend;

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public boolean isPrepend() {
        return prepend;
    }

    public void setPrepend(boolean prepend) {
        this.prepend = prepend;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}
