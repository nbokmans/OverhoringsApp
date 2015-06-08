package ica.han.oose.project.overhoorapp.endpoints.user;

import android.util.Log;

import org.apache.http.HttpStatus;

import java.io.IOException;
import java.util.List;

import ica.han.oose.project.overhoorapp.endpoints.Shared;
import ica.han.oose.project.overhoorapp.json.constants.Get;
import ica.han.oose.project.overhoorapp.json.constants.Post;
import ica.han.oose.project.overhoorapp.json.models.users.get.UserContainer;
import ica.han.oose.project.overhoorapp.json.models.users.get.Users;
import ica.han.oose.project.overhoorapp.json.wrappers.User;
import ica.han.oose.project.overhoorapp.util.genson.Deserializer;
import ica.han.oose.project.overhoorapp.util.requestbuilder.impl.GetRequestBuilder;
import ica.han.oose.project.overhoorapp.util.requestbuilder.impl.PostRequestBuilder;

/**
 * Provides easy access to endpoints related to users.
 *
 * @author Niels
 * @version 1.0
 * @since 1-6-2015
 */
public class UserAPI {

    /**
     * Private constructor to prevent instantiation.
     */
    private UserAPI() {
    }

    /**
     * Registers a user.
     * Returns <tt>true</tt> if the registration was successfull, <tt>false</tt> if not.
     *
     * @param firstName The first name of the account to register.
     * @param lastName  The last name of the accout to register.
     * @param username  The username of the account to register.
     * @param password  The password of the account to register.
     */
    public static boolean register(final String firstName, final String lastName, final String username, final String password) {
        final PostRequestBuilder builder = new PostRequestBuilder(Post.REGISTER.getUrl());
        builder.addKeyAndValue("firstname", firstName);
        builder.addKeyAndValue("lastname", lastName);
        builder.addKeyAndValue("username", username);
        builder.addKeyAndValue("password", password);
        Shared.execute(builder);
        try {
            return builder.getConnection().getResponseCode() != HttpStatus.SC_UNAUTHORIZED;
        } catch (IOException e) {
            Log.e("UserAPI", e.getMessage(), e);
        }
        return false;
    }

    /**
     * Returns an User object containing all the information of the currently logged in user.
     *
     * @return an User object containing all the information of the currently logged in user.
     */
    public static User getUserInfo() {
        final GetRequestBuilder builder = new GetRequestBuilder(Get.GET_USER_INFO.getUrl());
        Shared.execute(builder);
        try {
            if (builder.getConnection() != null && builder.getConnection().getResponseCode() != HttpStatus.SC_UNAUTHORIZED) {
                final UserContainer modelUser = (UserContainer)
                        Deserializer.deserialize(builder.getResult(), UserContainer.class);
                return new User(modelUser.getUser());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns a list containing all the users as a result of a search on an username.
     *
     * @param username The username to search for (can be partial).
     * @return A list containing all the users as a result of a search on an username.
     */
    public static List<User> findUserByUsername(final String username) {
        final GetRequestBuilder builder = new GetRequestBuilder(Get.FIND_BY_USERNAME.getUrl(username));
        Shared.execute(builder);
        final Users modelUsers = (Users) Deserializer.deserialize(builder.getResult(), Users.class);
        return modelUsers.getUserResults();
    }

    public static void logout() {
        final GetRequestBuilder builder = new GetRequestBuilder(Get.LOGOUT.getUrl());
        Shared.execute(builder);
    }

}
