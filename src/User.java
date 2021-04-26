import java.io.Serializable;

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
    public String personalityType;

    public User(String username)
    {
        this.username = username;
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
