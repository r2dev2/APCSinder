import java.io.Serializable;

/**
 *  This class represents a match between two users.
 *
 *  @author Ronak Badhe
 *  @version May 8, 2021
 */
public class Match implements Serializable
{
    /**
     * This represents the user with the lexicographically smaller name.
     */
    public final String firstUser;
    /**
     * This represents the user with the lexicographically larger name.
     */
    public final String secondUser;
    /**
     * A score representing how well the match is (use later)
     */
    public final float score;

    /**
     * Create a new Match object.
     * @param firstUser the first user
     * @param secondUser the second user
     * @param score matching score (doesn't exist yet)
     */
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

    /**
     * Create a new Match object.
     * @param firstUser the first user
     * @param secondUser the second user
     */
    public Match(String firstUser, String secondUser)
    {
        this(firstUser, secondUser, 5);
    }

    /**
     * Returns a hashcode for this match.
     * @return a hashcode for this match
     */
    public int hashCode()
    {
        return firstUser.hashCode() ^ secondUser.hashCode();
    }

    /**
     * Checks if two matches are equal by comparing their first user and second user.
     * @param other the other match to compare to
     * @return true if the first and second users line up, false otherwise.
     */
    public boolean equals(Match other)
    {
        return firstUser.equals(other.firstUser) && secondUser.equals(other.secondUser);
    }

    /**
     * Checks if this match equal to the other match by their first and second user.
     * @param other the other match to compare to.
     * @return true of the other object is a match and the first and seconds users are equal, false otherwise.
     */
    public boolean equals(Object other)
    {
        try {
            return equals((Match) other);
        }
        catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if a user is in this match.
     * @param user  username of user
     * @return  true if in match, false otherwise
     */
    public boolean hasUser(String user)
    {
        return firstUser.equals(user) || secondUser.equals(user);
    }

    /**
     * Returns the username of the other user in a match when
     * given one user.
     * @param user  username of one user in the match
     * @return  username of the other user in the match
     */
    public String otherUser(String user)
    {
        return secondUser.equals(user) ? firstUser : secondUser;
    }

    /**
     * Returns a string representation of this class.
     * @return a string representation of this class in the form Match(first user, second user, score)
     */
    public String toString()
    {
        return String.format("Match(%s, %s, %3f)", firstUser, secondUser, score);
    }
}
