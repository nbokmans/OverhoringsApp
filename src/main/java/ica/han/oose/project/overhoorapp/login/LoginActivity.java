package ica.han.oose.project.overhoorapp.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import ica.han.oose.project.overhoorapp.BaseActivity;
import ica.han.oose.project.overhoorapp.R;
import ica.han.oose.project.overhoorapp.account.RegisterActivity;
import ica.han.oose.project.overhoorapp.exceptions.UnauthorizedRequestException;
import ica.han.oose.project.overhoorapp.json.constants.Post;
import ica.han.oose.project.overhoorapp.knowledge.SummaryActivity;
import ica.han.oose.project.overhoorapp.util.Config;
import ica.han.oose.project.overhoorapp.util.PreferenceSaver;
import ica.han.oose.project.overhoorapp.util.requestbuilder.impl.PostRequestBuilder;

/**
 * This class handles all activities involving the login process. Displays the login fragment and handles the login procedure.
 *
 * @author Jaap Weijland
 * @date 30-04-2015
 */
public class LoginActivity extends BaseActivity {//implements LoginSuccessFragment.OnFragmentInteractionListener {

    /**
     * The statuscode returned by the loginHandler. Needed to check whether the user is authenticated or not.
     */
    private int statusCode;

    ProgressDialog progressDialog;

    /**
     * The emailfields and passwordfields for retrieving the user entered information.
     */
    private EditText emailField;
    private EditText passwordField;

    /**
     * The onCreate method, inherited from the Activity superclass. Called whenever this activity gets created. Sets and displays the first fragment.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty_container);
        setContainer(R.id.empty_container);
        getSupportActionBar().hide();
        if (savedInstanceState == null) {
            LoginFragment loginFragment = new LoginFragment();
            setFragment(loginFragment);
        }
    }

    /**
     * The onStart method, inherited from the Activity superclass. Called when this is activity is created and ready to run. Retrieves the input fields and sets dummy text.
     */
    @Override
    public void onStart() {
        super.onStart();
        emailField = (EditText) findViewById(R.id.textEmailAddress);
        passwordField = (EditText) findViewById(R.id.textPassword);
        String username = "";
        String password = "";
        if (getIntent().hasExtra("Username") && getIntent().hasExtra("Password")) {
            username = getIntent().getExtras().getString("Username");
            password = getIntent().getExtras().getString("Password");
        } else { //TODO: Haal dit weg bij oplevering.
            username = "roy_vankeysteren@hotmail.com";
            password = "qwerty12";
        }
        // TODO !!
        emailField.setText(username);
        passwordField.setText(password);
    }

    /**
     * Handles the logic when the user clicks on the button to log in. It gets the user credentials, then starts a seperate thread to handle
     * the login logic. That thread passes the credentials to the loginhandler, which tries to login with the data. When succeeded,
     * the loginhandler returns 303.
     *
     * @param v the view on which is clicked.
     */
    public void loginClick(View v) {
        final ProgressDialog progressUnit = new ProgressDialog(this);
        progressUnit.setTitle("Even geduld!");
        progressUnit.setMessage("Aan het inloggen...");
        progressUnit.show();
        final Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                final PostRequestBuilder builder = new PostRequestBuilder(Post.LOGIN.getUrl());
                builder.addKeyAndValue("username", getEmailFieldText());
                builder.addKeyAndValue("password", getPasswordFieldText());
                try {
                    builder.submit();
                    setStatusCode(builder.getConnection().getResponseCode());
                } catch (UnauthorizedRequestException | IOException e) {
                    Log.e("LoginActivity", e.getMessage(), e);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        afterLogin(getEmailFieldText(), getPasswordFieldText());
                    }
                });
            }
        });
        t.start();
    }

    public void registerClick(View v) {
        final Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }


    private String getPasswordFieldText() {
        return passwordField.getText().toString();
    }

    private String getEmailFieldText() {
        return emailField.getText().toString();
    }

    private void afterLogin(String user, String pass) {
        // If returned statuscode = 303, proceed with next activity
        if (statusCode == 303) {
            loginIsSuccess();
            Config.username = user;
            Config.password = pass;

            //save username to be used throughout the application
            PreferenceSaver.saveToPrefs(this, "username", user);

        } else {
            Toast.makeText(getApplicationContext(), "Er is een fout opgetreden tijdens het inloggen!", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Called when the login procedure was passed. Replaces the current fragment with LoginSuccessfragment.
     */
    public void loginIsSuccess() {
        final Intent intent = new Intent(this, SummaryActivity.class);
        startActivity(intent);
    }

    /**
     * Standard android method for creating the options menu
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    /**
     * Standard android method for handling option menu selections
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

/*    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.v("LoginActivity", "Interaction");
    }*/

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("username", getEmailFieldText());
        outState.putString("password", getPasswordFieldText());
    }

    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        emailField.setText(outState.getString("username"));
        passwordField.setText(outState.getString("password"));
    }
}
