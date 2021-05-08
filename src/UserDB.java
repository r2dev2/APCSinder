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
    // Map token -> username
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
        var result = new LoginResult(password.equals(record.password));
        if (result.success) {
            loggedIn.put(result.token, username);
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
        return loggedIn.getOrDefault(token, "\n" + username).equals(username);
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

    public boolean createMatch(Match m)
    {
        var first = getUser(m.firstUser);
        var second = getUser(m.secondUser);
        if (first.rejected.contains(second) || second.rejected.contains(first)) return false;
        matchWith(first, m.secondUser);
        matchWith(second, m.firstUser);
        return true;
    }

    private void matchWith(UserRecord first, String second)
    {
        first.matches.add(second);
    }

    /**
     * Returns the User with a given username.
     *
     * @param username the username
     * @return User for the username
     */
    public UserRecord getUser(String username)
    {
        return users.get(username);
    }

    /**
     * Returns the username linked to a token.
     *
     * @param token the token
     * @return the username of the token
     */
    public String getUsername(String token)
    {
        return loggedIn.getOrDefault(token, null);
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
