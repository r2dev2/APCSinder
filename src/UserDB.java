import java.util.*;
import java.io.*;
import java.nio.file.*;

/**
 * A persistent database for user authentication and matching.
 */
public class UserDB extends BaseDB<HashMap<String, UserRecord>, Match>
{
    // Map username -> user record
    private HashMap<String, UserRecord> users;
    // Map token -> username
    private HashMap<String, String> loggedIn;
    // Map PersonalityType -> array of usernames
    private HashMap<PersonalityType, List<String>> userPersonalities;

    private MatchingDB mdb;

    /**
     * Constructor.
     *
     * @param filename the filename to persist to
     */
    public UserDB(String filename)
    {
        super(filename, new HashMap<>());
        userPersonalities = new HashMap<>();
        loggedIn = new HashMap<>();
        users = load();
        loadPersonalityIndex();
        mdb = new MatchingDB(filename == null ? null : filename + ".pot.db");
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
        registerTokenIfSuccess(result, username);
        return result;
    }

    private void registerTokenIfSuccess(LoginResult res, String username)
    {
        if (res.success) {
            loggedIn.put(res.token, username);
        }
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
        addUserToRecord(newUser.user, newUser.password);
        addUserToPersonalityIndex(newUser.user.username);
        addNewUserPotentialMatches(newUser.user);
        save(users);
        return true;
    }

    private void addUserToRecord(User user, String password)
    {
        users.put(user.username, new UserRecord(user, password));
    }

    private void addNewUserPotentialMatches(User user)
    {
        for (PersonalityType p: user.getPreferredTypes()) {
            for (String other: userPersonalities.getOrDefault(p, new ArrayList<>())) {
                if (other.equals(user.username)) continue;
                mdb.addPotential(user.username, other);
            }
        }
    }

    public boolean createMatch(Match m)
    {
        var first = getUser(m.firstUser);
        var second = getUser(m.secondUser);
        if (oneRejectedOther(first, second)) return false;
        mdb.removePotential(m);
        matchWith(first, second);
        return true;
    }

    private boolean oneRejectedOther(UserRecord first, UserRecord second)
    {
        return first.hasRejected(second) || second.hasRejected(first);
    }

    public void subscribePotentialMatches(String id, BaseDB.Subscriber<Match> subscriber)
    {
        mdb.subscribe(id, subscriber);
    }

    private void matchWith(UserRecord first, String second)
    {
        var firstUsername = first.user.username;
        first.matches.add(second);
        notifySubscriber(new Match(firstUsername, second), firstUsername);
    }

    private void matchWith(UserRecord first, UserRecord second)
    {
        matchWith(first, second.user.username);
        matchWith(second, first.user.username);
    }

    public void reject(String user, String other)
    {
        mdb.removePotential(user, other);
        getUser(user).rejected.add(other);
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

    public Set<String> getMatches(String username)
    {
        return users.get(username).matches;
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
        var usernames = userPersonalities.getOrDefault(personality, new ArrayList<>());
        usernames.add(username);
        userPersonalities.put(personality, usernames);
    }
}
