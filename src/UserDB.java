import java.util.HashMap;
import java.util.HashSet;

/**
 * A persistent database for user authentication.
 */
public class UserDB
{
    private String filename;
    // Map user -> password
    private HashMap<User, String> passwords;
    // Map user -> token
    private HashMap<User, String> loggedIn;

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
     * @param user the user to log in
     * @param password the user's password
     */
    public LoginResult login(User user, String password)
    {
        // TODO
        return null;
    }

    /**
     * Checks if the user is logged in with the token.
     *
     * @param user the user
     * @param token the token
     */
    public boolean authenticate(User user, String token)
    {
        return false;
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
