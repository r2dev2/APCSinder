import java.io.Serializable;

/**
 *  TODO
 *
 *  @author Ronak Badhe
 *  @version May 8, 2021
 */
public class LoginAttempt implements Serializable
{
    /**
     * The username associated with this login attempt
     */
    public final String username;
    /**
     * The passworda associated with this login attempt
     */
    public final String password;

    /**
     * Create a new LoginAttempt object.
     * @param username the username associated with this login attempt
     * @param password the password associated with this login attempt
     */
    public LoginAttempt(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    /**
     * Returns a string representation of this login attempt.
     * @return a string representation of this attempt in LoginAttempt(username, password)
     */
    public String toString()
    {
        return String.format("LoginAttempt('%s', '%s')", username, password);
    }
}
