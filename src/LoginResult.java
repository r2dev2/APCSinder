import java.io.Serializable;
import java.util.UUID;

/**
 *  This class stores the result of a login attempt.
 *
 *  @author Ronak Badhe
 *  @version May 8, 2021
 */
public class LoginResult implements Serializable
{
    /**
     * Represents whether or not this login was a success.
     */
    public final boolean success;
    /**
     * A uuid login token.
     */
    public final String token;

    /**
     * Create a new LoginResult object.
     * @param success whether or not this login was successful
     */
    public LoginResult(boolean success)
    {
        this.token = success ? generateToken() : null;
        this.success = success;
    }

    /**
     * Returns a string representation of this login result.
     * @return a string representation of this login result.
     */
    public String toString()
    {
        return String.format("LoginResult(%s, '%s')", success, token);
    }

    private static String generateToken()
    {
        return UUID.randomUUID().toString();
    }
}
