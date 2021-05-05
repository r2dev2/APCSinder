import java.io.Serializable;

public class LoginAttempt implements Serializable
{
    public final String username;
    public final String password;

    public LoginAttempt(String username, String password)
    {
        this.username = username;
        this.password = password;
    }
}