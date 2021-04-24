import java.util.HashMap;
import java.util.HashSet;

public class UserDB
{
    private String filename;
    // Map user -> password
    private HashMap<User, String> passwords;
    // Map user -> token
    private HashMap<User, String> loggedIn;

    public UserDB(String filename)
    {
        this.filename = filename;
    }

    public LoginResult login(User user, String password)
    {
        // TODO
        return null;
    }

    public boolean authenticate(User user, String token)
    {
        return false;
    }

    private void load()
    {
        // TODO
    }
}
