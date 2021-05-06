import java.util.*;
import java.io.*;
import java.nio.file.*;

/**
 * A persistent database for user authentication.
 */
public class UserDB extends PersistentDB<HashMap<String, UserDB.UserRecord>>
{
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
        super(filename, new HashMap<String, UserRecord>());
        userPersonalities = new HashMap<PersonalityType, List<String>>();
        loggedIn = new HashMap<String, String>();
        users = load();
        loadPersonalityIndex();
    }

    /**
     * Logs in a user.
     *
     * @param username the username of the user to log in
     * @param password the user's password
     */
    public LoginResult login(String username, String password)
    {
        var record = users.getOrDefault(username,
                new UserRecord(null, "\n" + password));
        var result = new LoginResult(password.equals(record.password) &&
                !loggedIn.containsKey(username));
        if (result.success) {
            loggedIn.put(username, result.token);
        }
        return result;
    }

    /**
     * Checks if the user is logged in with the token.
     *
     * @param username the username
     * @param token the token
     */
    public boolean authenticate(String username, String token)
    {
        return loggedIn.getOrDefault(username, "\n" + token).equals(token);
    }

    /**
     * Creates a new user.
     *
     * @param newUser the information for the new user
     * @return whether the user was created
     */
    public boolean createUser(UserCreationAttempt newUser)
    {
        if (users.containsKey(newUser.user.username)) return false;
        users.put(newUser.user.username,
                  new UserRecord(newUser.user, newUser.password));
        addUserToPersonalityIndex(newUser.user.username);
        save(users);
        return true;
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

    private void loadPersonalityIndex()
    {
        users.keySet().forEach(this::addUserToPersonalityIndex);
    }

    private void addUserToPersonalityIndex(String username)
    {
        var personality = users.get(username).user.personality;
        var usernames = userPersonalities.getOrDefault(
            personality, new ArrayList<String>()
        );
        usernames.add(username);
        userPersonalities.put(personality, usernames);
    }

    public static class UserRecord implements Serializable
    {
        public String password;
        public User user;
        public HashSet<String> matches;
        public HashSet<String> rejected;

        public UserRecord(User user, String password)
        {
            this.user = user;
            this.password = password;
            matches = new HashSet<String>();
            rejected = new HashSet<String>();
        }
    }
}
