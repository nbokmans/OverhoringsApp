package ica.han.oose.project.overhoorapp.question;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ica.han.oose.project.overhoorapp.R;
import ica.han.oose.project.overhoorapp.json.models.questions.get.QuestionWrapper;

/**
 * TODO: korte beschrijving van klsse
 *
 * @author Jaap Weijland
 * @version 1.0
 * @since 20-5-2015
 */
public class QuestionListAdapter extends ArrayAdapter<QuestionWrapper> {

    private Context context;
    private int resource;
    private List<QuestionWrapper> questionList;

    public QuestionListAdapter(Context context, int resource, int textViewResourceId, List<QuestionWrapper> questionList) {
        super(context, resource, textViewResourceId, questionList);
        this.context = context;
        this.resource = resource;
        this.questionList = questionList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View row = convertView;
        row = inflater.inflate(resource, parent, false);
        TextView listTextView = (TextView) row.findViewById(R.id.list_item_basic_textview);
        listTextView.setText(this.questionList.get(position).getStatement().getText().getValue());
        return row;
    }
}
