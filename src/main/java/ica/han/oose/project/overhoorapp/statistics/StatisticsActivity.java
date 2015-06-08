package ica.han.oose.project.overhoorapp.statistics;

import android.app.Fragment;
import android.os.Bundle;

import ica.han.oose.project.overhoorapp.NavigationDrawerActivity;

/**
 * TODO: korte beschrijving van klsse
 *
 * @author Roy van Keijsteren
 * @version 1.0
 * @since 5/20/2015
 */
public class StatisticsActivity extends NavigationDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fragment statisticsFragment = new StatisticsFragment();
        replaceFragment(statisticsFragment);
    }
}
