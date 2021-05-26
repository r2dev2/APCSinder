import java.util.*;
import java.io.Serializable;

/**
 *  The UserRecord class holds the data on a user.
 *
 *  @author Ronak Badhe
 *  @version May 24, 2021
 */
public class UserRecord implements Serializable
{
    /**
     * The password of this user
     */
    public String password;
    /**
     * The user associated with this UserRecord
     */
    public User user;
    /**
     * List of users this user has matched with
     */
    public HashSet<String> matches;
    /**
     * List of users this user has accepted matches with
     */
    public HashSet<String> accepted;
    /**
     * List of users this user has rejected matches with
     */
    public HashSet<String> rejected;

    /**
     * Create a new UserRecord object.
     * @param user the user associated with this UserRecord
     * @param password the password linked to this UserRecord
     */
    public UserRecord(User user, String password)
    {
        this.user = user;
        this.password = password;
        this.matches = new HashSet<>();
        this.rejected = new HashSet<>();
        this.accepted = new HashSet<>();
    }

    /**
     * Gets the matches of the user.
     *
     * @return the matches of the user in ArrayList&lt;Match&gt; form.
     */
    public ArrayList<Match> getMatches()
    {
        var matches = new ArrayList<Match>();
        for (String other: this.matches) {
            if (user.username.equals(other)) continue;
            matches.add(new Match(user.username, other));
        }
        return matches;
    }

    /**
     * Returns whether the user has rejected the other user.
     *
     * @param other the other userrecord
     * @return whether the other person has been rejected
     */
    public boolean hasRejected(UserRecord other)
    {
        return rejected.contains(other.user.username);
    }

    /**
     * Get whether the user has accepted the other user.
     *
     * @param other the other userrecord
     * @return whether the other person has been approved for matching
     */
    public boolean hasAccepted(UserRecord other)
    {
        return accepted.contains(other.user.username);
    }

    /**
     * Get whether the user has matched with another user.
     *
     * @param other the other user
     * @return whether the user has matched with the other user.
     */
    public boolean hasMatched(UserRecord other)
    {
        return matches.contains(other.user.username);
    }

    /**
     * Approve of another user.
     *
     * @param user the user that the person wants to match with
     */
    public void acceptUser(String user)
    {
        accepted.add(user);
    }
}
