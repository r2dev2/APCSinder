import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.io.Serializable;

/**
 * A persistent database for user authentication.
 */
public class UserDB
{
    private String filename;
    // Map username -> user record
    private HashMap<String, UserRecord> users;
    // Map username -> token
    private HashMap<String, String> loggedIn;
    // Map PersonalityType -> array of usernames
    private HashMap<PersonalityType, List<String>> userPersonalities;

    /**
     * Constructor.
     *
     * @param filename the filename to persist to
     */
    public UserDB(String filename)
    {
        this.filename = filename;
    }

    /**
     * Logs in a user.
     *
     * @param username the username of the user to log in
     * @param password the user's password
     */
    public LoginResult login(String username, String password)
    {
        // TODO
        return null;
    }

    /**
     * Checks if the user is logged in with the token.
     *
     * @param username the username
     * @param token the token
     */
    public boolean authenticate(String username, String token)
    {
        return false;
    }

    /**
     * Returns the User with a given username.
     *
     * @param username the username
     * @return User for the username
     */
    public User getUser(String username)
    {
        return users.get(username).user;
    }

    /**
     * Returns the list of usernames for a given personality type.
     *
     * @param type the personality type
     * @return String array of matching usernames
     */
    public List<String> getUserPersonalities(PersonalityType type) {
        return userPersonalities.get(type);
    }

    private void load()
    {
        // TODO
    }

    private void save()
    {
        // TODO
    }

    private static class UserRecord implements Serializable
    {
        public String password;
        public User user;
        public HashSet<String> matches;
        public HashSet<String> rejected;

        public UserRecord(String password, User user)
        {
            this.password = password;
            this.user = user;
            matches = new HashSet<String>();
            rejected = new HashSet<String>();
        }
    }
}
