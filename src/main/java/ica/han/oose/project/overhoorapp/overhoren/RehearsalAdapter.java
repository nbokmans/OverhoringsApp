package ica.han.oose.project.overhoorapp.overhoren;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ica.han.oose.project.overhoorapp.R;
import ica.han.oose.project.overhoorapp.json.models.rehearsals.read.Rehearsal;

/**
 * Created by coen on 4-6-2015.
 */
public class RehearsalAdapter extends ArrayAdapter<Rehearsal> {

    private Context context;
    private int resource;
    private int textViewResourceId;
    private List<Rehearsal> rehearsalList;

    public RehearsalAdapter(Context context, int resource, int textViewResourceId, List<Rehearsal> rehearsalList) {
        super(context, resource, textViewResourceId, rehearsalList);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.rehearsalList = rehearsalList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View row = convertView;
        row = inflater.inflate(resource, parent, false);
        TextView listTextView = (TextView) row.findViewById(R.id.list_item_basic_textview);
        listTextView.setText(this.rehearsalList.get(position).getLabel());

        return row;
    }



}
