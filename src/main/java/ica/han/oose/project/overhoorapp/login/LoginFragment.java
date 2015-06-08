package ica.han.oose.project.overhoorapp.login;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ica.han.oose.project.overhoorapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * { LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private View rootView;

    public LoginFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static LoginFragment newInstance(int sectionNumber) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public View getRootView() {
        return rootView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
        return rootView;
    }
}
