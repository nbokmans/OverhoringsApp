package ica.han.oose.project.overhoorapp.json.wrappers;

/**
 * Provides a wrapper class for Users.
 *
 * @author Niels Bokmans
 * @version 1.0
 * @since 1-6-2015
 */
public class User {

    /**
     * Stores the User instance.
     */
    private ica.han.oose.project.overhoorapp.json.models.users.get.User user;

    /**
     * Creates a new User wrapper.
     *
     * @param user The (model) User instance.
     */
    public User(ica.han.oose.project.overhoorapp.json.models.users.get.User user) {
        this.user = user;
    }

    /**
     * Returns the user's object ID.
     *
     * @return The user's object ID.
     */
    public String getObjectID() {
        if (user.get_id() != null) {
            return user.get_id().get$oid();
        }
        return "UnknownUserObjectID";
    }

    /**
     * Returns the user's first name.
     *
     * @return The user's first name.
     */
    public String getFirstName() {
        if (user.getFirstName() != null && !user.getUsername().isEmpty()) {
            return user.getFirstName();
        }
        return "UnknownUserUsername";
    }

    /**
     * Returns the user's (model) version.
     *
     * @return The user's (model) version.
     */
    public double getVersion() {
        return user.getVersion();
    }

    /**
     * Returns the account last update time.
     *
     * @return The account's last update time.
     */
    public long getUpdateDate() {
        return user.getUpdateDate();
    }

    /**
     * Returns the user's last name.
     *
     * @return The user's last name.
     */
    public String getLastName() {
        return user.getLastName();
    }

    /**
     * Returns the user ID for this account.
     *
     * @return The user ID for this account.
     */
    public int getUserID() {
        if (user != null && user.getOwner() != null) {
            return Integer.parseInt(user.getOwner().getUid());
        }
        return -1;
    }

    /**
     * Returns the creation date of this account.
     *
     * @return The creation date of this account.
     */
    public long getCreationDate() {
        return user.getCreationDate();
    }

    /**
     * Returns isFollowing.
     * todo: wat doet isFollowing?
     *
     * @return isFollowing.
     */
    public boolean isFollowing() {
        return user.getIsFollowing();
    }

    /**
     * Returns the user's username (an e-mail).
     *
     * @return The user's username (an e-mail).
     */
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String toString() {
        final String first = getFirstName();
        final String last = getLastName();
        return (getFirstName().substring(0, 1).toUpperCase() + getFirstName().substring(1)).concat(" ").concat(getLastName());
    }

}
