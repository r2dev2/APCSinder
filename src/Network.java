import java.util.ArrayList;
import java.util.HashMap;

/**
 * Networking for client.
 */
public class Network
{
    private String url;
    private String username;
    private LoginResult loginInfo;

    /**
     * Constructor.
     *
     * @param url the url of the server
     * @param username the user to login
     */
    public Network(String url, String username)
    {
        this.url = url;
        this.username = username;
        this.loginInfo = null;
    }

    /**
     * Logs in the user to the server.
     *
     * @param password the user's password
     * @return the result of the login
     */
    public LoginResult login(String password)
    {
        return null;
    }

    private interface EventListener<T>
    {
        public void onEvent(T m);
    }

    /**
     * Subscribe to new matches.
     *
     * @param onMatch the callback for when a new match is found
     */
    public void subscribeMatches(EventListener<Match> onMatch)
    {
    }

    /**
     * Subscribe to new chat messages.
     *
     * @param onMessage the callback for when a new chat message is sent
     */
    public void subscribeMessage(EventListener<Message> onMessage)
    {
    }

    /**
     * Get the matches the user has made already.
     *
     * @return the matches the user has made already
     */
    public ArrayList<Match> getMatches()
    {
        return null;
    }

    /**
     * Get the messages in the user's conversations
     *
     * @return map of other user to the message history with said user
     */
    public HashMap<String, ArrayList<Message>> getMessages()
    {
        return null;
    }
}
