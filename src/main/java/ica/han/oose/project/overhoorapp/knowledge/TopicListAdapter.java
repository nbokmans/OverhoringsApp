package ica.han.oose.project.overhoorapp.knowledge;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ica.han.oose.project.overhoorapp.R;
import ica.han.oose.project.overhoorapp.json.models.topics.create.Topic;

/**
 * TODO: korte beschrijving van klsse
 *
 * @author Jaap Weijland
 * @version 1.0
 * @since 20-5-2015
 */
public class TopicListAdapter extends ArrayAdapter<Topic> {

    private Context cont;

    private int resource;

    private int textViewResourceId;

    private List<Topic> topicList;

    public TopicListAdapter(Context context, int resource, int textViewResourceId, List<Topic> topicList) {
        super(context, resource, textViewResourceId, topicList);
        this.cont = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.topicList = topicList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) cont).getLayoutInflater();
        View row = convertView;
        row = inflater.inflate(resource, parent, false);
        TextView txt = (TextView) row.findViewById(R.id.list_item_basic_textview);
//        final GetRequestBuilder builder = new GetRequestBuilder(Get.TOPIC_BY_ID.getUrl(topicList.get(position).getTopicObjectID()));
//        final Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                builder.submit();
//            }
//        });
//        t.start();
//        try {
//            t.join();
//        } catch (InterruptedException e) {
//            Log.e("TopicListAdapter", e.getMessage(), e);
//        }
//        if(builder.getResult() != null && builder.getResult().length() > 9){
//            final Topic deserialized = (Topic) Deserializer.deserialize(builder.getResult().substring(9, builder.getResult().length() -1), Topic.class);
//            txt.setText(deserialized.getTitle());
//        } else {
//            txt.setText("<Topic>");
//        }
        txt.setText(topicList.get(position).getTitle());


        return row;
    }
}
