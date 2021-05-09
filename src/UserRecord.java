import java.util.*;
import java.io.Serializable;

public class UserRecord implements Serializable
{
    public String password;
    public User user;
    public HashSet<String> matches;
    public HashSet<String> accepted;
    public HashSet<String> rejected;

    public UserRecord(User user, String password)
    {
        this.user = user;
        this.password = password;
        this.matches = new HashSet<>();
        this.rejected = new HashSet<>();
        this.accepted = new HashSet<>();
    }

    public ArrayList<Match> getMatches()
    {
        var matches = new ArrayList<Match>();
        for (String other: this.matches) {
            if (user.username.equals(other)) continue;
            matches.add(new Match(user.username, other));
        }
        return matches;
    }

    public boolean hasRejected(UserRecord other)
    {
        return rejected.contains(other.user.username);
    }

    public boolean hasAccepted(UserRecord other)
    {
        return accepted.contains(other.user.username);
    }

    public void acceptUser(String user)
    {
        accepted.add(user);
    }
}
