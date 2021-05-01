import java.io.Serializable;

/**
 * An immutable representation of a user to be used in the database and for communication.
 */
public class User implements Serializable
{
    public final String username;
    /**
     * String that represents a Myers-Briggs personality. Determined by adding up
     * numbers from PersonalityTest:
     *
     * Types that match well together:
     * ISTJ + ESTP
     * INTP + INTJ
     * ENFP + INFJ
     * ENTJ + ISTP
     * ISFP + ESFP
     */
    public final String personalityType; //TODO finish setup

    public User(String username, String personalityType)
    {
        this.username = username;
        this.personalityType = personalityType;
    }

    public String toString()
    {
        return String.format("User(%s)", username);
    }

    public boolean equals(User other)
    {
        return username.equals(other.username);
    }
}
