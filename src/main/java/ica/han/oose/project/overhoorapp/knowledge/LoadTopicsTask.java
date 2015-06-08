package ica.han.oose.project.overhoorapp.knowledge;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import ica.han.oose.project.overhoorapp.endpoints.topic.TopicAPI;
import ica.han.oose.project.overhoorapp.json.models.topics.create.Topic;
import ica.han.oose.project.overhoorapp.json.wrappers.Summary;
import ica.han.oose.project.overhoorapp.json.wrappers.TopicId;

/**
 * Created by Jaap Weijland on 6-6-2015.
 */
public class LoadTopicsTask extends AsyncTask<Summary, Void, List<Topic>> {
    @Override
    protected List<Topic> doInBackground(Summary... params) {
        List<Topic> topicList = new ArrayList<>();
        for (TopicId t : params[0].getChildrenTopics()) {
            final Topic topic = TopicAPI.getTopicByID(t.getTopicObjectID());
            if (topic != null) {
                topicList.add(topic);
            }
        }
        return topicList;
    }
}
