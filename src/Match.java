import java.io.Serializable;

public class Match implements Serializable
{
    public final String firstUser;
    public final String secondUser;
    public final float score;

    public Match(String firstUser, String secondUser, float score)
    {
        if (firstUser.compareTo(secondUser) < 0) {
            this.firstUser = firstUser;
            this.secondUser = secondUser;
        }
        else {
            this.firstUser = secondUser;
            this.secondUser = firstUser;
        }
        this.score = score;
    }

    public Match(String firstUser, String secondUser)
    {
        this(firstUser, secondUser, 5);
    }

    public int hashCode()
    {
        return firstUser.hashCode() ^ secondUser.hashCode();
    }

    public boolean equals(Match other)
    {
        return firstUser.equals(other.firstUser) && secondUser.equals(other.secondUser);
    }

    public boolean equals(Object other)
    {
        try {
            return equals((Match) other);
        }
        catch (Exception e) {
            return false;
        }
    }

    public String toString()
    {
        return String.format("Match(%s, %s, %3f)", firstUser, secondUser, score);
    }
}
