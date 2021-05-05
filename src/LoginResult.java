import java.io.Serializable;
import java.util.UUID;

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
        return UUID.randomUUID().toString();
    }
}
