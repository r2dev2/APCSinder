import java.io.Serializable;

/**
 * An immutable representation of a user to be used in the database and for communication.
 */
public class User implements Serializable
{
    public final String username;
    /**
     * A Myers-Briggs personality.
     */
    public final PersonalityType personality;
    public final String description;

    public User(String name, PersonalityType type, String desc)
    {
        username = name;
        personality = type;
        description = desc;
    }

    public User(String name, PersonalityType type)
    {
        this(name, type, "Apparently, this user prefers to keep an air of anonymity.");
    }

    public User()
    {
        this("Not found", new PersonalityType());
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
