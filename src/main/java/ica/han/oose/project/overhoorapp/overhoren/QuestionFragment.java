package ica.han.oose.project.overhoorapp.overhoren;

import android.app.Fragment;
import android.content.Context;

import ica.han.oose.project.overhoorapp.exceptions.PersistenceAdapterNotSetException;
import ica.han.oose.project.overhoorapp.persistence.PersistenceAdapter;

/**
 * Created by coen on 3-6-2015.
 */
public class QuestionFragment extends Fragment {

    private PersistenceAdapter persistenceAdapter;

    public void setPersistenceAdapter(Context context) {
        persistenceAdapter = new PersistenceAdapter(context);
    }

    protected void sendAnswer(int key, String value) {
        if (persistenceAdapter == null) {
            throw new PersistenceAdapterNotSetException();
        } else {
            // persistenceAdapter.answerQuestion(key, value);
        }
    }
}
