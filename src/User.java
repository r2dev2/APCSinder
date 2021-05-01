import java.io.Serializable;

public class User implements Serializable
{
    private final String username;
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
    private String personalityType;

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

    public String getUsername() {
        return username;
    }

    public String getPersonalityType() {
        return personalityType;
    }

    public void setPersonalityType(String type) {
        personalityType = type;
    }
}
