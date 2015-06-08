/**
 * This class is intentionally left undocumented.
 *
 * @author http://javafromjson.dashingrocket.com
 * @version 1.1
 * @since 01-06-2015
 */
package ica.han.oose.project.overhoorapp.json.models.topics.read;

import java.util.ArrayList;
import java.util.List;

import ica.han.oose.project.overhoorapp.json.wrappers.Topic;

/**
 * This class is intentionally left undocumented except for #getTopic(index).
 *
 * @author http://javafromjson.dashingrocket.com
 * @version 1.0
 * @see ica.han.oose.project.overhoorapp.json.wrappers.Topic
 * @since 16 april 2015
 */
public class Topics {
    private TopicElement[] topics;

    /**
     * Returns a wrapped Topic instance of getTopics()[index].
     *
     * @param index the index in the topics (TopicElement[]) array.
     * @return a wrapped Topic instance of getTopics()[index].
     */
    public Topic getTopic(int index) {
        if (index > topics.length) {
            throw new ArrayIndexOutOfBoundsException("Deze index bestaat niet!");
        }
        return new Topic(topics[index]);
    }

    public List<Topic> getTopics() {
        final List<Topic> topicList = new ArrayList<Topic>();
        for (TopicElement element : topics) {
            topicList.add(new Topic(element));
        }
        return topicList;
    }

    public void setTopics(TopicElement[] topics) {
        this.topics = topics;
    }

}