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
    private final PersonalityType personality; //TODO finish setup

    public User(String username, PersonalityType personality)
    {
        this.username = username;
        this.personality = personality;
    }

    public PersonalityType[] getPreferredTypes()
    {
        return personality.getPreferredTypes();
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
