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
import ica.han.oose.project.overhoorapp.json.wrappers.Summary;

/**
 * TODO: korte beschrijving van klsse
 *
 * @author Jaap Weijland
 * @version 1.0
 * @since 20-5-2015
 */
public class SummaryListAdapter extends ArrayAdapter<Summary> {

    private Context cont;
    private int resource;
    private int textViewResourceId;
    private List<Summary> summaryList;

    public SummaryListAdapter(Context context, int resource, int textViewResourceId, List<Summary> summaryList) {
        super(context, resource, textViewResourceId, summaryList);
        this.cont = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.summaryList = summaryList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) cont).getLayoutInflater();
        View row = convertView;
        row = inflater.inflate(resource, parent, false);
        TextView txt = (TextView) row.findViewById(R.id.list_item_basic_textview);
        txt.setText(summaryList.get(position).getTitle());
        return row;
    }
}
