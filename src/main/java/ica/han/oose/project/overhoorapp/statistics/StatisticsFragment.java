package ica.han.oose.project.overhoorapp.statistics;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ica.han.oose.project.overhoorapp.R;

/**
 * TODO: korte beschrijving van klsse
 *
 * @author Roy van Keijsteren
 * @version 1.0
 * @since 5/20/2015
 */
public class StatisticsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View fragment = inflater.inflate(R.layout.fragment_statistics, container, false);
        return fragment;
    }
}
