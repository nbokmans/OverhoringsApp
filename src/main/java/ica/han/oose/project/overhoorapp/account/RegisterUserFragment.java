package ica.han.oose.project.overhoorapp.account;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ica.han.oose.project.overhoorapp.R;

/**
 * The user registration fragment.
 *
 * @author Wilko Zonnenberg
 * @author Bob Linthorst
 * @version 1.0
 * @since 22-4-2015
 */
public class RegisterUserFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_user, container, false);
    }
}
