package ica.han.oose.project.overhoorapp.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ica.han.oose.project.overhoorapp.NavigationDrawerActivity;
import ica.han.oose.project.overhoorapp.R;
import ica.han.oose.project.overhoorapp.endpoints.user.UserAPI;
import ica.han.oose.project.overhoorapp.login.LoginActivity;

/**
 * Sends register request to server.
 *
 * @author Wilko Zonnenberg
 * @version 1.0
 * @since 21-4-2015
 */
public class RegisterActivity extends NavigationDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            RegisterUserFragment registerUserFragment = new RegisterUserFragment();
            setFragment(registerUserFragment);
        }
    }

    /**
     * Gets the credentials and sends it to the server if it validates
     *
     * @param v The view the register request was sent from.
     */
    public void buttonRegisterOnClick(View v) {
        String firstName = ((TextView) findViewById(R.id.name)).getText().toString();
        String lastName = ((TextView) findViewById(R.id.lastName)).getText().toString();
        String email = ((TextView) findViewById(R.id.mail)).getText().toString();
        String password = ((TextView) findViewById(R.id.password)).getText().toString();
        String passwordRepeated = ((TextView) findViewById(R.id.passwordRepeated)).getText().toString();
        AccountValidator validator = new AccountValidator();
        if (validator.validateCredentials(firstName, lastName, email, password, passwordRepeated)) {
            if (sendInputToServer(firstName, lastName, email, password)) {
                Intent intent = new Intent(this, LoginActivity.class);
                intent.putExtra("Username", email);
                intent.putExtra("Password", password);
                startActivity(intent);
            }
        } else {
            Toast toast = Toast.makeText(this, "De ingevulde gegevens zijn niet correct!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    /**
     * Sends register form to server to register the user.
     *
     * @param firstName firstName of the user.
     * @param lastName  LastName of the user.
     * @param email     Email of the user.
     * @param password  Validated password.
     * @return <tt>true</tt> if the registration succeeded, <tt>false</tt> otherwise.
     */
    public boolean sendInputToServer(final String firstName, final String lastName, final String email, final String password) {
        return UserAPI.register(firstName, lastName, email, password);
    }


}
