/**
 * This class is intentionally left undocumented.
 *
 * @author http://javafromjson.dashingrocket.com
 * @version 1.1
 * @since 01-06-2015
 */
package ica.han.oose.project.overhoorapp.json.models.users.get;

import java.util.ArrayList;

public class Users {
    private User[] users;

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public ArrayList<ica.han.oose.project.overhoorapp.json.wrappers.User> getUserResults() {
        final ArrayList<ica.han.oose.project.overhoorapp.json.wrappers.User> tempList = new ArrayList<>();
        for (final User user : users) {
            if (!contains(tempList, user.getUsername())) {
                tempList.add(new ica.han.oose.project.overhoorapp.json.wrappers.User(user));
            }
        }
        return tempList;
    }

    private boolean contains(final ArrayList<ica.han.oose.project.overhoorapp.json.wrappers.User>
                                     tempList, final String username) {
        for (final ica.han.oose.project.overhoorapp.json.wrappers.User user : tempList) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

}