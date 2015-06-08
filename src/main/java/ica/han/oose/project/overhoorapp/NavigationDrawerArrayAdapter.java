package ica.han.oose.project.overhoorapp;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * First item in Navigation Drawer is unclickable.
 *
 * @author Niels Bokmans
 * @version 1.0
 * @since 4-6-2015
 */
public class NavigationDrawerArrayAdapter extends ArrayAdapter<String> {
    public NavigationDrawerArrayAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(final int position) {
        return position != 0;
    }
}
