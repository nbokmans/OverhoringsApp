/**
 * This class is intentionally left undocumented.
 *
 * @author http://javafromjson.dashingrocket.com
 * @version 1.1
 * @since 01-06-2015
 */

package ica.han.oose.project.overhoorapp.json.models.summaries.read;

public class ChildElement {
    private ChildElement[] children;
    private TopicId topicId;

    public ChildElement[] getChildren() {
        return children;
    }

    public void setChildren(ChildElement[] children) {
        this.children = children;
    }

    public TopicId getTopicId() {
        return topicId;
    }

    public void setTopicId(TopicId topicId) {
        this.topicId = topicId;
    }

}