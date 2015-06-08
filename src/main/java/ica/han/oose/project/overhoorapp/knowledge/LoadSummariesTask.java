package ica.han.oose.project.overhoorapp.knowledge;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import ica.han.oose.project.overhoorapp.endpoints.summary.SummaryAPI;
import ica.han.oose.project.overhoorapp.json.wrappers.Summary;
import ica.han.oose.project.overhoorapp.persistence.PersistenceAdapter;
import ica.han.oose.project.overhoorapp.util.PreferenceSaver;

/**
 * Created by Jaap Weijland on 6-6-2015.
 */
public class LoadSummariesTask extends AsyncTask<Context, Void, List<Summary>> {
    @Override
    protected List<Summary> doInBackground(Context... params) {
        String username = PreferenceSaver.getFromPrefs(params[0], "username", null);
        if (!username.isEmpty()) {
            PersistenceAdapter persistenceAdapter = new PersistenceAdapter(params[0].getApplicationContext());
            return persistenceAdapter.getSummaries();
        } else {
            return SummaryAPI.getMySummaries();
        }
    }
}
