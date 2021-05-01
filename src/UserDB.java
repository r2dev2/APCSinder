import java.util.HashMap;
import java.util.HashSet;

/**
 * A persistent database for user authentication.
 */
public class UserDB
{
    private String filename;
    // Map username -> password
    private HashMap<String, String> passwords;
    // Map username -> token
    private HashMap<String, String> loggedIn;
    // Map username -> User
    private HashMap<String, User> users;

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
     * Returns the User with a given username
     * @param username the username
     * @return User for the username
     */
    public User getUser(String username) {
        return users.get(username);
    }

    private void load()
    {
        // TODO
    }

    private void save()
    {
        // TODO
    }
}
