import java.io.Serializable;

/**
 *  The UserCreationAttempt class is a class representing one user
 *  creation attempt that is linked to an attempted user and password.
 *
 *  @author Ronak Badhe
 *  @version May 24, 2021
 */
public class UserCreationAttempt implements Serializable
{
    /**
     * The user linked to this UserCreationAttempt
     */
    public final User user;
    /**
     * The password linked to this UserCreationAttempt
     */
    public final String password;

    /**
     * Create a new UserCreationAttempt object.
     * @param user the user to associate this UserCreationAttempt
     * @param password the password linked to this UserCreationAttempt
     */
    public UserCreationAttempt(User user, String password)
    {
        this.user = user;
        this.password = password;
    }
}
