package ica.han.oose.project.overhoorapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Arrays;

import ica.han.oose.project.overhoorapp.endpoints.user.UserAPI;
import ica.han.oose.project.overhoorapp.exceptions.IllegalMethodCallException;
import ica.han.oose.project.overhoorapp.json.constants.Get;
import ica.han.oose.project.overhoorapp.json.wrappers.User;
import ica.han.oose.project.overhoorapp.util.requestbuilder.impl.GetRequestBuilder;


/**
 * This class is the handler activity for the navigation drawer, so that the other activities don't have to implement anything
 * to realise the nav drawer. Every class that wishes to have the drawer should inherit from this class.
 *
 * @author Jaap Weijland
 * @version 1.0
 * @since 28-4-2015
 */
public abstract class NavigationDrawerActivity extends BaseActivity {

    private static final int NAVIGATION_DRAWER_SUMMARY = 1;
    private static final int NAVIGATION_DRAWER_LOGOUT = 2;
    protected static final String[] menuItems = {"", "Mijn Samenvattingen", "Uitloggen"};
    private DrawerLayout drawerLayout;
    private ListView drawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_navigation_drawer);
        super.setContainer(R.id.content_frame);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.drawer_container);

        // Set the adapter for the list view
        /*drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item_drawer,
                menuItems));*/
        final User user = UserAPI.getUserInfo();
        menuItems[0] = "Welkom, " + user;
        drawerList.setAdapter(new NavigationDrawerArrayAdapter(this, R.layout.list_item_drawer, Arrays.asList(menuItems)));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
    }

    /**
     * This class handles everything involving the handling of the option menu. When an item in the
     * option menu is selected, this method is called.
     *
     * @param item The item that is selected.
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    /**
     * This function ensures that classes which inherit from this class don't call the method from this classes superclass.
     *
     * @param i
     */
    @Override
    public int setContainer(int i) {
        throw new IllegalMethodCallException();
    }

    /**
     * This function ensures that classes which inherit from this class don't call the method from this classes superclass.
     *
     * @param i
     */
    @Override
    public void setContentView(int i) {
        throw new IllegalMethodCallException();
    }

    /**
     * Handles the logic when an item is selected.
     *
     * @param position
     */
    private void selectItem(int position) {
        handleStartActivity(position);
        drawerList.setItemChecked(position, true);
        drawerLayout.closeDrawer(drawerList);
    }

    /**
     * Handles the starting of other activities when an item is selected in the menu. Checks first if
     * the user chose to log out, otherwise find out which intent to start.
     *
     * @param position The position of the item clicked in the menu.
     */
    public void handleStartActivity(final int position) {
        if (position == NAVIGATION_DRAWER_LOGOUT) {
            logout();
        } else {
            startActivity(getIntentForPosition(position));
        }
    }

    /**
     * Determinating which intent should be started. Currently there is only 1 intent to start. Designed
     * to easily add more options to the menu.
     *
     * @param position The position of the item clicked in the menu.
     * @return The intent to start.
     */
    private Intent getIntentForPosition(final int position) {
        Intent intent = null;
        switch (position) {
            case NAVIGATION_DRAWER_SUMMARY:
                intent = new Intent(this, ica.han.oose.project.overhoorapp.knowledge.SummaryActivity.class);
                break;
        }
        return intent;
    }

    /**
     * Logs the user out of the app.
     */
    private void logout() {
        final Intent intent = new Intent(this, ica.han.oose.project.overhoorapp.login.LoginActivity.class);
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Uitloggen")
                .setMessage("weet je zeker dat u uit wil loggen?")
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserAPI.logout();
                    }
                })
                .setNegativeButton("Nee", null)
                .show();
    }

    /**
     * Listens to interaction with the menu.
     */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

}
