import java.io.Serializable;

public class UserCreationAttempt implements Serializable
{
    public final User user;
    public final String password;

    public UserCreationAttempt(User user, String password)
    {
        this.user = user;
        this.password = password;
    }
}
