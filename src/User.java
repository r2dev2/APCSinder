import java.io.Serializable;

/**
 * An immutable representation of a user to be used in the database and for communication.
 */
public class User implements Serializable
{
    /**
     * The username of the user.
     */
    public final String username;
    /**
     * A Myers-Briggs personality.
     */
    public final PersonalityType personality;
    /**
     * The user's description
     */
    public final String description;

    /**
     * Create a new User object.
     * @param name the user's name
     * @param type the user's personality type
     * @param desc the user's user description
     */
    public User(String name, PersonalityType type, String desc)
    {
        username = name;
        personality = type;
        description = desc;
    }

    /**
     * Create a new User object with an anonymous user description.
     * @param name the user's name
     * @param type the user's personality type
     */
    public User(String name, PersonalityType type)
    {
        this(name, type, "Apparently, this user prefers to keep an air of anonymity.");
    }

    /**
     * Create a new User object with a "Not found" name and a default personality type.
     */
    public User()
    {
        this("Not found", new PersonalityType());
    }

    /**
     * Returns an array of the user's preferred personality types.
     * @return an array of the user's preferred personality types.
     */
    public PersonalityType[] getPreferredTypes()
    {
        return personality.getPreferredTypes();
    }

    /**
     * Returns a string of the user in the form User(username, PersonalityType, user description)
     */
    public String toString()
    {
        return String.format("User(%s, %s, %s)", username, personality,
                description);
    }

    /**
     * Checks if a user is equal to the other user by username.
     * @param other the other user to compare with
     * @return true of the user's usernames are the same, false otherwise.
     */
    public boolean equals(User other)
    {
        return username.equals(other.username);
    }
}
