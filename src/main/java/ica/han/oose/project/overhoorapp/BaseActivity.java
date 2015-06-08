package ica.han.oose.project.overhoorapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


/**
 * Created by roy_000 on 4/23/2015.
 */
public abstract class BaseActivity extends ActionBarActivity {

    //private int container = R.layout.activity_navigation_drawer;
    private int container = R.layout.empty_container;

    public int setContainer(int cont) {
        container = cont;
        return container;
    }

    protected void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(container, fragment);
        fragmentTransaction.commit();
    }

    protected void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    // Needed for hiding the keyboard when touching inputless screen part
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        View v = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(event);

        if (v instanceof EditText) {
            View w = getCurrentFocus();
            int scrcoords[] = new int[2];
            w.getLocationOnScreen(scrcoords);
            float x = event.getRawX() + w.getLeft() - scrcoords[0];
            float y = event.getRawY() + w.getTop() - scrcoords[1];

            Log.d("Activity", "Touch event " + event.getRawX() + "," + event.getRawY() + " " + x + "," + y + " rect " + w.getLeft() + "," + w.getTop() + "," + w.getRight() + "," + w.getBottom() + " coords " + scrcoords[0] + "," + scrcoords[1]);
            if (event.getAction() == MotionEvent.ACTION_UP && (checkX(w, x) || checkY(w, y))) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
            }
        }
        return ret;
    }

    private boolean checkX(final View w, final float x) {
        return x < w.getLeft() || x >= w.getRight();
    }

    private boolean checkY(final View w, final float y) {
        return y < w.getTop() || y > w.getBottom();
    }


}
