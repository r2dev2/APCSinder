import java.io.Serializable;

public class LoginResult implements Serializable
{
    public final boolean success;
    public final String token;

    public LoginResult(boolean success)
    {
        this.token = success ? generateToken() : null;
        this.success = success;
    }

    private static String generateToken()
    {
        return "";
    }
}
